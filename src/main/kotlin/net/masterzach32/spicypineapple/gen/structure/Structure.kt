package net.masterzach32.spicypineapple.gen.structure

import net.masterzach32.spicypineapple.MOD_ID
import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.gen.ShrineLocData
import net.masterzach32.spicypineapple.network.ShrineLocUpdateMessage
import net.minecraft.util.Mirror
import net.minecraft.util.ResourceLocation
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.gen.feature.WorldGenerator
import net.minecraft.world.gen.structure.template.PlacementSettings
import net.minecraftforge.fml.common.FMLCommonHandler
import java.util.*

open class Structure(val name: String) : WorldGenerator() {

    private val data = ResourceLocation(MOD_ID, name)

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private val placementSettings: PlacementSettings = PlacementSettings()
            .setChunk(null)
            .setIgnoreEntities(false)
            .setIgnoreStructureBlock(false)
            .setMirror(Mirror.NONE)

    override fun generate(world: World, rand: Random, pos: BlockPos): Boolean {
        val template = FMLCommonHandler.instance().minecraftServerInstance.getWorld(0).structureTemplateManager.get(world.minecraftServer, data)
        if (template != null) {
            placementSettings.rotation = Rotation.values()[rand.nextInt(Rotation.values().size)]
            val state = world.getBlockState(pos)
            world.notifyBlockUpdate(pos, state, state, 3)
            template.addBlocksToWorldChunk(world, pos, placementSettings)
        }
        return true
    }
}