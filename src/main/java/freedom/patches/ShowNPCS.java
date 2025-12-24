package freedom.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.friendly.human.HumanMob;
import net.bytebuddy.asm.Advice;

public class ShowNPCS {
    public static boolean enabled = false;

    @ModMethodPatch(target = HumanMob.class, name = "shouldDrawOnMap", arguments = {})
    public static class ShouldDrawOnMapPatch {
        @Advice.OnMethodExit
        static void onExit(@Advice.This HumanMob humanMob, @Advice.Return(readOnly = false) boolean returnValue) {
            if (ShowNPCS.enabled && humanMob.isVisible()) {
                returnValue = true;
            }
        }
    }
}