package net.masterzach32.spicypineapple.network

import ibxm.Player
import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.gen.PineappleShrineGenerator
import net.masterzach32.spicypineapple.gen.ShrineSaveData
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
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
@Mod.EventBusSubscriber(modid = SpicyPineappleMod.MOD_ID)
class ShrineLocClientHandler : IMessageHandler<ShrineLocUpdateMessage, IMessage> {

    companion object {
        val updates = mutableListOf<ShrineLocUpdateMessage>()

        @JvmStatic
        @SubscribeEvent
        fun onClientTick(event: TickEvent.ClientTickEvent) {
            if (event.phase == TickEvent.Phase.START) {
                while (updates.size > 0) {
                    val update = updates.removeAt(0)
                    val shrineData = ShrineSaveData.getForWorld(Minecraft.getMinecraft().world)
                    if (update.action == ShrineLocUpdateMessage.Action.ADD) {
                        SpicyPineappleMod.logger.info("Received new shrine location from server: ${update.pos}")
                        shrineData.addShrineLocation(update.pos!!)
                    }
                }
            }
        }
    }

    override fun onMessage(message: ShrineLocUpdateMessage, ctx: MessageContext): IMessage? {
        SpicyPineappleMod.logger.info("Client received message: $message")
        updates.add(message)
        return null
    }
}