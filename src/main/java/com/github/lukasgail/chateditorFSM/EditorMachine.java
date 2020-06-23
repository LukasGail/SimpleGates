package com.github.lukasgail.chateditorFSM;

import com.github.lukasgail.simplegates.GlowingSelection;
import com.github.lukasgail.simplegates.SimpleGates;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class EditorMachine {

    EditorState chatMenu;
    EditorState waitingForName;


    EditorState chatRedstoneMenu;
    EditorState waitingForRedstoneButtonDelay;


    EditorState editorState;


    private Player player;
    private Plugin pluginSimpleGates;
    private SimpleGates mainSimpleGates;
    private World world;
    private final String pluginPrefix = ChatColor.GREEN + "[SimpleGate]";
    private String gateName;
    private Location selectedPos1;
    private Location selectedPos2;
    private GlowingSelection glowingSelection;
    private Material material;
    private boolean permeable;
    private double moveDistance;
    private double repetitions;
    private int delay;
    private String direction;
    private Block redstoneButton;
    private int redstoneButtonDelay;
    private int playerRange;
    private boolean opensOnlyWithPermission;


    boolean allImportantStuffIsSetup;




    public EditorMachine(Player player, Plugin pluginSimpleGates, SimpleGates mainSimpleGates) {

        chatMenu = new ChatMenu(this, player);
        waitingForName = new WaitingForName(this, player);
        chatRedstoneMenu = new ChatRedstoneMenu(this, player);
        waitingForRedstoneButtonDelay = new WaitingForRedstoneDelay(this, player);

        editorState = chatMenu;

        this.player = player;
        this.pluginSimpleGates = pluginSimpleGates;
        this.mainSimpleGates = mainSimpleGates;
        this.world = player.getWorld();

        glowingSelection = new GlowingSelection(player, pluginSimpleGates);
        material = Material.IRON_BLOCK;
        permeable = false;
        moveDistance = 0.1;
        repetitions = 10;
        delay = 0;
        redstoneButtonDelay = 0;
        playerRange = 0;
        opensOnlyWithPermission = false;
        allImportantStuffIsSetup = false;


    }

    void setEditorState(EditorState newEditorState){
        editorState = newEditorState;
    }

    public void enterRedstoneMenu(String string){
        editorState.enterRedstoneMenu(string);
    }



    void setGateName(String name){
        gateName = name;
    }

    void setBlockPosition1(Location loc){
        selectedPos1 = loc;
    }

    void setBlockPosition2(Location loc){
        selectedPos2 = loc;
    }

    void setMaterial(Material material){
        this.material = material;
    }

    void setPermeable(boolean bool){
        this.permeable = bool;
    }

    void setMoveDistance(double moveDistance){
        this.moveDistance = moveDistance;
    }

    void setRepetitions(double repetitions){
        this.repetitions = repetitions;
    }

    void setDelay(int delay){
        this.delay = delay;
    }

    void setDirection(String direction){
        this.direction = direction;
    }

    void setRedstoneButton(Block block){
        this.redstoneButton = block;
    }

    void removeRedstoneButton(){
        this.redstoneButton = null;
    }

    void setRedstoneButtonDelay(int delay){
        this.redstoneButtonDelay = delay;
    }

    void setPlayerRange(int range){
        this.playerRange = range;
    }

    void setOpensOnlyWithPermission(boolean bool){
        this.opensOnlyWithPermission = bool;
    }

    void setAllImportantStuffIsSetup(boolean bool){
        this.allImportantStuffIsSetup = bool;
    }

    void setFinished(){

    }

    public EditorState getChatMenu() {
        return chatMenu;
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public String getGateName() {
        return gateName;
    }

    public Location getSelectedPos1() {
        return selectedPos1;
    }

    public Location getSelectedPos2() {
        return selectedPos2;
    }

    public GlowingSelection getGlowingSelection() {
        return glowingSelection;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isPermeable() {
        return permeable;
    }

    public double getMoveDistance() {
        return moveDistance;
    }

    public double getRepetitions() {
        return repetitions;
    }

    public int getDelay() {
        return delay;
    }

    public String getDirection() {
        return direction;
    }

    public Block getRedstoneButton() {
        return redstoneButton;
    }

    public int getRedstoneButtonDelay() {
        return redstoneButtonDelay;
    }

    public int getPlayerRange() {
        return playerRange;
    }

    public boolean isOpensOnlyWithPermission() {
        return opensOnlyWithPermission;
    }

    public Plugin getPluginSimpleGates() {
        return pluginSimpleGates;
    }

    public SimpleGates getMainSimpleGates() {
        return mainSimpleGates;
    }

    public String getPluginPrefix() {
        return pluginPrefix;
    }

    public boolean isAllImportantStuffIsSetup() {
        return allImportantStuffIsSetup;
    }

    public boolean checkInputs(){
        return true;
    }

    public void setCancelSetup(){

    }

}