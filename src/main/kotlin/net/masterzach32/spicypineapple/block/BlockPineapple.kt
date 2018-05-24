package net.masterzach32.spicypineapple.block

import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import java.util.*

class BlockPineapple(private val itemDropped: Item, private val countDropped: Int,
                     private val isEnergized: Boolean) : Block(Material.CACTUS) {

    init {
        setCreativeTab(SpicyPineappleTab)
        setHardness(0.5f)
    }

    @SuppressWarnings("Deprecated")
    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos): AxisAlignedBB {
        return AxisAlignedBB(4/16.0, 0.0, 4/16.0, 12/16.0, 10/16.0, 12.0/16)
    }

    override fun randomDisplayTick(state: IBlockState, world: World, pos: BlockPos, r: Random) {
        if (isEnergized) {
            val f1 = pos.x + .5f
            val f2 = pos.y + .6f
            val f3 = pos.z + .5f
            val f4 = r.nextFloat() * 0.6f - 0.3f
            val f5 = r.nextFloat() * -0.6f - -0.3f

            world.spawnParticle(EnumParticleTypes.REDSTONE, pos.x + 0.5, pos.y + 0.7, pos.z + 0.5,
                    (f1 + f4).toDouble(), f2.toDouble(), (f3 + f5).toDouble())
        }
    }

    override fun getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item  = itemDropped

    override fun quantityDropped(state: IBlockState, fortune: Int, random: Random): Int  = countDropped

    @SuppressWarnings("Deprecated")
    override fun isFullBlock(state: IBlockState?): Boolean = false

    @SuppressWarnings("Deprecated")
    override fun isFullCube(state: IBlockState?): Boolean = false

    @SuppressWarnings("Deprecated")
    override fun isOpaqueCube(state: IBlockState): Boolean = false

}