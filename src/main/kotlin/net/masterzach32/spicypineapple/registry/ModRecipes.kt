package net.masterzach32.spicypineapple.registry

import com.spicymemes.core.recipe.RecipesRegistryManager
import net.masterzach32.spicypineapple.EnumPineappleType
import net.masterzach32.spicypineapple.LOGGER
import net.masterzach32.spicypineapple.util.ifModLoaded
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.registry.GameRegistry

object ModRecipes {

    fun init() {
        LOGGER.info("Registering recipes.")

        GameRegistry.addSmelting(ModItems.pineappleSlice, ItemStack(ModItems.grilledPineappleSlice), 1.0F)
    }

    fun initModRecipes() {
        LOGGER.info("Registering recipes with other mods.")

        ifModLoaded("spicytech") {
            val reg = RecipesRegistryManager.getRegistry("spicytech", "crusher")
            if (reg != null) {
                LOGGER.info("Registering crusher recipes for SpicyTech.")
                reg.apply {
                    add(ModItems.pineappleSlice, ItemStack(ModItems.essence, 2, EnumPineappleType.NORMAL.ordinal), 1.0f)
                    add(ModItems.spicyPineappleSlice, ItemStack(ModItems.essence, 2, EnumPineappleType.SPICY.ordinal), 1.0f)
                    add(ModItems.crystalPineappleSlice, ItemStack(ModItems.essence, 2, EnumPineappleType.CRYSTALIZED.ordinal), 2.0f)
                }
            } else {
                LOGGER.warn("Could not load SpicyTech crusher registry.")
            }
        }
    }
}