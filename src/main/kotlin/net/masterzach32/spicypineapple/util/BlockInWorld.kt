package net.masterzach32.spicypineapple.util

import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/*
 * SpicyPineapple - Created on 7/2/2018
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 7/2/2018
 */
data class BlockInWorld(val world: World, val pos: BlockPos) {

    val state: IBlockState = world.getBlockState(pos)
    val block: Block = state.block
}