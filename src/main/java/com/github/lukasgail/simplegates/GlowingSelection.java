package com.github.lukasgail.simplegates;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static com.github.lukasgail.simplegates.SimpleGates.select;

public class GlowingSelection {

    private Plugin pluginSimpleGate;
    private final static String META_STRING = ChatColor.GOLD + "" + ChatColor.BOLD + "Gate selector stick";
    private final String pluginPrefix = ChatColor.GREEN + "[SimpleGate]";
    private Player player;
    private String playerName;
    private Location selectedLocation1;
    private Location selectedLocation2;
    private List<Block> blocks;
    private FallingBlock[] fallingBlocks;
    private Shulker[] shulkers;


    public GlowingSelection(Player player, Plugin plugin) {
        this.pluginSimpleGate = plugin;
        this.player = player;
        this.playerName = player.getDisplayName();
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
            stickmeta.addEnchant(Enchantment.LUCK, 10, true);
            stick.setItemMeta(stickmeta);

            player.getInventory().setItem(player.getInventory().firstEmpty(), stick);
            player.sendMessage(pluginPrefix + ChatColor.WHITE + "A selector stick was given to your inventory.\nClick with the left mouse to select the first position\nand with the right for the second position.");
        }
    }



    public void glowEffect() {
        if (selectedLocation1 != null && selectedLocation2 != null) {
            blocks = select(player.getWorld(), selectedLocation1, selectedLocation2);


            removeSelectionEffect();


            fallingBlocks = new FallingBlock[blocks.size()];
            shulkers = new Shulker[blocks.size()];

            for (int i = 0; i < fallingBlocks.length; i++) {
                Location loc = blocks.get(i).getLocation().add(0.5, 0, 0.5);

                this.fallingBlocks[i] = blocks.get(i).getWorld().spawnFallingBlock(loc, Material.SHULKER_BOX, (byte) 0);
                this.fallingBlocks[i].addScoreboardTag("selection");
                this.fallingBlocks[i].setGravity(false);
                this.fallingBlocks[i].setInvulnerable(true);
                this.fallingBlocks[i].setDropItem(false);
                this.fallingBlocks[i].setFallDistance(0);
                this.fallingBlocks[i].setGlowing(true);
                this.fallingBlocks[i].setTicksLived(1);

                FallingBlock fallingBlock = this.fallingBlocks[i];

                new BukkitRunnable() {
                    public void run() {
                        if (fallingBlock.isDead()) {
                            this.cancel();
                        } else {
                            player.spawnParticle(Particle.FLAME, fallingBlock.getLocation().add(0, 0.5, 0), 1, 0, 0, 0, 0);

                            player.spawnParticle(Particle.DRAGON_BREATH, fallingBlock.getLocation().add(0.4, 0.1, 0.4), 1, 0, 0, 0, 0);
                            player.spawnParticle(Particle.DRAGON_BREATH, fallingBlock.getLocation().add(0.4, 0.1, -0.4), 1, 0, 0, 0, 0);
                            player.spawnParticle(Particle.DRAGON_BREATH, fallingBlock.getLocation().add(0.4, 0.9, 0.4), 1, 0, 0, 0, 0);
                            player.spawnParticle(Particle.DRAGON_BREATH, fallingBlock.getLocation().add(0.4, 0.9, -0.4), 1, 0, 0, 0, 0);
                            player.spawnParticle(Particle.DRAGON_BREATH, fallingBlock.getLocation().add(-0.4, 0.1, 0.4), 1, 0, 0, 0, 0);
                            player.spawnParticle(Particle.DRAGON_BREATH, fallingBlock.getLocation().add(-0.4, 0.1, -0.4), 1, 0, 0, 0, 0);
                            player.spawnParticle(Particle.DRAGON_BREATH, fallingBlock.getLocation().add(-0.4, 0.9, 0.4), 1, 0, 0, 0, 0);
                            player.spawnParticle(Particle.DRAGON_BREATH, fallingBlock.getLocation().add(-0.4, 0.9, -0.4), 1, 0, 0, 0, 0);

                        }
                    }
                }.runTaskTimer(pluginSimpleGate, 0, 20L);

                if (loc.getBlock().getType().isSolid()) {

                    this.shulkers[i] = loc.getWorld().spawn(loc, Shulker.class, shulker -> {

                        shulker.addScoreboardTag("slidingDoor");
                        shulker.addScoreboardTag("selection");
                        shulker.setGravity(false);
                        shulker.setSilent(true);
                        shulker.setInvulnerable(true);
                        shulker.setAI(false);
                        shulker.setCanPickupItems(false);
                        shulker.setHealth(30);
                        shulker.setAbsorptionAmount(30);
                        shulker.setCollidable(true);
                        shulker.setGlowing(true);
                        shulker.addPotionEffect((new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 10, false, false, false)));
                        shulker.addPotionEffect((new PotionEffect(PotionEffectType.ABSORPTION, Integer.MAX_VALUE, 10, false, false, false)));
                        shulker.addPotionEffect((new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 10, false, false, false)));
                        shulker.addPotionEffect((new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 10, false, false, false)));

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(shulker.isDead()){
                                    this.cancel();
                                }else{
                                    shulker.remove();
                                }

                            }
                        }.runTaskLaterAsynchronously(pluginSimpleGate, 600);


                    });

                }


            }


        }
    }

    public void removeSelectionEffect() {

        if (fallingBlocks != null) {
            for (int i = 0; i < fallingBlocks.length; i++) {
                if (fallingBlocks[i] != null) {
                    fallingBlocks[i].remove();
                }
                if (shulkers[i] != null) {
                    shulkers[i].remove();
                }
            }
        }
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Location getSelectedLocation1() {
        return selectedLocation1;
    }

    public void setSelectedLocation1(Location selectedLocation1) {
        this.selectedLocation1 = selectedLocation1;
        glowEffect();
    }

    public Location getSelectedLocation2() {
        return selectedLocation2;
    }

    public void setSelectedLocation2(Location selectedLocation2) {
        this.selectedLocation2 = selectedLocation2;
        glowEffect();
    }
}
