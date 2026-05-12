package com.swill.killaura;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class KillAura {
    
    private static boolean enabled = true;
    private static int range = 4;
    
    public static void tick(MinecraftClient client) {
        if (!enabled || client.player == null || client.world == null) return;
        if (client.player.getAttackCooldownProgress(0) < 0.9f) return;
        
        Entity target = getNearestEntity(client);
        if (target != null) {
            if (client.interactionManager != null) {
                client.interactionManager.attackEntity(client.player, target);
                client.player.swingHand(Hand.MAIN_HAND);
            }
        }
    }
    
    private static Entity getNearestEntity(MinecraftClient client) {
        Entity nearest = null;
        double nearestDist = range;
        
        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player) continue;
            if (!(entity instanceof LivingEntity)) continue;
            if (entity instanceof PlayerEntity && ((PlayerEntity)entity).isCreative()) continue;
            
            double dist = client.player.distanceTo(entity);
            if (dist < nearestDist) {
                nearestDist = dist;
                nearest = entity;
            }
        }
        return nearest;
    }
    
    public static void setEnabled(boolean e) { enabled = e; }
    public static boolean isEnabled() { return enabled; }
}
