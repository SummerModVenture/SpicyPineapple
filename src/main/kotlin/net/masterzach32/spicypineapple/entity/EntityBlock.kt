package net.masterzach32.spicypineapple.entity

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.crash.CrashReportCategory
import net.minecraft.entity.Entity
import net.minecraft.init.Blocks
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class EntityBlock(world: World) : Entity(world) {

    var state: IBlockState? = null
    private var tileEntityData: NBTTagCompound? = null
    var origin: BlockPos? = null

    var fallTime = 0

    constructor(world: World, pos: BlockPos, state: IBlockState) : this(world) {
        this.state = state
        setPosition(pos.x.toDouble(), pos.y.toDouble() + (1 - height)/2, pos.z.toDouble())
        motionX = 0.0
        motionY = 0.0
        motionZ = 0.0
        prevPosX = posX
        prevPosY = posY
        prevPosZ = posZ
        origin = pos
    }

    override fun onUpdate() {
        super.onUpdate()

        if (state != null) {
            if (state?.material == Material.AIR) {
                setDead()
            } else {
                prevPosX = posX
                prevPosY = posY
                prevPosZ = posZ

                fallTime++
            }
        }
    }

    override fun writeEntityToNBT(compound: NBTTagCompound) {
        val block = state?.block ?: Blocks.AIR
        val resourceLocation = Block.REGISTRY.getNameForObject(block)
        compound.setString("Block", resourceLocation.toString())
        compound.setByte("Data", block.getMetaFromState(state!!).toByte())
        compound.setInteger("Time", fallTime)
        compound.setLong("Origin", origin!!.toLong())

        if (tileEntityData != null)
            compound.setTag("TileEntityData", tileEntityData!!)
    }

    override fun readEntityFromNBT(compound: NBTTagCompound) {
        val i = compound.getByte("Data").toInt() and 255

        if (compound.hasKey("Block", 8))
            state = Block.getBlockFromName(compound.getString("Block"))?.getStateFromMeta(i)

        fallTime = compound.getInteger("Time")
        val block = state?.block

        if (compound.hasKey("TileEntityData", 10))
            tileEntityData = compound.getCompoundTag("TileEntityData")

        if (block == null || block.defaultState.material == Material.AIR)
            state = Blocks.SAND.defaultState

        origin = BlockPos.fromLong(compound.getLong("Origin"))
    }

    override fun addEntityCrashInfo(category: CrashReportCategory) {
        super.addEntityCrashInfo(category)

        if (state != null) {
            val block = state!!.block
            category.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(block)))
            category.addCrashSection("Immitating block data", Integer.valueOf(block.getMetaFromState(state!!)))
        }
    }

    override fun canBeAttackedWithItem(): Boolean = false

    override fun canTriggerWalking(): Boolean = false

    override fun entityInit() {}
}