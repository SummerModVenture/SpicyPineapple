package net.masterzach32.spicypineapple.entity.render

import net.masterzach32.spicypineapple.MOD_ID
import net.masterzach32.spicypineapple.entity.EntityBlackHole
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation

class RenderBlackHole(manager: RenderManager) : Render<EntityBlackHole>(manager) {

    override fun getEntityTexture(entity: EntityBlackHole): ResourceLocation {
        return ResourceLocation("$MOD_ID:textures/models/none.png")
    }
}