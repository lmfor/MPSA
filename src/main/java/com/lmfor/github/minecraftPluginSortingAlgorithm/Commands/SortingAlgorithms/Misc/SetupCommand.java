package com.lmfor.github.minecraftPluginSortingAlgorithm.Commands.SortingAlgorithms.Misc;

import com.lmfor.github.minecraftPluginSortingAlgorithm.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class SetupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("setup")) {
            if (sender instanceof Player p) {

                // Check if start and end are not null so we can begin command
                Block start = Plugin.getPlugin().start;
                Block end = Plugin.getPlugin().end;

                if (start == null || end == null) {
                    p.sendMessage(ChatColor.RED + "BOUNDS NOT PROPERLY SET");
                } else {
                    // -- Define deltaX and deltaZ
                    int deltaX = Math.abs(start.getX() - end.getX());
                    int deltaZ = Math.abs(start.getZ() - end.getZ());

                    // IF IT'S IN A STRAIGHT LINE EITHER X OR Z
                    // Meaning the bounds change on both axes
                    if (deltaX > 0 && deltaZ > 0) {
                        p.sendMessage(ChatColor.RED + "Invalid boundaries! Try again!");
                        Plugin.getPlugin().start = null;
                        Plugin.getPlugin().end = null;
                    } else {
                        if (args.length == 1) {
                            // Default range
                            int range = 10;

                            // Parse range if provided
                            try {
                                range = Integer.parseInt(args[0]);
                                p.sendMessage(String.format("Range is set to %d", range));
                            } catch (NumberFormatException e) {
                                p.sendMessage(ChatColor.RED + "Invalid range. Please enter a valid number.");
                                return true;
                            }

                            // Get the coordinates of start and end blocks
                            int startX = start.getX();
                            int endX = end.getX();
                            int fixedZ = start.getZ();
                            int minX = Math.min(startX, endX);
                            int maxX = Math.max(startX, endX);

                            // Fill blocks along the X-axis
                            for (int x = minX; x <= maxX; x++) {
                                Block blockToFill = start.getWorld().getBlockAt(x, start.getY(), fixedZ);
                                blockToFill.setType(Material.GLOWSTONE);
                            }

                            p.sendMessage(ChatColor.GREEN + "Blocks filled along the X-axis!");

                            // Fill in random values:
                            Random random = new Random();
                            deltaX += 1;

                            // We have start already defined
                            int baseX = start.getX();
                            int baseY = start.getY();
                            int baseZ = start.getZ();
                            World world = p.getWorld();

                            // DEFINE OBJECT LIST OF RANDOM VALUES TO USE AS List[n]
                            ArrayList<Integer> list_v = new ArrayList<>();
                            Plugin.getPlugin().range = range;

                            // Loop across x-axis
                            for (int i = 0; i < deltaX; i++) {
                                // random range value from 1-range
                                int range_value = random.nextInt(range) + 1;
                                // current block     start 1 up
                                Block currentXBlock = world.getBlockAt(baseX + i, baseY + 1, baseZ);

                                // loop thru and add blocks
                                for (int j = 0; j < range_value; j++) {
                                    // Going upwards
                                    Block currentYBlock = world.getBlockAt(baseX + i, (baseY + 1) + j, baseZ);
                                    currentYBlock.setType(Material.WHITE_WOOL);
                                }
                                list_v.add(range_value);
                                Plugin.getPlugin().original_arraylist = list_v;
                            }

                        } else {
                            p.sendMessage(ChatColor.RED + "Invalid Arguments. Usage: /setup <range>");
                        }
                    }
                }
            }
        }

        return true;
    }
}