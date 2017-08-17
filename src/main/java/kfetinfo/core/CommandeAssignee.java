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

/**
 * <p>CommandeAssignee est une classe décrivant une commande pouvant être formulée par un client pendant un service de la K'Fet qui a été affectée à un membre afin d'être confectionnée.
 * 
 * @see Commande
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class CommandeAssignee extends Commande {

	//attributs de la classe
	private Membre membre;
	private Date momentAssignation;
	private boolean estRealisee;
	private Date momentRealisation;
	private boolean estDonnee;

	/**
	 * <p>Constructeur CommandeAssignee.</p>
	 * <p>Tous les paramètres sont appliqués aux attributs de l'objet directement.</p>
	 * 
	 * @param commande la commande à transformer en commande assignée.
	 * @param membre le membre chargé de la confection de la commande.
	 * @param momentAssignation le moment où cette commande a été assignée.
	 * @param estRealisee le fait que cette commande soit réalisée.
	 * @param momentRealisation le moment où cette commande a été réalisée.
	 * @param estDonnee le fait que cette commande soit donnée.
	 */
	public CommandeAssignee(Commande commande, Membre membre, Date momentAssignation, boolean estRealisee, Date momentRealisation, boolean estDonnee){

		super(commande.getMoment(), commande.getNumero(), commande.getPlat(), commande.getIngredients(), commande.getSauces(), commande.getDessert(), commande.getBoisson(), commande.getSupplementBoisson());

		this.membre = membre;
		this.momentAssignation = momentAssignation;
		this.estRealisee = estRealisee;
		this.momentRealisation = momentRealisation;
		this.estDonnee = estDonnee;
	}

	/**
	 * Renvoie le {@code membre} chargé de la confection de cette commande.
	 * 
	 * @return le membre chargé de confectionner cette commande.
	 */
	public Membre getMembre(){

		return(membre);
	}

	/**
	 * Renvoie le {@code momentAssignation} de cette commande.
	 * 
	 * @return le moment où cette commande a été assignée.
	 */
	public Date getMomentAssignation(){

		return(momentAssignation);
	}

	/**
	 * Renvoie l'état {@code estRealisee} de cette commande.
	 * 
	 * @return le fait que cette commande soit réalisée.
	 */
	public boolean getEstRealisee(){

		return(estRealisee);
	}

	/**
	 * Renvoie le {@code momentRealisation} de cette commande.
	 * 
	 * @return le moment où cette commande a été réalisée.
	 */
	public Date getMomentRealisation(){

		return(momentRealisation);
	}

	/**
	 * Renvoie l'état {@code estDonnee} de cette commande.
	 * 
	 * @return le fait que cette commande soit donnée.
	 */
	public boolean getEstDonnee(){

		return(estDonnee);
	}

	/**
	 * Indique que cette commande a été réalisée.
	 * 
	 * @param service le service au sein duquel cette commande a été créée.
	 */
	public void realisee(Service service){

		momentRealisation = new Date();
		estRealisee = true;
		CreateurBase.ajouterCommandeAssignee(this);
		service.assignation();
		service.commandeRealisee(this);
	}

	/**
	 * Renvoie le temps qu'a mis le membre à réaliser cette commande.
	 * 
	 * @return la quantité de temps que la commande a pris pour être réalisée à partir du moment où elle a été assignée.
	 */
	public long tempsRealisation(){

		long temps = momentRealisation.getTime() - momentAssignation.getTime();

		if(temps > 0){
			return(temps);
		}
		else {
			return(0);
		}
	}

	/**
	 * Indique que cette commande a été donnée au client.
	 * 
	 * @param service le service au sein duquel cette commande a été créée.
	 */
	public void donnee(Service service){

		if(!estRealisee){
			realisee(service);
		}

		estDonnee = true;
		CreateurBase.ajouterCommandeAssignee(this);
		service.commandeDonnee(this);
	}

	/**
	 * Renvoie une représentation textuelle de cette commande assignée.
	 * 
	 * @return une représentation textuelle de cette commande assignée.
	 */
	@Override
	public String toString(){

		return(super.toString() + "Membre : " + membre.getBlaze() + "\n");
	}
}
