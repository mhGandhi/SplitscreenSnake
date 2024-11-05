import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerConfig {
    KeyEvent keyUp;
    KeyEvent keyLeft;
    KeyEvent keyDown;
    KeyEvent keyRight;
    Color color;

    public boolean isComplete(){
        return keyUp!=null && keyLeft != null && keyDown != null && keyRight !=null;
    }

    public PlayerConfig(KeyEvent pKeyUp, KeyEvent pKeyLeft, KeyEvent pKeyDown, KeyEvent pKeyRight, Color pColor){
        keyUp = pKeyUp;
        keyLeft = pKeyLeft;
        keyDown = pKeyDown;
        keyRight = pKeyRight;
        color = pColor;
    }
    public PlayerConfig(Color pCol){
        this(null,null,null,null,pCol);
    }

    public PlayerConfig copy() {
        return new PlayerConfig(keyUp,keyLeft,keyDown,keyRight,color);
    }

    @Override
    public String toString() {
        return keyUp.getKeyChar()+""+keyUp.getKeyCode()+" "+keyLeft.getKeyChar()+keyLeft.getKeyCode()+" "+keyDown.getKeyChar()+keyDown.getKeyCode()+" "+keyRight.getKeyChar()+keyRight.getKeyCode()+" - "+color.toString();
    }
}
