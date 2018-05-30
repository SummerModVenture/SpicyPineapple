package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.block.BlockJukebox
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.init.SoundEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemFood
import net.minecraft.item.ItemStack
import net.minecraft.stats.StatList
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/*
 * SpicyPineapple - Created on 5/29/2018
 * Author: Zach Kozar
 *
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 5/29/2018
 */
class ItemPineappleSlice(hungerFilled: Int, saturation: Double) : ItemFood(hungerFilled, saturation.toFloat(), false) {

    val sound: SoundEvent

    init {
        creativeTab = SpicyPineappleTab

        sound = SoundEvents.RECORD_13
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand,
                           facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val blockState = world.getBlockState(pos)

        if (blockState.block == Blocks.JUKEBOX && !blockState.getValue(BlockJukebox.HAS_RECORD)) {
            if (!world.isRemote) {
                val stack = player.getHeldItem(hand)
                (Blocks.JUKEBOX as BlockJukebox).insertRecord(world, pos, blockState, ItemStack(stack.item))
                world.playEvent(null, 1010, pos, Item.getIdFromItem(this))
                stack.shrink(1)
                player.addStat(StatList.RECORD_PLAYED)
            }
            return EnumActionResult.SUCCESS
        }
        return EnumActionResult.PASS
    }

}