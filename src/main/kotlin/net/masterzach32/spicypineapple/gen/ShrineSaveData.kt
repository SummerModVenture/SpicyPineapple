package net.masterzach32.spicypineapple.gen

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.network.ShrineLocUpdateMessage
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.storage.WorldSavedData

/*
 * SpicyPineapple - Created on 5/31/2018
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 5/31/2018
 */
class ShrineSaveData(name: String = ID) : WorldSavedData(ID) {
    companion object {
        const val ID = "PineappleShrineLoc"

        fun getForWorld(world: World): ShrineSaveData {
            var shrineData: WorldSavedData? = world.perWorldStorage.getOrLoadData(ShrineSaveData::class.java, ShrineSaveData.ID)
            if (shrineData == null) {
                shrineData = ShrineSaveData()
                world.perWorldStorage.setData(ShrineSaveData.ID, shrineData)
            }
            return shrineData as ShrineSaveData
        }
    }

    val map = mutableSetOf<BlockPos>()

    fun addShrineLocation(pos: BlockPos) {
        map.add(pos)
        markDirty()
        SpicyPineappleMod.NETWORK.sendToAll(ShrineLocUpdateMessage(ShrineLocUpdateMessage.Action.ADD, pos))
    }

    fun removeShrineLocation(pos: BlockPos) {
        map.remove(pos)
        markDirty()
        SpicyPineappleMod.NETWORK.sendToAll(ShrineLocUpdateMessage(ShrineLocUpdateMessage.Action.REMOVE, pos))
    }

    override fun writeToNBT(nbt: NBTTagCompound): NBTTagCompound {
        nbt.setInteger("count", map.size)
        map.forEachIndexed { i, pos ->
            nbt.setLong("$i", pos.toLong())
        }
        return nbt
    }

    override fun readFromNBT(nbt: NBTTagCompound) {
        map.clear()
        if (nbt.hasKey("count")) {
            for (i in 0..nbt.getInteger("count")) {
                map.add(BlockPos.fromLong(nbt.getLong("$i")))
            }
        }
    }
}