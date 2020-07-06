package com.github.lukasgail.chateditorFSM;

import com.github.lukasgail.simplegates.GlowingSelection;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MainMenu implements EditorState {

    EditorMachine editorMachine;
    private Player player;
    private TextComponent line1;
    private TextComponent line2;
    private TextComponent line3;
    private TextComponent line3sub;
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

    public MainMenu(EditorMachine editorMachine, Player player) {
        this.editorMachine = editorMachine;
        this.player = player;

    }

    public void chatInterface() {

        char subArrow = '\uFE44';
        String subArrowString = "  " + subArrow + " ";
        char cross = '\u2718';
        char checkMark = '\u2714';


        line1 = new TextComponent(ChatColor.AQUA + "[1] Refresh");
        line2 = new TextComponent(ChatColor.AQUA + "[2] Name: " + ChatColor.GOLD + editorMachine.getGateNameNeverNull(editorMachine.getGateName()));
        line3 = new TextComponent(ChatColor.AQUA + "[3] Selection: " + ChatColor.AQUA + "Pos1= " + ChatColor.GOLD + editorMachine.getLocationAsString(editorMachine.getSelectedLocation1()) + ChatColor.AQUA + "   Pos2= " + ChatColor.GOLD + editorMachine.getLocationAsString(editorMachine.getSelectedLocation2()));
        line4 = new TextComponent(ChatColor.AQUA + "" + subArrowString + "[+] Click for more options.");//polygon or possibility to add single blocks.
        line5 = new TextComponent(ChatColor.AQUA + "[5] Direction: " + ChatColor.GOLD + editorMachine.getDirectionNeverNull(editorMachine.getDirection()) + "   ");
        line6 = new TextComponent(ChatColor.AQUA + "[6] ActivatorButton(s): " + ChatColor.GOLD + editorMachine.getFirstRedstoneButtonAsStringNeverNull(editorMachine.getRedstoneButtons()));//Add remove option for block
        line7 = new TextComponent(ChatColor.AQUA + "" + subArrowString + "[+] Click for more redstone configurations.");//more buttons possible, Delay, playerInRange  line8 = new TextComponent(ChatColor.AQUA + "[8] Delay: " + ChatColor.GOLD + editorMachine.getDelay());
        line8 = new TextComponent(ChatColor.AQUA + "[8] GateOptions: " + ChatColor.GRAY + "(BlockMaterial, Permeability,...)"); //BlockMaterial, Permeability, MoveDistance, Repetitions  //line4 = new TextComponent(ChatColor.AQUA + "[4] BlockMaterial: " + ChatColor.GOLD + editorMachine.getMaterial().toString());  //line5 = new TextComponent(ChatColor.AQUA + "[5] Permeable: " + ChatColor.GOLD + editorMachine.isPermeable());  //line6 = new TextComponent(ChatColor.AQUA + "[6] MoveDistance: " + ChatColor.GOLD + editorMachine.getMoveDistance());  //line7 = new TextComponent(ChatColor.AQUA + "[7] Repetitions: " + ChatColor.GOLD + editorMachine.getRepetitions());
        //line9 = new TextComponent(ChatColor.AQUA + "[9]############## : " + ChatColor.GOLD + editorMachine.getDelay());
        //line0 = new TextComponent(ChatColor.GREEN+ "[0] Done!");
        done = new TextComponent(ChatColor.GREEN + "      " + checkMark + " Done! " + checkMark);
        cancelSetup = new TextComponent(ChatColor.DARK_RED + "      " + cross + " Quit Setup " + cross);
        cancelInput = new TextComponent(ChatColor.RED + "[Click to cancel Input]");
        get = new TextComponent(ChatColor.GREEN + "[Get your direction]");


        line1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "1"));
        line2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "2"));
        line3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "3"));
        line4.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "4"));
        line5.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "5"));
        line6.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "6"));
        line7.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "7"));
        line8.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "8"));
        //line9.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "9"));
        //line0.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "0"));
        done.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "done"));
        cancelSetup.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "exit"));
        cancelInput.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "cancel"));
        get.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "get"));

        done.addExtra(cancelSetup);
        line5.addExtra(get);

        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD + "=============== " + editorMachine.getPluginPrefix() + ChatColor.GREEN + " - Gate menu" + ChatColor.GOLD + " ===============");
        player.sendMessage(ChatColor.ITALIC + "" + ChatColor.YELLOW + "You can either click on the lines or enter the number to edit");
        player.sendMessage(ChatColor.STRIKETHROUGH + "" + ChatColor.UNDERLINE + ChatColor.DARK_AQUA + "----------------------------------------------------");
        player.sendMessage(ChatColor.BLUE + "Main >");
        player.spigot().sendMessage(line1);
        player.spigot().sendMessage(line2);
        player.spigot().sendMessage(line3);
        player.spigot().sendMessage(line4);
        player.spigot().sendMessage(line5);
        player.spigot().sendMessage(line6);
        player.spigot().sendMessage(line7);
        player.spigot().sendMessage(line8);
        player.spigot().sendMessage(line9);
        player.sendMessage("");
        player.spigot().sendMessage(done);
        player.sendMessage(ChatColor.STRIKETHROUGH + "" + ChatColor.UNDERLINE + ChatColor.DARK_AQUA + "----------------------------------------------------");
        //player.spigot().sendMessage(cancelSetup);


    }


    @Override
    public void refresh() {
        this.chatInterface();
    }

    @Override
    public void blockClick(PlayerInteractEvent event) {

        Action action = event.getAction();
        ItemStack item = event.getItem();
        Location playerLocation = event.getPlayer().getLocation();
        playerLocation.setWorld(player.getWorld()); //To be sure about the existence of the world in Location.

        if (player.getItemInHand().getType().equals(Material.AIR)) {

            GlowingSelection selection = editorMachine.getGlowingSelection();

            int posX = event.getClickedBlock().getX();
            int posY = event.getClickedBlock().getY();
            int posZ = event.getClickedBlock().getZ();


            if (action.equals((Action.LEFT_CLICK_BLOCK))) {
                selection.setSelectedLocation1(event.getClickedBlock().getLocation());
                editorMachine.setSelectedLocation1(event.getClickedBlock().getLocation());

            } else {
                selection.setSelectedLocation2(event.getClickedBlock().getLocation());
                editorMachine.setSelectedLocation2(event.getClickedBlock().getLocation());
            }

            if (selection.getBlocks() != null && selection.getBlocks().size() > 200) {

                player.sendMessage(ChatColor.RED + "\nTo prevent lagging or crashing the selection-preview is disabled when more than 200 blocks are in the selection!");
                player.sendMessage(ChatColor.RED + "You can still use your " + ChatColor.GOLD + selection.getBlocks().size() + ChatColor.RED + "-blocks selection for a gate BUT BE CAREFUL!");

            }

            player.sendMessage(
                    String.format("%s[SimpleGates] Position%d at %s X=%d Y=%d Z=%d %s has been selected!",
                            ChatColor.GREEN,
                            action.equals(Action.LEFT_CLICK_BLOCK) ? 1 : 2,
                            ChatColor.GOLD,
                            posX,
                            posY,
                            posZ,
                            ChatColor.GREEN));

            event.setCancelled(true);

            refresh();

        }


    }

    @Override
    public void sendedInput(String input) {

        if (editorMachine.validInput(input)) {


            TextComponent message;

            switch (input) {
                case "1":
                    chatInterface();
                    break;

                case "2":
                    editorMachine.setEditorState(editorMachine.waitingForName);
                    message = new TextComponent(ChatColor.YELLOW + "Please enter a name! ");
                    message.addExtra(cancelInput);
                    player.spigot().sendMessage(message);
                    break;

                case "3":
                    player.sendMessage("LeftClick with the fist to set Pos1 and RightClick to set Pos2.");
                    break;

                case "4":
                    editorMachine.setEditorState(editorMachine.selectionSubMenu);
                    editorMachine.selectionSubMenu.refresh();
                    break;

                case "5":
                    player.sendMessage("look in the direction you want to select and click the button!");
                    break;

                case "6":
                    editorMachine.setEditorState(editorMachine.waitingForRedstoneButton);
                    message = new TextComponent(ChatColor.YELLOW + "Hit a stone button with your fist. ");
                    message.addExtra(cancelInput);
                    player.spigot().sendMessage(message);
                    break;

                case "7":
                    editorMachine.setEditorState(editorMachine.redstoneMenu);
                    editorMachine.redstoneMenu.refresh();
                    break;

                case "8":
                    chatInterface();
                    break;

                case "9":
                    chatInterface();
                    break;

                case "done":
                    if(editorMachine.gateReadyToCreateCheck()){

                        editorMachine.getMainSimpleGates().summonGate(player, editorMachine);


                        player.sendMessage("GateCreation complete!");
                    }else{
                        player.sendMessage("Please check if all is setup properly\nSomething is missing to create the gate\nImportant things are: Name, Selection, Direction, Button.");
                    }

                    break;

                case "exit":
                    editorMachine.exitSetup();
                    break;

                case "get":
                    String direction = editorMachine.getMainSimpleGates().getCardinalDirection(player);
                    editorMachine.setDirection(direction);
                    refresh();
                    break;

                default:
                    player.sendMessage("No valid input found. Click on the lines to edit.");

            }

        } else {
            player.sendMessage("That was not a valid input. Please Click on lines or enter a number.");
        }

    }

}
