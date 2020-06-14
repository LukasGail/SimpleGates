package com.github.lukasgail.simplegates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class SimpleGates extends JavaPlugin implements Listener {
    private final static String META_STRING = ChatColor.GOLD + "" + ChatColor.BOLD + "Gate selector stick";
    private Location selectedLocation1;
    private Location selectedLocation2;
    private ArrayList<GateBlock[]> gatesList = new ArrayList<>();
    private final String pluginPrefix = ChatColor.GREEN + "[SimpleGate]";

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SimpleGates]\tPlugin loaded!");
        getServer().getPluginManager().registerEvents(this, this);
        ((Logger) LogManager.getRootLogger()).addFilter(new CustomConsoleOutputFilter());
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


    public void gateCommand(Player player, String[] args) {

        if (args.length == 0) {
            helpPage(player);
        } else {
            String arg1 = args[0];
            switch (arg1) {
                case "help":
                    helpPage(player);
                    getLogger().info(pluginPrefix + "Help command called.");
                    break;

                case "sel":
                case "selector":
                    giveSelectorStick(player);
                    break;

                case "set":
                    setGate(player, args);
                    break;

                case "move":
                    String moveArguments = pluginPrefix + "\nTry /gate move [number] <direction> <repetitions as number> <delay in ticks (20 ticks = 1 second)>";
                    if (args.length == 1) {
                        player.sendMessage(moveArguments);
                        player.sendMessage("Examples:\nnumber = 0.1\ndirection = n/s/w/e/ne/nw/se/sw/u(up)/d(down)\nrepetitions = 10\ndelay = 1");
                    }
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
                    break;


                case "del":
                case "delete":
                    delGate();
                    break;


                case "inv":
                    if (args.length >= 2) {
                        invGate(args[1]);
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

    public void giveSelectorStick(Player player) {
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(pluginPrefix);
            player.sendMessage(ChatColor.RED + "Your inventory is full!");
        } else {
            ItemStack stick = new ItemStack(Material.STICK, 1);
            ItemMeta stickmeta = stick.getItemMeta();
            stickmeta.setDisplayName(META_STRING);
            stickmeta.addEnchant(Enchantment.ARROW_INFINITE, 10, true);
            stick.setItemMeta(stickmeta);

            player.getInventory().setItem(player.getInventory().firstEmpty(), stick);
            player.sendMessage(pluginPrefix);
            player.sendMessage(ChatColor.GREEN + "A selector stick was given to your inventory.\n            Click with the left mouse to select the first position\n            and with the right for the second position.\n");
        }
    }

    public void setGate(Player player, String[] args) {

        if (selectedLocation1 != null && selectedLocation2 != null && selectedLocation1.getWorld().equals(selectedLocation2.getWorld())) {

            List<Block> blocks = select(player.getWorld(), selectedLocation1, selectedLocation2);
            Block[] blocksArray = new Block[blocks.size()];
            blocksArray = blocks.toArray(blocksArray);

            if (args.length == 1) {
                player.sendMessage(pluginPrefix + ChatColor.RED + "\nYou have to specify a name for the gate.\nTry /gate set [name] <force>");
            } else if (args.length >= 2 && blocksArray.length < 11) {

                spawnBlocks(player, args, blocksArray);

            } else {
                if (args.length >= 3 && args[2].matches("^f(orce)?$")) {

                    spawnBlocks(player, args, blocksArray);

                } else {
                    player.sendMessage(pluginPrefix);
                    player.sendMessage(ChatColor.GREEN + "You have selected " + blocksArray.length + " blocks.");
                    player.sendMessage(ChatColor.GREEN + "[" + ChatColor.GOLD + "Warning" + ChatColor.GREEN + "] A large number of blocks could cause lags.");
                    player.sendMessage(ChatColor.GREEN + "If you are sure you want to set the Gate type:" + ChatColor.GOLD + " /gate set [name] force  (alternative f) ");
                }
            }

        } else {
            player.sendMessage(pluginPrefix);
            player.sendMessage(ChatColor.RED + "You either have not yet set both selection points or the points are not in the same world!");
        }
    }


    public void spawnBlocks(Player player, String[] args, Block[] blocksArray) {

        GateBlock[] arrayForNewGate = ListManager(args[1], blocksArray.length);
        for (int i = 0; i < blocksArray.length; i++) {
            GateBlock gateBlock = new GateBlock(selectedLocation1.getWorld(), blocksArray[i].getX(), blocksArray[i].getY(), blocksArray[i].getZ(), args[1]);

            arrayForNewGate[i] = gateBlock;
        }

    }


    public void moveGate(Player player, double moveValue) {
        String direction = getCardinalDirection(player);
        moveGate(player, moveValue, direction, 0, 0);
    }

    public void moveGate(Player player, double moveValue, String direction) {
        moveGate(player, moveValue, direction, 0, 0);
    }

    public void moveGate(Player player, double moveValue, String direction, long repetitions) {
        moveGate(player, moveValue, direction, repetitions, 0);
    }

    public void moveGate(Player player, double moveValue, String direction, long repetitions, long delay) {
        if (direction.equals("n") || direction.equals("ne") || direction.equals("e") || direction.equals("se") || direction.equals("s") || direction.equals("sw") || direction.equals("w") || direction.equals("nw") || direction.equals("u") || direction.equals("d") || direction.equals("up") || direction.equals("down")) {
            if (delay < 1) {
                delay = 1;
            }

            String n = "~ ~ ~-" + moveValue;
            String s = "~ ~ ~" + moveValue;
            String w = "~-" + moveValue + " ~ ~";
            String e = "~" + moveValue + " ~ ~";
            String ne = "~" + moveValue + " ~ ~-" + moveValue;
            String se = "~" + moveValue + " ~ ~" + moveValue;
            String nw = "~-" + moveValue + " ~ ~-" + moveValue;
            String sw = "~-" + moveValue + " ~ ~" + moveValue;
            String u = "~ ~" + moveValue + " ~";
            String d = "~ ~-" + moveValue + " ~";
            String moveResult = "";


            switch (direction) {
                case "n":
                    moveResult = n;
                    break;
                case "s":
                    moveResult = s;
                    break;
                case "w":
                    moveResult = w;
                    break;
                case "e":
                    moveResult = e;
                    break;
                case "ne":
                    moveResult = ne;
                    break;
                case "se":
                    moveResult = se;
                    break;
                case "nw":
                    moveResult = nw;
                    break;
                case "sw":
                    moveResult = sw;
                    break;
                case "u":
                case "up":
                    moveResult = u;
                    break;
                case "d":
                case "down":
                    moveResult = d;
                    break;
            }
            String command = "execute as @e[type=minecraft:armor_stand,tag=slidingDoor] at @s run tp " + moveResult;
            long delayTimeToKillRunnable = delay * repetitions;
            long delayTimerFromInput = delay;

            MoveSchedulerTask task = new MoveSchedulerTask();
            task.setCommand(command);
            task.setRepetitions(repetitions);
            task.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, task, 0, delay));


        } else {
            player.sendMessage(pluginPrefix);
            player.sendMessage("/gate move [value] <direction> <repetitions as number> <delay in ticks (20 ticks = 1 second)>");
            player.sendMessage("Wrond direction identifier.\nPossible inputs are n/s/w/e/ne/nw/se/sw/u/up/d/down.");
            player.sendMessage("You can also look in the desired direction and input no specific direction.");
        }


    }

    public void invGate(String name) {

        int gateIndex = 0;
        for (GateBlock[] next : gatesList) {
            if (next[0].getName() == name) {

                GateBlock[] gate = gatesList.get(gateIndex);
                for (GateBlock block : gate) {
                    block.setCollision(false);
                }

                break;

            }
            gateIndex++;
        }

    }

    public void invAll(Player player) {
        for (GateBlock[] array : gatesList) {
            for (GateBlock block : array) {
                block.despawnGateBlock();
                player.sendMessage(block.getName() + " " + ChatColor.GOLD + block.getId() + ChatColor.GREEN + " has been removed");
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


    public GateBlock[] ListManager(String name, int size) {
        GateBlock[] arrayToAdd = new GateBlock[size];
        gatesList.add(arrayToAdd);
        return gatesList.get(gatesList.size() - 1);

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


    public void delGate() {
        String command = "kill @e[tag=slidingDoor]";
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

    }


    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        Location playerLocation = event.getPlayer().getLocation();
        playerLocation.setWorld(player.getWorld()); //To be sure about the existence of the world in Location.

        if (player.hasPermission("gate.selector") && action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.LEFT_CLICK_BLOCK)) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(META_STRING) && item.getType() == Material.STICK) {

                int posX = event.getClickedBlock().getX();
                int posY = event.getClickedBlock().getY();
                int posZ = event.getClickedBlock().getZ();

                if (action.equals((Action.LEFT_CLICK_BLOCK))) {
                    selectedLocation1 = event.getClickedBlock().getLocation();
                } else {
                    selectedLocation2 = event.getClickedBlock().getLocation();
                }

                player.sendMessage(
                        String.format("%s[SimpleGates] Position%d at %s X=%d Y=%d Z=%d %s has been selected!",
                                ChatColor.GREEN,
                                action.equals(Action.LEFT_CLICK_BLOCK) ? 1 : 2,
                                ChatColor.GOLD,
                                posX,
                                posY,
                                posZ,
                                ChatColor.GREEN));

                event.setCancelled(true);

            }
        }
    }

    /*
    @EventHandler
    public static void onBroadcast(BroadcastMessageEvent event) {
        event.setCancelled(true);
        event.setMessage("test");
        Bukkit.getServer().getPlayer("Xerl0x").sendMessage("test as message");

    }

     */


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


}
