package net.masterzach32.spicypineapple.network

import com.spicymemes.core.network.GenericPacketHandler
import io.netty.buffer.ByteBuf
import net.masterzach32.spicypineapple.entity.EntityBlackHole
import net.masterzach32.spicypineapple.entity.EntityEarthRipper
import net.masterzach32.spicypineapple.logger
import net.minecraft.util.math.Vec2f
import net.minecraft.util.math.Vec3d
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class StaffActivatedPacket(var pos: Vec3d, var pitchYaw: Vec2f, var name: Int) : IMessage {

    constructor() : this(Vec3d.ZERO, Vec2f.ZERO, -1)

    override fun fromBytes(buf: ByteBuf) {
        pos = Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble())
        pitchYaw = Vec2f(buf.readFloat(), buf.readFloat())
        name = buf.readShort().toInt()
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeDouble(pos.x)
        buf.writeDouble(pos.y)
        buf.writeDouble(pos.z)
        buf.writeFloat(pitchYaw.x)
        buf.writeFloat(pitchYaw.y)
        buf.writeShort(name)
    }

    class Handler : GenericPacketHandler<StaffActivatedPacket>() {

        override fun processMessage(message: StaffActivatedPacket, ctx: MessageContext) {
            val player = ctx.serverHandler.player
            val world = player.world
            when (message.name) {
                0 -> {
                    val earth = EntityEarthRipper(world)
                    earth.setPosition(player.posX + 0.5, player.posY, player.posZ + 0.5)
                    earth.shoot(0.0, player.pitchYaw.y.toDouble(), 3.0)
                    world.spawnEntity(earth)
                }
                1 -> {
                    val death = EntityBlackHole(world, player)
                    death.setPosition(player.posX + 0.5, player.posY + 1.0, player.posZ + 0.5)
                    death.shoot(player.pitchYaw.x.toDouble(), player.pitchYaw.y.toDouble(), 0.4)
                    world.spawnEntity(death)
                }
            }
        }
    }
}