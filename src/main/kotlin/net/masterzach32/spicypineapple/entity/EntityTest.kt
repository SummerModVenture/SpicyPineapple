package net.masterzach32.spicypineapple.entity

import net.minecraft.entity.Entity
import net.minecraft.entity.IProjectile
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

class EntityTest(world: World, val player: EntityPlayer) : Entity(world), IProjectile {

    init {
        setPosition(player.posX, player.posY, player.posZ)
    }

    override fun shoot(x: Double, y: Double, z: Double, velocity: Float, inaccuracy: Float) {

    }

    override fun writeEntityToNBT(compound: NBTTagCompound) {
        super.writeToNBT(compound)
    }

    override fun readEntityFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
    }

    override fun entityInit() {

    }
}