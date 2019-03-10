package net.masterzach32.spicypineapple.entity

import com.spicymemes.core.util.getBlock
import net.minecraft.entity.item.EntityFallingBlock
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class EntityEarthRipper(world: World) : EntityMagicProjectile(world, 10) {

    private val blocksDispaced = mutableSetOf<BlockPos>()

    init {
        isInvisible = true
    }

    override fun onUpdate() {
        super.onUpdate()

        if (world.isRemote)
            for (i in 1..10)
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + rand.nextDouble()/2, posY+0.5+rand.nextDouble()/2, posZ + rand.nextDouble()/2, 0.0, 0.0, 0.0)
        else {
            posX += motionX
            posY += motionY
            posZ += motionZ

            val blocks = mutableSetOf<BlockPos>()
            getAdjacentBlocks(position.down(), 4, blocks)
            blocksDispaced.addAll(blocks)

            blocks.map { world.getBlock(it) }
                    .forEach {
                        world.setBlockToAir(it.pos)

                        val entity = EntityFallingBlock(world, it.pos.x.toDouble()+0.5, it.pos.y.toDouble(), it.pos.z.toDouble()+0.5, it.state)
                        entity.fallTime = 1
                        entity.motionY = 1.0
                        world.spawnEntity(entity)
                    }

            blocks.clear()
            getAdjacentBlocks(position.down().down(), 3, blocks)
            blocksDispaced.addAll(blocks)

            blocks.map { world.getBlock(it) }
                    .forEach {
                        world.setBlockToAir(it.pos)

                        val entity = EntityFallingBlock(world, it.pos.x.toDouble()+0.5, it.pos.y.toDouble(), it.pos.z.toDouble()+0.5, it.state)
                        entity.fallTime = 1
                        entity.motionY = 0.9
                        world.spawnEntity(entity)
                    }

            blocks.clear()
            getAdjacentBlocks(position.down().down().down(), 2, blocks)
            blocksDispaced.addAll(blocks)

            blocks.map { world.getBlock(it) }
                    .forEach {
                        world.setBlockToAir(it.pos)

                        val entity = EntityFallingBlock(world, it.pos.x.toDouble()+0.5, it.pos.y.toDouble(), it.pos.z.toDouble()+0.5, it.state)
                        entity.fallTime = 1
                        entity.motionY = 0.9
                        world.spawnEntity(entity)
                    }

            timer--
            if (timer <= 0)
                setDead()
        }

    }

    private fun getAdjacentBlocks(pos: BlockPos, range: Int, blocks: MutableSet<BlockPos>) {
        if (range > 0) {
            blocks.add(pos)
            EnumFacing.HORIZONTALS.forEach { getAdjacentBlocks(pos.offset(it), range - 1, blocks) }
        }
    }

    override fun entityInit() {}
}