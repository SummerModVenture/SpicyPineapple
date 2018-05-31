package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.registry.ModBlocks
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.init.Blocks
import net.minecraft.item.ItemSeeds

/*
 * SpicyPineapple - Created on 5/31/2018
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 5/31/2018
 */
class ItemPineappleSeed : ItemSeeds(ModBlocks.pineappleBlock, Blocks.FARMLAND) {

    init {
        creativeTab = SpicyPineappleTab
    }
}