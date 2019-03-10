package net.masterzach32.spicypineapple.registry

import com.spicymemes.core.util.setCodename
import net.masterzach32.spicypineapple.block.BlockPineapple
import net.masterzach32.spicypineapple.logger
import net.masterzach32.spicypineapple.MOD_ID
import net.masterzach32.spicypineapple.block.BlockPineapplePlant
import net.masterzach32.spicypineapple.item.ItemPineappleSlice
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.ItemStack
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.client.event.ModelRegistryEvent

@Mod.EventBusSubscriber(modid = MOD_ID)
object ModBlocks {

    val pineappleBlock = BlockPineapple(ItemPineappleSlice.Type.NORMAL).setCodename("pineapple_block")
    val pineappleBlockSpicy = BlockPineapple(ItemPineappleSlice.Type.SPICY).setCodename("pineapple_block_spicy")
    val pineappleBlockCrystalized = BlockPineapple(ItemPineappleSlice.Type.CRYSTALIZED).setCodename("pineapple_block_crystalized")

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
                //crystalForge
        )
    }

    @JvmStatic
    @SubscribeEvent
    fun registerItemBlocks(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(
                pineappleBlockItem,
                pineappleBlockSpicyItem,
                pineappleBlockCrystalizedItem
                //crystalForgeItem
        )
    }


    @JvmStatic
    @SubscribeEvent
    fun registerRenders(event: ModelRegistryEvent) {
        registerRenders(
                pineappleBlock,
                pineappleBlockSpicy,
                pineappleBlockCrystalized
                //crystalForge
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
        logger.info("Loading blocks.")
    }

    fun lateInit() {
        (pineappleStem as BlockPineapplePlant).seedItem = ItemStack(ModItems.pineappleSeed)
        (spicyPineappleStem as BlockPineapplePlant).seedItem = ItemStack(ModItems.spicyPineappleSeed)
        (crystalizedPineappleStem as BlockPineapplePlant).seedItem = ItemStack(ModItems.crystalPineappleSeed)
    }
}