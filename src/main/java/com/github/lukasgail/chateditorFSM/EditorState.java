package com.github.lukasgail.chateditorFSM;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public interface EditorState {

    void refresh();
    void enterName(String name);
    void enterBlockPosition1(Location pos1);
    void enterBlockPosition2(Location pos2);
    void enterMaterial(Material material);
    void togglePermeability(Boolean bool);
    void enterMoveDistance(Double moveDistance);
    void enterRepetitions(Double repetitions);
    void enterDelay(int delay);
    void enterDirection(String direction);

    void enterRedstoneButton(Block button);

    void enterRedstoneMenu(String string);
    void enterRedstoneButtonDelay(int delay);
    void enterPlayerRange(int range); //Open if a player is in range (with permission).
    void toggleOpensWithPermission(boolean bool); //Just opens if player has permission.

    void finishEditing();
    void cancelInput();
    void cancelSetup();


}
