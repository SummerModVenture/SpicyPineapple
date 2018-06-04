package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.EnumPineappleType
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList

/*
 * SpicyPineapple - Created on 6/4/2018
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 6/4/2018
 */
class ItemEssence : Item() {

    init {
        creativeTab = SpicyPineappleTab
        hasSubtypes = true
    }

    override fun getSubItems(tab: CreativeTabs, items: NonNullList<ItemStack>) {
        if (isInCreativeTab(tab))
            items.addAll(EnumPineappleType.values().map { ItemStack(this, 1, it.ordinal) })
    }

    override fun getUnlocalizedName(stack: ItemStack): String = "item.${EnumPineappleType.values()[stack.metadata].getName()}_essence"
}