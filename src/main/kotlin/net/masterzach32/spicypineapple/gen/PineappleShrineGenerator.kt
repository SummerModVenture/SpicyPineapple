package net.masterzach32.spicypineapple.gen

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.registry.ModBlocks
import net.minecraft.block.BlockStairs
import net.minecraft.world.World
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import net.minecraftforge.fml.common.IWorldGenerator
import java.util.*
import net.minecraft.init.Blocks
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos


object PineappleShrineGenerator : IWorldGenerator {

    override fun generate(r: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkGenerator, chunkProvider: IChunkProvider) {
        if (r.nextInt(256) != 0)
            return

        val x = chunkX * 16 + r.nextInt(16) + 8
        val y = 70 + r.nextInt(50)
        val z = chunkZ * 16 + r.nextInt(16) + 8

        val centerBlock = BlockPos(x, y, z)

        val biome = world.biomeProvider.getBiome(centerBlock)

        if (world.getBlockState(centerBlock).block != Blocks.GRASS)
            return

        SpicyPineappleMod.logger.info("Spawning shrine at $centerBlock")

        world.setBlockState(BlockPos(x, y, z), Blocks.REDSTONE_LAMP.defaultState)
        // stairs
        world.setBlockState(BlockPos(x + 1, y, z), Blocks.STONE_BRICK_STAIRS.blockState.validStates
                .first { it.getValue(BlockStairs.FACING) == EnumFacing.WEST && it.getValue(BlockStairs.HALF) == BlockStairs.EnumHalf.BOTTOM })
        world.setBlockState(BlockPos(x - 1, y, z), Blocks.STONE_BRICK_STAIRS.blockState.validStates
                .first { it.getValue(BlockStairs.FACING) == EnumFacing.EAST && it.getValue(BlockStairs.HALF) == BlockStairs.EnumHalf.BOTTOM })
        world.setBlockState(BlockPos(x, y, z + 1), Blocks.STONE_BRICK_STAIRS.blockState.validStates
                .first { it.getValue(BlockStairs.FACING) == EnumFacing.NORTH && it.getValue(BlockStairs.HALF) == BlockStairs.EnumHalf.BOTTOM })
        world.setBlockState(BlockPos(x, y, z - 1), Blocks.STONE_BRICK_STAIRS.blockState.validStates
                .first { it.getValue(BlockStairs.FACING) == EnumFacing.SOUTH && it.getValue(BlockStairs.HALF) == BlockStairs.EnumHalf.BOTTOM })
        // under stairs
        world.setBlockState(BlockPos(x + 1, y - 1, z), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x + 2, y - 1, z), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x + 3, y - 1, z), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x - 1, y - 1, z), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x - 2, y - 1, z), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x - 3, y - 1, z), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x, y - 1, z + 1), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x, y - 1, z + 2), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x, y - 1, z + 3), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x, y - 1, z - 1), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x, y - 1, z - 2), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x, y - 1, z - 3), Blocks.STONEBRICK.defaultState)
        // beside under stairs
        world.setBlockState(BlockPos(x + 1, y - 1, z + 1), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x + 2, y - 1, z + 1), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x + 2, y - 1, z + 2), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x + 1, y - 1, z + 2), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x + 2, y, z + 2), Blocks.COBBLESTONE_WALL.defaultState)
        world.setBlockState(BlockPos(x + 2, y + 1, z + 2), ModBlocks.pineappleBlockCrystalized.defaultState)

        world.setBlockState(BlockPos(x + 1, y - 1, z - 1), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x + 2, y - 1, z - 1), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x + 2, y - 1, z - 2), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x + 1, y - 1, z - 2), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x + 2, y, z - 2), Blocks.COBBLESTONE_WALL.defaultState)
        world.setBlockState(BlockPos(x + 2, y + 1, z - 2), ModBlocks.pineappleBlockCrystalized.defaultState)

        world.setBlockState(BlockPos(x - 1, y - 1, z + 1), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x - 2, y - 1, z + 1), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x - 2, y - 1, z + 2), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x - 1, y - 1, z + 2), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x - 2, y, z + 2), Blocks.COBBLESTONE_WALL.defaultState)
        world.setBlockState(BlockPos(x - 2, y + 1, z + 2), ModBlocks.pineappleBlockCrystalized.defaultState)

        world.setBlockState(BlockPos(x - 1, y - 1, z - 1), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x - 2, y - 1, z - 1), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x - 2, y - 1, z - 2), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x - 1, y - 1, z - 2), Blocks.STONEBRICK.defaultState)
        world.setBlockState(BlockPos(x - 2, y, z - 2), Blocks.COBBLESTONE_WALL.defaultState)
        world.setBlockState(BlockPos(x - 2, y + 1, z - 2), ModBlocks.pineappleBlockCrystalized.defaultState)

        world.setBlockState(BlockPos(x, y + 1, z), ModBlocks.pineappleBlockCrystalized.defaultState)

    }
}