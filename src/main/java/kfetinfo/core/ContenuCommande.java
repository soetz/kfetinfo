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

public class ContenuCommande {
	String id;
	String nom;
	float cout;
	boolean estDisponible;
	int nbUtilisations;
	int priorite;

	public ContenuCommande(String id, String nom, float cout, boolean estDisponible, int nbUtilisations, int priorite){
		this.id = id;
		this.nom = nom;
		this.cout = cout;
		this.estDisponible = estDisponible;
		this.nbUtilisations = nbUtilisations;
		this.priorite = priorite;
	}

	public int getPriorite(){
		return(priorite);
	}

	public String getNom(){
		return(nom);
	}

	public String getId(){
		return(id);
	}

	public float getCout(){
		return(cout);
	}

	public boolean getDisponible(){
		return(estDisponible);
	}

	public void setDisponible(boolean disponible){
		estDisponible = disponible;
	}

	public boolean equals(Object o){
		if(o == null){
			return(false);
		}
		if(o == this){
			return(true);
		}
		if(!(o instanceof ContenuCommande)){
			return(false);
		}
		ContenuCommande oContenuCommande = (ContenuCommande)o;
		if(id.equals(oContenuCommande.getId())){
			return(true);
		} else {
			return(false);
		}
	}
}
