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

import java.util.ArrayList;

public class EditorMachine {

    EditorState mainMenu;
    EditorState waitingForName;
    EditorState selectionSubMenu;

    EditorState waitingForRedstoneButton;
    EditorState redstoneMenu;
    EditorState waitingForRedstoneButtonDelay;


    EditorState editorState;


    private Player player;
    private Plugin pluginSimpleGates;
    private SimpleGates mainSimpleGates;
    private World world;
    private final String pluginPrefix = ChatColor.GREEN + "[SimpleGate]";
    private String gateName;
    private Location selectedLocation1;
    private Location selectedLocation2;
    private GlowingSelection glowingSelection;
    private Material material;
    private boolean permeable;
    private double moveDistance;
    private double repetitions;
    private int delay;
    private String direction;
    private ArrayList<Block> redstoneButtons;
    private int redstoneButtonDelay;
    private int playerRange;
    private boolean opensOnlyWithPermission;


    boolean readyToSpawn;




    public EditorMachine(Player player, Plugin pluginSimpleGates, SimpleGates mainSimpleGates) {

        mainMenu = new MainMenu(this, player);
        waitingForName = new WaitingForName(this, player);
        selectionSubMenu = new SelectionSubMenu(this, player);
        redstoneMenu = new RedstoneMenu(this, player);
        waitingForRedstoneButtonDelay = new WaitingForRedstoneDelay(this, player);
        waitingForRedstoneButton = new WaitingForRedstoneButton(this, player);


        this.player = player;
        this.pluginSimpleGates = pluginSimpleGates;
        this.mainSimpleGates = mainSimpleGates;
        world = player.getWorld();
        redstoneButtons = new ArrayList<>();

        glowingSelection = new GlowingSelection(player, pluginSimpleGates);
        material = Material.IRON_BLOCK;
        permeable = false;
        moveDistance = 0.1;
        repetitions = 10;
        delay = 0;
        redstoneButtonDelay = 0;
        playerRange = 0;
        opensOnlyWithPermission = false;
        readyToSpawn = false;

        this.editorState = mainMenu;

    }


    void setEditorState(EditorState newEditorState){
        editorState = newEditorState;
    }

    public void sendedInput(String input){
        editorState.sendedInput(input);
    }


    public boolean validInput(String input){

        if(input.matches("[0-9]|done|exit|get")){

            return true;
        }
        return false;
    }



    public boolean gateReadyToCreateCheck() {
        if (this.getGateName() != null){
            if(direction.matches("[u]|[d]|[n]|[s]|[w]|[e]|[nw]|[ne]|[sw]|[se]")){
                if(glowingSelection.getBlocks() != null && glowingSelection.getBlocks().size() > 0) {
                    if(redstoneButtons != null && redstoneButtons.size() > 0){
                        return true;
                    }
                }
            }
        }
        return false;
    }



    public void exitSetup() {
        for (int i = 0; i < mainSimpleGates.nowEditing.size(); i++) {
            if (mainSimpleGates.nowEditing.get(i).getPlayer().equals(player)) {
                mainSimpleGates.nowEditing.remove(i);
                player.sendMessage("You are no longer editing!");
            }
        }


    }


    public void refresh(){
        editorState.refresh();
    }

    public EditorState getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(EditorState mainMenu) {
        this.mainMenu = mainMenu;
    }

    public EditorState getRedstoneMenu() {
        return redstoneMenu;
    }

    public void setRedstoneMenu(EditorState redstoneMenu) {
        this.redstoneMenu = redstoneMenu;
    }

    public EditorState getChatMenu() {
        return mainMenu;
    }

    public void setChatMenu(EditorState chatMenu) {
        this.mainMenu = chatMenu;
    }

    public EditorState getWaitingForName() {
        return waitingForName;
    }

    public void setWaitingForName(EditorState waitingForName) {
        this.waitingForName = waitingForName;
    }

    public EditorState getChatRedstoneMenu() {
        return redstoneMenu;
    }

    public void setChatRedstoneMenu(EditorState chatRedstoneMenu) {
        this.redstoneMenu = chatRedstoneMenu;
    }

    public EditorState getWaitingForRedstoneButtonDelay() {
        return waitingForRedstoneButtonDelay;
    }

    public void setWaitingForRedstoneButtonDelay(EditorState waitingForRedstoneButtonDelay) {
        this.waitingForRedstoneButtonDelay = waitingForRedstoneButtonDelay;
    }

    public EditorState getEditorState() {
        return editorState;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Plugin getPluginSimpleGates() {
        return pluginSimpleGates;
    }

    public void setPluginSimpleGates(Plugin pluginSimpleGates) {
        this.pluginSimpleGates = pluginSimpleGates;
    }

    public SimpleGates getMainSimpleGates() {
        return mainSimpleGates;
    }

    public void setMainSimpleGates(SimpleGates mainSimpleGates) {
        this.mainSimpleGates = mainSimpleGates;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public String getPluginPrefix() {
        return pluginPrefix;
    }

    public String getGateName() {
        return gateName;
    }

    public void setGateName(String gateName) {
        this.gateName = gateName;
    }

    public String getGateNameNeverNull(String gateName){
        if(this.gateName != null){
            return this.gateName;
        }
        return ChatColor.YELLOW+""+ChatColor.BOLD+"[No name set]";
    }

    public Location getSelectedLocation1() {
        return selectedLocation1;
    }

    public void setSelectedLocation1(Location selectedLocation1) {
        this.selectedLocation1 = selectedLocation1;
    }

    public Location getSelectedLocation2() {
        return selectedLocation2;
    }

    public void setSelectedLocation2(Location selectedLocation2) {
        this.selectedLocation2 = selectedLocation2;
    }

    public String getLocationAsString(Location loc){
        if(loc !=null){
            String locationString = loc.getBlock().getX()+","+loc.getBlock().getY()+","+loc.getBlock().getZ();
            return locationString;
        }
        return ChatColor.YELLOW+""+ChatColor.BOLD+"[not set]";
    }

    public GlowingSelection getGlowingSelection() {
        return glowingSelection;
    }

    public void setGlowingSelection(GlowingSelection glowingSelection) {
        this.glowingSelection = glowingSelection;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public boolean isPermeable() {
        return permeable;
    }

    public void setPermeable(boolean permeable) {
        this.permeable = permeable;
    }

    public double getMoveDistance() {
        return moveDistance;
    }

    public void setMoveDistance(double moveDistance) {
        this.moveDistance = moveDistance;
    }

    public double getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(double repetitions) {
        this.repetitions = repetitions;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirectionNeverNull(String direction){
        if(this.direction != null){
            return getDirection();
        }
        return ChatColor.YELLOW+""+ChatColor.BOLD+"[not set]";
    }

    public ArrayList<Block> getRedstoneButtons() {
        return redstoneButtons;
    }

    public void setRedstoneButtons(ArrayList<Block> redstoneButtons) {
        this.redstoneButtons = redstoneButtons;
    }

    public String getFirstRedstoneButtonAsStringNeverNull(ArrayList<Block> redstoneButtons){
        if(this.redstoneButtons != null && this.redstoneButtons.size()>0){
            return redstoneButtons.get(0).getType().toString()+" "+redstoneButtons.get(0).getX()+","+redstoneButtons.get(0).getY()+","+redstoneButtons.get(0).getZ();
        }else{
            return ChatColor.YELLOW+""+ChatColor.BOLD+"[not set]";
        }
    }

    public int getRedstoneButtonDelay() {
        return redstoneButtonDelay;
    }

    public void setRedstoneButtonDelay(int redstoneButtonDelay) {
        this.redstoneButtonDelay = redstoneButtonDelay;
    }

    public int getPlayerRange() {
        return playerRange;
    }

    public void setPlayerRange(int playerRange) {
        this.playerRange = playerRange;
    }

    public boolean isOpensOnlyWithPermission() {
        return opensOnlyWithPermission;
    }

    public void setOpensOnlyWithPermission(boolean opensOnlyWithPermission) {
        this.opensOnlyWithPermission = opensOnlyWithPermission;
    }

    public boolean isReadyToSpawn() {
        return readyToSpawn;
    }

    public void setReadyToSpawn(boolean readyToSpawn) {
        this.readyToSpawn = readyToSpawn;
    }
}