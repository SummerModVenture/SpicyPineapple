package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.entity.EntityTest
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.EnumParticleTypes
import net.minecraft.world.World

class ItemStaff : Item() {

    init {
        creativeTab = SpicyPineappleTab
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        if (!world.isRemote) {
            val entity = EntityTest(world, player)

            entity.shoot(player.posX, player.posY + player.eyeHeight - 0.10000000149011612, player.posZ, 2.0f, 0.1f)

            world.spawnEntity(entity)
        }
        if (world.isRemote)
            world.spawnParticle(EnumParticleTypes.REDSTONE, player.posX, player.posY + player.eyeHeight, player.posZ, 0.0, 0.2, 0.0)
        return super.onItemRightClick(world, player, hand)
    }
}