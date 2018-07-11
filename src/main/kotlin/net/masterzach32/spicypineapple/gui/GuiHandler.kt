package net.masterzach32.spicypineapple.gui

import net.masterzach32.spicypineapple.block.BlockCrystalForge
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

sealed class GuiHandler : IGuiHandler {

    val id = nextId++

    companion object {
        private var nextId = 0
    }
}

object CrystalForgeGuiHandler : GuiHandler() {

    override fun getClientGuiElement(id: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        val tile = world.getTileEntity(BlockPos(x, y, z))
        if (tile is BlockCrystalForge.CrystalForgeTileEntity)
            return CrystalForgeGui(player.inventory, tile)
        return null
    }

    override fun getServerGuiElement(id: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        val tile = world.getTileEntity(BlockPos(x, y, z))
        if (tile is BlockCrystalForge.CrystalForgeTileEntity)
            return BlockCrystalForge.CrystalForgeContainer(player.inventory, tile)
        return null
    }
}