package com.github.lukasgail.chateditorFSM;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class WaitingForName implements EditorState{
    EditorMachine editorMachine;
    Player player;

    public WaitingForName(EditorMachine editorMachine, Player player) {
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

        editorMachine.setGateName(input);
        editorMachine.setEditorState(editorMachine.mainMenu);
        refresh();

    }

    @Override
    public void refresh() {
        editorMachine.setEditorState(editorMachine.mainMenu);
        editorMachine.getMainMenu().refresh();

    }

    @Override
    public void blockClick(PlayerInteractEvent event) {

    }


}
