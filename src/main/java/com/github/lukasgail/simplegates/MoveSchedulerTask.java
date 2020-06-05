package com.github.lukasgail.simplegates;

import org.bukkit.Bukkit;

public class MoveSchedulerTask implements Runnable{
    private int taskId;
    private String command;
    private int runs;
    private long repetitions;

    public MoveSchedulerTask() {
        this.taskId = 0;
        this.command = "";
        this.runs = 0;
        this.repetitions = 0;
    }

    public void setTaskId(int id) {
        this.taskId = id;
    }
    public int getTaskId(){
        return taskId;
    }
    public void setCommand(String command){
        this.command = command;
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
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            cancelTask();
        }else{
            if(runs < repetitions){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }else{
                cancelTask();
            }
        }
        runs++;
    }
}
