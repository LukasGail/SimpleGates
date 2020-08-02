package com.github.lukasgail.simplegates;

import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

public class MoveSchedulerTask implements Runnable{
    private int taskId;
    private int runs;
    private long repetitions;
    private double distanceToMove;
    private String moveDirection;
    private GateBlock[] gateBlocks;
    private Vector vector = new Vector();

    public MoveSchedulerTask(GateBlock[] gateBlocks, String moveDirection, double distanceToMove) {
        this.taskId = 0;
        this.runs = 0;
        this.repetitions = 0;
        this.gateBlocks = gateBlocks;
        this.distanceToMove = distanceToMove;
        this.moveDirection = moveDirection;
        SimpleGates.setVectorDirection(vector, moveDirection, distanceToMove);
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
    public void setRepetitions(long repetitions){
        this.repetitions = repetitions;
    }

    public void cancelTask(){
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public void run() {
        if(repetitions == 0) {

            for(GateBlock tempBlock : gateBlocks){
                tempBlock.getArmorStand().teleport(tempBlock.getArmorStand().getLocation().add(vector));
            }
            cancelTask();
        }else{
            if(runs < repetitions){

                for(GateBlock tempBlock : gateBlocks){
                    tempBlock.getArmorStand().teleport(tempBlock.getArmorStand().getLocation().add(vector));
                }

            }else{
                cancelTask();
            }
        }
        runs++;
    }

}
