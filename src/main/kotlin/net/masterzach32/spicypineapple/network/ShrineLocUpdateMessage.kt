package net.masterzach32.spicypineapple.network

import io.netty.buffer.ByteBuf
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl.IMessage

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
data class ShrineLocUpdateMessage(var action: Action, var pos: BlockPos) : IMessage {

    constructor() : this(Action.UNKNOWN, BlockPos(0, 0, 0))

    override fun fromBytes(buf: ByteBuf) {
        pos = BlockPos.fromLong(buf.readLong())
        action = Action.values()[buf.readInt()]
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeLong(pos.toLong())
        buf.writeInt(action.ordinal)
    }

    enum class Action {
        ADD, REMOVE, UNKNOWN;
    }
}