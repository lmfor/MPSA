package com.lmfor.github.minecraftPluginSortingAlgorithm.Commands.SortingAlgorithms;

import com.lmfor.github.minecraftPluginSortingAlgorithm.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("reset"))
        {
            if(sender instanceof Player)
            {
                Player p = (Player) sender;
                p.sendMessage(ChatColor.GREEN + "Bounds reset!");
                Plugin.getPlugin().start = null;
                Plugin.getPlugin().end = null;
            }
        }

        return true;
    }
}
