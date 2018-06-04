package net.masterzach32.spicypineapple.item

import net.masterzach32.spicypineapple.dsl.setCodename
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.*

/*
 * SpicyPineapple - Created on 5/23/2018
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 5/23/2018
 */
class Toolset(val name: String, val material: Item.ToolMaterial, val creativeTab: CreativeTabs) {

    private val tools = mutableListOf<Item>()

    init {
        tools.addAll(listOf(
                ItemSword(material).setCreativeTab(creativeTab).setCodename("${name}_sword"),
                ItemBasicPickaxe(material).setCreativeTab(creativeTab).setCodename("${name}_pickaxe"),
                ItemBasicAxe(material).setCreativeTab(creativeTab).setCodename("${name}_axe"),
                ItemSpade(material).setCreativeTab(creativeTab).setCodename("${name}_shovel"),
                ItemHoe(material).setCreativeTab(creativeTab).setCodename("${name}_hoe")
        ))
    }

    fun getItems(): Array<Item> = tools.toTypedArray()

    class ItemBasicPickaxe(material: ToolMaterial) : ItemPickaxe(material)

    class ItemBasicAxe(material: ToolMaterial) : ItemAxe(material, material.attackDamage, -3.0f)
}