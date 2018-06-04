package net.masterzach32.spicypineapple

import net.masterzach32.spicypineapple.registry.ModBlocks
import net.masterzach32.spicypineapple.registry.ModItems
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.IStringSerializable

enum class EnumPineappleType(val serializedName: String, val rarity: Int) : IStringSerializable {
    NORMAL("normal", 1),
    SPICY("spicy", 4),
    CRYSTALIZED("crystalized", 8);

    override fun getName(): String = serializedName

    companion object {
        fun getTypeFromBlock(block: Block): EnumPineappleType? {
            return when (block) {
                ModBlocks.pineappleBlock -> NORMAL
                ModBlocks.pineappleBlockSpicy -> SPICY
                ModBlocks.pineappleBlockCrystalized -> CRYSTALIZED
                else -> null
            }
        }
    }
}

enum class EnumCrystalType : IStringSerializable {
    LIFE,
    ENERGY,
    FIRE,
    EARTH,
    WATER;

    override fun getName(): String {
        return name.toLowerCase() + "_crystal"
    }

    companion object {
        fun getTypeFromItem(crystal: ItemStack): EnumCrystalType = values()[crystal.metadata]
    }
}