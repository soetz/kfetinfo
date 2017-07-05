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

import java.util.Date;

import java.util.List;
import java.util.ArrayList;

public class Commande {
	Date moment;
	int numero;
	static int dernierNumero = 0;
	Plat plat;
	List<Ingredient> ingredients;
	List<Sauce> sauces;
	Dessert dessert;
	Boisson boisson;
	SupplementBoisson supplementBoisson;

	public Commande(Plat rienPlat, Dessert rienDessert, Boisson rienBoisson, SupplementBoisson rienSupplementBoisson) {
		moment = new Date();
		numero = dernierNumero + 1;
		dernierNumero = numero;
		plat = rienPlat;
		ingredients = new ArrayList<Ingredient>();
		sauces = new ArrayList<Sauce>();
		dessert = rienDessert;
		boisson = rienBoisson;
		supplementBoisson = rienSupplementBoisson;
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

	public float getPrix(){
		float prix = 0f;

		prix += plat.getPrix();

		if(this.ingredients.size() > this.plat.getNbMaxIngredients()){
			prix += Test.getPrixIngredientSupp() * (this.ingredients.size() - this.plat.getNbMaxIngredients());
		}

		prix += dessert.getPrix();

		if(this.boisson != Test.getBase().getRienBoisson()){
			prix += Test.getPrixBoisson();
		}

		prix += supplementBoisson.getPrix();

		return(prix);
	}

	public String toString(){
		String ingString = "";
		for(Ingredient ingredient : ingredients){
			ingString += "  - " + ingredient.getNom() + "\n";
		}

		String sauString = "";
		for(Sauce sauce : sauces){
			sauString += "  - " + sauce.getNom() + "\n";
		}

		return("\nPlat : " + plat.getNom() + "\nIngrédients :\n" + ingString + "Sauces :\n" + sauString + "Boisson : " + boisson.getNom() + " + " + supplementBoisson.getNom() + "\nDessert : " + dessert.getNom() + "\nPrix : " + getPrix() + "\n");
	}
}
