package net.masterzach32.spicypineapple.block

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.dsl.distance
import net.masterzach32.spicypineapple.gen.ShrineLocData
import net.masterzach32.spicypineapple.item.ItemCrystal
import net.masterzach32.spicypineapple.network.ShrineLocUpdateMessage
import net.masterzach32.spicypineapple.registry.ModItems
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.masterzach32.spicypineapple.tile.CrystalizedPineappleTileEntity
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.NonNullList
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import java.util.*

class BlockPineapple(private val itemDropped: Item, private val countDropped: Int,
                     private val isCrystalized: Boolean) : Block(Material.CACTUS) {

    companion object {
        val BB = AxisAlignedBB(4/16.0, 0.0, 4/16.0, 12/16.0, 10/16.0, 12.0/16)
    }

    init {
        setCreativeTab(SpicyPineappleTab)
        setHardness(0.5f)
        if (isCrystalized)
            setLightLevel(0.4f)
    }

    override fun onBlockDestroyedByPlayer(world: World, pos: BlockPos, state: IBlockState) {
        if (isCrystalized && !world.isRemote) {
            ShrineLocData.getForWorld(world).map
                    .filter { it.distance(pos) < 8 }
                    .forEach {
                        ShrineLocData.getForWorld(world).removeShrineLocation(it)
                        SpicyPineappleMod.NETWORK.sendToAll(ShrineLocUpdateMessage(ShrineLocUpdateMessage.Action.REMOVE, it))
                    }
        }
    }

    override fun canProvidePower(state: IBlockState): Boolean = true

    override fun getWeakPower(state: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing): Int = 5

    @SuppressWarnings("Deprecated")
    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos): AxisAlignedBB = BB

    override fun getDrops(drops: NonNullList<ItemStack>, blockAccess: IBlockAccess, pos: BlockPos, state: IBlockState, fortune: Int) {
        drops.add(ItemStack(itemDropped, countDropped))
        for (i in 0..(1+fortune)/2)
            drops.add(ItemStack(ModItems.crystal, 1, (Math.random()*ItemCrystal.COUNT).toInt()))
    }

    @SuppressWarnings("Deprecated")
    override fun isFullBlock(state: IBlockState): Boolean = false

    @SuppressWarnings("Deprecated")
    override fun isFullCube(state: IBlockState): Boolean = false

    @SuppressWarnings("Deprecated")
    override fun isOpaqueCube(state: IBlockState): Boolean = false

    override fun getBlockLayer(): BlockRenderLayer = BlockRenderLayer.CUTOUT

    override fun createTileEntity(world: World, state: IBlockState): TileEntity? {
        return if (isCrystalized) CrystalizedPineappleTileEntity() else null
    }
}