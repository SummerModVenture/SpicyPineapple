package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.dsl.setCodename
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
            .addRegen(5, 1).setCodename("pineapple_slice_golden")

    val godlyPineappleSlice = ItemPineappleSlice(10, 2F)
            .addRegen(5, 10).setCodename("pineapple_slice_godly")

    val pineappleToolset = Toolset("pineapple", ToolMaterialPineapple, SpicyPineappleTab)

    @JvmStatic
    @SubscribeEvent
    fun registerItemBlocks(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(
                pineappleSlice,
                spicyPineappleSlice,
                grilledPineappleSlice,
                goldenPineappleSlice,
                godlyPineappleSlice,
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
                godlyPineappleSlice
        )

        registerRenders(*pineappleToolset.getItems(), location =  "tools/")
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