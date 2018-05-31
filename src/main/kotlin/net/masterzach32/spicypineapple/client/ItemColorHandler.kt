package net.masterzach32.spicypineapple.client

import net.masterzach32.spicypineapple.item.ItemCrystal
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.item.ItemStack

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
object ItemColorHandler : IItemColor {

    override fun colorMultiplier(stack: ItemStack, tintIndex: Int): Int {
        return when (stack.metadata) {
            ItemCrystal.LIFE -> 0x68ff9a
            ItemCrystal.ENERGY -> 0xd67fff
            ItemCrystal.FIRE -> 0xff4800
            ItemCrystal.EARTH -> 0x179e00
            ItemCrystal.WATER -> 0x14a8ff
            else -> 0xffffff
        }
    }
}