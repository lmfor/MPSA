package com.lmfor.github.minecraftPluginSortingAlgorithm.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CommandWand implements CommandExecutor {


    // -------------------------------- Helper Methods --------------------------------

    // Check if Inventory Has Slot
    public static boolean slotAvailable(Player p)
    {
        for(ItemStack slot : p.getInventory())
        {
            if(slot == null || slot.getAmount()==0)
            {
                return true;
            }
        }
        p.sendMessage(ChatColor.RED + "Inventory full. Free a slot and try again!");
        return false;
    }

    // Check if player already has a wand
    // Check if player already has a wand
    public static boolean hasWand(Player p) {
        for (ItemStack item : p.getInventory()) {
            if (item == null) {
                continue;
            }

            ItemMeta meta = item.getItemMeta();
            if (meta != null && ChatColor.stripColor(meta.getDisplayName()).equalsIgnoreCase("wand")) {
                p.sendMessage(ChatColor.RED + "You already have a wand!");
                return true;
            }
        }
        return false;
    }


    public ItemStack createWand()
    {
        // Create Wand and its metadata
        ItemStack wand = new ItemStack(Material.STICK);

        ItemMeta wandMeta = wand.getItemMeta();

        wandMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Wand");
        wandMeta.setLore(Arrays.asList(ChatColor.ITALIC + "It is rumored that this wand holds great magical power."));


        wand.setItemMeta(wandMeta);

        return wand;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("wand"))
        {
            if (sender instanceof Player p)
            {
                // if player has no available slots or already has a wand
                if (!slotAvailable(p) || hasWand(p)) {
                    return true;
                }

                p.getInventory().setItemInMainHand(createWand());





            }
        }

        return true;
    }
}
