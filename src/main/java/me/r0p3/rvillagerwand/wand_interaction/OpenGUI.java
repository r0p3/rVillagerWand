package me.r0p3.rvillagerwand.wand_interaction;

import me.r0p3.rvillagerwand.GUIConst;
import me.r0p3.rvillagerwand.GUIItem;
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
import java.util.List;

public class OpenGUI implements Listener
{
    List<GUIItem> allGuiItems;
    public OpenGUI(List<GUIItem> guiItems)
    {
        this.allGuiItems = guiItems;
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent e)
    {

        try
        {
            if ((e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))
                    && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().startsWith(GUIConst.DefaultWandName))
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
        ItemStack[] guiItems = new ItemStack[18];

        for(GUIItem item : allGuiItems)
        {
            if(player.hasPermission(item.Permission))
            {
                guiItems[size] = (guiItemSetup(item.Name, item.Icon, item.Lore));
                size++;
            }
        }

        Inventory GUI = Bukkit.createInventory(player, 18, GUIConst.GUITitle);
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
}
