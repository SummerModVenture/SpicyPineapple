package net.masterzach32.spicypineapple.client

import net.masterzach32.spicypineapple.EnumCrystalType
import net.masterzach32.spicypineapple.EnumPineappleType
import net.masterzach32.spicypineapple.item.ItemCrystal
import net.masterzach32.spicypineapple.registry.ModBlocks
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess

object ColorHandler : IBlockColor, IItemColor {

    override fun colorMultiplier(state: IBlockState, worldIn: IBlockAccess?, pos: BlockPos?, tintIndex: Int): Int {
        return when (state.block) {
            ModBlocks.pineappleStem -> 0x97de55
            ModBlocks.spicyPineappleStem -> 0xc74500
            ModBlocks.crystalizedPineappleStem -> 0x9271ff
            else -> 0xffffff
        }
    }

    override fun colorMultiplier(stack: ItemStack, tintIndex: Int): Int {
        return EnumCrystalType.getTypeFromItem(stack).color
    }
}