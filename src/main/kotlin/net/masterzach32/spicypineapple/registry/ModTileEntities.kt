package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.logger
import net.masterzach32.spicypineapple.block.BlockPineapple
import net.minecraftforge.fml.common.registry.GameRegistry

object ModTileEntities {

    @Suppress("DEPRECATION")
    fun init() {
        logger.info("Loading tile entities.")

        GameRegistry.registerTileEntity(BlockPineapple.CrystalizedPineappleTileEntity::class.java, "crystalized_pineapple")
    }
}