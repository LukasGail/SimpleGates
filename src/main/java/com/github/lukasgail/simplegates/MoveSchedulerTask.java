package com.github.lukasgail.simplegates;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveSchedulerTask implements Runnable {
    private int taskId;
    private int runs;
    private long timesToRun;
    private double distanceToMove;
    private String moveDirection;
    private GateBlock[] gateBlocks;
    private Vector vector;

    public MoveSchedulerTask(GateBlock[] gateBlocks, String moveDirection, double distanceToMove, long timesToRun) {
        this.taskId = 0;
        this.runs = 0;
        this.timesToRun = timesToRun;
        this.gateBlocks = gateBlocks;
        this.distanceToMove = distanceToMove;
        this.moveDirection = moveDirection;
        this.vector = SimpleGates.setVectorDirection(moveDirection, distanceToMove);
    }

    public void setTaskId(int id) {
        this.taskId = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getRuns() {
        return runs;
    }

    public void setTimesToRun(long timesToRun) {
        this.timesToRun = timesToRun;
    }

    public void cancelTask() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public void run() {
        if (runs < timesToRun) {

            for (GateBlock tempBlock : gateBlocks) {

                ArmorStand tmpArmorStand = tempBlock.getArmorStand();
                ArrayList<Player> playersInLocationAbove = getPlayersStandingOnBlock(tmpArmorStand.getLocation());

                if (playersInLocationAbove != null) {
                    for (Player player : playersInLocationAbove) {

                        //player.teleport(player.getLocation().add(vector));
                        //Location newLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
                        //newLoc.add(vector);
                        //player.teleport(newLoc);
                        Location newLocationForPlayer = player.getLocation().add(vector);
                        Bukkit.dispatchCommand(player, String.format("tp %s %s %s %s",
                                player.getDisplayName(),
                                newLocationForPlayer.getX(),
                                newLocationForPlayer.getY(),
                                newLocationForPlayer.getZ()));

                    }

                }


                tmpArmorStand.eject();
                tmpArmorStand.teleport(tmpArmorStand.getLocation().add(vector));

                tmpArmorStand.addPassenger(tempBlock.getShulker());
                tmpArmorStand.addPassenger(tempBlock.getFallingBlock());

            }
        } else {
            cancelTask();
        }
        runs++;
    }

    public ArrayList<Player> getPlayersStandingOnBlock(Location blockLocation) {
        List<Player> playersInWorld = blockLocation.getWorld().getPlayers();
        ArrayList<Player> playersOnBlock = new ArrayList<>();

        double[] dimX = new double[2]; // west/east
        double[] dimZ = new double[2]; // north/south
        double[] dimY = new double[2]; // up/down

        dimX[0] = blockLocation.getX() - 0.5;
        dimX[1] = blockLocation.getX() + 0.5;
        Arrays.sort(dimX);
        dimZ[0] = blockLocation.getZ() - 0.5;
        dimZ[1] = blockLocation.getZ() + 0.5;
        Arrays.sort(dimZ);
        dimY[0] = blockLocation.getY() + 0.5;
        dimY[1] = blockLocation.getY() + 1.5;
        Arrays.sort(dimY);


        for (Player player : playersInWorld) {
            if ((player.getLocation().getX() < dimX[1] && player.getLocation().getX() > dimX[0])
                    && (player.getLocation().getZ() < dimZ[1] && player.getLocation().getZ() > dimZ[0])
                    && (player.getLocation().getY() < dimY[1] && player.getLocation().getY() > dimY[0])) {

                playersOnBlock.add(player);
            }
        }

        /*TODO same thing with y*/

        if (playersOnBlock.size() != 0) {
            return playersOnBlock;
        } else {
            return null;
        }
    }

}
