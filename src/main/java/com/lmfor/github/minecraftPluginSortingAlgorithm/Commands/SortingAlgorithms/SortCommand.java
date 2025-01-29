package com.lmfor.github.minecraftPluginSortingAlgorithm.Commands.SortingAlgorithms;

import com.lmfor.github.minecraftPluginSortingAlgorithm.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;

public class SortCommand implements CommandExecutor {
    // Delay between each step of the sorting algorithm (in ticks)
    private static final long DELAY_TICKS = 1L; // Adjust this for faster/slower visualization
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
                        int swapIndex = -1; // Track the column being swapped with
                        int length = 0;

                        @Override
                        public void run() {
                            if (i < list.size()) {
                                int key = list.get(i);
                                int j = i - 1;

                                // Highlight the current column and the column being compared
                                currentIndex = i;
                                swapIndex = j;
                                updateBlocks(world, baseX, baseY, baseZ, list, currentIndex, swapIndex, range);
                                world.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 5, 6);

                                // Move elements greater than key to the right
                                while (j >= 0 && list.get(j) > key) {
                                    list.set(j + 1, list.get(j));
                                    j--;

                                    // Update visualization after each swap
                                    swapIndex = j;

                                    updateBlocks(world, baseX, baseY, baseZ, list, currentIndex, swapIndex, range);
                                }

                                // Place key in its correct position
                                list.set(j + 1, key);

                                // Update visualization after placing key
                                swapIndex = j + 1;
                                updateBlocks(world, baseX, baseY, baseZ, list, currentIndex, swapIndex, range);

                                i++;
                                length++;
                            } else {
                                // Sorting complete
                                p.sendMessage(ChatColor.GREEN + "Insertion Sort Complete!");
                                p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "LENGTH: " + ChatColor.RESET + ChatColor.WHITE + length);
                                // Reset all red wool blocks to white wool
                                resetBlocks(world, baseX, baseY, baseZ, list);

                                this.cancel();
                            }
                        }
                    }.runTaskTimer(Plugin.getPlugin(), 0L, DELAY_TICKS); // Run with delay
                }
                else if (args[0].equalsIgnoreCase("merge")) {
                    p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "[Merge Sort]");

                    // Merge Sort Setup
                    World world = p.getWorld();
                    Block start = Plugin.getPlugin().start;
                    int baseX = start.getX();
                    int baseY = start.getY();
                    int baseZ = start.getZ();

                    ArrayList<Integer> temp = new ArrayList<>(Collections.nCopies(list.size(), 0));
                    java.util.Stack<int[]> stack = new java.util.Stack<>();

                    // Push the entire array range to process
                    stack.push(new int[]{0, list.size() - 1, 0}); // 0 = stage: splitting

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!stack.isEmpty()) {
                                int[] current = stack.pop();
                                int left = current[0];
                                int right = current[1];
                                int stage = current[2]; // 0 = split, 1 = merge

                                if (stage == 0) { // SPLITTING STAGE
                                    if (left < right) {
                                        int mid = left + (right - left) / 2;

                                        // Push the merge task
                                        stack.push(new int[]{left, right, 1});

                                        // Push the right half
                                        stack.push(new int[]{mid + 1, right, 0});

                                        // Push the left half
                                        stack.push(new int[]{left, mid, 0});
                                    }
                                } else { // MERGING STAGE
                                    int mid = left + (right - left) / 2;
                                    merge(list, temp, left, mid, right);

                                    // Update Minecraft visualization
                                    updateBlocks(world, baseX, baseY, baseZ, list, left, right);
                                    world.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1, 1);

                                    // p.sendMessage(ChatColor.YELLOW + "Merged from " + left + " to " + right);
                                }
                            } else {
                                p.sendMessage(ChatColor.GREEN + "Merge Sort Complete!");
                                resetBlocks(world, baseX, baseY, baseZ, list);
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(Plugin.getPlugin(), 0L, DELAY_TICKS);
                }
                else if (args[0].equalsIgnoreCase("ADMIN")) {
                    p.sendMessage(ChatColor.RED + "Invalid algorithm. Available: insertion, merge");
                }



            }
        }
        return true;
    }

    // Recursive Helper Merge Sort Function
    private static void mergeSort(ArrayList<Integer> arr, ArrayList<Integer> temp, int left, int right)
    {
        if (left < right)
        {
            int mid = left + (right-left) / 2;
            mergeSort(arr, temp, left ,mid);
            mergeSort(arr, temp, mid+1, right);
            merge(arr, temp, left, mid ,right);
        }
    }

    // Function to merge two halves
    private static void merge(ArrayList<Integer> arr, ArrayList<Integer> temp, int left, int mid, int right)
    {
        for(int i = left; i <= right; i++)
        {
            temp.set(i, arr.get(i));
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while(i<=mid && j <= right)
        {
            if(temp.get(i) <= temp.get(j))
            {
                arr.set(k, temp.get(i));
                i++;
            } else {
                arr.set(k, temp.get(j));
                j++;
            }
            k++;
        }

        while(i<=mid) {
            arr.set(k, temp.get(i));
            i++;
            k++;
        }
        while(j<=right){
            arr.set(k, temp.get(j));
            j++;
            k++;
        }
    }

    // Helper method to update blocks
    private void updateBlocks(World world, int baseX, int baseY, int baseZ, ArrayList<Integer> list, int currentIndex, int swapIndex)
    {
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

                // Highlight the current column and the swap column in red wool
                if (i >= currentIndex && i <= swapIndex) { // if i is between currentIndex and swap index
                    block.setType(Material.RED_WOOL);
                } else {
                    block.setType(Material.WHITE_WOOL);
                }

                //if(i >= currentIndex && i <= swapIndex)
                //if(i >= swapIndex && i <= currentIndex)
            }
        }
    }

    private void updateBlocks(World world, int baseX, int baseY, int baseZ, ArrayList<Integer> list, int currentIndex, int swapIndex, int range) {
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

                // Highlight the current column and the swap column in red wool
                if (i == currentIndex || i == swapIndex) {
                    block.setType(Material.RED_WOOL);
                } else {
                    block.setType(Material.WHITE_WOOL);
                }
            }
        }
    }

    // Helper method to reset all red wool blocks to white wool
    private void resetBlocks(World world, int baseX, int baseY, int baseZ, ArrayList<Integer> list) {
        // Find the maximum height in the list
        int maxHeight = 0;
        for (int height : list) {
            if (height > maxHeight) {
                maxHeight = height;
            }
        }

        for (int i = 0; i < list.size(); i++) {
            int height = list.get(i);

            // Reset all blocks in this column to white wool
            for (int j = 0; j < height; j++) {
                Block block = world.getBlockAt(baseX + i, baseY + 1 + j, baseZ);
                block.setType(Material.WHITE_WOOL);
            }
        }
    }
}