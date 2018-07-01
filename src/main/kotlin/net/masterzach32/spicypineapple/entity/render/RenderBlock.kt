package net.masterzach32.spicypineapple.entity.render

import net.masterzach32.spicypineapple.entity.EntityBlock
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper

class RenderBlock(manager: RenderManager) : Render<EntityBlock>(manager) {

    /**
     * Renders the desired `T` type Entity.
     */
    override fun doRender(entity: EntityBlock?, x: Double, y: Double, z: Double, entityYaw: Float, partialTicks: Float) {
        if (entity?.state != null) {
            val iblockstate = entity.state

            if (iblockstate!!.renderType == EnumBlockRenderType.MODEL) {
                val world = entity.world

                if (iblockstate !== world.getBlockState(BlockPos(entity)) && iblockstate.renderType != EnumBlockRenderType.INVISIBLE) {
                    this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)
                    GlStateManager.pushMatrix()
                    GlStateManager.disableLighting()
                    val tessellator = Tessellator.getInstance()
                    val bufferbuilder = tessellator.buffer

                    if (this.renderOutlines) {
                        GlStateManager.enableColorMaterial()
                        GlStateManager.enableOutlineMode(this.getTeamColor(entity))
                    }

                    bufferbuilder.begin(7, DefaultVertexFormats.BLOCK)
                    val blockpos = BlockPos(entity.posX, entity.entityBoundingBox.maxY, entity.posZ)
                    GlStateManager.translate((x - blockpos.x.toDouble() - 0.5).toFloat(), (y - blockpos.y.toDouble()).toFloat(), (z - blockpos.z.toDouble() - 0.5).toFloat())
                    val blockrendererdispatcher = Minecraft.getMinecraft().blockRendererDispatcher
                    blockrendererdispatcher.blockModelRenderer.renderModel(world, blockrendererdispatcher.getModelForState(iblockstate), iblockstate, blockpos, bufferbuilder, false, MathHelper.getPositionRandom(entity.origin!!))
                    tessellator.draw()

                    if (this.renderOutlines) {
                        GlStateManager.disableOutlineMode()
                        GlStateManager.disableColorMaterial()
                    }

                    GlStateManager.enableLighting()
                    GlStateManager.popMatrix()
                    super.doRender(entity, x, y, z, entityYaw, partialTicks)
                }
            }
        }
    }

    override fun getEntityTexture(entity: EntityBlock): ResourceLocation {
        return TextureMap.LOCATION_BLOCKS_TEXTURE
    }
}