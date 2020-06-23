package com.github.lukasgail.simplegates;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

import static com.github.lukasgail.simplegates.SimpleGates.getCardinalDirection;
import static com.github.lukasgail.simplegates.SimpleGates.select;

public class ChatGateEditor {
    private Player player;
    private Plugin pluginSimppleGates;
    private final String pluginPrefix = ChatColor.GREEN + "[SimpleGate]";
    private boolean editMode;
    private boolean selectingPositions;
    private Material material;
    private Location selectedPos1;
    private Location selectedPos2;
    private boolean permeable;
    private int delay;
    private double repetitions;
    private double moveDistance;
    private String direction;
    private String gateName;
    private String selectedLineToEdit;
    private GlowingSelection glowingSelection;
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
    private TextComponent cancel;
    private TextComponent back;
    private TextComponent getDirection;
    private SimpleGates mainSimpleGates;


    public ChatGateEditor(Player player, Plugin plugin, SimpleGates mainSimpleGates) {
        this.player = player;
        this.pluginSimppleGates = plugin;
        this.mainSimpleGates = mainSimpleGates;
        this.glowingSelection = new GlowingSelection(player, pluginSimppleGates);
        this.selectingPositions = false;
        this.permeable = false;
        this.material = Material.IRON_BLOCK;
        this.moveDistance = 0.1;
        this.repetitions = 10;
        this.delay = 0;
        this.direction = ChatColor.YELLOW+""+ChatColor.BOLD+""+ChatColor.ITALIC+"No Direction Set!";
        back = new TextComponent("Click -> " + ChatColor.YELLOW + "Back " + ChatColor.WHITE + "to cancel input.");
        back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "back"));
        cancel = new TextComponent(ChatColor.RED + "CANCEL Setup - Click or type \"cancel\"");
        cancel.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "cancel"));
        getDirection = new TextComponent("You also can look in a direction and type/click " + ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "get");
        getDirection.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "get"));

    }

    public void gateCreate() {
        this.editMode = true;
        this.selectedLineToEdit = "none";
        editGate("1");
    }


    public void editGate(String input) {
        if (selectedLineToEdit.equals("none")) {
            if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5") || input.equals("6") || input.equals("7") || input.equals("8") || input.equals("9") || input.equals("0") || input.equals("cancel") || input.equals("back")) {
                this.selectedLineToEdit = input;
                switch (input) {
                    case "1":
                        sendGateInterface();
                        selectedLineToEdit = "none";
                        break;
                    case "2":
                        player.sendMessage("Enter a Name for the Gate!");
                        selectedLineToEdit = "2";
                        break;
                    case "3":
                        player.sendMessage("LeftClick with the fist to set Pos1 and RightClick to set Pos2");
                        selectedLineToEdit = "none";
                        break;
                    case "4":
                        player.sendMessage("Enter a new material like gold_block or lapis_block!");
                        selectedLineToEdit = "4";
                        break;
                    case "5":
                        permeable = !permeable;
                        sendGateInterface();
                        player.sendMessage("Permeability toggled to: " + isPermeable());
                        selectedLineToEdit = "none";
                        break;
                    case "6":
                        player.sendMessage("Enter a number to set the distance the gate will move in one step. Like 0.5");
                        selectedLineToEdit = "6";
                        break;
                    case "7":
                        player.sendMessage("Enter a number to set how often the step distance will be performed.");
                        selectedLineToEdit = "7";
                        break;
                    case "8":
                        player.sendMessage("Enter a number to set the delay in ticks between the steps. 20 ticks = 1 second");
                        selectedLineToEdit = "8";
                        break;
                    case "9":
                        player.sendMessage("Specify a direction in which the gate should move.\nValid inputs are [u/d/n/s/w/e/nw/ne/sw/se]");
                        player.spigot().sendMessage(getDirection);
                        selectedLineToEdit = "9";
                        break;
                    case "0":
                        player.sendMessage("Checking!");
                        if(gateCheck()){
                            mainSimpleGates.summonGate(player, this, glowingSelection);
                            player.sendMessage(ChatColor.GREEN + "Gate created!");
                            exitEdit();

                        }else{
                            sendGateInterface();
                            player.sendMessage(ChatColor.RED+"Checking failed! Is everything set?");
                        }
                        selectedLineToEdit = "none";
                        break;
                    case "back":
                        sendGateInterface();
                        player.sendMessage("You have no line selected");
                        selectedLineToEdit = "none";
                        break;

                    case "cancel":
                        exitEdit();
                        player.sendMessage("You are no longer editing!");
                        break;

                }

            } else {
                player.sendMessage("Wrong number to identify the line to edit! Just Click them.");
            }

        } else {
            switch (selectedLineToEdit) {
                case "1":
                    if (input.equals("back")) {
                        selectedLineToEdit = "none";
                        sendGateInterface();
                        player.sendMessage("Line selection cleared!");
                        break;
                    }
                    sendGateInterface();
                    selectedLineToEdit = "none";
                    break;
                case "2":
                    if (input.equals("back")) {
                        selectedLineToEdit = "none";
                        sendGateInterface();
                        player.sendMessage("Line selection cleared!");
                        break;
                    }
                    setGateName(input);
                    sendGateInterface();
                    player.sendMessage("Name set!");
                    selectedLineToEdit = "none";
                    break;
                case "3":
                    if (input.equals("back")) {
                        selectedLineToEdit = "none";
                        sendGateInterface();
                        player.sendMessage("Line selection cleared!");
                        break;
                    }
                    player.sendMessage("LeftClick with the fist to set Pos1 and RightClick to set Pos2");
                    selectedLineToEdit = "none";
                    break;
                case "4":
                    if (input.equals("back")) {
                        selectedLineToEdit = "none";
                        sendGateInterface();
                        player.sendMessage("Line selection cleared!");
                        break;
                    }
                    Material material = getMaterialFromString(input);
                    if (material != null) {
                        this.setMaterial(material);
                        sendGateInterface();
                        player.sendMessage("Material set!");
                    } else {
                        selectedLineToEdit = "4";
                        player.spigot().sendMessage(back);
                        break;
                    }
                    selectedLineToEdit = "none";
                    break;
                case "5":
                    if (input.equals("back")) {
                        selectedLineToEdit = "none";
                        sendGateInterface();
                        player.sendMessage("Line selection cleared!");
                        break;
                    }
                    if (input.matches("^t(rue)?$")) {
                        setPermeable(true);
                    } else if (input.matches("^f(alse)?$")) {
                        setPermeable(false);
                    } else {
                        player.sendMessage("Wrong input! Type= t or f, true or false");
                        player.spigot().sendMessage(back);
                        selectedLineToEdit = "5";
                        break;
                    }

                    player.sendMessage("Permeability set to: " + isPermeable());
                    sendGateInterface();
                    selectedLineToEdit = "none";
                    break;
                case "6":
                    if (input.equals("back")) {
                        selectedLineToEdit = "none";
                        sendGateInterface();
                        player.sendMessage("Line selection cleared!");
                        break;
                    }
                    try {
                        setMoveDistance(Double.parseDouble(input));
                    } catch (NumberFormatException e) {

                        player.sendMessage("That was NOT a valid input. Try something like 0.5");
                        player.spigot().sendMessage(back);
                        selectedLineToEdit = "6";
                        break;
                    }
                    sendGateInterface();
                    player.sendMessage("Set moveDistance to: " + getMoveDistance());
                    selectedLineToEdit = "none";
                    break;
                case "7":
                    if (input.equals("back")) {
                        selectedLineToEdit = "none";
                        sendGateInterface();
                        player.sendMessage("Line selection cleared!");
                        break;
                    }
                    try {
                        double repetDouble = (double)Math.round(Double.parseDouble(input));
                        setRepetitions(repetDouble);
                    } catch (NumberFormatException e) {

                        player.sendMessage("That was NOT a valid input. Try something like 10");
                        player.spigot().sendMessage(back);
                        selectedLineToEdit = "7";
                        break;
                    }
                    sendGateInterface();
                    player.sendMessage("Set repetitions to: " + getRepetitions());
                    selectedLineToEdit = "none";
                    break;
                case "8":
                    if (input.equals("back")) {
                        selectedLineToEdit = "none";
                        sendGateInterface();
                        player.sendMessage("Line selection cleared!");
                        break;
                    }
                    try {
                        setDelay(Integer.parseInt(input));
                    } catch (NumberFormatException e) {
                        player.sendMessage("That was Not a valid number. Only natural numbers are allowed!");
                        player.spigot().sendMessage(back);
                        selectedLineToEdit = "8";
                        break;
                    }
                    sendGateInterface();
                    player.sendMessage("Set delay to: " + getDelay());
                    selectedLineToEdit = "none";
                    break;
                case "9":
                    if (input.equals("back")) {
                        selectedLineToEdit = "none";
                        sendGateInterface();
                        player.sendMessage("Line selection cleared!");
                        break;
                    }

                    if (input.equals("u") || input.equals("d") || input.equals("n") || input.equals("s") || input.equals("w") || input.equals("e") || input.equals("nw") || input.equals("ne") || input.equals("sw") || input.equals("se") || input.equals("get")) {
                        if (input.equals("get")) {
                            setDirection(getCardinalDirection(player));
                        } else {
                            setDirection(input);
                        }

                    } else {
                        player.sendMessage("Not a valid direction!");
                        player.spigot().sendMessage(back);
                        selectedLineToEdit = "9";
                        break;
                    }
                    sendGateInterface();
                    player.sendMessage("Set MoveDirection to: " + getDirection());
                    selectedLineToEdit = "none";
                    break;
                case "back":
                    sendGateInterface();
                    player.sendMessage("Canceled input!");
                    selectedLineToEdit = "none";
                    break;
                case "0":
                    selectedLineToEdit = "none";
                    break;
                default:
                    player.sendMessage("Something went wrong. Sorry...");
                    selectedLineToEdit = "none";
                    break;
            }
        }

    }


    public Material getMaterialFromString(String materialString) {
        Material material = Material.matchMaterial(materialString);
        if (material == null) {
            player.sendMessage(ChatColor.RED + "Material not found!");
            return null;
        }
        return material;
    }


    public void sendGateInterface() {

        line1 = new TextComponent(ChatColor.AQUA + "[1] Refresh");
        line2 = new TextComponent(ChatColor.AQUA + "[2] Name: " + ChatColor.GOLD + this.getGateName());
        line3 = new TextComponent(ChatColor.AQUA + "[3] GatePositions:\n"+ ChatColor.AQUA +"    Pos1= " + ChatColor.GOLD + this.getSelectedCoordinates1() + ChatColor.AQUA + "   Pos2= " + ChatColor.GOLD + this.getSelectedCoordinates2());
        line4 = new TextComponent(ChatColor.AQUA + "[4] BlockMaterial: " + ChatColor.GOLD + this.getMaterial().toString());
        line5 = new TextComponent(ChatColor.AQUA + "[5] Permeable: " + ChatColor.GOLD + this.isPermeable());
        line6 = new TextComponent(ChatColor.AQUA + "[6] MoveDistance: " + ChatColor.GOLD + this.getMoveDistance());
        line7 = new TextComponent(ChatColor.AQUA + "[7] Repetitions: " + ChatColor.GOLD + this.getRepetitions());
        line8 = new TextComponent(ChatColor.AQUA + "[8] Delay: " + ChatColor.GOLD + this.getDelay());
        line9 = new TextComponent(ChatColor.AQUA + "[9] Direction: " + ChatColor.GOLD + this.getDirection());
        line0 = new TextComponent(ChatColor.GREEN + "[0] Done");

        line1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "1"));
        line2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "2"));
        line3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "3"));
        line4.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "4"));
        line5.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "5"));
        line6.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "6"));
        line7.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "7"));
        line8.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "8"));
        line9.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "9"));
        line0.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "0"));


        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD + "=============== " + pluginPrefix + ChatColor.GREEN + " - Gate menu" + ChatColor.GOLD + " ===============");
        player.sendMessage(ChatColor.ITALIC + "" + ChatColor.YELLOW + "You can either click on the lines or enter the number to edit");
        player.sendMessage(ChatColor.STRIKETHROUGH + "" + ChatColor.UNDERLINE + ChatColor.DARK_AQUA + "----------------------------------------------------");
        player.spigot().sendMessage(line1);
        player.spigot().sendMessage(line2);
        player.spigot().sendMessage(line3);
        player.spigot().sendMessage(line4);
        player.spigot().sendMessage(line5);
        player.spigot().sendMessage(line6);
        player.spigot().sendMessage(line7);
        player.spigot().sendMessage(line8);
        player.spigot().sendMessage(line9);
        player.spigot().sendMessage(line0);
        player.spigot().sendMessage(cancel);

    }

    public boolean gateCheck() {
        if (this.getGateName() != null){
            if(direction.equals("u") || direction.equals("d") || direction.equals("n") || direction.equals("s") || direction.equals("w") || direction.equals("e") || direction.equals("ne") || direction.equals("nw") || direction.equals("se") || direction.equals("sw")){
                if(this.selectedPos1 != null && this.selectedPos2 != null) {
                    return true;
                }
            }
        }
        return false;
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

    public GlowingSelection getGlowingSelection() {
        return glowingSelection;
    }

    public void setGlowingSelection(GlowingSelection glowingSelection) {
        this.glowingSelection = glowingSelection;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Location getSelectedPos1() {
        setSelectedPos1();
        return selectedPos1;
    }

    public String getSelectedCoordinates1() {
        setSelectedPos1();
        if (selectedPos1 != null) {
            return selectedPos1.getX() + ", " + selectedPos1.getY() + ", " + selectedPos1.getBlockZ();
        }
        return ChatColor.YELLOW + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + "[not set]";
    }

    public void setSelectedPos1() {
        this.selectedPos1 = this.glowingSelection.getSelectedLocation1();
    }

    public Location getSelectedPos2() {
        setSelectedPos2();
        return selectedPos2;
    }

    public String getSelectedCoordinates2() {
        setSelectedPos2();
        if (selectedPos2 != null) {
            return selectedPos2.getX() + ", " + selectedPos2.getY() + ", " + selectedPos2.getBlockZ();
        }
        return ChatColor.YELLOW + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + "[not set]";
    }

    public void setSelectedPos2() {
        this.selectedPos2 = this.glowingSelection.getSelectedLocation2();
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
        return this.direction;
    }

    public void setDirection(String direction) {
        if (direction.equals("u") || direction.equals("d") || direction.equals("n") || direction.equals("s") || direction.equals("w") || direction.equals("e") || direction.equals("ne") || direction.equals("nw") || direction.equals("se") || direction.equals("sw")) {
            this.direction = direction;
        }else{
            player.sendMessage("Not a valid direction");
        }
    }

    public String getSelectedLineToEdit() {
        return selectedLineToEdit;
    }

    public void setSelectedLineToEdit(String selectedLineToEdit) {
        this.selectedLineToEdit = selectedLineToEdit;
    }

    public String getGateName() {
        if (gateName != null) {
            return this.gateName;
        }
        return ChatColor.YELLOW + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + "No Name Set!";
    }

    public void setGateName(String gateName) {
        this.gateName = gateName;
    }

    public void exitEdit() {
        for (int i = 0; i < mainSimpleGates.nowEditing.size(); i++) {
            if (mainSimpleGates.nowEditing.get(i).getPlayer().equals(player)) {
                mainSimpleGates.nowEditing.remove(i);
            }
        }


    }

}
