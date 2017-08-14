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

public class Membre {
	String id;
	String nom;
	String prenom;
	String surnom;
	String poste;
	Date dateNaissance;
	int nbCommandes;
	int nbServices;
	float tempsMoyenCommande;

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

	public Membre(){
		this.id = "f38aa97b-2c4b-491e-be10-884e48fbb6c2";
		this.nom = "";
		this.prenom = "Personne";
		this.surnom = "";
		this.poste = "";
		this.dateNaissance = new Date(0);
		this.nbCommandes = 0;
		this.nbServices = 0;
		this.tempsMoyenCommande = 0;
	}

	public String getId(){
		return(id);
	}

	public String getPrenom(){
		return(prenom);
	}

	public String getNom(){
		return(nom);
	}

	public String getSurnom(){
		return(surnom);
	}

	public String getPoste(){
		return(poste);
	}

	public Date getDateNaissance(){
		return(dateNaissance);
	}

	public int getNbCommandes(){
		return(nbCommandes);
	}

	public int getNbServices(){
		return(nbServices);
	}

	public float getTempsMoyenCommande(){
		return(tempsMoyenCommande);
	}

	public String getBlaze(){
		String prenomBlaze = getPrenom();
		String surnomBlaze = "";
		String nomBlaze = getNom();

		if(!(getSurnom().equals(""))){
			surnomBlaze = "\"" + getSurnom() + "\" ";
		}

		return(prenomBlaze + " " + surnomBlaze + nomBlaze);
	}

	public String getBlazeCourt(){
		return((getSurnom().equals("")) ? getPrenom() : getSurnom());
	}

	public boolean equals(Object o){
		if(o == null){
			return(false);
		}
		if(o == this){
			return(true);
		}
		if(!(o instanceof Membre)){
			return(false);
		}
		Membre oMembre = (Membre)o;
		if(oMembre.getId().equals(this.getId())){
			return(true);
		} else {
			return(false);
		}
	}

	public String toString(){
		return(getBlaze());
	}
}
