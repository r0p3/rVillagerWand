package me.r0p3.rvillagerwand.wand_interaction;

import me.r0p3.rvillagerwand.GUIItem;
import me.r0p3.rvillagerwand.PlayerMessages;
import me.r0p3.rvillagerwand.RVillagerWand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class OpenGUI implements Listener
{
    Plugin plugin = RVillagerWand.getPlugin(RVillagerWand.class);
    public List<GUIItem> allGuiItems;
    public OpenGUI(List<GUIItem> guiItems)
    {
        this.allGuiItems = guiItems;
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent e)
    {

        try
        {
            ItemStack mainHand = e.getPlayer().getInventory().getItemInMainHand();
            if ((e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) && mainHand.getItemMeta() != null
                    && mainHand.getItemMeta().getDisplayName().startsWith(PlayerMessages.textColor(plugin.getConfig().getString("WAND.Name")))&&
                    mainHand.getType().equals(Material.getMaterial(plugin.getConfig().getString("WAND.Item"))))
            {
                Player player = e.getPlayer();
                Inventory GUI = createInventory(player);
                player.openInventory(GUI);

            }
        }
        catch (Exception exception)
        {
            Bukkit.getLogger().info(exception.getMessage());
        }
    }

    public Inventory createInventory(Player player)
    {

        int size = 0;
        ItemStack[] guiItems = new ItemStack[27];

        for(GUIItem item : allGuiItems)
        {
            if(player.hasPermission(item.Permission))
            {
                guiItems[size] = (guiItemSetup(item.Name, item.Icon, item.Lore));
                size++;
            }
        }

        Inventory GUI = Bukkit.createInventory(player, 27, PlayerMessages.textColor(plugin.getConfig().getString("Menu_title")));
        GUI.setContents(guiItems);
        return GUI;
    }

    private ItemStack guiItemSetup(String itemName, Material material, List<String> lore)
    {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(itemName);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public void update(List<GUIItem> newGuiList)
    {
        allGuiItems = newGuiList;
    }
}
