package net.masterzach32.spicypineapple

import com.spicymemes.core.util.clientOnly
import net.masterzach32.spicypineapple.client.ColorHandler
import net.masterzach32.spicypineapple.gen.ClusterGenerator
import net.masterzach32.spicypineapple.gen.StructureGenerator
import net.masterzach32.spicypineapple.network.ClientShrineLocHandler
import net.masterzach32.spicypineapple.network.ShrineLocUpdateMessage
import net.masterzach32.spicypineapple.network.StaffActivatedPacket
import net.masterzach32.spicypineapple.registry.*
import net.masterzach32.spicypineapple.server.ShrineLocHandler
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side

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
        modLanguageAdapter = "com.spicymemes.core.KotlinAdapter")
object SpicyPineappleMod {

    val network: SimpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID)

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        logger = event.modLog
        logger.info("Creating some spicy pineapples.")

        ModItems.init()
        ModBlocks.init()
        ModTileEntities.init()
        ModEntities.init()
        ModLoot.init()

        logger.info("Registering world generators.")
        GameRegistry.registerWorldGenerator(ClusterGenerator(ModBlocks.pineappleBlock, 1/10.0), 0)
        GameRegistry.registerWorldGenerator(ClusterGenerator(ModBlocks.pineappleBlockSpicy, 1/40.0), 0)
        GameRegistry.registerWorldGenerator(StructureGenerator, 0)
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        ModBlocks.lateInit()

        ModRecipes.init()

        clientOnly {
            logger.info("Registering color handlers.")
            Minecraft.getMinecraft().itemColors.registerItemColorHandler(ColorHandler, ModItems.crystal)
            Minecraft.getMinecraft().blockColors.registerBlockColorHandler(ColorHandler, ModBlocks.pineappleStem)
            Minecraft.getMinecraft().blockColors.registerBlockColorHandler(ColorHandler, ModBlocks.spicyPineappleStem)
            Minecraft.getMinecraft().blockColors.registerBlockColorHandler(ColorHandler, ModBlocks.crystalizedPineappleStem)
        }
        logger.info("Registering network packets.")
        network.registerMessage(ClientShrineLocHandler::class.java, ShrineLocUpdateMessage::class.java, 0, Side.CLIENT)
        network.registerMessage(ShrineLocHandler::class.java, ShrineLocUpdateMessage::class.java, 1, Side.SERVER)
        network.registerMessage(StaffActivatedPacket.Handler::class.java, StaffActivatedPacket::class.java, 2, Side.SERVER)
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        ModRecipes.initModRecipes()
    }
}
