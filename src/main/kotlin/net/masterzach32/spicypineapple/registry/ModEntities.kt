package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.MOD_ID
import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.entity.EntityBlackHole
import net.masterzach32.spicypineapple.entity.EntityHealArea
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.registry.EntityRegistry

object ModEntities {

    @Mod.Instance(MOD_ID)
    lateinit var instance: SpicyPineappleMod

    private var currentId = 0

    fun init() {
        EntityRegistry.registerModEntity(ResourceLocation("$MOD_ID:healer"), EntityHealArea::class.java, "healer", getNextId(), instance, 20, 1, false)
        EntityRegistry.registerModEntity(ResourceLocation("$MOD_ID:black_hole"), EntityBlackHole::class.java, "black_hole", getNextId(), instance, 20, 1, false)
    }

    private fun getNextId(): Int {
        currentId++
        return currentId
    }
}