package net.masterzach32.spicypineapple.client

import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess

object BlockColorHandler : IBlockColor {

    override fun colorMultiplier(state: IBlockState, worldIn: IBlockAccess?, pos: BlockPos?, tintIndex: Int): Int {
        return 0x97de55
    }
}