import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel {
    List<Player> players;
    List<Pos> apples;
    ViewState viewState;
    Set<Integer> pressedButtons;

    public GamePanel(List<PlayerConfig> pPlayers){
        players = new LinkedList<Player>();
        apples = new LinkedList<Pos>();
        viewState = new ViewState(50d);

        for(PlayerConfig pc: pPlayers){
            players.add(new Player(pc,viewState,new Pos(0,-1)));
        }

        pressedButtons = new HashSet<Integer>();
        this.setFocusable(true);
    }

    public void start(){
        System.out.println(new Pos(0,0)+" to "+ getLowerRightCorner());
        for(Player p: players){
            p.occupies.clear();
            p.occupies.add(getFreeSpot());
        }
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                pressedButtons.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                pressedButtons.remove(e.getKeyCode());
            }
        });

        boolean running = true;
        long tickcounter = 0;
        while(running){
            if(pressedButtons.contains(Integer.valueOf(32)))running = false;
            tickcounter++;
            tick(tickcounter);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("END");
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.drawRect(0,0,(int)Math.round((1+getLowerRightCorner().x)* viewState.scale),(int)Math.round((1+getLowerRightCorner().y)* viewState.scale));

        for (Player pl : players){
            pl.paint(g);
        }

        for(Pos ap :apples){
            g.setColor(Color.RED);
            g.fillOval((int)Math.round(ap.x*viewState.scale),(int)Math.round(ap.y* viewState.scale), (int)Math.round(viewState.scale), (int)Math.round(viewState.scale));
        }
    }

    public void tick(long tnum){
        /*
        System.out.print("Pressed: ");
        for (Integer key : pressedButtons){
            System.out.print(key+" ");
        }
        System.out.println();
        */

        for (Player pl : players){
            if(pressedButtons.contains(pl.playerConfig.keyUp.getKeyCode())){
                pl.mUP();
            }
            if(pressedButtons.contains(pl.playerConfig.keyLeft.getKeyCode())){
                pl.mLEFT();
            }
            if(pressedButtons.contains(pl.playerConfig.keyDown.getKeyCode())){
                pl.mDOWN();
            }
            if(pressedButtons.contains(pl.playerConfig.keyRight.getKeyCode())){
                pl.mRIGHT();
            }
        }
        if(apples.size()<3&&tnum%2000==0){
            apples.add(getFreeSpot());
        }
        if(tnum%500==0){
            for (Player pl : players){
                Pos offset = new Pos(0,0);
                switch (pl.dir){
                    case UP -> offset.y--;
                    case LEFT -> offset.x--;
                    case DOWN -> offset.y++;
                    case RIGHT -> offset.x++;
                }
                if(canBeMovedTo(pl.occupies.getLast().added(offset))){
                    pl.occupies.add(pl.occupies.getLast().added(offset));

                    if(hasApple(pl.occupies.getLast())){
                        apples.remove(pl.occupies.getLast());
                    }else{
                        pl.occupies.removeFirst();
                    }

                }
            }
            repaint();
        }
    }

    private boolean isOccupied(Pos pPos){
        for(Player pl : players){
            if(pl.occupies.contains(pPos))return true;
        }
        return false;
    }

    private boolean isOutOfBounds(Pos pPos){
        if(pPos.x<0||pPos.y<0)return true;
        if(pPos.x> getLowerRightCorner().x||pPos.y>getLowerRightCorner().y)return true;
        return false;
    }

    private boolean hasApple(Pos pPos){
        return apples.contains(pPos);
    }

    private boolean canBeMovedTo(Pos pPos){
        return !(isOutOfBounds(pPos)||isOccupied(pPos));
    }

    private Pos getLowerRightCorner(){
        //System.out.println("pos "+getWidth()/ viewState.scale+" "+getHeight()/ viewState.scale);
        Pos p = new Pos(getWidth()/ viewState.scale-1, getHeight()/ viewState.scale-1);
        //System.out.println(p);
        return p;
    }

    private Pos getFreeSpot(){
        Pos ret = new Pos(-1,-1);

        int attempts = 0;
        do{
            //System.out.println("oldcord "+ ret.x+" "+ret.y);
            ret.x = (int)Math.round(Math.random()*getLowerRightCorner().x);
            ret.y = (int)Math.round(Math.random()*getLowerRightCorner().y);

            if(attempts>1000){
                ret.x = 0;
                ret.y = -1;
                System.err.println("Couldn't find free spot in "+attempts+" attempts");
                break;
            }
            attempts++;
        }while(!canBeMovedTo(ret)||apples.contains(ret));


        return ret;
    }
}
