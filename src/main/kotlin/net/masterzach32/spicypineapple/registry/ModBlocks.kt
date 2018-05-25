package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.block.BlockPineapple
import net.masterzach32.spicypineapple.dsl.setCodename
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.client.event.ModelRegistryEvent

@Mod.EventBusSubscriber(modid = SpicyPineappleMod.MOD_ID)
object ModBlocks {

    val pineappleBlock = BlockPineapple(ModItems.pineappleSlice, 4, false).setCodename("pineapple_block")
    val pineappleBlockCrystalized = BlockPineapple(ModItems.pineappleSlice, 4, true).setCodename("pineapple_block_energized")

    @JvmStatic
    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        event.registry.registerAll(
                pineappleBlock,
                pineappleBlockCrystalized
        )
    }

    @JvmStatic
    @SubscribeEvent
    fun registerItemBlocks(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(
                itemBlock(pineappleBlock),
                itemBlock(pineappleBlockCrystalized)
        )
    }


    @JvmStatic
    @SubscribeEvent
    fun registerRenders(event: ModelRegistryEvent) {
        registerRenders(
                pineappleBlock,
                pineappleBlockCrystalized
        )
    }

    private fun registerRenders(vararg blocks: Block) {
        blocks.forEach {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(it), 0,
                    ModelResourceLocation(it.registryName!!, "inventory"))
        }
    }

    private fun itemBlock(b: Block) = ItemBlock(b).setRegistryName(b.registryName)

    fun init() {}
}