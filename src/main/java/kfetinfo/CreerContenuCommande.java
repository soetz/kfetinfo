package kfetinfo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.UUID;

import org.apache.commons.text.WordUtils;

public class CreerContenuCommande {
	static String path = new File("").getAbsolutePath();

	public static JSONObject creer(String nom, float cout, int priorite) {
		JSONObject contenu = new JSONObject();
		UUID id = UUID.randomUUID();
		contenu.put("id", id.toString());
		contenu.put("nom", WordUtils.capitalize(nom));
		contenu.put("cout", cout);
		contenu.put("estDisponible", true);
		contenu.put("nbUtilisations", new Integer(0));
		contenu.put("priorite", priorite);
		return(contenu);
	}

	public static void creerIngredient(String nom, float cout, int priorite) {
		JSONObject ingredient = creer(nom, cout, priorite);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Ingrédients\\" + nom + ".json")) {

            file.write(ingredient.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(ingredient);
	}

	public static void creerSauce(String nom, float cout, int priorite) {
		JSONObject sauce = creer(nom, cout, priorite);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Sauces\\" + nom + ".json")) {

            file.write(sauce.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(sauce);
	}

	public static void creerDessert(String nom, float cout, int priorite, float prix) {
		JSONObject dessert = creer(nom, cout, priorite);
		dessert.put("prix", prix);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Desserts\\" + nom + ".json")) {

            file.write(dessert.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(dessert);
	}

	public static void creerBoisson(String nom, float cout, int priorite) {
		JSONObject boisson = creer(nom, cout, priorite);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Boissons\\" + nom + ".json")) {

            file.write(boisson.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(boisson);
	}

	public static void creerPlat(String nom, float cout, int priorite, float prix, int nbMaxIngredients, int nbMaxSauces) {
		JSONObject plat = creer(nom, cout, priorite);
		plat.put("prix", prix);
		plat.put("nbMaxIngredients", nbMaxIngredients);
		plat.put("nbMaxSauces", nbMaxSauces);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Plats\\" + nom + ".json")) {

            file.write(plat.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(plat);
	}

	public static void creerSupplementBoisson(String nom, float cout, int priorite, float prix) {
		JSONObject supplementBoisson = creer(nom, cout, priorite);
		supplementBoisson.put("prix", prix);
		try (FileWriter file = new FileWriter(path + "\\src\\main\\resources\\Base de Données\\Suppléments Boisson\\" + nom + ".json")) {

            file.write(supplementBoisson.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(supplementBoisson);
	}

}
