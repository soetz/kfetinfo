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

import java.util.List;
import java.util.ArrayList;

import java.util.Collections;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

public class BaseDonnees {
	static String path = new File("").getAbsolutePath();

	List<Ingredient> ingredients;
	List<Sauce> sauces;
	List<Dessert> desserts;
	List<Boisson> boissons;
	List<Plat> plats;
	List<SupplementBoisson> supplementsBoisson;
	List<Membre> membres;

	public BaseDonnees(){
		ingredients = new ArrayList();
		sauces = new ArrayList();
		desserts = new ArrayList();
		boissons = new ArrayList();
		plats = new ArrayList();
		supplementsBoisson = new ArrayList();
		membres = new ArrayList();
	}

	public void affIngredients(){
		for(Ingredient ingredient : ingredients){
			System.out.println(ingredient.getNom());
		}
	}

	public void chargerIngredients(){
		File dossierIngredients = new File(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Ingrédients\\");
		File[] listOfFiles = dossierIngredients.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()&&(FilenameUtils.getExtension(listOfFiles[i].getName()).equals("json"))) {
			    Ingredient ingredient = LecteurBase.lireIngredient(FilenameUtils.removeExtension(listOfFiles[i].getName()));
	    		ingredients.add(ingredient);
	    	}
		}

		Collections.sort(ingredients, new CompareContenuCommande());
		Collections.reverse(ingredients);
	}

	public void chargerSauces(){
		File dossierSauces = new File(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Sauces\\");
		File[] listOfFiles = dossierSauces.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()&&(FilenameUtils.getExtension(listOfFiles[i].getName()).equals("json"))) {
			    Sauce sauce = LecteurBase.lireSauce(FilenameUtils.removeExtension(listOfFiles[i].getName()));
	    		sauces.add(sauce);
	    	}
		}

		Collections.sort(sauces, new CompareContenuCommande());
		Collections.reverse(sauces);
	}

	public void chargerDesserts(){
		File dossierDesserts = new File(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Desserts\\");
		File[] listOfFiles = dossierDesserts.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()&&(FilenameUtils.getExtension(listOfFiles[i].getName()).equals("json"))) {
			    Dessert dessert = LecteurBase.lireDessert(FilenameUtils.removeExtension(listOfFiles[i].getName()));
	    		desserts.add(dessert);
	    	}
		}

		Collections.sort(desserts, new CompareContenuCommande());
		Collections.reverse(desserts);
	}

	public void chargerBoissons(){
		File dossierBoissons = new File(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Boissons\\");
		File[] listOfFiles = dossierBoissons.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()&&(FilenameUtils.getExtension(listOfFiles[i].getName()).equals("json"))) {
			    Boisson boisson = LecteurBase.lireBoisson(FilenameUtils.removeExtension(listOfFiles[i].getName()));
	    		boissons.add(boisson);
	    	}
		}

		Collections.sort(boissons, new CompareContenuCommande());
		Collections.reverse(boissons);
	}

	public void chargerPlats(){
		File dossierPlats = new File(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Plats\\");
		File[] listOfFiles = dossierPlats.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()&&(FilenameUtils.getExtension(listOfFiles[i].getName()).equals("json"))) {
			    Plat plat = LecteurBase.lirePlat(FilenameUtils.removeExtension(listOfFiles[i].getName()));
	    		plats.add(plat);
	    	}
		}

		Collections.sort(plats, new CompareContenuCommande());
		Collections.reverse(plats);
	}

	public void chargerSupplementsBoisson(){
		File dossierSupplementsBoisson = new File(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Suppléments Boisson\\");
		File[] listOfFiles = dossierSupplementsBoisson.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()&&(FilenameUtils.getExtension(listOfFiles[i].getName()).equals("json"))) {
				SupplementBoisson supplementBoisson = LecteurBase.lireSupplementBoisson(FilenameUtils.removeExtension(listOfFiles[i].getName()));
				supplementsBoisson.add(supplementBoisson);
	    	}
		}

		Collections.sort(supplementsBoisson, new CompareContenuCommande());
		Collections.reverse(supplementsBoisson);
	}

	public void chargerMembres(){
		File dossierMembres = new File(path + "\\src\\main\\resources\\Base de Données\\Membres\\");
		File[] listOfFiles = dossierMembres.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()&&(FilenameUtils.getExtension(listOfFiles[i].getName()).equals("json"))) {
			    Membre membre = LecteurBase.lireMembre(FilenameUtils.removeExtension(listOfFiles[i].getName()));
	    		membres.add(membre);
	    	}
		}

		Collections.sort(membres, new CompareMembre());
		Collections.reverse(membres);
	}
}
