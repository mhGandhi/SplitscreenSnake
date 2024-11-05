import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Stack;

public class SetupDialog extends JDialog {
    final int maxPlayers;
    List<PlayerConfig> playerConfigs;
    PlayerConfig currentPlayer;
    List<Color> colors;
    JPanel statusDisplay;
    JButton okButton;
    public SetupDialog(List<PlayerConfig> pPls, JFrame frame, String title, boolean modal){
        super(frame,title,modal);
        this.playerConfigs = pPls;
        colors = new Stack<Color>();
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        colors.add(Color.ORANGE);

        maxPlayers = colors.size();
        this.setFocusable(true);
        this.addKeyListener(new InputListener());

        statusDisplay = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                //this.setSize(this.getParent().getWidth(),20*(playerConfigs.size()+2));
                if(!colors.isEmpty()) {
                    int remColWidth = this.getWidth() / colors.size();
                    for (int i = 0; i < colors.size(); i++) {
                        g.setColor(colors.get(i));
                        g.fillRect(remColWidth * i, 0, remColWidth, 20);
                    }
                }else{
                    g.setColor(Color.GRAY);
                    g.fillRect(0,0,this.getWidth(),20);
                }

                for (int i = 0; i < playerConfigs.size(); i++) {
                    PlayerConfig pc = playerConfigs.get(i);
                    g.setColor(pc.color);//TODO loop
                    char[] inputChars = {
                            pc.keyUp.getKeyChar(),pc.keyLeft.getKeyChar(),
                            pc.keyDown.getKeyChar(), pc.keyRight.getKeyChar()
                    };
                        paintChars(g,inputChars,21*inputChars.length*(i%2),(i+1-i%2)*21);
                }
            }
            private void paintChar(Graphics g, char pChar, int pX, int pY){
                g.drawRect(pX,pY,20,20);
                g.drawString(pChar+"",pX+5,pY+15);
            }

            private void paintChars(Graphics g, char[] pChars, int pX, int pY){
                for (int i = 0; i < pChars.length; i++) {
                    paintChar(g,pChars[i],pX+20*i,pY);
                }
            }
        };
        this.add(statusDisplay);

        okButton = new JButton("OK");
        okButton.addActionListener(e -> this.dispose());
        this.add(okButton, "South");
    }

    private class InputListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            //System.out.println("["+e.getKeyCode()+"-"+e.getKeyChar()+"]");
            //System.out.println("\t["+e.getKeyCode()+" "+e.getKeyChar()+"]");
            switch (e.getKeyChar()){
                case '\n':
                    break;
                default:
                    addKey(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private void addKey(KeyEvent pE){
        if(colors.isEmpty()&&currentPlayer==null)return;

        if(currentPlayer == null){
            System.out.println("Player "+(maxPlayers-colors.size()+1)+"\\"+maxPlayers+" "+colors.getFirst().toString());
            currentPlayer = new PlayerConfig(colors.removeFirst());
        }

        //System.out.println("["+pE.getKeyCode()+"-"+pE.getKeyChar()+"]");
        if(currentPlayer.keyUp==null){
            currentPlayer.keyUp = pE;
        } else if (currentPlayer.keyLeft==null) {
            currentPlayer.keyLeft = pE;
        } else if (currentPlayer.keyDown==null) {
            currentPlayer.keyDown = pE;
        } else {
            currentPlayer.keyRight = pE;
        }

        if(currentPlayer.isComplete()){
            playerConfigs.add(currentPlayer.copy());
            System.out.println(currentPlayer);
            currentPlayer = null;
        }
        statusDisplay.repaint();
        //this.setSize(getWidth(),statusDisplay.getHeight()+okButton.getHeight());
    }
}
