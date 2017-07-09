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

package kfetinfo;

import java.util.Date;

public class CommandeAssignee extends Commande {
	Date momentAssignation;
	boolean estRealisee;
	boolean estDonnee;
	
	public CommandeAssignee(Plat rienPlat, Dessert rienDessert, Boisson rienBoisson, SupplementBoisson rienSupplementBoisson, Commande commande, Date momentAssignation, boolean estRealisee, boolean estDonnee){
		super(rienPlat, rienDessert, rienBoisson, rienSupplementBoisson);
		
		this.moment = commande.getMoment();
		this.numero = commande.getNumero();
		this.plat = commande.getPlat();
		this.ingredients = commande.getIngredients();
		this.sauces = commande.getSauces();
		this.dessert = commande.getDessert();
		this.boisson = commande.getBoisson();
		this.supplementBoisson = commande.getSupplementBoisson();

		this.momentAssignation = momentAssignation;
		this.estRealisee = estRealisee;
		this.estDonnee = estDonnee;
	}
}
