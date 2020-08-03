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
    private Vector vector = new Vector();

    public MoveSchedulerTask(GateBlock[] gateBlocks, String moveDirection, double distanceToMove, long timesToRun) {
        this.taskId = 0;
        this.runs = 0;
        this.timesToRun = timesToRun;
        this.gateBlocks = gateBlocks;
        this.distanceToMove = distanceToMove;
        this.moveDirection = moveDirection;
        //SimpleGates.setVectorDirection(vector, moveDirection, distanceToMove);
        vector=new Vector(0,1,0);
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
        Bukkit.broadcastMessage(vector.toString());
        if(runs < timesToRun) {

            ArmorStand firstArmorstand = gateBlocks[0].getArmorStand();
            ArmorStand lastArmorstand = gateBlocks[gateBlocks.length-1].getArmorStand();

            for(GateBlock tempBlock : gateBlocks){

                ArmorStand tmpArmorStand = tempBlock.getArmorStand();
                tmpArmorStand.setVisible(true);
                Shulker tmpShulker = tempBlock.getShulker();
                FallingBlock tmpFallingBlock = tempBlock.getFallingBlock();

                if((runs % 2) == 0){
                    firstArmorstand.addPassenger(tmpShulker);
                    firstArmorstand.addPassenger(tmpFallingBlock);

                }else{
                    lastArmorstand.addPassenger(tmpShulker);
                    lastArmorstand.addPassenger(tmpFallingBlock);
                }


            }
        }else{
            cancelTask();
        }
        runs++;
    }

}
