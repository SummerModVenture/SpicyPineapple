package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.block.BlockJukebox
import net.minecraft.client.Minecraft
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Blocks
import net.minecraft.init.Items
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
class ItemPineappleSlice(hungerFilled: Int, saturation: Double, name: String?, soundIn: SoundEvent?) : ItemRecord(name, soundIn) {

    /** Number of ticks to run while 'EnumAction'ing until result.  */
    var itemUseDuration: Int
    /** The amount this food item heals the player.  */
    private var healAmount: Int
    private var saturationModifier: Float
    /** Whether wolves like this food (true for raw and cooked porkchop).  */
    private var isWolfsFavoriteMeat: Boolean = false
    /** If this field is true, the food can be consumed even if the player don't need to eat.  */
    private var alwaysEdible: Boolean = false
    /** represents the potion effect that will occurr upon eating this food. Set by setPotionEffect  */
    private var potionId: PotionEffect? = null
    /** probably of the set potion effect occurring  */
    private var potionEffectProbability: Float = 0.toFloat()

    init {
        creativeTab = SpicyPineappleTab
        itemUseDuration = 32
        healAmount = hungerFilled
        saturationModifier = saturation.toFloat()
        maxStackSize = 64
    }

    override fun onItemUseFinish(stack: ItemStack, worldIn: World, entityLiving: EntityLivingBase): ItemStack {
        if (entityLiving is EntityPlayer) {
            entityLiving.foodStats.addStats(healAmount, saturationModifier)
            worldIn.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5f, worldIn.rand.nextFloat() * 0.1f + 0.9f)
            this.onFoodEaten(stack, worldIn, entityLiving)
            entityLiving.addStat(StatList.getObjectUseStats(this)!!)
        }

        stack.shrink(1)
        return stack
    }

    protected fun onFoodEaten(stack: ItemStack, worldIn: World, player: EntityPlayer) {
        if (!worldIn.isRemote && this.potionId != null && worldIn.rand.nextFloat() < this.potionEffectProbability) {
            player.addPotionEffect(PotionEffect(this.potionId))
        }
    }

    override fun onItemUse(player: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val iblockstate = worldIn.getBlockState(pos)

        if (iblockstate.block === Blocks.JUKEBOX && !(iblockstate.getValue(BlockJukebox.HAS_RECORD) as Boolean)) {
            if (!worldIn.isRemote) {
                val itemstack = player.getHeldItem(hand)
                (Blocks.JUKEBOX as BlockJukebox).insertRecord(worldIn, pos, iblockstate, ItemStack(this, 1, itemstack.itemDamage))
                worldIn.playEvent(null, 1010, pos, Item.getIdFromItem(this))
                itemstack.shrink(1)
                player.addStat(StatList.RECORD_PLAYED)
            }

            return EnumActionResult.SUCCESS
        } else {
            return EnumActionResult.PASS
        }
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        //tooltip.add(this.recordNameLocal)
    }

    /**
     * How long it takes to use or consume an item
     */
    override fun getMaxItemUseDuration(stack: ItemStack): Int {
        return itemUseDuration
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    override fun getItemUseAction(stack: ItemStack): EnumAction {
        return EnumAction.EAT
    }

    /**
     * Called when the equipped item is right clicked.
     */
    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        val itemstack = playerIn.getHeldItem(handIn)

        if (playerIn.canEat(this.alwaysEdible)) {
            playerIn.activeHand = handIn
            return ActionResult(EnumActionResult.SUCCESS, itemstack)
        } else {
            return ActionResult(EnumActionResult.FAIL, itemstack)
        }
    }

    fun getHealAmount(stack: ItemStack): Int {
        return this.healAmount
    }

    fun getSaturationModifier(stack: ItemStack): Float {
        return this.saturationModifier
    }

    /**
     * Whether wolves like this food (true for raw and cooked porkchop).
     */
    fun isWolfsFavoriteMeat(): Boolean {
        return this.isWolfsFavoriteMeat
    }

    fun setPotionEffect(effect: PotionEffect, probability: Float): ItemPineappleSlice {
        this.potionId = effect
        this.potionEffectProbability = probability
        return this
    }

    /**
     * Set the field 'alwaysEdible' to true, and make the food edible even if the player don't need to eat.
     */
    fun setAlwaysEdible(): ItemPineappleSlice {
        this.alwaysEdible = true
        return this
    }

}