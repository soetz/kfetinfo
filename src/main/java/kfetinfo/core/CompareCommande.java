package kfetinfo.core;

import java.util.Comparator;

/**
 * <p>CompareCommande est une classe permettant de comparer deux commandes, utile pour mettre une liste en ordre en utilisant {@code Collections.sort()}.</p>
 * <p>Elle implémente {@code Comparator} et sa méthode {@code compare(arg0, arg1)}.</p>
 * 
 * @see Comparator
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class CompareCommande implements Comparator<Commande> {

	/**
	 * Renvoie la différence entre le numéro de la commande passée en premier paramètre et celui de la commande passée en second paramètre.
	 * 
	 * @return la différence de numéro entre les commandes.
	 */
	public int compare(Commande arg0, Commande arg1) {

		return(arg0.getNumero() - arg1.getNumero());
	}
}
