package net.masterzach32.spicypineapple.network

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.gen.ShrineLocData
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent
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
        @JvmStatic
        @SubscribeEvent
        fun onPlayerJoinServer(event: PlayerEvent.PlayerLoggedInEvent) {
            val shrineData = ShrineLocData.getForWorld(event.player.world)

            if (shrineData.map.isNotEmpty())
                SpicyPineappleMod.logger.info("Player joined world, updating their list of shrine locations...")
            shrineData.map.forEach {
                SpicyPineappleMod.logger.info("Sending location: $it")
                SpicyPineappleMod.NETWORK.sendTo(ShrineLocUpdateMessage(ShrineLocUpdateMessage.Action.ADD, it), event.player as EntityPlayerMP)
            }
        }
    }

    override fun onMessage(message: ShrineLocUpdateMessage, ctx: MessageContext): IMessage? {
        SpicyPineappleMod.logger.info("Server received message: $message")

        val world = ctx.serverHandler.player.serverWorld

        if (message.action == ShrineLocUpdateMessage.Action.REMOVE) {
            world.addScheduledTask {
                SpicyPineappleMod.logger.info("Player found shrine at ${message.pos}, removing from server list.")
                ShrineLocData.getForWorld(world).removeShrineLocation(message.pos)
            }
        }

        return null
    }
}