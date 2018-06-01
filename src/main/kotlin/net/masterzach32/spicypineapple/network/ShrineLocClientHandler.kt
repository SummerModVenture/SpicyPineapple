package net.masterzach32.spicypineapple.network

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.gen.ShrineLocData
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

/*
 * SpicyPineapple - Created on 5/31/2018
 * Author: Zach Kozar
 * 
 * This code is licensed under the GNU GPL v3
 * You can find more info in the LICENSE file at the project root.
 */

/**
 * @author Zach Kozar
 * @version 5/31/2018
 */
class ShrineLocClientHandler : IMessageHandler<ShrineLocUpdateMessage, IMessage> {

    override fun onMessage(message: ShrineLocUpdateMessage, ctx: MessageContext): IMessage? {
        val world = Minecraft.getMinecraft().world
        if (world != null && message.action == ShrineLocUpdateMessage.Action.ADD) {
            Minecraft.getMinecraft().addScheduledTask {
                SpicyPineappleMod.logger.info("Received new shrine location from server: ${message.pos}")
                ShrineLocData.getForWorld(world).addShrineLocation(message.pos)
            }
        } else if (world != null && message.action == ShrineLocUpdateMessage.Action.REMOVE) {
            Minecraft.getMinecraft().addScheduledTask {
                SpicyPineappleMod.logger.info("Removing shrine location from local list: ${message.pos}")
                ShrineLocData.getForWorld(world).removeShrineLocation(message.pos)
            }
        }

        return null
    }
}