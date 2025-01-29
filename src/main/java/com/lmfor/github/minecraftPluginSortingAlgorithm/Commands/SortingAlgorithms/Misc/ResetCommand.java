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

public class ResetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final int maxHeight = 319;

        if(command.getName().equalsIgnoreCase("reset"))
        {
            if(sender instanceof Player)
            {
                Player p = (Player) sender;
                World world = p.getWorld();

                Block start = Plugin.getPlugin().start;
                Block end = Plugin.getPlugin().end;

                int startX = start.getX();
                int endX = end.getX();
                int deltaX = endX-startX;


                int lowerBound = start.getY()+1;


                for(int i = startX; i <= endX; i++)
                {
                    try {
                        for(int j = lowerBound; j < maxHeight; j++)
                        {
                            Block selectedBlock = world.getBlockAt(i, j, start.getZ());
                            selectedBlock.setType(Material.AIR);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }

                p.sendMessage(ChatColor.GREEN + "Bounds reset!");
            }
        }

        return true;
    }
}
