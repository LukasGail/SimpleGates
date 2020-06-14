package com.github.lukasgail.simplegates;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.loot.LootTable;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GateBlock {

    private Location loc;
    private Material material;
    private boolean collision;
    private int timeAlive;
    private LootTable lootTable;
    private ArmorStand armorStand;
    private Shulker shulker;
    private FallingBlock fallingBlock;
    private int id;
    private String name;
    private Plugin pluginSimpleGate;

    public GateBlock(World world, double x, double y, double z, String name, Plugin pluginSimpleGate) {
        this.pluginSimpleGate = pluginSimpleGate;
        this.loc = new Location(world, x + 0.5, y, z + 0.5);
        this.name = name;
        spawnGateBlock(world, this.loc, name);
        this.collision = true;

    }

    @SuppressWarnings("Deprecated")
    private void spawnGateBlock(World world, Location location, String name) {

        this.shulker = world.spawn(location, Shulker.class, shulker -> {

            shulker.addScoreboardTag("slidingDoor");
            shulker.addScoreboardTag(name);
            shulker.setGravity(false);
            shulker.setSilent(true);
            shulker.setInvulnerable(true);
            shulker.setAI(false);
            shulker.setCanPickupItems(false);
            shulker.setHealth(30);
            shulker.setAbsorptionAmount(30);
            shulker.setCollidable(false);
            shulker.setGlowing(false);
            shulker.addPotionEffect((new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 10, false, false, false)));
            shulker.addPotionEffect((new PotionEffect(PotionEffectType.ABSORPTION, Integer.MAX_VALUE, 10, false, false, false)));
            shulker.addPotionEffect((new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 10, false, false, false)));
            shulker.addPotionEffect((new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 10, false, false, false)));



            this.fallingBlock = world.spawnFallingBlock(location, Material.IRON_BLOCK, (byte) 0);
            fallingBlock.addScoreboardTag("slidingDoor");
            fallingBlock.addScoreboardTag(name);
            fallingBlock.setGravity(false);
            fallingBlock.setInvulnerable(true);
            fallingBlock.setDropItem(false);
            fallingBlock.setFallDistance(0);
            fallingBlock.setGlowing(false);
            fallingBlock.setTicksLived(1);



            this.armorStand = world.spawn(location, ArmorStand.class, armorStand -> {
                armorStand.addScoreboardTag("slidingDoor");
                armorStand.addScoreboardTag(name);
                armorStand.setGravity(false);
                armorStand.setInvulnerable(true);
                armorStand.setSmall(true);
                armorStand.setMarker(true);
                armorStand.setVisible(false);
                armorStand.setGlowing(false);
                armorStand.addPassenger(shulker);
                armorStand.addPassenger(fallingBlock);



            });

        });

        this.id = armorStand.getEntityId();

        new BukkitRunnable() {
            public void run() {
                if(fallingBlock.isDead()) {
                    this.cancel();
                } else {
                    fallingBlock.setTicksLived(1);
                }
            }
        }.runTaskTimer(pluginSimpleGate, 0, 20L);


    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


    public void despawnGateBlock() {
        this.shulker.remove();
        this.fallingBlock.remove();
        this.armorStand.remove();
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
        if (!collision) {
            this.shulker.remove();
        }
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

    public void blockUpdate() {
        //TODO
    }


    public int getId() {
        return this.id;
    }


}
