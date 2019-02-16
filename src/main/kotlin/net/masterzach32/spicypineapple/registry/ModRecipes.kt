package net.masterzach32.spicypineapple.registry

import com.spicymemes.core.recipe.RecipesRegistryManager
import net.masterzach32.spicypineapple.EnumPineappleType
import net.masterzach32.spicypineapple.logger
import net.masterzach32.spicypineapple.util.ifModLoaded
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.registry.GameRegistry

object ModRecipes {

    fun init() {
        logger.info("Registering recipes.")

        GameRegistry.addSmelting(ModItems.pineappleSlice, ItemStack(ModItems.grilledPineappleSlice), 1.0F)
        GameRegistry.addSmelting(ModItems.spicyPineappleSlice, ItemStack(ModItems.grilledSpicyPineappleSlice), 1.0F)

        //RecipesRegistryManager.addRegistry(MOD_ID, "crystal_forge", )
    }

    fun initModRecipes() {
        logger.info("Registering recipes with other mods.")

        ifModLoaded("spicytech") {
            val reg = RecipesRegistryManager.getRegistry("spicytech", "crusher")
            if (reg != null) {
                logger.info("Registering crusher recipes for SpicyTech.")
                reg.apply {
                    add(ModItems.pineappleSlice, ItemStack(ModItems.essence, 2, EnumPineappleType.NORMAL.ordinal), 1.0f)
                    add(ModItems.spicyPineappleSlice, ItemStack(ModItems.essence, 2, EnumPineappleType.SPICY.ordinal), 1.0f)
                    add(ModItems.crystalPineappleSlice, ItemStack(ModItems.essence, 2, EnumPineappleType.CRYSTALIZED.ordinal), 2.0f)
                }
            } else {
                logger.warn("Could not load SpicyTech crusher registry.")
            }
        }
    }
}