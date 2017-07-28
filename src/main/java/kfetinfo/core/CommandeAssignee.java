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

import java.util.Date;

public class CommandeAssignee extends Commande {
	Membre membre;
	Date momentAssignation;
	boolean estRealisee;
	Date momentRealisation;
	boolean estDonnee;
	
	public CommandeAssignee(Commande commande, Membre membre, Date momentAssignation, boolean estRealisee, Date momentRealisation, boolean estDonnee){
		super(commande.getMoment(), commande.getNumero(), commande.getPlat(), commande.getIngredients(), commande.getSauces(), commande.getDessert(), commande.getBoisson(), commande.getSupplementBoisson());

		this.membre = membre;
		this.momentAssignation = momentAssignation;
		this.estRealisee = estRealisee;
		this.momentRealisation = momentRealisation;
		this.estDonnee = estDonnee;
	}

	public Membre getMembre(){
		return(membre);
	}

	public Date getMomentAssignation(){
		return(momentAssignation);
	}

	public boolean getEstRealisee(){
		return(estRealisee);
	}

	public Date getMomentRealisation(){
		return(momentRealisation);
	}

	public boolean getEstDonnee(){
		return(estDonnee);
	}

	public void realisee(){
		momentRealisation = new Date();
	}

	public long tempsRealisation(){
		long temps = momentRealisation.getTime() - momentAssignation.getTime();
		if(temps > 0){
			return(temps);
		}
		else {
			return(0);
		}
	}

	public String toString(){
		return(chaineToString() + "Membre : " + membre.getBlaze() + "\n");
	}
}
