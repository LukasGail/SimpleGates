package com.github.lukasgail.simplegates;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;
import org.bukkit.loot.Lootable;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import javax.naming.Name;

public class GateBlock {

    private Location loc;
    private Material material;
    private boolean collision;
    private int timeAlive;
    private ArmorStand armorStand;
    private int id;

    public GateBlock(World world, double x, double y, double z) {
        this.loc = new Location(world, x + 0.5, y, z + 0.5);
        spawnGateBlock(world, this.loc);

    }

    @SuppressWarnings("Deprecated")
    private void spawnGateBlock(World world, Location location) {

        String command = "summon minecraft:armor_stand " + location.getX() +" "+ location.getY() +" "+ location.getZ() + " {NoGravity:1b,Invulnerable:1b,Small:1b,Marker:1b,Invisible:1b,PersistenceRequired:1b,Tags:[\"slidingDoor\"],Passengers:[{id:\"minecraft:shulker\",NoGravity:1b,Silent:1b,Invulnerable:1b,DeathLootTable:\"null\",PersistenceRequired:1b,NoAI:1b,AttachFace:0b,Tags:[\"slidingDoor\"],ActiveEffects:[{Id:10b,Amplifier:10b,Duration:247483647,ShowParticles:0b},{Id:14b,Amplifier:1b,Duration:247483647,ShowParticles:0b}]},{id:\"minecraft:falling_block\",BlockState:{Name:\"minecraft:iron_block\"},NoGravity:1b,Time:-247483648,DropItem:0b,Tags:[\"slidingDoor\"]}]}";
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

        

        //this.id = armorStand.getEntityId();
    }



    public void despawnGateBlock() {

    }


    public void setMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return this.material;
    }


    public void setX(double newX) {
        this.loc.setX(newX);
    }

    public double getX() {
        return this.loc.getX();
    }


    public void setY(double newY) {
        this.loc.setY(newY);
    }

    public double getY() {
        return this.loc.getY();
    }


    public void setZ(double newZ) {
        this.loc.setZ(newZ);
    }

    public double getZ() {
        return this.loc.getZ();
    }


    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean getCollision() {
        return this.collision;
    }


    public void setTimeAlive(int time) {
        this.timeAlive = time;
    }

    public int getTimeAlive() {
        return this.timeAlive;
    }


}
