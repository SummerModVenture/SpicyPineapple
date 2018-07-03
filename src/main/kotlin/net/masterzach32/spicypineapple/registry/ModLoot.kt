package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.LOGGER
import net.masterzach32.spicypineapple.MOD_ID
import net.minecraft.util.ResourceLocation
import net.minecraft.world.storage.loot.LootTableList
import net.minecraftforge.event.LootTableLoadEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object ModLoot {

    val shrineLoot = LootTableList.register(ResourceLocation(MOD_ID, "shrine_loot"))

    @JvmStatic
    @SubscribeEvent
    fun registerLootTables(event: LootTableLoadEvent) {

    }

    fun init() {
        LOGGER.info("Loading loot tables.")
    }
}