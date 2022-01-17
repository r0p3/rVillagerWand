package me.r0p3.rvillagerwand;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIItem
{
    public static ChatColor nameColor = ChatColor.YELLOW;

    public String Name;
    public Material Icon;
    public List<String> Lore;
    public Material MaterialCost;
    public int CostAmount;
    public String Permission;
    public GUIItem (String name, Material guiIcon, List<String> lore, Material materialCost, int costAmount, String permission)
    {
        this.Name = ChatColor.YELLOW + name; this.Icon = guiIcon; this.MaterialCost = materialCost; this.CostAmount = costAmount; this.Permission = permission;
        this.Lore = new ArrayList<>();
        for (String loreText : lore)
            this.Lore.add(loreText);

        if(costAmount > 0)
            this.Lore.add(ChatColor.YELLOW + "Cost: " + ChatColor.RED + this.CostAmount + " " + this.MaterialCost.name());
        Lore.add("");
        Lore.add(GUIConst.DefaultWandLoreLeftClick);
        Lore.add(GUIConst.DefaultWandLoreRightClick);
    }

    public ItemMeta getMeta(ItemMeta meta)
    {
        meta.setLore(Lore);
        meta.setDisplayName(generateWandName());
        return meta;
    }


    public String generateWandName()
    {
        return GUIConst.DefaultWandName + ChatColor.WHITE + " (" + Name + ChatColor.WHITE + ")";
    }

    public static String generateWandName(String name)
    {
        return GUIConst.DefaultWandName + ChatColor.WHITE + " (" + nameColor + name + ChatColor.WHITE + ")";
    }
}
