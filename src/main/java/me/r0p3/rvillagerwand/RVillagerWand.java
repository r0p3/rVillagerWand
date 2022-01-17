package me.r0p3.rvillagerwand;

import me.r0p3.rvillagerwand.commands.Wand;
import me.r0p3.rvillagerwand.wand_interaction.GUIClickItem;
import me.r0p3.rvillagerwand.wand_interaction.OpenGUI;
import me.r0p3.rvillagerwand.wand_interaction.UseWand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RVillagerWand extends JavaPlugin
{
    List<GUIItem> guiItems = new ArrayList<>();
    @Override
    public void onEnable()
    {
        try
        {
            guiItems = generateWands();
            getCommand("wand").setExecutor(new Wand());
            getServer().getPluginManager().registerEvents(new OpenGUI(guiItems), this);
            getServer().getPluginManager().registerEvents(new GUIClickItem(guiItems), this);
            getServer().getPluginManager().registerEvents(new UseWand(guiItems), this);
        }
        catch (Exception e)
        {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    private List<GUIItem> generateWands()
    {
        List<GUIItem> tempItems = new ArrayList<GUIItem>();
        tempItems.add(new GUIItem(GUIConst.ToggleTypeName, Material.CHAINMAIL_CHESTPLATE, Arrays.asList(ChatColor.GREEN + "Change the villagers clothes"), null, 0, Permissions.TOGGLETYPE));
        tempItems.add(new GUIItem(GUIConst.ConvertZombieName, Material.GOLDEN_APPLE, Arrays.asList(ChatColor.GREEN + "Turn zombiefied villager into villager"), Material.GOLDEN_APPLE, 1, Permissions.CONVERTZOMBIE));
        tempItems.add(new GUIItem(GUIConst.ZombificationName, Material.ROTTEN_FLESH, Arrays.asList(ChatColor.GREEN + "Turn the villager into a zombie"), Material.ROTTEN_FLESH, 1, Permissions.ZOMBIFICATION));
        tempItems.add(new GUIItem(GUIConst.DropName, Material.HOPPER, Arrays.asList(ChatColor.GREEN + "Villager will drop its inventory"), null, 0, Permissions.DROP));
        tempItems.add(new GUIItem(GUIConst.FollowName, Material.LEAD, Arrays.asList(ChatColor.GREEN + "Toggle if villager will follow you"), null, 0, Permissions.FOLLOW));
        tempItems.add(new GUIItem(GUIConst.GetHPName, Material.RED_WOOL, Arrays.asList(ChatColor.GREEN + "Gets the villagers HP"), null, 0, Permissions.GETHP));
        tempItems.add(new GUIItem(GUIConst.GlowName, Material.GLOWSTONE, Arrays.asList(ChatColor.GREEN + "Toggle if villager should glow"), Material.GLOWSTONE, 1, Permissions.GLOW));
        tempItems.add(new GUIItem(GUIConst.HealName, Material.BREAD, Arrays.asList(ChatColor.GREEN + "Fully heal the villager"), Material.BREAD, 1, Permissions.HEAL));
        tempItems.add(new GUIItem(GUIConst.KillName, Material.LAVA_BUCKET, Arrays.asList(ChatColor.GREEN + "Kills the villager"), null, 0, Permissions.KILL));
        tempItems.add(new GUIItem(GUIConst.NewTradesName, Material.WRITABLE_BOOK, Arrays.asList(ChatColor.GREEN + "Reset villagers trades, only works if villager has 0 exp"), null, 0, Permissions.NEWTRADES));
        tempItems.add(new GUIItem(GUIConst.PickupName, Material.VILLAGER_SPAWN_EGG, Arrays.asList(ChatColor.GREEN + "Turn the villager into a spawn egg"), null, 0, Permissions.PICKUP));
        tempItems.add(new GUIItem(GUIConst.ToggleAIName, Material.ENCHANTED_BOOK, Arrays.asList(ChatColor.GREEN + "Toggle if villagers AI is on"), null, 0, Permissions.TOGGLEAI));
        tempItems.add(new GUIItem(GUIConst.ToggleInvisibleName, Material.POTION, Arrays.asList(ChatColor.GREEN + "Toggle if villager is invisible"), null, 0, Permissions.TOGGLEINVISIBLE));
        tempItems.add(new GUIItem(GUIConst.ToggleInvulnerableName, Material.BEDROCK, Arrays.asList(ChatColor.GREEN + "Toggle if villager is invulnerable"), null, 0, Permissions.TOGGLEINVULNERABLE));
        tempItems.add(new GUIItem(GUIConst.TogglePickUpItemsName, Material.CHEST, Arrays.asList(ChatColor.GREEN + "Toggle if villager can pick up items"), null, 0, Permissions.TOGGLEPICKUPITEMS));
        tempItems.add(new GUIItem(GUIConst.ToggleSilentName, Material.WHITE_WOOL, Arrays.asList(ChatColor.GREEN + "Toggle if villager should be silent"), null, 0, Permissions.TOGGLESILENT));
        tempItems.add(new GUIItem(GUIConst.UnemploymentName, Material.FIRE, Arrays.asList(ChatColor.GREEN + "Reset villager to 0 exp and no profession"), null, 0, Permissions.UNEMPLOYMENT));

        return tempItems;
    }
}
