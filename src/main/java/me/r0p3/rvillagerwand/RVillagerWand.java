package me.r0p3.rvillagerwand;

import me.r0p3.rvillagerwand.commands.Reload;
import me.r0p3.rvillagerwand.commands.Wand;
import me.r0p3.rvillagerwand.events.VillagerInfiniteTrades;
import me.r0p3.rvillagerwand.wand_interaction.GUIClickItem;
import me.r0p3.rvillagerwand.wand_interaction.OpenGUI;
import me.r0p3.rvillagerwand.wand_interaction.UseWand;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RVillagerWand extends JavaPlugin
{
    public List<GUIItem> guiItems = new ArrayList<>();
    public static PlayerMessages playerMessages;

    OpenGUI openGUI;
    GUIClickItem guiClickItem;
    UseWand useWand;

    @Override
    public void onEnable()
    {

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        guiItems = generateWands();
        openGUI = new OpenGUI(guiItems);
        guiClickItem = new GUIClickItem(guiItems);
        useWand = new UseWand(guiItems);

        getServer().getPluginManager().registerEvents(openGUI, this);
        getServer().getPluginManager().registerEvents(guiClickItem, this);
        getServer().getPluginManager().registerEvents(useWand, this);
        getServer().getPluginManager().registerEvents(new VillagerInfiniteTrades(), this);
        getCommand("wand").setExecutor(new Wand());
        getCommand("wandreload").setExecutor(new Reload(this, openGUI, guiClickItem, useWand));
        playerMessages = new PlayerMessages();

        updateConfig();
    }

    private void updateConfig()
    {
        File fileOnDisk = new File(this.getDataFolder(), "config.yml");
        FileConfiguration fileOnDiskConfiguration = YamlConfiguration.loadConfiguration(fileOnDisk);

        for (String section : getConfig().getConfigurationSection("").getKeys(true))
        {
            if(fileOnDiskConfiguration.get(section) != null) continue;
            fileOnDiskConfiguration.set(section, getConfig().get(section));
        }
        updateComments(fileOnDiskConfiguration);
        try
        {
            fileOnDiskConfiguration.save(fileOnDisk);
        }
        catch (Exception e)
        {
            getServer().getLogger().warning("Error when trying to update config.yml");
        }
    }

    private void updateComments(FileConfiguration fileOnDiskConfiguration)
    {
        fileOnDiskConfiguration.setComments("NEWTRADES.Only_show_enchanted_books", Arrays.asList("If true only allow Success_message when a Villager is offering an enchanted book"));
    }

    public void reload()
    {
        guiItems = generateWands();
    }


    private List<GUIItem> generateWands()
    {
        //CHANGE CLOTHES
        List<GUIItem> tempItems = new ArrayList<GUIItem>();
        tempItems.add(new GUIItem(getConfig().getString("TOGGLECLOTHES.Name"),
                Material.getMaterial(getConfig().getString("TOGGLECLOTHES.Icon")),
                (List<String>)getConfig().getList("TOGGLECLOTHES.Lore"),
                Material.getMaterial(getConfig().getString("TOGGLECLOTHES.Item")),
                getConfig().getInt("TOGGLECLOTHES.Amount"),
                Permissions.TOGGLETYPE));

        //CHANGE PROFESSION
        tempItems.add(new GUIItem(getConfig().getString("TOGGLEPROFESSION.Name"),
                Material.getMaterial(getConfig().getString("TOGGLEPROFESSION.Icon")),
                (List<String>)getConfig().getList("TOGGLEPROFESSION.Lore"),
                Material.getMaterial(getConfig().getString("TOGGLEPROFESSION.Item")),
                getConfig().getInt("TOGGLEPROFESSION.Amount"),
                Permissions.TOGGLEPROFESSION));

        //AWARENESS
        tempItems.add(new GUIItem(getConfig().getString("AWARE.Name"),
                Material.getMaterial(getConfig().getString("AWARE.Icon")),
                (List<String>)getConfig().getList("AWARE.Lore"),
                Material.getMaterial(getConfig().getString("AWARE.Item")),
                getConfig().getInt("AWARE.Amount"),
                Permissions.AWARE));

        //CONVERT ZOMBIE TO VILLAGER
        tempItems.add(new GUIItem(getConfig().getString("ZOMBIETOVILLAGER.Name"),
                Material.getMaterial(getConfig().getString("ZOMBIETOVILLAGER.Icon")),
                (List<String>)getConfig().getList("ZOMBIETOVILLAGER.Lore"),
                Material.getMaterial(getConfig().getString("ZOMBIETOVILLAGER.Item")),
                getConfig().getInt("ZOMBIETOVILLAGER.Amount"),
                Permissions.CONVERTZOMBIE));

        //CONVERT VILLAGER TO ZOMBIE
        tempItems.add(new GUIItem(getConfig().getString("VILLAGERTOZOMBIE.Name"),
                Material.getMaterial(getConfig().getString("VILLAGERTOZOMBIE.Icon")),
                (List<String>)getConfig().getList("VILLAGERTOZOMBIE.Lore"),
                Material.getMaterial(getConfig().getString("VILLAGERTOZOMBIE.Item")),
                getConfig().getInt("VILLAGERTOZOMBIE.Amount"),
                Permissions.ZOMBIFICATION));

        //Drop villagers inventory
        tempItems.add(new GUIItem(getConfig().getString("DROP.Name"),
                Material.getMaterial(getConfig().getString("DROP.Icon")),
                (List<String>)getConfig().getList("DROP.Lore"),
                Material.getMaterial(getConfig().getString("DROP.Item")),
                getConfig().getInt("DROP.Amount"),
                Permissions.DROP));

        //FOLLOW
        tempItems.add(new GUIItem(getConfig().getString("FOLLOW.Name"),
                Material.getMaterial(getConfig().getString("FOLLOW.Icon")),
                (List<String>)getConfig().getList("FOLLOW.Lore"),
                Material.getMaterial(getConfig().getString("FOLLOW.Item")),
                getConfig().getInt("FOLLOW.Amount"),
                Permissions.FOLLOW));

        //GET HP
        tempItems.add(new GUIItem(getConfig().getString("GETHP.Name"),
                Material.getMaterial(getConfig().getString("GETHP.Icon")),
                (List<String>)getConfig().getList("GETHP.Lore"),
                Material.getMaterial(getConfig().getString("GETHP.Item")),
                getConfig().getInt("GETHP.Amount"),
                Permissions.GETHP));

        //GIVE EXP
        tempItems.add(new GUIItem(getConfig().getString("GIVEEXP.Name"),
                Material.getMaterial(getConfig().getString("GIVEEXP.Icon")),
                (List<String>)getConfig().getList("GIVEEXP.Lore"),
                Material.getMaterial(getConfig().getString("GIVEEXP.Item")),
                getConfig().getInt("GIVEEXP.Amount"),
                Permissions.GIVEEXP));

        //GLOW
        tempItems.add(new GUIItem(getConfig().getString("GLOW.Name"),
                Material.getMaterial(getConfig().getString("GLOW.Icon")),
                (List<String>)getConfig().getList("GLOW.Lore"),
                Material.getMaterial(getConfig().getString("GLOW.Item")),
                getConfig().getInt("GLOW.Amount"),
                Permissions.GLOW));

        //HEAL
        tempItems.add(new GUIItem(getConfig().getString("HEAL.Name"),
                Material.getMaterial(getConfig().getString("HEAL.Icon")),
                (List<String>)getConfig().getList("HEAL.Lore"),
                Material.getMaterial(getConfig().getString("HEAL.Item")),
                getConfig().getInt("HEAL.Amount"),
                Permissions.HEAL));

        //KILL
        tempItems.add(new GUIItem(getConfig().getString("KILL.Name"),
                Material.getMaterial(getConfig().getString("KILL.Icon")),
                (List<String>)getConfig().getList("KILL.Lore"),
                Material.getMaterial(getConfig().getString("KILL.Item")),
                getConfig().getInt("KILL.Amount"),
                Permissions.KILL));

        //NEW TRADES
        tempItems.add(new GUIItem(getConfig().getString("NEWTRADES.Name"),
                Material.getMaterial(getConfig().getString("NEWTRADES.Icon")),
                (List<String>)getConfig().getList("NEWTRADES.Lore"),
                Material.getMaterial(getConfig().getString("NEWTRADES.Item")),
                getConfig().getInt("NEWTRADES.Amount"),
                Permissions.NEWTRADES));

        //PICKUP
        tempItems.add(new GUIItem(getConfig().getString("PICKUP.Name"),
                Material.getMaterial(getConfig().getString("PICKUP.Icon")),
                (List<String>)getConfig().getList("PICKUP.Lore"),
                Material.getMaterial(getConfig().getString("PICKUP.Item")),
                getConfig().getInt("PICKUP.Amount"),
                Permissions.PICKUP));

        //TOGGLE AI
        tempItems.add(new GUIItem(getConfig().getString("TOGGLEAI.Name"),
                Material.getMaterial(getConfig().getString("TOGGLEAI.Icon")),
                (List<String>)getConfig().getList("TOGGLEAI.Lore"),
                Material.getMaterial(getConfig().getString("TOGGLEAI.Item")),
                getConfig().getInt("TOGGLEAI.Amount"),
                Permissions.TOGGLEAI));

        //TOGGLEINVISIBILITY
        tempItems.add(new GUIItem(getConfig().getString("TOGGLEINVISIBILITY.Name"),
                Material.getMaterial(getConfig().getString("TOGGLEINVISIBILITY.Icon")),
                (List<String>)getConfig().getList("TOGGLEINVISIBILITY.Lore"),
                Material.getMaterial(getConfig().getString("TOGGLEINVISIBILITY.Item")),
                getConfig().getInt("TOGGLEINVISIBILITY.Amount"),
                Permissions.TOGGLEINVISIBLE));

        //TOGGLEINVULNERABLE
        tempItems.add(new GUIItem(getConfig().getString("TOGGLEINVULNERABLE.Name"),
                Material.getMaterial(getConfig().getString("TOGGLEINVULNERABLE.Icon")),
                (List<String>)getConfig().getList("TOGGLEINVULNERABLE.Lore"),
                Material.getMaterial(getConfig().getString("TOGGLEINVULNERABLE.Item")),
                getConfig().getInt("TOGGLEINVULNERABLE.Amount"),
                Permissions.TOGGLEINVULNERABLE));

        //TOGGLEPICKUPITEMS
        tempItems.add(new GUIItem(getConfig().getString("TOGGLEPICKUPITEMS.Name"),
                Material.getMaterial(getConfig().getString("TOGGLEPICKUPITEMS.Icon")),
                (List<String>)getConfig().getList("TOGGLEPICKUPITEMS.Lore"),
                Material.getMaterial(getConfig().getString("TOGGLEPICKUPITEMS.Item")),
                getConfig().getInt("TOGGLEPICKUPITEMS.Amount"),
                Permissions.TOGGLEPICKUPITEMS));

        //TOGGLEMUTE
        tempItems.add(new GUIItem(getConfig().getString("TOGGLEMUTE.Name"),
                Material.getMaterial(getConfig().getString("TOGGLEMUTE.Icon")),
                (List<String>)getConfig().getList("TOGGLEMUTE.Lore"),
                Material.getMaterial(getConfig().getString("TOGGLEMUTE.Item")),
                getConfig().getInt("TOGGLEMUTE.Amount"),
                Permissions.TOGGLESILENT));

        //UNEMPLOYMENT
        tempItems.add(new GUIItem(getConfig().getString("UNEMPLOYMENT.Name"),
                Material.getMaterial(getConfig().getString("UNEMPLOYMENT.Icon")),
                (List<String>)getConfig().getList("UNEMPLOYMENT.Lore"),
                Material.getMaterial(getConfig().getString("UNEMPLOYMENT.Item")),
                getConfig().getInt("UNEMPLOYMENT.Amount"),
                Permissions.UNEMPLOYMENT));

        return tempItems;
    }
}
