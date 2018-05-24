package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.dsl.setCodename
import net.masterzach32.spicypineapple.item.ItemEnergizedPickaxe
import net.masterzach32.spicypineapple.item.ItemPineappleSlice
import net.masterzach32.spicypineapple.item.ToolMaterialPineapple
import net.masterzach32.spicypineapple.item.Toolset
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
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

    val pineappleSlice = ItemPineappleSlice(2, 0.25F).setCodename("pineapple_slice")
    val spicyPineappleSlice = ItemPineappleSlice(3, .5F).setCodename("pineapple_slice_spicy")
    val grilledPineappleSlice = ItemPineappleSlice(4, 1F).setCodename("pineapple_slice_grilled")
    val goldenPineappleSlice = ItemPineappleSlice(6, 1F)
            .setPotionEffect(PotionEffect(Potion.getPotionFromResourceLocation("regeneration")!!, 150, 1), 1F)
            .setCodename("pineapple_slice_golden")
    val energizedPineappleSlice = ItemPineappleSlice(0, 2F).setCodename("pineapple_slice_energized")
    val energizedCrystal = Item().setCodename("energized_crystal")

    val pineappleToolset = Toolset("pineapple", ToolMaterialPineapple, SpicyPineappleTab)

    val energizedPickaxe = ItemEnergizedPickaxe().setCodename("energized_pickaxe")

    @JvmStatic
    @SubscribeEvent
    fun registerItemBlocks(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(
                pineappleSlice,
                spicyPineappleSlice,
                grilledPineappleSlice,
                goldenPineappleSlice,
                energizedPineappleSlice,
                energizedCrystal,
                energizedPickaxe,
                *pineappleToolset.getItems()
        )
    }

    @JvmStatic
    @SubscribeEvent
    fun registerRenders(event: ModelRegistryEvent) {
        registerRenders(
                pineappleSlice,
                spicyPineappleSlice,
                grilledPineappleSlice,
                goldenPineappleSlice,
                energizedPineappleSlice,
                energizedCrystal
        )

        registerRenders(
                *pineappleToolset.getItems(),
                energizedPickaxe,
                location =  "tools/"
        )
    }

    @JvmStatic
    private fun registerRenders(vararg items: Item, location: String = "") {
        items.forEach {
            val registryName = it.registryName!!
            ModelLoader.setCustomModelResourceLocation(it, 0,
                    ModelResourceLocation("${registryName.resourceDomain}:$location${registryName.resourcePath}", "inventory"))
        }
    }

    fun init() {}
}