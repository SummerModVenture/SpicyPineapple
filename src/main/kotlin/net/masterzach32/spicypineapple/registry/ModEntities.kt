package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.MOD_ID
import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.entity.EntityBlackHole
import net.masterzach32.spicypineapple.entity.EntityBlock
import net.masterzach32.spicypineapple.entity.EntityHealArea
import net.masterzach32.spicypineapple.entity.render.RenderBlackHole
import net.masterzach32.spicypineapple.entity.render.RenderBlock
import net.masterzach32.spicypineapple.entity.render.RenderHealArea
import net.masterzach32.spicypineapple.util.clientOnly
import net.minecraft.client.renderer.entity.Render
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.registry.EntityRegistry
import kotlin.reflect.KClass

object ModEntities {

    @Mod.Instance(MOD_ID)
    lateinit var instance: SpicyPineappleMod

    private var currentId = 0

    fun init() {
        registerEntity(EntityHealArea::class, RenderHealArea::class, "healer", 20, 1, false)
        registerEntity(EntityBlackHole::class, RenderBlackHole::class, "black_hole", 20, 1, true)
        registerEntity(EntityBlock::class, RenderBlock::class, "block", 20, 1, true)
    }

    private fun <E : Entity, R : Render<E>> registerEntity(
            entityClass: KClass<E>,
            renderClass: KClass<R>,
            name: String,
            trackingRange: Int,
            updateFrequency: Int,
            sendVelocityData: Boolean
    ) {

        EntityRegistry.registerModEntity(
                ResourceLocation("$MOD_ID:$name"),
                entityClass.java,
                name,
                getNextId(),
                instance,
                trackingRange,
                updateFrequency,
                sendVelocityData

        )
        clientOnly {
            RenderingRegistry.registerEntityRenderingHandler(entityClass.java) { renderClass.constructors.first().call(it) }
        }
    }

    private fun getNextId(): Int {
        currentId++
        return currentId
    }
}