package com.github.lukasgail.simplegates;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Shulker;
import org.bukkit.util.Vector;

public class MoveSchedulerTask implements Runnable{
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
    public int getTaskId(){
        return taskId;
    }
    public int getRuns(){
        return runs;
    }
    public void setTimesToRun(long timesToRun){
        this.timesToRun = timesToRun;
    }

    public void cancelTask(){
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public void run() {
        if(runs < timesToRun) {

            for(GateBlock tempBlock : gateBlocks){

                ArmorStand tmpArmorStand = tempBlock.getArmorStand();
                Shulker tmpShulker = tempBlock.getShulker();
                FallingBlock tmpFallingBlock = tempBlock.getFallingBlock();

                tmpArmorStand.eject();
                tmpArmorStand.teleport(tmpArmorStand.getLocation().add(vector));

                tmpArmorStand.addPassenger(tmpShulker);
                tmpArmorStand.addPassenger(tmpFallingBlock);

            }
        }else{
            cancelTask();
        }
        runs++;
    }

}
