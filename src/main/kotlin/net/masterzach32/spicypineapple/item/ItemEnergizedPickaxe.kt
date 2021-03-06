package net.masterzach32.spicypineapple.item

import com.spicymemes.core.util.getBlocksWithin
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemPickaxe
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/*
 * SpicyPineapple - Created on 5/24/2018
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar1
 * @version 5/24/2018
 */
class ItemEnergizedPickaxe : ItemPickaxe(ToolMaterialEnergized) {

    init {
        creativeTab = SpicyPineappleTab
    }

    override fun onBlockDestroyed(stack: ItemStack, world: World, state: IBlockState, pos: BlockPos, entity: EntityLivingBase): Boolean {
        if (world.isRemote || entity !is EntityPlayer || entity.isSneaking)
            return super.onBlockDestroyed(stack, world, state, pos, entity)
        val blocks = pos.getBlocksWithin(1)
        blocks.forEach {
            val otherState = world.getBlockState(it)
            @Suppress("DEPRECATION")
            if (it != pos && otherState.block.getBlockHardness(otherState, world, it) != -1.0F) {
                otherState.block.harvestBlock(world, entity, it, otherState, null, stack)
                world.setBlockToAir(it)
            }
        }
        stack.damageItem(4, entity)
        entity.foodStats.addExhaustion(4.0f * entity.foodStats.foodLevel/20)
        return super.onBlockDestroyed(stack, world, state, pos, entity)
    }
}