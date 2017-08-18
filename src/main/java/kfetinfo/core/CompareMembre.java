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

import java.util.Comparator;

/**
 * <p>CompareMembre est une classe permettant de comparer deux membres, utile pour mettre une liste en ordre en utilisant {@code Collections.sort()}.</p>
 * <p>Elle implémente {@code Comparator} et sa méthode {@code compare(arg0, arg1)}.</p>
 * 
 * @see Comparator
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class CompareMembre implements Comparator<Membre> {

	/**
	 * Renvoie la position relative du prénom du premier membre passé en paramètres avec celle du second.
	 * 
	 * @return la comparaison dans l'ordre alphabétique des prénoms des deux membres.
	 */
	public int compare(Membre arg0, Membre arg1) {

		return(arg0.getPrenom().compareTo(arg1.getPrenom()));
	}

}
