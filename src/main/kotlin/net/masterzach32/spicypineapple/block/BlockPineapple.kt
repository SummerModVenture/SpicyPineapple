package net.masterzach32.spicypineapple.block

import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import java.util.*

class BlockPineapple(private val itemDropped: Item, private val countDropped: Int) : Block(Material.GOURD) {

    init {
        setCreativeTab(SpicyPineappleTab)
    }

    @SuppressWarnings("Deprecated")
    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos): AxisAlignedBB {
        return AxisAlignedBB(4/16.0, 0.0, 4/16.0, 12/16.0, 10/16.0, 12.0/16)
    }

    @SuppressWarnings("Deprecated")
    override fun isOpaqueCube(state: IBlockState): Boolean {
        return false
    }

    override fun getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item {
        return itemDropped
    }

    override fun quantityDropped(state: IBlockState, fortune: Int, random: Random): Int {
        return countDropped
    }
}