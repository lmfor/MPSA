package com.lmfor.github.minecraftPluginSortingAlgorithm;

import com.lmfor.github.minecraftPluginSortingAlgorithm.Commands.CommandPosition;
import com.lmfor.github.minecraftPluginSortingAlgorithm.Commands.CommandWand;
import com.lmfor.github.minecraftPluginSortingAlgorithm.Commands.SortingAlgorithms.Misc.ResetCommand;
import com.lmfor.github.minecraftPluginSortingAlgorithm.Commands.SortingAlgorithms.SortCommand;
import com.lmfor.github.minecraftPluginSortingAlgorithm.Commands.SortingAlgorithms.Misc.SetupCommand;
import com.lmfor.github.minecraftPluginSortingAlgorithm.Listeners.StoneBreakListener;
import com.lmfor.github.minecraftPluginSortingAlgorithm.Listeners.WandBlockBreakListener;
import com.lmfor.github.minecraftPluginSortingAlgorithm.Listeners.XPBottleBreakListener;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;

public final class Plugin extends JavaPlugin {

    // Global Plugin Reference
    private static Plugin plugin;

    // Global Variables Regarding CommandPosition
    public Location pos1, pos2;
    public BlockData blockChoice;
    public int minX, maxX, minY, maxY, minZ, maxZ;

    // Global Variables Regarding CommandWand
    public Block start, end;

    // Global Variables Regarding SortCommand
    public int range;

    // ------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------
    // -------------------------------- GLOBAL VARIABLES FOR SORTING --------------------------------
    // ------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------

    public ArrayList<Integer> original_arraylist;


    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;



        // -------------------------------- Registers Events --------------------------------

        // XP BOTTLE BREAK LISTENER EVENT
        getServer().getPluginManager().registerEvents(new XPBottleBreakListener(),this);
        getServer().getPluginManager().registerEvents(new StoneBreakListener(), this);
        getServer().getPluginManager().registerEvents(new WandBlockBreakListener(), this);
        // -------------------------------- Register Commands --------------------------------

        // POSITION COMMAND
        Objects.requireNonNull(getCommand("pos1")).setExecutor(new CommandPosition());
        Objects.requireNonNull(getCommand("pos2")).setExecutor(new CommandPosition());
        Objects.requireNonNull(getCommand("undo")).setExecutor(new CommandPosition());

        // WAND COMMAND
        Objects.requireNonNull(getCommand("wand")).setExecutor(new CommandWand());

        // SETUP COMMAND
        Objects.requireNonNull(getCommand("setup")).setExecutor(new SetupCommand());

        // RESET COMMAND
        Objects.requireNonNull(getCommand("reset")).setExecutor(new ResetCommand());

        // SORT COMMAND
        Objects.requireNonNull(getCommand("sort")).setExecutor(new SortCommand());
    }


    // Global getPlugin Reference Method
    public static Plugin getPlugin()
    {
        return plugin;
    }
}
