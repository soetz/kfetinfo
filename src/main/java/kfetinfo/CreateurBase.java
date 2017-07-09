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

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.util.List;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.UUID;

public class CreateurBase {
	static String path = new File("").getAbsolutePath();
	static SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

	public static void initialiserBase(){
		try {
			Files.createDirectories(Paths.get(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Ingrédients"));
			Files.createDirectories(Paths.get(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Sauces"));
			Files.createDirectories(Paths.get(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Desserts"));
			Files.createDirectories(Paths.get(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Boissons"));
			Files.createDirectories(Paths.get(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Plats"));
			Files.createDirectories(Paths.get(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Suppléments Boisson"));
			Files.createDirectories(Paths.get(path + "\\src\\main\\resources\\Base de Données\\Membres"));
			Files.createDirectories(Paths.get(path + "\\src\\main\\resources\\Base de Données\\Services"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		ajouterRiens();
	}

	private static JSONObject creer() {
		JSONObject objet = new JSONObject();
		UUID id = UUID.randomUUID();
		objet.put("id", id.toString());
		return(objet);
	}

	private static JSONObject creerContenuCommande(String nom, float cout, int priorite) {
		JSONObject contenu = creer();
		contenu.put("nom", nom);
		contenu.put("cout", cout);
		contenu.put("estDisponible", true);
		contenu.put("nbUtilisations", new Integer(0));
		contenu.put("priorite", priorite);
		return(contenu);
	}

	public static void creerIngredient(String nom, float cout, int priorite) {
		JSONObject ingredient = creerContenuCommande(nom, cout, priorite);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Ingrédients\\" + nom.toLowerCase() + ".json")) {

            file.write(ingredient.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println(ingredient);
	}

	public static void creerSauce(String nom, float cout, int priorite) {
		JSONObject sauce = creerContenuCommande(nom, cout, priorite);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Sauces\\" + nom.toLowerCase() + ".json")) {

            file.write(sauce.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println(sauce);
	}

	public static void creerDessert(String nom, float cout, int priorite, float prix) {
		JSONObject dessert = creerContenuCommande(nom, cout, priorite);
		dessert.put("prix", prix);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Desserts\\" + nom.toLowerCase() + ".json")) {

            file.write(dessert.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println(dessert);
	}

	public static void creerBoisson(String nom, float cout, int priorite) {
		JSONObject boisson = creerContenuCommande(nom, cout, priorite);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Boissons\\" + nom.toLowerCase() + ".json")) {

            file.write(boisson.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println(boisson);
	}

	public static void creerPlat(String nom, float cout, int priorite, float prix, int nbMaxIngredients, int nbMaxSauces) {
		JSONObject plat = creerContenuCommande(nom, cout, priorite);
		plat.put("prix", prix);
		plat.put("nbMaxIngredients", nbMaxIngredients);
		plat.put("nbMaxSauces", nbMaxSauces);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Plats\\" + nom.toLowerCase() + ".json")) {

            file.write(plat.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println(plat);
	}

	public static void creerSupplementBoisson(String nom, float cout, int priorite, float prix) {
		JSONObject supplementBoisson = creerContenuCommande(nom, cout, priorite);
		supplementBoisson.put("prix", prix);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Suppléments Boisson\\" + nom.toLowerCase() + ".json")) {

            file.write(supplementBoisson.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println(supplementBoisson);
	}

	public static void creerMembre(String nom, String prenom, String surnom, String poste, Date dateNaissance){
		JSONObject membre = creer();
		membre.put("nom", nom);
		membre.put("prenom", prenom);
		membre.put("surnom", surnom);
		membre.put("poste", poste);
		membre.put("dateNaissance", formatDate.format(dateNaissance));
		membre.put("nbCommandes", new Integer(0));
		membre.put("nbServices", new Integer(0));
		membre.put("tempsMoyenCommande", 0f);
		try (
			FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Membres\\" + prenom.toLowerCase() + " " + nom.toLowerCase() + ".json")) {
            file.write(membre.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println(membre);
	}

	private static JSONObject objetCommande(int numero, Date moment, Plat plat, List<Ingredient> ingredients, List<Sauce> sauces, Dessert dessert, Boisson boisson, SupplementBoisson supplementBoisson, float prix){
		JSONObject commande = new JSONObject();
		commande.put("numero", numero);
		commande.put("moment", moment.getTime());
		commande.put("plat", plat.getId());

		JSONArray listeIngredients = new JSONArray();
		for(Ingredient ingredient : ingredients){
			listeIngredients.add(ingredient.getId());
		}

		commande.put("ingredients", listeIngredients);

		JSONArray listeSauces = new JSONArray();
		for(Sauce sauce : sauces){
			listeSauces.add(sauce.getId());
		}

		commande.put("sauces", listeSauces);
		commande.put("dessert", dessert.getId());
		commande.put("boisson", boisson.getId());
		commande.put("supplementBoisson", supplementBoisson.getId());

		return(commande);
	}

	private static void ecrireCommande(int numero, Date moment, JSONObject commande){
		SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		SimpleDateFormat mois = new SimpleDateFormat("MM");
		SimpleDateFormat jour = new SimpleDateFormat("dd");

		String zeros = "";

		if(numero < 10){
			zeros += "0";
			if(numero < 100){
				zeros += "0";
			}
		}

		try {
				Files.createDirectories(Paths.get(path + "\\src\\main\\resources\\Base de Données\\Services\\" + annee.format(moment) + "\\" + mois.format(moment) + "\\" + jour.format(moment) + "\\"));
		} catch (Exception e){
			e.printStackTrace();
		}

		try (
				FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Services\\" + annee.format(moment) + "\\" + mois.format(moment) + "\\" + jour.format(moment) + "\\" + zeros + numero + ".json")) {
	            file.write(commande.toJSONString());
	            file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(commande);
	}

	public static void ajouterCommande(int numero, Date moment, Plat plat, List<Ingredient> ingredients, List<Sauce> sauces, Dessert dessert, Boisson boisson, SupplementBoisson supplementBoisson, float prix){
		JSONObject commande = objetCommande(numero, moment, plat, ingredients, sauces, dessert, boisson, supplementBoisson, prix);

		commande.put("assignee", false);

		ecrireCommande(numero, moment, commande);
	}

	public static void ajouterCommande(Commande commande){
		ajouterCommande(commande.getNumero(), commande.getMoment(), commande.getPlat(), commande.getIngredients(), commande.getSauces(), commande.getDessert(), commande.getBoisson(), commande.getSupplementBoisson(), commande.getPrix());
	}

	public static void ajouterCommandeAssignee(int numero, Date moment, Plat plat, List<Ingredient> ingredients, List<Sauce> sauces, Dessert dessert, Boisson boisson, SupplementBoisson supplementBoisson, float prix, Membre membre, Date momentAssignation, boolean estRealisee, Date momentRealisation, boolean estDonnee){
		JSONObject commandeAssignee = objetCommande(numero, moment, plat, ingredients, sauces, dessert, boisson, supplementBoisson, prix);

		commandeAssignee.put("assignee", true);
		commandeAssignee.put("membre", membre.getId());
		commandeAssignee.put("momentAssignation", momentAssignation.getTime());
		commandeAssignee.put("estRealisee", estRealisee);
		commandeAssignee.put("momentRealisation", momentRealisation.getTime());
		commandeAssignee.put("estDonnee", estDonnee);

		ecrireCommande(numero, moment, commandeAssignee);
	}

	public static void ajouterCommandeAssignee(CommandeAssignee commandeAssignee){
		ajouterCommandeAssignee(commandeAssignee.getNumero(), commandeAssignee.getMoment(), commandeAssignee.getPlat(), commandeAssignee.getIngredients(), commandeAssignee.getSauces(), commandeAssignee.getDessert(), commandeAssignee.getBoisson(), commandeAssignee.getSupplementBoisson(), commandeAssignee.getPrix(), commandeAssignee.getMembre(), commandeAssignee.getMomentAssignation(), commandeAssignee.getEstRealisee(), commandeAssignee.getMomentRealisation(), commandeAssignee.getEstDonnee());
	}

	public static void ajouterRiens(){
		File f = new File(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Ingrédients\\" + "rien" + ".json");
		if(!(f.exists() && !f.isDirectory())) { 
			JSONObject rienIngredient = new JSONObject();
			rienIngredient.put("id", "e8a6d3a2-7e0b-4587-ac85-462329b4a776");
			rienIngredient.put("nom", "Rien");
			rienIngredient.put("cout", 0f);
			rienIngredient.put("estDisponible", true);
			rienIngredient.put("nbUtilisations", new Integer(0));
			rienIngredient.put("priorite", -1);
			try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Ingrédients\\" + "rien" + ".json")) {

	            file.write(rienIngredient.toJSONString());
	            file.flush();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        System.out.println(rienIngredient);
		}

		f = new File(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Sauces\\" + "rien" + ".json");
		if(!(f.exists() && !f.isDirectory())) { 
			JSONObject rienSauce = new JSONObject();
			rienSauce.put("id", "dc9e18ea-ff8c-4d71-8d11-ed18489df6a1");
			rienSauce.put("nom", "Rien");
			rienSauce.put("cout", 0f);
			rienSauce.put("estDisponible", true);
			rienSauce.put("nbUtilisations", new Integer(0));
			rienSauce.put("priorite", -1);
			try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Sauces\\" + "rien" + ".json")) {

	            file.write(rienSauce.toJSONString());
	            file.flush();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        System.out.println(rienSauce);
		}

        f = new File(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Desserts\\" + "rien" + ".json");
        if(!(f.exists() && !f.isDirectory())) { 
    		JSONObject rienDessert = new JSONObject();
    		rienDessert.put("id", "962e1223-cdda-47ef-85ab-20eede2a0dc0");
    		rienDessert.put("nom", "Rien");
    		rienDessert.put("cout", 0f);
    		rienDessert.put("estDisponible", true);
    		rienDessert.put("nbUtilisations", new Integer(0));
    		rienDessert.put("priorite", -1);
    		rienDessert.put("prix", 0f);
    		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Desserts\\" + "rien" + ".json")) {

                file.write(rienDessert.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(rienDessert);
        }



        f = new File(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Boissons\\" + "rien" + ".json");
        if(!(f.exists() && !f.isDirectory())) { 
        	JSONObject rienBoisson = new JSONObject();
    		rienBoisson.put("id", "c1d0b7e7-b9f8-4d2f-8c3d-7a0edcc413fe");
    		rienBoisson.put("nom", "Rien");
    		rienBoisson.put("cout", 0f);
    		rienBoisson.put("estDisponible", true);
    		rienBoisson.put("nbUtilisations", new Integer(0));
    		rienBoisson.put("priorite", -1);
    		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Boissons\\" + "rien" + ".json")) {

                file.write(rienBoisson.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(rienBoisson);
        }

        f = new File(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Plats\\" + "rien" + ".json");
        if(!(f.exists() && !f.isDirectory())) { 
        	JSONObject rienPlat = new JSONObject();
    		rienPlat.put("id", "ff56da46-bddd-4e4f-a871-6fa03b0e814b");
    		rienPlat.put("nom", "Rien");
    		rienPlat.put("cout", 0f);
    		rienPlat.put("estDisponible", true);
    		rienPlat.put("nbUtilisations", new Integer(0));
    		rienPlat.put("priorite", -1);
    		rienPlat.put("prix", 0f);
    		rienPlat.put("nbMaxIngredients", 0);
    		rienPlat.put("nbMaxSauces", 0);
    		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Plats\\" + "rien" + ".json")) {

                file.write(rienPlat.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(rienPlat);
        }		
		
        f = new File(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Suppléments Boisson\\" + "rien" + ".json");
        if(!(f.exists() && !f.isDirectory())) { 
        	JSONObject rienSupplementBoisson = new JSONObject();
    		rienSupplementBoisson.put("id", "fa03180b-95ad-4a5b-84f2-cbdc2beae920");
    		rienSupplementBoisson.put("nom", "Rien");
    		rienSupplementBoisson.put("cout", 0f);
    		rienSupplementBoisson.put("estDisponible", true);
    		rienSupplementBoisson.put("nbUtilisations", new Integer(0));
    		rienSupplementBoisson.put("priorite", -1);
    		rienSupplementBoisson.put("prix", 0f);
    		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Suppléments Boisson\\" + "rien" + ".json")) {

                file.write(rienSupplementBoisson.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(rienSupplementBoisson);
        }
        
	}

}
