package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemFood
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.world.World

class ItemPineappleSlice(hungerFilled: Int, saturation: Float) : ItemFood(hungerFilled, saturation, false) {

    init {
        creativeTab = SpicyPineappleTab
        setAlwaysEdible()
    }

    fun addRegen(duration: Int, strength: Int): ItemFood {
        return setPotionEffect(PotionEffect(Potion.getPotionFromResourceLocation("regeneration")!!, 30*duration, strength), 1F)
    }
}