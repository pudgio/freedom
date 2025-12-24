package freedom.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.entity.mobs.gameDamageType.DamageType;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.ItemAttackerWeaponItem;
import necesse.inventory.item.toolItem.ToolItem;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import net.bytebuddy.asm.Advice;

/**
 * Patches all weapons to maximum Necesse stats when enabled.
 * 
 * MAXIMUM VALUES:
 * - Attack Damage: 540 (Tier 10 Glacial Greatsword base)
 * - Attack Speed Modifier: +30% (Agile enchantment)
 * - Attack Range: 800 (Ice Javelin)
 * - Knockback: 150 (Greatswords)
 * - Velocity: 800 (Necrotic Flask)
 * - Mana Cost: 0 (free casting)
 * - Life Cost: 0 (no health cost)
 * - Resilience Gain: 2.0F (Venom Staff)
 * - Life Steal: 3 (Blood Grimoire)
 */
public class MaxWeapons {
    
    public static boolean enabled = false;
    
    // Thread-local flag to prevent infinite recursion
    public static ThreadLocal<Boolean> isProcessing = ThreadLocal.withInitial(() -> false);
    
    /**
     * Patches weapon damage to maximum value (540)
     */
    @ModMethodPatch(target = ToolItem.class, name = "getFlatAttackDamage", arguments = {InventoryItem.class})
    public static class OverrideDamage {
        @Advice.OnMethodExit
        static void onExit(@Advice.This ToolItem toolItem,
                          @Advice.Argument(0) InventoryItem item,
                          @Advice.Return(readOnly = false) GameDamage returnValue) {
            if (!enabled || !(toolItem instanceof ItemAttackerWeaponItem)) {
                return;
            }
            
            if (isProcessing.get()) {
                return;
            }
            
            try {
                isProcessing.set(true);
                // Set maximum weapon damage (540)
                returnValue = new GameDamage(540);
            } finally {
                isProcessing.set(false);
            }
        }
    }
    
    /**
     * Patches weapon attack speed to +30%
     */
    @ModMethodPatch(target = ToolItem.class, name = "getAttackSpeedModifier", arguments = {InventoryItem.class, Mob.class})
    public static class OverrideAttackSpeed {
        @Advice.OnMethodExit
        static void onExit(@Advice.This ToolItem toolItem,
                          @Advice.Return(readOnly = false) float returnValue) {
            if (enabled && toolItem instanceof ItemAttackerWeaponItem) {
                returnValue = 1.3f; // +30% attack speed
            }
        }
    }
    
    /**
     * Patches weapon attack range to maximum (800)
     */
    @ModMethodPatch(target = ToolItem.class, name = "getAttackRange", arguments = {InventoryItem.class})
    public static class OverrideAttackRange {
        @Advice.OnMethodExit
        static void onExit(@Advice.This ToolItem toolItem,
                          @Advice.Return(readOnly = false) int returnValue) {
            if (enabled && toolItem instanceof ItemAttackerWeaponItem) {
                returnValue = 800; // Maximum range (Ice Javelin)
            }
        }
    }
    
    /**
     * Patches weapon knockback to maximum (150)
     */
    @ModMethodPatch(target = ToolItem.class, name = "getKnockback", arguments = {InventoryItem.class, Mob.class})
    public static class OverrideKnockback {
        @Advice.OnMethodExit
        static void onExit(@Advice.This ToolItem toolItem,
                          @Advice.Return(readOnly = false) int returnValue) {
            if (enabled && toolItem instanceof ItemAttackerWeaponItem) {
                returnValue = 150; // Maximum knockback (Greatswords)
            }
        }
    }
    
    /**
     * Patches projectile velocity to maximum (800)
     */
    @ModMethodPatch(target = ToolItem.class, name = "getFlatVelocity", arguments = {InventoryItem.class})
    public static class OverrideVelocity {
        @Advice.OnMethodExit
        static void onExit(@Advice.This ToolItem toolItem,
                          @Advice.Return(readOnly = false) int returnValue) {
            if (enabled && toolItem instanceof ItemAttackerWeaponItem) {
                returnValue = 800; // Maximum velocity (Necrotic Flask)
            }
        }
    }
    
    /**
     * Patches mana cost to 0 (free casting)
     */
    @ModMethodPatch(target = ToolItem.class, name = "getFlatManaCost", arguments = {InventoryItem.class})
    public static class OverrideManaCost {
        @Advice.OnMethodExit
        static void onExit(@Advice.This ToolItem toolItem,
                          @Advice.Return(readOnly = false) float returnValue) {
            if (enabled && toolItem instanceof ItemAttackerWeaponItem) {
                returnValue = 0.0f; // No mana cost
            }
        }
    }
    
    /**
     * Patches life cost to 0 (no health cost)
     */
    @ModMethodPatch(target = ToolItem.class, name = "getFlatLifeCost", arguments = {InventoryItem.class})
    public static class OverrideLifeCost {
        @Advice.OnMethodExit
        static void onExit(@Advice.This ToolItem toolItem,
                          @Advice.Return(readOnly = false) int returnValue) {
            if (enabled && toolItem instanceof ItemAttackerWeaponItem) {
                returnValue = 0; // No life cost
            }
        }
    }
    
    /**
     * Patches resilience gain to maximum (2.0F)
     */
    @ModMethodPatch(target = ToolItem.class, name = "getResilienceGain", arguments = {InventoryItem.class})
    public static class OverrideResilienceGain {
        @Advice.OnMethodExit
        static void onExit(@Advice.This ToolItem toolItem,
                          @Advice.Return(readOnly = false) float returnValue) {
            if (enabled && toolItem instanceof ItemAttackerWeaponItem) {
                returnValue = 2.0f; // Maximum resilience gain (Venom Staff)
            }
        }
    }
    
    /**
     * Patches life steal to maximum (3)
     */
    @ModMethodPatch(target = ToolItem.class, name = "getLifeSteal", arguments = {InventoryItem.class})
    public static class OverrideLifeSteal {
        @Advice.OnMethodExit
        static void onExit(@Advice.This ToolItem toolItem,
                          @Advice.Return(readOnly = false) int returnValue) {
            if (enabled && toolItem instanceof ItemAttackerWeaponItem) {
                returnValue = 3; // Maximum life steal (Blood Grimoire)
            }
        }
    }
}
