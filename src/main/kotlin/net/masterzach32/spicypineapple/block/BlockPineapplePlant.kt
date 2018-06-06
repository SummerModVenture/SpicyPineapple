package net.masterzach32.spicypineapple.block

import net.masterzach32.spicypineapple.EnumPineappleType
import net.minecraft.block.*
import net.minecraft.block.properties.IProperty
import net.minecraft.block.properties.PropertyBool
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

class BlockPineapplePlant(val crop: Block) : BlockBush(), IGrowable {

    lateinit var seedItem: Item

    companion object {
        const val MAX_AGE = 7
        val STEM_AABB = arrayOf(
                AxisAlignedBB(6.0/16, 0.0, 6.0/16, 10.0/16, 1.0/16, 10.0/16),
                AxisAlignedBB(6.0/16, 0.0, 6.0/16, 10.0/16, 2.0/16, 10.0/16),
                AxisAlignedBB(5.0/16, 0.0, 5.0/16, 11.0/16, 3.0/16, 11.0/16),
                AxisAlignedBB(5.0/16, 0.0, 5.0/16, 11.0/16, 5.0/16, 11.0/16),
                AxisAlignedBB(4.0/16, 0.0, 4.0/16, 12.0/16, 8.0/16, 12.0/16),
                AxisAlignedBB(3.0/16, 0.0, 3.0/16, 13.0/16, 11.0/16, 13.0/16),
                AxisAlignedBB(1.0/16, 0.0, 1.0/16, 15.0/16, 13.0/16, 15.0/16),
                AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 13.0/16, 1.0)
        )

        @JvmStatic val AGE: IProperty<Int> = PropertyInteger.create("age", 0, MAX_AGE)
    }

    init {
        defaultState = blockState.baseState.withProperty(AGE, 0)
        tickRandomly = true
    }

    @Suppress("OverridingDeprecatedMember")
    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos): AxisAlignedBB {
        return STEM_AABB[state.getValue(AGE)]
    }

    override fun updateTick(world: World, pos: BlockPos, state: IBlockState, rand: Random) {
        super.updateTick(world, pos, state, rand)

        if (!world.isAreaLoaded(pos, 1))
            return
        if (world.getLightFromNeighbors(pos.up()) >= 9 &&
                ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(EnumPineappleType.getTypeFromBlock(crop)!!.rarity*2+2) == 0)) {
            val currentAge = state.getValue(AGE)

            if (currentAge < 7)
                world.setBlockState(pos, state.withProperty(AGE, currentAge + 1), 2)
            else if (world.isAirBlock(pos.up()))
                world.setBlockState(pos.up(), crop.defaultState)

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

    override fun grow(world: World, rand: Random, pos: BlockPos, state: IBlockState) = growStem(world, pos, state)

    override fun canUseBonemeal(world: World, rand: Random, pos: BlockPos, state: IBlockState): Boolean = true

    override fun canSustainBush(state: IBlockState): Boolean = state.block == Blocks.FARMLAND

    override fun getMetaFromState(state: IBlockState): Int = state.getValue(AGE)

    @Suppress("OverridingDeprecatedMember")
    override fun getStateFromMeta(meta: Int): IBlockState = defaultState.withProperty(AGE, meta)

    override fun createBlockState(): BlockStateContainer = BlockStateContainer(this, AGE)

}