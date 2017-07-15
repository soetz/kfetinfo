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
import java.text.SimpleDateFormat;

import java.util.List;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Service {
	Date date;
	List<Commande> commandes;
	float nbBaguettesBase;
	float nbBaguettesUtilisees;
	Membre ordi;
	List<Membre> commis;
	List<Membre> confection;

	static String path = new File("").getAbsolutePath();

	public Service(Date date){
		SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		SimpleDateFormat mois = new SimpleDateFormat("MM");
		SimpleDateFormat jour = new SimpleDateFormat("dd");

		try {
			Files.createDirectories(Paths.get(path + "\\src\\main\\resources\\Base de Données\\Services\\" + annee.format(date) + "\\" + mois.format(date) + "\\" + jour.format(date) + "\\"));
		} catch (Exception e){
			e.printStackTrace();
		}

		this.date = date;
		commandes = new ArrayList<Commande>();
		nbBaguettesBase = 0;
		nbBaguettesUtilisees = 0;
//		ordi = getOrdiDernierService();
//		commis = getCommisDernierService();
//		confection = getConfectionDernierService();

		chargerCommandes();
	}

	public Service(Date date, List<Commande> commandes, float nbBaguettesBase, float nbBaguettesUtilisees, Membre ordi, List<Membre> commis, List<Membre> confection){
		SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		SimpleDateFormat mois = new SimpleDateFormat("MM");
		SimpleDateFormat jour = new SimpleDateFormat("dd");

		try {
			Files.createDirectories(Paths.get(path + "\\src\\main\\resources\\Base de Données\\Services\\" + annee.format(date) + "\\" + mois.format(date) + "\\" + jour.format(date) + "\\"));
		} catch (Exception e){
			e.printStackTrace();
		}

		this.date = date;
		this.commandes = commandes;
		this.nbBaguettesBase = nbBaguettesBase;
		this.nbBaguettesUtilisees = nbBaguettesUtilisees;
		this.ordi = ordi;
		this.commis = commis;
		this.confection = confection;

		chargerCommandes();
	}

	public float getNbBaguettesRestantes(){
		float restantes = nbBaguettesBase - nbBaguettesUtilisees;
		if(restantes <= 0){
			return(0);
		}
		else {
			return(restantes);
		}
	}

	public int getDernierNumeroCommande(){
		chargerCommandes();

		return(commandes.size());
	}

	public void ajouterCommande(Commande commande){
		CreateurBase.ajouterCommande(commande);

		chargerCommandes();
	}

	public void chargerCommandes(){
		commandes = new ArrayList<Commande>();

		SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		SimpleDateFormat mois = new SimpleDateFormat("MM");
		SimpleDateFormat jour = new SimpleDateFormat("dd");

		try {
			Files.createDirectories(Paths.get(path + "\\src\\main\\resources\\Base de Données\\Services\\" + annee.format(date) + "\\" + mois.format(date) + "\\" + jour.format(date) + "\\"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		File dossierCommandes = new File(path + "\\src\\main\\resources\\Base de Données\\Services\\" + annee.format(date) + "\\" + mois.format(date) + "\\" + jour.format(date) + "\\");
		File[] listOfFiles = dossierCommandes.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()&&(FilenameUtils.getExtension(listOfFiles[i].getName()).equals("json"))) {
				String nom = FilenameUtils.removeExtension(listOfFiles[i].getName());
				int numero = Integer.parseInt(nom);

				if(LecteurBase.estAssignee(date, numero)){
					CommandeAssignee commande = LecteurBase.lireCommandeAssignee(date, numero);
					commandes.add(commande);
				}
				else {
					Commande commande = LecteurBase.lireCommande(date, numero);
					commandes.add(commande);
				}
	    	}
		}

		Collections.sort(commandes, new CompareCommande());
	}

	public Commande getCommande(int numero){
		Commande commande = new Commande(BaseDonnees.getRienPlat(), BaseDonnees.getRienDessert(), BaseDonnees.getRienBoisson(), BaseDonnees.getRienSupplementBoisson());

		chargerCommandes();

		for(Commande commandeListe : commandes){
			if(commandeListe.getNumero() == numero){
				commande = commandeListe;
			}
		}

		chargerCommandes();

		return(commande);
	}

	public void affCommandes(){
		chargerCommandes();

		for(Commande commande : commandes){
			System.out.println(commande);
		}
	}

	public void assignerCommande(int numero, Membre membre){
		chargerCommandes();

		Commande commande = getCommande(numero);
		CommandeAssignee commandeAssignee = new CommandeAssignee(commande, membre, new Date(), false, new Date(0), false);
		CreateurBase.ajouterCommandeAssignee(commandeAssignee);
		chargerCommandes();
	}
}
