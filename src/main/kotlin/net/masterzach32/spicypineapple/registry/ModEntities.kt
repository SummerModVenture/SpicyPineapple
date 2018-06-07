package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.entity.EntityHealArea
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.EntityRegistry

object ModEntities {

    fun init() {
        EntityRegistry.registerModEntity(ResourceLocation("${SpicyPineappleMod.MOD_ID}:healer"), EntityHealArea::class.java, "healer", 0, this, 20, 1, false)
    }
}