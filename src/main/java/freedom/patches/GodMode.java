package freedom.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.Attacker;
import net.bytebuddy.asm.Advice;

public class GodMode {
    public static boolean enabled = false;

    @ModMethodPatch(target = Mob.class, name = "setHealthHidden", arguments = {int.class, float.class, float.class, Attacker.class, boolean.class})
    public static class PreventDamage {
        @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
        static boolean onEnter(@Advice.This Mob mob, @Advice.Argument(0) int newHealth, @Advice.Argument(value = 0, readOnly = false) int health) {
            if (!enabled) return false;
            
            if (mob instanceof PlayerMob) {
                int currentHealth = mob.getHealth();

                if (newHealth < currentHealth) {
                    health = currentHealth;
                }
            }
            return false;
        }
    }

    @ModMethodPatch(target = PlayerMob.class, name = "useHunger", arguments = {float.class, boolean.class})
    public static class PreventHungerLoss {
        @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
        static boolean onEnter() {
            return enabled;
        }
    }
}
