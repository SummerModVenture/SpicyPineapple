package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.util.setCodename
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.block.BlockJukebox
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Blocks
import net.minecraft.init.SoundEvents
import net.minecraft.item.*
import net.minecraft.potion.PotionEffect
import net.minecraft.stats.StatList
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/*
 * SpicyPineapple - Created on 5/29/2018
 * Author: Zach Kozar
 *
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 5/29/2018
 */
class ItemPineappleSlice(name: String, val hungerFilled: Int, val saturation: Double, val alwaysEdible: Boolean = false,
                         var potionEffect: PotionEffect? = null, sound: SoundEvent = SoundEvents.RECORD_13)
    : ItemRecord(name, sound) {

    companion object {
        const val itemUseDuration = 32
    }

    init {
        maxStackSize = 64
        creativeTab = SpicyPineappleTab
        setCodename(name)
    }

    // RECORD CODE

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand,
                           facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val blockState = world.getBlockState(pos)

        if (blockState.block == Blocks.JUKEBOX && !blockState.getValue(BlockJukebox.HAS_RECORD)) {
            if (!world.isRemote) {
                val stack = player.getHeldItem(hand)
                (Blocks.JUKEBOX as BlockJukebox).insertRecord(world, pos, blockState, ItemStack(stack.item))
                world.playEvent(null, 1010, pos, Item.getIdFromItem(this))
                stack.shrink(1)
                player.addStat(StatList.RECORD_PLAYED)
            }
            return EnumActionResult.SUCCESS
        }
        return EnumActionResult.PASS
    }

    // FOOD CODE

    override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)

        return if (player.canEat(alwaysEdible)) {
            player.activeHand = hand
            ActionResult(EnumActionResult.SUCCESS, stack)
        } else
            ActionResult(EnumActionResult.FAIL, stack)
    }

    override fun onItemUseFinish(stack: ItemStack, world: World, player: EntityLivingBase): ItemStack {
        if (player is EntityPlayer) {
            player.foodStats.addStats(hungerFilled, saturation.toFloat())
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP,
                    SoundCategory.PLAYERS, 0.5f, world.rand.nextFloat() * 0.1f + 0.9f)
            if (!world.isRemote && potionEffect != null)
                player.addPotionEffect(potionEffect!!)
            player.addStat(StatList.getObjectUseStats(this)!!)

            if (player is EntityPlayerMP)
                CriteriaTriggers.CONSUME_ITEM.trigger(player, stack)
        }
        stack.shrink(1)
        return stack
    }

    override fun getMaxItemUseDuration(stack: ItemStack): Int = itemUseDuration

    override fun getItemUseAction(stack: ItemStack): EnumAction = EnumAction.EAT

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {}
}