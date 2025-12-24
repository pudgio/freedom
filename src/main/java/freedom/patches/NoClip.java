package freedom.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.PlayerMob;
import necesse.level.maps.CollisionFilter;
import net.bytebuddy.asm.Advice;

/**
 * NoClip Patch
 * 
 * When enabled, allows the player to walk through walls, objects, and other mobs.
 */
public class NoClip {
    public static boolean enabled = false;

    /**
     * Patch PlayerMob.getLevelCollisionFilter() to return null when enabled.
     * When the collision filter is null, Level.collides() returns false, effectively
     * disabling all collision checks for the player.
     */
    @ModMethodPatch(target = PlayerMob.class, name = "getLevelCollisionFilter", arguments = {})
    public static class DisableCollisionFilter {
        @Advice.OnMethodExit
        static void onExit(@Advice.Return(readOnly = false) CollisionFilter returnValue) {
            if (enabled) {
                // Return null collision filter (disables all collision checking)
                returnValue = null;
            }
        }
    }
}
