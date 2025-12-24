package freedom;

import necesse.engine.modLoader.annotations.ModEntry;
import freedom.patches.FreeCrafting;
import freedom.patches.MaxTools;
import freedom.patches.MovementSpeed;

@ModEntry
public class Freedom {
    
    public void init() {

    }
    
    public void postInit() {
        System.out.println("Freedom - [LOADED]");
    }
    
}