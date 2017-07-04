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
}
