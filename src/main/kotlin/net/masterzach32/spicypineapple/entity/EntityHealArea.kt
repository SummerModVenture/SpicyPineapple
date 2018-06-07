package net.masterzach32.spicypineapple.entity

import net.minecraft.entity.Entity
import net.minecraft.entity.IProjectile
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.ITickable
import net.minecraft.world.World

class EntityHealArea(world: World) : Entity(world) {

    var timer: Int = 100

    init {
        isInvisible = true
    }

    constructor(world: World, player: EntityPlayer) : this(world) {
        posX = player.posX
        posY = player.posY
        posZ = player.posZ
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
            world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, posX, posY, posZ, 0.0, 0.0, 0.0)
        } else {
            timer--
            if (timer <= 0)
                setDead()
        }
    }
}