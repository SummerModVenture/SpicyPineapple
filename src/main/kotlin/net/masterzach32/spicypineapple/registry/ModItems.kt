package net.masterzach32.spicypineapple.registry

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.dsl.setCodename
import net.masterzach32.spicypineapple.item.*
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

    val pineappleSlice = ItemPineappleSlice("pineapple_slice", 2, 0.25)
    val spicyPineappleSlice = ItemPineappleSlice("pineapple_slice_spicy", 3, 0.5)
    val grilledPineappleSlice = ItemPineappleSlice("pineapple_slice_grilled", 4, 1.0)
    val crystalPineappleSlice = ItemPineappleSlice("pineapple_slice_crystal", 6, 1.0, true,
            PotionEffect(Potion.getPotionFromResourceLocation("regeneration")!!, 150, 1))


    val energyCrystal = Item().setCreativeTab(SpicyPineappleTab).setCodename("energy_crystal")
    val lifeCrystal = Item().setCreativeTab(SpicyPineappleTab).setCodename("life_crystal")
    val fireCrystal = Item().setCreativeTab(SpicyPineappleTab).setCodename("fire_crystal")
    val earthCrystal = Item().setCreativeTab(SpicyPineappleTab).setCodename("earth_crystal")
    val waterCrystal = Item().setCreativeTab(SpicyPineappleTab).setCodename("water_crystal")
    val crystals = listOf(energyCrystal, lifeCrystal, fireCrystal, earthCrystal, waterCrystal)

    val pineappleToolset = Toolset("pineapple", ToolMaterialPineapple, SpicyPineappleTab)

    val energizedPickaxe = ItemEnergizedPickaxe().setCodename("energized_pickaxe")
    val energizedAxe = ItemEnergizedAxe().setCodename("energized_axe")

    val staff = ItemStaff().setCodename("staff")

    @JvmStatic
    @SubscribeEvent
    fun registerItemBlocks(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(
                pineappleSlice,
                spicyPineappleSlice,
                grilledPineappleSlice,
                crystalPineappleSlice,
                energyCrystal,
                lifeCrystal,
                fireCrystal,
                earthCrystal,
                waterCrystal,
                energizedPickaxe,
                energizedAxe,
                staff,
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
                crystalPineappleSlice,
                energyCrystal,
                lifeCrystal,
                fireCrystal,
                earthCrystal,
                waterCrystal,
                staff
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