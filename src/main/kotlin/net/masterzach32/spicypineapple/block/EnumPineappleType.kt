package net.masterzach32.spicypineapple.block

import net.minecraft.util.IStringSerializable

enum class EnumPineappleType(val serializedName: String) : IStringSerializable {
    NORMAL("normal"),
    SPICY("spicy"),
    CRYSTALIZED("crystalized");

    override fun getName(): String = serializedName
}