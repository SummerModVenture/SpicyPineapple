package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.item.ItemHoe

/*
 * SpicyPineapple - Created on 6/5/2018
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 6/5/2018
 */
class ItemEnergizedHoe : ItemHoe(ToolMaterialEnergized) {

    init {
        creativeTab = SpicyPineappleTab
    }
}