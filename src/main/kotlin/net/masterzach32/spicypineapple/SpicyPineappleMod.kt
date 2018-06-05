package net.masterzach32.spicypineapple

import com.spicymemes.core.recipe.RecipesRegistryManager
import net.masterzach32.spicypineapple.SpicyPineappleMod.MOD_ID
import net.masterzach32.spicypineapple.SpicyPineappleMod.MOD_NAME
import net.masterzach32.spicypineapple.SpicyPineappleMod.MOD_VERSION
import net.masterzach32.spicypineapple.client.ColorHandler
import net.masterzach32.spicypineapple.util.clientOnly
import net.masterzach32.spicypineapple.util.ifModLoaded
import net.masterzach32.spicypineapple.util.serverOnly
import net.masterzach32.spicypineapple.gen.ClusterGenerator
import net.masterzach32.spicypineapple.gen.PineappleShrineGenerator
import net.masterzach32.spicypineapple.network.ShrineLocClientHandler
import net.masterzach32.spicypineapple.network.ShrineLocServerHandler
import net.masterzach32.spicypineapple.network.ShrineLocUpdateMessage
import net.masterzach32.spicypineapple.registry.ModBlocks
import net.masterzach32.spicypineapple.registry.ModItems
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
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
    const val MOD_VERSION = "1.0.0"

    lateinit var logger: Logger

    val NETWORK: SimpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID)

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        logger = event.modLog
        logger.info("Creating some spicy pineapples.")

        ModItems.init()
        ModBlocks.init()

        logger.info("Registering world generators.")
        GameRegistry.registerWorldGenerator(ClusterGenerator(ModBlocks.pineappleBlock, 1/10.0), 0)
        GameRegistry.registerWorldGenerator(ClusterGenerator(ModBlocks.pineappleBlockSpicy, 1/40.0), 0)
        GameRegistry.registerWorldGenerator(PineappleShrineGenerator, 0)
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        ModBlocks.lateInit()

        logger.info("Registering recipes.")
        GameRegistry.addSmelting(ModItems.pineappleSlice, ItemStack(ModItems.grilledPineappleSlice), 1.0F)

        clientOnly {
            logger.info("Registering color handlers.")
            Minecraft.getMinecraft().itemColors.registerItemColorHandler(ColorHandler, ModItems.crystal)
            Minecraft.getMinecraft().blockColors.registerBlockColorHandler(ColorHandler, ModBlocks.pineappleStem)
            Minecraft.getMinecraft().blockColors.registerBlockColorHandler(ColorHandler, ModBlocks.spicyPineappleStem)
            Minecraft.getMinecraft().blockColors.registerBlockColorHandler(ColorHandler, ModBlocks.crystalizedPineappleStem)
            logger.info("Registering client network handler.")
            NETWORK.registerMessage(ShrineLocClientHandler::class.java, ShrineLocUpdateMessage::class.java, 0, Side.CLIENT)
        }
        serverOnly {
            logger.info("Registering server network handler.")
            NETWORK.registerMessage(ShrineLocServerHandler::class.java, ShrineLocUpdateMessage::class.java, 1, Side.SERVER)
        }

        ifModLoaded("spicytech") {
            logger.info("Registering crusher recipes.")
            val reg = RecipesRegistryManager.getRegistry("spicytech", "crusher")
            if (reg != null) {
                reg.apply {
                    add(ModItems.pineappleSlice, ItemStack(ModItems.essence, 2, EnumPineappleType.NORMAL.ordinal), 1.0f)
                    add(ModItems.spicyPineappleSlice, ItemStack(ModItems.essence, 2, EnumPineappleType.SPICY.ordinal), 1.0f)
                    add(ModItems.crystalPineappleSlice, ItemStack(ModItems.essence, 2, EnumPineappleType.CRYSTALIZED.ordinal), 2.0f)
                }
            } else {
                logger.warn("Could not load SpicyTech crusher registry.")
            }
        }
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {

    }
}