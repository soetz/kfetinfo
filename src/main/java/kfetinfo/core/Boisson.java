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
 * <p>Boisson est une classe décrivant une boisson pouvant être servie à la K'Fet. Elle hérite de {@code ContenuCommande}.</p>
 * 
 * @see ContenuCommande
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class Boisson extends ContenuCommande {

	/**
	 * <p>Constructeur Boisson.</p>
	 * <p>Tous les paramètres sont appliqués aux attributs de l'objet directement.</p>
	 *
	 * @param id l'identificateur de la boisson, utilisé dans le cadre de la base de données notamment.
	 * @param nom le nom de la boisson.
	 * @param cout le coût de la boisson, c'est à dire une estimation de la quantité d'argent dépensée pour servir la boisson si celle-ci est servie de manière classique.
	 * @param estDisponible le fait que la boisson soit en stock ou pas.
	 * @param nbUtilisations le nombre d'utilisations de la boisson. Non implémenté pour l'instant.
	 * @param priorite la position de la boisson dans la liste des boissons (la liste est triée d'abord par ordre de priorité puis par ordre alphabétique si les priorités sont égales).
	 */
	public Boisson(String id, String nom, float cout, boolean estDisponible, int nbUtilisations, int priorite){

		super(id, nom, cout, estDisponible, nbUtilisations, priorite); //appel au constructeur de la classe parente
	}

	/**
	 * Modifie l'état {@code estDisponible} de la boisson en lui affectant la valeur passée en paramètres. Met également à jour le fichier de la base de données correspondant à cette boisson.
	 * 
	 * @param disponible le fait que la boisson soit en stock.
	 */
	@Override
	public void setDisponible(boolean disponible){

		super.setDisponible(disponible);
		CreateurBase.mettreBoissonAJour(this);
	}
}
