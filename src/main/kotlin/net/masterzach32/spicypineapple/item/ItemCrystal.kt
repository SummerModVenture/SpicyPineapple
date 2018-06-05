package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.EnumCrystalType
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList

class ItemCrystal : Item() {

    init {
        creativeTab = SpicyPineappleTab
        hasSubtypes = true
    }

    override fun getSubItems(tab: CreativeTabs, items: NonNullList<ItemStack>) {
        if (isInCreativeTab(tab))
            items.addAll(EnumCrystalType.values().map { ItemStack(this, 1, it.ordinal) })
    }

    override fun getUnlocalizedName(stack: ItemStack): String = "item.${EnumCrystalType.getTypeFromItem(stack).getName()}"
}