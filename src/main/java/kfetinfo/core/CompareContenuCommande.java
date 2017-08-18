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
 * <p>CompareContenuCommande est une classe permettant de comparer deux contenus commande, utile pour mettre une liste en ordre en utilisant {@code Collections.sort()}.</p>
 * <p>Elle implémente {@code Comparator} et sa méthode {@code compare(arg0, arg1)}.</p>
 * 
 * @see Comparator
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class CompareContenuCommande implements Comparator<ContenuCommande> {

	/**
	 * Renvoie la différence entre la priorité du premier contenu commande passé en paramètres et celle du second, et s'ils s'ont égaux, compare la position de leur nom dans l'ordre alphabétique.
	 * 
	 * @return la différence de priorité entre les contenus commande, et si elles sont égales compare leur nom dans l'ordre alphabétique.
	 */
	public int compare(ContenuCommande arg0, ContenuCommande arg1) {

		if(arg0.getPriorite() == (arg1.getPriorite())){
			return(arg1.getNom().compareTo(arg0.getNom()));
		} else {
			return(arg0.getPriorite() - arg1.getPriorite());
		}
	}
}
