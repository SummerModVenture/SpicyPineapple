package net.masterzach32.spicypineapple.client

import net.minecraft.client.particle.Particle
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import org.lwjgl.opengl.GL11



/*
 * SpicyPineapple - Created on 6/1/2018
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 6/1/2018
 */
class EssenceParticle(world: World, posX: Double, posY: Double, posZ: Double) : Particle(world, posX, posY, posZ) {

    private val texture = ResourceLocation("spicypineapple:textures/items/pineapple_slice.png")
    private val r = Random()

    init {
        val speedMagnitude = 0.1
        val x = r.nextGaussian()
        val y = r.nextGaussian()
        val z = r.nextGaussian()
        val mult = 1/ sqrt(x.pow(2) + y.pow(2) + z.pow(2)) * speedMagnitude
        motionX = x * mult
        motionY = y * mult
        motionZ = z * mult
        canCollide = false
        particleMaxAge = (6.0 / (rand.nextFloat() * .9 + .1)).toInt()
    }

    override fun renderParticle(
            buffer: BufferBuilder,
            entityIn: Entity,
            partialTicks: Float,
            rotX: Float,
            rotZ: Float,
            rotYZ: Float,
            rotXY: Float,
            rotXZ: Float
    ) {

        Minecraft.getMinecraft().entityRenderer.disableLightmap()
        GlStateManager.enableAlpha()
        GlStateManager.enableBlend()
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE)
        val f3 = (prevPosX + (posX - prevPosX) * partialTicks - Particle.interpPosX - Minecraft.getMinecraft().player.motionX / 2f)
        val f4 = (prevPosY + (posY - prevPosY) * partialTicks - Particle.interpPosY - Minecraft.getMinecraft().player.motionY / 2f)
        val f5 = (prevPosZ + (posZ - prevPosZ) * partialTicks - Particle.interpPosZ - Minecraft.getMinecraft().player.motionZ / 2f)
        val i = getBrightnessForRender(partialTicks)
        //val j11 = i shr 16 and 65535
        //val k11 = i and 65535
        val size = 0.04f//directScale > 0 ? directScale : (0.1F * (isCauldronTop ? 3.15f : this.particleScale));
        Minecraft.getMinecraft().textureManager.bindTexture(texture)
        val k = particleTextureIndexX / 16.0
        val k1 = 1
        val k2 = particleTextureIndexY / 16.0
        val k3 = 1
        val t = doubleArrayOf(k1.toDouble(), k3.toDouble(), k1.toDouble(), k2, k, k2, k, k3.toDouble())
        val f6 = MathHelper.clamp(world.getLight(BlockPos(posX, posY, posZ)) / 16f, 0.3f, 1f)
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR)
        //buffer.color(1,1,1, (float) Math.sqrt(this.particleAge / this.particleMaxAge));
        buffer.pos((f3 - rotX * size - rotXY * size), (f4 - rotZ * size), (f5 - rotYZ * size - rotXZ * size)).tex(t[0], t[1]).color(f6, f6, f6, particleAlpha).endVertex()
        buffer.pos((f3 - rotX * size + rotXY * size), (f4 + rotZ * size), (f5 - rotYZ * size + rotXZ * size)).tex(t[2], t[3]).color(f6, f6, f6, particleAlpha).endVertex()
        buffer.pos((f3 + rotX * size + rotXY * size), (f4 + rotZ * size), (f5 + rotYZ * size + rotXZ * size)).tex(t[4], t[5]).color(f6, f6, f6, particleAlpha).endVertex()
        buffer.pos((f3 + rotX * size - rotXY * size), (f4 - rotZ * size), (f5 + rotYZ * size - rotXZ * size)).tex(t[6], t[7]).color(f6, f6, f6, particleAlpha).endVertex()
        Tessellator.getInstance().draw()
        //GlStateManager.enableLighting();
        GlStateManager.disableBlend()
        GlStateManager.disableAlpha()
        Minecraft.getMinecraft().entityRenderer.enableLightmap()
    }

    override fun onUpdate() {
        super.onUpdate()
        particleAlpha = sqrt(1 - particleAge / particleMaxAge.toFloat())
    }

    override fun getFXLayer(): Int = 3
}