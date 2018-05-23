package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.item.ItemPineappleSlice
import net.minecraft.item.Item
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader

@Mod.EventBusSubscriber(modid = SpicyPineappleMod.MOD_ID)
object ModItems {

    val pineappleSlice = ItemPineappleSlice(2, 0.25F).addTooltip {
        it.add("§e+2 hearts over 5 seconds.")
    }.setPotionEffect(PotionEffect(Potion.getPotionFromResourceLocation("regeneration")!!, 30*5, 0), 1F).setUnlocalizedName("pineapple_slice").setRegistryName("pineapple_slice")
    val spicyPineappleSlice = ItemPineappleSlice(3, .5F).addTooltip {
        it.add("§e+5 hearts over 5 seconds")
    }.setPotionEffect(PotionEffect(Potion.getPotionFromResourceLocation("regeneration")!!, 30*5, 1), 1F).setUnlocalizedName("pineapple_slice_spicy").setRegistryName("pineapple_slice_spicy")
    val grilledPineappleSlice = ItemPineappleSlice(4, 1F).setUnlocalizedName("pineapple_slice_grilled").setRegistryName("pineapple_slice_grilled")
    val goldenPineappleSlice = ItemPineappleSlice(6, 1F).addTooltip {
        it.add("§e+5 hearts over 5 seconds")
    }.setPotionEffect(PotionEffect(Potion.getPotionFromResourceLocation("regeneration")!!, 30*5, 1), 1F).setUnlocalizedName("pineapple_slice_golden").setRegistryName("pineapple_slice_golden")
    val godlyPineappleSlice = ItemPineappleSlice(10, 2F).addTooltip {
        it.add("§e+20 hearts over 5 seconds")
    }.setPotionEffect(PotionEffect(Potion.getPotionFromResourceLocation("regeneration")!!, 30*5, 10), 1F).setUnlocalizedName("pineapple_slice_godly").setRegistryName("pineapple_slice_godly")

    fun init() {

    }

    @JvmStatic
    @SubscribeEvent
    fun registerItemBlocks(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(pineappleSlice, spicyPineappleSlice, grilledPineappleSlice, goldenPineappleSlice, godlyPineappleSlice)
    }

    @JvmStatic
    @SubscribeEvent
    fun registerRenders(event: ModelRegistryEvent) {
        registerRenders(pineappleSlice, spicyPineappleSlice, grilledPineappleSlice, goldenPineappleSlice, godlyPineappleSlice)
    }

    @JvmStatic
    private fun registerRenders(vararg items: Item) {
        items.forEach { ModelLoader.setCustomModelResourceLocation(it, 0, ModelResourceLocation(it.registryName!!, "inventory")) }
    }
}