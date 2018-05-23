package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.item.ItemFood

class ItemPineappleSlice(hungerFilled: Int, saturation: Float) : ItemFood(hungerFilled, saturation, false) {

    init {
        creativeTab = SpicyPineappleTab
    }
}