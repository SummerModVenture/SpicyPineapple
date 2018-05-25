package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.dsl.setCodename
import net.masterzach32.spicypineapple.item.ItemEnergizedAxe
import net.masterzach32.spicypineapple.item.ItemEnergizedPickaxe
import net.masterzach32.spicypineapple.item.ToolMaterialPineapple
import net.masterzach32.spicypineapple.item.Toolset
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.item.Item
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.ItemFood
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader

@Mod.EventBusSubscriber(modid = SpicyPineappleMod.MOD_ID)
object ModItems {

    val pineappleSlice = ItemFood(2, 0.25F, false).setCreativeTab(SpicyPineappleTab).setCodename("pineapple_slice")
    val spicyPineappleSlice = ItemFood(3, .5F, false).setCreativeTab(SpicyPineappleTab).setCodename("pineapple_slice_spicy")
    val grilledPineappleSlice = ItemFood(4, 1F, false).setCreativeTab(SpicyPineappleTab).setCodename("pineapple_slice_grilled")
    val goldenPineappleSlice = ItemFood(6, 1F, false).setAlwaysEdible()
            .setPotionEffect(PotionEffect(Potion.getPotionFromResourceLocation("regeneration")!!, 150, 1), 1F)
            .setCreativeTab(SpicyPineappleTab).setCodename("pineapple_slice_golden")

    val energyCrystal = Item().setCreativeTab(SpicyPineappleTab).setCodename("energy_crystal")
    val lifeCrystal = Item().setCreativeTab(SpicyPineappleTab).setCodename("life_crystal")
    val fireCrystal = Item().setCreativeTab(SpicyPineappleTab).setCodename("fire_crystal")

    val pineappleToolset = Toolset("pineapple", ToolMaterialPineapple, SpicyPineappleTab)

    val energizedPickaxe = ItemEnergizedPickaxe().setCodename("energized_pickaxe")
    val energizedAxe = ItemEnergizedAxe().setCodename("energized_axe")

    @JvmStatic
    @SubscribeEvent
    fun registerItemBlocks(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(
                pineappleSlice,
                spicyPineappleSlice,
                grilledPineappleSlice,
                goldenPineappleSlice,
                energyCrystal,
                lifeCrystal,
                fireCrystal,
                energizedPickaxe,
                energizedAxe,
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
                energyCrystal,
                lifeCrystal,
                fireCrystal
        )

        registerRenders(
                *pineappleToolset.getItems(),
                energizedPickaxe,
                energizedAxe,
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