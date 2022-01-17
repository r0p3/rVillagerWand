package me.r0p3.rvillagerwand.commands;

import me.r0p3.rvillagerwand.GUIConst;
import me.r0p3.rvillagerwand.PlayerMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Wand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            ItemStack mainHandItem = player.getInventory().getItemInMainHand();
            if(mainHandItem.getType().equals(GUIConst.WandMaterial))
            {
                ItemMeta wand = mainHandItem.getItemMeta();
                wand.setDisplayName(GUIConst.DefaultWandName);
                wand.setLore(Arrays.asList(GUIConst.DefaultWandLoreLeftClick, GUIConst.DefaultWandLoreRightClick));
                //ADD CUSTOM ENCHANT LATER
                mainHandItem.setItemMeta(wand);
            }
            else
            {
                PlayerMessages.sendMessage(player, "You can only turn a " + GUIConst.WandMaterial.name() + " into a " + GUIConst.DefaultWandName);
            }
        }
        return true;
    }
}
