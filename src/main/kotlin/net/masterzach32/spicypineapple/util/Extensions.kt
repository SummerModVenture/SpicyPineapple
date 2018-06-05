package net.masterzach32.spicypineapple.util

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.relauncher.Side

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

fun BlockPos.distance(other: BlockPos): Double {
    return Math.sqrt(Math.pow(x-other.x.toDouble(), 2.0) + Math.pow(y-other.y.toDouble(), 2.0) + Math.pow(z-other.z.toDouble(), 2.0))
}

inline fun serverOnly(action: () -> Unit) {
    if (FMLCommonHandler.instance().effectiveSide == Side.SERVER)
        action()
}

inline fun clientOnly(action: () -> Unit) {
    if (FMLCommonHandler.instance().effectiveSide == Side.CLIENT)
        action()
}

inline fun ifModLoaded(modid: String, action: () -> Unit) {
    if (Loader.isModLoaded(modid))
        action()
}