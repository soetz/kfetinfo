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
 * <p>Plat est une classe décrivant un plat pouvant être servi à la K'Fet. Elle hérite de {@code ContenuCommande}.</p>
 * <p>Les plats ont comme spécificités :
 * <ul><li>un attribut {@code nbMaxIngredients} de type {@code int} décrivant le nombre maximal d'ingrédients pouvant être ajoutés au plat avant de payer un supplément,</li>
 * <li>un attribut {@code nbMaxSauces} de type {@code int} décrivant le nombre maximal de sauces pouvant être ajoutées au plat,</li>
 * <li>un attribut {@code utilisePain} de type {@code boolean} décrivant si le plat a besoin de pain pour être réalisé,</li>
 * <li>un attribut {@code prix} de type {@code float} décrivant le prix minimum que doit payer un client choisissant ce plat.</li></ul></p>
 * 
 * @see ContenuCommande
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class Plat extends ContenuCommande {

	//attributs de la classe
	private int nbMaxIngredients;
	private int nbMaxSauces;
	private boolean utilisePain;
	private float prix;

	/**
	 * <p>Constructeur Plat.</p>
	 * <p>Tous les paramètres sont appliqués aux attributs de l'objet directement.</p>
	 * 
	 * @param id l'identificateur du plat, utilisé dans le cadre de la base de données notamment.
	 * @param nom le nom du plat.
	 * @param cout le coût du plat, c'est à dire une estimation de la quantité d'argent dépensée pour ajouter le plat à la commande pour une portion classique.
	 * @param estDisponible le fait que le plat soit en stock ou pas.
	 * @param nbUtilisations le nombre d'utilisations du plat. Non implémenté pour l'instant.
	 * @param priorite la position du plat dans la liste des plats (la liste est triée d'abord par ordre de priorité puis par ordre alphabétique si les priorités sont égales).
	 * @param nbMaxIngredients le nombre maximal d'ingrédients pouvant être ajoutés au plat avant de payer un supplément.
	 * @param nbMaxSauces le nombre maximal de sauces pouvant être ajoutées au plat.
	 * @param utilisePain le fait que le plat ait besoin de pain pour être réalisé.
	 * @param prix le prix minimum que doit payer un client choisissant ce plat.
	 */
	public Plat(String id, String nom, float cout, boolean estDisponible, int nbUtilisations, int priorite, int nbMaxIngredients, int nbMaxSauces, boolean utilisePain, float prix){

		super(id, nom, cout, estDisponible, nbUtilisations, priorite); //appel au constructeur de la classe parente
		this.nbMaxIngredients = nbMaxIngredients;
		this.nbMaxSauces = nbMaxSauces;
		this.utilisePain = utilisePain;
		this.prix = prix;
	}

	/**
	 * Renvoie le {@code prix} du plat.
	 * 
	 * @return le prix du plat.
	 */
	public float getPrix(){

		return(prix);
	}

	/**
	 * Renvoie l'état {@code utilisePain} du plat.
	 * 
	 * @return le fait que le plat ait besoin de pain pour être réalisé.
	 */
	public boolean getUtilisePain(){

		return(utilisePain);
	}

	/**
	 * Renvoie le {@code nbMaxIngredients} du plat.
	 * 
	 * @return le nombre maximal d'ingrédients pouvant être ajoutés au plat avant de payer un supplément.
	 */
	public int getNbMaxIngredients(){

		return(nbMaxIngredients);
	}

	/**
	 * Renvoie le {@code nbMaxSauces} du plat.
	 * 
	 * @return le nombre maximal de sauces pouvant être ajoutées au plat.
	 */
	public int getNbMaxSauces(){

		return(nbMaxSauces);
	}

	/**
	 * Modifie l'état {@code estDisponible} du plat en lui affectant la valeur passée en paramètres. Met également à jour le fichier de la base de données correspondant à ce plat.
	 * 
	 * @param disponible le fait que le plat soit en stock.
	 */
	@Override
	public void setDisponible(boolean disponible){

		super.setDisponible(disponible);
		CreateurBase.mettrePlatAJour(this);
	}
}
