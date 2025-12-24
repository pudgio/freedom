package freedom.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.inventory.item.toolItem.ToolDamageItem;
import necesse.inventory.InventoryItem;
import necesse.entity.mobs.Mob;
import net.bytebuddy.asm.Advice;

/**
 * Max Tools Patch
 * 
 * Overrides tool stats to maximum values when enabled.
 */
public class MaxTools {
    
    // Toggle for max tool upgrades
    public static boolean enabled = false;
    
    @ModMethodPatch(target = ToolDamageItem.class, name = "getToolTier", arguments = {InventoryItem.class, Mob.class})
    public static class OverrideToolTier {
        @Advice.OnMethodExit
        static void onExit(@Advice.Return(readOnly = false) float returnValue) {
            if (MaxTools.enabled) {
                returnValue = 10.0f;
            }
        }
    }
    
    @ModMethodPatch(target = ToolDamageItem.class, name = "getToolDps", arguments = {InventoryItem.class, Mob.class})
    public static class OverrideToolDps {
        @Advice.OnMethodExit
        static void onExit(@Advice.Return(readOnly = false) int returnValue) {
            if (MaxTools.enabled) {
                returnValue = 800;
            }
        }
    }
    
    @ModMethodPatch(target = ToolDamageItem.class, name = "getMiningSpeedModifier", arguments = {InventoryItem.class, Mob.class})
    public static class OverrideMiningSpeed {
        @Advice.OnMethodExit
        static void onExit(@Advice.Return(readOnly = false) float returnValue) {
            if (MaxTools.enabled) {
                returnValue = 16.0f;
            }
        }
    }
}
