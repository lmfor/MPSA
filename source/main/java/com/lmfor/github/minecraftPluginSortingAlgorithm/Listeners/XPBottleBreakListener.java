package com.lmfor.github.minecraftPluginSortingAlgorithm.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;

public class XPBottleBreakListener implements Listener {

    @EventHandler
    public void onXPBottleBreak(ExpBottleEvent e)
    {
        e.setShowEffect(false);
        Block hitBlock = e.getHitBlock();
        if (hitBlock==null) return; //assert null

        int block_x = hitBlock.getX();
        int block_y = hitBlock.getY();
        int block_z = hitBlock.getZ();

        final int size = 2;


        // get 3x3 radius near XP bottle
        for(int i = -size; i<1;i++){
            for(int j = -size; j < 1; j++){
                for(int k = -size; k < 1; k++)
                {
                    Block selected = hitBlock.getWorld().getBlockAt(block_x+i,block_y+j,block_z+k);
                    selected.setType(Material.AIR);
                    //selected.breakNaturally();
                }
            }
        }


        hitBlock.breakNaturally();
    }
}
