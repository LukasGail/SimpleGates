package com.github.lukasgail.simplegates;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;
import org.bukkit.loot.Lootable;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;

import javax.naming.Name;
import java.util.Collection;
import java.util.Random;

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

    public GateBlock(World world, double x, double y, double z, String name) {
        this.loc = new Location(world, x + 0.5, y, z + 0.5);
        this.name = name;
        spawnGateBlock(world, this.loc, name);
        LootTable emptyLootTable = new LootTable() {
            @Override
            public Collection<ItemStack> populateLoot(Random random, LootContext context) {
                return null;
            }

            @Override
            public void fillInventory(Inventory inventory, Random random, LootContext context) {

            }

            @Override
            public NamespacedKey getKey() {
                return null;
            }
        };

        this.lootTable = emptyLootTable;

    }

    @SuppressWarnings("Deprecated")
    private void spawnGateBlock(World world, Location location, String name) {

        world.spawn(location, Shulker.class, shulker -> {

            shulker.addScoreboardTag("slidingDoor");
            shulker.setGravity(false);
            shulker.setSilent(true);
            shulker.setInvulnerable(true);
            shulker.setLootTable(lootTable);
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

            this.shulker = shulker;


            FallingBlock fallingBlock = world.spawnFallingBlock(location, Material.IRON_BLOCK, (byte) 0);
            fallingBlock.addScoreboardTag("slidingDoor");
            fallingBlock.setGravity(false);
            fallingBlock.setInvulnerable(true);
            fallingBlock.setDropItem(false);
            fallingBlock.setFallDistance(0);
            fallingBlock.setGlowing(false);
            fallingBlock.setTicksLived(1);

            this.fallingBlock = fallingBlock;


            world.spawn(location, ArmorStand.class, armorStand -> {
                armorStand.addScoreboardTag("slidingDoor");
                armorStand.setGravity(false);
                armorStand.setInvulnerable(true);
                armorStand.setSmall(true);
                armorStand.setMarker(true);
                armorStand.setVisible(false);
                armorStand.setGlowing(false);
                armorStand.addPassenger(shulker);
                armorStand.addPassenger(fallingBlock);

                this.armorStand = armorStand;


            });

        });

        this.id = armorStand.getEntityId();

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
