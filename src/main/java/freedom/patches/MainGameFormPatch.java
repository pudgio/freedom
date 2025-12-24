package freedom.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.client.Client;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.state.MainGame;
import necesse.engine.window.GameWindow;
import necesse.gfx.forms.MainGameFormManager;
import necesse.gfx.forms.components.FormComponent;
import necesse.gfx.forms.components.FormContentIconButton;
import necesse.gfx.forms.components.FormInputSize;
import necesse.gfx.forms.presets.MapForm;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.ui.ButtonColor;
import necesse.gfx.ui.ButtonTexture;
import necesse.gfx.forms.events.FormEventListener;
import necesse.gfx.forms.events.FormInputEvent;
import net.bytebuddy.asm.Advice;
import freedom.forms.FreedomSettings;
import java.awt.Color;

public class MainGameFormPatch {
    
    private static FreedomSettings activeSettingsForm = null;
    private static boolean wasGameRunning = false;
    private static FormContentIconButton settingsButton = null;
    private static MainGameFormManager formManager = null;
    private static Client client = null;
    
    public static FreedomSettings getActiveSettingsForm() {
        return activeSettingsForm;
    }
    
    public static MainGameFormManager getFormManager() {
        return formManager;
    }
    
    public static void clearActiveSettingsForm() {
        activeSettingsForm = null;
    }
    
    public static void setGameWasRunning(boolean running) {
        wasGameRunning = running;
    }
    
    public static boolean getGameWasRunning() {
        return wasGameRunning;
    }
    
    public static void setSettingsButton(FormContentIconButton button, MainGameFormManager manager, Client clientRef) {
        settingsButton = button;
        formManager = manager;
        client = clientRef;
    }
    
    public static void hideSettingsButton() {
        if (settingsButton != null && formManager != null) {
            formManager.removeComponent(settingsButton);
        }
    }
    
    public static void showSettingsButton() {
        if (settingsButton != null && formManager != null && settingsButton.isDisposed()) {
            formManager.addComponent(settingsButton);
        }
    }
    
    public static void updateButtonPosition() {
        if (settingsButton != null && formManager != null && formManager.minimap != null) {
            MapForm minimap = formManager.minimap;
            int minimapX = minimap.getX();
            int minimapY = minimap.getY();
            int minimapHeight = minimap.getHeight();
            int minimapWidth = minimap.getWidth();
            
            int buttonSize = 32;
            int buttonX = minimapX + minimapWidth - buttonSize;
            int buttonY = minimapY + minimapHeight + 5;
            
            settingsButton.setPosition(buttonX, buttonY);
        }
    }
    
    public static class OpenSettingsListener implements FormEventListener<FormInputEvent<necesse.gfx.forms.components.FormButton>> {
        private final MainGameFormManager formManager;
        private final FormContentIconButton button;
        private final MainGame mainGame;
        private FreedomSettings settingsForm;
        
        public OpenSettingsListener(MainGameFormManager formManager, FormContentIconButton button, MainGame mainGame) {
            this.formManager = formManager;
            this.button = button;
            this.mainGame = mainGame;
        }
        
        @Override
        public void onEvent(FormInputEvent<necesse.gfx.forms.components.FormButton> event) {
            if (settingsForm == null || settingsForm.isDisposed()) {

                formManager.removeComponent(button);

                settingsForm = new FreedomSettings(button);
                activeSettingsForm = settingsForm;
                formManager.addComponent(settingsForm);
                
                MapForm minimap = formManager.minimap;
                int buttonSize = 32;
                int formX = minimap.getX() + minimap.getWidth() - settingsForm.getWidth();
                int formY = button.getY() + buttonSize + 5;
                settingsForm.setPosition(formX, formY);
                
                System.out.println("Freedom settings form opened");
            }
        }
    }
    
    @ModMethodPatch(target = MainGameFormManager.class, name = "setup", arguments = {})
    public static class AddModButton {
        
        @Advice.OnMethodExit
        static void onExit(@Advice.This MainGameFormManager formManager,
                          @Advice.FieldValue("client") Client client,
                          @Advice.FieldValue("mainGame") necesse.engine.state.MainGame mainGame) {
            
            // minimap alignment
            MapForm minimap = formManager.minimap;
            
            int minimapX = minimap.getX();
            int minimapY = minimap.getY();
            int minimapHeight = minimap.getHeight();
            int minimapWidth = minimap.getWidth();
            
            int buttonSize = 32;
            int buttonX = minimapX + minimapWidth - buttonSize;
            int buttonY = minimapY + minimapHeight + 5;
    
            GameTexture texture = GameTexture.fromFile("buttons/settings");
            ButtonTexture buttonTexture = new ButtonTexture(texture, new Color(255, 255, 255));
            
            FormContentIconButton modButton = new FormContentIconButton(
                buttonX,
                buttonY,
                FormInputSize.SIZE_32,
                ButtonColor.BASE,
                buttonTexture,
                new StaticMessage("Freedom Settings")
            );        

            MainGameFormPatch.setSettingsButton(modButton, formManager, client);            

            modButton.onClicked(new OpenSettingsListener(formManager, modButton, mainGame));
            formManager.addComponent((FormComponent)modButton);
        }
    }
    
    @ModMethodPatch(target = MainGameFormManager.class, name = "onWindowResized", arguments = {GameWindow.class})
    public static class OnWindowResized {
        
        @Advice.OnMethodExit
        static void onExit(@Advice.This MainGameFormManager formManager,
                          @Advice.Argument(0) GameWindow window) {
            MainGameFormPatch.updateButtonPosition();
        }
    }
}
