package com.github.lukasgail.chateditorFSM;


import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;

public interface EditorState {

    void sendedInput(String input);
    void refresh();
    void blockClick(PlayerInteractEvent event);

}
