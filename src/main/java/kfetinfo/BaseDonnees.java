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
import java.util.Date;
import java.io.File;

import org.apache.commons.io.FilenameUtils;

public class BaseDonnees {
	static String path = new File("").getAbsolutePath();

	static List<Ingredient> ingredients;
	static List<Sauce> sauces;
	static List<Dessert> desserts;
	static List<Boisson> boissons;
	static List<Plat> plats;
	static List<SupplementBoisson> supplementsBoisson;
	static List<Membre> membres;

	static public Ingredient getRienIngredient(){
		Ingredient ingredient = new Ingredient("e8a6d3a2-7e0b-4587-ac85-462329b4a776", "Rien", 0, true, 0, -1);
		for(Ingredient ingredientListe : ingredients){
			if(ingredientListe.id.equals("e8a6d3a2-7e0b-4587-ac85-462329b4a776")){
				ingredient = ingredientListe;
			}
		}

		return(ingredient);
	}

	static public Sauce getRienSauce(){
		Sauce sauce = new Sauce("dc9e18ea-ff8c-4d71-8d11-ed18489df6a1", "Rien", 0, true, 0, -1);
		for(Sauce sauceListe : sauces){
			if(sauceListe.getId().equals("dc9e18ea-ff8c-4d71-8d11-ed18489df6a1")){
				sauce = sauceListe;
			}
		}

		return(sauce);
	}

	static public Dessert getRienDessert(){
		Dessert dessert = new Dessert("962e1223-cdda-47ef-85ab-20eede2a0dc0", "Rien", 0, true, 0, -1, 0);
		for(Dessert dessertListe : desserts){
			if(dessertListe.getId().equals("962e1223-cdda-47ef-85ab-20eede2a0dc0")){
				dessert = dessertListe;
			}
		}

		return(dessert);
	}

	static public Boisson getRienBoisson(){
		Boisson boisson = new Boisson("c1d0b7e7-b9f8-4d2f-8c3d-7a0edcc413fe", "Rien", 0, true, 0, -1);
		for(Boisson boissonListe : boissons){
			if(boissonListe.getId().equals("c1d0b7e7-b9f8-4d2f-8c3d-7a0edcc413fe")){
				boisson = boissonListe;
			}
		}

		return(boisson);
	}

	static public Plat getRienPlat(){
		Plat plat = new Plat("ff56da46-bddd-4e4f-a871-6fa03b0e814b", "Rien", 0, true, 0, -1, 0, 0, false, 0);
		for(Plat platListe : plats){
			if(platListe.getId().equals("ff56da46-bddd-4e4f-a871-6fa03b0e814b")){
				plat = platListe;
			}
		}

		return(plat);
	}

	static public SupplementBoisson getRienSupplementBoisson(){
		SupplementBoisson supplementBoisson = new SupplementBoisson("fa03180b-95ad-4a5b-84f2-cbdc2beae920", "Rien", 0, true, 0, -1, 0);
		for(SupplementBoisson supplementBoissonListe : supplementsBoisson){
			if(supplementBoissonListe.getId().equals("fa03180b-95ad-4a5b-84f2-cbdc2beae920")){
				supplementBoisson = supplementBoissonListe;
			}
		}

		return(supplementBoisson);
	}

	static public Ingredient getIngredient(String id){
		chargerMenu();

		Ingredient ingredient = getRienIngredient();
		for(Ingredient ingredientListe : ingredients){
			if(ingredientListe.getId().equals(id)){
				ingredient = ingredientListe;
			}
		}

		return(ingredient);
	}

	static public Ingredient getIngredientNom(String nom){
		chargerMenu();

		Ingredient ingredient = getRienIngredient();
		for(Ingredient ingredientListe : ingredients){
			if(ingredientListe.getNom().toLowerCase().equals(nom.toLowerCase())){
				ingredient = ingredientListe;
			}
		}

		return(ingredient);
	}

	static public Sauce getSauce(String id){
		chargerMenu();

		Sauce sauce = getRienSauce();
		for(Sauce sauceListe : sauces){
			if(sauceListe.getId().equals(id)){
				sauce = sauceListe;
			}
		}

		return(sauce);
	}

	static public Sauce getSauceNom(String nom){
		chargerMenu();

		Sauce sauce = getRienSauce();
		for(Sauce sauceListe : sauces){
			if(sauceListe.getNom().toLowerCase().equals(nom.toLowerCase())){
				sauce = sauceListe;
			}
		}

		return(sauce);
	}

	static public Dessert getDessert(String id){
		chargerMenu();

		Dessert dessert = getRienDessert();
		for(Dessert dessertListe : desserts){
			if(dessertListe.getId().equals(id)){
				dessert = dessertListe;
			}
		}

		return(dessert);
	}

	static public Dessert getDessertNom(String nom){
		chargerMenu();

		Dessert dessert = getRienDessert();
		for(Dessert dessertListe : desserts){
			if(dessertListe.getNom().toLowerCase().equals(nom.toLowerCase())){
				dessert = dessertListe;
			}
		}

		return(dessert);
	}

	static public Boisson getBoisson(String id){
		chargerMenu();

		Boisson boisson = getRienBoisson();
		for(Boisson boissonListe : boissons){
			if(boissonListe.getId().equals(id)){
				boisson = boissonListe;
			}
		}

		return(boisson);
	}

	static public Boisson getBoissonNom(String nom){
		chargerMenu();

		Boisson boisson = getRienBoisson();
		for(Boisson boissonListe : boissons){
			if(boissonListe.getNom().toLowerCase().equals(nom.toLowerCase())){
				boisson = boissonListe;
			}
		}

		return(boisson);
	}

	static public Plat getPlat(String id){
		chargerMenu();

		Plat plat = getRienPlat();
		for(Plat platListe : plats){
			if(platListe.getId().equals(id)){
				plat = platListe;
			}
		}

		return(plat);
	}

	static public Plat getPlatNom(String nom){
		chargerMenu();

		Plat plat = getRienPlat();
		for(Plat platListe : plats){
			if(platListe.getNom().toLowerCase().equals(nom.toLowerCase())){
				plat = platListe;
			}
		}

		return(plat);
	}

	static public SupplementBoisson getSupplementBoisson(String id){
		chargerMenu();

		SupplementBoisson supplementBoisson = getRienSupplementBoisson();
		for(SupplementBoisson supplementBoissonListe : supplementsBoisson){
			if(supplementBoissonListe.getId().equals(id)){
				supplementBoisson = supplementBoissonListe;
			}
		}

		return(supplementBoisson);
	}

	static public SupplementBoisson getSupplementBoissonNom(String nom){
		chargerMenu();

		SupplementBoisson supplementBoisson = getRienSupplementBoisson();
		for(SupplementBoisson supplementBoissonListe : supplementsBoisson){
			if(supplementBoissonListe.getNom().toLowerCase().equals(nom.toLowerCase())){
				supplementBoisson = supplementBoissonListe;
			}
		}

		return(supplementBoisson);
	}

	static public Membre getMembre(String id){
		chargerMembres();

		Membre membre = new Membre("f38aa97b-2c4b-491e-be10-884e48fbb6c2", "", "", "", "", new Date(0), 0, 0, 0);
		for(Membre membreListe : membres){
			if(membreListe.getId().equals(id)){
				membre = membreListe;
			}
		}

		return(membre);
	}

	static public Membre getMembreNomPrenom(String nom, String prenom){
		chargerMembres();

		Membre membre = new Membre("f38aa97b-2c4b-491e-be10-884e48fbb6c2", "", "", "", "", new Date(0), 0, 0, 0);
		for(Membre membreListe : membres){
			if((membreListe.getNom().toLowerCase().equals(nom.toLowerCase()))&&(membreListe.getPrenom().toLowerCase().equals(prenom.toLowerCase()))){
				membre = membreListe;
			}
		}

		return(membre);
	}

	static public void chargerMenu(){
		chargerIngredients();
		chargerSauces();
		chargerDesserts();
		chargerBoissons();
		chargerPlats();
		chargerSupplementsBoisson();
	}

	static public void affMenu(){
		chargerMenu();

		for(Ingredient ingredient : ingredients){
			System.out.println(ingredient.getNom());
		}

		System.out.println("");

		for(Sauce sauce: sauces){
			System.out.println(sauce.getNom());
		}

		System.out.println("");

		for(Dessert dessert: desserts){
			System.out.println(dessert.getNom());
		}

		System.out.println("");

		for(Boisson boisson: boissons){
			System.out.println(boisson.getNom());
		}

		System.out.println("");

		for(Plat plat: plats){
			System.out.println(plat.getNom());
		}

		System.out.println("");

		for(SupplementBoisson supplementBoisson: supplementsBoisson){
			System.out.println(supplementBoisson.getNom());
		}
	}

	static private void chargerIngredients(){
		ingredients = new ArrayList<Ingredient>();

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

	static private void chargerSauces(){
		sauces = new ArrayList<Sauce>();

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

	static private void chargerDesserts(){
		desserts = new ArrayList<Dessert>();

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

	static private void chargerBoissons(){
		boissons = new ArrayList<Boisson>();

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

	static private void chargerPlats(){
		plats = new ArrayList<Plat>();

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

	static private void chargerSupplementsBoisson(){
		supplementsBoisson = new ArrayList<SupplementBoisson>();

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

	static public void chargerMembres(){
		membres = new ArrayList<Membre>();

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
