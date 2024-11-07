import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Player{

    ViewState viewState;
    PlayerConfig playerConfig;
    Direction dir;
    List<Pos> occupies;
    int spawnProtection;

    public Player(PlayerConfig pPlayerConfig, ViewState pViewState, Pos pStart){
        viewState = pViewState;
        playerConfig = pPlayerConfig;
        dir = Direction.NONE;
        spawnProtection = 0;
        occupies = new LinkedList<Pos>();
        occupies.add(pStart);

        System.out.println("new Player at "+pStart);
    }
    public void paint(Graphics g){
        g.setColor(playerConfig.color);
        for(Pos pos : occupies){
            g.fillRect((int)Math.round(pos.x*viewState.scale),(int)Math.round(pos.y*viewState.scale),
                    (int)Math.round(viewState.scale),(int)Math.round(viewState.scale));
        }

        g.setColor(Color.WHITE);
        g.fillRect((int)Math.round((occupies.getLast().x+0.25)* viewState.scale-1),(int)Math.round((occupies.getLast().y+0.25)*viewState.scale-1), (int)Math.round(viewState.scale*0.5), (int)Math.round(viewState.scale*0.5));
        g.setColor(Color.BLACK);
        //g.fillOval((int)Math.round(occupies.getLast().x* viewState.scale),(int)Math.round(occupies.getLast().y*viewState.scale), (int)Math.round(viewState.scale), (int)Math.round(viewState.scale));
        Sprites.paintArrow(g,dir,(int)Math.round((0.5+occupies.getLast().x)* viewState.scale-3),(int)Math.round((0.5+occupies.getLast().y)*viewState.scale+3),(int)Math.round(viewState.scale));

        if(spawnProtection>0){
            g.setColor(Color.BLUE);
            g.drawRect((int)Math.round(-1+(occupies.getLast().x-0.25)* viewState.scale),(int)Math.round(-1+(occupies.getLast().y-0.25)* viewState.scale),(int)Math.round(viewState.scale*1.5),(int)Math.round(viewState.scale*1.5));
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
