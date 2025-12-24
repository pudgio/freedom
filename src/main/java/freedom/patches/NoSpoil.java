package freedom.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.Item;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = Item.class, name = "shouldSpoilTick", arguments = {InventoryItem.class})
public class NoSpoil {
    public static boolean enabled = false;
    
    public static void apply() {
    }
    
    @Advice.OnMethodExit
    static void onExit(@Advice.Return(readOnly = false) boolean returnValue) {
        if (enabled) {
            returnValue = false;
        }
    }
}
