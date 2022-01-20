package me.r0p3.rvillagerwand.wand_interaction;

import me.r0p3.rvillagerwand.*;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.List;

public class UseWand implements Listener
{
    public List<GUIItem> guiItems;
    Plugin plugin = RVillagerWand.getPlugin(RVillagerWand.class);
    Player player;
    Villager villager;
    ZombieVillager zombieVillager;
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
            if(mainHand.hasItemMeta() && mainHand.getItemMeta().getDisplayName().startsWith(PlayerMessages.textColor(plugin.getConfig().getString("WAND.Name"))))
            {
                String wandName = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                e.setCancelled(true);

                if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("DROP.Name"))))
                    dropInventory(false);
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("FOLLOW.Name"))))
                    toggleFollow();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("GETHP.Name"))))
                    getHP();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("GLOW.Name"))))
                    glow();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("GIVEEXP.Name"))))
                    giveEXP();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("HEAL.Name"))))
                    heal();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("KILL.Name"))))
                        kill();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("NEWTRADES.Name"))))
                    newTrades();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("PICKUP.Name"))))
                    pickup();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("TOGGLEAI.Name"))))
                    toggleAI();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("AWARE.Name"))))
                    toggleAware();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("TOGGLEINVISIBILITY.Name"))))
                    toggleInvisible();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("TOGGLEINVULNERABLE.Name"))))
                    toggleInvulnerable();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("TOGGLEPICKUPITEMS.Name"))))
                    togglePickUpItems();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("TOGGLEMUTE.Name"))))
                    toggleSilent();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("TOGGLECLOTHES.Name"))))
                    toggleType();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("TOGGLEPROFESSION.Name"))))
                    toggleProfession();
                else if(wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("UNEMPLOYMENT.Name"))))
                    unemployment();
                else if (wandName.equals(GUIItem.generateWandName(plugin.getConfig().getString("VILLAGERTOZOMBIE.Name"))))
                    zombification();

            }
        }
        else if(e.getEntity() instanceof ZombieVillager && e.getDamager() instanceof Player)
        {
            player = (Player) e.getDamager();
            zombieVillager = (ZombieVillager) e.getEntity();
            e.setCancelled(true);
            if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals((GUIItem.generateWandName(plugin.getConfig().getString("ZOMBIETOVILLAGER.Name")))))
            {
                convertZombie();
            }
        }
    }

    //PLAYER METHODS
    private void dropInventory(boolean killed)
    {
        GUIItem guiItem = getGUIItem(Permissions.DROP);
        if(killed || player.hasPermission(guiItem.Permission))
        {
            boolean emptyInventory = true;
            for (ItemStack item : villager.getInventory().getContents())
                if (item != null)
                {
                    Bukkit.getWorld(villager.getWorld().getName()).dropItem(villager.getLocation(), item);
                    emptyInventory = false;
                }
            if (!emptyInventory && (killed || removeItemInInventory(guiItem)))
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "DROP.Message");
            else
                if(!killed)
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "DROP.Empty_inventory_message");
            villager.getInventory().clear();

        }
        else noPermssion();
    }

    private void toggleFollow()
    {
        GUIItem guiItem = getGUIItem(Permissions.FOLLOW);
        if(player.hasPermission(guiItem.Permission))
        {
            if (removeItemInInventory(guiItem))
            {
                if (player.hasPermission(Permissions.FOLLOW))
                {
                    if (villager.getTarget() == null || villager.getTarget() != player)
                    {
                        villager.setTarget(player);
                        RVillagerWand.playerMessages.sendMessageFromConfig(player, "FOLLOW.Start_message");
                    }
                    else if (villager.getTarget().equals(player))
                    {
                        villager.setTarget(null);
                        RVillagerWand.playerMessages.sendMessageFromConfig(player, "FOLLOW.Stop_message");
                    }
                }
            }
        }
        else noPermssion();
    }

    private void getHP()
    {
        GUIItem guiItem = getGUIItem(Permissions.GETHP);
        if(player.hasPermission(guiItem.Permission))
        {
            if (removeItemInInventory(guiItem))
            {
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "GETHP.Message", "{hp}",  Math.round(villager.getHealth() *10.0)/10.0 + "");
            }
        }
    }

    private void glow()
    {
        GUIItem guiItem = getGUIItem(Permissions.GLOW);
        if(player.hasPermission(guiItem.Permission))
        {
            if (removeItemInInventory(guiItem))
            {
                villager.setGlowing(!villager.isGlowing());
                if(villager.isGlowing())
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "GLOW.Start_message", "{glow}", villager.isGlowing() + "");
                else
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "GLOW.Stop_message", "{glow}", villager.isGlowing() + "");
            }
        }
    }

    private void heal()
    {
        GUIItem guiItem = getGUIItem(Permissions.HEAL);
        if(player.hasPermission(guiItem.Permission))
        {
            if (villager.getHealth() != 20)
            {
                if (removeItemInInventory(guiItem))
                {
                    villager.setHealth(20);
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "HEAL.Healed_message");
                }
            }
            else
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "HEAL.Already_full_health_message");

        }
    }

    private void giveEXP()
    {
        GUIItem guiItem = getGUIItem(Permissions.GIVEEXP);
        if(player.hasPermission(guiItem.Permission))
        {
            if(villager.getProfession().equals(Villager.Profession.NONE) || villager.getProfession().equals(Villager.Profession.NITWIT))
            {
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "GIVEEXP.No_proffesion_message", "{exp}", villager.getVillagerExperience() + "");
                return;
            }

            if (villager.getVillagerExperience() <= 250)
            {

                if (removeItemInInventory(guiItem))
                {
                    villager.setVillagerExperience(villager.getVillagerExperience() + plugin.getConfig().getInt("GIVEEXP.EXP"));
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "GIVEEXP.GiveEXP_message", "{exp}", villager.getVillagerExperience() + "");
                }
            }
            else
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "GIVEEXP.HighestEXP_message", "{exp}", villager.getVillagerExperience() + "");

        }
    }

    private void kill()
    {
        GUIItem guiItem = getGUIItem(Permissions.KILL);
        if(player.hasPermission(guiItem.Permission))
        {
            if (removeItemInInventory(guiItem))
            {
                if (player.hasPermission(Permissions.DROP))
                    dropInventory(plugin.getConfig().getBoolean("KILL.Drop"));
                villager.remove();
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "KILL.Message");
            }
        }
        else noPermssion();
    }

    private void newTrades()
    {
        GUIItem guiItem = getGUIItem(Permissions.NEWTRADES);
        if(player.hasPermission(guiItem.Permission))
        {
            if (villager.isAdult() && (!villager.getProfession().equals(Villager.Profession.NONE) && !villager.getProfession().equals(Villager.Profession.NITWIT)) && villager.getVillagerExperience() == 0 && villager.getVillagerLevel() == 1)
            {
                if (removeItemInInventory(guiItem))
                {
                    Villager.Profession profession = villager.getProfession();
                    villager.setProfession(Villager.Profession.NONE);
                    villager.setProfession(profession);
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "NEWTRADES.Success_message");
                }
            }
            else
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "NEWTRADES.Fail_message");
        }
        else noPermssion();

    }

    private void pickup()
    {
        GUIItem guiItem = getGUIItem(Permissions.PICKUP);
        if(player.hasPermission(guiItem.Permission))
        {
            if (!villager.isAdult())
            {
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "PICKUP.Not_adult_message");
                return;
            }
            if(villager.getProfession() != Villager.Profession.NONE && villager.getProfession() != Villager.Profession.NITWIT)
            {
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "PICKUP.Has_profession_message");
                return;
            }
            if (removeItemInInventory(guiItem))
            {
                Bukkit.getWorld(villager.getWorld().getName()).dropItem(villager.getLocation(), new ItemStack(Material.VILLAGER_SPAWN_EGG));
                villager.remove();
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "PICKUP.Message");
            }
        }
        else noPermssion();
    }

    private void toggleAI()
    {
        GUIItem guiItem = getGUIItem(Permissions.TOGGLEAI);
        if(player.hasPermission(guiItem.Permission))
        {
            if (removeItemInInventory(guiItem))
            {
                villager.setAI(!villager.hasAI());
                if (villager.hasAI())
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "TOGGLEAI.On_message");
                else
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "TOGGLEAI.Off_message");
            }
        }
        else noPermssion();
    }

    private void toggleInvisible()
    {
        GUIItem guiItem = getGUIItem(Permissions.TOGGLEINVISIBLE);
        if(player.hasPermission(guiItem.Permission))
        {
            if (removeItemInInventory(guiItem))
            {
                villager.setInvisible(!villager.isInvisible());
                if (villager.isInvisible())
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "TOGGLEINVISIBILITY.Is_visible_message");
                else
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "TOGGLEINVISIBILITY.Is_invisible_message");
            }
        }
        else noPermssion();
    }

    private void toggleInvulnerable()
    {
        GUIItem guiItem = getGUIItem(Permissions.TOGGLEINVULNERABLE);
        if(player.hasPermission(guiItem.Permission))
        {
            if (removeItemInInventory(guiItem))
            {
                villager.setInvulnerable(!villager.isInvulnerable());
                if (villager.isInvulnerable())
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "TOGGLEINVULNERABLE.Invulnerable_message");
                else
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "TOGGLEINVULNERABLE.Vulnerable_message");
            }
        }
        else noPermssion();
    }

    private void togglePickUpItems()
    {
        GUIItem guiItem = getGUIItem(Permissions.TOGGLEPICKUPITEMS);
        if(player.hasPermission(guiItem.Permission))
        {
            if (removeItemInInventory(guiItem))
            {
                villager.setCanPickupItems(!villager.getCanPickupItems());
                if (villager.getCanPickupItems())
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "TOGGLEPICKUPITEMS.Pickup_on_message");
                else
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "TOGGLEPICKUPITEMS.Pickup_off_message");
            }
        }
        else noPermssion();
    }

    private void toggleAware()
    {
        GUIItem guiItem = getGUIItem(Permissions.AWARE);
        if(player.hasPermission(guiItem.Permission))
        {
            if (removeItemInInventory(guiItem))
            {
                villager.setAware(!villager.isAware());
                if (villager.isAware())
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "AWARE.On_message");
                else
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "AWARE.Off_message");
            }
        }
        else noPermssion();
    }

    private void toggleSilent()
    {
        GUIItem guiItem = getGUIItem(Permissions.TOGGLESILENT);
        if(player.hasPermission(guiItem.Permission))
        {
            if (removeItemInInventory(guiItem))
            {
                villager.setSilent(!villager.isSilent());
                if (villager.isSilent())
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "TOGGLEMUTE.Muted_message");
                else
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "TOGGLEMUTE.Unmuted_message");
            }
        }
        else noPermssion();
    }

    private void toggleType()
    {
        GUIItem guiItem = getGUIItem(Permissions.TOGGLETYPE);
        if(player.hasPermission(guiItem.Permission))
        {
            if (removeItemInInventory(guiItem))
            {
                switch (villager.getVillagerType())
                {
                    case PLAINS:villager.setVillagerType(Villager.Type.TAIGA);break;
                    case TAIGA:villager.setVillagerType(Villager.Type.SNOW);break;
                    case SNOW:villager.setVillagerType(Villager.Type.SWAMP);break;
                    case SWAMP:villager.setVillagerType(Villager.Type.DESERT);break;
                    case DESERT:villager.setVillagerType(Villager.Type.JUNGLE);break;
                    case JUNGLE:villager.setVillagerType(Villager.Type.SAVANNA);break;
                    case SAVANNA:villager.setVillagerType(Villager.Type.PLAINS);break;
                }
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "TOGGLECLOTHES.Message", "{type}", villager.getVillagerType().name());
            }
        }
        else noPermssion();
    }

    private void toggleProfession()
    {
        GUIItem guiItem = getGUIItem(Permissions.TOGGLEPROFESSION);
        if(player.hasPermission(guiItem.Permission))
        {
            if (removeItemInInventory(guiItem))
            {
                switch (villager.getProfession())
                {
                    case NONE:villager.setProfession(Villager.Profession.ARMORER);break;
                    case ARMORER:villager.setProfession(Villager.Profession.BUTCHER);break;
                    case BUTCHER:villager.setProfession(Villager.Profession.CARTOGRAPHER);break;
                    case CARTOGRAPHER:villager.setProfession(Villager.Profession.CLERIC);break;
                    case CLERIC:villager.setProfession(Villager.Profession.FARMER);break;
                    case FARMER:villager.setProfession(Villager.Profession.FISHERMAN);break;
                    case FISHERMAN:villager.setProfession(Villager.Profession.FLETCHER);break;
                    case FLETCHER:villager.setProfession(Villager.Profession.LEATHERWORKER);break;
                    case LEATHERWORKER:villager.setProfession(Villager.Profession.LIBRARIAN);break;
                    case LIBRARIAN:villager.setProfession(Villager.Profession.MASON);break;
                    case MASON:villager.setProfession(Villager.Profession.SHEPHERD);break;
                    case SHEPHERD:villager.setProfession(Villager.Profession.TOOLSMITH);break;
                    case TOOLSMITH:villager.setProfession(Villager.Profession.WEAPONSMITH);break;
                    case WEAPONSMITH:villager.setProfession(Villager.Profession.NITWIT);break;
                    case NITWIT:villager.setProfession(Villager.Profession.NONE);break;

                }
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "TOGGLEPROFESSION.Message", "{type}", villager.getProfession().name());
            }
        }
        else noPermssion();
    }


    private void unemployment()
    {
        GUIItem guiItem = getGUIItem(Permissions.UNEMPLOYMENT);
        if(player.hasPermission(guiItem.Permission))
        {
            if(villager.getVillagerLevel() > 1 || villager.getVillagerExperience() > 0 || !villager.getProfession().equals(Villager.Profession.NONE))
            {
                if (removeItemInInventory(guiItem))
                {
                    Location l = villager.getMemory(MemoryKey.JOB_SITE);
                    villager.setVillagerLevel(1);
                    villager.setVillagerExperience(0);
                    villager.setProfession(Villager.Profession.NONE);
                    villager.setMemory(MemoryKey.JOB_SITE, null);
                    if (l != null)
                    {
                        Block workstation = villager.getWorld().getBlockAt(l);
                        Material workstationType = workstation.getType();
                        workstation.setType(Material.AIR);
                        workstation.setType(workstationType);
                    }
                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "UNEMPLOYMENT.Success_message");
                }
            }
            else
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "UNEMPLOYMENT.Failed_message");
        }
        else noPermssion();
    }

    private void zombification()
    {
        GUIItem guiItem = getGUIItem(Permissions.ZOMBIFICATION);
        if(player.hasPermission(guiItem.Permission))
        {
            if (villager.isAdult())
            {
                if (removeItemInInventory(guiItem))
                {
                    Difficulty difficulty = player.getWorld().getDifficulty();
                    Entity entity = Bukkit.getWorld(villager.getWorld().getName()).spawnEntity(villager.getLocation(), EntityType.ZOMBIE_VILLAGER);
                    player.getWorld().setDifficulty(Difficulty.HARD);
                    villager.damage(villager.getHealth(), entity);
                    player.getWorld().setDifficulty(difficulty);
                    entity.remove();

                    RVillagerWand.playerMessages.sendMessageFromConfig(player, "VILLAGERTOZOMBIE.Success_message");
                }
            }
            else
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "VILLAGERTOZOMBIE.Fail_message");


        }
        else noPermssion();
    }

    private void convertZombie()
    {
        GUIItem guiItem = getGUIItem(Permissions.UNEMPLOYMENT);
        if (player.hasPermission(guiItem.Permission))
        {
            if (removeItemInInventory(guiItem))
            {
                zombieVillager.setConversionTime(10);
                zombieVillager.setConversionPlayer(player);
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "ZOMBIETOVILLAGER.Success_message");
            }
        }
        else noPermssion();
    }

    //TODO TEST IF THIS WORKDS
    //METHODS
    private boolean removeItemInInventory(GUIItem guiItem)
    {
        if(player.hasPermission(Permissions.NOITEMSNEEDED) || guiItem.MaterialCost == null && guiItem.CostAmount == 0)
            return true;
        int totalAmount = 0;
        for (ItemStack itemStack : player.getInventory().getContents())
            if(itemStack != null && itemStack.getType().equals(guiItem.MaterialCost))
            {
                totalAmount += itemStack.getAmount();
                if(totalAmount >= guiItem.CostAmount)
                    break;
            }
        if(totalAmount < guiItem.CostAmount || player.getInventory().removeItem(new ItemStack(guiItem.MaterialCost, guiItem.CostAmount)).size() != 0)
        {
            if(plugin.getConfig().getBoolean("NOTENOUGHITEMS.Show_message"))
                RVillagerWand.playerMessages.sendMessageFromConfig(player, "NOTENOUGHITEMS.Message");
            return false;
        }
        return true;
    }

    private void noPermssion()
    {
        if(plugin.getConfig().getBoolean("PERMSSION.Show_message"))
            RVillagerWand.playerMessages.sendMessageFromConfig(player, "PERMSSION.Message");
    }

    private GUIItem getGUIItem(String permission)
    {
        for (GUIItem guiItem : guiItems)
            if(guiItem.Permission.equals(permission))
                return guiItem;
        return null;
    }
}
