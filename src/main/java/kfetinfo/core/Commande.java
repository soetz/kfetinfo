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

import java.util.Date;

import java.util.List;
import java.util.ArrayList;

public class Commande {
	Date moment;
	int numero;
	Plat plat;
	List<Ingredient> ingredients;
	List<Sauce> sauces;
	Dessert dessert;
	Boisson boisson;
	SupplementBoisson supplementBoisson;

	public Commande(Plat rienPlat, Dessert rienDessert, Boisson rienBoisson, SupplementBoisson rienSupplementBoisson) {
		moment = new Date();
		numero = Core.getService().getDernierNumeroCommande() + 1;
		plat = rienPlat;
		ingredients = new ArrayList<Ingredient>();
		sauces = new ArrayList<Sauce>();
		dessert = rienDessert;
		boisson = rienBoisson;
		supplementBoisson = rienSupplementBoisson;
	}

	public Commande(){
		this(BaseDonnees.getRienPlat(), BaseDonnees.getRienDessert(), BaseDonnees.getRienBoisson(), BaseDonnees.getRienSupplementBoisson());
	}

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

	public void setPlat(Plat plat){
		this.plat = plat;
	}

	public void addIngredient(Ingredient ingredient){
		this.ingredients.add(ingredient);
	}

	public void addSauce(Sauce sauce){
		this.sauces.add(sauce);
	}

	public void setDessert(Dessert dessert){
		this.dessert = dessert;
	}

	public void setBoisson(Boisson boisson){
		this.boisson = boisson;
	}

	public void setSupplementBoisson(SupplementBoisson supplementBoisson){
		this.supplementBoisson = supplementBoisson;
	}

	public Date getMoment(){
		return(moment);
	}

	public int getNumero(){
		return(numero);
	}

	public Plat getPlat(){
		return(plat);
	}

	public List<Ingredient> getIngredients(){
		return(ingredients);
	}

	public List<Sauce> getSauces(){
		return(sauces);
	}

	public Dessert getDessert(){
		return(dessert);
	}

	public Boisson getBoisson(){
		return(boisson);
	}

	public SupplementBoisson getSupplementBoisson(){
		return(supplementBoisson);
	}

	public float getPrix(){
		return(prixCommande(this.plat, this.ingredients, this.sauces, this.boisson, this.supplementBoisson, this.dessert));
	}

	public static float prixCommande(Plat plat, List<Ingredient> ingredients, List<Sauce> sauces, Boisson boisson, SupplementBoisson supplementBoisson, Dessert dessert){
		float prix = 0f;

		if(!(plat.getId().equals("ff56da46-bddd-4e4f-a871-6fa03b0e814b"))&&!(dessert.getId().equals("962e1223-cdda-47ef-85ab-20eede2a0dc0"))&&!(boisson.getId().equals("c1d0b7e7-b9f8-4d2f-8c3d-7a0edcc413fe"))){
			prix -= Core.getParametres().getReducMenu();
		}

		prix += plat.getPrix();

		if(ingredients.size() > plat.getNbMaxIngredients()){
			prix += Core.getParametres().getPrixIngredientSupp() * (ingredients.size() - plat.getNbMaxIngredients());
		}

		prix += dessert.getPrix();

		if(!(boisson.getId().equals("c1d0b7e7-b9f8-4d2f-8c3d-7a0edcc413fe"))){
			prix += Core.getParametres().getPrixBoisson();
		}

		prix += supplementBoisson.getPrix();

		return(prix);
	}

	public float getCout(){
		float cout = 0;

		cout += getPlat().getCout();

		for(Ingredient ingredient : ingredients){
			cout += ingredient.getCout();
		}

		for(Sauce sauce : sauces){
			cout += sauce.getCout();
		}

		cout += getDessert().getCout();

		cout += getBoisson().getCout();
		cout += getSupplementBoisson().getCout();

		return(cout);
	}

	public void envoyer(){
		moment = new Date();

		Core.getService().ajouterCommande(this);
	}

	public String chaineToString(){
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

	public String toString(){
		return(chaineToString());
	}
}
