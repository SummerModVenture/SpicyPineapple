package net.masterzach32.spicypineapple.dsl

import net.minecraft.block.Block
import net.minecraft.item.Item

/*
 * SpicyPineapple - Created on 5/23/2018
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 5/23/2018
 */

fun Item.setCodename(name: String): Item {
    return setUnlocalizedName(name).setRegistryName(name)
}

fun Block.setCodename(name: String): Block {
    return setUnlocalizedName(name).setRegistryName(name)
}