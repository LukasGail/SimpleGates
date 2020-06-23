package com.github.lukasgail.simplegates;

import org.bukkit.entity.Player;

public class LinkedPlayersAndEditors {
    private Player player;
    private ChatGateEditor editor;

    public LinkedPlayersAndEditors(Player player, ChatGateEditor editor) {

        this.player = player;
        this.editor = editor;

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ChatGateEditor getEditor() {
        return editor;
    }

    public void setEditor(ChatGateEditor editor) {
        this.editor = editor;
    }
}
