package me.r0p3.rvillagerwand.wand_interaction;

import me.r0p3.rvillagerwand.GUIConst;
import me.r0p3.rvillagerwand.GUIItem;
import me.r0p3.rvillagerwand.Permissions;
import me.r0p3.rvillagerwand.PlayerMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class UseWand implements Listener
{
    Player player;
    Villager villager;
    ZombieVillager zombieVillager;
    List<GUIItem> guiItems;
    public UseWand(List<GUIItem> guitems)
    {
        this.guiItems = guitems;
    }

    @EventHandler
    public void onWandHitVillager(EntityDamageByEntityEvent e)
    {
        if(e.getEntity() instanceof Villager && e.getDamager() instanceof Player)
        {
            player = (Player) e.getDamager();
            villager = (Villager) e.getEntity();
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            if(mainHand.hasItemMeta() && mainHand.getItemMeta().getDisplayName().startsWith(GUIConst.DefaultWandName))
            {
                String wandName = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                e.setCancelled(true);

                if (wandName.equals(GUIItem.generateWandName(GUIConst.DropName)))
                    dropInventory();
                else if (wandName.equals(GUIItem.generateWandName(GUIConst.FollowName)))
                    toggleFollow();
                else if (wandName.equals(GUIItem.generateWandName(GUIConst.GetHPName)))
                    getHP();
                else if (wandName.equals(GUIItem.generateWandName(GUIConst.GlowName)))
                    glow();
                else if (wandName.equals(GUIItem.generateWandName(GUIConst.HealName)))
                    heal();
                else if (wandName.equals(GUIItem.generateWandName(GUIConst.KillName)))
                    {
                        e.setCancelled(false);
                        kill();
                    }
                else if (wandName.equals(GUIItem.generateWandName(GUIConst.NewTradesName)))
                    newTrades();
                else if (wandName.equals(GUIItem.generateWandName(GUIConst.PickupName)))
                    pickup();
                else if (wandName.equals(GUIItem.generateWandName(GUIConst.ToggleAIName)))
                    toggleAI();
                else if (wandName.equals(GUIItem.generateWandName(GUIConst.ToggleInvisibleName)))
                    toggleInvisible();
                else if (wandName.equals(GUIItem.generateWandName(GUIConst.ToggleInvulnerableName)))
                    toggleInvulnerable();
                else if (wandName.equals(GUIItem.generateWandName(GUIConst.TogglePickUpItemsName)))
                    togglePickUpItems();
                else if (wandName.equals(GUIItem.generateWandName(GUIConst.ToggleSilentName)))
                    toggleSilent();
                else if (wandName.equals(GUIItem.generateWandName(GUIConst.ToggleTypeName)))
                    toggleType();
                else if (wandName.equals(GUIItem.generateWandName(GUIConst.ZombificationName)))
                    zombification();

            }
        }
        else if(e.getEntity() instanceof ZombieVillager && e.getDamager() instanceof Player)
        {
            player = (Player) e.getDamager();
            zombieVillager = (ZombieVillager) e.getEntity();
            e.setCancelled(true);
            if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals((GUIItem.generateWandName(GUIConst.ConvertZombieName))))
            {
                convertZombie();
            }
        }
    }

    //PLAYER METHODS
    private void dropInventory()
    {
        if(player.hasPermission(Permissions.DROP))
        {
            for (ItemStack item : villager.getInventory().getContents())
                if (item != null)
                {
                    Bukkit.getWorld(villager.getWorld().getName()).dropItem(villager.getLocation(), item);
                }
            villager.getInventory().clear();
        }
        else noPermssion();
    }

    private void toggleFollow()
    {
        if(player.hasPermission(Permissions.FOLLOW))
        {
            if(villager.getTarget() == null || villager.getTarget() != player)
            {
                villager.setTarget(player);
                PlayerMessages.sendMessage(player, "This villager will try to follow you");
            }
            else if (villager.getTarget().equals(player))
            {
                villager.setTarget(null);
                PlayerMessages.sendMessage(player, "This villager will not follow you anymore");
            }
        }
        else noPermssion();
    }

    private void getHP()
    {
        if(player.hasPermission(Permissions.GETHP))
            PlayerMessages.sendMessage(player, "This villagers HP is: " + villager.getHealth());
    }

    private void glow()
    {
        if(player.hasPermission(Permissions.GLOW))
        {
            if(!villager.isGlowing())
            {
                for(GUIItem item : guiItems)
                {
                    if(item.Permission.equals(Permissions.GLOW))
                    if (removeItemInInventory(item.MaterialCost, item.CostAmount))
                        villager.setGlowing(true);
                }
            }
            else
            {
                villager.setGlowing(false);
            }
        }
    }

    private void heal()
    {
        if(player.hasPermission(Permissions.HEAL))
        {
            if(villager.getHealth() == 20)
            {
                PlayerMessages.sendMessage(player, "This villager is already fully healed");
                return;
            }
            for(GUIItem item : guiItems)
                if(item.Permission == Permissions.HEAL)
                {
                    if(removeItemInInventory(item.MaterialCost, item.CostAmount))
                    {
                        villager.setHealth(20);
                        PlayerMessages.sendMessage(player, "This villager has been healed");
                        break;
                    }
                }

        }
    }

    private void kill()
    {
        if(player.hasPermission(Permissions.KILL))
        {
            if (player.hasPermission(Permissions.DROP))
                dropInventory();
            villager.setHealth(0);
            PlayerMessages.sendMessage(player, "That poor villager didn't stand a chance");
        }
        else noPermssion();
    }

    private void newTrades()
    {
        if(villager.isAdult() && (!villager.getProfession().equals(Villager.Profession.NONE) && !villager.getProfession().equals(Villager.Profession.NITWIT)) && villager.getVillagerExperience() == 0)
        {
            Villager.Profession profession = villager.getProfession();
            villager.setProfession(Villager.Profession.NONE);
            villager.setProfession(profession);
        }
        else
            PlayerMessages.sendMessage(player, net.md_5.bungee.api.ChatColor.RED + "This only works if the villager is an adult, has a profession and has 0 exp");
    }

    private void pickup()
    {
        if(player.hasPermission(Permissions.PICKUP))
        {
            if (villager.isAdult() && (villager.getProfession() == Villager.Profession.NONE || villager.getProfession() == Villager.Profession.NITWIT))
            {
                Bukkit.getWorld(villager.getWorld().getName()).dropItem(villager.getLocation(), new ItemStack(Material.VILLAGER_SPAWN_EGG));
                villager.remove();
            }
        }
        else noPermssion();
    }

    private void toggleAI()
    {
        if(player.hasPermission(Permissions.TOGGLEAI))
        {
            villager.setAI(!villager.hasAI());
            PlayerMessages.sendMessage(player, "This villagers Ai is now set to: " + villager.hasAI());
        }
    }

    private void toggleInvisible()
    {
        if(player.hasPermission(Permissions.TOGGLEINVISIBLE))
            villager.setInvisible(!villager.isInvisible());
    }

    private void toggleInvulnerable()
    {
        if(player.hasPermission(Permissions.TOGGLEINVULNERABLE))
        {
            villager.setInvulnerable(!villager.isInvulnerable());
            PlayerMessages.sendMessage(player, "This villager invulnerability is now set to: " + villager.isInvulnerable());
        }
    }

    private void togglePickUpItems()
    {
        if(player.hasPermission(Permissions.TOGGLEPICKUPITEMS))
        {
            villager.setCanPickupItems(!villager.getCanPickupItems());
            String message = (villager.getCanPickupItems())?"This villager can now pick up items":"This villager can't pick up items";
            PlayerMessages.sendMessage(player, message);
        }
    }

    private void toggleSilent()
    {
        if(player.hasPermission(Permissions.TOGGLESILENT))
        {
            villager.setSilent(!villager.isSilent());
            String message = (villager.isSilent())? "This villager is now silent" : "This villager is no longer silent";
            PlayerMessages.sendMessage(player, message);
        }
    }

    private void toggleType()
    {
        if(player.hasPermission(Permissions.TOGGLETYPE))
        {
            switch (villager.getVillagerType())
            {
                case PLAINS: villager.setVillagerType(Villager.Type.TAIGA);break;
                case TAIGA: villager.setVillagerType(Villager.Type.SNOW);break;
                case SNOW: villager.setVillagerType(Villager.Type.SWAMP);break;
                case SWAMP: villager.setVillagerType(Villager.Type.DESERT);break;
                case DESERT: villager.setVillagerType(Villager.Type.JUNGLE);break;
                case JUNGLE: villager.setVillagerType(Villager.Type.SAVANNA);break;
                case SAVANNA: villager.setVillagerType(Villager.Type.PLAINS);break;
            }
        }
        else noPermssion();
    }

    private void zombification()
    {
        if(player.hasPermission(Permissions.ZOMBIFICATION))
        {
            for (GUIItem item : guiItems)
            {
                if(item.Permission == Permissions.ZOMBIFICATION)
                {
                    if (villager.isAdult() && (villager.getProfession().equals(Villager.Profession.NONE) || villager.getProfession().equals(Villager.Profession.NITWIT)))
                    {
                        if (removeItemInInventory(item.MaterialCost, item.CostAmount))
                        {
                            Entity entity = Bukkit.getWorld(villager.getWorld().getName()).spawnEntity(villager.getLocation(), EntityType.ZOMBIE_VILLAGER);
                            ((ZombieVillager) entity).setAdult();
                            ((ZombieVillager) entity).setVillagerProfession(Villager.Profession.NONE);
                            villager.remove();
                        }
                    }
                }
            }
        }
        else noPermssion();
    }

    private void convertZombie()
    {
        if(player.hasPermission(Permissions.CONVERTZOMBIE))
        {
            for (GUIItem item : guiItems)
                if(item.Permission == Permissions.CONVERTZOMBIE)
                {
                    if(removeItemInInventory(item.MaterialCost, item.CostAmount))
                    {
                        zombieVillager.setConversionTime(0);
                        break;
                    }
                }
        }
        else noPermssion();
    }

    //METHODS
    private boolean removeItemInInventory(Material item, int amount)
    {
        if(player.hasPermission(Permissions.NOITEMSNEEDED))
            return true;

        if(!player.getInventory().contains(item))
            return false;
        int counter = 0;
        for (ItemStack itemStack : player.getInventory().getContents())
        {
            if (itemStack.getType().equals(item))
                counter += itemStack.getAmount();
            if(counter >= amount)
                break;
        }
        if(counter >= amount)
        {
            player.getInventory().removeItem(new ItemStack(item, amount));
            return true;
        }
        else
        {
            PlayerMessages.sendMessage(player, ChatColor.RED + "You don't have the required items for this action: " + amount + " " + item.name());
            return false;
        }

    }

    private void noPermssion()
    {
        PlayerMessages.sendMessagec(player, net.md_5.bungee.api.ChatColor.RED, "You do not have the required permission to use this wand");
    }
}
