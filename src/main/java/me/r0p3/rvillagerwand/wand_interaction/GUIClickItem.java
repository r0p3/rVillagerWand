package me.r0p3.rvillagerwand.wand_interaction;

import me.r0p3.rvillagerwand.GUIConst;
import me.r0p3.rvillagerwand.GUIItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class GUIClickItem implements Listener
{
    List<GUIItem> guiItems;
    public GUIClickItem (List<GUIItem> guiItems)
    {
        this.guiItems = guiItems;
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent e)
    {
        if(e.getView().getTitle().equals(GUIConst.GUITitle))
        {
            Player player = (Player)e.getWhoClicked();
            ItemStack wand = player.getInventory().getItemInMainHand();
            if(e.getClickedInventory() != null && e.getCurrentItem() != null && !e.getClickedInventory().equals(player.getInventory()))
            {
                Material clickedItem = e.getCurrentItem().getType();
                for (GUIItem item: guiItems)
                {
                    if(clickedItem.equals(item.Icon))
                        wand.setItemMeta(item.getMeta(wand.getItemMeta()));
                }
                player.closeInventory();
                e.setCancelled(true);
            }
            if(e.getClickedInventory() != null && e.getClickedInventory().equals(player.getInventory()))
                e.setCancelled(true);

        }
    }

}
