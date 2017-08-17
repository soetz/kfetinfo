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
import java.util.Objects;
import java.util.ArrayList;

import java.util.Date;

/**
 * <p>Commande est une classe décrivant une commande pouvant être formulée par un client pendant un service de la K'Fet.</p>
 * <p>Une commande est identifiée par son {@code numero}. Un objet {@code Commande} décrit aussi les différents {@code ContenuCommande} qui la constituent, mais aussi le moment (le point dans le temps) où elle a été formulée.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class Commande {

	//attributs de la classe
	private Date moment;
	private int numero;
	private Plat plat;
	private List<Ingredient> ingredients;
	private List<Sauce> sauces;
	private Dessert dessert;
	private Boisson boisson;
	private SupplementBoisson supplementBoisson;

	/**
	 * <p>Constructeur Commande.</p>
	 * <p>Constructeur permettant de créer une nouvelle commande au sein d'un service.</p>
	 * <p>Tous les paramètres spécifiés sont appliqués aux attributs de l'objet directement.</p>
	 * 
	 * @param moment le moment auquel la commande a été créée.
	 * @param plat le plat de la commande.
	 * @param ingredients une {@code List<Ingredient>} contenant tous les ingrédients de la commande.
	 * @param sauces une {@code List<Sauce>} contenant toutes les sauces de la commande.
	 * @param dessert le dessert de la commande.
	 * @param boisson la boisson de la commande.
	 * @param supplementBoisson le supplément boisson de la commande.
	 * @param service le service au sein duquel la commande est créée.
	 */
	public Commande(Date moment, Plat plat, List<Ingredient> ingredients, List<Sauce> sauces, Dessert dessert, Boisson boisson, SupplementBoisson supplementBoisson, Service service){

		this.moment = moment;
		this.numero = service.getDernierNumeroCommande() + 1;
		this.plat = plat;
		this.ingredients = ingredients;
		this.sauces = sauces;
		this.dessert = dessert;
		this.boisson = boisson;
		this.supplementBoisson = supplementBoisson;
	}

	/**
	 * <p>Constructeur Commande.</p>
	 * <p>Constructeur permettant de créer une nouvelle commande alors que son numéro est déjà connu.</p>
	 * <p>Tous les paramètres sont appliqués aux attributs de l'objet directement.</p>
	 * 
	 * @param moment le moment auquel la commande a été créée.
	 * @param numero le numéro de la commande.
	 * @param plat le plat de la commande.
	 * @param ingredients une {@code List<Ingredient>} contenant tous les ingrédients de la commande.
	 * @param sauces une {@code List<Sauce>} contenant toutes les sauces de la commande.
	 * @param dessert le dessert de la commande.
	 * @param boisson la boisson de la commande.
	 * @param supplementBoisson le supplément boisson de la commande.
	 */
	public Commande(Date moment, int numero, Plat plat, List<Ingredient> ingredients, List<Sauce> sauces, Dessert dessert, Boisson boisson, SupplementBoisson supplementBoisson){

		this.moment = moment;
		this.numero = numero;
		this.plat = plat;
		this.ingredients = ingredients;
		this.sauces = sauces;
		this.dessert = dessert;
		this.boisson = boisson;
		this.supplementBoisson = supplementBoisson;
	}

	/**
	 * <p>Constructeur Commande.</p>
	 * <p>Constructeur permettant d'initialiser une commande vide.</p>
	 * 
	 * @param rienPlat le plat correspondant à aucune sélection de plat.
	 * @param rienBoisson la boisson correspondant à aucune sélection de boisson.
	 * @param rienSupplementBoisson le supplément boisson correspondant à aucune sélection de supplément boisson.
	 * @param rienDessert le dessert correspondant à aucune sélection de dessert.
	 */
	private Commande(Plat rienPlat, Boisson rienBoisson, SupplementBoisson rienSupplementBoisson, Dessert rienDessert){

		moment = new Date();
		numero = Core.getService().getDernierNumeroCommande() + 1;
		plat = rienPlat;
		ingredients = new ArrayList<Ingredient>();
		sauces = new ArrayList<Sauce>();
		dessert = rienDessert;
		boisson = rienBoisson;
		supplementBoisson = rienSupplementBoisson;
	}

	/**
	 * <p>Constructeur Commande.</p>
	 * <p>Constructeur permettant d'initialiser une commande vide.</p>
	 */
	public Commande(){

		this(BaseDonnees.getRienPlat(), BaseDonnees.getRienBoisson(), BaseDonnees.getRienSupplementBoisson(), BaseDonnees.getRienDessert());
	}

	/**
	 * Renvoie le {@code moment} de création de cette commande.
	 * 
	 * @return le moment où cette commande a été créée.
	 */
	public Date getMoment(){

		return(moment);
	}

	/**
	 * Renvoie le {@code numero} de la commande.
	 * 
	 * @return le numéro de la commande.
	 */
	public int getNumero(){

		return(numero);
	}

	/**
	 * Renvoie le {@code plat} de la commande.
	 * 
	 * @return le plat de la commande.
	 */
	public Plat getPlat(){

		return(plat);
	}

	/**
	 * Renvoie la {@code List<Ingredient>} décrivant la liste des ingrédients de cette commande.
	 * 
	 * @return la liste des ingrédients de cette commande.
	 */
	public List<Ingredient> getIngredients(){

		return(ingredients);
	}

	/**
	 * Renvoie la {@code List<Sauce>} décrivant la liste des sauces de cette commande.
	 * 
	 * @return la liste des sauces de cette commande.
	 */
	public List<Sauce> getSauces(){

		return(sauces);
	}

	/**
	 * Renvoie la {@code boisson} de la commande.
	 * 
	 * @return la boisson de la commande.
	 */
	public Boisson getBoisson(){

		return(boisson);
	}

	/**
	 * Renvoie le {@code supplementBoisson} de la commande.
	 * 
	 * @return le supplément boisson de la commande.
	 */
	public SupplementBoisson getSupplementBoisson(){

		return(supplementBoisson);
	}

	/**
	 * Renvoie le {@code dessert} de la commande.
	 * 
	 * @return le dessert de la commande.
	 */
	public Dessert getDessert(){

		return(dessert);
	}

	/**
	 * Renvoie le prix de la commande, c'est à dire la quantité d'argent que devra débourser un client pour formuler cette commande.
	 * 
	 * @return le prix de la commande.
	 */
	public float getPrix(){

		return(prixCommande(this.plat, this.ingredients, this.boisson, this.supplementBoisson, this.dessert));
	}

	/**
	 * Renvoie le coût de la commande, c'est à dire une estimation de la quantité d'argent qui doit être dépensée pour réaliser la commande.
	 *
	 * @return le coût de la commande.
	 */
	public float getCout(){

		float cout = 0;

		cout += getPlat().getCout();

		for(Ingredient ingredient : ingredients){
			cout += ingredient.getCout();
		}

		for(Sauce sauce : sauces){
			cout += sauce.getCout();
		}

		cout += getBoisson().getCout();

		cout += getSupplementBoisson().getCout();

		cout += getDessert().getCout();

		return(cout);
	}

	/**
	 * Modifie le {@code plat} de la commande en lui affectant la valeur passée en paramètres.
	 * 
	 * @param plat le nouveau plat de la commande.
	 */
	public void setPlat(Plat plat){

		this.plat = plat;
	}

	/**
	 * Ajoute l'{@code Ingredient} passé en paramètres à la commande.
	 * 
	 * @param ingredient l'ingrédient à ajouter à la commande.
	 */
	public void addIngredient(Ingredient ingredient){

		this.ingredients.add(ingredient);
	}

	/**
	 * Ajoute la {@code Sauce} passée en paramètres à la commande.
	 * 
	 * @param sauce la sauce à ajouter à la commande.
	 */
	public void addSauce(Sauce sauce){

		this.sauces.add(sauce);
	}

	/**
	 * Modifie la {@code boisson} de la commande en lui affectant la valeur passée en paramètres.
	 * 
	 * @param boisson la nouvelle boisson de la commande.
	 */
	public void setBoisson(Boisson boisson){

		this.boisson = boisson;
	}

	/**
	 * Modifie le {@code supplementBoisson} de la commande en lui affectant la valeur passée en paramètres.
	 * 
	 * @param supplementBoisson le nouveau supplément boisson de la commande.
	 */
	public void setSupplementBoisson(SupplementBoisson supplementBoisson){

		this.supplementBoisson = supplementBoisson;
	}

	/**
	 * Modifie le {@code dessert} de la commande en lui affectant la valeur passée en paramètres.
	 * 
	 * @param dessert le nouveau dessert de la commande.
	 */
	public void setDessert(Dessert dessert){

		this.dessert = dessert;
	}

	/**
	 * Renvoie le prix d'une commande contenant les {@code ContenuCommande} passés en paramètres.
	 * 
	 * @param plat le plat utilisé pour calculer le prix de la commande.
	 * @param ingredients la {@code List<Ingredient>} décrivant la liste des ingrédients utilisés pour calculer le prix de la commande.
	 * @param sauces la {@code List<Sauce>} décrivant la liste des sauces utilisées pour calculer le prix de la commande.
	 * @param boisson la boisson utilisée pour calculer le prix de la commande.
	 * @param supplementBoisson le supplément boisson utilisé pour calculer le prix de la commande.
	 * @param dessert le dessert utilisé pour calculer le prix de la commande.
	 * 
	 * @return le prix de la commande.
	 */
	public static float prixCommande(Plat plat, List<Ingredient> ingredients, Boisson boisson, SupplementBoisson supplementBoisson, Dessert dessert){

		float prix = 0f;

		if(!(plat.getId().equals(BaseDonnees.ID_RIEN_PLAT))&&!(boisson.getId().equals(BaseDonnees.ID_RIEN_BOISSON))&&!(dessert.getId().equals(BaseDonnees.ID_RIEN_DESSERT))){ //si la commande contient un plat, une boisson et un dessert, alors le client a droit à une réduction
			prix -= Core.getParametres().getReducMenu();
		}

		prix += plat.getPrix();

		if(ingredients.size() > plat.getNbMaxIngredients()){
			prix += Core.getParametres().getPrixIngredientSupp() * (ingredients.size() - plat.getNbMaxIngredients());
		}

		prix += dessert.getPrix();

		if(!(boisson.getId().equals(BaseDonnees.ID_RIEN_BOISSON))){
			prix += Core.getParametres().getPrixBoisson();
		}

		prix += supplementBoisson.getPrix();

		return(prix);
	}

	/**
	 * Indique si deux objets de types {@code Commande} sont « égaux », c'est à dire qu'ils ont le même {@code numero}.
	 * 
	 * @param o l'objet avec lequel comparer cette commande.
	 * 
	 * @return le fait que la commande passée en paramètres et la commande propriétaire de la méthode ont des attributs {@code numero} égaux.
	 */
	@Override
	public boolean equals(Object o){

		if(o == null){ //si l'objet passé en paramètres est null, on renvoie false
			return(false);
		}
		if(o == this){ //si cet objet et celui passé en paramètres font référence au même objet, on renvoie true
			return(true);
		}
		if(!(o instanceof Commande)){ //si l'objet passé en paramètres n'est pas une instance de Commande, on renvoie false
			return(false);
		}

		Commande oCommande = (Commande)o; //on cast l'objet passé en paramètres en Commande
		if(oCommande.getNumero() == this.getNumero()){ //si les numéros de la Commande passée en paramètres et de cet objet sont égaux, on renvoie true, et false sinon
			return(true);
		} else {
			return(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Cette méthode doit être override si on override java.lang.Object.equals(Object o).
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){

		return(Objects.hash(numero));
	}

	/**
	 * Renvoie une représentation textuelle de cette commande.
	 * 
	 * @return une représentation textuelle de cette commande.
	 */
	@Override
	public String toString(){

		String ingString = "";
		for(Ingredient ingredient : ingredients){
			ingString += "  - " + ingredient.getNom() + "\n";
		}

		String sauString = "";
		for(Sauce sauce : sauces){
			sauString += "  - " + sauce.getNom() + "\n";
		}

		return("Commande #" + numero + "\n—————————————————" + "\nPlat : " + plat.getNom() + "\nIngrédients :\n" + ingString + "Sauces :\n" + sauString + "Boisson : " + boisson.getNom() + " + " + supplementBoisson.getNom() + "\nDessert : " + dessert.getNom() + "\nPrix : " + getPrix() + "\n");
	}
}
