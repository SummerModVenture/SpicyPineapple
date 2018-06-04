package net.masterzach32.spicypineapple.block

import net.masterzach32.spicypineapple.EnumPineappleType
import net.minecraft.block.*
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.NonNullList
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks
import java.util.*

class PineappleStem(val crop: Block) : BlockBush(), IGrowable {

    lateinit var seedItem: Item

    companion object {
        private const val MAX_AGE = 7
        val STEM_AABB = arrayOf(AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 0.125, 0.625),
                AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 0.25, 0.625),
                AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 0.375, 0.625),
                AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 0.5, 0.625),
                AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 0.625, 0.625),
                AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 0.75, 0.625),
                AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 0.875, 0.625),
                AxisAlignedBB(0.375, 0.0, 0.375, 0.625, 1.0, 0.625))


        @JvmStatic val FACING: PropertyDirection = BlockTorch.FACING
        @JvmStatic val AGE: PropertyInteger = PropertyInteger.create("age", 0, MAX_AGE)
    }

    init {
        defaultState = blockState.baseState.withProperty(FACING, EnumFacing.UP).withProperty(AGE, 0)
        tickRandomly = true
    }

    @Suppress("OverridingDeprecatedMember")
    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos): AxisAlignedBB {
        return STEM_AABB[state.getValue(AGE)]
    }

    @Suppress("OverridingDeprecatedMember")
    override fun getActualState(state: IBlockState, world: IBlockAccess, pos: BlockPos): IBlockState {
        val currentAge = state.getValue(AGE)
        val newState = state.withProperty(FACING, EnumFacing.UP)

        EnumFacing.Plane.HORIZONTAL
                .filter { world.getBlockState(pos.offset(it)).block == crop && currentAge == MAX_AGE }
                .forEach { return newState.withProperty(FACING, it) }

        return state
    }

    override fun updateTick(world: World, pos: BlockPos, state: IBlockState, rand: Random) {
        super.updateTick(world, pos, state, rand)

        if (!world.isAreaLoaded(pos, 1))
            return
        if (world.getLightFromNeighbors(pos.up()) >= 9 &&
                ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(EnumPineappleType.getTypeFromBlock(crop)!!.rarity*2+2) == 0)) {
            val currentAge = state.getValue(AGE)
            if (currentAge < 7) {
                val newState = state.withProperty(AGE, currentAge + 1)
                world.setBlockState(pos, newState, 2)
            } else {
                if (EnumFacing.Plane.HORIZONTAL.any { world.getBlockState(pos.offset(it)).block == crop })
                    return
                val newFruit = EnumFacing.Plane.HORIZONTAL
                        .map { pos.offset(it) }
                        .filter { world.isAirBlock(it) }
                        .map { Pair<BlockPos, IBlockState>(it.down(), world.getBlockState(it.down())) }
                        .firstOrNull { it.second.block == Blocks.DIRT || it.second.block == Blocks.GRASS ||
                                it.second.block.canSustainPlant(it.second, world, it.first, EnumFacing.UP, this) } ?: return
                world.setBlockState(newFruit.first.up(), crop.defaultState)
            }
            ForgeHooks.onCropsGrowPost(world, pos, state, world.getBlockState(pos))
        }
    }

    private fun growStem(world: World, pos: BlockPos, state: IBlockState) {
        val newAge = state.getValue(AGE) + MathHelper.getInt(world.rand, 2, 5)
        world.setBlockState(pos, state.withProperty(AGE, Math.min(MAX_AGE, newAge)), 2)
    }

    override fun getDrops(drops: NonNullList<ItemStack>, world: IBlockAccess, pos: BlockPos, state: IBlockState, fortune: Int) {
        val currentAge = state.getValue(AGE)
        for (i in 0 until 3)
            if (Block.RANDOM.nextInt(15) < currentAge)
                drops.add(ItemStack(seedItem))
    }

    override fun getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item = Items.AIR

    @Suppress("OverridingDeprecatedMember")
    override fun getItem(world: World, pos: BlockPos, state: IBlockState): ItemStack = ItemStack(seedItem)

    override fun canGrow(world: World, pos: BlockPos, state: IBlockState, isClient: Boolean): Boolean = state.getValue(AGE) != MAX_AGE

    override fun grow(world: World, rand: Random, pos: BlockPos, state: IBlockState) {
        growStem(world, pos, state)
    }

    override fun canUseBonemeal(world: World, rand: Random, pos: BlockPos, state: IBlockState): Boolean = true

    override fun canSustainBush(state: IBlockState): Boolean = state.block == Blocks.FARMLAND

    override fun getMetaFromState(state: IBlockState): Int = state.getValue(AGE)

    @Suppress("OverridingDeprecatedMember")
    override fun getStateFromMeta(meta: Int): IBlockState = defaultState.withProperty(AGE, meta)

    override fun createBlockState(): BlockStateContainer = BlockStateContainer(this, FACING, AGE)

}