package com.lmfor.github.minecraftPluginSortingAlgorithm.Commands.SortingAlgorithms;

import com.lmfor.github.minecraftPluginSortingAlgorithm.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class SortCommand implements CommandExecutor
{
    // COOLDOWNS COOLDOWNS COOLDOWNS
    private static final long COOLDOWN_TICKS = 1L; // 0.5 seconds

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("sort"))
        {
               if(sender instanceof Player p)
               {
                   ArrayList<Integer> list;
                   // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-
                   // -=-=-=-=-=-=-=-=-=-=-=-=-=- COMMAND SETUP =-=-=-=-=-=-=-=-=-=-=-=-=-=-
                   // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=
                   if(Plugin.getPlugin().original_arraylist == null)
                   {
                       list = new ArrayList<>();
                       p.sendMessage("NO DATASET AVAILABLE. TRY AGAIN LATER.");
                       return true;
                   } else
                   {
                       list = Plugin.getPlugin().original_arraylist;

                   }

                   if(args.length != 1)
                   {
                       p.sendMessage(ChatColor.RED + "Requires arguments to specify which sort!");
                       return true;
                   }


                   // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-
                   // -=-=-=-=-=-=-=-=-=-=-=-=-=- INSERTION SORT =-=-=-=-=-=-=-=-=-=-=-=-=-=-
                   // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-
                   if (args[0].equalsIgnoreCase("insertion")) {
                       p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "[Insertion Sort]");

                   }








                   // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-
                   // -=-=-=-=-=-=-=-=-=-=-=-=-=- SELECTION SORT =-=-=-=-=-=-=-=-=-=-=-=-=-=-
                   // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-




                   // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-
                   // -=-=-=-=-=-=-=-=-=-=-=-=-=-=- MERGE SORT -==-=-=-=-=-=-=-=-=-=-=-=-=-=-
                   // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-



               }
        }




        return true;
    }



}
