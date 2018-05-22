package net.masterzach32.spicypineapple.block

import net.masterzach32.spicypineapple.item.ItemSpicyPineappleSlice
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import java.util.*

object BlockSpicyPineapple : Block(Material.GOURD) {

    init {
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS)
        unlocalizedName = "spicypineapple"
    }

    override fun getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item {
        return ItemSpicyPineappleSlice
    }

    override fun quantityDropped(state: IBlockState, fortune: Int, random: Random): Int {
        return 4
    }
}