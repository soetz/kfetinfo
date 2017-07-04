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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.IOException;

import java.util.UUID;

import org.apache.commons.text.WordUtils;

public class CreateurBase {
	static String path = new File("").getAbsolutePath();

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
		contenu.put("nom", WordUtils.capitalize(nom));
		contenu.put("cout", cout);
		contenu.put("estDisponible", true);
		contenu.put("nbUtilisations", new Integer(0));
		contenu.put("priorite", priorite);
		return(contenu);
	}

	public static void creerIngredient(String nom, float cout, int priorite) {
		JSONObject ingredient = creerContenuCommande(nom, cout, priorite);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Ingrédients\\" + nom + ".json")) {

            file.write(ingredient.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(ingredient);
	}

	public static void creerSauce(String nom, float cout, int priorite) {
		JSONObject sauce = creerContenuCommande(nom, cout, priorite);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Sauces\\" + nom + ".json")) {

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
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Desserts\\" + nom + ".json")) {

            file.write(dessert.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(dessert);
	}

	public static void creerBoisson(String nom, float cout, int priorite) {
		JSONObject boisson = creerContenuCommande(nom, cout, priorite);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Boissons\\" + nom + ".json")) {

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
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Plats\\" + nom + ".json")) {

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
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Suppléments Boisson\\" + nom + ".json")) {

            file.write(supplementBoisson.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(supplementBoisson);
	}

	public static void ajouterRiens(){
		File f = new File(path + "\\src\\main\\resources\\Base de Données\\Contenus Commandes\\Ingrédients\\" + "rien" + ".json");
		if(!(f.exists() && !f.isDirectory())) { 
			JSONObject rienIngredient = new JSONObject();
			rienIngredient.put("id", "e8a6d3a2-7e0b-4587-ac85-462329b4a776");
			rienIngredient.put("nom", "rien");
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
			rienSauce.put("nom", "rien");
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
    		rienDessert.put("nom", "rien");
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
    		rienBoisson.put("nom", "rien");
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
    		rienPlat.put("nom", "rien");
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
    		rienSupplementBoisson.put("nom", "rien");
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