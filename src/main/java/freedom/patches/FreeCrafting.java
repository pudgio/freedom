package freedom.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.inventory.InventoryItemsRemoved;
import necesse.inventory.InventoryRange;
import necesse.inventory.recipe.CanCraft;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.item.Item;
import necesse.entity.mobs.PlayerMob;
import necesse.level.maps.Level;
import net.bytebuddy.asm.Advice;

import java.util.ArrayList;
import java.util.function.Predicate;

public class FreeCrafting {
    
    public static boolean enabled = false;
    
    @ModMethodPatch(target = Recipe.class, name = "canCraftRange", arguments = {
        Ingredient[].class, Level.class, PlayerMob.class, Iterable.class, 
        boolean.class, Predicate.class
    })
    public static class PatchCanCraftRange {
        
        @Advice.OnMethodExit
        static void exit(@Advice.Argument(0) Ingredient[] ingredients,
                        @Advice.Argument(2) PlayerMob player,
                        @Advice.Argument(4) boolean countAllIngredients,
                        @Advice.Return(readOnly = false) CanCraft result) {
            if (enabled && player != null) {
                // ignore food production stations (will generate infinite food)
                result = new CanCraft(ingredients, countAllIngredients);
                
                for (int i = 0; i < ingredients.length; i++) {
                    int amount = ingredients[i].getIngredientAmount();
                    result.addIngredient(i, (amount == 0) ? -1 : amount);
                }
            }
        }
    }
    
    @ModMethodPatch(target = Recipe.class, name = "craftRange", arguments = {
        Ingredient[].class, Level.class, PlayerMob.class, Iterable.class, 
        Predicate.class
    })
    public static class PatchCraftRange {
        
        @Advice.OnMethodExit
        static void exit(@Advice.Argument(2) PlayerMob player,
                        @Advice.Return(readOnly = false) ArrayList<InventoryItemsRemoved> result) {
            if (enabled && player != null) {
                
                result = new ArrayList<>();
            }
        }
    }
}