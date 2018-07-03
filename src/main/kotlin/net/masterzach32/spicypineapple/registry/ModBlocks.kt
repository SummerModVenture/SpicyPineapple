package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.block.BlockPineapple
import net.masterzach32.spicypineapple.EnumPineappleType
import net.masterzach32.spicypineapple.LOGGER
import net.masterzach32.spicypineapple.MOD_ID
import net.masterzach32.spicypineapple.block.BlockPineapplePlant
import net.masterzach32.spicypineapple.util.setCodename
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.client.event.ModelRegistryEvent

@Mod.EventBusSubscriber(modid = MOD_ID)
object ModBlocks {

    val pineappleBlock = BlockPineapple(EnumPineappleType.NORMAL).setCodename("pineapple_block")
    val pineappleBlockSpicy = BlockPineapple(EnumPineappleType.SPICY).setCodename("pineapple_block_spicy")
    val pineappleBlockCrystalized = BlockPineapple(EnumPineappleType.CRYSTALIZED).setCodename("pineapple_block_crystalized")

    val pineappleStem = BlockPineapplePlant(pineappleBlock).setCodename("pineapple_stem")
    val spicyPineappleStem = BlockPineapplePlant(pineappleBlockSpicy).setCodename("pineapple_stem_spicy")
    val crystalizedPineappleStem = BlockPineapplePlant(pineappleBlockCrystalized).setCodename("pineapple_stem_crystalized")

    val pineappleBlockItem = itemBlock(pineappleBlock)
    val pineappleBlockSpicyItem = itemBlock(pineappleBlockSpicy)
    val pineappleBlockCrystalizedItem = itemBlock(pineappleBlockCrystalized)

    @JvmStatic
    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        event.registry.registerAll(
                pineappleBlock,
                pineappleBlockSpicy,
                pineappleBlockCrystalized,
                pineappleStem,
                spicyPineappleStem,
                crystalizedPineappleStem
        )
    }

    @JvmStatic
    @SubscribeEvent
    fun registerItemBlocks(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(
                pineappleBlockItem,
                pineappleBlockSpicyItem,
                pineappleBlockCrystalizedItem
        )
    }


    @JvmStatic
    @SubscribeEvent
    fun registerRenders(event: ModelRegistryEvent) {
        registerRenders(
                pineappleBlock,
                pineappleBlockSpicy,
                pineappleBlockCrystalized,
                pineappleStem,
                spicyPineappleStem,
                crystalizedPineappleStem
        )
    }

    private fun registerRenders(vararg blocks: Block) {
        blocks.forEach {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(it), 0,
                    ModelResourceLocation(it.registryName!!, "inventory"))
        }
    }

    private fun itemBlock(b: Block) = ItemBlock(b).setRegistryName(b.registryName)!!

    fun init() {
        LOGGER.info("Loading blocks.")
    }

    fun lateInit() {
        (pineappleStem as BlockPineapplePlant).seedItem = ModItems.pineappleSeed
        (spicyPineappleStem as BlockPineapplePlant).seedItem = ModItems.spicyPineappleSeed
        (crystalizedPineappleStem as BlockPineapplePlant).seedItem = ModItems.crystalPineappleSeed
    }
}