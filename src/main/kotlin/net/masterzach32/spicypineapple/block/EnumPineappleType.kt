package net.masterzach32.spicypineapple.block

import net.masterzach32.spicypineapple.registry.ModBlocks
import net.minecraft.block.Block
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