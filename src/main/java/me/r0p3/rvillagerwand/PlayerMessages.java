package me.r0p3.rvillagerwand;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class PlayerMessages
{
    private static Plugin plugin;
    public PlayerMessages()
    {
        plugin = RVillagerWand.getPlugin(RVillagerWand.class);
        plugin.reloadConfig();
    }


    public void sendMessage(Player player, String message)
    {
        player.sendMessage(textColor(plugin.getConfig().getString("Prefix")) + message);
    }
    public void sendMessagec(Player player, ChatColor color, String message)
    {
        player.sendMessage(textColor(plugin.getConfig().getString("Prefix")) + color + message);
    }

    public String getConfigText(Player player, String configIndex)
    {
        return textColor(plugin.getConfig().getString(configIndex).replace("{player}", player.getName()));
    }

    public void sendMessageFromConfig(Player player, String configIndex)
    {
            String message = plugin.getConfig().getString(configIndex);
            if(message != null && message != "")
                player.sendMessage(textColor(plugin.getConfig().getString("Prefix")) + textColor(message.replace("{player}", player.getName())));
    }

    public void sendMessageFromConfig(Player player, String configIndex, String replaceString, String content)
    {
        String message = plugin.getConfig().getString(configIndex);
        if(message != null && message != "")
            player.sendMessage(textColor(plugin.getConfig().getString("Prefix")) + textColor(message.replace("{player}", player.getName()).replace(replaceString, content)));
    }

    public String replace(String message, String itemToReplace, String replacement)
    {
        return message.replace(itemToReplace, replacement);
    }


    public static void broadCast(String message)
    {
        Bukkit.broadcastMessage(textColor(plugin.getConfig().getString("Prefix")) + message);
    }

    public static String textColor(String text)
    {
        text = text.replace("&0", ChatColor.BLACK + "");
        text = text.replace("&1", ChatColor.DARK_BLUE + "");
        text = text.replace("&2", ChatColor.DARK_GREEN + "");
        text = text.replace("&3", ChatColor.DARK_AQUA + "");
        text = text.replace("&4", ChatColor.DARK_RED + "");
        text = text.replace("&5", ChatColor.DARK_PURPLE + "");
        text = text.replace("&6", ChatColor.GOLD + "");
        text = text.replace("&7", ChatColor.GRAY + "");
        text = text.replace("&8", ChatColor.DARK_GRAY + "");
        text = text.replace("&9", ChatColor.BLUE + "");
        text = text.replace("&a", ChatColor.GREEN + "");
        text = text.replace("&b", ChatColor.AQUA + "");
        text = text.replace("&c", ChatColor.RED + "");
        text = text.replace("&d", ChatColor.LIGHT_PURPLE + "");
        text = text.replace("&e", ChatColor.YELLOW + "");
        text = text.replace("&f", ChatColor.WHITE + "");
        text = text.replace("&k", ChatColor.MAGIC + "");
        text = text.replace("&l", ChatColor.BOLD + "");
        text = text.replace("&m", ChatColor.STRIKETHROUGH + "");
        text = text.replace("&n", ChatColor.UNDERLINE + "");
        text = text.replace("&o", ChatColor.ITALIC + "");
        text = text.replace("&r", ChatColor.RESET + "");
        return  text;
    }
}
