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

enum class EnumCrystalType(val color: Int) : IStringSerializable {
    LIFE(0x68ff9a),
    ENERGY(0xd67fff),
    FIRE(0xff4800),
    EARTH(0x179e00),
    WATER(0x14a8ff);

    override fun getName(): String {
        return name.toLowerCase() + "_crystal"
    }

    companion object {
        fun getTypeFromItem(crystal: ItemStack): EnumCrystalType = values()[crystal.metadata]
    }
}