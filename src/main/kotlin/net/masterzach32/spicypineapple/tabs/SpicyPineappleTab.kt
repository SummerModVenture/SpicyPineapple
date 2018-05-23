package net.masterzach32.spicypineapple.tabs

import net.masterzach32.spicypineapple.registry.ModItems
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

object SpicyPineappleTab : CreativeTabs("spicy_pineapple_stuff") {

    override fun getTabIconItem(): ItemStack {
        return ItemStack(ModItems.pineappleSlice, 1, 0)
    }
}