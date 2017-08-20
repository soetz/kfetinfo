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

import java.io.File;

/**
 * <p>Parametres est une classe constituée uniquement d'attributs et de méthodes statiques permettant de gérer les paramètres du logiciel.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public final class Parametres {

	//paramètres par défaut du logiciel
	public static final float PRIX_INGREDIENT_SUPP_DEFAUT = 0.3f;
	public static final float PRIX_BOISSON_DEFAUT = 0.5f;
	public static final float REDUC_MENU_DEFAUT = 0.3f;
	public static final float COUT_PAIN_DEFAUT = 0.5f;

	//variables de la classe
	static float prixIngredientSupp = PRIX_INGREDIENT_SUPP_DEFAUT;
	static float prixBoisson = PRIX_BOISSON_DEFAUT;
	static float reducMenu = REDUC_MENU_DEFAUT;
	static float coutPain = COUT_PAIN_DEFAUT;

	/**
	 * Affecte aux variables de la classe les valeurs stockées dans la base de données.
	 */
	public static final void initialiser(){

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Paramètres/").toURI()); //on définit le dossier de lecture sur le dossier Paramètres
		} catch (Exception e) {
			e.printStackTrace();
		}

		File f = new File(dossier + "/" + "paramètres.json");
		if(f.exists() && !f.isDirectory()){ //si le fichier paramètres existe, on affecte les valeurs qui s'y trouvent à notre classe
			LecteurBase.lireParametres();
		}

		ecrireFichier();
	}

	/**
	 * Renvoie le prix que doit payer le client chaque fois qu'il ajoute un {@code Ingredient} à sa commande alors que le nombre maximal d'ingrédients du plat est atteint.
	 * 
	 * @return le prix d'un ingrédient en supplément.
	 */
	public static final float getPrixIngredientSupp(){

		return(prixIngredientSupp);
	}

	/**
	 * Renvoie le prix d'une {@code Boisson}.
	 * 
	 * @return le prix d'une boisson.
	 */
	public static final float getPrixBoisson(){

		return(prixBoisson);
	}

	/**
	 * Renvoie le montant de la réduction accordée à un membre choisissant un plat, une boisson et un dessert à sa commande.
	 * 
	 * @return la réduction en cas de menu.
	 */
	public static final float getReducMenu(){

		return(reducMenu);
	}

	/**
	 * Renvoie le coût d'une baguette de pain.
	 * 
	 * @return le coût d'une baguette de pain.
	 */
	public static final float getCoutPain(){

		return(coutPain);
	}

	/**
	 * Modifie le prix d'un ingrédient en supplément en lui affectant la valeur passée en paramètres.
	 * 
	 * @param prix le nouveau prix d'un ingrédient en supplément.
	 */
	public static final void setPrixIngredientSupp(float prix){

		prixIngredientSupp = prix;
	}

	/**
	 * Modifie le prix d'une boisson en lui affectant la valeur passée en paramètres.
	 * 
	 * @param prix le nouveau prix d'une boisson.
	 */
	public static final void setPrixBoisson(float prix){

		prixBoisson = prix;
	}

	/**
	 * Modifie le montant de la réduction accordée à un membre choisissant un plat, une boisson et un dessert à sa commande en lui affectant la valeur passée en paramètres.
	 * 
	 * @param reduc le nouveau montant de la réduction en cas de menu.
	 */
	public static final void setReducMenu(float reduc){

		reducMenu = reduc;
	}

	/**
	 * Modifie le coût d'une baguette de pain en lui affectant la valeur passée en paramètres.
	 * 
	 * @param cout le nouveau coût d'une baguette de pain.
	 */
	public static final void setCoutPain(float cout){

		coutPain = cout;
	}

	/**
	 * Met à jour le fichier correspondant aux paramètres dans la base de données.
	 */
	public static final void ecrireFichier(){

		CreateurBase.ajouterParametres();
	}
}
