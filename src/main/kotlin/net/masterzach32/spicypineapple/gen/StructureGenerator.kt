package net.masterzach32.spicypineapple.gen

import net.masterzach32.spicypineapple.gen.structure.PineappleShrine
import net.masterzach32.spicypineapple.gen.structure.Structure
import net.minecraft.block.Block
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import net.minecraftforge.fml.common.IWorldGenerator
import java.util.*
import kotlin.math.min

object StructureGenerator : IWorldGenerator {

    val shrine: Structure = PineappleShrine()

    val structures = listOf(shrine)

    override fun generate(
            rand: Random,
            chunkX: Int,
            chunkZ: Int,
            world: World,
            chunkGenerator: IChunkGenerator,
            chunkProvider: IChunkProvider
    ) {

        structures
                .filter { it.dimension == world.provider.dimension }
                .forEach { generateStructure(it, world, rand, chunkX, chunkZ) }
    }

    private fun generateStructure(
            structure: Structure,
            world: World,
            rand: Random,
            chunkX: Int,
            chunkZ: Int
    ) {

        val x = chunkX * 16 + rand.nextInt(16)
        val z = chunkZ * 16 + rand.nextInt(16)
        val y = calcGenHeight(world, x, z, structure.biomes.map { it.topBlock.block }, structure.minHeight, structure.maxHeight)
        if (y == -1)
            return
        val pos = BlockPos(x, y, z)

        val biome = world.provider.getBiomeForCoords(pos)

        if (structure.biomes.contains(biome) && rand.nextInt(structure.chance) == 0)
            structure.generate(world, rand, pos)
    }

    private fun calcGenHeight(world: World, x: Int, z: Int, groundBlocks: List<Block>, minHeight: Int = 0, maxHeight: Int = world.height-1): Int {
        for (y in min(world.height-1, maxHeight) downTo minHeight)
            if (groundBlocks.any { it == world.getBlockState(BlockPos(x, y, z)).block })
                return y
        return -1
    }
}
