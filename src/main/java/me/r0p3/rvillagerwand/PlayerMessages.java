package me.r0p3.rvillagerwand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerMessages
{
    public static String prefix = ChatColor.YELLOW + "[" + ChatColor.BLUE + "rVillagerWand" + ChatColor.YELLOW +"] ";
    public static void sendMessage(Player player, String message)
    {
        player.sendMessage(prefix + message);
    }
    public static void sendMessagec(Player player, ChatColor color, String message)
    {
        player.sendMessage(prefix + color + message);
    }

    public static void broadCast(String message)
    {
        Bukkit.broadcastMessage(prefix + message);
    }
}
