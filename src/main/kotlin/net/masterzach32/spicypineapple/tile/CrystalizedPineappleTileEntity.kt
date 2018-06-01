package net.masterzach32.spicypineapple.tile

import net.masterzach32.spicypineapple.client.EssenceParticle
import net.minecraft.client.Minecraft
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ITickable

/*
 * SpicyPineapple - Created on 6/1/2018
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 6/1/2018
 */
class CrystalizedPineappleTileEntity : TileEntity(), ITickable {

    var tick = 3

    override fun update() {
        if (world.isRemote) {
            tick--
            if (tick == 0) {
                val x = pos.x + 0.5
                val y = pos.y + 0.6
                val z = pos.z + 0.5
                Minecraft.getMinecraft().effectRenderer.addEffect(EssenceParticle(world, x, y, z))
                tick = 2 + world.rand.nextInt(3)
            }
        }
    }
}