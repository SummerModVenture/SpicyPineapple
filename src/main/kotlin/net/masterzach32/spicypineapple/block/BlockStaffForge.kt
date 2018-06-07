package net.masterzach32.spicypineapple.block

import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.block.properties.IProperty
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class BlockStaffForge : BlockContainer(Material.WOOD) {

    companion object {
        val FACING: IProperty<EnumFacing> = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL)
    }

    override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float,
                                      hitZ: Float, meta: Int, placer: EntityLivingBase, hand: EnumHand): IBlockState {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(FACING, placer.horizontalFacing.opposite)
    }

    override fun getMetaFromState(state: IBlockState): Int = state.getValue(FACING).horizontalIndex

    @Suppress("OverridingDeprecatedMember")
    override fun getStateFromMeta(meta: Int): IBlockState {
        return defaultState.withProperty(FACING, EnumFacing.getHorizontal(meta))
    }

    override fun createBlockState(): BlockStateContainer = BlockStateContainer(this, FACING)

    override fun createNewTileEntity(world: World, meta: Int): TileEntity? {
        return null
    }

    @Suppress("OverridingDeprecatedMember")
    override fun getRenderType(state: IBlockState): EnumBlockRenderType = EnumBlockRenderType.MODEL
}