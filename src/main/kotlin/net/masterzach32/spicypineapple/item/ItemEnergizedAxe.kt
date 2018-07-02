package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.masterzach32.spicypineapple.util.*
import net.minecraft.block.Block
import net.minecraft.block.BlockLeaves
import net.minecraft.block.BlockLog
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemAxe
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ItemEnergizedAxe : ItemAxe(ToolMaterialEnergized, ToolMaterialEnergized.attackDamage, -3.0f) {

    init {
        creativeTab = SpicyPineappleTab
    }

    override fun onBlockDestroyed(stack: ItemStack, world: World, state: IBlockState, pos: BlockPos, player: EntityLivingBase): Boolean {
        if (world.isRemote || player !is EntityPlayer || player.isSneaking)
            return super.onBlockDestroyed(stack, world, state, pos, player)

        if (world.getBlockState(pos).block is BlockLog) {
            val logs = mutableSetOf<BlockPos>()
            harvestAllWood(world.getBlock(pos), logs, player, stack)
            val leaves = mutableSetOf<BlockPos>()
            logs.forEach { harvestDecayableLeaves(world.getBlock(it), leaves, player, stack) }
        }

        return super.onBlockDestroyed(stack, world, state, pos, player)
    }

    private fun harvestAllWood(biw: BlockInWorld, broken: MutableSet<BlockPos>, player: EntityPlayer, stack: ItemStack) {
        biw.destroyBlock(player, stack)
        if (broken.size >= 100)
            return
        biw.pos.getBlocksWithin(1)
                .filter { it != biw.pos }
                .map { biw.world.getBlock(it) }
                .filter { it.block is BlockLog && broken.add(it.pos) }
                .forEach { harvestAllWood(it, broken, player, stack) }
    }

    private fun harvestDecayableLeaves(biw: BlockInWorld, broken: MutableSet<BlockPos>, player: EntityPlayer, stack: ItemStack) {
        if (broken.size > 200)
            return
        biw.pos.getBlocksWithinTopCone(4)
                .filter { it != biw.pos }
                .map { biw.world.getBlock(it) }
                .filter { it.block is BlockLeaves && it.state.getValue(BlockLeaves.CHECK_DECAY) && broken.add(it.pos) }
                .forEach { it.destroyBlock(player, stack) }
    }

    private fun BlockInWorld.destroyBlock(player: EntityPlayer, stack: ItemStack) {
        block.harvestBlock(world, player, pos, state, null, stack)
        world.setBlockToAir(pos)

        if (block is BlockLog)
            stack.damageItem(1, player)
        player.foodStats.addExhaustion(0.1f)
    }

    private fun BlockInWorld.isNearWood(): Boolean {
        return pos.getBlocksWithinMutable(3).any { world.getBlockState(it).block is BlockLog }
    }
}