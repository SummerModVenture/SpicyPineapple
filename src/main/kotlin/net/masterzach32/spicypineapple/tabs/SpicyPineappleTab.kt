package net.masterzach32.spicypineapple.tabs

import net.masterzach32.spicypineapple.registry.ModBlocks
import net.masterzach32.spicypineapple.registry.ModItems
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack

object SpicyPineappleTab : CreativeTabs("spicy_pineapple_stuff") {

    override fun getTabIconItem(): ItemStack {
        return ItemStack(ModBlocks.pineappleBlockItem)
    }
}