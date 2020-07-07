package com.github.lukasgail.chateditorFSM;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.github.lukasgail.chateditorFSM.EditorMachine.formatMenuString;

public class GateOptionsMenu implements EditorState{
    EditorMachine editorMachine;
    Player player;

    private TextComponent line1;
    private TextComponent line2;
    private TextComponent line3;
    private TextComponent line4;
    private TextComponent line5;
    private TextComponent done;
    private TextComponent cancelInput;
    private TextComponent cancelSetup;
    private TextComponent get;

    public GateOptionsMenu(EditorMachine editorMachine, Player player) {
        this.editorMachine = editorMachine;
        this.player = player;
    }


    @Override
    public void sendedInput(String input) {

        if(editorMachine.validInput(input)){


            TextComponent message;

            switch (input){
                case "1":
                    chatInterface();
                    break;

                case "2":
                    player.sendMessage("WorkingOn.");
                    break;

                case "3":
                    player.sendMessage("WorkingOn.");
                    break;

                case "4":
                    player.sendMessage("WorkingOn.");
                    break;

                case "5":
                    player.sendMessage("WorkingOn.");
                    break;


                case "done":
                    editorMachine.setEditorState(editorMachine.mainMenu);
                    editorMachine.mainMenu.refresh();
                    break;

                case "exit":
                    editorMachine.exitSetup();
                    break;

                default:
                    player.sendMessage("No valid input found. Click on the lines to edit.");

            }

        }else{
            player.sendMessage("That was not a valid input. Please Click on lines or enter a number.");
        }

    }

    @Override
    public void refresh() {
        chatInterface();

    }

    @Override
    public void blockClick(PlayerInteractEvent event) {

    }

    public void chatInterface(){


        char subArrow = '\uFE44';
        String subArrowString = "  "+subArrow+" ";
        char cross = '\u2718';
        char checkMark = '\u2714';


        line1 = new TextComponent(formatMenuString(ChatColor.AQUA, 1, "Refresh"));
        line2 = new TextComponent(formatMenuString(ChatColor.AQUA, 2, "Not Implemented"));
        line3 = new TextComponent(formatMenuString(ChatColor.AQUA, 3, "Not Implemented"));
        line4 = new TextComponent(formatMenuString(ChatColor.AQUA, 4, "Not Implemented"));
        line5 = new TextComponent(formatMenuString(ChatColor.AQUA, 5, "Not Implemented"));
        done = new TextComponent(ChatColor.GREEN+ "      "+checkMark+" Back "+ checkMark);
        cancelSetup = new TextComponent(ChatColor.DARK_RED + "      "+cross+" Quit Setup "+cross);
        cancelInput = new TextComponent(ChatColor.RED + "[Click to cancel Input]");
        get = new TextComponent(ChatColor.GREEN+"[Get your direction]");



        line1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "1"));
        line2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "2"));
        line3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "3"));
        line4.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "4"));
        line5.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "5"));
        done.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "done"));
        cancelSetup.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "exit"));
        cancelInput.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "cancel"));
        get.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "get"));

        done.addExtra(cancelSetup);

        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD + "=============== " + editorMachine.getPluginPrefix() + ChatColor.GREEN + " - Gate menu" + ChatColor.GOLD + " ===============");
        player.sendMessage(ChatColor.ITALIC + "" + ChatColor.YELLOW + "You can either click on the lines or enter the number to edit");
        player.sendMessage(ChatColor.STRIKETHROUGH + "" + ChatColor.UNDERLINE + ChatColor.DARK_AQUA + "----------------------------------------------------");
        player.sendMessage(ChatColor.DARK_BLUE + "Main "+ChatColor.GREEN+"> "+ChatColor.BLUE+"GateOptions");
        player.spigot().sendMessage(line1);
        player.spigot().sendMessage(line2);
        player.spigot().sendMessage(line3);
        player.spigot().sendMessage(line4);
        player.spigot().sendMessage(line5);
        player.sendMessage("");
        player.spigot().sendMessage(done);
        player.sendMessage(ChatColor.STRIKETHROUGH + "" + ChatColor.UNDERLINE + ChatColor.DARK_AQUA + "----------------------------------------------------");
        //player.spigot().sendMessage(cancelSetup);


    }



}