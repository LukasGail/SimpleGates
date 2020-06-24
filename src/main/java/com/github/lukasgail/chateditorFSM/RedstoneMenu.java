package com.github.lukasgail.chateditorFSM;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class RedstoneMenu implements EditorState{

    EditorMachine editorMachine;
    private Player player;
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
    private TextComponent done;
    private TextComponent cancelInput;
    private TextComponent cancelSetup;
    private TextComponent get;

    public RedstoneMenu(EditorMachine editorMachine, Player player){
        this.editorMachine = editorMachine;
        this.player = player;


        line1 = new TextComponent();
        line2 = new TextComponent();
        line3 = new TextComponent();
        line4 = new TextComponent();
        line5 = new TextComponent();
        line6 = new TextComponent();
        line7 = new TextComponent();
        line8 = new TextComponent();
        line9 = new TextComponent();
        line0 = new TextComponent();
        done = new TextComponent();
        cancelInput = new TextComponent();
        cancelSetup = new TextComponent();
        get = new TextComponent();

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
        done.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "done"));
        cancelInput.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "cancel"));
        cancelSetup.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "exit"));
        get.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "get"));


    }


    public void chatInterface(){

        line1 = new TextComponent(ChatColor.AQUA + "[1] Refresh");
        line2 = new TextComponent(ChatColor.AQUA + "[2] RedstoneButton: " + ChatColor.GOLD + editorMachine.getRedstoneButton());
        line3 = new TextComponent(ChatColor.AQUA + "[3] RedstoneButtonDelay: "+ ChatColor.GOLD + editorMachine.getRedstoneButtonDelay());
        line4 = new TextComponent(ChatColor.AQUA + "[4] ActivateWhenPlayerInRange: " + ChatColor.GOLD + editorMachine.getPlayerRange());
        line5 = new TextComponent(ChatColor.AQUA + "[5] OpensOnlyWithPermission: " + ChatColor.GOLD + editorMachine.isOpensOnlyWithPermission());
        line0 = new TextComponent(ChatColor.GREEN + "[0] Done");


        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD + "=============== " + editorMachine.getPluginPrefix() + ChatColor.GREEN + " - Gate menu" + ChatColor.GOLD + " ===============");
        player.sendMessage(ChatColor.ITALIC + "" + ChatColor.YELLOW + "You can either click on the lines or enter the number to edit");
        player.sendMessage(ChatColor.STRIKETHROUGH + "" + ChatColor.UNDERLINE + ChatColor.DARK_AQUA + "----------------------------------------------------");
        player.sendMessage(ChatColor.BLUE + "Main "+ChatColor.GREEN+"> "+ChatColor.BLUE+"RedstoneMenu");
        player.spigot().sendMessage(line1);
        player.spigot().sendMessage(line2);
        player.spigot().sendMessage(line3);
        player.spigot().sendMessage(line4);
        player.spigot().sendMessage(line5);
        player.spigot().sendMessage(line0);
        player.spigot().sendMessage(cancelSetup);


    }


    public void refresh() {
        chatInterface();
    }

    @Override
    public void blockClick(PlayerInteractEvent event) {

    }

    public boolean validInput(String input){

        if(input.matches("[0-5]|[done]|[exit]")){

            return true;
        }
        return false;
    }

    @Override
    public void sendedInput(String input) {

        if(validInput(input)){

            if (input.equals("1")){
                chatInterface();
            }

            if (input.equals("2")){
                editorMachine.setEditorState(editorMachine.waitingForName);
                player.sendMessage("Please enter a name!");
            }


        }



    }


}
