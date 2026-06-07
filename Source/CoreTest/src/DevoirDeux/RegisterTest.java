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
 * CU_I4  : Composé de strictement 12 chiifres avec une clé de controle invalide (Invalide -> InvalidCheckDigitException ) [Heuristique : Spécifique]
 * 
 * 
 *Classe 4 : La Quantité QU
 * 
 * QU_V1  : Item avec quantité strictement supérieur à 0 [Heuristique : Intervalle]
 * QU_V2  : Item avec quantité négative servant à annuler un item identique déjà présent auparavant avec une valeur inférieur ou égal [Heuristique : Spécifique]
 * QU_V3  : Item avec quantité Fractionaire si CUP commence par un 2 [Heuristique : Groupe]
 * QU_I1  : Saisie d'une quantité négative sans qu'aucun item correspondant n'ait été scanné avant (Invalide -> NoSuchItemException) [Heuristique : Spécifique]
 * QU_I2  : Saisie d'une quantité négative superieur au premier produit (Invalide -> CouponException.InvalidCouponQuantityException) [Heuristique : Intervalle - Spécifique]
 * (QU_I3  : Autre valeur qu'un nombre (Invalide ->) [Heuristique : Spécifique])
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
 * CO_I2  : Valeur du coupon <= 0[Heuristique : Intervalle]
 * (CO_I3  : Valeur du coupon autre qu'un nombre [Heuristique : Spécifique])
 * ============================================================================
 * STRATÉGIE DE COMBINAISON DES CLASSES
 * ============================================================================
 * - Pour les classes VALIDES : Nous maximisons en regroupant le plus grand nombre
 *    de cas passant.
 * - Pour les classes INVALIDES :Pour respecter l'hypothèse d'un seul défault par test, chaque 
 *   test d'erreur isole un seul cas non invalide.
 */


public class RegisterTest {

	 /**
     * test
     */
    @Test
    public void testNormalFrameInitial() {
    	Register register = Register.getRegister();
		assertEquals(true, true);
    }

}

//Mettre de l'aléatoire'