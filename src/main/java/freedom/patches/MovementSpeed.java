package freedom.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.engine.modifiers.ModifierValue;
import net.bytebuddy.asm.Advice;

/**
 * Movement Speed Patch
 * 
 * Modifies player movement speed based on the multiplier value.
 * Patches the getSpeed() method in Mob class but only affects PlayerMob.
 */
public class MovementSpeed {
    
    // Movement speed multiplier (1.0 = normal, 2.0 = double speed, etc.)
    public static float multiplier = 1.0f;
    
    @ModMethodPatch(target = Mob.class, name = "getSpeed", arguments = {})
    public static class OverrideSpeed {
        @Advice.OnMethodExit
        static void onExit(@Advice.This Mob mob, @Advice.Return(readOnly = false) float returnValue) {
            // Only apply to PlayerMob, not all mobs
            if (multiplier != 1.0f && mob instanceof PlayerMob) {
                returnValue = returnValue * multiplier;
            }
        }
    }
}
