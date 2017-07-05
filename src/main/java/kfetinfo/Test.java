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

import java.io.File;

import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.stage.Stage;

public class Test /*extends Application*/ {

	static float prixIngredientSupp = 0.3f;
	static float prixBoisson = 0.5f;
	static BaseDonnees base = new BaseDonnees();

	public static void main(String[] args) {
		base.affMenu();
		Commande commande = new Commande(base.getRienPlat(), base.getRienDessert(), base.getRienBoisson(), base.getRienSupplementBoisson());
		System.out.println(commande);
		commande.setPlat(base.getPlat("19e392a8-17ec-4396-8305-70dcc4dca13a"));
		System.out.println(commande);
		commande.setPlat(base.getPlat("d5548771-4116-4f9b-baf5-20b0bb4874ce"));
		System.out.println(commande);
		commande.addIngredient(base.getIngredient("5e920d8b-983d-4e06-b372-589b58a18feb"));
		System.out.println(commande);
		commande.addIngredient(base.getIngredient("17e00364-e494-4241-b592-ccba572643a4"));
		System.out.println(commande);
		commande.addIngredient(base.getIngredient("b44e1e13-6aa4-493b-94eb-b21fdca5529b"));
		System.out.println(commande);
		commande.addIngredient(base.getIngredient("4f4edfb7-c2fc-4d2a-aac2-93197bdc8765"));
		System.out.println(commande);
		commande.addSauce(base.getSauce("81c19081-cb63-4639-803d-e819221dde3a"));
		System.out.println(commande);
		commande.addSauce(base.getSauce("1cca8192-ad1e-4cf6-abd6-2e45f20da5f6"));
		System.out.println(commande);
		commande.setBoisson(base.getBoisson("19e5431e-a848-4774-b548-306cfb1cd224"));
		System.out.println(commande);
		commande.setDessert(base.getDessert("d09234f6-eb62-41d9-82bf-758982a937dc"));
		System.out.println(commande);
		commande.setSupplementBoisson(base.getSupplementBoisson("eb30ae6f-05bd-4953-bf60-a34814716aed"));
		System.out.println(commande);
		commande.setDessert(base.getDessert("7bbe9b8d-893c-4f39-8da4-2ddf28b620ff"));
		System.out.println(commande);

		/*launch(args);*/
	}

	public static float getPrixIngredientSupp(){
		return(prixIngredientSupp);
	}

	public static float getPrixBoisson(){
		return(prixBoisson);
	}

	public static BaseDonnees getBase(){
		return(base);
	}

	/*public void start(Stage args) throws Exception {
		
	}*/

}
