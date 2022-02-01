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
import org.bukkit.plugin.PluginManager;
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

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(openGUI, this);
        pluginManager.registerEvents(guiClickItem, this);
        pluginManager.registerEvents(useWand, this);
        pluginManager.registerEvents(new VillagerInfiniteTrades(), this);
        getCommand("wand").setExecutor(new Wand());
        getCommand("wandreload").setExecutor(new Reload(this, openGUI, guiClickItem, useWand));
        playerMessages = new PlayerMessages();

        updateConfig();
    }

    private void updateConfig()
    {
        File fileOnDisk = new File(this.getDataFolder(), "config.yml");
        FileConfiguration fileOnDiskConfiguration = YamlConfiguration.loadConfiguration(fileOnDisk);
        FileConfiguration config = getConfig();

        for (String section : config.getConfigurationSection("").getKeys(true))
        {
            if(fileOnDiskConfiguration.get(section) != null) continue;
            fileOnDiskConfiguration.set(section, config.get(section));
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

        List<GUIItem> tempItems = new ArrayList<GUIItem>();

        FileConfiguration config = getConfig();

        //CHANGE CLOTHES
        if (config.getBoolean("TOGGLECLOTHES.Show")) {
            tempItems.add(new GUIItem(config.getString("TOGGLECLOTHES.Name"),
                Material.getMaterial(config.getString("TOGGLECLOTHES.Icon")),
                (List<String>)config.getList("TOGGLECLOTHES.Lore"),
                Material.getMaterial(config.getString("TOGGLECLOTHES.Item")),
                config.getInt("TOGGLECLOTHES.Amount"),
                Permissions.TOGGLETYPE));
        }

        //CHANGE PROFESSION
        if (config.getBoolean("TOGGLEPROFESSION.Show")) {
            tempItems.add(new GUIItem(config.getString("TOGGLEPROFESSION.Name"),
                Material.getMaterial(config.getString("TOGGLEPROFESSION.Icon")),
                (List<String>)config.getList("TOGGLEPROFESSION.Lore"),
                Material.getMaterial(config.getString("TOGGLEPROFESSION.Item")),
                config.getInt("TOGGLEPROFESSION.Amount"),
                Permissions.TOGGLEPROFESSION));
        }

        //AWARENESS
        if (config.getBoolean("AWARE.Show")) {
            tempItems.add(new GUIItem(config.getString("AWARE.Name"),
                Material.getMaterial(config.getString("AWARE.Icon")),
                (List<String>)config.getList("AWARE.Lore"),
                Material.getMaterial(config.getString("AWARE.Item")),
                config.getInt("AWARE.Amount"),
                Permissions.AWARE));
        }

        //CONVERT ZOMBIE TO VILLAGER
        if (config.getBoolean("ZOMBIETOVILLAGER.Show")) {
            tempItems.add(new GUIItem(config.getString("ZOMBIETOVILLAGER.Name"),
                Material.getMaterial(config.getString("ZOMBIETOVILLAGER.Icon")),
                (List<String>)config.getList("ZOMBIETOVILLAGER.Lore"),
                Material.getMaterial(config.getString("ZOMBIETOVILLAGER.Item")),
                config.getInt("ZOMBIETOVILLAGER.Amount"),
                Permissions.CONVERTZOMBIE));
        }

        //CONVERT VILLAGER TO ZOMBIE
        if (config.getBoolean("VILLAGERTOZOMBIE.Show")) {
            tempItems.add(new GUIItem(config.getString("VILLAGERTOZOMBIE.Name"),
                Material.getMaterial(config.getString("VILLAGERTOZOMBIE.Icon")),
                (List<String>)config.getList("VILLAGERTOZOMBIE.Lore"),
                Material.getMaterial(config.getString("VILLAGERTOZOMBIE.Item")),
                config.getInt("VILLAGERTOZOMBIE.Amount"),
                Permissions.ZOMBIFICATION));
        }

        //Drop villagers inventory
        if (config.getBoolean("DROP.Show")) {
            tempItems.add(new GUIItem(config.getString("DROP.Name"),
                Material.getMaterial(config.getString("DROP.Icon")),
                (List<String>)config.getList("DROP.Lore"),
                Material.getMaterial(config.getString("DROP.Item")),
                config.getInt("DROP.Amount"),
                Permissions.DROP));
        }

        //FOLLOW
        if (config.getBoolean("FOLLOW.Show")) {
            tempItems.add(new GUIItem(config.getString("FOLLOW.Name"),
                Material.getMaterial(config.getString("FOLLOW.Icon")),
                (List<String>)config.getList("FOLLOW.Lore"),
                Material.getMaterial(config.getString("FOLLOW.Item")),
                config.getInt("FOLLOW.Amount"),
                Permissions.FOLLOW));
        }

        //GET HP
        if (config.getBoolean("GETHP.Show")) {
            tempItems.add(new GUIItem(config.getString("GETHP.Name"),
                Material.getMaterial(config.getString("GETHP.Icon")),
                (List<String>)config.getList("GETHP.Lore"),
                Material.getMaterial(config.getString("GETHP.Item")),
                config.getInt("GETHP.Amount"),
                Permissions.GETHP));
        }

        //GIVE EXP
        if (config.getBoolean("GIVEEXP.Show")) {
            tempItems.add(new GUIItem(config.getString("GIVEEXP.Name"),
                Material.getMaterial(config.getString("GIVEEXP.Icon")),
                (List<String>)config.getList("GIVEEXP.Lore"),
                Material.getMaterial(config.getString("GIVEEXP.Item")),
                config.getInt("GIVEEXP.Amount"),
                Permissions.GIVEEXP));
        }

        //GLOW
        if (config.getBoolean("GLOW.Show")) {
            tempItems.add(new GUIItem(config.getString("GLOW.Name"),
                Material.getMaterial(config.getString("GLOW.Icon")),
                (List<String>)config.getList("GLOW.Lore"),
                Material.getMaterial(config.getString("GLOW.Item")),
                config.getInt("GLOW.Amount"),
                Permissions.GLOW));
        }

        //HEAL
        if (config.getBoolean("HEAL.Show")) {
            tempItems.add(new GUIItem(config.getString("HEAL.Name"),
                Material.getMaterial(config.getString("HEAL.Icon")),
                (List<String>)config.getList("HEAL.Lore"),
                Material.getMaterial(config.getString("HEAL.Item")),
                config.getInt("HEAL.Amount"),
                Permissions.HEAL));
        }

        //KILL
        if (config.getBoolean("KILL.Show")) {
            tempItems.add(new GUIItem(config.getString("KILL.Name"),
                Material.getMaterial(config.getString("KILL.Icon")),
                (List<String>)config.getList("KILL.Lore"),
                Material.getMaterial(config.getString("KILL.Item")),
                config.getInt("KILL.Amount"),
                Permissions.KILL));
        }

        //NEW TRADES
        if (config.getBoolean("NEWTRADES.Show")) {
            tempItems.add(new GUIItem(config.getString("NEWTRADES.Name"),
                Material.getMaterial(config.getString("NEWTRADES.Icon")),
                (List<String>)config.getList("NEWTRADES.Lore"),
                Material.getMaterial(config.getString("NEWTRADES.Item")),
                config.getInt("NEWTRADES.Amount"),
                Permissions.NEWTRADES));
        }

        //PICKUP
        if (config.getBoolean("PICKUP.Show")) {
            tempItems.add(new GUIItem(config.getString("PICKUP.Name"),
                Material.getMaterial(config.getString("PICKUP.Icon")),
                (List<String>)config.getList("PICKUP.Lore"),
                Material.getMaterial(config.getString("PICKUP.Item")),
                config.getInt("PICKUP.Amount"),
                Permissions.PICKUP));
        }

        //TOGGLE AI
        if (config.getBoolean("TOGGLEAI.Show")) {
            tempItems.add(new GUIItem(config.getString("TOGGLEAI.Name"),
                Material.getMaterial(config.getString("TOGGLEAI.Icon")),
                (List<String>)config.getList("TOGGLEAI.Lore"),
                Material.getMaterial(config.getString("TOGGLEAI.Item")),
                config.getInt("TOGGLEAI.Amount"),
                Permissions.TOGGLEAI));
        }

        //TOGGLEINVISIBILITY
        if (config.getBoolean("TOGGLEINVISIBILITY.Show")) {
            tempItems.add(new GUIItem(config.getString("TOGGLEINVISIBILITY.Name"),
                Material.getMaterial(config.getString("TOGGLEINVISIBILITY.Icon")),
                (List<String>)config.getList("TOGGLEINVISIBILITY.Lore"),
                Material.getMaterial(config.getString("TOGGLEINVISIBILITY.Item")),
                config.getInt("TOGGLEINVISIBILITY.Amount"),
                Permissions.TOGGLEINVISIBLE));
        }

        //TOGGLEINVULNERABLE
        if (config.getBoolean("TOGGLEINVULNERABLE.Show")) {
            tempItems.add(new GUIItem(config.getString("TOGGLEINVULNERABLE.Name"),
                Material.getMaterial(config.getString("TOGGLEINVULNERABLE.Icon")),
                (List<String>)config.getList("TOGGLEINVULNERABLE.Lore"),
                Material.getMaterial(config.getString("TOGGLEINVULNERABLE.Item")),
                config.getInt("TOGGLEINVULNERABLE.Amount"),
                Permissions.TOGGLEINVULNERABLE));
        }

        //TOGGLEPICKUPITEMS
        if (config.getBoolean("TOGGLEPICKUPITEMS.Show")) {
            tempItems.add(new GUIItem(config.getString("TOGGLEPICKUPITEMS.Name"),
                Material.getMaterial(config.getString("TOGGLEPICKUPITEMS.Icon")),
                (List<String>)config.getList("TOGGLEPICKUPITEMS.Lore"),
                Material.getMaterial(config.getString("TOGGLEPICKUPITEMS.Item")),
                config.getInt("TOGGLEPICKUPITEMS.Amount"),
                Permissions.TOGGLEPICKUPITEMS));
        }

        //TOGGLEMUTE
        if (config.getBoolean("TOGGLEMUTE.Show")) {
            tempItems.add(new GUIItem(config.getString("TOGGLEMUTE.Name"),
                Material.getMaterial(config.getString("TOGGLEMUTE.Icon")),
                (List<String>)config.getList("TOGGLEMUTE.Lore"),
                Material.getMaterial(config.getString("TOGGLEMUTE.Item")),
                config.getInt("TOGGLEMUTE.Amount"),
                Permissions.TOGGLESILENT));
        }

        //UNEMPLOYMENT
        if (config.getBoolean("UNEMPLOYMENT.Show")) {
            tempItems.add(new GUIItem(config.getString("UNEMPLOYMENT.Name"),
                Material.getMaterial(config.getString("UNEMPLOYMENT.Icon")),
                (List<String>)config.getList("UNEMPLOYMENT.Lore"),
                Material.getMaterial(config.getString("UNEMPLOYMENT.Item")),
                config.getInt("UNEMPLOYMENT.Amount"),
                Permissions.UNEMPLOYMENT));
        }

        return tempItems;
    }
}
