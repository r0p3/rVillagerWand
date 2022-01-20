package me.r0p3.rvillagerwand.commands;

import me.r0p3.rvillagerwand.Permissions;
import me.r0p3.rvillagerwand.PlayerMessages;
import me.r0p3.rvillagerwand.RVillagerWand;
import me.r0p3.rvillagerwand.wand_interaction.GUIClickItem;
import me.r0p3.rvillagerwand.wand_interaction.OpenGUI;

import me.r0p3.rvillagerwand.wand_interaction.UseWand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Reload implements CommandExecutor
{
    RVillagerWand rVillagerWand;
    OpenGUI openGUI;
    GUIClickItem guiClickItem;
    UseWand useWand;
    public Reload(RVillagerWand rVillagerWand, OpenGUI openGUI, GUIClickItem guiClickItem, UseWand useWand)
    {
        this.rVillagerWand = rVillagerWand; this.openGUI = openGUI; this.guiClickItem = guiClickItem; this.useWand = useWand;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(((Player)sender).hasPermission(Permissions.RELOAD))
        {
            rVillagerWand.reloadConfig();
            rVillagerWand.reload();
            openGUI.allGuiItems = rVillagerWand.guiItems;
            guiClickItem.guiItems = rVillagerWand.guiItems;
            useWand.guiItems = rVillagerWand.guiItems;
            if (sender instanceof Player)
                PlayerMessages.sendMessage((Player) sender, ChatColor.GREEN + "rVillagerWand reloaded");
        }
        return true;
    }
}
