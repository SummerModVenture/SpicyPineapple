package net.masterzach32.spicypineapple.entity

import com.spicymemes.core.util.distance
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumParticleTypes
import net.minecraft.world.World

class EntityHealArea(world: World) : Entity(world) {

    var timer: Int = DURATION
    var strength: Int = 1

    init {
        isInvisible = true
    }

    constructor(world: World, player: EntityPlayer, strength: Int) : this(world) {
        posX = player.posX
        posY = player.posY
        posZ = player.posZ
        this.strength = strength
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
        world.loadedEntityList
                .filter { it != this && it.position.distance(this.position) < RADIUS }
                .mapNotNull { it as? EntityLivingBase }
                .forEach {
                    if (!world.isRemote) {
                        it.heal(0.05f + 0.05f * strength)
                        if (it is EntityPlayer && timer % (HUNGER_PERIOD / strength) == 0)
                            it.foodStats.addStats(1, 0.4f)
                    } else {
                        for (i in 1..5)
                            world.spawnParticle(EnumParticleTypes.REDSTONE, it.posX + world.rand.nextDouble(), it.posY + world.rand.nextDouble()*it.height, it.posZ + world.rand.nextDouble(), 0.0, 0.0, 0.0, 1)
                    }
                }
        if (world.isRemote) {
            for (i in 1..(2*3.14*strength*RADIUS).toInt()) {
                // generate random point using spherical coordinates
                val theta = Math.random()*2*3.14
                val phi = Math.sqrt(Math.random())*3.14-3.14/2
                val r = RADIUS
                world.spawnParticle(EnumParticleTypes.REDSTONE, posX + r*Math.cos(theta)*Math.cos(phi), posY + r*Math.sin(phi), posZ + r*Math.sin(theta)*Math.cos(phi), 0.0, 0.0, 0.0, 1)
            }
        } else {
            timer--
            if (timer <= 0)
                setDead()
        }
    }

    companion object {
        const val RADIUS = 6
        const val DURATION = 150
        const val HUNGER_PERIOD = 150/10
    }
}