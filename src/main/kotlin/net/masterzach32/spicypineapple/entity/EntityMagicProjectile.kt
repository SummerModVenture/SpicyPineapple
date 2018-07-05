package net.masterzach32.spicypineapple.entity

import net.minecraft.entity.Entity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import kotlin.math.*

abstract class EntityMagicProjectile(world: World, val duration: Int) : Entity(world) {

    var timer = duration

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
}