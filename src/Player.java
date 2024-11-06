import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Player{

    ViewState viewState;
    PlayerConfig playerConfig;
    Direction dir;
    List<Pos> occupies;

    public Player(PlayerConfig pPlayerConfig, ViewState pViewState, Pos pStart){
        viewState = pViewState;
        playerConfig = pPlayerConfig;
        dir = Direction.NONE;
        occupies = new LinkedList<Pos>();
        occupies.add(pStart);
    }
    public void paint(Graphics g){
        g.setColor(playerConfig.color);
        for(Pos pos : occupies){
            g.fillRect((int)Math.round(pos.x*viewState.scale),(int)Math.round(pos.y*viewState.scale),
                    (int)Math.round(viewState.scale),(int)Math.round(viewState.scale));
        }
    }
    public void mUP(){
        dir = Direction.UP;
    }
    public void mLEFT(){
        dir = Direction.LEFT;
    }
    public void mDOWN(){
        dir = Direction.DOWN;
    }
    public void mRIGHT(){
        dir = Direction.RIGHT;
    }
}
