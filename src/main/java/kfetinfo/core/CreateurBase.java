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

import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;

import javax.swing.JOptionPane;

/**
 * <p>CreateurBase est une classe constituée uniquement de méthodes statiques utiles à la gestion de l'écriture des fichiers de la base de données.</p>
 * <p>Elle possède également des méthodes utiles pour supprimer certains fichiers de la base de données.</p>
 * <p>Elle est capable d'écrire les fichiers de la base de donnée pour les classes :
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
 * <p>Elle permet également d'initialiser la base de données avec des valeurs par défaut au cas où toutes les données soient perdues ou en cas de nouvelle installation.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public final class CreateurBase {

	//format de date utilisé dans les fichiers
	public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * Initialise la base de données avec des dossiers et des fichiers essentiels au bon déroulement du programme. Elle est appelée à chaque fois que le programme est lancé.
	 */
	public static final void initialiserBase(){

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/").toURI()); //on définit le dossier de base sur le dossier Base de Données qui se trouve à la racine de l'arborescence du programme
		} catch (NullPointerException e) { //En général lorsque cette exception est levée, c'est que le dossier Base de Données n'existe pas à la racine du logiciel, alors on affiche un petit message pour indiquer la marche à suivre
			JOptionPane.showMessageDialog(null, "Il faut créer un dossier « Base de Données » à la racine du logiciel, sinon ça ne peut pas fonctionner ! :(");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try { //on ajoute de tous les dossiers nécessaires à la bonne mise en place de la base de données
			Files.createDirectories(Paths.get(dossier + "/" + "Contenus Commandes/Ingrédients"));
			Files.createDirectories(Paths.get(dossier + "/" + "Contenus Commandes/Sauces"));
			Files.createDirectories(Paths.get(dossier + "/" + "Contenus Commandes/Desserts"));
			Files.createDirectories(Paths.get(dossier + "/" + "Contenus Commandes/Boissons"));
			Files.createDirectories(Paths.get(dossier + "/" + "Contenus Commandes/Plats"));
			Files.createDirectories(Paths.get(dossier + "/" + "Contenus Commandes/Suppléments Boisson"));
			Files.createDirectories(Paths.get(dossier + "/" + "Membres"));
			Files.createDirectories(Paths.get(dossier + "/" + "Services"));
			Files.createDirectories(Paths.get(dossier + "/" + "Paramètres"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		ajouterRiens(); //on ajoute les riens des contenus commande et le premier service (chaque service allant chercher le précédent pour définir certaines de ses valeurs)
		ajouterPremierService();
	}

	/**
	 * Ajoute les <i>riens</i> des contenus commande.
	 */
	@SuppressWarnings("unchecked")
	public static final void ajouterRiens(){

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Plats/").toURI()); //on définit le dossier d'écriture sur le dossier Plats
		} catch (Exception e) {
			e.printStackTrace();
		}

        File f = new File(dossier + "/" + "rien" + ".json"); //on crée une nouvelle référence vers le rien qu'on souhaite ajouter
        if(!(f.exists() && !f.isDirectory())){  //si celui-ci n'existe pas ou s'il s'agit d'un dossier
        	JSONObject rienPlat = new JSONObject();
    		rienPlat.put("id", BaseDonnees.ID_RIEN_PLAT);
    		rienPlat.put("nom", "Rien");
    		rienPlat.put("cout", 0f);
    		rienPlat.put("estDisponible", true);
    		rienPlat.put("nbUtilisations", new Integer(0));
    		rienPlat.put("priorite", -1);
    		rienPlat.put("prix", 0f);
    		rienPlat.put("nbMaxIngredients", 0);
    		rienPlat.put("nbMaxSauces", 0);
    		rienPlat.put("utilisePain", false);

    		try (FileWriter file = new FileWriter(dossier + "/" + "rien" + ".json")) {
                file.write(rienPlat.toJSONString()); //on l'écrit
                file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Création de : " + rienPlat); //on écrit dans la console qu'on a créé un nouveau fichier
        }

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Boissons/").toURI()); //on définit le dossier d'écriture sur le dossier Boissons
		} catch (Exception e) {
			e.printStackTrace();
		}

        f = new File(dossier + "/" + "rien" + ".json"); //on crée une nouvelle référence vers le rien qu'on souhaite ajouter
        if(!(f.exists() && !f.isDirectory())){ //si celui-ci n'existe pas ou s'il s'agit d'un dossier
        	JSONObject rienBoisson = new JSONObject();
    		rienBoisson.put("id", BaseDonnees.ID_RIEN_BOISSON);
    		rienBoisson.put("nom", "Rien");
    		rienBoisson.put("cout", 0f);
    		rienBoisson.put("estDisponible", true);
    		rienBoisson.put("nbUtilisations", new Integer(0));
    		rienBoisson.put("priorite", -1);

    		try (FileWriter file = new FileWriter(dossier + "/" + "rien" + ".json")) {
                file.write(rienBoisson.toJSONString()); //on l'écrit
                file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Création de : " + rienBoisson); //on écrit dans la console qu'on a créé un nouveau fichier
        }

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Suppléments Boisson/").toURI()); //on définit le dossier d'écriture sur le dossier Suppléments Boisson
		} catch (Exception e) {
			e.printStackTrace();
		}

        f = new File(dossier + "/" + "rien" + ".json"); //on crée une nouvelle référence vers le rien qu'on souhaite ajouter
        if(!(f.exists() && !f.isDirectory())){ //si celui-ci n'existe pas ou s'il s'agit d'un dossier
        	JSONObject rienSupplementBoisson = new JSONObject();
    		rienSupplementBoisson.put("id", BaseDonnees.ID_RIEN_SUPPLEMENT_BOISSON);
    		rienSupplementBoisson.put("nom", "Rien");
    		rienSupplementBoisson.put("cout", 0f);
    		rienSupplementBoisson.put("estDisponible", true);
    		rienSupplementBoisson.put("nbUtilisations", new Integer(0));
    		rienSupplementBoisson.put("priorite", -1);
    		rienSupplementBoisson.put("prix", 0f);

    		try (FileWriter file = new FileWriter(dossier + "/" + "rien" + ".json")) {
                file.write(rienSupplementBoisson.toJSONString()); //on l'écrit
                file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Création de : " + rienSupplementBoisson); //on écrit dans la console qu'on a créé un nouveau fichier
        }

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Desserts/").toURI()); //on définit le dossier d'écriture sur le dossier Desserts
		} catch (Exception e) {
			e.printStackTrace();
		}

		f = new File(dossier + "/" + "rien" + ".json"); //on crée une nouvelle référence vers le rien qu'on souhaite ajouter
        if(!(f.exists() && !f.isDirectory())){ //si celui-ci n'existe pas ou s'il s'agit d'un dossier
    		JSONObject rienDessert = new JSONObject();
    		rienDessert.put("id", BaseDonnees.ID_RIEN_DESSERT);
    		rienDessert.put("nom", "Rien");
    		rienDessert.put("cout", 0f);
    		rienDessert.put("estDisponible", true);
    		rienDessert.put("nbUtilisations", new Integer(0));
    		rienDessert.put("priorite", -1);
    		rienDessert.put("prix", 0f);
    		try (FileWriter file = new FileWriter(dossier + "/" + "rien" + ".json")) {
                file.write(rienDessert.toJSONString()); //on l'écrit
                file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Création de : " + rienDessert); //on écrit dans la console qu'on a créé un nouveau fichier
        }
        
	}

	/**
	 * Ajoute le premier service, indispensable car chaque service va chercher le précédent pour définir certaines de ses valeurs.
	 */
	public static final void ajouterPremierService(){

		Service service = new Service(
			new Date(0),
			new ArrayList<Commande>(),
			0,
			0,
			0,
			new Membre(),
			new ArrayList<Membre>(),
			new ArrayList<Membre>());

		ajouterService(service);
	}

	/**
	 * Renvoie une base pour les nouveaux fichiers de contenu commande et de membre.
	 * 
	 * @return un {@code JSONObject} avec un nouvel {@code id}.
	 */
	@SuppressWarnings("unchecked")
	private static final JSONObject creer(){

		JSONObject objet = new JSONObject();
		UUID id = UUID.randomUUID(); //on attribue un ID aléatoire à l'objet
		objet.put("id", id.toString());
		return(objet);
	}

	/**
	 * Renvoie une base pour les nouveaux fichiers de contenu commande contenant ce que tous les contenus commande ont en commun.
	 * 
	 * @param nom le nom du contenu commande.
	 * @param cout le coût du contenu commande, c'est à dire une estimation de la quantité d'argent dépensée pour ajouter le contenu commande à la commande pour une portion classique.
	 * @param estDisponible le fait que le contenu commande soit en stock ou pas.
	 * @param priorite la position du contenu commande dans sa liste (la liste est triée d'abord par ordre de priorité puis par ordre alphabétique si les priorités sont égales).
	 * 
	 * @return un {@code JSONObject} prêt à l'emploi pour les contenus commande.
	 */
	@SuppressWarnings("unchecked")
	private static final JSONObject creerContenuCommande(String nom, float cout, boolean estDisponible, int priorite){

		JSONObject contenu = creer(); //on récupère l'objet JSON avec l'id et on y ajoute les attributs communs à tous les contenus commande
		contenu.put("nom", nom);
		contenu.put("cout", cout);
		contenu.put("estDisponible", estDisponible);
		contenu.put("nbUtilisations", new Integer(0));
		contenu.put("priorite", priorite);
		return(contenu);
	}

	/**
	 * Crée un nouveau fichier dans la base de données correspondant à un nouveau plat. Ne pas donner à votre plat le nom d'un plat déjà présent dans la base de données ou celui-ci sera écrasé.
	 * 
	 * @param nom le nom du plat.
	 * @param cout le coût du plat, c'est à dire une estimation de la quantité d'argent dépensée pour ajouter le plat à la commande pour une portion classique.
	 * @param estDisponible le fait que le plat soit en stock ou pas.
	 * @param nbUtilisations le nombre d'utilisations du plat. Non implémenté pour l'instant.
	 * @param priorite la position du plat dans la liste des plats (la liste est triée d'abord par ordre de priorité puis par ordre alphabétique si les priorités sont égales).
	 * @param prix le prix minimum que doit payer un client choisissant ce plat.
	 * @param nbMaxIngredients le nombre maximal d'ingrédients pouvant être ajoutés au plat avant de payer un supplément.
	 * @param nbMaxSauces le nombre maximal de sauces pouvant être ajoutées au plat.
	 * @param utilisePain le fait que le plat ait besoin de pain pour être réalisé.
	 */
	@SuppressWarnings("unchecked")
	public static final void creerPlat(String nom, float cout, boolean estDisponible, int priorite, float prix, int nbMaxIngredients, int nbMaxSauces, boolean utilisePain) {

		JSONObject plat = creerContenuCommande(nom, cout, estDisponible, priorite); //on récupère l'objet JSON avec les attributs communs à tous les contenus commandes
		plat.put("prix", prix); //on y ajoute les attributs spécifiques aux plats
		plat.put("nbMaxIngredients", nbMaxIngredients);
		plat.put("nbMaxSauces", nbMaxSauces);
		plat.put("utilisePain", utilisePain);

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Plats/").toURI()); //on définit le dossier d'écriture sur le dossier Plats
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (FileWriter file = new FileWriter(dossier + "/" + nom.toLowerCase() + ".json")) { //on crée une nouvelle référence à un fichier portant le nom de notre futur fichier
            file.write(plat.toJSONString()); //puis on l'écrit
            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("Création de : " + plat); //on écrit dans la console qu'on a créé un nouveau fichier
	}

	/**
	 * Crée un nouveau fichier dans la base de données correspondant à un nouvel ingrédient. Ne pas donner à votre ingrédient le nom d'un ingrédient déjà présent dans la base de données ou celui-ci sera écrasé.
	 * 
	 * @param nom le nom de l'ingrédient.
	 * @param cout le coût de l'ingrédient, c'est à dire une estimation de la quantité d'argent dépensée pour ajouter l'ingrédient au plat pour une portion classique.
	 * @param estDisponible le fait que l'ingrédient soit en stock ou pas.
	 * @param nbUtilisations le nombre d'utilisations de l'ingrédient. Non implémenté pour l'instant.
	 * @param priorite la position de l'ingrédient dans la liste des ingrédients (la liste est triée d'abord par ordre de priorité puis par ordre alphabétique si les priorités sont égales).
	 */
	public static final void creerIngredient(String nom, float cout, boolean estDisponible, int priorite){

		JSONObject ingredient = creerContenuCommande(nom, cout, estDisponible, priorite); //on récupère l'objet JSON avec les attributs communs à tous les contenus commandes

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Ingrédients/").toURI()); //on définit le dossier d'écriture sur le dossier Ingrédients
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (FileWriter file = new FileWriter(dossier + "/" + nom.toLowerCase() + ".json")) { //on crée une nouvelle référence à un fichier portant le nom de notre futur fichier
            file.write(ingredient.toJSONString()); //puis on l'écrit
            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("Création de : " + ingredient); //on écrit dans la console qu'on a créé un nouveau fichier
	}

	/**
	 * Crée un nouveau fichier dans la base de données correspondant à une nouvelle sauce. Ne pas donner à votre sauce le nom d'une sauce déjà présente dans la base de données ou celle-ci sera écrasée.
	 * 
	 * @param nom le nom de la sauce.
	 * @param cout le coût de la sauce, c'est à dire une estimation de la quantité d'argent dépensée pour ajouter la sauce au plat pour une portion classique.
	 * @param estDisponible le fait que la sauce soit en stock ou pas.
	 * @param nbUtilisations le nombre d'utilisations de la sauce. Non implémenté pour l'instant.
	 * @param priorite la position de la sauce dans la liste des sauces (la liste est triée d'abord par ordre de priorité puis par ordre alphabétique si les priorités sont égales).
	 */
	public static final void creerSauce(String nom, float cout, boolean estDisponible, int priorite){

		JSONObject sauce = creerContenuCommande(nom, cout, estDisponible, priorite); //on récupère l'objet JSON avec les attributs communs à tous les contenus commandes

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Sauces/").toURI()); //on définit le dossier d'écriture sur le dossier Sauces
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (FileWriter file = new FileWriter(dossier + "/" + nom.toLowerCase() + ".json")) { //on crée une nouvelle référence à un fichier portant le nom de notre futur fichier
            file.write(sauce.toJSONString()); //puis on l'écrit
            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("Création de : " + sauce); //on écrit dans la console qu'on a créé un nouveau fichier
	}

	/**
	 * Crée un nouveau fichier dans la base de données correspondant à une nouvelle boisson. Ne pas donner à votre boisson le nom d'une boisson déjà présente dans la base de données ou celle-ci sera écrasée.
	 * 
	 * @param nom le nom de la boisson.
	 * @param cout le coût de la boisson, c'est à dire une estimation de la quantité d'argent dépensée pour servir la boisson si celle-ci est servie de manière classique.
	 * @param estDisponible le fait que la boisson soit en stock ou pas.
	 * @param nbUtilisations le nombre d'utilisations de la boisson. Non implémenté pour l'instant.
	 * @param priorite la position de la boisson dans la liste des boissons (la liste est triée d'abord par ordre de priorité puis par ordre alphabétique si les priorités sont égales).
	 */
	public static final void creerBoisson(String nom, float cout, boolean estDisponible, int priorite) {

		JSONObject boisson = creerContenuCommande(nom, cout, estDisponible, priorite); //on récupère l'objet JSON avec les attributs communs à tous les contenus commandes

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Boissons/").toURI()); //on définit le dossier d'écriture sur le dossier Boissons
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (FileWriter file = new FileWriter(dossier + "/" + nom.toLowerCase() + ".json")) { //on crée une nouvelle référence à un fichier portant le nom de notre futur fichier
            file.write(boisson.toJSONString()); //puis on l'écrit
            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("Création de : " + boisson); //on écrit dans la console qu'on a créé un nouveau fichier
	}

	/**
	 * Crée un nouveau fichier dans la base de données correspondant à un nouveau supplément boisson. Ne pas donner à votre supplément boisson le nom d'un supplément boisson déjà présent dans la base de données ou celui-ci sera écrasé.
	 * 
	 * @param nom le nom du supplément boisson.
	 * @param cout le coût du supplément boisson, c'est à dire une estimation de la quantité d'argent dépensée pour ajouter le supplément à une boisson pour une portion classique.
	 * @param estDisponible le fait que le supplément boisson soit en stock ou pas.
	 * @param nbUtilisations le nombre d'utilisations du supplément boisson. Non implémenté pour l'instant.
	 * @param priorite la position du supplément boisson dans la liste des suppléments boisson (la liste est triée d'abord par ordre de priorité puis par ordre alphabétique si les priorités sont égales).
	 * @param prix le prix que doit payer le client pour ajouter ce supplément boisson à sa boisson.
	 */
	@SuppressWarnings("unchecked")
	public static final void creerSupplementBoisson(String nom, float cout, boolean estDisponible, int priorite, float prix) {

		JSONObject supplementBoisson = creerContenuCommande(nom, cout, estDisponible, priorite); //on récupère l'objet JSON avec les attributs communs à tous les contenus commandes
		supplementBoisson.put("prix", prix); //on y ajoute l'attribut prix

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Suppléments Boisson/").toURI()); //on définit le dossier d'écriture sur le dossier Suppléments Boisson
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (FileWriter file = new FileWriter(dossier + "/" + nom.toLowerCase() + ".json")) { //on crée une nouvelle référence à un fichier portant le nom de notre futur fichier
            file.write(supplementBoisson.toJSONString()); //puis on l'écrit
            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("Création de : " + supplementBoisson); //on écrit dans la console qu'on a créé un nouveau fichier
	}

	/**
	 * Crée un nouveau fichier dans la base de données correspondant à un nouveau dessert. Ne pas donner à votre dessert le nom d'un dessert déjà présent dans la base de données ou celui-ci sera écrasé.
	 * 
	 * @param nom le nom du dessert.
	 * @param cout le coût du dessert, c'est à dire une estimation de la quantité d'argent dépensée pour réaliser le dessert pour une portion classique.
	 * @param estDisponible le fait que le dessert soit en stock ou pas.
	 * @param nbUtilisations le nombre d'utilisations du dessert. Non implémenté pour l'instant.
	 * @param priorite la position du dessert dans la liste des desserts (la liste est triée d'abord par ordre de priorité puis par ordre alphabétique si les priorités sont égales).
	 * @param prix le prix que doit payer le client pour ajouter ce dessert à sa commande.
	 */
	@SuppressWarnings("unchecked")
	public static final void creerDessert(String nom, float cout, boolean estDisponible, int priorite, float prix){

		JSONObject dessert = creerContenuCommande(nom, cout, estDisponible, priorite); //on récupère l'objet JSON avec les attributs communs à tous les contenus commandes
		dessert.put("prix", prix); //on y ajoute l'attribut prix

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Desserts/").toURI()); //on définit le dossier d'écriture sur le dossier Desserts
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (FileWriter file = new FileWriter(dossier + "/" + nom.toLowerCase() + ".json")) { //on crée une nouvelle référence à un fichier portant le nom de notre futur fichier
            file.write(dessert.toJSONString()); //puis on l'écrit
            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("Création de : " + dessert); //on écrit dans la console qu'on a créé un nouveau fichier
	}

	/**
	 * Crée un nouveau fichier dans la base de données correspondant à un nouveau membre. Ne pas donner à votre membre le prénom et le nom d'un membre déjà présent dans la base de données ou celui-ci sera écrasé.
	 * 
	 * @param nom le nom de famille du membre.
	 * @param prenom le prénom du membre.
	 * @param surnom le surnom du membre.
	 * @param poste le poste du membre.
	 * @param dateNaissance la date de naissance du membre.
	 */
	@SuppressWarnings("unchecked")
	public static final void creerMembre(String nom, String prenom, String surnom, String poste, Date dateNaissance){

		JSONObject membre = creer(); //on récupère l'objet JSON avec l'id et on y ajoute les attributs spécifiques aux membres
		membre.put("nom", nom);
		membre.put("prenom", prenom);
		membre.put("surnom", surnom);
		membre.put("poste", poste);
		membre.put("dateNaissance", FORMAT_DATE.format(dateNaissance));
		membre.put("nbCommandes", new Integer(0));
		membre.put("nbServices", new Integer(0));
		membre.put("tempsMoyenCommande", 0f);

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Membres/").toURI()); //on définit le dossier d'écriture sur le dossier Membres
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (FileWriter file = new FileWriter(dossier + "/" + prenom.toLowerCase() + " " + nom.toLowerCase() + ".json")) { //on crée une nouvelle référence à un fichier portant le nom de notre futur fichier
            file.write(membre.toJSONString()); //puis on l'écrit
            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("Création de : " + membre); //on écrit dans la console qu'on a créé un nouveau fichier
	}

	/**
	 * Renvoie une base pour les mises à jour de fichiers de contenu commande contenant ce que tous les contenus commande ont en commun.
	 * 
	 * @param id l'identificateur du contenu commande, utilisé dans le cadre de la base de données notamment.
	 * @param nom le nom du contenu commande.
	 * @param cout le coût du contenu commande, c'est à dire une estimation de la quantité d'argent dépensée pour ajouter le contenu commande à la commande pour une portion classique.
	 * @param estDisponible le fait que le contenu commande soit en stock ou pas.
	 * @param priorite la position du contenu commande dans sa liste (la liste est triée d'abord par ordre de priorité puis par ordre alphabétique si les priorités sont égales).
	 * 
	 * @return un {@code JSONObject} prêt à l'emploi pour les contenus commande.
	 */
	@SuppressWarnings("unchecked")
	private static final JSONObject mettreContenuCommandeAJour(String id, String nom, float cout, boolean estDisponible, int priorite){

		JSONObject contenu = new JSONObject();
		contenu.put("id", id);
		contenu.put("nom", nom);
		contenu.put("cout", cout);
		contenu.put("estDisponible", estDisponible);
		contenu.put("nbUtilisations", new Integer(0));
		contenu.put("priorite", priorite);
		return(contenu);
	}

	/**
	 * Met le fichier de la base de données correspondant au plat passé en paramètres à jour, c'est à dire que l'ancien fichier du plat est remplacé par un nouveau contenant les informations mises à jour.
	 * 
	 * @param plat le plat à mettre à jour
	 */
	@SuppressWarnings("unchecked")
	public static final void mettrePlatAJour(Plat plat){

		JSONObject platJson = mettreContenuCommandeAJour(plat.getId(), plat.getNom(), plat.getCout(), plat.getDisponible(), plat.getPriorite()); //on récupère l'objet JSON avec les attributs communs à tous les contenus commandes
		platJson.put("prix", plat.getPrix()); //on y ajoute les attributs spécifiques aux plats
		platJson.put("nbMaxIngredients", plat.getNbMaxIngredients());
		platJson.put("nbMaxSauces", plat.getNbMaxSauces());
		platJson.put("utilisePain", plat.getUtilisePain());

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Plats/").toURI()); //on définit le dossier d'écriture sur le dossier Plats
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(File file : dossier.listFiles()){ //pour chaque fichier du dossier
			if(file.isFile()&&(FilenameUtils.getExtension(file.getName())).equals("json")){ //si le fichier en question est un fichier et que son extension est json
				Plat pla = LecteurBase.lirePlat(FilenameUtils.removeExtension(file.getName())); //on lit le fichier
				if(pla.getId().equals(plat.getId())){ //et si son id est identique à celui du plat passé en paramètres, on supprime son fichier
					supprimerPlat(pla);
				}
			}
		}

		try (FileWriter file = new FileWriter(dossier + "/" + plat.getNom().toLowerCase() + ".json")) { //on crée une nouvelle référence à un fichier portant le nom de notre futur fichier
            file.write(platJson.toJSONString()); //puis on l'écrit
            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("Mise à jour de : " + platJson); //on écrit dans la console qu'on a mis à jour un fichier
	}

	/**
	 * Met le fichier de la base de données correspondant à l'ingrédient passé en paramètres à jour, c'est à dire que l'ancien fichier de l'ingrédient est remplacé par un nouveau contenant les informations mises à jour.
	 * 
	 * @param ingredient l'ingrédient à mettre à jour
	 */
	public static final void mettreIngredientAJour(Ingredient ingredient) {

		JSONObject ingredientJson = mettreContenuCommandeAJour(ingredient.getId(), ingredient.getNom(), ingredient.getCout(), ingredient.getDisponible(), ingredient.getPriorite()); //on récupère l'objet JSON avec les attributs communs à tous les contenus commandes

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Ingrédients/").toURI()); //on définit le dossier d'écriture sur le dossier Ingrédients
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(File file : dossier.listFiles()){ //pour chaque fichier du dossier
			if(file.isFile()&&(FilenameUtils.getExtension(file.getName())).equals("json")){ //si le fichier en question est un fichier et que son extension est json
				Ingredient ing = LecteurBase.lireIngredient(FilenameUtils.removeExtension(file.getName())); //on lit le fichier
				if(ing.getId().equals(ingredient.getId())){ //et si son id est identique à celui de l'ingrédient passé en paramètres, on supprime son fichier
					supprimerIngredient(ing);
				}
			}
		}

		try (FileWriter file = new FileWriter(dossier + "/" + ingredient.getNom().toLowerCase() + ".json")) { //on crée une nouvelle référence à un fichier portant le nom de notre futur fichier
            file.write(ingredientJson.toJSONString()); //puis on l'écrit
            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("Mise à jour de : " + ingredientJson); //on écrit dans la console qu'on a mis à jour un fichier
	}

	/**
	 * Met le fichier de la base de données correspondant à la sauce passée en paramètres à jour, c'est à dire que l'ancien fichier de la sauce est remplacé par un nouveau contenant les informations mises à jour.
	 * 
	 * @param sauce la sauce à mettre à jour
	 */
	public static final void mettreSauceAJour(Sauce sauce) {

		JSONObject sauceJson = mettreContenuCommandeAJour(sauce.getId(), sauce.getNom(), sauce.getCout(), sauce.getDisponible(), sauce.getPriorite()); //on récupère l'objet JSON avec les attributs communs à tous les contenus commandes

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Sauces/").toURI()); //on définit le dossier d'écriture sur le dossier Sauces
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(File file : dossier.listFiles()){ //pour chaque fichier du dossier
			if(file.isFile()&&(FilenameUtils.getExtension(file.getName())).equals("json")){ //si le fichier en question est un fichier et que son extension est json
				Sauce sau = LecteurBase.lireSauce(FilenameUtils.removeExtension(file.getName())); //on lit le fichier
				if(sau.getId().equals(sauce.getId())){ //et si son id est identique à celui de la sauce passée en paramètres, on supprime son fichier
					supprimerSauce(sau);
				}
			}
		}

		try (FileWriter file = new FileWriter(dossier + "/" + sauce.getNom().toLowerCase() + ".json")) { //on crée une nouvelle référence à un fichier portant le nom de notre futur fichier
            file.write(sauceJson.toJSONString()); //puis on l'écrit
            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("Mise à jour de : " + sauceJson); //on écrit dans la console qu'on a mis à jour un fichier
	}

	/**
	 * Met le fichier de la base de données correspondant à la boisson passée en paramètres à jour, c'est à dire que l'ancien fichier de la boisson est remplacé par un nouveau contenant les informations mises à jour.
	 * 
	 * @param boisson la boisson à mettre à jour
	 */
	public static final void mettreBoissonAJour(Boisson boisson) {

		JSONObject boissonJson = mettreContenuCommandeAJour(boisson.getId(), boisson.getNom(), boisson.getCout(), boisson.getDisponible(), boisson.getPriorite()); //on récupère l'objet JSON avec les attributs communs à tous les contenus commandes

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Boissons/").toURI()); //on définit le dossier d'écriture sur le dossier Boissons
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(File file : dossier.listFiles()){ //pour chaque fichier du dossier
			if(file.isFile()&&(FilenameUtils.getExtension(file.getName())).equals("json")){ //si le fichier en question est un fichier et que son extension est json
				Boisson boi = LecteurBase.lireBoisson(FilenameUtils.removeExtension(file.getName())); //on lit le fichier
				if(boi.getId().equals(boisson.getId())){ //et si son id est identique à celui de la sauce passée en paramètres, on supprime son fichier
					supprimerBoisson(boi);
				}
			}
		}

		try (FileWriter file = new FileWriter(dossier + "/" + boisson.getNom().toLowerCase() + ".json")) { //on crée une nouvelle référence à un fichier portant le nom de notre futur fichier
            file.write(boissonJson.toJSONString()); //puis on l'écrit
            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("Mise à jour de : " + boissonJson); //on écrit dans la console qu'on a mis à jour un fichier
	}

	/**
	 * Met le fichier de la base de données correspondant au supplément boisson passé en paramètres à jour, c'est à dire que l'ancien fichier du supplément boisson est remplacé par un nouveau contenant les informations mises à jour.
	 * 
	 * @param supplementBoisson le supplément boisson à mettre à jour
	 */
	@SuppressWarnings("unchecked")
	public static final void mettreSupplementBoissonAJour(SupplementBoisson supplementBoisson) {

		JSONObject supplementBoissonJson = mettreContenuCommandeAJour(supplementBoisson.getId(), supplementBoisson.getNom(), supplementBoisson.getCout(), supplementBoisson.getDisponible(), supplementBoisson.getPriorite()); //on récupère l'objet JSON avec les attributs communs à tous les contenus commandes
		supplementBoissonJson.put("prix", supplementBoisson.getPrix()); //on y ajoute l'attribut prix

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Suppléments Boisson/").toURI()); //on définit le dossier d'écriture sur le dossier Suppléments Boisson
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(File file : dossier.listFiles()){ //pour chaque fichier du dossier
			if(file.isFile()&&(FilenameUtils.getExtension(file.getName())).equals("json")){ //si le fichier en question est un fichier et que son extension est json
				SupplementBoisson sup = LecteurBase.lireSupplementBoisson(FilenameUtils.removeExtension(file.getName())); //on lit le fichier
				if(sup.getId().equals(supplementBoisson.getId())){ //et si son id est identique à celui du plat passé en paramètres, on supprime son fichier
					supprimerSupplementBoisson(sup);
				}
			}
		}

		try (FileWriter file = new FileWriter(dossier + "/" + supplementBoisson.getNom().toLowerCase() + ".json")) { //on crée une nouvelle référence à un fichier portant le nom de notre futur fichier
            file.write(supplementBoissonJson.toJSONString()); //puis on l'écrit
            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("Mise à jour de : " + supplementBoissonJson); //on écrit dans la console qu'on a mis à jour un fichier
	}

	/**
	 * Met le fichier de la base de données correspondant au dessert passé en paramètres à jour, c'est à dire que l'ancien fichier du dessert est remplacé par un nouveau contenant les informations mises à jour.
	 * 
	 * @param dessert le dessert à mettre à jour
	 */
	@SuppressWarnings("unchecked")
	public static final void mettreDessertAJour(Dessert dessert) {

		JSONObject dessertJson = mettreContenuCommandeAJour(dessert.getId(), dessert.getNom(), dessert.getCout(), dessert.getDisponible(), dessert.getPriorite()); //on récupère l'objet JSON avec les attributs communs à tous les contenus commandes
		dessertJson.put("prix", dessert.getPrix()); //on y ajoute l'attribut prix

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Desserts/").toURI()); //on définit le dossier d'écriture sur le dossier Desserts
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(File file : dossier.listFiles()){ //pour chaque fichier du dossier
			if(file.isFile()&&(FilenameUtils.getExtension(file.getName())).equals("json")){ //si le fichier en question est un fichier et que son extension est json
				Dessert des = LecteurBase.lireDessert(FilenameUtils.removeExtension(file.getName())); //on lit le fichier
				if(des.getId().equals(dessert.getId())){ //et si son id est identique à celui du plat passé en paramètres, on supprime son fichier
					supprimerDessert(des);
				}
			}
		}

		try (FileWriter file = new FileWriter(dossier + "/" + dessert.getNom().toLowerCase() + ".json")) { //on crée une nouvelle référence à un fichier portant le nom de notre futur fichier
            file.write(dessertJson.toJSONString()); //puis on l'écrit
            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("Mise à jour de : " + dessertJson); //on écrit dans la console qu'on a mis à jour un fichier
	}

	/**
	 * Met le fichier de la base de données correspondant au membre passé en paramètres à jour, c'est à dire que l'ancien fichier du membre est remplacé par un nouveau contenant les informations mises à jour.
	 * 
	 * @param membre le membre à mettre à jour
	 */
	@SuppressWarnings("unchecked")
	public static final void mettreMembreAJour(Membre membre){

		JSONObject membreJson = new JSONObject();
		membreJson.put("id", membre.getId());
		membreJson.put("nom", membre.getNom());
		membreJson.put("prenom", membre.getPrenom());
		membreJson.put("poste", membre.getPoste());
		membreJson.put("dateNaissance", FORMAT_DATE.format(membre.getDateNaissance()));
		membreJson.put("nbCommandes", membre.getNbCommandes());
		membreJson.put("nbServices", membre.getNbServices());
		membreJson.put("tempsMoyenCommande", membre.getTempsMoyenCommande());

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Membres/").toURI()); //on définit le dossier d'écriture sur le dossier Membres
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(File file : dossier.listFiles()){ //pour chaque fichier du dossier
			if(file.isFile()&&(FilenameUtils.getExtension(file.getName())).equals("json")){ //si le fichier en question est un fichier et que son extension est json
				Membre mem = LecteurBase.lireMembre(FilenameUtils.removeExtension(file.getName())); //on lit le fichier
				if(mem.getId().equals(membre.getId())){ //et si son id est identique à celui du plat passé en paramètres, on supprime son fichier
					supprimerMembre(mem);
				}
			}
		}

		try (FileWriter file = new FileWriter(dossier + "/" + membre.getPrenom().toLowerCase() + " " + membre.getNom().toLowerCase() + ".json")) { //on crée une nouvelle référence à un fichier portant le nom de notre futur fichier
            file.write(membreJson.toJSONString()); //puis on l'écrit
            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("Mise à jour de : " + membre); //on écrit dans la console qu'on a mis à jour un fichier
	}

	/**
	 * Renvoie une base pour les nouveaux fichiers de commande contenant ce que toutes les commandes ont en commun.
	 * 
	 * @param numero le numéro de la commande dans le service au sein duquel elle a été créée.
	 * @param moment le moment (un point dans le temps) où la commande a été créée.
	 * @param plat le plat de la commande.
	 * @param ingredients une {@code List<Ingredient>} contenant tous les ingrédients de la commande.
	 * @param sauces une {@code List<Sauce>} contenant toutes les sauces de la commande.
	 * @param dessert le dessert de la commande.
	 * @param boisson la boisson de la commande.
	 * @param supplementBoisson le supplément boisson de la commande.
	 * @param prix le prix que paye le client pour formuler sa commande.
	 * 
	 * @return un {@code JSONObject} contenant toutes les informations de la commande.
	 */
	@SuppressWarnings("unchecked")
	private static final JSONObject objetCommande(int numero, Date moment, Plat plat, List<Ingredient> ingredients, List<Sauce> sauces, Dessert dessert, Boisson boisson, SupplementBoisson supplementBoisson, float prix){

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

	/**
	 * Écrit un fichier dans la base de données décrivant la commande passée en paramètres.
	 * 
	 * @param numero le numéro de la commande au sein de son service. Il servira pour le nom du fichier à écrire.
	 * @param moment le moment auquel la commande a été créée, utile pour déterminer sa date et donc son emplacement dans l'arborescence de la base de données.
	 * @param commande la commande à écrire, sous la forme d'un {@code JSONObject}.
	 */
	private static final void ecrireCommande(int numero, Date moment, JSONObject commande){

		final SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		final SimpleDateFormat mois = new SimpleDateFormat("MM");
		final SimpleDateFormat jour = new SimpleDateFormat("dd");

		String zeros = "";

		if(numero < 100){ //si le numéro de commande est inférieur à 100, on ajoute un 0 devant (pour que les commandes apparaissent dans l'ordre lorsqu'elle sont visualisées dans le navigateur de fichiers)
			zeros += "0";
			if(numero < 10){ //de même si le numéro est inférieur à 10, on ajoute un autre 0
				zeros += "0";
			}
		}

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Services/").toURI()); //on définit le dossier d'écriture sur le dossier Services
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
				Files.createDirectories(Paths.get(dossier + "/" + annee.format(moment) + "/" + mois.format(moment) + "/" + jour.format(moment) + "/")); //on crée les dossiers de l'arborescence dont on a besoin pour écrire le fichier, s'ils n'existaient pas déjà
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (FileWriter file = new FileWriter(dossier + "/" + annee.format(moment) + "/" + mois.format(moment) + "/" + jour.format(moment) + "/" + zeros + numero + ".json")) { //on crée une nouvelle référence au futur fichier de la commande à l'emplacement Services/annee/mois/jour/numero de commande.json
	            file.write(commande.toJSONString()); //puis on l'écrit
	            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
	            file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Écriture de : " +  commande); //on indique dans la console qu'on a écrit un fichier de commande
	}

	/**
	 * Ajoute (ou met à jour) un fichier de commande à la base de données.
	 * 
	 * @param numero le numéro de la commande au sein de son service. Il servira pour le nom du fichier à écrire.
	 * @param moment le moment auquel la commande a été créée, utile pour déterminer sa date et donc son emplacement dans l'arborescence de la base de données.
	 * @param plat le plat de la commande.
	 * @param ingredients une {@code List<Ingredient>} contenant tous les ingrédients de la commande.
	 * @param sauces une {@code List<Sauce>} contenant toutes les sauces de la commande.
	 * @param dessert le dessert de la commande.
	 * @param boisson la boisson de la commande.
	 * @param supplementBoisson le supplément boisson de la commande.
	 * @param prix le prix que paye le client pour formuler sa commande.
	 */
	@SuppressWarnings("unchecked")
	private static final void ajouterCommande(int numero, Date moment, Plat plat, List<Ingredient> ingredients, List<Sauce> sauces, Dessert dessert, Boisson boisson, SupplementBoisson supplementBoisson, float prix){

		JSONObject commande = objetCommande(numero, moment, plat, ingredients, sauces, dessert, boisson, supplementBoisson, prix); //on crée un JSONObject contenant les informations de la commande

		commande.put("assignee", false); //on lui ajoute un attribut assignee à false, utile lors de la lecture

		ecrireCommande(numero, moment, commande); //on écrit la commande
	}

	/**
	 * Ajoute (ou met à jour) un fichier de commande à la base de données. Permet de ne passer que l'objet commande en paramètres.
	 * 
	 * @param commande la commande à écrire dans la base de données.
	 */
	public static final void ajouterCommande(Commande commande){

		ajouterCommande(commande.getNumero(), commande.getMoment(), commande.getPlat(), commande.getIngredients(), commande.getSauces(), commande.getDessert(), commande.getBoisson(), commande.getSupplementBoisson(), commande.getPrix());
	}

	/**
	 * Ajoute (ou met à jour) un fichier de commande assignée à la base de données.
	 * 
	 * @param numero le numéro de la commande au sein de son service. Il servira pour le nom du fichier à écrire.
	 * @param moment le moment auquel la commande a été créée, utile pour déterminer sa date et donc son emplacement dans l'arborescence de la base de données.
	 * @param plat le plat de la commande.
	 * @param ingredients une {@code List<Ingredient>} contenant tous les ingrédients de la commande.
	 * @param sauces une {@code List<Sauce>} contenant toutes les sauces de la commande.
	 * @param dessert le dessert de la commande.
	 * @param boisson la boisson de la commande.
	 * @param supplementBoisson le supplément boisson de la commande.
	 * @param prix le prix que paye le client pour formuler sa commande.
	 * @param membre le membre à qui la commande a été assignée.
	 * @param momentAssignation le moment où la commande a été assignée.
	 * @param estRealisee le fait que la commande soit réalisée.
	 * @param momentRealisation le moment où la commande a été réalisée si elle est réalisée.
	 * @param estDonnee le fait que la commande soit donnée.
	 */
	@SuppressWarnings("unchecked")
	private static final void ajouterCommandeAssignee(int numero, Date moment, Plat plat, List<Ingredient> ingredients, List<Sauce> sauces, Dessert dessert, Boisson boisson, SupplementBoisson supplementBoisson, float prix, Membre membre, Date momentAssignation, boolean estRealisee, Date momentRealisation, boolean estDonnee){

		JSONObject commandeAssignee = objetCommande(numero, moment, plat, ingredients, sauces, dessert, boisson, supplementBoisson, prix); //on crée un JSONObject contenant les informations de la commande

		commandeAssignee.put("assignee", true); //on lui ajoute un attribut assignee à false, utile lors de la lecture, ainsi que les attributs spécifiques aux commandes assignées
		commandeAssignee.put("membre", membre.getId());
		commandeAssignee.put("momentAssignation", momentAssignation.getTime());
		commandeAssignee.put("estRealisee", estRealisee);
		commandeAssignee.put("momentRealisation", momentRealisation.getTime());
		commandeAssignee.put("estDonnee", estDonnee);

		ecrireCommande(numero, moment, commandeAssignee); //on écrit la commande
	}

	/**
	 * Ajoute (ou met à jour) un fichier de commande assignée à la base de données. Permet de ne passer que l'objet commande assignée en paramètres.
	 * 
	 * @param commandeAssignee la commande assignée à écrire dans la base de données.
	 */
	public static final void ajouterCommandeAssignee(CommandeAssignee commandeAssignee){

		ajouterCommandeAssignee(commandeAssignee.getNumero(), commandeAssignee.getMoment(), commandeAssignee.getPlat(), commandeAssignee.getIngredients(), commandeAssignee.getSauces(), commandeAssignee.getDessert(), commandeAssignee.getBoisson(), commandeAssignee.getSupplementBoisson(), commandeAssignee.getPrix(), commandeAssignee.getMembre(), commandeAssignee.getMomentAssignation(), commandeAssignee.getEstRealisee(), commandeAssignee.getMomentRealisation(), commandeAssignee.getEstDonnee());
	}

	/**
	 * Renvoie une base pour les nouveaux fichiers de service.
	 * 
	 * @param nbBaguettesUtilisees le nombre de baguettes qui ont été utilisées lors du service.
	 * @param nbBaguettesAchetees le nombre de baguettes qui ont été achetées pour le service.
	 * @param nbBaguettesReservees le nombre de baguettes qui ont été réservées pour les membres effectuant le service.
	 * @param nbCommandes le nombre de commandes effectuées dans le service.
	 * @param date la date du service.
	 * @param cout le coût total des commandes du service, c'est à dire une estimation de la quantité d'argent dépensée pour réaliser toutes les commandes servies.
	 * @param revenu le revenu total des commandes du service, c'est à dire une estimation de la quantité d'argent qu'a rapporté le service.
	 * @param ordi le membre qui s'occupait de prendre les commandes lors du service.
	 * @param commis une {@code List<Membre>} contenant les membres qui étaient chargés d'assembler les commandes et de préparer les boissons et les desserts lors du service.
	 * @param confection une {@code List<Membre>} contenant les membres qui étaient chargés de confectionner les plats lors du service.
	 * 
	 * @return un {@code JSONObject} contenant toutes les informations du service.
	 */
	@SuppressWarnings("unchecked")
	private static final JSONObject objetService(float nbBaguettesUtilisees, float nbBaguettesAchetees, float nbBaguettesReservees, int nbCommandes, Date date, float cout, float revenu, Membre ordi, List<Membre> commis, List<Membre> confection){

		JSONObject service = new JSONObject();

		service.put("nbBaguettesUtilisees", nbBaguettesUtilisees);
		service.put("nbBaguettesAchetees", nbBaguettesAchetees);
		service.put("nbBaguettesReservees", nbBaguettesReservees);		
		service.put("nbCommandes", nbCommandes);
		service.put("date", FORMAT_DATE.format(date));
		service.put("cout", cout);
		service.put("revenu", revenu);
		service.put("marge", revenu - cout);
		service.put("ordi", ordi.getId());

		JSONArray listeCommis = new JSONArray();
		for(Membre membre : commis){
			listeCommis.add(membre.getId());
		}
		
		service.put("commis", listeCommis);

		JSONArray listeConfection = new JSONArray();
		for(Membre membre : confection){
			listeConfection.add(membre.getId());
		}

		service.put("confection", listeConfection);

		return(service);
	}

	/**
	 * Écrit un fichier dans la base de données décrivant le service passé en paramètres.
	 * 
	 * @param date la date du service, utile pour déterminer son emplacement dans l'arborescence de la base de données.
	 * @param service le service à écrire, sous la forme d'un {@code JSONObject}.
	 */
	private static final void ecrireService(Date date, JSONObject service){

		final SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		final SimpleDateFormat mois = new SimpleDateFormat("MM");
		final SimpleDateFormat jour = new SimpleDateFormat("dd");

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Services/").toURI()); //on définit le dossier d'écriture sur le dossier Services
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
				Files.createDirectories(Paths.get(dossier + "/" + annee.format(date) + "/" + mois.format(date) + "/" + jour.format(date) + "/")); //on crée les dossiers de l'arborescence dont on a besoin pour écrire le fichier, s'ils n'existaient pas déjà
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (FileWriter file = new FileWriter(dossier + "/" + annee.format(date) + "/" + mois.format(date) + "/" + jour.format(date) + "/" + "_service" + ".json")) { //on crée une nouvelle référence au futur fichier du service à l'emplacement Services/annee/mois/jour/_service.json (le _ c'est pour que le fichier soit tout en haut de la liste de fichiers lorsque celle-ci est visualisée dans le navigateur de fichiers)
	            file.write(service.toJSONString()); //puis on l'écrit
	            file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
	            file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Écriture de : " + service); //on indique dans la console qu'on a écrit un fichier de service
	}

	/**
	 * Ajoute (ou met à jour) un fichier de service à la base de données.
	 * 
	 * @param nbBaguettesUtilisees le nombre de baguettes qui ont été utilisées lors du service.
	 * @param nbBaguettesAchetees le nombre de baguettes qui ont été achetées pour le service.
	 * @param nbBaguettesReservees le nombre de baguettes qui ont été réservées pour les membres effectuant le service.
	 * @param nbCommandes le nombre de commandes effectuées dans le service.
	 * @param date la date du service.
	 * @param cout le coût total des commandes du service, c'est à dire une estimation de la quantité d'argent dépensée pour réaliser toutes les commandes servies.
	 * @param revenu le revenu total des commandes du service, c'est à dire une estimation de la quantité d'argent qu'a rapporté le service.
	 * @param ordi le membre qui s'occupait de prendre les commandes lors du service.
	 * @param commis une {@code List<Membre>} contenant les membres qui étaient chargés d'assembler les commandes et de préparer les boissons et les desserts lors du service.
	 * @param confection une {@code List<Membre>} contenant les membres qui étaient chargés de confectionner les plats lors du service.
	 */
	private static final void ajouterService(float nbBaguettesUtilisees, float nbBaguettesAchetees, float nbBaguettesReservees, int nbCommandes, Date date, float cout, float revenu, Membre ordi, List<Membre> commis, List<Membre> confection){

		JSONObject service = objetService(nbBaguettesUtilisees, nbBaguettesAchetees, nbBaguettesReservees, nbCommandes, date, cout, revenu, ordi, commis, confection); //on crée un JSONObject contenant les informations du service

		ecrireService(date, service); //on écrit le service
	}

	/**
	 * Ajoute (ou met à jour) un fichier de service à la base de données. Permet de ne passer que l'objet service en paramètres.
	 * 
	 * @param service le service à écrire dans la base de données.
	 */
	public static final void ajouterService(Service service){

		ajouterService(service.getNbBaguettesUtilisees(), service.getNbBaguettesAchetees(), service.getNbBaguettesReservees(), service.getNbCommandes(), service.getDate(), service.getCout(), service.getRevenu(), service.getOrdi(), service.getCommis(), service.getConfection());
	}

	/**
	 * Renvoie une base pour les nouveaux fichiers de paramètres.
	 * 
	 * @param prixIngredientSupp le prix d'un ingrédient supplémentaire (lorsque le client choisit plus d'ingrédients que ce que le plat autorise d'ordinaire).
	 * @param prixBoisson le prix d'une boisson.
	 * @param reducMenu le montant de la réduction accordée si le client choisit un plat, une boisson et un dessert pour sa commande.
	 * 
	 * @return un {@code JSONObject} contenant toutes les informations des paramètres.
	 */
	@SuppressWarnings("unchecked")
	private static final JSONObject objetParametres(float prixIngredientSupp, float prixBoisson, float reducMenu, float coutPain){

		JSONObject parametres = new JSONObject();

		parametres.put("prixIngredientSupp", prixIngredientSupp);
		parametres.put("prixBoisson", prixBoisson);
		parametres.put("reducMenu", reducMenu);
		parametres.put("coutPain", coutPain);

		return(parametres);
	}

	/**
	 * Écrit un fichier dans la base de données décrivant les paramètres passés en paramètres.
	 * 
	 * @param parametres les paramètres à écrire, sous la forme d'un {@code JSONObject}.
	 */
	private static final void ecrireParametres(JSONObject parametres){

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Paramètres/").toURI()); //on définit le dossier d'écriture sur le dossier Paramètres
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Files.createDirectories(Paths.get(dossier + "/")); //on crée le dossier s'il n'existait pas déjà
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (FileWriter file = new FileWriter(dossier + "/" + "paramètres.json")) { //on crée une nouvelle référence au futur fichier des paramètres à l'emplacement Paramètres/paramètres.json
				file.write(parametres.toJSONString()); //puis on l'écrit
				file.flush(); //on oublie pas de flush et de fermer le fichier sans quoi on risquerait de gros bugs
	            file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Écriture de : " + parametres); //on indique dans la console qu'on a écrit le fichier de paramètres
	}

	/**
	 * Ajoute (ou met à jour) un fichier de paramètres à la base de données.
	 * 
	 * @param prixIngredientSupp le prix d'un ingrédient supplémentaire (lorsque le client choisit plus d'ingrédients que ce que le plat autorise d'ordinaire).
	 * @param prixBoisson le prix d'une boisson.
	 * @param reducMenu le montant de la réduction accordée si le client choisit un plat, une boisson et un dessert pour sa commande.
	 */
	private static final void ajouterParametres(float prixIngredientSupp, float prixBoisson, float reducMenu, float coutPain){

		JSONObject parametres = objetParametres(prixIngredientSupp, prixBoisson, reducMenu, coutPain); //on crée un JSONObject contenant les informations des paramètres

		ecrireParametres(parametres); //on écrit les paramètres
	}

	/**
	 * Ajoute (ou met à jour) un fichier de paramètres à la base de données. Permet de ne passer que l'objet paramètres en paramètres.
	 * 
	 * @param parametres les paramètres à écrire dans la base de données.
	 */
	public static final void ajouterParametres(){

		ajouterParametres(Parametres.getPrixIngredientSupp(), Parametres.getPrixBoisson(), Parametres.getReducMenu(), Parametres.getCoutPain());
	}

	/**
	 * Supprime un plat de la base de données. Celui-ci est retiré définitivement.
	 * 
	 * @param plat le plat à supprimer de la base de données.
	 */
	public static final void supprimerPlat(ContenuCommande plat){

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Plats/").toURI()); //on définit le dossier d'écriture sur le dossier Plats
		} catch (Exception e) {
			e.printStackTrace();
		}

		File fichier = new File(dossier + "/" + plat.getNom().toLowerCase() + ".json"); //on crée une nouvelle référence vers le fichier qu'on souhaite supprimer
		fichier.delete(); //et on le supprime
	}

	/**
	 * Supprime un ingrédient de la base de données. Celui-ci est retiré définitivement.
	 * 
	 * @param ingredient l'ingrédient à supprimer de la base de données.
	 */
	public static final void supprimerIngredient(ContenuCommande ingredient){

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Ingrédients/").toURI()); //on définit le dossier d'écriture sur le dossier Ingrédients
		} catch (Exception e) {
			e.printStackTrace();
		}

		File fichier = new File(dossier + "/" + ingredient.getNom().toLowerCase() + ".json"); //on crée une nouvelle référence vers le fichier qu'on souhaite supprimer
		fichier.delete(); //et on le supprime
	}

	/**
	 * Supprime une sauce de la base de données. Celle-ci est retirée définitivement.
	 * 
	 * @param sauce la sauce à supprimer de la base de données.
	 */
	public static final void supprimerSauce(ContenuCommande sauce){

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Sauces/").toURI()); //on définit le dossier d'écriture sur le dossier Ingrédients
		} catch (Exception e) {
			e.printStackTrace();
		}

		File fichier = new File(dossier + "/" + sauce.getNom().toLowerCase() + ".json"); //on crée une nouvelle référence vers le fichier qu'on souhaite supprimer
		fichier.delete(); //et on le supprime
	}

	/**
	 * Supprime une boisson de la base de données. Celle-ci est retirée définitivement.
	 * 
	 * @param boisson la boisson à retirer de la base de données.
	 */
	public static final void supprimerBoisson(ContenuCommande boisson){

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Boissons/").toURI()); //on définit le dossier d'écriture sur le dossier Boissons
		} catch (Exception e) {
			e.printStackTrace();
		}

		File fichier = new File(dossier + "/" + boisson.getNom().toLowerCase() + ".json"); //on crée une nouvelle référence vers le fichier qu'on souhaite supprimer
		fichier.delete(); //et on le supprime
	}

	/**
	 * Supprime un supplément boisson de la base de données. Celui-ci est retiré définitivement.
	 * 
	 * @param supplementBoisson le supplément boisson à supprimer de la base de données.
	 */
	public static final void supprimerSupplementBoisson(ContenuCommande supplementBoisson){

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Suppléments Boisson/").toURI()); //on définit le dossier d'écriture sur le dossier Suppléments Boisson
		} catch (Exception e) {
			e.printStackTrace();
		}

		File fichier = new File(dossier + "/" + supplementBoisson.getNom().toLowerCase() + ".json"); //on crée une nouvelle référence vers le fichier qu'on souhaite supprimer
		fichier.delete(); //et on le supprime
	}

	/**
	 * Supprime un dessert de la base de données. Celui-ci est retiré définitivement.
	 * 
	 * @param dessert le dessert à supprimer de la base de données.
	 */
	public static final void supprimerDessert(ContenuCommande dessert){

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Contenus Commandes/Desserts/").toURI()); //on définit le dossier d'écriture sur le dossier Desserts
		} catch (Exception e) {
			e.printStackTrace();
		}

		File fichier = new File(dossier + "/" + dessert.getNom().toLowerCase() + ".json"); //on crée une nouvelle référence vers le fichier qu'on souhaite supprimer
		fichier.delete(); //et on le supprime
	}

	/**
	 * Supprime un membre de la base de données. Celui-ci est retiré définitivement.
	 * 
	 * @param membre le membre à supprimer de la base de données.
	 */
	public static final void supprimerMembre(Membre membre){

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Membres").toURI()); //on définit le dossier d'écriture sur le dossier Membres
		} catch (Exception e) {
			e.printStackTrace();
		}

		File fichier = new File(dossier + "/" + membre.getPrenom().toLowerCase() + " " + membre.getNom().toLowerCase() + ".json"); //on crée une nouvelle référence vers le fichier qu'on souhaite supprimer
		fichier.delete(); //et on le supprime
	}

	/**
	 * Retire définitivement une commande de la base de données.
	 * 
	 * @param commande la commande à retirer de la base de données.
	 */
	public static final void retirerCommande(Commande commande){

		final SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		final SimpleDateFormat mois = new SimpleDateFormat("MM");
		final SimpleDateFormat jour = new SimpleDateFormat("dd");

		String zeros = "";

		if(commande.getNumero() < 100){  //si le numéro de commande est inférieur à 100, on ajoute un 0 devant (pour que les commandes apparaissent dans l'ordre lorsqu'elle sont visualisées dans le navigateur de fichiers)
			zeros += "0";
			if(commande.getNumero() < 10){ //de même si le numéro est inférieur à 10, on ajoute un autre 0
				zeros += "0";
			}
		}

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Services").toURI()); //on définit le dossier d'écriture sur le dossier Membres
		} catch (Exception e) {
			e.printStackTrace();
		}

		File fichier = new File(dossier + "/" + annee.format(commande.getMoment()) + "/" + mois.format(commande.getMoment()) + "/" + jour.format(commande.getMoment()) + "/" + zeros + commande.getNumero() + ".json"); //on crée une nouvelle référence vers le fichier qu'on souhaite supprimer
		fichier.delete(); //et on le supprime
	}
}
