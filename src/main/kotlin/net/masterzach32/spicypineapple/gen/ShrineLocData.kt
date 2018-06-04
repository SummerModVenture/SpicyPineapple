package net.masterzach32.spicypineapple.gen

import net.masterzach32.spicypineapple.util.distance
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
class ShrineLocData(name: String = ID) : WorldSavedData(ID) {
    companion object {
        const val ID = "PineappleShrineLoc"

        fun getForWorld(world: World): ShrineLocData {
            var shrineData: WorldSavedData? = world.perWorldStorage.getOrLoadData(ShrineLocData::class.java, ShrineLocData.ID)
            if (shrineData == null) {
                shrineData = ShrineLocData()
                world.perWorldStorage.setData(ShrineLocData.ID, shrineData)
            }
            return shrineData as ShrineLocData
        }
    }

    val map = mutableListOf<BlockPos>()

    fun getClosestShrine(pos: BlockPos): BlockPos? {
        var min = Double.MAX_VALUE
        var index = -1
        for (s in map.indices) {
            val dist = pos.distance(map[s])
            if (dist < min) {
                min = dist
                index = s
            }
        }
        return if (index == -1) null else map[index]
    }

    fun addShrineLocation(pos: BlockPos) {
        map.add(pos)
        markDirty()
    }

    fun removeShrineLocation(pos: BlockPos) {
        map.remove(pos)
        markDirty()
    }

    override fun writeToNBT(nbt: NBTTagCompound): NBTTagCompound {
        nbt.setInteger("count", map.size)
        map.filter { it != BlockPos(0, 0, 0) }.forEachIndexed { i, pos ->
            nbt.setLong("$i", pos.toLong())
        }
        return nbt
    }

    override fun readFromNBT(nbt: NBTTagCompound) {
        map.clear()
        if (nbt.hasKey("count")) {
            for (i in 0..nbt.getInteger("count")) {
                val pos = BlockPos.fromLong(nbt.getLong("$i"))
                if (pos != BlockPos(0, 0, 0))
                    map.add(pos)
            }
        }
    }
}