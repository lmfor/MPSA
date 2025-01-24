package com.lmfor.github.minecraftPluginSortingAlgorithm.Listeners;

import com.lmfor.github.minecraftPluginSortingAlgorithm.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class WandBlockBreakListener implements Listener
{
    // Cooldown variables
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTime = 1000; //ms


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        // if start block hasnt been used yet, then simply set to glowstone
        // if start back HAS been used yet, reset it to stone, then set new block



        // only if wand is held :
        Player p = e.getPlayer();
        ItemStack heldItem = p.getInventory().getItemInMainHand();

        if (heldItem.getType() == Material.STICK)
        {
            if (Plugin.getPlugin().start == null) {
                e.getBlock().setType(Material.GLOWSTONE);
            } else {
                Plugin.getPlugin().start.setType(Material.STONE);
                e.getBlock().setType(Material.GLOWSTONE);
            }


            Plugin.getPlugin().start = e.getBlock();
            p.sendMessage(ChatColor.LIGHT_PURPLE + String.format("Selected position 1 at %d, %d, %d", e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ()));

            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent e)
    {
        switch (e.getAction()) {
            case RIGHT_CLICK_AIR:
                //System.out.println("RIGHT CLICKED AIR");
            case RIGHT_CLICK_BLOCK:
                //System.out.println("RIGHT CLICKED A BLOCK");
                break; // Continue processing
            default:
                return; // Ignore other actions
        }



        Player p = e.getPlayer();
        ItemStack heldItem = p.getInventory().getItemInMainHand();
        UUID playerID = p.getUniqueId();

        long currentTime = System.currentTimeMillis();
        if(cooldowns.containsKey(playerID))
        {
            long lastTime = cooldowns.get(playerID);
            if(currentTime - lastTime < cooldownTime){
                return;
            }
        }
        cooldowns.put(playerID, currentTime);

        // if end block hasnt been used yet, then simply set to glowstone
        // if end back HAS been used yet, reset it to stone, then set new block

        if (heldItem.getType() == Material.STICK)
        {
            if (Plugin.getPlugin().end == null)
            {
                e.getClickedBlock().setType(Material.GLOWSTONE);
            } else {
                Plugin.getPlugin().end.setType(Material.STONE);
                e.getClickedBlock().setType(Material.GLOWSTONE);
            }


            Plugin.getPlugin().end = e.getClickedBlock();
            p.sendMessage(ChatColor.DARK_PURPLE + String.format("Selected position 2 at %d, %d, %d", Objects.requireNonNull(e.getClickedBlock()).getX(), e.getClickedBlock().getY(), e.getClickedBlock().getZ()));

            e.setCancelled(true);
            return;
        }

    }
}
