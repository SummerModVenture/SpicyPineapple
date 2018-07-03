package net.masterzach32.spicypineapple.gen.structure

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.gen.ShrineLocData
import net.masterzach32.spicypineapple.network.ShrineLocUpdateMessage
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class PineappleShrine : Structure("pineappleshrine") {

    override fun generate(world: World, rand: Random, pos: BlockPos): Boolean {
        super.generate(world, rand, pos.down(3))
        ShrineLocData.getForWorld(world).addShrineLocation(pos)
        SpicyPineappleMod.NETWORK.sendToAll(ShrineLocUpdateMessage(ShrineLocUpdateMessage.Action.ADD, pos))
        return true
    }
}