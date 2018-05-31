package net.masterzach32.spicypineapple.network

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.gen.PineappleShrineGenerator
import net.masterzach32.spicypineapple.gen.ShrineSaveData
import net.minecraft.entity.player.EntityPlayerMP
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
class ShrineLocServerHandler : IMessageHandler<ShrineLocUpdateMessage, IMessage> {

    companion object {
        val updates = mutableListOf<ShrineLocUpdateMessage>()

        @JvmStatic
        @SubscribeEvent
        fun onPlayerJoinServer(event: PlayerEvent.PlayerLoggedInEvent) {
            val shrineData = ShrineSaveData.getForWorld(event.player.world)

            SpicyPineappleMod.logger.info("Player joined world, updating their list of shrine locations...")
            shrineData.map.forEach {
                SpicyPineappleMod.logger.info("Sending location: $it")
                SpicyPineappleMod.NETWORK.sendTo(ShrineLocUpdateMessage(ShrineLocUpdateMessage.Action.ADD, it), event.player as EntityPlayerMP)
            }
        }

        @JvmStatic
        @SubscribeEvent
        fun onServerTick(event: TickEvent.ServerTickEvent) {
            if (event.phase == TickEvent.Phase.START) {
                while (updates.size > 0) {
                    val update = updates.removeAt(0)

                    if (update.action == ShrineLocUpdateMessage.Action.REMOVE) {
                        SpicyPineappleMod.logger.info("Player found shrine at ${update.pos}, removing from server list.")
                        // TODO somehow get world instance here
                    }
                }
            }
        }
    }

    override fun onMessage(message: ShrineLocUpdateMessage, ctx: MessageContext): IMessage? {
        SpicyPineappleMod.logger.info("Server received message: $message")
        updates.add(message)
        return null
    }
}