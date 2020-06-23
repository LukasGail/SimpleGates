package com.github.lukasgail.chateditorFSM;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class WaitingForRedstoneDelay implements EditorState{
    EditorMachine editorMachine;
    Player player;

    public WaitingForRedstoneDelay(EditorMachine editorMachine, Player player) {
        this.editorMachine = editorMachine;
        this.player = player;
    }


    @Override
    public void refresh() {

    }

    @Override
    public void enterName(String name) {

    }

    @Override
    public void enterBlockPosition1(Location pos1) {

    }

    @Override
    public void enterBlockPosition2(Location pos2) {

    }

    @Override
    public void enterMaterial(Material material) {

    }

    @Override
    public void togglePermeability(Boolean bool) {

    }

    @Override
    public void enterMoveDistance(Double moveDistance) {

    }

    @Override
    public void enterRepetitions(Double repetitions) {

    }

    @Override
    public void enterDelay(int delay) {

    }

    @Override
    public void enterDirection(String direction) {

    }

    @Override
    public void enterRedstoneButton(Block button) {

    }

    @Override
    public void enterRedstoneMenu(String string) {

    }

    @Override
    public void enterRedstoneButtonDelay(int delay) {

    }

    @Override
    public void enterPlayerRange(int range) {

    }

    @Override
    public void toggleOpensWithPermission(boolean bool) {

    }

    @Override
    public void finishEditing() {

    }

    @Override
    public void cancelInput() {

    }

    @Override
    public void cancelSetup() {

    }
}
