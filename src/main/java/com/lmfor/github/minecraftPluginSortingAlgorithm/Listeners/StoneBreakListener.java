package com.lmfor.github.minecraftPluginSortingAlgorithm.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class StoneBreakListener implements Listener
{
    @EventHandler
    public void onStoneBreak(BlockBreakEvent e)
    {
        if (e.getBlock().getType() == Material.STONE)
        {
            e.setCancelled(true);
        }

    }
}
