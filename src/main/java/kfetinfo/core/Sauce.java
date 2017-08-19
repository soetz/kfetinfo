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
 * <p>Sauce est une classe décrivant une sauce pouvant être utilisée pour assaisonner un plat pouvant être servi à la K'Fet. Elle hérite de {@code ContenuCommande}.</p>
 * 
 * @see ContenuCommande
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class Sauce extends ContenuCommande {

	/**
	 * <p>Constructeur Sauce.</p>
	 * <p>Tous les paramètres sont appliqués aux attributs de l'objet directement.</p>
	 *
	 * @param id l'identificateur de la sauce, utilisé dans le cadre de la base de données notamment.
	 * @param nom le nom de la sauce.
	 * @param cout le coût de la sauce, c'est à dire une estimation de la quantité d'argent dépensée pour ajouter la sauce au plat pour une portion classique.
	 * @param estDisponible le fait que la sauce soit en stock ou pas.
	 * @param nbUtilisations le nombre d'utilisations de la sauce. Non implémenté pour l'instant.
	 * @param priorite la position de la sauce dans la liste des sauces (la liste est triée d'abord par ordre de priorité puis par ordre alphabétique si les priorités sont égales).
	 */
	public Sauce(String id, String nom, float cout, boolean estDisponible, int nbUtilisations, int priorite){

		super(id, nom, cout, estDisponible, nbUtilisations, priorite); //appel au constructeur de la classe parente
	}

	/**
	 * <p>Constructeur Sauce.</p>
	 * <p>Crée une sauce basique, avec un {@code id} et un {@code nom} vide, un {@code cout} de 0, un état {@code estDisponible} faux, un {@code nbUtilisations} de 0 et une {@code priorite} de 0.</p>
	 */
	public Sauce(){

		this("", "", 0f, false, 0, 0);
	}

	/**
	 * Modifie l'état {@code estDisponible} de la sauce en lui affectant la valeur passée en paramètres. Met également à jour le fichier de la base de données correspondant à cette sauce.
	 * 
	 * @param disponible le fait que la sauce soit en stock.
	 */
	@Override
	public void setDisponible(boolean disponible){

		super.setDisponible(disponible);
		CreateurBase.mettreSauceAJour(this);
	}
}
