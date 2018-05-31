package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList

class ItemCrystal : Item() {

    companion object {
        const val LIFE = 0
        const val ENERGY = 1
        const val FIRE = 2
        const val EARTH = 3
        const val WATER = 4

        const val COUNT = 5
    }

    init {
        creativeTab = SpicyPineappleTab
        hasSubtypes = true
    }

    override fun getSubItems(tab: CreativeTabs, items: NonNullList<ItemStack>) {
        if (isInCreativeTab(tab)) {
            for (i in 0..4) {
                val stack = ItemStack(this, 1, i)
                items.add(stack)
            }
        }
    }

    override fun getUnlocalizedName(stack: ItemStack): String {
        return "item." + when(stack.metadata) {
            LIFE -> "life_crystal"
            ENERGY -> "energy_crystal"
            FIRE -> "fire_crystal"
            EARTH -> "earth_crystal"
            WATER -> "water_crystal"
            else -> "unknown_crystal"
        }
    }
}