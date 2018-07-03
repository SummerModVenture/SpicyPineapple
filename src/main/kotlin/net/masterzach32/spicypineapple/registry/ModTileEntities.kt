package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.LOGGER
import net.masterzach32.spicypineapple.block.BlockPineapple
import net.minecraftforge.fml.common.registry.GameRegistry

object ModTileEntities {

    @Suppress("DEPRECATION")
    fun init() {
        LOGGER.info("Loading tile entities.")

        GameRegistry.registerTileEntity(BlockPineapple.CrystalizedPineappleTileEntity::class.java, "crystalized_pineapple")
    }
}