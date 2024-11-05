import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel {
    List<Player> players;
    ViewState viewState;
    Set<Integer> pressedButtons;

    public GamePanel(List<PlayerConfig> pPlayers){
        players = new LinkedList<Player>();
        viewState = new ViewState(50d);

        List<Pos> startingPositions = new LinkedList<Pos>();
        for(PlayerConfig pc: pPlayers){
            Pos newPos;
            do {
                newPos = new Pos((int) Math.round(Math.random() * 10), (int) Math.round(Math.random() * 10));
            }while(startingPositions.contains(newPos));
            startingPositions.add(newPos);
        }

        for(PlayerConfig pc: pPlayers){
            players.add(new Player(pc,viewState,startingPositions.removeFirst()));
        }

        pressedButtons = new HashSet<Integer>();
        this.setFocusable(true);
    }

    public void start(){
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

        for (Player pl : players){
            pl.paint(g);
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

                    pl.occupies.removeFirst();
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
        if(pPos.x*viewState.scale>getWidth()||pPos.y*viewState.scale>getHeight())return true;
        return false;
    }

    private boolean canBeMovedTo(Pos pPos){
        return !(isOutOfBounds(pPos)||isOccupied(pPos));
    }
}
