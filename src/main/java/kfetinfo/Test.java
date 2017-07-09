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
	static float reducMenu = 0.3f;
	static BaseDonnees base = new BaseDonnees(new Date());

	public static void main(String[] args) {
		Commande commande = new Commande(base.getRienPlat(), base.getRienDessert(), base.getRienBoisson(), base.getRienSupplementBoisson(), base);
		commande.setPlat(base.getPlat("19e392a8-17ec-4396-8305-70dcc4dca13a"));
		commande.addSauce(base.getSauce("abb31c7d-7388-46e2-aa04-ea3cd7f8fa7b"));
		commande.addSauce(base.getSauce("3c597aa0-5a68-4a89-98eb-a03cbd384161"));
		CreateurBase.ajouterCommande(commande);

		base.assignerCommande(1, base.getMembre("1eafc5b3-5a4c-4eb6-8bbc-9ad5dbab726d"));
		
		base.affCommandes();

		/*launch(args);*/
	}

	public static float getPrixIngredientSupp(){
		return(prixIngredientSupp);
	}

	public static float getPrixBoisson(){
		return(prixBoisson);
	}

	public static float getReducMenu(){
		return(reducMenu);
	}

	public static BaseDonnees getBase(){
		return(base);
	}

	/*public void start(Stage args) throws Exception {
		
	}*/

}
