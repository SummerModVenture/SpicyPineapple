package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.entity.item.EntityFallingBlock
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World
import net.minecraft.world.WorldServer

/*
 * SpicyPineapple - Created on 6/7/2018
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 6/7/2018
 */
class ItemEarthStaff : Item() {

    init {
        creativeTab = SpicyPineappleTab
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        val trace = player.rayTrace(10.0, 0f)
        if (!world.isRemote && trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK) {
            val lookingAt = trace.blockPos

            player.health = 2f
            player.foodStats.foodLevel = 5

            val blocks = mutableSetOf<BlockPos>()

            getAdjacentBlocks(lookingAt, 4, blocks)

            val states = blocks.map { it to world.getBlockState(it) }

            for (pair in states) {
                val pos = pair.first
                val state = pair.second
                world.setBlockToAir(pos)

                val entity = EntityFallingBlock(world, pos.x.toDouble()+0.5, pos.y.toDouble(), pos.z.toDouble()+0.5, state)
                entity.fallTime = 1 // falling block entity will despawn if it cant find its source at fallTime = 0
                entity.motionY = world.rand.nextDouble()*2
                world.spawnEntity(entity)
            }
        }

        return super.onItemRightClick(world, player, handIn)
    }

    private fun getAdjacentBlocks(pos: BlockPos, range: Int, blocks: MutableSet<BlockPos>) {
        if (range > 0) {
            blocks.add(pos)
            EnumFacing.VALUES.map { pos.offset(it) }.forEach { getAdjacentBlocks(it, range - 1, blocks) }
        }
    }
}