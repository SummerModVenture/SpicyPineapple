package net.masterzach32.spicypineapple.gen

import net.masterzach32.spicypineapple.gen.structure.PineappleShrine
import net.masterzach32.spicypineapple.gen.structure.Structure
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import net.minecraftforge.fml.common.IWorldGenerator
import java.util.*

object StructureGenerator : IWorldGenerator {

    val shrine: Structure = PineappleShrine()

    override fun generate(
            rand: Random,
            chunkX: Int,
            chunkZ: Int,
            world: World,
            chunkGenerator: IChunkGenerator,
            chunkProvider: IChunkProvider
    ) {

        when (world.provider.dimension) {
            0 -> generateStructure(shrine, world, rand, chunkX, chunkZ, 10, Blocks.GRASS)
        }
    }

    private fun generateStructure(
            structure: Structure,
            world: World,
            rand: Random,
            chunkX: Int,
            chunkZ: Int,
            chance: Int,
            topBlock: Block
    ) {

        val x = chunkX * 16 + rand.nextInt(16)
        val z = chunkZ * 16 + rand.nextInt(16)
        val y = calcGenHeight(world, x, z, topBlock, minHeight = 70, maxHeight = 100)
        if (y == -1)
            return
        val pos = BlockPos(x, y, z)

        val biome = world.provider.getBiomeForCoords(pos)

        if (rand.nextInt(chance) == 0)
            structure.generate(world, rand, pos)
    }

    private fun calcGenHeight(world: World, x: Int, z: Int, topBlock: Block, minHeight: Int = 0, maxHeight: Int = world.height-1): Int {
        for (y in  Math.min(world.height-1, maxHeight) downTo minHeight)
            if (world.getBlockState(BlockPos(x, y, z)).block == topBlock)
                return y
        return -1
    }
}