package net.masterzach32.spicypineapple.util

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.relauncher.Side
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/*
 * SpicyPineapple - Created on 5/23/2018
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 5/23/2018
 */

fun Item.setCodename(name: String): Item {
    return setUnlocalizedName(name).setRegistryName(name)
}

fun Block.setCodename(name: String): Block {
    return setUnlocalizedName(name).setRegistryName(name)
}

fun BlockPos.distance(other: BlockPos): Double {
    return sqrt((x-other.x.toDouble()).pow(2) + (y-other.y.toDouble()).pow(2) + (z-other.z.toDouble()).pow(2))
}

fun BlockPos.horSquaredDistance(other: BlockPos): Int {
    return abs(x - other.x) + abs(z - other.z)
}

fun BlockPos.getBlocksWithin(range: Int): Iterable<BlockPos> {
    return BlockPos.getAllInBox(x - range, y - range, z - range, x + range, y + range, z + range)
}

fun BlockPos.getBlocksWithinMutable(range: Int): Iterable<BlockPos.MutableBlockPos> {
    return BlockPos.getAllInBoxMutable(x - range, y - range, z - range, x + range, y + range, z + range)
}

fun BlockPos.getBlocksWithinHorizontal(range: Int): Iterable<BlockPos> {
    return BlockPos.getAllInBox(x - range, y, z - range, x + range, y, z + range)
}

fun BlockPos.getBlocksWithinTopCone(range: Int): Iterable<BlockPos> {
    return BlockPos.getAllInBox(x - range, y, z - range, x + range, y + range, z + range)
}

fun World.getBlock(pos: BlockPos) = BlockInWorld(this, pos)

inline fun serverOnly(action: () -> Unit) {
    if (FMLCommonHandler.instance().effectiveSide == Side.SERVER)
        action()
}

inline fun clientOnly(action: () -> Unit) {
    if (FMLCommonHandler.instance().effectiveSide == Side.CLIENT)
        action()
}

inline fun ifModLoaded(modid: String, action: () -> Unit) {
    if (Loader.isModLoaded(modid))
        action()
}