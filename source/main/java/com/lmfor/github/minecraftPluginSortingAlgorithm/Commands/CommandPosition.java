package com.lmfor.github.minecraftPluginSortingAlgorithm.Commands;

import com.lmfor.github.minecraftPluginSortingAlgorithm.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPosition implements CommandExecutor {


    // Position protected values for final use
    public Location pos1, pos2;
    int minX, maxX, minY, maxY, minZ, maxZ;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("pos1")) {
            if (sender instanceof Player p)
            {
                Location updated_location = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY()-1, p.getLocation().getBlockZ());
                Block block_at_player_location = updated_location.getBlock();

                // Try except

                try{
                    String blockType = args[0];
                    BlockData blockChoice = Bukkit.createBlockData("minecraft:" + blockType);
                    block_at_player_location.setBlockData(blockChoice);
                } catch (Exception e) {
                    BlockData redstone_block = Bukkit.createBlockData("minecraft:stone");
                    block_at_player_location.setBlockData(redstone_block);

                }
                //Location l = p.getLocation();



                // Player Notification
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Position 1 Selected!");

                // Set global location for later use
                Plugin.getPlugin().pos1 = updated_location;
            }
        }

        if (command.getName().equalsIgnoreCase("pos2")) {
            if (sender instanceof Player p) {
                if (Plugin.getPlugin().pos1 != null) {
                    Location updated_location = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY() - 1, p.getLocation().getBlockZ());
                    Block block_at_player_location = updated_location.getBlock();

                    BlockData blockData = Plugin.getPlugin().blockChoice != null
                            ? Plugin.getPlugin().blockChoice
                            : Bukkit.createBlockData("minecraft:stone");
                    block_at_player_location.setBlockData(blockData);

                    // Player Notification
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "Position 2 Selected!");

                    // Set global location
                    Plugin.getPlugin().pos2 = updated_location;

                    // Calculate and store bounds globally
                    Plugin.getPlugin().minX = Math.min(Plugin.getPlugin().pos1.getBlockX(), Plugin.getPlugin().pos2.getBlockX());
                    Plugin.getPlugin().maxX = Math.max(Plugin.getPlugin().pos1.getBlockX(), Plugin.getPlugin().pos2.getBlockX());
                    Plugin.getPlugin().minY = Math.min(Plugin.getPlugin().pos1.getBlockY(), Plugin.getPlugin().pos2.getBlockY());
                    Plugin.getPlugin().maxY = Math.max(Plugin.getPlugin().pos1.getBlockY(), Plugin.getPlugin().pos2.getBlockY());
                    Plugin.getPlugin().minZ = Math.min(Plugin.getPlugin().pos1.getBlockZ(), Plugin.getPlugin().pos2.getBlockZ());
                    Plugin.getPlugin().maxZ = Math.max(Plugin.getPlugin().pos1.getBlockZ(), Plugin.getPlugin().pos2.getBlockZ());

                    // Fill blocks in the 3D space
                    for (int x = Plugin.getPlugin().minX; x <= Plugin.getPlugin().maxX; x++) {
                        for (int y = Plugin.getPlugin().minY; y <= Plugin.getPlugin().maxY; y++) {
                            for (int z = Plugin.getPlugin().minZ; z <= Plugin.getPlugin().maxZ; z++) {
                                Location current = new Location(p.getWorld(), x, y, z);
                                Block current_block = current.getBlock();

                                // Set the block to the chosen block type
                                current_block.setBlockData(blockData);
                            }
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Position 1 Not Selected!");
                }
            }
        }

        if (command.getName().equalsIgnoreCase("undo")) {
            if (sender instanceof Player p) {
                if (Plugin.getPlugin().pos1 != null && Plugin.getPlugin().pos2 != null) {
                    // Use stored bounds
                    for (int x = Plugin.getPlugin().minX; x <= Plugin.getPlugin().maxX; x++) {
                        for (int y = Plugin.getPlugin().minY; y <= Plugin.getPlugin().maxY; y++) {
                            for (int z = Plugin.getPlugin().minZ; z <= Plugin.getPlugin().maxZ; z++) {
                                Location current = new Location(p.getWorld(), x, y, z);
                                Block current_block = current.getBlock();
                                current_block.setBlockData(Bukkit.createBlockData("minecraft:air"));
                            }
                        }
                    }

                    // Notify player
                    p.sendMessage(ChatColor.GREEN + "Blocks have been undone!");
                } else {
                    p.sendMessage(ChatColor.RED + "Positions are not set!");
                }
            }
        }

        return true;
    }

}
