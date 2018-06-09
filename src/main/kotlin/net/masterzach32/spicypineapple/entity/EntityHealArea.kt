package net.masterzach32.spicypineapple.entity

import net.masterzach32.spicypineapple.util.distance
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.IProjectile
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.ITickable
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
        if (world.isRemote) {
            for (i in 1..2*strength*RADIUS) {
                // generate random point using spherical coordinates
                val theta = Math.random()*2*3.14
                val phi = Math.random()*3.14-3.14/2
                val r = Math.sqrt(Math.random()) * RADIUS
                world.spawnParticle(EnumParticleTypes.SPELL, posX + r*Math.cos(theta)*Math.cos(phi), posY + r*Math.sin(phi), posZ + r*Math.sin(theta)*Math.cos(phi), 0.0, 0.0, 0.0)
            }
        } else {
            world.loadedEntityList
                    .filter { it != this && it.position.distance(this.position) < RADIUS }
                    .mapNotNull { it as? EntityLivingBase }
                    .forEach {
                        it.heal(0.1f * strength)
                        if (it is EntityPlayer && timer % HUNGER_PERIOD == 0)
                            it.foodStats.addStats(1, 0.4f * strength)
                    }
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