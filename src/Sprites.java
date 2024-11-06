import java.awt.*;

public class Sprites {
    public static void paintChar(Graphics g, char pChar, int pX, int pY){
        g.drawRect(pX,pY,20,20);
        g.drawString(pChar+"",pX+5,pY+15);
    }

    public static void paintChars(Graphics g, char[] pChars, int pX, int pY){
        for (int i = 0; i < pChars.length; i++) {
            paintChar(g,pChars[i],pX+20*i,pY);
        }
    }

    public static void paintArrow(Graphics g, Direction pDir, int pX, int pY, int pScale){
        switch (pDir){
            case UP -> g.drawString("/\\",pX,pY);
            case LEFT -> g.drawString("<",pX,pY);
            case DOWN -> g.drawString("\\/",pX,pY);
            case RIGHT -> g.drawString(">",pX,pY);
            case NONE -> g.drawString("o",pX,pY);
        }
    }
}
