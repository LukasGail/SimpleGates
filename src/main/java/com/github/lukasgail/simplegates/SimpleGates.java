package com.github.lukasgail.simplegates;

import com.github.lukasgail.chateditorFSM.EditorMachine;
import com.github.lukasgail.saveAndLoad.DataManager;
import com.github.lukasgail.saveAndLoad.Gate;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.io.File;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SimpleGates extends JavaPlugin implements Listener {
    private ArrayList<GateBlock[]> gatesList = new ArrayList<>();
    private ArrayList<GlowingSelection> glowingSelections = new ArrayList<>();
    public ArrayList<LinkedPlayersAndEditors> nowEditing = new ArrayList<>();
    private final String pluginPrefix = ChatColor.GREEN + "[SimpleGate]";
    private final static String META_STRING = ChatColor.GOLD + "" + ChatColor.BOLD + "Gate selector stick";
    public Plugin pluginSimpleGate = this;
    public SimpleGates mainSimpleGates = this;
    public DataManager dataManager = new DataManager();

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SimpleGates]\tPlugin loaded!");
        getServer().getPluginManager().registerEvents(this, this);
        ((Logger) LogManager.getRootLogger()).addFilter(new CustomConsoleOutputFilter());


        File dir = getDataFolder();
        if (!dir.exists()){
            if (!dir.mkdir()){
                ((Logger) LogManager.getRootLogger()).error("Creation of plugin folder failed for plugin: "+ getDescription().getName());
            }
            else {
                System.out.println("Created plugin folder");
            }
        }

        // TODO: init database
        String databaseUrl = "jdbc:sqlite:" + new File(getDataFolder(), "data.db").getAbsolutePath();
        System.out.println("ConnectionString: " + databaseUrl);
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
            Dao<Gate, String> gateDao = DaoManager.createDao(connectionSource, Gate.class);
            TableUtils.createTableIfNotExists(connectionSource, Gate.class);

            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));

            Gate gate = new Gate();
            gate.setName(generatedString);
            gate.setWorld("coole world");
            gate.setOnlyOpensWithPermission(false);

            gateDao.create(gate);
            System.out.println(gateDao.queryForId(generatedString).getWorld());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_GREEN + "Commands for SimpleGate can only be executed as a Player.");
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            String command = cmd.getName().toLowerCase();

            String[] argsLowerCase = new String[args.length];
            if (args.length != 0) {
                for (int i = 0; i < args.length; i++) {
                    argsLowerCase[i] = args[i].toLowerCase();
                }
            }


            if (command.matches("gate")) {
                gateCommand(player, argsLowerCase);
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public boolean onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String input = event.getMessage();

        if (isPlayerEditing(player)) {

            EditorMachine editor = getEditor(player);
            editor.sendedInput(input);

            event.setCancelled(true);


        }

        return false;
    }


    public void gateCommand(Player player, String[] args) {

        if (args.length == 0) {
            helpPage(player);
        } else {
            String arg1 = args[0];
            switch (arg1) {

                case "create":
                    EditorMachine editorMachine = getEditor(player);
                    //EditorMachine editorMachine = new EditorMachine(player, pluginSimpleGate, mainSimpleGates);
                    editorMachine.getMainMenu().refresh();
                    break;

                case "help":
                    helpPage(player);
                    getLogger().info(pluginPrefix + "Help command called.");
                    break;

                case "sel":
                case "selector":
                    //giveSelectorStick(player);  In edit mode fore some seconds. Or the Hands. If player selected right Click on Chat or write done.
                    break;

                case "set":
                    //setGate(player, args);
                    break;

                case "setmaterial":
                    changeMaterial(player, args[1], args[2]);
                    break;

                case "move":
                    String moveArguments = pluginPrefix + "\nTry /gate move [number] <direction> <repetitions as number> <delay in ticks (20 ticks = 1 second)>";
                    if (args.length == 1) {
                        player.sendMessage(moveArguments);
                        player.sendMessage("Examples:\nnumber = 0.1\ndirection = n/s/w/e/ne/nw/se/sw/u(up)/d(down)\nrepetitions = 10\ndelay = 1");
                    }

                    if(args.length == 6){
                        moveGate(player, args[1], args[2], args[3], args[4], args[5]);

                    }


                    /*
                    if (args.length == 2) {
                        try {
                            moveGate(player, Double.parseDouble(args[1]));

                        } catch (Exception e) {
                            player.sendMessage(moveArguments);
                        }
                    } else if (args.length == 3) {
                        try {
                            moveGate(player, Double.parseDouble(args[1]), args[2]);
                        } catch (Exception e2) {
                            player.sendMessage(moveArguments);
                        }
                    } else if (args.length == 4) {
                        try {
                            moveGate(player, Double.parseDouble(args[1]), args[2], Long.parseLong(args[3]));
                        } catch (Exception e2) {
                            player.sendMessage(moveArguments);
                        }
                    } else if (args.length == 5) {
                        try {
                            moveGate(player, Double.parseDouble(args[1]), args[2], Long.parseLong(args[3]), Long.parseLong(args[4]));
                        } catch (Exception e2) {
                            player.sendMessage(moveArguments);
                        }
                    }

                     */



                    break;


                case "del":
                case "delete":
                    killAllGateBlocks();
                    break;


                case "inv":
                    if (args.length >= 2) {
                        invGate(player, args[1]);
                    } else {
                        player.sendMessage("Try /gate inv [gatename]");
                        player.sendMessage("For a list of Names type /gate list");
                    }
                    break;


                case "testinvall":
                    invAll(player);
                    break;


                case "list":
                    listGates(player);
                    break;

                default:
                    player.sendMessage(pluginPrefix);
                    player.sendMessage("Wrong arguments?");
                    player.sendMessage("Try /gate - for help.");
                    break;
            }
        }

    }

    public void helpPage(Player player) {
        //TODO
        player.sendMessage(pluginPrefix);
        player.sendMessage("-help Page-");
        player.sendMessage("/gate - help page");
        player.sendMessage("/gate help - help page");
        player.sendMessage("/gate sel/selector - gate selector tool");
        player.sendMessage("/gate set - summon gate");
        player.sendMessage("/gate del/delete - delete  all");
    }


    public void summonGate(Player player, EditorMachine editorMachine){

        GlowingSelection selection = editorMachine.getGlowingSelection();

        if (selection.getSelectedLocation1() != null && selection.getSelectedLocation2() != null && selection.getSelectedLocation1().getWorld().equals(selection.getSelectedLocation2().getWorld())) {

            List<Block> blocksList = selection.getBlocks();
            Block[] blocksArray = new Block[blocksList.size()];
            for (int i = 0; i < blocksArray.length; i++){
                blocksArray[i] = blocksList.get(i);
            }

            selection.removeSelectionEffect();

            GateBlock[] arrayForNewGate = listManager(editorMachine.getGateName(), blocksArray.length);
            for (int i = 0; i < blocksArray.length; i++) {
                GateBlock gateBlock = new GateBlock(selection.getSelectedLocation1().getWorld(), blocksArray[i].getX() + 0.5, blocksArray[i].getY(), blocksArray[i].getZ() + 0.5, editorMachine.getGateName(), editorMachine.getMaterial(), pluginSimpleGate);

                arrayForNewGate[i] = gateBlock;
            }




        } else {
            player.sendMessage(pluginPrefix);
            player.sendMessage(ChatColor.RED + "You either have not yet set both selection points or the points are not in the same world!");
        }

    }



    public void changeMaterial(Player player, String name, String materialString) {


        Material material = Material.matchMaterial(materialString);
        if (material == null) {
            material = Material.COAL_BLOCK;
            player.sendMessage("material not found!");
        }

        int gateIndex = 0;

        for (GateBlock[] next : gatesList) {
            if (next[0].getName().toLowerCase().equals(name)) {
                GateBlock[] gate = gatesList.get(gateIndex);
                for (int i = 0; i < gate.length; i++) {
                    GateBlock oldBlock = gate[i];
                    gate[i] = new GateBlock(oldBlock.getLoc().getWorld(), oldBlock.getX(), oldBlock.getY(), oldBlock.getZ(), oldBlock.getName(), material, pluginSimpleGate);
                    oldBlock.despawnGateBlock();
                }
                player.sendMessage("gatename: " + name + "was set to material: " + material);
                break;

            }
            gateIndex++;
        }


        player.sendMessage("gatename: " + name + "was not found. Try \"/gate list\" for a list of available names");


    }


    public static Vector setVectorDirection(String moveDirection, double distanceToMove){

        Vector vector = new Vector();

        if(moveDirection.toLowerCase().matches("n")){
            vector = new Vector(0, 0, -distanceToMove);
        }else if(moveDirection.toLowerCase().matches("s")){
            vector = new Vector(0, 0, distanceToMove);
        }else if(moveDirection.toLowerCase().matches("w")){
            vector = new Vector(-distanceToMove, 0, 0);
        }else if(moveDirection.toLowerCase().matches("e")){
            vector = new Vector(distanceToMove, 0, 0);
        }else if(moveDirection.toLowerCase().matches("ne")){
            vector = new Vector(distanceToMove, 0, -distanceToMove);
        }else if(moveDirection.toLowerCase().matches("se")){
            vector = new Vector(distanceToMove, 0, distanceToMove);
        }else if(moveDirection.toLowerCase().matches("nw")){
            vector = new Vector(-distanceToMove, 0, -distanceToMove);
        }else if(moveDirection.toLowerCase().matches("sw")){
            vector = new Vector(-distanceToMove, 0, distanceToMove);
        }else if(moveDirection.toLowerCase().matches("u")){
            vector = new Vector(0, distanceToMove, 0);
        }else if(moveDirection.toLowerCase().matches("d")){
            vector = new Vector(0, -distanceToMove, 0);
        }else{
            vector = new Vector(0, 0, 0);
        }

        return vector;

    }



    public void moveGate(Player player, String name, String direction, String distance, String timesToRun, String delay){

        GateBlock[] gateBlocks;

        for(GateBlock[] tempGateArray : gatesList){
            if(tempGateArray[0].getName().toLowerCase().equals(name.toLowerCase())){
                gateBlocks = tempGateArray;

                MoveSchedulerTask task = new MoveSchedulerTask(gateBlocks, direction, Double.parseDouble(distance), Long.parseLong(timesToRun));
                task.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, task, 0, Integer.parseInt(delay)));


                return;
            }
        }
        player.sendMessage("Gate was not found. /gate list - for a list of all available names.");
    }


    public void invGate(Player player, String name) {

        int gateIndex = 0;
        for (GateBlock[] next : gatesList) {
            if (next[0].getName().equals(name)) {
                GateBlock[] gate = gatesList.get(gateIndex);
                for (GateBlock block : gate) {
                    block.setCollision(false);
                }
                player.sendMessage("Changed collision from: " + name);
                break;

            }
            gateIndex++;
        }

    }

    public boolean isPlayerEditing(Player player) {
        for (LinkedPlayersAndEditors linked : nowEditing) {
            if (linked.getPlayer().equals(player)) {
                return true;
            }
        }
        return false;
    }


    public EditorMachine getEditor(Player player) {
        for (LinkedPlayersAndEditors linked : nowEditing) {
            if (linked.getPlayer().equals(player)) {
                return linked.getEditor();
            }
        }
        EditorMachine editor = new EditorMachine(player, pluginSimpleGate, mainSimpleGates);
        nowEditing.add(new LinkedPlayersAndEditors(player, editor));

        return editor;
    }


    public void invAll(Player player) {
        for (GateBlock[] array : gatesList) {
            for (GateBlock block : array) {
                block.setCollision(false);
                player.sendMessage(block.getName() + " " + ChatColor.GOLD + block.getId() + ChatColor.GREEN + " collision has been set to " + block.getCollision());
            }
        }
    }


    public void listGates(Player player) {
        String[] gateNamesList = new String[gatesList.size()];
        for (int i = 0; i < gatesList.size(); i++) {
            gateNamesList[i] = gatesList.get(i)[0].getName();
        }

        player.sendMessage(gateNamesList);
    }


    public GateBlock[] listManager(String name, int size) {
        GateBlock[] arrayToAdd = new GateBlock[size];
        gatesList.add(arrayToAdd);
        return arrayToAdd;
    }




    public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        double verticalRotation = player.getLocation().getPitch();
        if (rotation < 0) {
            rotation += 360.0;
        }

        if (verticalRotation < -60) {
            return "u";
        } else if (verticalRotation > 60) {
            return "d";
        } else if (0 <= rotation && rotation < 22.5) {
            return "w";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "nw";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "n";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "ne";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "e";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "se";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "s";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "sw";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "w";
        } else {
            return null;
        }
    }


    public void killAllGateBlocks() {
        //TODO
        String command = "kill @e[tag=slidingDoor]";
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

    }


    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (player.hasPermission("gate.selector") && (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.LEFT_CLICK_BLOCK))) {
            if (isPlayerEditing(player)) {

                if(event.getHand() == EquipmentSlot.OFF_HAND){
                    return;
                }

                EditorMachine editorMachine = getEditor(player);
                editorMachine.getEditorState().blockClick(event);

            }
        }
    }

    public GlowingSelection getPlayerGlowingSelection(Player player) {

        for (GlowingSelection selection : glowingSelections) {
            if (selection.getPlayer().equals(player)) {
                return selection;
            }
        }
        return null;
    }


    public static List<Block> select(World world, Location loc1, Location loc2) {

        //First of all, we create the list:
        List<Block> blocks = new ArrayList<Block>();

        //Next we will name each coordinate
        int x1 = loc1.getBlockX();
        int y1 = loc1.getBlockY();
        int z1 = loc1.getBlockZ();

        int x2 = loc2.getBlockX();
        int y2 = loc2.getBlockY();
        int z2 = loc2.getBlockZ();

        //Then we create the following integers
        int xMin, yMin, zMin;
        int xMax, yMax, zMax;
        int x, y, z;

        //Now we need to make sure xMin is always lower then xMax
        if (x1 > x2) { //If x1 is a higher number then x2
            xMin = x2;
            xMax = x1;
        } else {
            xMin = x1;
            xMax = x2;
        }

        //Same with Y
        if (y1 > y2) {
            yMin = y2;
            yMax = y1;
        } else {
            yMin = y1;
            yMax = y2;
        }

        //And Z
        if (z1 > z2) {
            zMin = z2;
            zMax = z1;
        } else {
            zMin = z1;
            zMax = z2;
        }

        //Now it's time for the loop
        for (x = xMin; x <= xMax; x++) {
            for (y = yMin; y <= yMax; y++) {
                for (z = zMin; z <= zMax; z++) {
                    Block b = new Location(world, x, y, z).getBlock();
                    blocks.add(b);
                }
            }
        }

        //And last but not least, we return with the list
        return blocks;

    }


    public ArrayList<GateBlock[]> getGatesList() {
        return gatesList;
    }

    public void setGatesList(ArrayList<GateBlock[]> gatesList) {
        this.gatesList = gatesList;
    }

    public ArrayList<GlowingSelection> getGlowingSelections() {
        return glowingSelections;
    }

    public void setGlowingSelections(ArrayList<GlowingSelection> glowingSelections) {
        this.glowingSelections = glowingSelections;
    }

    public ArrayList<LinkedPlayersAndEditors> getNowEditing() {
        return nowEditing;
    }

    public void setNowEditing(ArrayList<LinkedPlayersAndEditors> nowEditing) {
        this.nowEditing = nowEditing;
    }
}
