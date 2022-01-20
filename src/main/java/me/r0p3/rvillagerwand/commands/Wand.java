package me.r0p3.rvillagerwand.commands;

import me.r0p3.rvillagerwand.Permissions;
import me.r0p3.rvillagerwand.PlayerMessages;
import me.r0p3.rvillagerwand.RVillagerWand;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Wand implements CommandExecutor
{
    Plugin plugin = RVillagerWand.getPlugin(RVillagerWand.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            if(player.hasPermission(Permissions.WAND))
            {
                Material wandMaterial = Material.getMaterial(plugin.getConfig().getString("WAND.Item"));
                ItemStack mainHandItem = player.getInventory().getItemInMainHand();
                if (mainHandItem != null && mainHandItem.getType().equals(wandMaterial))
                {
                    ItemMeta wand = mainHandItem.getItemMeta();
                    wand.setDisplayName(PlayerMessages.textColor(plugin.getConfig().getString("WAND.Name")));
                    List<String> temp = new ArrayList<>();
                    for (String lore : (List<String>) plugin.getConfig().getList("WAND.Lore"))
                        temp.add(PlayerMessages.textColor(lore));
                    wand.setLore(temp);
                    //ADD CUSTOM ENCHANT LATER???
                    mainHandItem.setItemMeta(wand);
                }
                else
                {
                    RVillagerWand.playerMessages.sendMessageFromConfig(
                            player, "WAND.Wrong_item_message", "{wand}", wandMaterial.name());

                }
            }
        }
        return true;
    }
}
