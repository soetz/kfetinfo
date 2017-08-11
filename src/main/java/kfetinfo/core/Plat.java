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

public class Plat extends ContenuCommande {
	int nbMaxIngredients;
	int nbMaxSauces;
	boolean utilisePain;
	float prix;

	public Plat(String id, String nom, float cout, boolean estDisponible, int nbTotalUtilisations, int priorite, int nbMaxIngredients, int nbMaxSauces, boolean utilisePain, float prix){
		super(id, nom, cout, estDisponible, nbTotalUtilisations, priorite);
		this.nbMaxIngredients = nbMaxIngredients;
		this.nbMaxSauces = nbMaxSauces;
		this.utilisePain = utilisePain;
		this.prix = prix;
	}

	public float getPrix(){
		return(prix);
	}

	public boolean getUtilisePain(){
		return(utilisePain);
	}

	public int getNbMaxIngredients(){
		return(nbMaxIngredients);
	}

	public int getNbMaxSauces(){
		return(nbMaxSauces);
	}

	public void setDisponible(boolean disponible){
		super.setDisponible(disponible);
		CreateurBase.mettrePlatAJour(this);
	}
}
