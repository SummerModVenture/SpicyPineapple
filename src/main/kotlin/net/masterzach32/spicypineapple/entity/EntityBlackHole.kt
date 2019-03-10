package net.masterzach32.spicypineapple.entity

import com.spicymemes.core.util.distance
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.DamageSource
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import kotlin.math.*

class EntityBlackHole(world: World, var entity: EntityPlayer?) : Entity(world) {

    var timer: Int = DURATION

    init {
        isInvisible = true
    }

    constructor(world: World) : this(world, null)

    fun shoot(pitch: Double, yaw: Double, velocity: Double) {
        val x = -sin(yaw * 0.017453292) * cos(pitch * 0.017453292)
        val y = -sin(pitch * 0.017453292)
        val z = cos(yaw * 0.017453292) * cos(pitch * 0.017453292)

        val f = sqrt(x.pow(2) + y.pow(2) + z.pow(2))
        this.motionX = x / f * velocity
        this.motionY = y / f * velocity
        this.motionZ = z / f * velocity
        val f1 = sqrt(x * x + z * z)
        this.rotationYaw = (atan2(x, z) * (180.0 / Math.PI)).toFloat()
        this.rotationPitch = (atan2(y, f1) * (180.0 / Math.PI)).toFloat()
        this.prevRotationYaw = this.rotationYaw
        this.prevRotationPitch = this.rotationPitch
    }

    override fun onUpdate() {
        super.onUpdate()

        if (world.isRemote)
            for (i in 1..10)
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + rand.nextDouble()/2, posY+0.5+rand.nextDouble()/2, posZ + rand.nextDouble()/2, 0.0, 0.0, 0.0)
        else {
            posX += motionX
            posY += motionY
            posZ += motionZ
        }

        if (timer % 2 == 0) {
            val currentRadius = RADIUS - (timer.toDouble() / DURATION) * RADIUS + 2
            world.loadedEntityList
                    .filter { it != this && sqrt(sqr(posX - it.posX) + sqr(posY - it.posY) + sqr(posZ - it.posZ)) < currentRadius }
                    .filter { it != entity }
                    .forEach {
                        if (!world.isRemote) {
                            val dirVec = Vec3d(posX - it.posX, posY - it.posY, posZ - it.posZ).normalize()
                            it.addVelocity(dirVec.x, dirVec.y, dirVec.z)
                            it.velocityChanged = true
                            if (position.distance(it.position) < 2) {
                                if (it is EntityLivingBase)
                                    it.attackEntityFrom(DamageSource.WITHER, 40f)
                                else
                                    it.setDead()
                            }
                        } else {
                            for (i in 1..4)
                                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, it.posX + world.rand.nextDouble(), it.posY + world.rand.nextDouble()*it.height, it.posZ + world.rand.nextDouble(), 0.0, 0.0, 0.0, 1)
                        }
                    }
            /*if (timer % 4 == 0) {
                val blocks = BlockPos.getAllInBoxMutable(
                        BlockPos(position.x - currentRadius, position.y - currentRadius, position.z - currentRadius),
                        BlockPos(position.x + currentRadius, position.y + currentRadius, position.z + currentRadius)
                )
                blocks
                        .filter { it.distance(position) < currentRadius }
                        .map { it to world.getBlockState(it) }
                        .forEach {
                            if (!world.isRemote) {
                                val entityBlock = EntityBlock(world, it.first, it.second)
                                val dirVec = Vec3d(posX - it.first.x, posY - it.first.y, posZ - it.first.z).normalize().scale(4.0)
                                entityBlock.addVelocity(dirVec.x, dirVec.y, dirVec.z)
                                entityBlock.velocityChanged = true
                                world.spawnEntity(entityBlock)
                            }
                            world.setBlockToAir(it.first)
                        }
            }*/
        }
        if (!world.isRemote) {
            timer--
            if (timer <= 0)
                setDead()
        }
    }

    override fun writeEntityToNBT(compound: NBTTagCompound) {
        compound.setDouble("posX", posX)
        compound.setDouble("posY", posY)
        compound.setDouble("posZ", posZ)
        compound.setDouble("velX", motionX)
        compound.setDouble("velY", motionY)
        compound.setDouble("velZ", motionZ)
        compound.setInteger("timer", timer)
    }

    override fun readEntityFromNBT(compound: NBTTagCompound) {
        posX = compound.getDouble("posX")
        posY = compound.getDouble("posY")
        posZ = compound.getDouble("posZ")
        motionX = compound.getDouble("velX")
        motionY = compound.getDouble("velY")
        motionZ = compound.getDouble("velZ")
        timer = compound.getInteger("timer")
    }

    override fun entityInit() {}

    override fun isInvisibleToPlayer(player: EntityPlayer): Boolean = true

    private fun sqr(x: Double): Double = x * x

    companion object {
        const val RADIUS = 6
        const val DURATION = 90
    }
}