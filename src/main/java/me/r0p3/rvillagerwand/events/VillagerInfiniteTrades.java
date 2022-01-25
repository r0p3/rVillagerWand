package me.r0p3.rvillagerwand.events;

import me.r0p3.rvillagerwand.RVillagerWand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;

public class VillagerInfiniteTrades implements Listener
{
    @EventHandler
    public void onVillagerTrade(InventoryOpenEvent e)
    {
        if(e.getInventory() instanceof MerchantInventory)
        {
            if(RVillagerWand.getPlugin(RVillagerWand.class).getConfig().getBoolean("Unlimitade_trades"))
            {
                for (MerchantRecipe recipe : ((MerchantInventory) e.getInventory()).getMerchant().getRecipes())
                {
                    recipe.setUses(0);
                    recipe.setMaxUses(Integer.MAX_VALUE);
                    recipe.setDemand(0);
                }
            }
        }
    }
}
