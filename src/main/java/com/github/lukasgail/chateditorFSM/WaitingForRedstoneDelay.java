package com.github.lukasgail.chateditorFSM;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class WaitingForRedstoneDelay implements EditorState{
    EditorMachine editorMachine;
    Player player;

    public WaitingForRedstoneDelay(EditorMachine editorMachine, Player player) {
        this.editorMachine = editorMachine;
        this.player = player;
    }

    @Override
    public void sendedInput(String input) {

        editorMachine.setGateName(input);
        editorMachine.setEditorState(editorMachine.redstoneMenu);

    }

    @Override
    public void refresh() {

        editorMachine.setEditorState(editorMachine.redstoneMenu);
        editorMachine.getRedstoneMenu().refresh();


    }

    @Override
    public void blockClick(PlayerInteractEvent event) {

    }


}
