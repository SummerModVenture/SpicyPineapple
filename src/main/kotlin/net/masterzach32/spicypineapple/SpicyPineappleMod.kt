package net.masterzach32.spicypineapple

import net.masterzach32.spicypineapple.SpicyPineappleMod.MOD_ID
import net.masterzach32.spicypineapple.SpicyPineappleMod.MOD_NAME
import net.masterzach32.spicypineapple.SpicyPineappleMod.MOD_VERSION
import net.masterzach32.spicypineapple.block.BlockSpicyPineapple
import net.masterzach32.spicypineapple.item.ItemSpicyPineappleSlice
import net.masterzach32.spicypineapple.registry.ModBlocks
import net.masterzach32.spicypineapple.registry.ModItems
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.apache.logging.log4j.Logger

/*
 * SpicyPineappleMod - Created on 5/22/2018
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 5/22/2018
 */
@Mod(modid = MOD_ID, name = MOD_NAME, version = MOD_VERSION, modLanguage = "kotlin",
        modLanguageAdapter = "net.masterzach32.spicypineapple.util.KotlinAdapter")
object SpicyPineappleMod {

    const val MOD_ID = "spicypineapple"
    const val MOD_NAME = "Spicy Pineapple Mod"
    const val MOD_VERSION = "3.0.0"

    lateinit var logger: Logger

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        logger = event.modLog
        logger.info("Creating some spicy pineapples.")
        ModBlocks.init()
        ModItems.init()
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {

    }

    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        event.registry.registerAll(BlockSpicyPineapple)
    }

    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        // item blocks
        event.registry.registerAll(ItemBlock(BlockSpicyPineapple).setRegistryName(BlockSpicyPineapple.unlocalizedName))

        // items
        event.registry.registerAll(ItemSpicyPineappleSlice)
    }
}