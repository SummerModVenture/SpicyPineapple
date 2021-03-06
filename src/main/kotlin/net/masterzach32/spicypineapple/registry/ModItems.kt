package net.masterzach32.spicypineapple.registry

import com.spicymemes.core.util.setCodename
import net.masterzach32.spicypineapple.logger
import net.masterzach32.spicypineapple.MOD_ID
import net.masterzach32.spicypineapple.item.*
import net.masterzach32.spicypineapple.item.magic.ItemDeathStaff
import net.masterzach32.spicypineapple.item.magic.ItemEarthStaff
import net.masterzach32.spicypineapple.item.magic.ItemHealingStaff
import net.masterzach32.spicypineapple.item.magic.ItemStaff
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.item.Item
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.init.Blocks
import net.minecraft.item.ItemSeeds
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.oredict.OreDictionary

@Mod.EventBusSubscriber(modid = MOD_ID)
object ModItems {

    val pineappleSlice = ItemPineappleSlice()

    val pineappleSeed = ItemSeeds(ModBlocks.pineappleStem, Blocks.FARMLAND)
            .setCreativeTab(SpicyPineappleTab).setCodename("pineapple_seed")
    val spicyPineappleSeed = ItemSeeds(ModBlocks.spicyPineappleStem, Blocks.FARMLAND)
            .setCreativeTab(SpicyPineappleTab).setCodename("pineapple_seed_spicy")
    val crystalPineappleSeed = ItemSeeds(ModBlocks.crystalizedPineappleStem, Blocks.FARMLAND)
            .setCreativeTab(SpicyPineappleTab).setCodename("pineapple_seed_crystal")

    val crystal = ItemCrystal().setCodename("crystal")
    val essence = ItemEssence().setCodename("essence")

    val pineappleToolset = Toolset("pineapple", ToolMaterialPineapple, SpicyPineappleTab)

    val energizedPickaxe = ItemEnergizedPickaxe().setCodename("energized_pickaxe")
    val energizedAxe = ItemEnergizedAxe().setCodename("energized_axe")
    val energizedHoe = ItemEnergizedHoe().setCodename("energized_hoe")
    val energizedShovel = ItemEnergizedShovel().setCodename("energized_shovel")

    val staffRod = Item().setCreativeTab(SpicyPineappleTab).setCodename("staff_rod")

    val staff = ItemStaff().setCodename("staff")
    val healingStaff = ItemHealingStaff().setCodename("healing_staff")
    val earthStaff = ItemEarthStaff().setCodename("earth_staff")
    val deathStaff = ItemDeathStaff().setCodename("death_staff")

    @JvmStatic
    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(
                pineappleSlice,
                pineappleSeed,
                spicyPineappleSeed,
                crystalPineappleSeed,
                *pineappleToolset.getItems(),
                energizedPickaxe,
                energizedAxe,
                energizedHoe,
                energizedShovel,
                crystal,
                essence,
                staffRod,
                staff,
                healingStaff,
                earthStaff,
                deathStaff
        )

        ItemPineappleSlice.Type.values()
                .sliceArray(0..1)
                .forEach {
                    OreDictionary.registerOre("itemPineapple", ItemStack(pineappleSlice, 1, it.ordinal))
                    OreDictionary.registerOre("foodPineapple", ItemStack(pineappleSlice, 1, it.ordinal))
                }
        ItemPineappleSlice.Type.values()
                .sliceArray(3..4)
                .forEach {
                    OreDictionary.registerOre("itemGrilledPineapple", ItemStack(pineappleSlice, 1, it.ordinal))
                    OreDictionary.registerOre("itemPineappleGrilled", ItemStack(pineappleSlice, 1, it.ordinal))
                    OreDictionary.registerOre("foodGrilledPineapple", ItemStack(pineappleSlice, 1, it.ordinal))
                    OreDictionary.registerOre("foodPineappleGrilled", ItemStack(pineappleSlice, 1, it.ordinal))
                }


        OreDictionary.registerOre("itemCrystalPineapple", ItemStack(pineappleSlice, 1, ItemPineappleSlice.Type.CRYSTALIZED.ordinal))
    }

    @JvmStatic
    @SubscribeEvent
    fun registerRenders(event: ModelRegistryEvent) {
        registerItemTexture(pineappleSlice, useUnlocalizedName = true)

        registerItemTexture(
                pineappleSeed,
                spicyPineappleSeed,
                crystalPineappleSeed,
                crystal,
                essence,
                staffRod,
                staff,
                healingStaff,
                earthStaff,
                deathStaff
        )

        registerItemTexture(
                *pineappleToolset.getItems(),
                energizedPickaxe,
                energizedAxe,
                energizedHoe,
                energizedShovel,
                location =  "tools/"
        )
    }

    @JvmStatic
    private fun registerItemTexture(vararg items: Item, useUnlocalizedName: Boolean = false, location: String = "") {
        fun setResourceLocation(item: Item, metadata: Int, registryName: ResourceLocation, location: String) {
            ModelLoader.setCustomModelResourceLocation(item, metadata,
                    ModelResourceLocation("${registryName.resourceDomain}:$location${registryName.resourcePath}", "inventory"))
        }

        items.forEach {
            if (it.hasSubtypes) {
                val stacks = NonNullList.create<ItemStack>()
                it.getSubItems(it.creativeTab!!, stacks)

                stacks.forEach { stack ->
                    if (useUnlocalizedName)
                        setResourceLocation(it, stack.metadata, ResourceLocation("spicypineapple", it.getUnlocalizedName(stack).drop(5)), location)
                    else
                        setResourceLocation(it, stack.metadata, it.registryName!!, location)
                }
            } else {
                setResourceLocation(it, 0, it.registryName!!, location)
            }
        }
    }

    fun init() {
        logger.info("Loading items.")
    }
}