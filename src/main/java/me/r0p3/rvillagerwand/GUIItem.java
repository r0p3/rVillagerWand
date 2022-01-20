package me.r0p3.rvillagerwand;


import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIItem
{
    private Plugin plugin = RVillagerWand.getPlugin(RVillagerWand.class);
    public String Name;
    public Material Icon;
    public List<String> Lore;
    public Material MaterialCost;
    public int CostAmount;
    public String Permission;
    public GUIItem (String name, Material guiIcon, List<String> lore, Material materialCost, int costAmount, String permission)
    {
        this.Name = PlayerMessages.textColor(name); this.Icon = guiIcon; this.MaterialCost = materialCost; this.CostAmount = costAmount; this.Permission = permission;
        this.Lore = new ArrayList<>();
        this.Lore.add("");
        for (String loreText : lore)
            this.Lore.add(PlayerMessages.textColor(loreText));

        if(costAmount > 0 && MaterialCost != null)
        {
            Lore.add("");
            for(String item : (List<String>)plugin.getConfig().getList("WAND.Cost_display"))
                Lore.add(PlayerMessages.textColor(item).replace("{item}", MaterialCost.name()).replace("{cost}", costAmount + ""));
        }

        Lore.add("");
        for (String item : (List<String>)plugin.getConfig().getList("WAND.Lore"))
            Lore.add(PlayerMessages.textColor(item));
    }

    public ItemMeta getMeta(ItemMeta meta)
    {
        meta.setLore(Lore);
        meta.setDisplayName(generateWandName());
        return meta;
    }


    public String generateWandName()
    {
        return PlayerMessages.textColor(plugin.getConfig().getString("WAND.Name") + " " +  Name);
    }

    public static String generateWandName(String name)
    {
        return PlayerMessages.textColor(RVillagerWand.getPlugin(RVillagerWand.class).getConfig().getString("WAND.Name") + " " +  name);
    }
}
