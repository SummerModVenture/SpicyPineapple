package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemFood
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.world.World

class ItemPineappleSlice(hungerFilled: Int, saturation: Float) : ItemFood(hungerFilled, saturation, false) {

    var getTooltip: (MutableList<String>) -> Unit = {}

    init {
        creativeTab = SpicyPineappleTab
    }

    fun addTooltip(func: (MutableList<String>) -> Unit): ItemPineappleSlice {
        getTooltip = func
        return this
    }

    fun addRegen(duration: Int, strength: Int): ItemFood {
        return setPotionEffect(PotionEffect(Potion.getPotionFromResourceLocation("regeneration")!!, 30*duration, strength), 1F)
    }

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        getTooltip(tooltip)
    }
}