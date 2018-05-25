package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemAxe
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ItemEnergizedAxe : ItemAxe(ToolMaterialEnergized, ToolMaterialEnergized.attackDamage, -3.0f) {

    init {
        creativeTab = SpicyPineappleTab
    }

    override fun onBlockDestroyed(stack: ItemStack, world: World, state: IBlockState, pos: BlockPos, entity: EntityLivingBase): Boolean {
        if (world.isRemote || entity !is EntityPlayer || !entity.isSneaking)
            return super.onBlockDestroyed(stack, world, state, pos, entity)

        if (world.getBlockState(pos).block == Blocks.LOG || world.getBlockState(pos).block == Blocks.LOG2)
            harvestAllWood(world, pos, mutableSetOf(), entity, stack)

        return super.onBlockDestroyed(stack, world, state, pos, entity)
    }

    private fun harvestAllWood(world: World, pos: BlockPos, broken: MutableSet<BlockPos>, player: EntityPlayer, stack: ItemStack) {
        val block = world.getBlockState(pos).block
        destroyBlock(world, block, pos, player, stack)
        if (broken.size >= 400)
            return
        EnumFacing.VALUES
                .filter {
                    val other = world.getBlockState(pos.offset(it)).block
                    other == Blocks.LOG || other == Blocks.LOG2 || other == Blocks.LEAVES || other == Blocks.LEAVES2
                }
                .filter { broken.add(pos.offset(it)) }
                .forEach { harvestAllWood(world, pos.offset(it), broken, player, stack) }
    }

    private fun destroyBlock(world: World, block: Block, pos: BlockPos, player: EntityPlayer, stack: ItemStack) {
        block.harvestBlock(world, player, pos, world.getBlockState(pos), null, stack)
        world.setBlockToAir(pos)
        if (block == Blocks.LOG || block == Blocks.LOG2)
            stack.damageItem(1, player)
        player.foodStats.addExhaustion(0.1f)
    }
}