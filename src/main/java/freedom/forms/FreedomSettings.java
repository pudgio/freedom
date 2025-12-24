package freedom.forms;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.StaticMessage;
import necesse.engine.window.GameWindow;
import necesse.gfx.forms.Form;
import necesse.gfx.forms.MainGameFormManager;
import necesse.gfx.forms.presets.MapForm;
import necesse.gfx.forms.components.FormContentIconButton;
import necesse.gfx.forms.components.localComponents.FormLocalCheckBox;
import necesse.gfx.forms.components.localComponents.FormLocalLabel;
import necesse.gfx.forms.components.localComponents.FormLocalTextButton;
import necesse.gfx.gameFont.FontOptions;
import freedom.patches.FreeCrafting;
import freedom.patches.MaxTools;
import freedom.patches.MaxWeapons;
import freedom.patches.MovementSpeed;
import freedom.patches.GodMode;
import freedom.patches.NoClip;
import freedom.patches.ShowNPCS;
import freedom.patches.NoSpoil;
import freedom.patches.MainGameFormPatch;

public class FreedomSettings extends Form {
    private FormContentIconButton settingsButton;
    
    public FreedomSettings(FormContentIconButton settingsButton) {
        super("focus", 280, 500);
        this.settingsButton = settingsButton;
        
        FormLocalLabel titleLabel = new FormLocalLabel(
            (GameMessage)new StaticMessage("Freedom Settings"),
            new FontOptions(16),
            -1,
            65,
            12,
            150
        );
        this.addComponent(titleLabel);
        
        FormLocalLabel checkboxLabel = new FormLocalLabel(
            (GameMessage)new StaticMessage("Free Crafting"),
            new FontOptions(11),
            -1,
            35,
            50,
            200
        );
        this.addComponent(checkboxLabel);
        
        FormLocalCheckBox checkbox = new FormLocalCheckBox(
            (GameMessage)new StaticMessage(""),
            10,
            50,
            FreeCrafting.enabled
        );
        
        checkbox.onClicked(e -> {
            FreeCrafting.enabled = checkbox.checked;
        });
        this.addComponent(checkbox);
        
        FormLocalLabel pickaxeLabel = new FormLocalLabel(
            (GameMessage)new StaticMessage("Max Tool Upgrades"),
            new FontOptions(11),
            -1,
            35,
            80,
            200
        );
        this.addComponent(pickaxeLabel);
        
        // tool max
        FormLocalCheckBox pickaxeCheckbox = new FormLocalCheckBox(
            (GameMessage)new StaticMessage(""),
            10,
            80,
            MaxTools.enabled
        );
        
        pickaxeCheckbox.onClicked(e -> {
            MaxTools.enabled = pickaxeCheckbox.checked;
        });
        this.addComponent(pickaxeCheckbox);
        
        FormLocalLabel weaponLabel = new FormLocalLabel(
            (GameMessage)new StaticMessage("Max Weapon Upgrades"),
            new FontOptions(11),
            -1,
            35,
            110,
            200
        );
        this.addComponent(weaponLabel);
        
        // weapon max
        FormLocalCheckBox weaponCheckbox = new FormLocalCheckBox(
            (GameMessage)new StaticMessage(""),
            10,
            110,
            MaxWeapons.enabled
        );
        
        weaponCheckbox.onClicked(e -> {
            MaxWeapons.enabled = weaponCheckbox.checked;
        });
        this.addComponent(weaponCheckbox);
        
        // godmode
        FormLocalLabel godModeLabel = new FormLocalLabel(
            (GameMessage)new StaticMessage("God Mode"),
            new FontOptions(11),
            -1,
            35,
            140,
            200
        );
        this.addComponent(godModeLabel);
        
        FormLocalCheckBox godModeCheckbox = new FormLocalCheckBox(
            (GameMessage)new StaticMessage(""),
            10,
            140,
            GodMode.enabled
        );
        
        godModeCheckbox.onClicked(e -> {
            GodMode.enabled = godModeCheckbox.checked;
        });
        this.addComponent(godModeCheckbox);
        
        // noclip
        FormLocalLabel noClipLabel = new FormLocalLabel(
            (GameMessage)new StaticMessage("No Clip"),
            new FontOptions(11),
            -1,
            35,
            170,
            200
        );
        this.addComponent(noClipLabel);
        
        FormLocalCheckBox noClipCheckbox = new FormLocalCheckBox(
            (GameMessage)new StaticMessage(""),
            10,
            170,
            NoClip.enabled
        );
        
        noClipCheckbox.onClicked(e -> {
            NoClip.enabled = noClipCheckbox.checked;
        });
        this.addComponent(noClipCheckbox);
        
        // npc
        FormLocalLabel showNPCsLabel = new FormLocalLabel(
            (GameMessage)new StaticMessage("Show NPCs on Minimap"),
            new FontOptions(11),
            -1,
            35,
            200,
            200
        );
        this.addComponent(showNPCsLabel);
        
        FormLocalCheckBox showNPCsCheckbox = new FormLocalCheckBox(
            (GameMessage)new StaticMessage(""),
            10,
            200,
            ShowNPCS.enabled
        );
        
        showNPCsCheckbox.onClicked(e -> {
            ShowNPCS.enabled = showNPCsCheckbox.checked;
        });
        this.addComponent(showNPCsCheckbox);
        
        // nospoil
        FormLocalLabel noSpoilLabel = new FormLocalLabel(
            (GameMessage)new StaticMessage("No Food Spoiling"),
            new FontOptions(11),
            -1,
            35,
            230,
            200
        );
        this.addComponent(noSpoilLabel);
        
        FormLocalCheckBox noSpoilCheckbox = new FormLocalCheckBox(
            (GameMessage)new StaticMessage(""),
            10,
            230,
            NoSpoil.enabled
        );
        
        noSpoilCheckbox.onClicked(e -> {
            NoSpoil.enabled = noSpoilCheckbox.checked;
            NoSpoil.apply();
        });
        this.addComponent(noSpoilCheckbox);
        
        // movement
        FormLocalLabel speedLabel = new FormLocalLabel(
            (GameMessage)new StaticMessage("Movement Speed"),
            new FontOptions(11),
            -1,
            10,
            270,
            200
        );
        this.addComponent(speedLabel);
        
        MovementSpeedSlider speedSlider = new MovementSpeedSlider(
            10,
            265,
            260,  // bar width
            MovementSpeed.multiplier
        );
        
        speedSlider.onChanged(e -> {
            MovementSpeed.multiplier = speedSlider.getFloatValue();
        });
        this.addComponent(speedSlider);
        
        FormLocalTextButton closeButton = new FormLocalTextButton(
            (GameMessage)new StaticMessage("Close"),
            (GameMessage)new StaticMessage("Close"),
            80,
            450,
            120
        );
        
        closeButton.onClicked(e -> {
            closeForm();
        });
        this.addComponent(closeButton);
    }
    
    @Override
    public void onWindowResized(GameWindow window) {
        super.onWindowResized(window);
        updatePosition();
    }
    
    private void updatePosition() {
        MainGameFormManager formManager = MainGameFormPatch.getFormManager();
        if (formManager != null && formManager.minimap != null && settingsButton != null) {
            MapForm minimap = formManager.minimap;
            int buttonSize = 32;
            int formX = minimap.getX() + minimap.getWidth() - this.getWidth();
            int formY = settingsButton.getY() + buttonSize + 5;
            this.setPosition(formX, formY);
        }
    }
    
    private void closeForm() {
        if (this.getManager() != null) {
            this.getManager().removeComponent(this);
        }
        cleanup();
    }
    
    private void cleanup() {
        MainGameFormPatch.showSettingsButton();
        MainGameFormPatch.clearActiveSettingsForm();
    }
}
