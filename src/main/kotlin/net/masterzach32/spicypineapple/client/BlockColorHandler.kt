package net.masterzach32.spicypineapple.client

import net.masterzach32.spicypineapple.registry.ModBlocks
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess

object BlockColorHandler : IBlockColor {

    override fun colorMultiplier(state: IBlockState, worldIn: IBlockAccess?, pos: BlockPos?, tintIndex: Int): Int {
        return when (state.block) {
            ModBlocks.pineappleStem -> 0x97de55
            ModBlocks.spicyPineappleStem -> 0xc74500
            ModBlocks.crystalizedPineappleStem -> 0x9271ff
            else -> 0xffffff
        }
    }
}