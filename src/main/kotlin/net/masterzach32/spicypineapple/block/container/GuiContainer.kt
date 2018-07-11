package net.masterzach32.spicypineapple.block.container

import net.masterzach32.spicypineapple.gui.slots.IVanillaSlotSet
import net.masterzach32.spicypineapple.gui.slots.Slots
import net.masterzach32.spicypineapple.gui.slots.Slots.MOD_FIRST_SLOT_INDEX
import net.masterzach32.spicypineapple.gui.slots.Slots.PLAYER_INVENTORY_FIRST_SLOT_INDEX
import net.masterzach32.spicypineapple.gui.slots.Slots.VANILLA_FIRST_SLOT_INDEX
import net.masterzach32.spicypineapple.gui.slots.Slots.isPlayerInventory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class GuiContainer(
        protected val playerInventory: InventoryPlayer,
        protected val tileInventory: IInventory,
        vararg slotSets: Slots.SlotSet
) : Container() {

    init {
        var vanillaSlotIndex = 0
        var modSlotIndex = 0
        for (slotSet in slotSets) {
            val slots: Array<Slot>
            if (slotSet is IVanillaSlotSet) {
                slots = slotSet.buildSlots(vanillaSlotIndex)
                vanillaSlotIndex += slotSet.numSlots
            } else {
                slots = slotSet.buildSlots(modSlotIndex)
                modSlotIndex = slotSet.numSlots
            }
            slots.forEach { addSlotToContainer(it) }
        }
    }

    private var cachedFields: IntArray? = null

    override fun canInteractWith(playerIn: EntityPlayer): Boolean {
        return tileInventory.isUsableByPlayer(playerIn)
    }

    override fun onContainerClosed(playerIn: EntityPlayer) {
        super.onContainerClosed(playerIn)
        tileInventory.closeInventory(playerIn)
    }

    abstract fun onPlayerToContainer(player: EntityPlayer, sourceStack: ItemStack, index: Int): ItemStack?
    abstract fun onContainerToPlayer(player: EntityPlayer, sourceStack: ItemStack, index: Int): ItemStack?

    override fun transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack {
        val sourceSlot = inventorySlots[index]
        if (sourceSlot == null || !sourceSlot.hasStack) return ItemStack.EMPTY
        val sourceStack = sourceSlot.stack
        val copyOfSourceStack = sourceStack.copy()

        if (isPlayerInventory(index)) {
            val stack = onPlayerToContainer(playerIn, sourceStack, index)
            if (stack != null) return stack
        } else {
            val stack = onContainerToPlayer(playerIn, sourceStack, index)
            if (stack != null) return stack
        }

        if (sourceStack.count == 0) {
            sourceSlot.putStack(ItemStack.EMPTY)
        } else {
            sourceSlot.onSlotChanged()
        }

        sourceSlot.onTake(playerIn, sourceStack)
        return copyOfSourceStack
    }

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()

        if (tileInventory.fieldCount > 0) {
            var allFieldsHaveChanged = false
            val fieldHasChanged = BooleanArray(tileInventory.fieldCount) { false }
            if (cachedFields == null) {
                cachedFields = IntArray(tileInventory.fieldCount)
                allFieldsHaveChanged = true
            }
            val cachedFields = this.cachedFields!! //force smart cast
            for (i in 0 until cachedFields.size) {
                if (allFieldsHaveChanged || cachedFields[i] != tileInventory.getField(i)) {
                    cachedFields[i] = tileInventory.getField(i)
                    fieldHasChanged[i] = true
                }
            }

            listeners.forEach {
                (0 until cachedFields.size)
                        .filter { fieldHasChanged[it] }
                        .forEach { i -> it.sendWindowProperty(this, i, cachedFields[i]) }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    override fun updateProgressBar(id: Int, data: Int) {
        tileInventory.setField(id, data)
    }

    /**
     * Returns true if successful
     */
    protected fun mergeWithHotbar(source: ItemStack): Boolean {
        return mergeItemStack(source, VANILLA_FIRST_SLOT_INDEX, PLAYER_INVENTORY_FIRST_SLOT_INDEX, false)
    }

    /**
     * Returns true if successful
     */
    protected fun mergeWithPlayerInventory(source: ItemStack): Boolean {
        return mergeItemStack(source, PLAYER_INVENTORY_FIRST_SLOT_INDEX, MOD_FIRST_SLOT_INDEX, false)
    }
}