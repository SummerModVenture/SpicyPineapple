package net.masterzach32.spicypineapple.gen

import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import net.minecraftforge.fml.common.IWorldGenerator
import java.util.*

class ClusterGenerator(private val toSpawn: Block, private val probability: Double) : IWorldGenerator {

    override fun generate(r: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkGenerator, chunkProvider: IChunkProvider) {
        if (Math.random() < probability) {
            val x = chunkX*16 + r.nextInt(16) + 8
            val y = 50 + r.nextInt(60)
            val z = chunkZ*16 + r.nextInt(16) + 8

            if (world.getBiome(BlockPos(x, y, z)).isSnowyBiome)
                return

            var count = 0
            var i = 0
            while (count < 8 && i < 48) {
                val x1 = x + r.nextInt(8) - r.nextInt(8)
                val y1 = y + r.nextInt(4) - r.nextInt(4)
                val z1 = z + r.nextInt(8) - r.nextInt(8)

                val blockPos = BlockPos(x1, y1, z1)

                if (world.isAirBlock(blockPos) && world.getBlockState(BlockPos(x1, y1-1, z1)).block == Blocks.GRASS
                        && toSpawn.canPlaceBlockAt(world, blockPos)) {
                    world.setBlockState(blockPos, toSpawn.defaultState)
                    count++
                }
                i++
            }
        }
    }
}