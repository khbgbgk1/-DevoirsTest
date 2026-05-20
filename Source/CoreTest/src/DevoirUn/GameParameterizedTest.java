package DevoirUn;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import stev.bowling.Game;
import stev.bowling.LastFrame;
import stev.bowling.NormalFrame;

import java.util.Arrays;
import java.util.Collection;

/**
 * Tests paramétrés pour valider des parties de bowling entières.
 */
@RunWith(Parameterized.class)
public class GameParameterizedTest {

    private int[][] rolls1to9;
    private int[] lastRolls;
    private int expectedScore;
    private String description;

    public GameParameterizedTest(String description, int expectedScore, int[][] rolls1to9, int[] lastRolls) {
        this.description   = description;
        this.expectedScore = expectedScore;
        this.rolls1to9     = rolls1to9;
        this.lastRolls     = lastRolls;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { "Partie parfaite", 
            	300, 
            	new int[][]{{10},{10},{10},{10},{10},{10},{10},{10},{10}}, 
            	new int[]{10,10,10} 
            },
            { "Partie nulle", 0, 
            	new int[][]{{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}}, 
            	new int[]{0,0} 
            },
            { "Toutes reserves", 150, 
            	new int[][]{{5,5},{5,5},{5,5},{5,5},{5,5},{5,5},{5,5},{5,5},{5,5}}, 
            	new int[]{5,5,5} 
            	},
            { "Exemple Devoir", 109, 
            		new int[][]{{3,6},{10},{5,0},{1,9},{10},{0,0},{0,6},{10},{2,8}}, 
            		new int[]{1,9,3} 
            }
        });
    }

    @Test
    public void testScoreFinal() {
        Game g = new Game();
        for (int i = 0; i < 9; i++) {
            NormalFrame f = new NormalFrame(i + 1);
            for (int j = 0; j < rolls1to9[i].length; j++) {
                f.setPinsDown(j + 1, rolls1to9[i][j]);
            }
            g.addFrame(f);
        }
        LastFrame last = new LastFrame(10);
        for (int j = 0; j < lastRolls.length; j++) {
            last.setPinsDown(j + 1, lastRolls[j]);
        }
        g.addFrame(last);

        assertEquals(description, expectedScore, g.getCumulativeScore(10));
    }
}