import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class GameResults {
    List<Map<Color,Integer>> lengthOverTime;
    Map<Color, Integer> totalScores;

    public GameResults(){
        lengthOverTime = new LinkedList<Map<Color,Integer>>();
        totalScores = new HashMap<Color,Integer>();
    }

    public void step(List<Player> pPlayers){
        Map<Color,Integer> tmp = new HashMap<Color,Integer>();
        for (Player p : pPlayers){
            tmp.put(p.playerConfig.color, p.occupies.size());
            totalScores.putIfAbsent(p.playerConfig.color, 0);
            totalScores.put(p.playerConfig.color, p.occupies.size()+totalScores.get(p.playerConfig.color));//TODO fix
        }
        lengthOverTime.add(tmp);

    }

    public int getTotalScore(Color pCol){
        totalScores.putIfAbsent(pCol, 0);
        return totalScores.get(pCol);
    }

    public int computeTotalScore(Color pColor) {
        int tot = 0;
        for (Map<Color,Integer> m : lengthOverTime){
            tot += m.get(pColor);
        }
        return tot;
    }
}
