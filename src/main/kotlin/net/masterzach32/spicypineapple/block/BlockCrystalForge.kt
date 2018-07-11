package net.masterzach32.spicypineapple.block

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.block.container.GuiContainer
import net.masterzach32.spicypineapple.gui.CrystalForgeGuiHandler
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.block.properties.IProperty
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ITickable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

@Suppress("OverridingDeprecatedMember")
class BlockCrystalForge : BlockContainer(Material.WOOD) {

    override fun getStateForPlacement(
            world: World,
            pos: BlockPos,
            facing: EnumFacing,
            hitX: Float,
            hitY: Float,
            hitZ: Float,
            meta: Int,
            placer: EntityLivingBase,
            hand: EnumHand
    ): IBlockState {

        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand)
                .withProperty(FACING, placer.horizontalFacing.opposite)
    }

    override fun onBlockActivated(
            world: World,
            pos: BlockPos,
            state: IBlockState,
            player: EntityPlayer,
            hand: EnumHand,
            facing: EnumFacing,
            hitX: Float,
            hitY: Float,
            hitZ: Float
    ): Boolean {

        if (!world.isRemote)
            player.openGui(SpicyPineappleMod, CrystalForgeGuiHandler.id, world, pos.x, pos.y, pos.z)
        return true
    }

    override fun getMetaFromState(state: IBlockState): Int = state.getValue(FACING).horizontalIndex

    override fun getStateFromMeta(meta: Int): IBlockState {
        return defaultState.withProperty(FACING, EnumFacing.getHorizontal(meta))
    }

    override fun createBlockState(): BlockStateContainer = BlockStateContainer(this, FACING, ACTIVE)

    override fun getActualState(state: IBlockState, world: IBlockAccess, pos: BlockPos): IBlockState {
        val tile = world.getTileEntity(pos)
        if (tile is CrystalForgeTileEntity)
            return state.withProperty(ACTIVE, tile.isRunning())
        return state
    }

    override fun createNewTileEntity(world: World, meta: Int): TileEntity? = null

    override fun getRenderType(state: IBlockState): EnumBlockRenderType = EnumBlockRenderType.MODEL

    companion object {
        val FACING: IProperty<EnumFacing> = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL)
        val ACTIVE: IProperty<Boolean> = PropertyBool.create("active")
    }

    class CrystalForgeTileEntity : TileEntity(), ISidedInventory, ITickable {

        fun isRunning(): Boolean = false

        override fun getStackInSlot(index: Int): ItemStack {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun clear() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getName(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getInventoryStackLimit(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getSlotsForFace(side: EnumFacing): IntArray {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun openInventory(player: EntityPlayer) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun setField(id: Int, value: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun removeStackFromSlot(index: Int): ItemStack {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getFieldCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getField(id: Int): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun hasCustomName(): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun decrStackSize(index: Int, count: Int): ItemStack {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getSizeInventory(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun isEmpty(): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun isItemValidForSlot(index: Int, stack: ItemStack): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun canInsertItem(index: Int, stack: ItemStack, direction: EnumFacing): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun isUsableByPlayer(player: EntityPlayer): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun canExtractItem(index: Int, stack: ItemStack, direction: EnumFacing): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun closeInventory(player: EntityPlayer) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun setInventorySlotContents(index: Int, stack: ItemStack) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun update() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    class CrystalForgeContainer(inventory: InventoryPlayer, tile: CrystalForgeTileEntity) : GuiContainer(inventory, tile) {

        override fun onPlayerToContainer(player: EntityPlayer, sourceStack: ItemStack, index: Int): ItemStack? {

        }

        override fun onContainerToPlayer(player: EntityPlayer, sourceStack: ItemStack, index: Int): ItemStack? {

        }
    }
}