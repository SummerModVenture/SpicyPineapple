package net.masterzach32.spicypineapple.item

import com.spicymemes.core.util.codename
import net.masterzach32.spicypineapple.registry.ModBlocks
import net.masterzach32.spicypineapple.tabs.SpicyPineappleTab
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.block.Block
import net.minecraft.block.BlockJukebox
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Blocks
import net.minecraft.init.SoundEvents
import net.minecraft.item.*
import net.minecraft.potion.Potion
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
class ItemPineappleSlice : ItemRecord("pineapple_slice", SoundEvents.RECORD_13) {

    init {
        maxStackSize = 64
        creativeTab = SpicyPineappleTab
        codename = "pineapple_slice"
        hasSubtypes = true
    }

    override fun getSubItems(tab: CreativeTabs, items: NonNullList<ItemStack>) {
        if (isInCreativeTab(tab)) {
            items.addAll(Type.values().map { ItemStack(this, 1, it.ordinal) })
        }
    }

    override fun getUnlocalizedName(stack: ItemStack): String = "item.pineapple_slice${Type.getTypeFromMeta(stack.metadata).prefix}"

    // RECORD CODE

    override fun onItemUse(
            player: EntityPlayer,
            world: World,
            pos: BlockPos,
            hand: EnumHand,
            facing: EnumFacing,
            hitX: Float,
            hitY: Float,
            hitZ: Float
    ): EnumActionResult {

        val state = world.getBlockState(pos)

        if (state.block == Blocks.JUKEBOX && !state.getValue(BlockJukebox.HAS_RECORD)) {
            if (!world.isRemote) {
                val stack = player.getHeldItem(hand)
                (state.block as BlockJukebox).insertRecord(world, pos, state, ItemStack(stack.item))
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

        return if (player.canEat(stack.alwaysEdible)) {
            player.activeHand = hand
            ActionResult(EnumActionResult.SUCCESS, stack)
        } else
            ActionResult(EnumActionResult.FAIL, stack)
    }

    override fun onItemUseFinish(stack: ItemStack, world: World, player: EntityLivingBase): ItemStack {
        if (player is EntityPlayer) {
            player.foodStats.addStats(stack.hungerFilled, stack.saturation)
            world.playSound(
                    null,
                    player.posX,
                    player.posY,
                    player.posZ,
                    SoundEvents.ENTITY_PLAYER_BURP,
                    SoundCategory.PLAYERS,
                    0.5f,
                    world.rand.nextFloat() * 0.1f + 0.9f

            )

            if (!world.isRemote && stack.potionEffect != null)
                player.addPotionEffect(stack.potionEffect!!)
            player.addStat(StatList.getObjectUseStats(this)!!)

            if (player is EntityPlayerMP)
                CriteriaTriggers.CONSUME_ITEM.trigger(player, stack)
        }
        stack.shrink(1)
        return stack
    }

    override fun getMaxItemUseDuration(stack: ItemStack): Int = USE_DURATION

    override fun getItemUseAction(stack: ItemStack): EnumAction = EnumAction.EAT

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack, world: World?, tooltip: MutableList<String>, flag: ITooltipFlag) {}

    private val ItemStack.alwaysEdible: Boolean
        get() = Type.getTypeFromMeta(metadata).alwaysEdible

    private val ItemStack.hungerFilled: Int
        get() = Type.getTypeFromMeta(metadata).hungerFilled

    private val ItemStack.saturation: Float
        get() = Type.getTypeFromMeta(metadata).saturation

    private val ItemStack.potionEffect: PotionEffect?
        get() = Type.getTypeFromMeta(metadata).potionEffect

    companion object {
        const val USE_DURATION = 32
    }

    enum class Type(
            val serializedName: String,
            val rarity: Int,
            val hungerFilled: Int,
            val saturation: Float,
            val alwaysEdible: Boolean = false,
            val potionEffect: PotionEffect? = null
    ) : IStringSerializable {
        NORMAL("", 1, 2, 0.25F),
        SPICY("spicy", 4, 3, 0.5F),
        CRYSTALIZED("crystal", 8, 6, 1.0F, true, PotionEffect(Potion.getPotionFromResourceLocation("regeneration")!!, 150, 1)),
        NORMAL_GRILLED("grilled", 1, 4, 0.75F),
        SPICY_GRILLED("spicy_grilled", 4, 6, 1.5F);

        val prefix: String
            get() = if (serializedName.isEmpty()) "" else "_$serializedName"

        override fun getName(): String = serializedName

        companion object {
            fun getTypeFromBlock(block: Block): Type? {
                return when (block) {
                    ModBlocks.pineappleBlock -> NORMAL
                    ModBlocks.pineappleBlockSpicy -> SPICY
                    ModBlocks.pineappleBlockCrystalized -> CRYSTALIZED
                    else -> null
                }
            }

            fun getTypeFromMeta(meta: Int): Type {
                return values()[meta]
            }
        }
    }
}