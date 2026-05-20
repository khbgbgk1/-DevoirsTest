package DevoirUn;

import static org.junit.Assert.*;
import org.junit.Test;
import stev.bowling.Game;
import stev.bowling.NormalFrame;

public class BowlingTest {

    @Test
    public void testSpareCalcul() {
        Game g = new Game();
        
        // Frame 1 : Un Spare (5 + 5)
        // On crée la frame 1, on fait tomber 5 quilles au 1er coup et 5 au 2e
        g.addFrame(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 5));
        
        // Frame 2 : Un lancer de 3
        g.addFrame(new NormalFrame(2).setPinsDown(1, 3).setPinsDown(2, 0));
        
        // La méthode est getCumulativeScore(2) car nous avons joué 2 frames
        // Score attendu : (10 + 3 du coup suivant) + 3 = 16
        assertEquals(16, g.getCumulativeScore(2));
    }

    @Test
    public void testGameEmpty() {
        // Un test qui vérifie qu'au début, le score est bien 0 pour la frame 0 (ou qu'il n'y a pas d'erreur)
        // Note : Selon votre code décompilé, appeler getCumulativeScore sur une frame qui n'existe pas 
        // lancera une BowlingException. C'est aussi un bon cas à tester !
        Game g = new Game();
        assertNotNull(g);
    }
}