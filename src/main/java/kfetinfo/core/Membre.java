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

import java.util.Date;

/**
 * <p>Membre est une classe décrivant une personne pouvant participer au service de la K'Fet.</p>
 * <p>Chaque membre a un {@code id} qui permet de l'identifier dans la base de données.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class Membre {

	//id du membre par défaut
	public static final String ID_MEMBRE_DEFAUT = "f38aa97b-2c4b-491e-be10-884e48fbb6c2";

	//attributs de la classe
	private String id;
	private String nom;
	private String prenom;
	private String surnom;
	private String poste;
	private Date dateNaissance;
	private int nbCommandes;
	private int nbServices;
	private float tempsMoyenCommande;

	/**
	 * <p>Constructeur Membre.</p>
	 * <p>Tous les paramètres sont appliqués aux attributs de l'objet directement.</p>
	 * 
	 * @param id l'identificateur du membre, utilisé dans le cadre de la base de données notamment.
	 * @param nom le nom de famille du membre.
	 * @param prenom le prénom du membre.
	 * @param surnom le surnom du membre.
	 * @param poste le poste du membre.
	 * @param dateNaissance la date de naissance du membre.
	 * @param nbCommandes le nombre de commandes effectuées par le membre. Non implémenté pour l'instant.
	 * @param nbServices le nombre de services auquels a participé le membre. Non implémenté pour l'instant.
	 * @param tempsMoyenCommande le temps moyen que met le membre pour réaliser une commande. Non implémenté pour l'instant.
	 */
	public Membre(String id, String nom, String prenom, String surnom, String poste, Date dateNaissance, int nbCommandes, int nbServices, float tempsMoyenCommande){

		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.surnom = surnom;
		this.poste = poste;
		this.dateNaissance = dateNaissance;
		this.nbCommandes = nbCommandes;
		this.nbServices = nbServices;
		this.tempsMoyenCommande = tempsMoyenCommande;
	}

	/**
	 * <p>Constructeur Membre.</p>
	 * <p>Crée un membre basique, avec un {@code id} fixé, un {@code nom}, {@code surnom} et {@code poste} vides, un {@code prenom} défini sur « Personne », une {@code dateNaissance} définie sur la date la plus petite gérée par la classe java.util.Date (le 1er Janvier 1970 au moment où cette doc est rédigée), un {@code nbCommandes} de 0, un {@code nbServices} de 0 et un {@code tempsMoyenCommande} de 0.
	 */
	public Membre(){

		this.id = ID_MEMBRE_DEFAUT;
		this.nom = "";
		this.prenom = "Personne";
		this.surnom = "";
		this.poste = "";
		this.dateNaissance = new Date(0);
		this.nbCommandes = 0;
		this.nbServices = 0;
		this.tempsMoyenCommande = 0;
	}

	/**
	 * Renvoie l'{@code id} du membre.
	 * 
	 * @return l'identificateur du membre.
	 */
	public String getId(){

		return(id);
	}

	/**
	 * Renvoie le {@code prenom} du membre.
	 * 
	 * @return le prénom du membre.
	 */
	public String getPrenom(){

		return(prenom);
	}

	/**
	 * Renvoie le {@code nom} du membre.
	 * 
	 * @return le nom de famille du membre.
	 */
	public String getNom(){

		return(nom);
	}

	/**
	 * Renvoie le {@code surnom} du membre.
	 * 
	 * @return le surnom du membre.
	 */
	public String getSurnom(){

		return(surnom);
	}

	/**
	 * Renvoie le {@code poste} du membre.
	 *
	 * @return le poste du membre.
	 */
	public String getPoste(){

		return(poste);
	}

	/**
	 * Renvoie la {@code dateNaissance} du membre.
	 * 
	 * @return la date de naissance du membre.
	 */
	public Date getDateNaissance(){

		return(dateNaissance);
	}

	/**
	 * Renvoie le {@code nbCommandes} du membre.
	 * 
	 * @return le nombre de commandes réalisées par le membre.
	 */
	public int getNbCommandes(){

		return(nbCommandes);
	}

	/**
	 * Renvoie le {@code nbServices} du membre.
	 * 
	 * @return le nombre de services auquel le membre a participé.
	 */
	public int getNbServices(){

		return(nbServices);
	}

	/**
	 * Renvoie le {@code tempsMoyenCommande} du membre.
	 * 
	 * @return le temps que met en moyenne le membre à réaliser une commande.
	 */
	public float getTempsMoyenCommande(){

		return(tempsMoyenCommande);
	}

	/**
	 * Renvoie le blaze du membre, c'est à dire son prénom, son surnom entre guillemets s'il en possède un puis son nom de famille.
	 * 
	 * @return le blaze du membre.
	 */
	public String getBlaze(){

		String prenomBlaze = getPrenom();
		String surnomBlaze = (getSurnom().equals("")) ? "" : "\"" + getSurnom() + "\" "; //si le surnom du membre n'est pas vide, on l'insère entre guillemets entre le prénom et le nom de famille
		String nomBlaze = getNom();

		return(prenomBlaze + " " + surnomBlaze + nomBlaze);
	}

	/**
	 * Renvoie le blaze abrégé du membre, c'est à dire son surnom s'il en possède un, et sinon son prénom.
	 * 
	 * @return le blaze court du membre.
	 */
	public String getBlazeCourt(){

		return((getSurnom().equals("")) ? getPrenom() : getSurnom()); //si le surnom du membre est vide, on renvoie getPrenom(), sinon on renvoie getSurnom()
	}

	/**
	 * Indique si deux objets de types {@code Membre} sont « égaux », c'est à dire qu'ils ont le même {@code id}.
	 * 
	 * @param o l'objet avec lequel comparer ce membre.
	 * 
	 * @return le fait que le membre passé en paramètres et le membre propriétaire de la méthode ont des attributs {@code id} égaux.
	 */
	@Override
	public boolean equals(Object o){

		if(o == null){ //si l'objet passé en paramètres est null, on renvoie false
			return(false);
		}
		if(o == this){ //si cet objet et celui passé en paramètres font référence au même objet, on renvoie true
			return(true);
		}
		if(!(o instanceof Membre)){ //si l'objet passé en paramètres n'est pas une instance de Membre, on renvoie false
			return(false);
		}

		Membre oMembre = (Membre)o; //on cast l'objet passé en paramètres en Membre
		if(oMembre.getId().equals(this.getId())){ //si les id du Membre passé en paramètres et de cet objet sont égaux, on renvoie true, et false sinon
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

	/**
	 * Renvoie une représentation textuelle de ce membre, c'est à dire son blaze.
	 * 
	 * @return le blaze du membre.
	 */
	@Override
	public String toString(){

		return(getBlaze());
	}
}
