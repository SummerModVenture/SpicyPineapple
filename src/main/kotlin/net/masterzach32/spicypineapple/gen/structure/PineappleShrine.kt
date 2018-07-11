package net.masterzach32.spicypineapple.gen.structure

import net.masterzach32.spicypineapple.SpicyPineappleMod
import net.masterzach32.spicypineapple.gen.ShrineLocData
import net.masterzach32.spicypineapple.network.ShrineLocUpdateMessage
import net.masterzach32.spicypineapple.registry.ModLoot
import net.minecraft.init.Biomes
import net.minecraft.tileentity.TileEntityShulkerBox
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class PineappleShrine : Structure(
        "pineappleshrine",
        300,
        listOf(Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.COLD_TAIGA, Biomes.COLD_TAIGA_HILLS, Biomes.MUTATED_TAIGA_COLD),
        dimension = 0,
        minHeight = 70,
        maxHeight = 100
) {

    override fun generate(world: World, rand: Random, pos: BlockPos): Boolean {
        val template = getTemplate(world)
        if (template != null) {
            addBlocksToWorld(template, world, pos.down(3))

            ShrineLocData.getForWorld(world).addShrineLocation(pos)
            SpicyPineappleMod.network.sendToAll(ShrineLocUpdateMessage(ShrineLocUpdateMessage.Action.ADD, pos))

            (world.getTileEntity(pos.add(7 ,0, 5)) as? TileEntityShulkerBox)?.setLootTable(ModLoot.shrineLoot, rand.nextLong())

            return true
        }

        return false
    }
}