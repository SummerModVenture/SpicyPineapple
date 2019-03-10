package net.masterzach32.spicypineapple.registry

import com.spicymemes.core.recipe.RecipesRegistryManager
import com.spicymemes.core.util.ifModLoaded
import net.masterzach32.spicypineapple.item.ItemPineappleSlice
import net.masterzach32.spicypineapple.logger
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.registry.GameRegistry

object ModRecipes {

    fun init() {
        logger.info("Registering recipes.")

        GameRegistry.addSmelting(ModItems.pineappleSlice, ItemStack(ModItems.pineappleSlice, 1, ItemPineappleSlice.Type.NORMAL_GRILLED.ordinal), 1.0F)
        GameRegistry.addSmelting(ModItems.pineappleSlice, ItemStack(ModItems.pineappleSlice, 1, ItemPineappleSlice.Type.SPICY_GRILLED.ordinal), 1.0F)

        //RecipesRegistryManager.addRegistry(MOD_ID, "crystal_forge", )
    }

    fun initModRecipes() {
        logger.info("Registering recipes with other mods.")

        ifModLoaded("spicytech") {
            val reg = RecipesRegistryManager.getRegistry("spicytech", "crusher")
            if (reg != null) {
                logger.info("Registering crusher recipes for SpicyTech.")
                reg.apply {
                    add(
                            ItemStack(ModItems.pineappleSlice, 1, ItemPineappleSlice.Type.NORMAL.ordinal),
                            ItemStack(ModItems.essence, 2, ItemPineappleSlice.Type.NORMAL.ordinal),
                            1.0f
                    )
                    add(
                            ItemStack(ModItems.pineappleSlice, 1, ItemPineappleSlice.Type.SPICY.ordinal),
                            ItemStack(ModItems.essence, 2, ItemPineappleSlice.Type.SPICY.ordinal),
                            1.0f
                    )
                    add(
                            ItemStack(ModItems.pineappleSlice, 1, ItemPineappleSlice.Type.CRYSTALIZED.ordinal),
                            ItemStack(ModItems.essence, 2, ItemPineappleSlice.Type.CRYSTALIZED.ordinal),
                            1.0f
                    )
                }
            } else {
                logger.warn("Could not load SpicyTech crusher registry.")
            }
        }
    }
}