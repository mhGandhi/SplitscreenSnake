import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1000,1000);
        frame.setTitle("snek");

        List<PlayerConfig> players = new LinkedList<PlayerConfig>();

        setUpPlayers(players, frame);
        for(PlayerConfig pc : players){
            System.out.println(pc);
        }

        GamePanel gp = new GamePanel(players);

        frame.add(gp);
        frame.setVisible(true);

        gp.start();
    }

    private static void setUpPlayers(List<PlayerConfig> players, JFrame frame) {
        JDialog dialog = new SetupDialog(players ,frame, "Enter Keystrokes", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(250, 150);;
        dialog.setLocationRelativeTo(frame);

        dialog.setVisible(true);
    }//TODO pause button, boost, pretty arrows, streamlined spawn protection, graph auswertung, rule cross own snake, pretty snake, bug pressing against wall, between frames timing
}