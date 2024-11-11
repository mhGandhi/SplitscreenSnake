import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class GameResults {
    List<Map<Color,Integer>> lengthOverTime;

    public GameResults(){
        lengthOverTime = new LinkedList<Map<Color,Integer>>();
    }

    public void step(List<Player> pPlayers){
        Map<Color,Integer> tmp = new HashMap<Color,Integer>();
        for (Player p : pPlayers){
            tmp.put(p.playerConfig.color, p.occupies.size());
        }
        lengthOverTime.add(tmp);
    }

    public int getTotalScore(Color pColor) {
        int tot = 0;
        for (Map<Color,Integer> m : lengthOverTime){
            tot += m.get(pColor);
        }
        return tot;
    }
}
