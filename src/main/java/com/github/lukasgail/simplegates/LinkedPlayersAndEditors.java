package com.github.lukasgail.simplegates;

import com.github.lukasgail.chateditorFSM.EditorMachine;
import org.bukkit.entity.Player;

public class LinkedPlayersAndEditors {
    private Player player;
    private EditorMachine editor;

    public LinkedPlayersAndEditors(Player player, EditorMachine editor) {

        this.player = player;
        this.editor = editor;

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public EditorMachine getEditor() {
        return editor;
    }

    public void setEditor(EditorMachine editor) {
        this.editor = editor;
    }
}
