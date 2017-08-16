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

/**
 * <p>Dessert est une classe décrivant un dessert pouvant être servi à la K'Fet.</p>
 * <p>Les desserts ont pour spécificité le fait d'avoir un attribut {@code prix} de type {@code float} décrivant le prix que doit payer le client pour ajouter ce dessert à sa commande.</p>
 * 
 * @see ContenuCommande
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class Dessert extends ContenuCommande {

	//attribut de la classe
	private float prix;

	/**
	 * <p>Constructeur Dessert.</p>
	 * <p>Tous les paramètres sont appliqués aux attributs de l'objet directement.</p>
	 *
	 * @param id l'identificateur du dessert, utilisé dans le cadre de la base de données notamment.
	 * @param nom le nom du dessert.
	 * @param cout le coût du dessert, c'est à dire une estimation de la quantité d'argent dépensée pour réaliser le dessert pour une portion classique.
	 * @param estDisponible le fait que le dessert soit en stock ou pas.
	 * @param nbUtilisations le nombre d'utilisations du dessert. Non implémenté pour l'instant.
	 * @param priorite la position du dessert dans la liste des desserts (la liste est triée d'abord par ordre de priorité puis par ordre alphabétique si les priorités sont égales).
	 * @param prix le prix que doit payer le client pour ajouter ce dessert à sa commande.
	 */
	public Dessert(String id, String nom, float cout, boolean estDisponible, int nbUtilisations, int priorite, float prix){

		super(id, nom, cout, estDisponible, nbUtilisations, priorite); //appel au constructeur de la classe parente
		this.prix = prix;
	}

	/**
	 * Renvoie le {@code prix} du dessert.
	 * 
	 * @return le prix du dessert.
	 */
	public float getPrix(){

		return(prix);
	}

	/**
	 * Modifie l'état {@code estDisponible} du dessert en lui affectant la valeur passée en paramètres. Met également à jour le fichier de la base de données correspondant à ce dessert.
	 * 
	 * @param disponible le fait que le dessert soit en stock.
	 */
	@Override
	public void setDisponible(boolean disponible){

		super.setDisponible(disponible);
		CreateurBase.mettreDessertAJour(this);
	}
}
