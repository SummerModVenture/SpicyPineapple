package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IStringSerializable
import net.minecraft.util.NonNullList

class ItemCrystal : Item() {

    init {
        creativeTab = SpicyPineappleTab
        hasSubtypes = true
    }

    override fun getSubItems(tab: CreativeTabs, items: NonNullList<ItemStack>) {
        if (isInCreativeTab(tab))
            items.addAll(Type.values().map { ItemStack(this, 1, it.ordinal) })
    }

    override fun getUnlocalizedName(stack: ItemStack): String = "item.${Type.getTypeFromItem(stack).getName()}"

    enum class Type(val color: Int) : IStringSerializable {
        LIFE(0x68ff9a),
        ENERGY(0xd67fff),
        FIRE(0xff4800),
        EARTH(0x179e00),
        WATER(0x14a8ff);

        override fun getName(): String {
            return name.toLowerCase() + "_crystal"
        }

        companion object {
            fun getTypeFromItem(crystal: ItemStack): Type = values()[crystal.metadata]
        }
    }
}