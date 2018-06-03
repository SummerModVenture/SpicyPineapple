package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.dsl.distance
import net.masterzach32.spicypineapple.gen.ShrineLocData
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.*

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
                val r = Random()
                val dirVec = Vec3d(closestShrine.x - player.posX, closestShrine.y - player.posY, closestShrine.z - player.posZ).normalize()
                for (i in 0 until 20 - r.nextInt(4)) {
                    world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, player.posX + r.nextDouble()*1,
                            player.posY + r.nextDouble()*2, player.posZ + r.nextDouble()*1,
                            dirVec.x + Math.random()*rand-rand/2, dirVec.y + Math.random()*rand-rand/2 + .1,
                            dirVec.z + Math.random()*rand-rand/2)
                }
            }
        }
        return super.onItemRightClick(world, player, hand)
    }
}