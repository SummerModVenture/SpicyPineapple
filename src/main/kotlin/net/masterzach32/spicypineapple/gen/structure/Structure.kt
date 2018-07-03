package net.masterzach32.spicypineapple.gen.structure

import net.masterzach32.spicypineapple.LOGGER
import net.masterzach32.spicypineapple.MOD_ID
import net.minecraft.block.Block
import net.minecraft.util.Mirror
import net.minecraft.util.ResourceLocation
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.feature.WorldGenerator
import net.minecraft.world.gen.structure.template.PlacementSettings
import net.minecraft.world.gen.structure.template.Template
import net.minecraftforge.fml.common.FMLCommonHandler
import java.util.*

open class Structure(
        val name: String,
        val chance: Int,
        val biomes: List<Biome>,
        val dimension: Int = 0, // overworld
        val minHeight: Int = 0,
        val maxHeight: Int = 255
) : WorldGenerator() {

    private val data = ResourceLocation(MOD_ID, name)

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private val placementSettings: PlacementSettings = PlacementSettings()
            .setChunk(null)
            .setIgnoreEntities(false)
            .setIgnoreStructureBlock(false)
            .setMirror(Mirror.NONE)

    override fun generate(world: World, rand: Random, pos: BlockPos): Boolean {
        val template = getTemplate(world)
        if (template != null) {
            addBlocksToWorld(template, world, pos)
            return true
        } else {
            LOGGER.error("Could not find template for structure $name!")
        }
        return false
    }

    fun addBlocksToWorld(template: Template, world: World, pos: BlockPos) {
        val state = world.getBlockState(pos)
        world.notifyBlockUpdate(pos, state, state, 3)
        template.addBlocksToWorldChunk(world, pos, placementSettings)
    }

    fun getTemplate(world: World): Template? {
        return FMLCommonHandler.instance().minecraftServerInstance.getWorld(0).structureTemplateManager.get(world.minecraftServer, data)
    }
}