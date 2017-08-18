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

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>LecteurBase est une classe constituée uniquement de méthodes statiques utiles à la gestion de la lecture des fichiers de la base de données.</p>
 * <p>Elle est capable de lire les fichiers de la base de donnée pour les classes :
 * <ul><li>{@code Plat},</li>
 * <li>{@code Ingredient},</li>
 * <li>{@code Sauce},</li>
 * <li>{@code Boisson},</li>
 * <li>{@code SupplementBoisson},</li>
 * <li>{@code Dessert},</li>
 * <li>{@code Membre},</li>
 * <li>{@code Commande},</li>
 * <li>{@code CommandeAssignee},</li>
 * <li>{@code Service}, et</li>
 * <li>{@code Parametres}.</li></ul></p>
 * <p>Elle possède également une méthode {@code estAssignee(Date, int)} qui renvoie si une commande est assignée.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public final class LecteurBase {

	/**
	 * Renvoie le contenu d'un fichier de la base de données sous la forme d'un {@code JSONObject}.
	 * 
	 * @param path le chemin du fichier à lire.
	 * 
	 * @return un {@code JSONObject} contenant toutes les informations du fichier.
	 */
	private static final JSONObject lireObjet(String path){

		JSONParser parser = new JSONParser();

		Object objet = null;
		try {
			FileReader lecteur = new FileReader(path);
			objet = parser.parse(lecteur);
			lecteur.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject objetJson = (JSONObject)objet;

		return(objetJson);
	}

	/**
	 * Renvoie le contenu du fichier de la base de données de type {@code Plat} ayant pour nom le nom passé en paramètres.
	 * 
	 * @param nom le nom du plat à lire.
	 * 
	 * @return le {@code Plat} lu.
	 */
	public static final Plat lirePlat(String nom){

		JSONObject platJson = new JSONObject();

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Plats/").toURI()); //on définit le dossier de lecture sur le dossier Plats
			platJson = lireObjet(dossier + "/" + nom + ".json"); //on récupère l'objet JSON du plat lu
			System.out.println("Lecture de : " + platJson); //on écrit dans la console qu'on a lu un fichier
		} catch (Exception e) {
			e.printStackTrace();
		}

		Plat plat = new Plat( //on initialise un nouveau plat avec ses données
				(String)platJson.get("id"),
				(String)platJson.get("nom"),
				((Number)platJson.get("cout")).floatValue(),
				(boolean)platJson.get("estDisponible"),
				((Number)platJson.get("nbUtilisations")).intValue(),
				((Number)platJson.get("priorite")).intValue(),
				((Number)platJson.get("nbMaxIngredients")).intValue(),
				((Number)platJson.get("nbMaxSauces")).intValue(),
				(boolean)platJson.get("utilisePain"),
				((Number)platJson.get("prix")).floatValue());

		return(plat);
	}

	/**
	 * Renvoie le contenu du fichier de la base de données de type {@code Ingredient} ayant pour nom le nom passé en paramètres.
	 * 
	 * @param nom le nom de l'ingrédient à lire.
	 * 
	 * @return l'{@code Ingredient} lu.
	 */
	public static final Ingredient lireIngredient(String nom){

		JSONObject ingredientJson = new JSONObject();

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Ingrédients/").toURI()); //on définit le dossier de lecture sur le dossier Ingrédients
			ingredientJson = lireObjet(dossier + "/" + nom + ".json"); //on récupère l'objet JSON de l'ingrédient lu
			System.out.println("Lecture de : " + ingredientJson); //on écrit dans la console qu'on a lu un fichier
		} catch (Exception e) {
			e.printStackTrace();
		}

		Ingredient ingredient = new Ingredient( //on initialise un nouvel ingrédient avec ses données
				(String)ingredientJson.get("id"),
				(String)ingredientJson.get("nom"),
				((Number)ingredientJson.get("cout")).floatValue(),
				(boolean)ingredientJson.get("estDisponible"),
				((Number)ingredientJson.get("nbUtilisations")).intValue(),
				((Number)ingredientJson.get("priorite")).intValue());

		return(ingredient);
	}

	/**
	 * Renvoie le contenu du fichier de la base de données de type {@code Sauce} ayant pour nom le nom passé en paramètres.
	 * 
	 * @param nom le nom de la sauce à lire.
	 * 
	 * @return la {@code Sauce} lue.
	 */
	public static final Sauce lireSauce(String nom){

		JSONObject sauceJson = new JSONObject();

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Sauces/").toURI()); //on définit le dossier de lecture sur le dossier Sauces
			sauceJson = lireObjet(dossier + "/" + nom + ".json"); //on récupère l'objet JSON de la sauce lue
			System.out.println("Lecture de : " + sauceJson); //on écrit dans la console qu'on a lu un fichier
		} catch (Exception e) {
			e.printStackTrace();
		}

		Sauce sauce = new Sauce( //on initialise une nouvelle sauce avec ses données
				(String)sauceJson.get("id"),
				(String)sauceJson.get("nom"),
				((Number)sauceJson.get("cout")).floatValue(),
				(boolean)sauceJson.get("estDisponible"),
				((Number)sauceJson.get("nbUtilisations")).intValue(),
				((Number)sauceJson.get("priorite")).intValue());

		return(sauce);
	}

	/**
	 * Renvoie le contenu du fichier de la base de données de type {@code Boisson} ayant pour nom le nom passé en paramètres.
	 * 
	 * @param nom le nom de la boisson à lire.
	 * 
	 * @return la {@code Boisson} lue.
	 */
	public static final Boisson lireBoisson(String nom){

		JSONObject boissonJson = new JSONObject();

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Boissons/").toURI()); //on définit le dossier de lecture sur le dossier Boissons
			boissonJson = lireObjet(dossier + "/" + nom + ".json"); //on récupère l'objet JSON de la boisson lue
			System.out.println("Lecture de : " + boissonJson); //on écrit dans la console qu'on a lu un fichier
		} catch (Exception e) {
			e.printStackTrace();
		}

		Boisson boisson = new Boisson( //on initialise une nouvelle boisson avec ses données
				(String)boissonJson.get("id"),
				(String)boissonJson.get("nom"),
				((Number)boissonJson.get("cout")).floatValue(),
				(boolean)boissonJson.get("estDisponible"),
				((Number)boissonJson.get("nbUtilisations")).intValue(),
				((Number)boissonJson.get("priorite")).intValue());

		return(boisson);
	}

	/**
	 * Renvoie le contenu du fichier de la base de données de type {@code SupplementBoisson} ayant pour nom le nom passé en paramètres.
	 * 
	 * @param nom le nom du supplément boisson à lire.
	 * 
	 * @return le {@code SupplementBoisson} lu.
	 */
	public static final SupplementBoisson lireSupplementBoisson(String nom){

		JSONObject supplementBoissonJson = new JSONObject();

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Suppléments Boisson/").toURI()); //on définit le dossier de lecture sur le dossier Suppléments Boisson
			supplementBoissonJson = lireObjet(dossier + "/" + nom + ".json"); //on récupère l'objet JSON du supplément boisson lu
			System.out.println("Lecture de : " + supplementBoissonJson); //on écrit dans la console qu'on a lu un fichier
		} catch (Exception e) {
			e.printStackTrace();
		}

		SupplementBoisson supplementBoisson = new SupplementBoisson( //on initialise un nouveau supplément boisson avec ses données
				(String)supplementBoissonJson.get("id"),
				(String)supplementBoissonJson.get("nom"),
				((Number)supplementBoissonJson.get("cout")).floatValue(),
				(boolean)supplementBoissonJson.get("estDisponible"),
				((Number)supplementBoissonJson.get("nbUtilisations")).intValue(),
				((Number)supplementBoissonJson.get("priorite")).intValue(),
				((Number)supplementBoissonJson.get("prix")).floatValue());

		return(supplementBoisson);
	}

	/**
	 * Renvoie le contenu du fichier de la base de données de type {@code Dessert} ayant pour nom le nom passé en paramètres.
	 * 
	 * @param nom le nom du dessert à lire.
	 * 
	 * @return le {@code Dessert} lu.
	 */
	public static final Dessert lireDessert(String nom){

		JSONObject dessertJson = new JSONObject();

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Desserts/").toURI()); //on définit le dossier de lecture sur le dossier Desserts
			dessertJson = lireObjet(dossier + "/" + nom + ".json"); //on récupère l'objet JSON du dessert lu
			System.out.println("Lecture de : " + dessertJson); //on écrit dans la console qu'on a lu un fichier
		} catch (Exception e) {
			e.printStackTrace();
		}

		Dessert dessert = new Dessert( //on initialise un nouveau dessert avec ses données
				(String)dessertJson.get("id"),
				(String)dessertJson.get("nom"),
				((Number)dessertJson.get("cout")).floatValue(),
				(boolean)dessertJson.get("estDisponible"),
				((Number)dessertJson.get("nbUtilisations")).intValue(),
				((Number)dessertJson.get("priorite")).intValue(),
				((Number)dessertJson.get("prix")).floatValue());

		return(dessert);
	}

	/**
	 * Renvoie le contenu du fichier de la base de données de type {@code Membre} ayant pour nom de fichier le nom de fichier passé en paramètres.
	 * 
	 * @param nomFichier le nom du fichier du membre à lire.
	 * 
	 * @return le {@code Membre} lu.
	 */
	public static final Membre lireMembre(String nomFichier){

		JSONObject membreJson = new JSONObject();

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Membres/").toURI()); //on définit le dossier de lecture sur le dossier Membres
			membreJson = lireObjet(dossier + "/" + nomFichier + ".json"); //on récupère l'objet JSON du membre lu
			System.out.println("Lecture de : " + membreJson); //on écrit dans la console qu'on a lu un fichier
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Membre membre = new Membre( //on initialise un nouveau membre avec ses données
					(String)membreJson.get("id"),
					(String)membreJson.get("nom"),
					(String)membreJson.get("prenom"),
					(String)membreJson.get("surnom"),
					(String)membreJson.get("poste"),
					CreateurBase.FORMAT_DATE.parse((String)membreJson.get("dateNaissance")),
					((Number)membreJson.get("nbCommandes")).intValue(),
					((Number)membreJson.get("nbServices")).intValue(),
					((Number)membreJson.get("tempsMoyenCommande")).floatValue());

			return(membre);
		} catch (Exception e) {
			e.printStackTrace();
			Membre membre = null;

			return(membre);
		}
	}

	/**
	 * Renvoie le contenu du fichier de la base de données de type {@code Commande} ayant pour date et numéro la date et le numéro passés en paramètres.
	 * 
	 * @param moment la date de la commande.
	 * @param numero le numéro de la commande.
	 * 
	 * @return la {@code Commande} lue.
	 */
	public static final Commande lireCommande(Date moment, int numero){

		JSONObject commandeJson = new JSONObject();

		final SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		final SimpleDateFormat mois = new SimpleDateFormat("MM");
		final SimpleDateFormat jour = new SimpleDateFormat("dd");

		String zeros = "";

		if(numero < 100){ //si le numéro de commande est inférieur à 100, on ajoute un 0 devant (pour que les commandes apparaissent dans l'ordre lorsqu'elle sont visualisées dans le navigateur de fichiers)
			zeros += "0";
			if((int)numero < 10){ //de même si le numéro est inférieur à 10, on ajoute un autre 0
				zeros += "0";
			}
		}

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Services/").toURI()); //on définit le dossier de lecture sur le dossier Services
		} catch (Exception e) {
			e.printStackTrace();
		}

		commandeJson = lireObjet(dossier + "/" + annee.format(moment) + "/" + mois.format(moment) + "/" + jour.format(moment) + "/" + zeros + numero + ".json"); //on récupère l'objet JSON de la commande lue
		System.out.println("Lecture de : " + commandeJson); //on écrit dans la console qu'on a lu un fichier

		try {
			List<Ingredient> ingredientsCommande = new ArrayList<Ingredient>();
			JSONArray ingredientsCommandeJson = (JSONArray) commandeJson.get("ingredients");
			
			for(Object ing : ingredientsCommandeJson){
				ingredientsCommande.add(BaseDonnees.getIngredient((String)ing));
			}

			List<Sauce> saucesCommande = new ArrayList<Sauce>();
			JSONArray saucesCommandeJson = (JSONArray) commandeJson.get("sauces");

			for(Object sau : saucesCommandeJson){
				saucesCommande.add(BaseDonnees.getSauce((String)sau));
			}

			Commande commande = new Commande( //on initialise une nouvelle commande avec ses données
					new Date(((Number)commandeJson.get("moment")).longValue()),
					((Number)commandeJson.get("numero")).intValue(),
					BaseDonnees.getPlat((String)commandeJson.get("plat")),
					ingredientsCommande,
					saucesCommande,
					BaseDonnees.getDessert((String)commandeJson.get("dessert")),
					BaseDonnees.getBoisson((String)commandeJson.get("boisson")),
					BaseDonnees.getSupplementBoisson((String)commandeJson.get("supplementBoisson")));

			return(commande);
		} catch (Exception e) {
			e.printStackTrace();
			Commande commande = new Commande(new Date(0), 0, BaseDonnees.getRienPlat(), new ArrayList<Ingredient>(), new ArrayList<Sauce>(), BaseDonnees.getRienDessert(), BaseDonnees.getRienBoisson(), BaseDonnees.getRienSupplementBoisson());

			return(commande);
		}
	}

	/**
	 * Renvoie le contenu du fichier de la base de données de type {@code CommandeAssignee} ayant pour date et numéro la date et le numéro passés en paramètres.
	 * 
	 * @param moment la date de la commande assignée.
	 * @param numero le numéro de la commande assignée.
	 * 
	 * @return la {@code CommandeAssignée} lue.
	 */
	public static final CommandeAssignee lireCommandeAssignee(Date moment, int numero){

		JSONObject commandeJson = new JSONObject();

		final SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		final SimpleDateFormat mois = new SimpleDateFormat("MM");
		final SimpleDateFormat jour = new SimpleDateFormat("dd");

		String zeros = "";

		if(numero < 100){ //si le numéro de commande est inférieur à 100, on ajoute un 0 devant (pour que les commandes apparaissent dans l'ordre lorsqu'elle sont visualisées dans le navigateur de fichiers)
			zeros += "0";
			if((int)numero < 10){ //de même si le numéro est inférieur à 10, on ajoute un autre 0
				zeros += "0";
			}
		}

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Services/").toURI()); //on définit le dossier de lecture sur le dossier Services
		} catch (Exception e) {
			e.printStackTrace();
		}

		commandeJson = lireObjet(dossier + "/" + annee.format(moment) + "/" + mois.format(moment) + "/" + jour.format(moment) + "/" + zeros + numero + ".json"); //on récupère l'objet JSON de la commande lue
		System.out.println("Lecture de l'assignation de : " + commandeJson); //on écrit dans la console qu'on a lu un fichier

		try {
			List<Ingredient> ingredientsCommande = new ArrayList<Ingredient>();
			JSONArray ingredientsCommandeJson = (JSONArray)commandeJson.get("ingredients");
			
			for(Object ing : ingredientsCommandeJson){
				ingredientsCommande.add(BaseDonnees.getIngredient((String)ing));
			}

			List<Sauce> saucesCommande = new ArrayList<Sauce>();
			JSONArray saucesCommandeJson = (JSONArray)commandeJson.get("sauces");

			for(Object sau : saucesCommandeJson){
				saucesCommande.add(BaseDonnees.getSauce((String)sau));
			}

			Commande commande = new Commande( //on initialise une nouvelle commande avec ses données
					new Date(((Number)commandeJson.get("moment")).longValue()),
					((Number)commandeJson.get("numero")).intValue(),
					BaseDonnees.getPlat((String)commandeJson.get("plat")),
					ingredientsCommande,
					saucesCommande,
					BaseDonnees.getDessert((String)commandeJson.get("dessert")),
					BaseDonnees.getBoisson((String)commandeJson.get("boisson")),
					BaseDonnees.getSupplementBoisson((String)commandeJson.get("supplementBoisson")));

			CommandeAssignee commandeAssignee = new CommandeAssignee( //on initialise une nouvelle commande assignée avec la commande créée et avec les données du fichier
					commande,
					BaseDonnees.getMembre((String)commandeJson.get("membre")),
					new Date(((Number)commandeJson.get("momentAssignation")).longValue()),
					(boolean)commandeJson.get("estRealisee"),
					new Date(((Number)commandeJson.get("momentRealisation")).longValue()),
					(boolean)commandeJson.get("estDonnee"));
			
			return(commandeAssignee);
		} catch (Exception e) {
			e.printStackTrace();
			Commande commande = new Commande(new Date(0), 0, BaseDonnees.getRienPlat(), new ArrayList<Ingredient>(), new ArrayList<Sauce>(), BaseDonnees.getRienDessert(), BaseDonnees.getRienBoisson(), BaseDonnees.getRienSupplementBoisson());
			Membre membre = new Membre();
			CommandeAssignee commandeAssignee = new CommandeAssignee(commande, membre, new Date(0), false, new Date(0), false);

			return(commandeAssignee);
		}
	}

	/**
	 * Renvoie le contenu du fichier de la base de données de type {@code Service} ayant pour date la date passée en paramètres.
	 * 
	 * @param date la date du service.
	 * 
	 * @return le {@code Service} lu.
	 */
	public static final Service lireService(Date date){

		JSONObject serviceJson = new JSONObject();

		final SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		final SimpleDateFormat mois = new SimpleDateFormat("MM");
		final SimpleDateFormat jour = new SimpleDateFormat("dd");

		File dossier = null;
		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Services/").toURI()); //on définit le dossier de lecture sur le dossier Services
		} catch (Exception e) {
			e.printStackTrace();
		}

		serviceJson = lireObjet(dossier + "/" + annee.format(date) + "/" + mois.format(date) + "/" + jour.format(date) + "/" + "_service.json"); //on récupère l'objet JSON du service lu
		System.out.println("Lecture de : " + serviceJson); //on écrit dans la console qu'on a lu un fichier

		try {
			List<Membre> commisService = new ArrayList<Membre>();
			JSONArray commisServiceJson = (JSONArray) serviceJson.get("commis");

			for(Object com : commisServiceJson){
				commisService.add(BaseDonnees.getMembre((String)com));
			}

			List<Membre> confectionService = new ArrayList<Membre>();
			JSONArray confectionServiceJson = (JSONArray) serviceJson.get("confection");

			for(Object con : confectionServiceJson){
				confectionService.add(BaseDonnees.getMembre((String)con));
			}

			Service service = new Service( //on initialise un nouveau service avec ses données
					CreateurBase.FORMAT_DATE.parse((String)serviceJson.get("date")),
					new ArrayList<Commande>(),
					((Number)serviceJson.get("nbBaguettesUtilisees")).floatValue(),
					((Number)serviceJson.get("nbBaguettesAchetees")).floatValue(),
					((Number)serviceJson.get("nbBaguettesReservees")).floatValue(),
					BaseDonnees.getMembre((String)serviceJson.get("ordi")),
					commisService,
					confectionService);

			return(service);
		} catch (Exception e) {
			e.printStackTrace();

			Membre membre = new Membre();
			Service service = new Service(new Date(0), new ArrayList<Commande>(), 0, 0, 0, membre, new ArrayList<Membre>(), new ArrayList<Membre>());

			return(service);
		}
	}

	/**
	 * Modifie les valeurs de la classe {@code Parametres} en utilisant le contenu du fichier de la base de données stockant les paramètres du logiciel.
	 */
	public static final void lireParametres(){

		JSONObject parametresJson;

		File dossier = null;
		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Paramètres/").toURI()); //on définit le dossier de lecture sur le dossier Paramètres
		} catch (Exception e) {
			e.printStackTrace();
		}

		parametresJson = lireObjet(dossier + "/" + "paramètres.json"); //on récupère l'objet JSON des paramètres lus
		System.out.println("Lecture de : " + parametresJson); //on écrit dans la console qu'on a lu un fichier

		if(parametresJson != null){ //si le fichier a été lu, on change les valeurs de la classe Parametres
			Parametres.setPrixIngredientSupp(((Number)parametresJson.get("prixIngredientSupp")).floatValue());
			Parametres.setPrixBoisson(((Number)parametresJson.get("prixBoisson")).floatValue());
			Parametres.setReducMenu(((Number)parametresJson.get("reducMenu")).floatValue());
			Parametres.setCoutPain(((Number)parametresJson.get("coutPain")).floatValue());
		}
	}

	/**
	 * Renvoie le fait qu'une commande ayant pour date et numéro les date et numéro passés en paramètres soit assignée.
	 * 
	 * @param moment la date de la commande à tester.
	 * @param numero le numéro de la commande à tester.
	 * 
	 * @return l'état assigné de la commande.
	 */
	public static final boolean estAssignee(Date moment, int numero){

		JSONObject commandeJson = new JSONObject();

		final SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		final SimpleDateFormat mois = new SimpleDateFormat("MM");
		final SimpleDateFormat jour = new SimpleDateFormat("dd");

		String zeros = "";

		if(numero < 100){ //si le numéro de commande est inférieur à 100, on ajoute un 0 devant (pour que les commandes apparaissent dans l'ordre lorsqu'elle sont visualisées dans le navigateur de fichiers)
			zeros += "0";
			if((int)numero < 10){ //de même si le numéro est inférieur à 10, on ajoute un autre 0
				zeros += "0";
			}
		}

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Services/").toURI()); //on définit le dossier de lecture sur le dossier Services
		} catch (Exception e) {
			e.printStackTrace();
		}

		commandeJson = lireObjet(dossier + "/" + annee.format(moment) + "/" + mois.format(moment) + "/" + jour.format(moment) + "/" + zeros + numero + ".json"); //on récupère l'objet JSON de la commande lue

		boolean estAssignee = false;

		try {
			estAssignee = (boolean)commandeJson.get("assignee");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return(estAssignee);
	}
}
