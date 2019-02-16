package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.masterzach32.spicypineapple.util.getBlocksWithin
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemSpade
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ItemEnergizedShovel : ItemSpade(ToolMaterialEnergized) {

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
            if (it != pos && otherState.block.isToolEffective("shovel", otherState)) {
                otherState.block.harvestBlock(world, entity, it, otherState, null, stack)
                world.setBlockToAir(it)
            }
        }
        stack.damageItem(4, entity)
        entity.foodStats.addExhaustion(4.0f * entity.foodStats.foodLevel/20)
        return super.onBlockDestroyed(stack, world, state, pos, entity)
    }
}