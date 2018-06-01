package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.dsl.distance
import net.masterzach32.spicypineapple.gen.ShrineLocData
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumAction
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i
import net.minecraft.util.text.TextComponentString
import net.minecraft.world.World

class ItemStaff : Item() {

    companion object {
        const val rand = 0.1
    }

    init {
        creativeTab = SpicyPineappleTab
    }

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        if (world.isRemote) {
            val closestShrine = ShrineLocData.getForWorld(world).getClosestShrine(player.position)
            if (closestShrine != null && closestShrine.distance(player.position) < 400) {
                val dirVec = Vec3d(closestShrine.x - player.posX, closestShrine.y - player.posY, closestShrine.z - player.posZ).normalize()
                for (i in 1..6) {
                    world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, player.posX, player.posY + player.eyeHeight - .1, player.posZ,
                            dirVec.x + Math.random()*rand-rand/2, dirVec.y + Math.random()*rand-rand/2 + .1, dirVec.z + Math.random()*rand-rand/2)
                }
            }
        }
        return super.onItemRightClick(world, player, hand)
    }
}