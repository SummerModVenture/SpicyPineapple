package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.block.BlockPineapple
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

    val pineappleBlock = BlockPineapple(ModItems.pineappleSlice, 4).setUnlocalizedName("pineapple").setRegistryName("pineapple")
    val spicyPineappleBlock = BlockPineapple(ModItems.spicyPineappleSlice, 4).setUnlocalizedName("pineapple_spicy").setRegistryName("pineapple_spicy")

    fun init() {

    }

    @JvmStatic
    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        event.registry.registerAll(pineappleBlock, spicyPineappleBlock)
    }

    @JvmStatic
    @SubscribeEvent
    fun registerItemBlocks(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(ItemBlock(pineappleBlock).setRegistryName(pineappleBlock.registryName))
        event.registry.registerAll(ItemBlock(spicyPineappleBlock).setRegistryName(spicyPineappleBlock.registryName))
    }

    @SubscribeEvent
    fun registerRenders(event: ModelRegistryEvent) {
        registerRenders(pineappleBlock, spicyPineappleBlock)
    }

    private fun registerRenders(vararg blocks: Block) {
        blocks.forEach { ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(it), 0, ModelResourceLocation(it.registryName!!, "inventory")) }
    }
}