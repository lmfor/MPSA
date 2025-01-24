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

        if (command.getName().equalsIgnoreCase("setup"))
        {
            if (sender instanceof Player p)
            {

                // Check if start and end are not null so we can begin command
                Block start = Plugin.getPlugin().start;
                Block end = Plugin.getPlugin().end;

                if (start == null || end == null) {
                    p.sendMessage(ChatColor.RED + "BOUNDS NOT PROPERLY SET");
                } else
                {
                    // -- Define deltaX and deltaZ
                    int deltaX = Math.abs(start.getX() - end.getX());
                    int deltaZ = Math.abs(start.getZ() - end.getZ());

                    // IF IT'S IN A STRAIGHT LINE EITHER X OR Z
                    // Meaning the bounds change on both axes
                    if (deltaX > 0 && deltaZ > 0)
                    {
                        p.sendMessage(ChatColor.RED + "Invalid boundaries! Try again!");
                        Plugin.getPlugin().start = null;
                        Plugin.getPlugin().end = null;
                    } else
                    {
                        if (args.length == 1 || args.length == 2) {
                            // Default range
                            int range = 10;

                            // Parse range if provided
                            if (args.length == 2) {
                                try {
                                    range = Integer.parseInt(args[1]);
                                    p.sendMessage(String.format("Range is set to %d", range));
                                } catch (NumberFormatException e) {
                                    p.sendMessage(ChatColor.RED + "Invalid range. Please enter a valid number.");
                                    return true;
                                }
                            } else {
                                p.sendMessage("Range is set to 10");
                            }

                            // Determine the axis
                            String axis = args[0].toLowerCase();
                            if (!axis.equals("x") && !axis.equals("z")) {
                                p.sendMessage(ChatColor.RED + "Invalid axis. Use 'x' or 'z'.");
                                return true;
                            }

                            // Get the coordinates of start and end blocks
                            int startCoord = axis.equals("x") ? start.getX() : start.getZ();
                            int endCoord = axis.equals("x") ? end.getX() : end.getZ();
                            int fixedCoord = axis.equals("x") ? start.getZ() : start.getX();
                            int minCoord = Math.min(startCoord, endCoord);
                            int maxCoord = Math.max(startCoord, endCoord);

                            // Fill blocks along the specified axis
                            for (int coord = minCoord; coord <= maxCoord; coord++) {
                                Block blockToFill = axis.equals("x")
                                        ? start.getWorld().getBlockAt(coord, start.getY(), fixedCoord)
                                        : start.getWorld().getBlockAt(fixedCoord, start.getY(), coord);

                                // Fill the block with some material (e.g., STONE)
                                blockToFill.setType(Material.GLOWSTONE);
                            }

                            p.sendMessage(ChatColor.GREEN + String.format("Blocks filled along the %s-axis!", axis.toUpperCase()));


                            // Fill in random values:
                            // HARDCODED FOR ONLY X-AXIS  || || || || WARNING || || ||

                            //p.sendMessage(String.valueOf(deltaX));
                            Random random = new Random();
                            deltaX += 1;

                            // We have start already defined
                            int baseX = start.getX();
                            int baseY = start.getY();
                            int baseZ = start.getZ();
                            World world = p.getWorld();


                            // DEFINE PRIMITIVE LIST OF RANDOM VALUES TO USE AS list[n] IN SORTING
                            //int[] list_d = new int[deltaX];

                            // DEFINE OBJECT LIST OF RANDOM VALUES TO USE AS List[n]
                            ArrayList<Integer> list_v = new ArrayList<>();



                            // Loop across x-axis
                            for(int i = 0; i < deltaX; i++)
                            {
                                // random range value from 1-range
                                int range_value = random.nextInt(range)+1;
                                //                                current block     start 1 up
                                Block currentXBlock = world.getBlockAt(baseX+i, baseY+1, baseZ);

                                //loop thru and add blocks
                                for(int j = 0; j < range_value; j++)
                                {
                                    // Going upwards
                                    Block currentYBlock = world.getBlockAt(baseX+i,(baseY+1)+j,baseZ);
                                    currentYBlock.setType(Material.WHITE_WOOL);

                                    // value range_value != ((baseY+1) + j)
                                    // I will only add range_value to the list, and make baseY+1 public for
                                    // visual updates

                                    // THE ORDER OF THE NUMBERS IS IRRELEVANT
                                    // HOWEVER, list[0] goes on Plugin.getPlugin().start
                                    // ANDm list[-1] goes on Plugin.getPlugin().end so on and so forth

                                    // all I need is the (baseY+1 value) which I can get later
                                    // and I need to sort and update after each iteration


                                    // update lists
                                    //list_d[i] = range_value;


                                }
                                list_v.add(range_value);
                                //System.out.println("Range value: " + range_value);

                                Plugin.getPlugin().original_arraylist = list_v;
                                //Plugin.getPlugin().original_list = list_d;

                                //System.out.println(Plugin.getPlugin().original_arraylist + "\n\n\n");
                            }




                        } else {
                            p.sendMessage(ChatColor.RED + "Invalid Arguments. Usage: /setup <axis> [range]");
                        }








                    }


                }




                // fill in the x value or z value between the two positions (blocks)

                // set the default range to 10, unless they specific a range argument

                // fill in each block with that range to begin with (starting data)




            }
        }

        return true;
    }
}
