package net.masterzach32.spicypineapple.entity

import net.masterzach32.spicypineapple.util.distance
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityFallingBlock
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.DamageSource
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class EntityBlackHole(world: World) : Entity(world) {

    var timer: Int = DURATION

    init {
        isInvisible = true
    }

    override fun writeEntityToNBT(compound: NBTTagCompound) {
        compound.setDouble("posX", posX)
        compound.setDouble("posY", posY)
        compound.setDouble("posZ", posZ)
        compound.setInteger("timer", timer)
    }

    override fun readEntityFromNBT(compound: NBTTagCompound) {
        posX = compound.getDouble("posX")
        posY = compound.getDouble("posY")
        posZ = compound.getDouble("posZ")
        timer = compound.getInteger("timer")
    }

    override fun entityInit() {}

    override fun onUpdate() {
        super.onUpdate()

        if (world.isRemote) {

        } else {
            val currentRadius = RADIUS - (timer.toDouble() / DURATION) * RADIUS + 2
            if (timer % 2 == 0) {
                world.loadedEntityList
                        .filter { it != this && Math.sqrt(sqr(posX-it.posX) + sqr(posY - it.posY) + sqr(posZ - it.posZ)) < currentRadius }
                        .forEach {
                            val dirVec = Vec3d(posX - it.posX, posY - it.posY, posZ - it.posZ).normalize()
                            it.addVelocity(dirVec.x, dirVec.y, dirVec.z)
                            it.velocityChanged = true
                            if (position.distance(it.position) < 2) {
                                if (it is EntityLivingBase)
                                    it.attackEntityFrom(DamageSource.WITHER, 4f)
                                else
                                    it.setDead()
                            }
                        }
                val blocks = BlockPos.getAllInBoxMutable(
                        BlockPos(position.x - currentRadius, position.y - currentRadius, position.z - currentRadius),
                        BlockPos(position.x + currentRadius, position.y + currentRadius, position.z + currentRadius)
                )
                blocks
                        .filter { it.distance(position) < currentRadius }
                        .filter { !world.isAirBlock(it) }
                        .map { Pair(it, world.getBlockState(it)) }
                        .forEach {
                            val entityBlock = EntityFallingBlock(world, it.first.x.toDouble(), it.first.y.toDouble(), it.first.z.toDouble(), it.second)
                            val dirVec = Vec3d(posX - it.first.x, posY - it.first.y, posZ - it.first.z).normalize().scale(4.0)
                            entityBlock.posY += 0.5
                            entityBlock.addVelocity(dirVec.x, dirVec.y, dirVec.z)
                            entityBlock.velocityChanged = true
                            entityBlock.fallTime = 1
                            world.spawnEntity(entityBlock)
                        }
            }
            timer--
            if (timer <= 0)
                setDead()
        }
    }

    override fun isInvisibleToPlayer(player: EntityPlayer): Boolean = true

    private fun sqr(x: Double): Double = x * x

    companion object {
        const val RADIUS = 6
        const val DURATION = 90
    }
}