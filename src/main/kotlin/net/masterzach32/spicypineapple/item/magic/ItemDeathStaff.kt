package net.masterzach32.spicypineapple.item.magic

import net.masterzach32.spicypineapple.entity.EntityBlackHole
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumAction
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World

class ItemDeathStaff : Item() {

    init {
        creativeTab = SpicyPineappleTab
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        return if (player.cooldownTracker.getCooldown(this, 0f) == 0f) {
            player.activeHand = hand
            ActionResult(EnumActionResult.SUCCESS, stack)
        } else
            ActionResult(EnumActionResult.FAIL, stack)
    }

    override fun onItemUseFinish(stack: ItemStack, world: World, player: EntityLivingBase): ItemStack {
        if (!world.isRemote && player is EntityPlayer) {
            val death = EntityBlackHole(world, player)
            death.setPosition(player.posX + 0.5, player.posY + 1.0, player.posZ + 0.5)
            death.shoot(player.pitchYaw.x.toDouble(), player.pitchYaw.y.toDouble(), 0.4)
            world.spawnEntity(death)
        }
        return super.onItemUseFinish(stack, world, player)
    }

    override fun getMaxItemUseDuration(stack: ItemStack): Int = CHARGE_TIME

    override fun getItemUseAction(stack: ItemStack): EnumAction = EnumAction.BOW

    companion object {
        const val CHARGE_TIME = 32
        const val COOLDOWN = 250
    }
}