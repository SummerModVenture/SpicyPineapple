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
import net.minecraft.init.Blocks
import net.minecraft.item.ItemSeeds
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader

@Mod.EventBusSubscriber(modid = SpicyPineappleMod.MOD_ID)
object ModItems {

    val pineappleSlice = ItemPineappleSlice("pineapple_slice", 2, 0.25)
    val spicyPineappleSlice = ItemPineappleSlice("pineapple_slice_spicy", 3, 0.5)
    val grilledPineappleSlice = ItemPineappleSlice("pineapple_slice_grilled", 4, 1.0)
    val crystalPineappleSlice = ItemPineappleSlice("pineapple_slice_crystal", 6, 1.0, true,
            PotionEffect(Potion.getPotionFromResourceLocation("regeneration")!!, 150, 1))

    val pineappleSeed = ItemSeeds(ModBlocks.pineappleStem, Blocks.FARMLAND)
            .setCreativeTab(SpicyPineappleTab).setCodename("pineapple_seed")

    val crystal = ItemCrystal().setCodename("crystal")

    val pineappleToolset = Toolset("pineapple", ToolMaterialPineapple, SpicyPineappleTab)

    val energizedPickaxe = ItemEnergizedPickaxe().setCodename("energized_pickaxe")
    val energizedAxe = ItemEnergizedAxe().setCodename("energized_axe")

    val staff = ItemStaff().setCodename("staff")

    @JvmStatic
    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(
                pineappleSlice,
                spicyPineappleSlice,
                grilledPineappleSlice,
                crystalPineappleSlice,
                pineappleSeed,
                *pineappleToolset.getItems(),
                energizedPickaxe,
                energizedAxe,
                crystal,
                staff
        )
    }

    @JvmStatic
    @SubscribeEvent
    fun registerRenders(event: ModelRegistryEvent) {
        registerItemTexture(
                pineappleSlice,
                spicyPineappleSlice,
                grilledPineappleSlice,
                crystalPineappleSlice,
                pineappleSeed,
                crystal,
                staff
        )

        registerItemTexture(
                *pineappleToolset.getItems(),
                energizedPickaxe,
                energizedAxe,
                location =  "tools/"
        )
    }

    @JvmStatic
    private fun registerItemTexture(vararg items: Item, location: String = "") {
        fun setResourceLocation(item: Item, metadata: Int, registryName: ResourceLocation, location: String) {
            ModelLoader.setCustomModelResourceLocation(item, metadata,
                    ModelResourceLocation("${registryName.resourceDomain}:$location${registryName.resourcePath}", "inventory"))
        }

        items.forEach {
            if (it.hasSubtypes) {
                val stacks = NonNullList.create<ItemStack>()
                it.getSubItems(it.creativeTab!!, stacks)

                stacks.forEach { stack ->
                    setResourceLocation(it, stack.metadata, it.registryName!!, location)
                }
            } else {
                setResourceLocation(it, 0, it.registryName!!, location)
            }
        }
    }

    fun init() {}
}