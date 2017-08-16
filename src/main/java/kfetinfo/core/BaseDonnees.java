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

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.io.File;

import org.apache.commons.io.FilenameUtils;

/**
 * <p>BaseDonnees est une classe constituée uniquement d'attributs et de méthodes statiques relatifs à la gestion de la base de données.</p>
 * <p>Elle charge les contenus commandes et les membres depuis la classe LecteurBase et les stocke.</p>
 * <p>Elle permet d'accéder aux listes de contenus commandes ou de membres mais aussi à des contenus commandes ou membres particuliers.</p>
 * <p>Elle est munie de méthodes utiles pour le développement telles que {@code affMenu()} et {@code affMembres()}.</p>
 * 
 * <p>Utilisation :
 * <ul><li>{@code chargerMenu()} et {@code chargerMembres()} vont chercher dans les fichiers de la base de données et mettent à jour les listes de contenus commandes et de membres. En principe ces méthodes sont appelées à chaque modification de la base, mais aussi parfois lorsqu'on veut accéder à la base au cas où un autre programme ait modifié la base.</li>
 * <li>{@code get*NomDeClasseAuPluriel*()} retourne la liste des éléments en mémoire correspondant à cette classe.</li>
 * <li>{@code get*NomDeClasse*(String id)} retourne un élément particulier identifié par son attribut {@code id}.</li>
 * <li>{@code get*NomDeClasse*Nom(String nom)} idem mais avec le {@code nom} de l'élément. Normalement jamais appelé par le logiciel mais utile pour le développement.</li>
 * <li>{@code getRien*NomDeClasse*()} retourne le <i>rien</i> d'un contenu commande.</li></ul></p>
 * 
 * <p>Elle ne gère pas les {@code Commande}, c'est la classe {@code Service} qui s'en charge (une commande étant relative à un service).</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class BaseDonnees {

	//listes des éléments
	private static List<Ingredient> ingredients;
	private static List<Sauce> sauces;
	private static List<Dessert> desserts;
	private static List<Boisson> boissons;
	private static List<Plat> plats;
	private static List<SupplementBoisson> supplementsBoisson;
	private static List<Membre> membres;

	//ids des contenus commande correspondant à rien
	public static final String ID_RIEN_PLAT = "ff56da46-bddd-4e4f-a871-6fa03b0e814b";
	public static final String ID_RIEN_BOISSON = "c1d0b7e7-b9f8-4d2f-8c3d-7a0edcc413fe";
	public static final String ID_RIEN_SUPPLEMENT_BOISSON = "fa03180b-95ad-4a5b-84f2-cbdc2beae920";
	public static final String ID_RIEN_DESSERT = "962e1223-cdda-47ef-85ab-20eede2a0dc0";

	/**
	 * Renvoie la liste des {@code Plat}.
	 * 
	 * @return Une {@code List<Plat>} contenant la liste des plats de la base de données.
	 * 
	 * @see Plat
	 */
	static public List<Plat> getPlats(){

		return(plats);
	}

	/**
	 * Renvoie la liste des {@code Ingredient}.
	 * 
	 * @return Une {@code List<Ingredient>} contenant la liste des ingrédients de la base de données.
	 * 
	 * @see Ingredient
	 */
	static public List<Ingredient> getIngredients(){

		return(ingredients);
	}

	/**
	 * Renvoie la liste des {@code Sauce}.
	 * 
	 * @return Une {@code List<Sauce>} contenant la liste des sauces de la base de données.
	 * 
	 * @see Sauce
	 */
	static public List<Sauce> getSauces(){

		return(sauces);
	}

	/**
	 * Renvoie la liste des {@code Boisson}.
	 * 
	 * @return Une {@code List<Boisson>} contenant la liste des boissons de la base de données.
	 * 
	 * @see Boisson
	 */
	static public List<Boisson> getBoissons(){

		return(boissons);
	}

	/**
	 * Renvoie la liste des {@code SupplementBoisson}.
	 * 
	 * @return Une {@code List<SupplementBoisson>} contenant la liste des suppléments boisson de la base de données.
	 * 
	 * @see SupplementBoisson
	 */
	static public List<SupplementBoisson> getSupplementsBoisson(){

		return(supplementsBoisson);
	}

	/**
	 * Renvoie la liste des {@code Dessert}.
	 * 
	 * @return Une {@code List<Dessert>} contenant la liste des desserts de la base de données.
	 * 
	 * @see Dessert
	 */
	static public List<Dessert> getDesserts(){

		return(desserts);
	}

	/**
	 * Renvoie la liste des {@code Membre}.
	 * 
	 * @return Une {@code List<Membre>} contenant la liste des membres de la base de données.
	 * 
	 * @see Membre
	 */
	static public List<Membre> getMembres(){

		return(membres);
	}

	/**
	 * Renvoie l'élément <i>rien</i> de type {@code Plat}. C'est à dire l'élément qui est utilisé pour indiquer que le client n'a choisi aucun plat. Son {@code id} est fixé et il a une {@code priorite} de {@code -1}.
	 * 
	 * @return Un {@code Plat} correspondant au fait que l'utilisateur n'a pas séléctionné de plat.
	 */
	static public Plat getRienPlat(){

		Plat plat = new Plat(ID_RIEN_PLAT, "Rien", 0, true, 0, -1, 0, 0, false, 0); //élément par défaut

		for(Plat platListe : plats){
			if(platListe.getId().equals(ID_RIEN_PLAT)){ //si on trouve le rien dans la liste des éléments c'est quand même mieux de choisir celui-ci
				plat = platListe;
			}
		}

		return(plat);
	}

	/**
	 * Renvoie l'élément <i>rien</i> de type {@code Boisson}. C'est à dire l'élément qui est utilisé pour indiquer que le client n'a choisi aucune boisson. Son {@code id} est fixé et il a une {@code priorite} de {@code -1}.
	 * 
	 * @return Une {@code Boisson} correspondant au fait que l'utilisateur n'a pas séléctionné de boisson.
	 */
	static public Boisson getRienBoisson(){

		Boisson boisson = new Boisson(ID_RIEN_BOISSON, "Rien", 0, true, 0, -1); //élément par défaut

		for(Boisson boissonListe : boissons){
			if(boissonListe.getId().equals(ID_RIEN_BOISSON)){ //si on trouve le rien dans la liste des éléments c'est quand même mieux de choisir celui-ci
				boisson = boissonListe;
			}
		}

		return(boisson);
	}

	/**
	 * Renvoie l'élément <i>rien</i> de type {@code SupplementBoisson}. C'est à dire l'élément qui est utilisé pour indiquer que le client n'a choisi aucun supplément boisson. Son {@code id} est fixé et il a une {@code priorite} de {@code -1}.
	 * 
	 * @return Un {@code SupplementBoisson} correspondant au fait que l'utilisateur n'a pas séléctionné de supplément boisson.
	 */
	static public SupplementBoisson getRienSupplementBoisson(){

		SupplementBoisson supplementBoisson = new SupplementBoisson(ID_RIEN_SUPPLEMENT_BOISSON, "Rien", 0, true, 0, -1, 0); //élément par défaut

		for(SupplementBoisson supplementBoissonListe : supplementsBoisson){
			if(supplementBoissonListe.getId().equals(ID_RIEN_SUPPLEMENT_BOISSON)){ //si on trouve le rien dans la liste des éléments c'est quand même mieux de choisir celui-ci
				supplementBoisson = supplementBoissonListe;
			}
		}

		return(supplementBoisson);
	}

	/**
	 * Renvoie l'élément <i>rien</i> de type {@code Dessert}. C'est à dire l'élément qui est utilisé pour indiquer que le client n'a choisi aucun dessert. Son {@code id} est fixé et il a une {@code priorite} de {@code -1}.
	 * 
	 * @return Un {@code Dessert} correspondant au fait que l'utilisateur n'a pas séléctionné de dessert.
	 */
	static public Dessert getRienDessert(){

		Dessert dessert = new Dessert(ID_RIEN_DESSERT, "Rien", 0, true, 0, -1, 0); //élément par défaut

		for(Dessert dessertListe : desserts){
			if(dessertListe.getId().equals(ID_RIEN_DESSERT)){ //si on trouve le rien dans la liste des éléments c'est quand même mieux de choisir celui-ci
				dessert = dessertListe;
			}
		}

		return(dessert);
	}

	/**
	 * Renvoie le {@code Plat} ayant l'{@code id} passé en paramètres dans la base de données.
	 * 
	 * @param id l'{@code id} du plat à aller chercher.
	 * 
	 * @return Le {@code Plat} correspondant.
	 */
	static public Plat getPlat(String id){

		chargerMenu(); //on recharge la liste des éléments

		Plat plat = getRienPlat(); //élément par défaut (si on ne trouve aucun élément avec l'id passé en paramètres)
		for(Plat platListe : plats){ //pour chaque élément de la liste
			if(platListe.getId().equals(id)){ //si l'id correspond l'élément par défaut est remplacé
				plat = platListe;
			}
		}

		return(plat);
	}

	/**
	 * Renvoie le dernier {@code Plat} ayant le {@code nom} passé en paramètres dans la base de données.
	 * 
	 * @param nom le {@code nom} du plat à aller chercher.
	 * 
	 * @return Le {@code Plat} correspondant.
	 */
	static public Plat getPlatNom(String nom){

		chargerMenu(); //on recharge la liste des éléments

		Plat plat = getRienPlat(); //élément par défaut (si on ne trouve aucun élément avec le nom passé en paramètres)
		for(Plat platListe : plats){ //pour chaque élément de la liste
			if(platListe.getNom().toLowerCase().equals(nom.toLowerCase())){ //si le nom correspond l'élément par défaut est remplacé
				plat = platListe;
			}
		}

		return(plat);
	}

	/**
	 * Renvoie l'{@code Ingredient} ayant l'{@code id} passé en paramètres dans la base de données.
	 * 
	 * @param id l'{@code id} de l'ingrédient à aller chercher.
	 * 
	 * @return L'{@code Ingredient} correspondant.
	 */
	static public Ingredient getIngredient(String id){

		chargerMenu(); //on recharge la liste des éléments

		Ingredient ingredient = new Ingredient(); //élément par défaut (si on ne trouve aucun élément avec l'id passé en paramètres)
		for(Ingredient ingredientListe : ingredients){ //pour chaque élément de la liste
			if(ingredientListe.getId().equals(id)){ //si l'id correspond l'élément par défaut est remplacé
				ingredient = ingredientListe;
			}
		}

		return(ingredient);
	}


	/**
	 * Renvoie le dernier {@code Ingredient} ayant le {@code nom} passé en paramètres dans la base de données.
	 * 
	 * @param nom le {@code nom} de l'ingrédient à aller chercher.
	 * 
	 * @return L'{@code Ingredient} correspondant.
	 */
	static public Ingredient getIngredientNom(String nom){

		chargerMenu(); //on recharge la liste des éléments

		Ingredient ingredient = new Ingredient(); //élément par défaut (si on ne trouve aucun élément avec le nom passé en paramètres)
		for(Ingredient ingredientListe : ingredients){ //pour chaque élément de la liste
			if(ingredientListe.getNom().toLowerCase().equals(nom.toLowerCase())){ //si le nom correspond l'élément par défaut est remplacé
				ingredient = ingredientListe;
			}
		}

		return(ingredient);
	}

	/**
	 * Renvoie la {@code Sauce} ayant l'{@code id} passé en paramètres dans la base de données.
	 * 
	 * @param id l'{@code id} de la sauce à aller chercher.
	 * 
	 * @return La {@code Sauce} correspondante.
	 */
	static public Sauce getSauce(String id){

		chargerMenu(); //on recharge la liste des éléments

		Sauce sauce = new Sauce(); //élément par défaut (si on ne trouve aucun élément avec l'id passé en paramètres)
		for(Sauce sauceListe : sauces){ //pour chaque élément de la liste
			if(sauceListe.getId().equals(id)){ //si l'id correspond l'élément par défaut est remplacé
				sauce = sauceListe;
			}
		}

		return(sauce);
	}

	/**
	 * Renvoie la dernière {@code Sauce} ayant le {@code nom} passé en paramètres dans la base de données.
	 * 
	 * @param nom le {@code nom} de la sauce à aller chercher.
	 * 
	 * @return La {@code Sauce} correspondante.
	 */
	static public Sauce getSauceNom(String nom){

		chargerMenu(); //on recharge la liste des éléments

		Sauce sauce = new Sauce(); //élément par défaut (si on ne trouve aucun élément avec le nom passé en paramètres)
		for(Sauce sauceListe : sauces){ //pour chaque élément de la liste
			if(sauceListe.getNom().toLowerCase().equals(nom.toLowerCase())){ //si le nom correspond l'élément par défaut est remplacé
				sauce = sauceListe;
			}
		}

		return(sauce);
	}

	/**
	 * Renvoie la {@code Boisson} ayant l'{@code id} passé en paramètres dans la base de données.
	 * 
	 * @param id l'{@code id} de la boisson à aller chercher.
	 * 
	 * @return La {@code Boisson} correspondante.
	 */
	static public Boisson getBoisson(String id){

		chargerMenu(); //on recharge la liste des éléments

		Boisson boisson = getRienBoisson(); //élément par défaut (si on ne trouve aucun élément avec l'id passé en paramètres)
		for(Boisson boissonListe : boissons){ //pour chaque élément de la liste
			if(boissonListe.getId().equals(id)){ //si l'id correspond l'élément par défaut est remplacé
				boisson = boissonListe;
			}
		}

		return(boisson);
	}

	/**
	 * Renvoie la dernière {@code Boisson} ayant le {@code nom} passé en paramètres dans la base de données.
	 * 
	 * @param nom le {@code nom} de la boisson à aller chercher.
	 * 
	 * @return La {@code Boisson} correspondante.
	 */
	static public Boisson getBoissonNom(String nom){

		chargerMenu(); //on recharge la liste des éléments

		Boisson boisson = getRienBoisson(); //élément par défaut (si on ne trouve aucun élément avec le nom passé en paramètres)
		for(Boisson boissonListe : boissons){ //pour chaque élément de la liste
			if(boissonListe.getNom().toLowerCase().equals(nom.toLowerCase())){ //si le nom correspond l'élément par défaut est remplacé
				boisson = boissonListe;
			}
		}

		return(boisson);
	}

	/**
	 * Renvoie le {@code SupplementBoisson} ayant l'{@code id} passé en paramètres dans la base de données.
	 * 
	 * @param id l'{@code id} du supplément boisson à aller chercher.
	 * 
	 * @return Le {@code SupplementBoisson} correspondant.
	 */
	static public SupplementBoisson getSupplementBoisson(String id){

		chargerMenu(); //on recharge la liste des éléments

		SupplementBoisson supplementBoisson = getRienSupplementBoisson(); //élément par défaut (si on ne trouve aucun élément avec l'id passé en paramètres)
		for(SupplementBoisson supplementBoissonListe : supplementsBoisson){ //pour chaque élément de la liste
			if(supplementBoissonListe.getId().equals(id)){ //si l'id correspond l'élément par défaut est remplacé
				supplementBoisson = supplementBoissonListe;
			}
		}

		return(supplementBoisson);
	}

	/**
	 * Renvoie le dernier {@code SupplementBoisson} ayant le {@code nom} passé en paramètres dans la base de données.
	 * 
	 * @param nom le {@code nom} du supplément boisson à aller chercher.
	 * 
	 * @return Le {@code SupplementBoisson} correspondant.
	 */
	static public SupplementBoisson getSupplementBoissonNom(String nom){

		chargerMenu(); //on recharge la liste des éléments

		SupplementBoisson supplementBoisson = getRienSupplementBoisson(); //élément par défaut (si on ne trouve aucun élément avec le nom passé en paramètres)
		for(SupplementBoisson supplementBoissonListe : supplementsBoisson){ //pour chaque élément de la liste
			if(supplementBoissonListe.getNom().toLowerCase().equals(nom.toLowerCase())){ //si le nom correspond l'élément par défaut est remplacé
				supplementBoisson = supplementBoissonListe;
			}
		}

		return(supplementBoisson);
	}

	/**
	 * Renvoie le {@code Dessert} ayant l'{@code id} passé en paramètres dans la base de données.
	 * 
	 * @param id l'{@code id} du dessert à aller chercher.
	 * 
	 * @return Le {@code Dessert} correspondant.
	 */
	static public Dessert getDessert(String id){

		chargerMenu(); //on recharge la liste des éléments

		Dessert dessert = getRienDessert(); //élément par défaut (si on ne trouve aucun élément avec l'id passé en paramètres)
		for(Dessert dessertListe : desserts){ //pour chaque élément de la liste
			if(dessertListe.getId().equals(id)){ //si l'id correspond l'élément par défaut est remplacé
				dessert = dessertListe;
			}
		}

		return(dessert);
	}

	/**
	 * Renvoie le dernier {@code Dessert} ayant le {@code nom} passé en paramètres dans la base de données.
	 * 
	 * @param nom le {@code nom} du dessert à aller chercher.
	 * 
	 * @return Le {@code Dessert} correspondant.
	 */
	static public Dessert getDessertNom(String nom){

		chargerMenu(); //on recharge la liste des éléments

		Dessert dessert = getRienDessert(); //élément par défaut (si on ne trouve aucun élément avec le nom passé en paramètres)
		for(Dessert dessertListe : desserts){ //pour chaque élément de la liste
			if(dessertListe.getNom().toLowerCase().equals(nom.toLowerCase())){ //si le nom correspond l'élément par défaut est remplacé
				dessert = dessertListe;
			}
		}

		return(dessert);
	}

	/**
	 * Renvoie le {@code Membre} ayant l'{@code id} passé en paramètres dans la base de données.
	 * 
	 * @param id l'{@code id} du membre à aller chercher.
	 * 
	 * @return Le {@code Membre} correspondant.
	 */
	static public Membre getMembre(String id){

		chargerMembres(); //on recharge la liste des éléments

		Membre membre = new Membre("f38aa97b-2c4b-491e-be10-884e48fbb6c2", "", "", "", "", new Date(0), 0, 0, 0); //élément par défaut (si on ne trouve aucun élément avec l'id passé en paramètres)
		for(Membre membreListe : membres){ //pour chaque élément de la liste
			if(membreListe.getId().equals(id)){ //si l'id correspond l'élément par défaut est remplacé
				membre = membreListe;
			}
		}

		return(membre);
	}

	/**
	 * Renvoie le dernier {@code Membre} ayant le {@code nom} et le {@code prenom} passés en paramètres dans la base de données.
	 * 
	 * @param nom le {@code nom} du membre à aller chercher.
	 * @param prenom le {@code prenom} du membre à aller chercher.
	 * 
	 * @return Le {@code Membre} correspondant.
	 */
	static public Membre getMembreNomPrenom(String nom, String prenom){

		chargerMembres(); //on recharge la liste des éléments

		Membre membre = new Membre("f38aa97b-2c4b-491e-be10-884e48fbb6c2", "", "", "", "", new Date(0), 0, 0, 0); //élément par défaut (si on ne trouve aucun élément avec les noms et prénoms passé en paramètres)
		for(Membre membreListe : membres){ //pour chaque élément de la liste
			if((membreListe.getNom().toLowerCase().equals(nom.toLowerCase()))&&(membreListe.getPrenom().toLowerCase().equals(prenom.toLowerCase()))){ //si le nom et le prénom correspondent l'élément par défaut est remplacé
				membre = membreListe;
			}
		}

		return(membre);
	}

	/**
	 * Recharge la totalité des listes de contenus commande depuis la base de données.
	 */
	static public void chargerMenu(){

		chargerIngredients();
		chargerSauces();
		chargerDesserts();
		chargerBoissons();
		chargerPlats();
		chargerSupplementsBoisson();
	}

	/**
	 * Affiche la totalité du menu (tous les {@code ContenuCommande} de la base de données) dans la console.
	 */
	static public void affMenu(){

		chargerMenu(); //on recharge la liste des éléments

		for(Plat plat: plats){ //pour chaque élément de la liste, on l'affiche
			System.out.println(plat.getNom());
		}

		System.out.println(""); //on saute une ligne

		for(Ingredient ingredient : ingredients){ //pour chaque élément de la liste, on l'affiche
			System.out.println(ingredient.getNom());
		}

		System.out.println(""); //on saute une ligne

		for(Sauce sauce: sauces){ //pour chaque élément de la liste, on l'affiche
			System.out.println(sauce.getNom());
		}

		System.out.println(""); //on saute une ligne

		for(Boisson boisson: boissons){ //pour chaque élément de la liste, on l'affiche
			System.out.println(boisson.getNom());
		}

		System.out.println(""); //on saute une ligne

		for(SupplementBoisson supplementBoisson: supplementsBoisson){ //pour chaque élément de la liste, on l'affiche
			System.out.println(supplementBoisson.getNom());
		}

		System.out.println(""); //on saute une ligne

		for(Dessert dessert: desserts){ //pour chaque élément de la liste, on l'affiche
			System.out.println(dessert.getNom());
		}
	}

	/**
	 * Affiche la liste des membres (tous les {@code Membre} de la base de données) dans la console.
	 */
	static public void affMembres(){

		chargerMembres(); //on recharge la liste des éléments

		for(Membre membre : membres){ //pour chaque élément de la liste, on l'affiche
			System.out.println(membre);
		}
	}

	/**
	 * Lit dans la base de données la liste des {@code Plat} et les place dans la liste des plats.
	 */
	static private void chargerPlats(){

		plats = new ArrayList<Plat>(); //on réinitialise la liste des plats

		File dossierPlats = null;

		try {
			dossierPlats = new File(BaseDonnees.class.getResource("../../Base de Données/Contenus Commandes/Plats/").toURI()); //on attribue le chemin du dossier des plats
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(File fichier : dossierPlats.listFiles()){ //pour chaque fichier dans le dossier des plats
			if(fichier.isFile()&&(FilenameUtils.getExtension(fichier.getName())).equals("json")){ //si le fichier en question est un fichier et que son extension est json
				Plat plat = LecteurBase.lirePlat(FilenameUtils.removeExtension(fichier.getName())); //on lit le fichier pour obtenir un Plat et on l'ajoute à la liste des plats
				plats.add(plat);
			}
		}

		Collections.sort(plats, new CompareContenuCommande());
		Collections.reverse(plats);
	}

	/**
	 * Lit dans la base de données la liste des {@code Ingredient} et les place dans la liste des ingrédients.
	 */
	static private void chargerIngredients(){

		ingredients = new ArrayList<Ingredient>(); //on réinitialise la liste des ingrédients

		File dossierIngredients = null;

		try {
			dossierIngredients = new File(BaseDonnees.class.getResource("../../Base de Données/Contenus Commandes/Ingrédients/").toURI()); //on attribue le chemin du dossier des ingrédients
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(File fichier : dossierIngredients.listFiles()){ //pour chaque fichier dans le dossier des ingrédients
			if(fichier.isFile()&&(FilenameUtils.getExtension(fichier.getName())).equals("json")){ //si le fichier en question est un fichier et que son extension est json
				Ingredient ingredient = LecteurBase.lireIngredient(FilenameUtils.removeExtension(fichier.getName())); //on lit le fichier pour obtenir un Ingredient et on l'ajoute à la liste des ingrédients
				ingredients.add(ingredient);
			}
		}

		Collections.sort(ingredients, new CompareContenuCommande()); //on met la liste des ingrédients dans l'ordre (plus grande priorité d'abord puis ordre alphabétique)
		Collections.reverse(ingredients);
	}

	/**
	 * Lit dans la base de données la liste des {@code Sauce} et les place dans la liste des sauces.
	 */
	static private void chargerSauces(){

		sauces = new ArrayList<Sauce>(); //on réinitialise la liste des sauces

		File dossierSauces = null;

		try {
			dossierSauces = new File(BaseDonnees.class.getResource("../../Base de Données/Contenus Commandes/Sauces/").toURI()); //on attribue le chemin du dossier des sauces
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(File fichier : dossierSauces.listFiles()){ //pour chaque fichier dans le dossier des sauces
			if(fichier.isFile()&&(FilenameUtils.getExtension(fichier.getName())).equals("json")){ //si le fichier en question est un fichier et que son extension est json
				Sauce sauce = LecteurBase.lireSauce(FilenameUtils.removeExtension(fichier.getName())); //on lit le fichier pour obtenir une Sauce et on l'ajoute à la liste des sauces
				sauces.add(sauce);
			}
		}

		Collections.sort(sauces, new CompareContenuCommande()); //on met la liste des sauces dans l'ordre (plus grande priorité d'abord puis ordre alphabétique)
		Collections.reverse(sauces);
	}

	/**
	 * Lit dans la base de données la liste des {@code Boisson} et les place dans la liste des boissons.
	 */
	static private void chargerBoissons(){

		boissons = new ArrayList<Boisson>(); //on réinitialise la liste des boissons

		File dossierBoissons = null;

		try {
			dossierBoissons = new File(BaseDonnees.class.getResource("../../Base de Données/Contenus Commandes/Boissons/").toURI()); //on attribue le chemin du dossier des boissons
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(File fichier : dossierBoissons.listFiles()){ //pour chaque fichier dans le dossier des boissons
			if(fichier.isFile()&&(FilenameUtils.getExtension(fichier.getName())).equals("json")){ //si le fichier en question est un fichier et que son extension est json
				Boisson boisson = LecteurBase.lireBoisson(FilenameUtils.removeExtension(fichier.getName())); //on lit le fichier pour obtenir une Boisson et on l'ajoute à la liste des boissons
				boissons.add(boisson);
			}
		}

		Collections.sort(boissons, new CompareContenuCommande()); //on met la liste des boissons dans l'ordre (plus grande priorité d'abord puis ordre alphabétique)
		Collections.reverse(boissons);
	}

	/**
	 * Lit dans la base de données la liste des {@code SupplementBoisson} et les place dans la liste des suppléments boisson.
	 */
	static private void chargerSupplementsBoisson(){

		supplementsBoisson = new ArrayList<SupplementBoisson>(); //on réinitialise la liste des suppléments boisson

		File dossierSupplementsBoisson = null;

		try {
			dossierSupplementsBoisson = new File(BaseDonnees.class.getResource("../../Base de Données/Contenus Commandes/Suppléments Boisson/").toURI()); //on attribue le chemin du dossier des suppléments boisson
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(File fichier : dossierSupplementsBoisson.listFiles()){ //pour chaque fichier dans le dossier des suppléments boisson
			if(fichier.isFile()&&(FilenameUtils.getExtension(fichier.getName())).equals("json")){ //si le fichier en question est un fichier et que son extension est json
				SupplementBoisson supplementBoisson = LecteurBase.lireSupplementBoisson(FilenameUtils.removeExtension(fichier.getName())); //on lit le fichier pour obtenir un SupplementBoisson et on l'ajoute à la liste des suppléments boisson
				supplementsBoisson.add(supplementBoisson);
			}
		}

		Collections.sort(supplementsBoisson, new CompareContenuCommande()); //on met la liste des suppléments boisson dans l'ordre (plus grande priorité d'abord puis ordre alphabétique)
		Collections.reverse(supplementsBoisson);
	}

	/**
	 * Lit dans la base de données la liste des {@code Dessert} et les place dans la liste des desserts.
	 */
	static private void chargerDesserts(){

		desserts = new ArrayList<Dessert>(); //on réinitialise la liste des desserts

		File dossierDesserts = null;

		try {
			dossierDesserts = new File(BaseDonnees.class.getResource("../../Base de Données/Contenus Commandes/Desserts/").toURI()); //on attribue le chemin du dossier des desserts
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(File fichier : dossierDesserts.listFiles()){ //pour chaque fichier dans le dossier des desserts
			if(fichier.isFile()&&(FilenameUtils.getExtension(fichier.getName())).equals("json")){ //si le fichier en question est un fichier et que son extension est json
				Dessert dessert = LecteurBase.lireDessert(FilenameUtils.removeExtension(fichier.getName())); //on lit le fichier pour obtenir un Dessert et on l'ajoute à la liste des desserts
				desserts.add(dessert);
			}
		}

		Collections.sort(desserts, new CompareContenuCommande()); //on met la liste des desserts dans l'ordre (plus grande priorité d'abord puis ordre alphabétique)
		Collections.reverse(desserts);
	}

	/**
	 * Lit dans la base de données la liste des {@code Membre} et les place dans la liste des membres.
	 */
	static public void chargerMembres(){

		membres = new ArrayList<Membre>(); //on réinitialise la liste des membres

		File dossierMembres = null;

		try {
			dossierMembres = new File(BaseDonnees.class.getResource("../../Base de Données/Membres/").toURI()); //on attribue le chemin du dossier des membres
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(File fichier : dossierMembres.listFiles()){ //pour chaque fichier dans le dossier des membres
			if(fichier.isFile()&&(FilenameUtils.getExtension(fichier.getName())).equals("json")){ //si le fichier en question est un fichier et que son extension est json
				Membre membre = LecteurBase.lireMembre(FilenameUtils.removeExtension(fichier.getName())); //on lit le fichier pour obtenir un Membre et on l'ajoute à la liste des membres
				membres.add(membre);
			}
		}

		Collections.sort(membres, new CompareMembre()); //on met la liste des membres dans l'ordre (dans l'ordre alphabétique des prénoms)
	}
}
