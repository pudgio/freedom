package freedom.forms;

// ...existing code...
import necesse.engine.util.GameMath;
import necesse.gfx.forms.components.FormSlider;
import necesse.gfx.gameFont.FontOptions;

// ...existing code...

public class MovementSpeedSlider extends FormSlider {
    private static final float MIN_VALUE = 1.0f;
    private static final float MAX_VALUE = 20.0f;
    
    public MovementSpeedSlider(int x, int y, int width, float initialValue) {
        super(" ", x, y, (int)(initialValue * 10), 10, 200, width, new FontOptions(11));
        this.drawValue = true;
        this.drawValueInPercent = false;
    }
    
    public float getFloatValue() {
        return getValue() / 10.0f;
    }
    
    public void setFloatValue(float value) {
        setValue((int)(GameMath.limit(value, MIN_VALUE, MAX_VALUE) * 10));
    }
    
    @Override
    public String getValueText() {
        return String.format("%.1fx", getValue() / 10.0f);
    }
}