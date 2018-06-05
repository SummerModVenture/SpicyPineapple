package net.masterzach32.spicypineapple.block

import net.masterzach32.spicypineapple.EnumPineappleType
import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.block.BlockPineapplePlant.Companion.AGE
import net.masterzach32.spicypineapple.util.distance
import net.masterzach32.spicypineapple.gen.ShrineLocData
import net.masterzach32.spicypineapple.network.ShrineLocUpdateMessage
import net.masterzach32.spicypineapple.registry.ModItems
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.masterzach32.spicypineapple.tile.CrystalizedPineappleTileEntity
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.properties.IProperty
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.NonNullList
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import java.util.*

class BlockPineapple(private val type: EnumPineappleType) : Block(Material.CACTUS) {

    companion object {
        val BB = AxisAlignedBB(4/16.0, 0.0, 4/16.0, 12/16.0, 10/16.0, 12.0/16)
        val PLANT_BB = AxisAlignedBB(4/16.0, 0.0, 4/16.0, 12/16.0, 10/16.0, 12.0/16)

        @JvmStatic val ON_PLANT: IProperty<Boolean> = PropertyBool.create("onplant")
    }

    init {
        setCreativeTab(SpicyPineappleTab)
        setHardness(0.5f)
        if (type == EnumPineappleType.CRYSTALIZED)
            setLightLevel(0.4f)

        defaultState = blockState.baseState.withProperty(ON_PLANT, false)
    }

    override fun onBlockDestroyedByPlayer(world: World, pos: BlockPos, state: IBlockState) {
        if (type == EnumPineappleType.CRYSTALIZED && !world.isRemote) {
            ShrineLocData.getForWorld(world).map
                    .filter { it.distance(pos) < 8 }
                    .forEach {
                        ShrineLocData.getForWorld(world).removeShrineLocation(it)
                        SpicyPineappleMod.NETWORK.sendToAll(ShrineLocUpdateMessage(ShrineLocUpdateMessage.Action.REMOVE, it))
                    }
        }
    }

    override fun canPlaceBlockAt(world: World, pos: BlockPos): Boolean {
        val ground = world.getBlockState(pos.down())
        return (ground.isFullCube && ground.isFullBlock) ||
                (ground.block is BlockPineapplePlant && ground.getValue(AGE) == BlockPineapplePlant.MAX_AGE)
    }

    @Suppress("OverridingDeprecatedMember")
    override fun canProvidePower(state: IBlockState): Boolean = type == EnumPineappleType.CRYSTALIZED

    @Suppress("OverridingDeprecatedMember")
    override fun getWeakPower(state: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing): Int {
        return if (type == EnumPineappleType.CRYSTALIZED) 5 else 0
    }

    @Suppress("OverridingDeprecatedMember")
    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos): AxisAlignedBB {
        return if (state.getValue(ON_PLANT)) PLANT_BB else BB
    }

    override fun getDrops(drops: NonNullList<ItemStack>, blockAccess: IBlockAccess, pos: BlockPos, state: IBlockState, fortune: Int) {
        val shouldDropSeed = Random().nextInt(4) == 0

        when (type) {
            EnumPineappleType.NORMAL -> {
                drops.add(ItemStack(ModItems.pineappleSlice, 4))
                if (shouldDropSeed)
                    drops.add(ItemStack(ModItems.pineappleSeed))
            }
            EnumPineappleType.SPICY -> {
                drops.add(ItemStack(ModItems.spicyPineappleSlice, 4))
                if (shouldDropSeed)
                    drops.add(ItemStack(ModItems.spicyPineappleSeed))
            }
            EnumPineappleType.CRYSTALIZED -> {
                drops.add(ItemStack(ModItems.crystalPineappleSlice, 4))
                if (shouldDropSeed)
                    drops.add(ItemStack(ModItems.crystalPineappleSeed))
                for (i in 0..(1 + fortune) / 2)
                    drops.add(ItemStack(ModItems.crystal, 1, (Math.random() * EnumPineappleType.values().size).toInt()))
            }
        }
    }

    @Suppress("OverridingDeprecatedMember")
    override fun isFullBlock(state: IBlockState): Boolean = false

    @Suppress("OverridingDeprecatedMember")
    override fun isFullCube(state: IBlockState): Boolean = false

    @Suppress("OverridingDeprecatedMember")
    override fun isOpaqueCube(state: IBlockState): Boolean = false

    override fun getBlockLayer(): BlockRenderLayer = BlockRenderLayer.CUTOUT

    override fun createTileEntity(world: World, state: IBlockState): TileEntity? {
        return if (type == EnumPineappleType.CRYSTALIZED) CrystalizedPineappleTileEntity() else null
    }

    override fun createBlockState(): BlockStateContainer = BlockStateContainer(this, ON_PLANT)

    override fun getMetaFromState(state: IBlockState): Int = 0

    @Suppress("OverridingDeprecatedMember")
    override fun getStateFromMeta(meta: Int): IBlockState = defaultState

    override fun getActualState(state: IBlockState, world: IBlockAccess, pos: BlockPos): IBlockState {
        return state.withProperty(ON_PLANT, world.getBlockState(pos.down()).block is BlockPineapplePlant)
    }
}