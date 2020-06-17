package com.github.lukasgail.simplegates;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ChatGateEditor {
    private Player player;
    private Plugin pluginSimppleGates;
    private final String pluginPrefix = ChatColor.GREEN + "[SimpleGate]";
    private boolean editMode;
    private Material material;
    private Location selectedPos1;
    private Location selectedPos2;
    private boolean permeable;
    private int delay;
    private double repetitions;
    private double moveDistance;
    private String direction;
    private String gateName;
    private TextComponent line1;
    private TextComponent line2;
    private TextComponent line3;
    private TextComponent line4;
    private TextComponent line5;
    private TextComponent line6;
    private TextComponent line7;
    private TextComponent line8;
    private TextComponent line9;
    private TextComponent line0;





    public ChatGateEditor(Player player, Plugin plugin) {
        this.player = player;
        this.pluginSimppleGates = plugin;
        this.permeable = false;
        this.material = Material.IRON_BLOCK;
        this.moveDistance = 0.1;
        this.repetitions = 10;
        this.delay = 0;
        line1 = new TextComponent(ChatColor.AQUA + "[1] Refresh");
        line2 = new TextComponent(ChatColor.AQUA + "[2] Name: "+ChatColor.GOLD+this.getGateName());
        line3 = new TextComponent(ChatColor.AQUA + "[3] GatePositions: Pos1= "+ChatColor.GOLD+this.getSelectedCoordinates1()+ChatColor.AQUA+"   Pos2= "+ChatColor.GOLD+this.getSelectedCoordinates2());
        line4 = new TextComponent(ChatColor.AQUA + "[4] BlockMaterial: "+ChatColor.GOLD+this.getMaterial().toString());
        line5 = new TextComponent(ChatColor.AQUA + "[5] Permeable/Collision: "+ChatColor.GOLD+this.isPermeable());
        line6 = new TextComponent(ChatColor.AQUA + "[6] MoveDistance: "+ChatColor.GOLD+this.getMoveDistance());
        line7 = new TextComponent(ChatColor.AQUA + "[7] Repetitions: "+ChatColor.GOLD+this.getRepetitions());
        line8 = new TextComponent(ChatColor.AQUA + "[8] Delay: "+ChatColor.GOLD+this.getDelay());
        line9 = new TextComponent(ChatColor.AQUA + "[9] Direction: "+ChatColor.GOLD+this.getDirection());
        line0 = new TextComponent(ChatColor.GREEN + "[0] Done");
        line1.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "1" ) );
        line2.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "2" ) );
        line3.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "3" ) );
        line4.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "4" ) );
        line5.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "5" ) );
        line6.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "6" ) );
        line7.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "7" ) );
        line8.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "8" ) );
        line9.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "9" ) );
        line0.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "0" ) );

    }

    public void gateCreate() {
        this.editMode = true;
        editGate();
    }

    public void editGate() {
        sendGateInterface();


    }

    public void sendGateInterface() {

        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD+"=============== "+ pluginPrefix +ChatColor.GREEN+" - Gate menu"+ChatColor.GOLD+" ===============");
        player.sendMessage(ChatColor.ITALIC+""+ChatColor.YELLOW +"You can either click on the lines or enter the number to edit");
        player.sendMessage(ChatColor.STRIKETHROUGH+""+ChatColor.UNDERLINE+ChatColor.DARK_AQUA+"----------------------------------------------------");
        player.spigot().sendMessage( line1 );
        player.spigot().sendMessage( line2 );
        player.spigot().sendMessage( line3 );
        player.spigot().sendMessage( line4 );
        player.spigot().sendMessage( line5 );
        player.spigot().sendMessage( line6 );
        player.spigot().sendMessage( line7 );
        player.spigot().sendMessage( line8 );
        player.spigot().sendMessage( line9 );
        player.spigot().sendMessage( line0 );

    }











    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Plugin getPluginSimppleGates() {
        return pluginSimppleGates;
    }

    public void setPluginSimppleGates(Plugin pluginSimppleGates) {
        this.pluginSimppleGates = pluginSimppleGates;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Location getSelectedPos1() {
        return selectedPos1;
    }

    public String getSelectedCoordinates1() {
        if(selectedPos1 != null) {
            return "x= "+selectedPos1.getX()+" y= "+selectedPos1.getY()+" z= "+selectedPos1.getBlockZ();
        }
        return ChatColor.YELLOW+""+ChatColor.ITALIC+""+ChatColor.BOLD+"[not set]";
    }

    public void setSelectedPos1(Location selectedPos1) {
        this.selectedPos1 = selectedPos1;
    }

    public Location getSelectedPos2() {
        return selectedPos2;
    }

    public String getSelectedCoordinates2() {
        if(selectedPos2 != null) {
            return "x= "+selectedPos2.getX()+" y= "+selectedPos2.getY()+" z= "+selectedPos2.getBlockZ();
        }
        return ChatColor.YELLOW+""+ChatColor.ITALIC+""+ChatColor.BOLD+"[not set]";
    }

    public void setSelectedPos2(Location selectedPos2) {
        this.selectedPos2 = selectedPos2;
    }

    public boolean isPermeable() {
        return permeable;
    }

    public void setPermeable(boolean permeable) {
        this.permeable = permeable;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public double getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(double repetitions) {
        this.repetitions = repetitions;
    }

    public double getMoveDistance() {
        return moveDistance;
    }

    public void setMoveDistance(double moveDistance) {
        this.moveDistance = moveDistance;
    }

    public String getDirection() {
        if(direction == ("n") || direction == ("s") || direction == ("w") || direction == ("e") || direction == ("ne") || direction == ("nw") || direction == ("se") || direction == ("sw")) {
            return this.direction;
        }
        return ChatColor.YELLOW+""+ChatColor.ITALIC+""+ChatColor.BOLD+"No Direction Set!";
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getGateName() {
        if(gateName != null){
            return this.gateName;
        }
        return ChatColor.YELLOW+""+ChatColor.ITALIC+""+ChatColor.BOLD+"No Name Set!";
    }

    public void setGateName(String gateName) {
        this.gateName = gateName;
    }

    public void exitEdit() {

    }

}
