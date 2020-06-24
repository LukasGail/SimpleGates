package com.github.lukasgail.chateditorFSM;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WaitingForRedstoneButton implements EditorState{
    EditorMachine editorMachine;
    Player player;

    public WaitingForRedstoneButton(EditorMachine editorMachine, Player player) {
        this.editorMachine = editorMachine;
        this.player = player;
    }


    @Override
    public void sendedInput(String input) {

        if(input.equals("cancel")){
            player.sendMessage("canceled input!");
            editorMachine.setEditorState(editorMachine.mainMenu);
            return;
        }

        player.sendMessage("Select a button or cancel selection!");

    }

    @Override
    public void refresh() {

        editorMachine.getMainMenu().refresh();

    }

    @Override
    public void blockClick(PlayerInteractEvent event) {

        Action action = event.getAction();
        ItemStack item = event.getItem();
        Location playerLocation = event.getPlayer().getLocation();
        playerLocation.setWorld(player.getWorld()); //To be sure about the existence of the world in Location.

        if(player.getItemInHand().getType().equals(Material.AIR)){

            //TODO Check that all buttons can be used.
            if(event.getClickedBlock().getType().equals(Material.STONE_BUTTON)){
                editorMachine.setRedstoneButton(event.getClickedBlock());
                editorMachine.setEditorState(editorMachine.mainMenu);
                refresh();
            }else{
                player.sendMessage("That was not a StoneButton!");

            }
            event.setCancelled(true);
        }


    }


}