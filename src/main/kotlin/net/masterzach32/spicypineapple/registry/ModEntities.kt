package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.LOGGER
import net.masterzach32.spicypineapple.MOD_ID
import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.entity.EntityBlackHole
import net.masterzach32.spicypineapple.entity.EntityBlock
import net.masterzach32.spicypineapple.entity.EntityEarthRipper
import net.masterzach32.spicypineapple.entity.EntityHealArea
import net.masterzach32.spicypineapple.entity.render.RenderBlackHole
import net.masterzach32.spicypineapple.entity.render.RenderBlock
import net.masterzach32.spicypineapple.entity.render.RenderEarthRipper
import net.masterzach32.spicypineapple.entity.render.RenderHealArea
import net.masterzach32.spicypineapple.util.clientOnly
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.registry.EntityRegistry
import kotlin.reflect.KClass

object ModEntities {

    private var currentId = 0

    fun init() {
        LOGGER.info("Loading entities.")

        registerEntity(EntityHealArea::class, "healer", 20, 1, false) { RenderHealArea(it) }
        registerEntity(EntityBlackHole::class, "black_hole", 20, 1, true) { RenderBlackHole(it) }
        registerEntity(EntityBlock::class, "block", 20, 1, true) { RenderBlock(it) }
        registerEntity(EntityEarthRipper::class, "earth_ripper", 20, 1, true) { RenderEarthRipper(it) }
    }

    private fun <E : Entity, R : Render<E>> registerEntity(
            entityClass: KClass<E>,
            name: String,
            trackingRange: Int,
            updateFrequency: Int,
            sendVelocityData: Boolean,
            newRenderClass: (RenderManager) -> R
    ) {

        EntityRegistry.registerModEntity(
                ResourceLocation("$MOD_ID:$name"),
                entityClass.java,
                name,
                currentId++,
                SpicyPineappleMod,
                trackingRange,
                updateFrequency,
                sendVelocityData

        )
        clientOnly {
            RenderingRegistry.registerEntityRenderingHandler(entityClass.java, newRenderClass)
        }
    }
}