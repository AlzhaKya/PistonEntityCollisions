package org.alzhakya;

import cn.nukkit.block.BlockPistonBase;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.BlockFace;
import cn.nukkit.plugin.PluginBase;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockPistonEvent;


@SuppressWarnings("unused")
public class PistonEntityCollisions extends PluginBase implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void BlockPistonEvent(BlockPistonEvent event) {
        BlockFace direction = event.getDirection();
        BlockPistonBase base = event.getBlock();

        AxisAlignedBB boundingBox = event.getBlock().getBoundingBox().getOffsetBoundingBox(
                direction.getXOffset(), direction.getYOffset(), direction.getZOffset()
        );

        Entity[] entities = base.level.getCollidingEntities(boundingBox);

        if (event.isExtending()){
            for (Entity entity : entities){
                moveEntity(entity, direction);
            }
        }
    }

    void moveEntity(Entity entity, BlockFace direction) {
        if (entity == null) return;  // A Check to see if an entity can be pushed would be a good idea too.
        // float diff = this.progress - this.lastProgress;   /* This is what I should be doing. I believe this will be fine for now though. */
        float diff = 1.f;

        entity.move(
                diff * direction.getXOffset(),
                diff * direction.getYOffset(),
                diff * direction.getZOffset()
        );

        entity.updateMovement();
    }

}

