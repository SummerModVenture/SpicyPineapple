package net.masterzach32.spicypineapple.entity.render

import net.masterzach32.spicypineapple.MOD_ID
import net.masterzach32.spicypineapple.entity.EntityHealArea
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation

class RenderHealArea(manager: RenderManager) : Render<EntityHealArea>(manager) {

    override fun getEntityTexture(entity: EntityHealArea): ResourceLocation {
        return ResourceLocation("$MOD_ID:textures/entity/none.png")
    }
}