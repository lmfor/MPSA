package com.lmfor.github.minecraftPluginSortingAlgorithm.Commands.SortingAlgorithms;

import com.lmfor.github.minecraftPluginSortingAlgorithm.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class SortCommand implements CommandExecutor {
    // Delay between each step of the sorting algorithm (in ticks)
    private static final long DELAY_TICKS = 2; // Adjust this for faster/slower visualization
    public int range = Plugin.getPlugin().range;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("sort")) {
            if (sender instanceof Player p) {
                // Check if dataset is available
                if (Plugin.getPlugin().original_arraylist == null) {
                    p.sendMessage(ChatColor.RED + "NO DATASET AVAILABLE. RUN /setup FIRST.");
                    return true;
                }

                if (args.length != 1) {
                    p.sendMessage(ChatColor.RED + "Usage: /sort <algorithm>");
                    return true;
                }

                // Get the dataset
                ArrayList<Integer> list = Plugin.getPlugin().original_arraylist;

                // Insertion Sort
                if (args[0].equalsIgnoreCase("insertion")) {
                    p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "[Insertion Sort]");

                    // Get the world and base coordinates
                    World world = p.getWorld();
                    Block start = Plugin.getPlugin().start;
                    int baseX = start.getX();
                    int baseY = start.getY();
                    int baseZ = start.getZ();

                    // Run the sorting algorithm asynchronously with visualization
                    new BukkitRunnable() {
                        int i = 1; // Start from the second element
                        int currentIndex = i; // Track the current column being processed

                        @Override
                        public void run() {
                            if (i < list.size()) {
                                int key = list.get(i);
                                int j = i - 1;

                                // Highlight the current column in red wool
                                currentIndex = i;
                                updateBlocks(world, baseX, baseY, baseZ, list, currentIndex, range);

                                // Move elements greater than key to the right
                                while (j >= 0 && list.get(j) > key) {
                                    list.set(j + 1, list.get(j));
                                    j--;

                                    // Update visualization after each swap
                                    updateBlocks(world, baseX, baseY, baseZ, list, currentIndex, range);
                                }

                                // Place key in its correct position
                                list.set(j + 1, key);

                                // Update visualization after placing key
                                updateBlocks(world, baseX, baseY, baseZ, list, currentIndex, range);

                                i++;
                            } else {
                                // Sorting complete
                                p.sendMessage(ChatColor.GREEN + "Insertion Sort Complete!");
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(Plugin.getPlugin(), 0L, DELAY_TICKS); // Run with delay
                } else {
                    p.sendMessage(ChatColor.RED + "Invalid algorithm. Available: insertion");
                }
            }
        }
        return true;
    }

    // Helper method to update blocks in Minecraft
    private void updateBlocks(World world, int baseX, int baseY, int baseZ, ArrayList<Integer> list, int currentIndex, int range) {
        // Find the maximum height in the list
        int maxHeight = 0;
        for (int height : list) {
            if (height > maxHeight) {
                maxHeight = height;
            }
        }

        for (int i = 0; i < list.size(); i++) {
            int height = list.get(i);

            // Clear blocks above the new height for this column
            for (int y = baseY + 1; y <= baseY + maxHeight; y++) {
                Block block = world.getBlockAt(baseX + i, y, baseZ);
                block.setType(Material.AIR);
            }

            // Place new blocks up to the current height
            for (int j = 0; j < height; j++) {
                Block block = world.getBlockAt(baseX + i, baseY + 1 + j, baseZ);

                // Highlight the current column in red wool
                if (i == currentIndex) {
                    block.setType(Material.RED_WOOL);
                } else {
                    block.setType(Material.WHITE_WOOL);
                }
            }
        }
    }
}