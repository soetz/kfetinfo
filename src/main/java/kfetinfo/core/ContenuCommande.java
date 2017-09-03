/*
 * kfetinfo - Logiciel pour la K'Fet du BDE Info de l'IUT Lyon 1
 *  Copyright (C) 2017 Simon Lecutiez

 *  This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package kfetinfo.core;

import java.util.Objects;

/**
 * <p>ContenuCommande est une classe décrivant un élément pouvant faire partie d'une commande.</p>
 * <p>Les éléments pouvant être des contenus commande et qui héritent tous de cette classe sont :
 * <ul><li>les {@code Plat},</li>
 * <li>les {@code Ingredient},</li>
 * <li>les {@code Sauce},</li>
 * <li>les {@code Boisson},</li>
 * <li>les {@code SupplementBoisson}, et</li>
 * <li>les {@code Dessert}.</li></ul></p>
 * <p>Chaque contenu commande a un {@code id} qui permet de l'identifier dans la base de données.</p>
 * 
 * @see Plat
 * @see Ingredient
 * @see Sauce
 * @see Boisson
 * @see SupplementBoisson
 * @see Dessert
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public abstract class ContenuCommande {

	//attributs de la classe
	private String id;
	private String nom;
	private float cout;
	private boolean estDisponible;
	private int nbUtilisations;
	private int priorite;

	/**
	 * <p>Constructeur ContenuCommande.</p>
	 * <p>Tous les paramètres sont appliqués aux attributs de l'objet directement.</p>
	 * 
	 * @param id l'identificateur du contenu commande, utilisé dans le cadre de la base de données notamment.
	 * @param nom le nom du contenu commande.
	 * @param cout le coût du contenu commande, c'est à dire une estimation de la quantité d'argent dépensée pour ajouter le contenu commande à la commande pour une portion classique.
	 * @param estDisponible le fait que le contenu commande soit en stock ou pas.
	 * @param nbUtilisations le nombre d'utilisations du contenu commande. Non implémenté pour l'instant.
	 * @param priorite la position du contenu commande dans sa liste (la liste est triée d'abord par ordre de priorité puis par ordre alphabétique si les priorités sont égales).
	 */
	public ContenuCommande(String id, String nom, float cout, boolean estDisponible, int nbUtilisations, int priorite){

		this.id = id;
		this.nom = nom;
		this.cout = cout;
		this.estDisponible = estDisponible;
		this.nbUtilisations = nbUtilisations;
		this.priorite = priorite;
	}

	/**
	 * Renvoie la {@code priorite} du contenu commande.
	 * 
	 * @return la priorité du contenu commande.
	 */
	public int getPriorite(){

		return(priorite);
	}

	/**
	 * Renvoie le {@code nom} du contenu commande.
	 * 
	 * @return le nom du contenu commande.
	 */
	public String getNom(){

		return(nom);
	}

	/**
	 * Renvoie l'{@code id} du contenu commande.
	 * 
	 * @return l'identificateur du contenu commande.
	 */
	public String getId(){

		return(id);
	}

	/**
	 * Renvoie le {@code cout} du contenu commande.
	 * 
	 * @return le coût du contenu commande, c'est à dire une estimation de la quantité d'argent dépensée pour ajouter le contenu commande à la commande pour une portion classique.
	 */
	public float getCout(){

		return(cout);
	}

	/**
	 * Renvoie l'état {@code estDisponible} du contenu commande.
	 * 
	 * @return le booléen correspondant au fait que le contenu commande soit en stock.
	 */
	public boolean getDisponible(){

		return(estDisponible);
	}

	/**
	 * Renvoie le {@code nbUtilisations} du contenu commande.
	 * 
	 * @return le nombre de fois où le contenu commande a déjà été utilisé dans une commande.
	 */
	public int getNbUtilisations(){

		return(nbUtilisations);
	}

	/**
	 * Modifie l'état {@code estDisponible} du contenu commande en lui affectant la valeur passée en paramètres.
	 * 
	 * @param disponible le fait que le contenu commande soit en stock.
	 */
	public void setDisponible(boolean disponible){

		estDisponible = disponible;
	}

	/**
	 * Indique si deux objets de types {@code ContenuCommande} sont « égaux », c'est à dire qu'ils ont le même {@code id}.
	 * 
	 * @param o l'objet avec lequel comparer ce contenu commande.
	 * 
	 * @return le fait que le contenu commande passé en paramètres et le contenu commande propriétaire de la méthode ont des attributs {@code id} égaux.
	 */
	@Override
	public boolean equals(Object o){

		if(o == null){ //si l'objet passé en paramètres est null, on renvoie false
			return(false);
		}
		if(o == this){ //si cet objet et celui passé en paramètres font référence au même objet, on renvoie true
			return(true);
		}
		if(!(o instanceof ContenuCommande)){ //si l'objet passé en paramètres n'est pas une instance de ContenuCommande, on renvoie false
			return(false);
		}

		ContenuCommande oContenuCommande = (ContenuCommande)o; //on cast l'objet passé en paramètres en ContenuCommande
		if(id.equals(oContenuCommande.getId())){ //si les id du ContenuCommande passé en paramètres et de cet objet sont égaux, on renvoie true, et false sinon
			return(true);
		} else {
			return(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Cette méthode doit être override si on override java.lang.Object.equals(Object o).
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){

		return(Objects.hash(id));
	}
}
