package DevoirUn;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import stev.bowling.BowlingException;
import stev.bowling.Game;
import stev.bowling.LastFrame;
import stev.bowling.NormalFrame;

import java.util.Arrays;
import java.util.Collection;

/**
 * Tests JUnit pour vérifier le bon fonctionnement du système de pointage de bowling.
 * Thibault BISAGNI
 */
public class BowlingTest {

    // =========================================================================
    // SECTION 1 : TESTS UNITAIRES STANDARDS (Exécutés une seule fois)
    // =========================================================================
	
	 /**

     * Les "testNormal" sont les cas qui doivent etre passant

     */

    /**
     * Test d'un carreau vide. Soit 0 lancers et 0 quilles abattues.
     */
    @Test
    public void testNormalFrameInitial() {
        NormalFrame f = new NormalFrame(1);
        assertEquals(0, f.countRolls());
        assertEquals(0, f.countPinsDown());
    }

    /**
     * Vérifie le nombre de lancers et de quilles après deux lancers normaux.
     */
    @Test
    public void testNormalFrameDeuxLancers() {
        NormalFrame f = new NormalFrame(1);
        f.setPinsDown(1, 3);
        f.setPinsDown(2, 6);
        assertEquals(2, f.countRolls());
        assertEquals(9, f.countPinsDown());
    }
    
    /**
     * Vérifie que le nombre de quilles renversées est le bon pour un nombre de quilles entre 0 et 10 après 1 lancer.
     */
    @Test
    public void testNormalValeurBonne() {
        NormalFrame f = new NormalFrame(1);
        f.setPinsDown(1, 5);
        assertEquals(1, f.countRolls());
        assertEquals(5, f.countPinsDown());
    }

    /**
     * Un abat compte comme 1 seul lancer (et pas 2) et 10 quilles abattues. 
     */
    @Test
    public void testNormalFrameAbat() {
        NormalFrame f = new NormalFrame(1);
        f.setPinsDown(1, 10);
        assertEquals(1, f.countRolls());
        assertEquals(10, f.countPinsDown());
    }
    
    /**
     * Vérifie spécifiquement la valeur absolue d'un carreau isolé contenant une Reserve.
     * Une Reserve doit intrinsèquement valoir 10 quilles abattues sur son propre carreau, 
     * peu importe la configuration du jeu (avant application des bonus cumulés du Game).
     */
    @Test
    public void testNormalQuillesCarreauReserve() {
        NormalFrame f = new NormalFrame(1);
        f.setPinsDown(1, 4);
        f.setPinsDown(2, 6);
        
        assertEquals("Un carreau de type Reserve doit compter un total de 10 quilles abattues", 10, f.countPinsDown());
        assertEquals("Le nombre de lancers enregistrés sur un Reserve doit être de 2", 2, f.countRolls());
    }

    /**
     * reset() remet le carreau à zéro : 0 lancers et 0 quilles.
     */
    @Test
    public void testNormalFrameReset() {
        NormalFrame f = new NormalFrame(1);
        f.setPinsDown(1, 5);
        f.setPinsDown(2, 3);
        f.reset();
        assertEquals(0, f.countRolls());
        assertEquals(0, f.countPinsDown());
    }

    /**
     * toString() doit retourner exactement 2 caractères pour un NormalFrame.
     */
    @Test
    public void testNormalFrameToStringLongueur() {
        NormalFrame f = new NormalFrame(1);
        f.setPinsDown(1, 3);
        f.setPinsDown(2, 6);
        assertEquals(2, f.toString().length());
    }

    /**
     * toString() d'un abat doit retourner "X ".
     */
    @Test
    public void testNormalFrameToStringAbat() {
        NormalFrame f = new NormalFrame(1);
        f.setPinsDown(1, 10);
        assertEquals("X ", f.toString());
    }

    /**
     * toString() d'une réserve doit contenir "/".
     */
    @Test
    public void testNormalFrameToStringReserve() {
        NormalFrame f = new NormalFrame(1);
        f.setPinsDown(1, 4);
        f.setPinsDown(2, 6);
        assertEquals("4/", f.toString());
    }

    /**
     * toString() d'un dalot doit utiliser "-".
     */
    @Test
    public void testNormalFrameToStringDalot() {
        NormalFrame f = new NormalFrame(1);
        f.setPinsDown(1, 0);
        f.setPinsDown(2, 0);
        assertEquals("--", f.toString());
    }
    
    /**

     * Les "testException" sont les cas qui doivent renvoyer une erreur

     */
    

    /**
     * Une valeur négative doit lancer une Exception.
     */
    @Test(expected = BowlingException.class)
    public void testExpectionValeurNegative() {
        new NormalFrame(1).setPinsDown(1, -1);
    }

    /**
     * Plus de 10 quilles en un lancer doit lancer une Exception.
     */
    @Test(expected = BowlingException.class)
    public void testExpectionTropDeQuilles() {
        new NormalFrame(1).setPinsDown(1, 11);
    }

    /**
     * Un total de quilles supérieur à 10 sur deux lancers doit lancer une Exception.
     */
    @Test(expected = BowlingException.class)
    public void testExceptionTotalDepasseDix() {
        NormalFrame f = new NormalFrame(1);
        f.setPinsDown(1, 7);
        f.setPinsDown(2, 5);
    }

    /**
     * Enregistrer le lancer 2 avant le lancer 1 doit lancer une Exception.
     */
    @Test(expected = BowlingException.class)
    public void testExceptionOrdreInvalide() {
        new NormalFrame(1).setPinsDown(2, 3);
    }

    /**
     * Un deuxième lancer après un abat doit lancer une Exception.
     */
    @Test(expected = BowlingException.class)
    public void testExceptionDeuxiemeLancerApresAbat() {
        NormalFrame f = new NormalFrame(1);
        f.setPinsDown(1, 10);
        f.setPinsDown(2, 3);
    }

    /**
     * Une exception ne doit pas modifier l'état du carreau.
     */
    @Test
    public void testExceptionEtatInchange() {
        NormalFrame f = new NormalFrame(1);
        f.setPinsDown(1, 5);
        try {
            f.setPinsDown(2, 8);
        } catch (BowlingException e) {
            // normal
        }
        assertEquals(1, f.countRolls());
        assertEquals(5, f.countPinsDown());
    }
    
    
    /**

     * Les "testDernierCarreau" sont les cas specifique au dernier carreau

     */

    /**
     * Un abat au carreau 10 autorise 3 lancers au total.
     */
    @Test
    public void testDernierCarreauUnAbat() {
        LastFrame f = new LastFrame(10);
        f.setPinsDown(1, 10);
        f.setPinsDown(2, 5);
        f.setPinsDown(3, 3);
        assertEquals(3, f.countRolls());
    }

    /**
     * Une réserve au carreau 10 autorise un troisième lancer.
     */
    @Test
    public void testDernierCarreauReserve() {
        LastFrame f = new LastFrame(10);
        f.setPinsDown(1, 7);
        f.setPinsDown(2, 3);
        f.setPinsDown(3, 6);
        assertEquals(3, f.countRolls());
    }

    /**
     * Un carreau ouvert au carreau 10 limite à 2 lancers.
     */
    @Test
    public void testDernierCarreauOuvert() {
        LastFrame f = new LastFrame(10);
        f.setPinsDown(1, 3);
        f.setPinsDown(2, 5);
        assertEquals(2, f.countRolls());
    }

    /**
     * toString() du carreau 10 doit retourner exactement 3 caractères. On suppose qu'il doit toujours prevoir 3 caractères.
     */
    @Test
    public void testDernierCarreauToStringLongueur() {
        LastFrame f = new LastFrame(10);
        f.setPinsDown(1, 3);
        f.setPinsDown(2, 5);
        assertEquals(3, f.toString().length());
    }

    /**
     * toString() de trois abats consécutifs au carreau 10 doit retourner "XXX".
     */
    @Test
    public void testDernierCarreauToStringTroisAbats() {
        LastFrame f = new LastFrame(10);
        f.setPinsDown(1, 10);
        f.setPinsDown(2, 10);
        f.setPinsDown(3, 10);
        assertEquals("XXX", f.toString());
    }

    /**
     * Un troisième lancer est interdit si le carreau 10 est ouvert.
     */
    @Test(expected = BowlingException.class)
    public void testDernierCarreauExceptionTroisLancersSansBonus() {
        LastFrame f = new LastFrame(10);
        f.setPinsDown(1, 3);
        f.setPinsDown(2, 5);
        f.setPinsDown(3, 2);
    }

    /**
     * Un quatrième lancer est toujours interdit au carreau 10.
     */
    @Test(expected = BowlingException.class)
    public void testDernierCarreauExceptionQuatreLancers() {
        LastFrame f = new LastFrame(10);
        f.setPinsDown(1, 10);
        f.setPinsDown(2, 10);
        f.setPinsDown(3, 10);
        f.setPinsDown(4, 5);
    }
    

    /**

     * Les "testScores" sont les tests qui verifient le cacul des points

     */

    /**
     * Le score cumulatif d'un carreau ouvert est égal à la somme des deux lancers.
     */
    @Test
    public void testScoresCarreauOuvert() {
        Game g = new Game();
        g.addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 6));
        assertEquals(9, g.getCumulativeScore(1));
    }

    /**
     * Le score d'une réserve est 10 plus le prochain lancer.
     */
    @Test
    public void testScoresReserve() {
        Game g = new Game();
        g.addFrame(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 5));
        g.addFrame(new NormalFrame(2).setPinsDown(1, 3).setPinsDown(2, 0));
        assertEquals(16, g.getCumulativeScore(2));
    }

    /**
     * Le score d'un abat est 10 plus les deux prochains lancers.
     */
    @Test
    public void testScoresAbat() {
        Game g = new Game();
        g.addFrame(new NormalFrame(1).setPinsDown(1, 10));
        g.addFrame(new NormalFrame(2).setPinsDown(1, 5).setPinsDown(2, 0));
        assertEquals(20, g.getCumulativeScore(2));
    }

    /**
     * Demander le score d'un carreau non ajouté doit lancer une BowlingException.
     */
    @Test(expected = BowlingException.class)
    public void testScoresExceptionCarreauInexistant() {
        Game g = new Game();
        g.addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 2));
        g.getCumulativeScore(5);
    }
    
    /**
     * Vérifie le score si on fait un carreau nul (0, 0). Le score doit être de 0.
     */
    @Test
    public void testScoresDoubleZero() {
        Game g = new Game();
        g.addFrame(new NormalFrame(1).setPinsDown(1, 0).setPinsDown(2, 0));
        assertEquals(0, g.getCumulativeScore(1));
    }

    /**
     * Vérifie le score d'une réserve suivi d'un carreau nul (0, 0).
     * Le premier carreau doit valoir 10 (la Reserve) + 0 (prochain lancer) = 10.
     */
    @Test
    public void testScoresReserveSuiviDeZeroZero() {
        Game g = new Game();
        g.addFrame(new NormalFrame(1).setPinsDown(1, 7).setPinsDown(2, 3));
        g.addFrame(new NormalFrame(2).setPinsDown(1, 0).setPinsDown(2, 0));
        
        assertEquals(10, g.getCumulativeScore(1));
        assertEquals(10, g.getCumulativeScore(2));
    }

    /**
     * Vérifie le score d'un abat suivi d'un carreau nul (0, 0).
     * Le premier carreau doit valoir 10 (l'Abat) + 0 + 0 (les deux prochains lancers) = 10.
     */
    @Test
    public void testScoresAbatSuiviDeZeroZero() {
        Game g = new Game();
        g.addFrame(new NormalFrame(1).setPinsDown(1, 10)); 
        g.addFrame(new NormalFrame(2).setPinsDown(1, 0).setPinsDown(2, 0));
        
        assertEquals(10, g.getCumulativeScore(1));
        assertEquals(10, g.getCumulativeScore(2));
    }

    /**
     * Vérifie le score de deux Abats consécutifs suivis d'un carreau ouvert (2, 2).
     * - Carreau 1 (Abat 1) : 10 + 10 (Abat 2) + 2 (Lancer 1 du C3) = 22.
     * - Carreau 2 (Abat 2) : 10 + 2 + 2 (Lancers du C3) = 14. Cumulé = 22 + 14 = 36.
     * - Carreau 3 (Ouvert) : 2 + 2 = 4. Cumulé final = 36 + 4 = 40.
     */
    @Test
    public void testScoresDoubleAbbatSuiviDeDeuxDeux() {
        Game g = new Game();
        g.addFrame(new NormalFrame(1).setPinsDown(1, 10));
        g.addFrame(new NormalFrame(2).setPinsDown(1, 10)); 
        g.addFrame(new NormalFrame(3).setPinsDown(1, 2).setPinsDown(2, 2));
        
        assertEquals(22, g.getCumulativeScore(1));
        assertEquals(36, g.getCumulativeScore(2));
        assertEquals(40, g.getCumulativeScore(3));
    }
    
    /**
     * Vérifie le score cumulé d'une Réserve suivi d'un Abbat, puis d'un carreau ouvert (2, 2).
     * - Carreau 1 (Réserve) : 10 + 10 (Bonus de l'Abbat) = 20.
     * - Carreau 2 (Abbat) : 10 + 2 + 2 (Bonus du C3) = 14. Cumulé = 20 + 14 = 34.
     * - Carreau 3 (Ouvert) : 2 + 2 = 4. Cumulé final = 34 + 4 = 38.
     */
    @Test
    public void testScoresReserveSuiviAbatSuiviOuvert() {
        Game g = new Game();
        g.addFrame(new NormalFrame(1).setPinsDown(1, 4).setPinsDown(2, 6));
        g.addFrame(new NormalFrame(2).setPinsDown(1, 10));
        g.addFrame(new NormalFrame(3).setPinsDown(1, 2).setPinsDown(2, 2));
        
        assertEquals(20, g.getCumulativeScore(1));
        assertEquals(34, g.getCumulativeScore(2));
        assertEquals(38, g.getCumulativeScore(3));
    }
    
    /**
     * Vérifie le score cumulé d'une Réserve suivi d'un carreau ouvert (2, 2).
     * - Carreau 1 (Réserve) : 10 + 2 = 12.
     * - Carreau 2 (Ouvert) : 2 + 2 = 4. Cumulé final = 12 + 4 = 16.
     */
    @Test
    public void testScoresReserveSuiviOuvert() {
        Game g = new Game();
        g.addFrame(new NormalFrame(1).setPinsDown(1, 4).setPinsDown(2, 6));
        g.addFrame(new NormalFrame(2).setPinsDown(1, 2).setPinsDown(2, 2));
        
        assertEquals(12, g.getCumulativeScore(1));
        assertEquals(16, g.getCumulativeScore(2));
    }
    
    /**
     * Vérifie que le score cumulatif d'un Abat n'est pas disponible (lève une exception) 
     * tant que ses deux lancers de bonus nécessaires ne sont pas effectués.
     */
    @Test
    public void testScoresBloqueTantQueLancersBonusAbatManquants() {
        Game g = new Game();
        
        
        g.addFrame(new NormalFrame(1).setPinsDown(1, 10));
        
        //Aucun lancer après l'Abat -> Score indisponible
        try {
            g.getCumulativeScore(1);
            fail("Un Abat sans aucun lancer subséquent ne devrait pas pouvoir calculer son score.");
        } catch (BowlingException e) {
            // Succès : L'exception est attendue
        }
        
        //Un seul lancer après l'Abat -> Score toujours indisponible
        NormalFrame c2 = new NormalFrame(2);
        c2.setPinsDown(1, 5);
        g.addFrame(c2);
        
        try {
            g.getCumulativeScore(1);
            fail("Un Abat avec un seul lancer subséquent ne devrait pas pouvoir calculer son score.");
        } catch (BowlingException e) {
            // Succès : L'exception est attendue
        }
        
        //Deux lancers après l'Abat -> Le score doit enfin se débloquer
        c2.setPinsDown(2, 3);
        
        assertEquals("L'Abat devrait être calculable après 2 lancers de bonus (10 + 5 + 3 = 18)", 18, g.getCumulativeScore(1));
    }

    /**
     * Vérifie que le score cumulatif d'une Reserve n'est pas disponible (lève une exception) 
     * tant que son unique lancer de bonus nécessaire n'est pas effectué.
     */
    @Test
    public void testScoresBloqueTantQueLancerBonusReserveManquant() {
        Game g = new Game();
        g.addFrame(new NormalFrame(1).setPinsDown(1, 7).setPinsDown(2, 3));
        
        // Aucun lancer après la Reserve -> Score indisponible
        try {
            g.getCumulativeScore(1);
            fail("Une Reserve sans aucun lancer subséquent ne devrait pas pouvoir calculer son score.");
        } catch (BowlingException e) {
            // Succès : L'exception est attendue
        }
        
        //On ajoute le premier lancer du carreau suivant -> Le score doit se débloquer
        NormalFrame c2 = new NormalFrame(2);
        c2.setPinsDown(1, 4);
        g.addFrame(c2);
        
        assertEquals(14, g.getCumulativeScore(1));
    }


    // =========================================================================
    // Exploration des tests avec Data dans une autre classe (je sais qu'il faut rendre un seul document mais l'autre document est surtout pour exploration)
    // =========================================================================

}