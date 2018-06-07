package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.entity.EntityHealArea
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World

class ItemHealingStaff : Item() {

    init {
        creativeTab = SpicyPineappleTab
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        if (!world.isRemote)
            println("spawning entity: " + world.spawnEntity(EntityHealArea(world, player)))
        return super.onItemRightClick(world, player, hand)
    }
}