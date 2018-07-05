package net.masterzach32.spicypineapple.entity.render

import net.masterzach32.spicypineapple.MOD_ID
import net.masterzach32.spicypineapple.entity.EntityEarthRipper
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation

class RenderEarthRipper(manager: RenderManager) : Render<EntityEarthRipper>(manager) {

    override fun getEntityTexture(entity: EntityEarthRipper): ResourceLocation {
        return ResourceLocation("$MOD_ID:textures/entity/none.png")
    }
}