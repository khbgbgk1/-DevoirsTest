package DevoirDeux;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import stev.kwikemart.*;
/**
 * Tests JUnit pour vérifier le bon fonctionnement du système de register.
 * Thibault BISAGNI
 * Lien Github : https://github.com/khbgbgk1/-DevoirsTest/tree/main
 */

/**
 * ============================================================================
 * CLASSES D'ÉQUIVALENCE DÉTERMINÉES (Technique PCE & AVL)
 * ============================================================================
 * 
 * Classe 1 : La Liste LI
 * 
 * LI_V1  : Liste d'achats contenant entre 1 et 10 (inclus) entrées [Heuristique : Intervalle]
 * LI_V2  : Pas de ligne coupons si aucun item dont le CUP  commence par  5 [Heuristique : Spécifique]
 * LI_I1  : Liste d'achats vide (Invalide -> EmptyGroceryListException) [Heuristique : Unique]
 * LI_I2  : Liste d'achats contenant strictement plus de 10 entrées (Invalide -> TooManyItemsException) [Heuristique : Intervalle]
 * LI_I3  : Liste d'achats contenant 2 items avec le meme CUP sans annulation (Invalide -> DuplicateItemException ) [Heuristique : Spécifique]
 * 
 * 
 * Classe 2 : Le Prix PI
 * 
 * PI_V1  : Item avec un prix unitaire supérieur ou égal à 0$ et inférieur ou égal à 35$ [Heuristique : Intervalle]
 * PI_V2  : Item précédé par un @ si produit avec un CUP commencant par un 2 [Heuristique : Spécifique]
 * PI_I1  : Item avec un prix unitaire strictement supérieur à 35$ (Invalide -> AmountTooLargeException) [Heuristique : Intervalle]
 * PI_I2  : Item avec un prix unitaire strictement négatif (Invalide -> NegativeAmountException)
 * (PI_I3  : Item avec un prix dont la valeur n'est pas un nombre valide (Invalide -> )[Heuristique : Spécifique])
 * 
 * 
 * Classe 3 : Le CUP CU
 * 
 * CU_V1  : Item avec quantité fractionnaire dont le code CUP commence par '2' [Heuristique : Groupe]
 * CU_V2  : Composé de 12 chiifres dont le 12 ème est une clé de controle valide [Heuristique : Spécifique]
 * CU_V3  : Commence par 5 si un coupon  [Heuristique : Groupe]
 * CU_I1  : Item avec quantité fractionnaire dont le code CUP ne commence pas par '2' (Invalide -> InvalidQuantityForCategoryException) [Heuristique : Spécifique]
 * CU_I2  : Composé de strictement moins de 12 chiifres (Invalide -> InvalidUpcException.UpcTooShortExceptio ) [Heuristique : Intervalle]
 * CU_I3  : Composé de strictement plus de 12 chiifres (Invalide ->  InvalidUpcException.UpcTooLongException) [Heuristique : Intervalle]
 * CU_I4  : Composé de strictement 12 chifres avec une clé de controle invalide (Invalide -> InvalidCheckDigitException ) [Heuristique : Spécifique]
 * 
 * 
 *Classe 4 : La Quantité QU
 * 
 * QU_V1  : Item avec quantité strictement supérieur à 0 [Heuristique : Intervalle]
 * QU_V2  : Item avec quantité négative servant à annuler un item identique déjà présent auparavant avec une valeur inférieur ou égal [Heuristique : Spécifique]
 * QU_V3  : Item avec quantité Fractionaire si CUP commence par un 2 [Heuristique : Groupe]
 * QU_I1  : Saisie d'une quantité négative sans qu'aucun item correspondant n'ait été scanné avant (Invalide -> NoSuchItemException) [Heuristique : Spécifique]
 * QU_I2  : Saisie d'une quantité négative superieur au premier produit (Invalide -> CouponException.InvalidCouponQuantityException) [Heuristique : Intervalle - Spécifique]
 * QU_I3  : Saisie d'un prix négative superieur au premier produit (Invalide -> CouponException.InvalidCouponQuantityException) [Heuristique : Intervalle - Spécifique]
 * (QU_I4  : Autre valeur qu'un nombre (Invalide ->) [Heuristique : Spécifique])
 * 
 * 
 * Classe 5 : Le Rabais RA
 * 
 * RA_V1  : Facture avec au moins 5 items distincts ET sous-total >= 2$ (Rabais de 1$ appliqué) [Heuristique : Spécifique]
 * RA_V2  : Facture avec moins de 5 items distincts (Aucun rabais appliqué) [Heuristique : Spécifique]
 * RA_V3  : Facture avec un sous-total < 2$ (Aucun rabais appliqué) [Heuristique : Spécifique]
 * 
 * 
 * Classe 6 : Le Coupon CO
 * 
 * CO_V1  : Valeur du coupon > 0 [Heuristique : Intervalle]
 * CO_V2  : Valeur du coupon est un nombre decimal [Heuristique : Groupe]
 * CO_I1  : Valeur du coupon >= total des achats, il n'est pas traité [Heuristique : Spécifique]
 * CO_I2  : Valeur du coupon < 0[Heuristique : Intervalle]
 * CO_I3  : Valeur du coupon == 0 (on suppose que "possitif" exclus 0) [Heuristique : Unique] 
 * (CO_I4  : Valeur du coupon autre qu'un nombre [Heuristique : Spécifique])
 * ============================================================================
 * STRATÉGIE DE COMBINAISON DES CLASSES
 * ============================================================================
 * - Pour les classes VALIDES : Nous maximisons en regroupant le plus grand nombre
 *    de cas passant.
 * - Pour les classes INVALIDES :Pour respecter l'hypothèse d'un seul défault par test, chaque 
 *   test d'erreur isole un seul cas non invalide.
 */


public class RegisterTest {

	private static Register register;
	
	@BeforeClass
    public static void setUpClass() {
        register = Register.getRegister();
     // allocation du grand rouleau
        register.changePaper(PaperRoll.LARGE_ROLL);
    }
	
	
	/**
     * T01_Combinaison_Valide_Maximale
     * * @Cibles LI_V1, PI_V1, PI_V2, CU_V1, CU_V2, CU_V3, QU_V1, QU_V2, QU_V3, RA_V1, CO_V1, CO_V2
     */
	@Test
    public void testCombinaisonValideMaximale() {
        List<Item> grocery = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        
        // Variables pour calculer dynamiquement les montants attendus dans le test
        double expectedSubTotal = 0.0;
        int distinctItemsCount = 5;
        
        // Génération des prix unitaires aléatoires (Bornés entre 0.50$ et 5.00$ -> PI_V1)
        double priceBananas = 0.50 + (4.50 * random.nextDouble());
        double priceChewingGum = 0.50 + (4.50 * random.nextDouble());
        double priceGobstoppers = 0.50 + (4.50 * random.nextDouble());
        double priceNerds = 0.50 + (4.50 * random.nextDouble());
        double priceBeef = 5.75; // Prix unitaire fixe pour le bœuf pour correspondre à l'énoncé
        
        // Ajout des items de base et calcul du sous-total théorique
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 2, priceBananas));
        expectedSubTotal += 2 * priceBananas;
        
        double randomWeight = 0.10 + (0.90 * random.nextDouble()); // Quantité fractionnaire
        grocery.add(new Item(Upc.generateCode("22804918500"), "Beef", randomWeight, priceBeef)); 
        expectedSubTotal += randomWeight * priceBeef;
        
        // Annulation d'une banane
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", -1, priceBananas)); 
        expectedSubTotal += (-1) * priceBananas;
        
        grocery.add(new Item(Upc.generateCode("64748119599"), "Chewing gum", 1, priceChewingGum));
        expectedSubTotal += 1 * priceChewingGum;
        
        grocery.add(new Item(Upc.generateCode("44348225996"), "Gobstoppers", 1, priceGobstoppers));
        expectedSubTotal += 1 * priceGobstoppers;
        
        grocery.add(new Item(Upc.generateCode("34323432343"), "Nerds", 1, priceNerds));
        expectedSubTotal += 1 * priceNerds;
        
        int extraItemsCount = random.nextInt(3); // 0, 1 ou 2 items de plus
        for (int i = 0; i < extraItemsCount; i++) {
            String upcBase = "6151931415" + i;
            double extraPrice = 0.50 + (2.00 * random.nextDouble());
            grocery.add(new Item(Upc.generateCode(upcBase), "Extra Candy " + i, 1, extraPrice));
            
            expectedSubTotal += 1 * extraPrice;
            distinctItemsCount++; 
        }
        
        //Calcul de la taxe de Springfield (5%) sur le sous-total courant
        double expectedTax = expectedSubTotal * 0.05;
        double expectedTotalBeforeDiscounts = expectedSubTotal + expectedTax;
        
        //Ajout du coupon
        double randomCouponValue = 0.10 + (0.30 * random.nextDouble());
        grocery.add(new Item(Upc.generateCode("54323432343"), "Coupon Rabais", 1, randomCouponValue));
        
        double expectedTotal = expectedTotalBeforeDiscounts;
        if (expectedTotal >= randomCouponValue) {
            expectedTotal -= randomCouponValue;
        }
        
        // Logique du rabais
        if (distinctItemsCount >= 5 && expectedSubTotal >= 2.00) {
            expectedTotal -= 1.00;
        }
        
        //Demande d'impression à la caisse
        String receipt = register.print(grocery);
        assertNotNull(receipt);
        
        
        assertTrue("Le rabais de 1$ aurait dû s'appliquer", receipt.contains("Rebate for 5 items")); 
        
        // VERIFICATION DU SYMBOLE '@'
        assertTrue("Le reçu doit contenir le symbole '@' pour les articles au poids", 
                receipt.contains("@"));
        
        //!!! Non valide dans le programme !!!
        //ASSERTIONS DE VALEURS
        String subTotalStr = String.format(java.util.Locale.FRANCE, "%.2f$", expectedSubTotal);
        String totalStr = String.format(java.util.Locale.FRANCE, "%.2f$", expectedTotal);

        // Assertion pour le sous-total avec message d'erreur détaillé
        String messageErreurSubTotal = "Sous-total attendu : " + subTotalStr + ". Mais le reçu contient : \n" + receipt;
        assertTrue(messageErreurSubTotal, receipt.contains(subTotalStr));

        // Assertion pour le total avec message d'erreur détaillé
        String messageErreurTotal = "Total attendu : " + totalStr + ". Mais le reçu contient : \n" + receipt;
        assertTrue(messageErreurTotal, receipt.contains(totalStr));
        
    }
	
	/**
     * T02_Valide_Pas_De_Rabais_Et_Pas_De_Coupon
     * * @Cibles RA_V2, LI_V2
     */
	@Test
    public void testVerificationAbsenceRabais_MoinsDe5Items() {
		List<Item> grocery = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        
        // Randomisation : 1 à 4 items seulement
        int itemCount = 1 + random.nextInt(4); 
        
        for (int i = 0; i < itemCount; i++) {
            String base = String.format("1111111111%d", i);
            double price = 1.00 + (4.00 * random.nextDouble());
            grocery.add(new Item(Upc.generateCode(base), "Item " + i, 1, price));
        }
        
        String receipt = register.print(grocery);
        
        assertFalse("Le rabais ne doit pas être appliqué car il y a < 5 items.", 
                    receipt.contains("Rebate"));
        assertFalse("Le coupon ne doit pas être affiché.", 
                    receipt.contains("Coupon"));
    }
	
	/**
     * T03_Valide_Pas_De_Rabais_Et_Pas_De_Coupon
     * * @Cibles RA_V3
     */
	@Test
    public void testVerificationAbsenceRabais_SousTotalFaible() {
		List<Item> grocery = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        
        // 5 items pour respecter la condition du rabais, mais prix très bas
        for (int i = 0; i < 5; i++) {
            String base = String.format("2222222222%d", i);
            // Prix entre 0.10$ et 0.30$ -> 5 * max 0.30 = 1.50$ (soit < 2.00$)
            double lowPrice = 0.10 + (0.20 * random.nextDouble());
            grocery.add(new Item(Upc.generateCode(base), "Item " + i, 1, lowPrice));
        }
        
        String receipt = register.print(grocery);
        
        assertFalse("RA_V3 : Le rabais ne doit pas être appliqué car le sous-total est < 2.00$.", 
                    receipt.contains("Rebate"));
    }
    
    /**
     * T04_Liste_Vide
     * * @Cibles LI_I1
     */
    @Test(expected = RegisterException.EmptyGroceryListException.class)
    public void testListeVide() {
        List<Item> grocery = new ArrayList<>();
        register.print(grocery);
    }

    /**
     * T05_Trop_D_Entrees_Dans_La_Liste
     * * @Cibles LI_I2 (Valeur limite : 11 entrées)
     */
    @Test(expected = RegisterException.TooManyItemsException.class)
    public void testTropDEntreesDansLaListe() {
        List<Item> grocery = new ArrayList<>();
        for (int i = 0; i < 11; i++) { //On ne rendomise pas pour economiser du papier
            String base = String.format("1000000000%d", i);
            grocery.add(new Item(Upc.generateCode(base), "Item " + i, 1, 0.50));
        }
        register.print(grocery);
    }

    /**
     * T06_Doublon_CUP_Sans_Annulation
     * * @Cibles LI_I3
     */
    @Test(expected = Register.DuplicateItemException.class)
    public void testDoublonCupSansAnnulation() {
        List<Item> grocery = new ArrayList<>();
        String upcShared = Upc.generateCode("12345678901");
        grocery.add(new Item(upcShared, "Buzz Cola", 1, 1.99));
        grocery.add(new Item(upcShared, "Buzz Cola", 1, 1.99));
        register.print(grocery);
    }

    /**
     * T07_Prix_Trop_Eleve
     * * @Cibles PI_I1 (Valeur limite : 35.01$)
     */
    @Test(expected = AmountException.AmountTooLargeException.class)
    public void testPrixTropEleve() {
        List<Item> grocery = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        
        // Génération d'un prix aléatoire compris entre 35.01$ et 100.00$
        // On couvre une plage plus large tout en restant dans des valeurs pas trop haute.
        double invalidPrice = 35.01 + (64.99 * random.nextDouble());
        
        grocery.add(new Item(Upc.generateCode("12345678901"), "Premium Item", 1, invalidPrice));
        register.print(grocery);
    }

    /**
     * T08_Prix_Strictement_Negatif
     * * @Cibles PI_I2
      */
    @Test(expected = AmountException.NegativeAmountException.class)
    public void testPrixStrictementNegatif() {
        List<Item> grocery = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        
        // Génération d'un prix négatif aléatoire compris entre -0.01$ et -100.00$
        // 0.01 + (99.99 * random.nextDouble()) donne une valeur entre 0.01 et 100.00 multiplie par -1
        double negativePrice = -(0.01 + (99.99 * random.nextDouble()));
        
        grocery.add(new Item(Upc.generateCode("12345678901"), "Negative Price Item", 1, negativePrice));
        register.print(grocery);
    }

    /**
     * T09_Quantite_Fractionnaire_CUP_Invalide
     * * @Cibles CU_I1
     */
    @Test(expected = InvalidQuantityException.InvalidQuantityForCategoryException.class)
    public void testQuantiteFractionnaireCupInvalide() {
        List<Item> grocery = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        
        // On veut un chiffre entre 1 et 9, mais pas 2
        int[] choixPossibles = {1, 3, 4, 5, 6, 7, 8, 9};
        int firstDigit = choixPossibles[random.nextInt(choixPossibles.length)];
        
        // Construction de la base
        StringBuilder base = new StringBuilder();
        base.append(firstDigit);
        for (int i = 0; i < 10; i++) {
            base.append(random.nextInt(10)); // Les 10 chiffres suivants peuvent être n'importe quoi (0-9)
        }
        
        // Ajout de l'item
        grocery.add(new Item(Upc.generateCode(base.toString()), "Wrong Weight Code", 1.25, 2.00));
        register.print(grocery);
    }

    /**
     * T10_CUP_Trop_Court
     * * @Cibles CU_I2 (Valeur limite : <12 caractères)
    */
    @Test(expected = InvalidUpcException.class)
    public void testCupTropCourt() {
        List<Item> grocery = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        
        // Randomise la longueur entre 1 et 11 (car 12 est le format valide)
        int length = 1 + random.nextInt(11); 
        
        // Génère une chaîne de chiffres aléatoires de la longueur choisie
        StringBuilder shortUpc = new StringBuilder();
        for (int i = 0; i < length; i++) {
            shortUpc.append(random.nextInt(10));
        }
        
        // On insère ce CUP invalide (trop court)
        grocery.add(new Item(shortUpc.toString(), "Short UPC", 1, 1.00));
        
        register.print(grocery);
    }

    /**
     * T11_CUP_Trop_Long
     * * @Cibles CU_I3 (Valeur limite : >12 caractères)
     */
    @Test(expected = InvalidUpcException.class)
    public void testCupTropLong() {
        List<Item> grocery = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        
        // Randomise la longueur entre 13 et 20 (car 12 est le maximum valide)
        int length = 13 + random.nextInt(8);
        
        // Génère une chaîne de chiffres aléatoires de la longueur choisie
        StringBuilder longUpc = new StringBuilder();
        for (int i = 0; i < length; i++) {
            longUpc.append(random.nextInt(10));
        }
        
        grocery.add(new Item(longUpc.toString(), "Long UPC", 1, 1.00));
        
        register.print(grocery);
    }

    /**
     * T12_Cle_Controle_CUP_Invalide
     * * @Cibles CU_I4
     */
    @Test(expected = InvalidUpcException.InvalidCheckDigitException.class)
    public void testCleControleCupInvalide() {
        List<Item> grocery = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        
        //Générer une base valide de 11 chiffres
        StringBuilder base = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            base.append(random.nextInt(10));
        }
        
        //Obtenir le code complet valide (pour connaître la vraie clé)
        String codeValide = Upc.generateCode(base.toString());
        
        // Extraire les 11 premiers chiffres et forcer une erreur sur le 12e
        String base11 = codeValide.substring(0, 11);
        int vraiCheckDigit = Character.getNumericValue(codeValide.charAt(11));
        
        // Choisir un chiffre différent du vrai check digit
        int fauxCheckDigit = (vraiCheckDigit + 1 + random.nextInt(9)) % 10;
        
        String upcInvalide = base11 + fauxCheckDigit;
        
        //Utiliser ce CUP invalide
        grocery.add(new Item(upcInvalide, "Bad Check Digit", 1, 1.00));
        
        register.print(grocery);
    }

    /**
     * T13_Annulation_Sans_Ajout_Prealable
     * * @Cibles QU_I1
     */
    @Test(expected = Register.NoSuchItemException.class)
    public void testAnnulationSansAjoutPrealable() {
        List<Item> grocery = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        
        // Génération d'une quantité négative aléatoire entre -1 et -20
        int negativeQuantity = -(1 + random.nextInt(20));
        
        // Ajout de l'item avec une quantité négative
        grocery.add(new Item(Upc.generateCode("12345678901"), "Ghost Item", negativeQuantity, 1.00));
        
        register.print(grocery);
    }

    /**
     * T14_Annulation_Superieure_Au_Produit
     * * @Cibles QU_I2
     */
    @Test
    public void testAnnulationSuperieureAuProduit() {
        List<Item> grocery = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        String upc = Upc.generateCode("12345678901");
        
        int quantiteAjoutee = 1 + random.nextInt(5);
        int quantiteAnnulee = quantiteAjoutee + 1 + random.nextInt(10);
        
        grocery.add(new Item(upc, "Produit Test", quantiteAjoutee, 1.50));
        grocery.add(new Item(upc, "Annulation", -quantiteAnnulee, 1.50));
        
        // --- BLOC DE DÉBOGAGE ---
        try {
            register.print(grocery);
            // Si on arrive ici, aucune exception n'a été levée
            org.junit.Assert.fail("Test échoué : RegisterException aurait dû être levée car l'annulation (" 
                + quantiteAnnulee + ") est supérieure à la quantité (" + quantiteAjoutee + ")");
        } catch (stev.kwikemart.RegisterException e) {
            // C'est le comportement attendu, tout va bien !
            System.out.println("Succès : L'exception RegisterException a bien été levée.");
        } catch (Exception e) {
            // Une autre exception (non prévue) a été levée
            e.printStackTrace();
            org.junit.Assert.fail("Mauvaise exception levée : " + e.getClass().getName());
        }
    }
    
    /**
     * T15_Annulation_Prix_Superieur_Invalide
     * @Cibles QU_I3
     */
    @Test
    public void testAnnulationPrixSuperieurInvalide() {
        List<Item> grocery = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        
        String upc = Upc.generateCode("12345678901");
        double prixInitial = 10.00;
        grocery.add(new Item(upc, "Produit", 1, prixInitial));
        
        double prixAnnulationInvalide = 10.01 + (39.99 * random.nextDouble());
        grocery.add(new Item(upc, "Annulation Frauduleuse", -1, prixAnnulationInvalide));
        
        // --- BLOC DE DÉBOGAGE ---
        try {
            register.print(grocery);
            // Si on arrive ici, c'est qu'aucune exception n'a été levée !
            org.junit.Assert.fail("Le test aurait dû échouer avec une exception, mais rien n'a été lancé.");
        } catch (Exception e) {
            // Ici, on attrape TOUTES les exceptions
            System.out.println("DEBUG : Exception attrapée = " + e.getClass().getName());
            e.printStackTrace(); // Affiche la trace complète dans la console
            
            // Vérifie si c'est bien celle qu'on attend
            if (!(e instanceof CouponException.InvalidCouponQuantityException)) {
                org.junit.Assert.fail("L'exception levée n'est pas la bonne. Attendue: InvalidCouponQuantityException, Reçue: " + e.getClass().getName());
            }
        }
    }

    /**
     * T16_Coupon_Superieur_Au_Total_Achats
     * * @Cibles CO_I1
      */
    @Test
    public void testCouponSuperieurAuTotalAchats() {
        List<Item> grocery = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        
        // Achat normal (positif)
        double prixAchat = 1.00 + (9.00 * random.nextDouble());
        grocery.add(new Item(Upc.generateCode("12345678901"), "Item Achat", 1, prixAchat));
        
        // Coupon avec PRIX > au prix d'achat
        double valeurCoupon = prixAchat + 0.01 + (10.00 * random.nextDouble());
        grocery.add(new Item(Upc.generateCode("54323432343"), "Giant Coupon", 1, valeurCoupon)); 

        // On vérifie que le reçu n'inclut pas le coupon car il est trop grand
        String receipt = register.print(grocery);
        assertFalse("Le coupon géant aurait dû être ignoré car il dépasse le total", 
                    receipt.contains("Giant Coupon"));
    }

    /**
     * T17_Coupon_Valeur_Negative
     * * @Cibles CO_I2
     */
    @Test(expected = AmountException.NegativeAmountException.class)
    public void testCouponValeurNegative() {
        List<Item> grocery = new ArrayList<>();
        java.util.Random random = new java.util.Random();
        
        // Génère un montant négatif aléatoire entre -0.01 et -10.00
        double negativeAmount = -(0.01 + (9.99 * random.nextDouble()));
        
        grocery.add(new Item(Upc.generateCode("54323432343"), "Negative Coupon", 1, negativeAmount));
        register.print(grocery);
    }
    
    /**
     * T18_Coupon_Valeur_Zero
     * @Cibles CO_I3
     */
    @Test
    public void testCouponValeurZero() {
        List<Item> grocery = new ArrayList<>();
        
        // 1. Setup : Ajout d'un item valide
        grocery.add(new Item(Upc.generateCode("12345678901"), "Item Valide", 1, 5.00));
        
        // 2. Action : Tentative d'ajout du coupon à 0.00$
        grocery.add(new Item(Upc.generateCode("54323432343"), "Zero Coupon", 1, 0.00));
        
        try {
            register.print(grocery);
            // Si aucune exception n'est levée, le test échoue
            org.junit.Assert.fail("Une NegativeAmountException aurait dû être levée pour un montant de 0.00$");
        } catch (AmountException.NegativeAmountException e) {//exception choisi arbitrairement (l'exception la plus proche)
            // Test réussi : l'exception attendue a bien été interceptée
        } catch (Exception e) {
            // Si une autre exception inattendue est levée (ex: NullPointerException), 
            // on veut savoir laquelle pour débugger
            org.junit.Assert.fail("Exception inattendue levée : " + e.getClass().getName());
        }
    }
    
/** Résumé
 	* Classes|Tests
	* LI_V1	T01
	* LI_V2	T02
	* LI_I1 T04
	* LI_I2	T05
	* LI_I3	T06
	* PI_V1	T01
	* PI_V2	T01
	* PI_I1	T07
	* PI_I2	T08
	* CU_V1	T01
	* CU_V2	T01
	* CU_V3	T01
	* CU_I1	T09
	* CU_I2	T10
	* CU_I3	T11
	* CU_I4	T12
	* QU_V1	T01
	* QU_V2	T01
	* QU_V3	T01
	* QU_I1	T13
	* QU_I2	T14
	* QU_I3	T15
	* RA_V1	T01
	* RA_V2	T02
	* RA_V3	T03
	* CO_V1	T01
	* CO_V2	T01
	* CO_I1	T16
	* CO_I2	T17
	* CO_I3	T18
 */

}

