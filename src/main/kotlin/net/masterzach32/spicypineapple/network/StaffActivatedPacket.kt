package net.masterzach32.spicypineapple.network

import com.spicymemes.core.network.GenericPacketHandler
import io.netty.buffer.ByteBuf
import net.masterzach32.spicypineapple.entity.EntityBlackHole
import net.masterzach32.spicypineapple.entity.EntityEarthRipper
import net.minecraft.util.math.Vec3d
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class StaffActivatedPacket(var pos: Vec3d, var pitch: Double, var yaw: Double, var name: Int) : IMessage {

    constructor(pos: Vec3d, pitch: Float, yaw: Float, name: Int) : this(pos, pitch.toDouble(), yaw.toDouble(), name)

    constructor() : this(Vec3d.ZERO, 0.0, 0.0, -1)

    override fun fromBytes(buf: ByteBuf) {
        pos = Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble())
        pitch = buf.readDouble()
        yaw = buf.readDouble()
        name = buf.readShort().toInt()
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeDouble(pos.x)
        buf.writeDouble(pos.y)
        buf.writeDouble(pos.z)
        buf.writeDouble(pitch)
        buf.writeDouble(yaw)
        buf.writeShort(name)
    }

    class Handler : GenericPacketHandler<StaffActivatedPacket>() {

        override fun processMessage(message: StaffActivatedPacket, ctx: MessageContext) {
            val player = ctx.serverHandler.player
            val world = player.world
            when (message.name) {
                0 -> {
                    val earth = EntityEarthRipper(world)
                    earth.setPosition(message.pos.x + 0.5, message.pos.y, message.pos.z + 0.5)
                    earth.shoot(0.0, message.yaw, 3.0)
                    world.spawnEntity(earth)
                }
                1 -> {
                    val death = EntityBlackHole(world, player)
                    death.setPosition(message.pos.x + 0.5, message.pos.y, message.pos.z + 0.5)
                    death.shoot(message.pitch, message.yaw, 0.4)
                    world.spawnEntity(death)
                }
            }
        }
    }
}