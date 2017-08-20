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

package kfetinfo.ui;

import kfetinfo.core.Commande;
import kfetinfo.core.CommandeAssignee;
import kfetinfo.core.Core;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Membre;
import kfetinfo.core.Sauce;

import java.util.List;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <p>EcranConfection est une classe constituée uniquement d'attributs et de méthodes statiques relatifs à l'affichage de l'écran servant aux membres confectionnant les commandes à récupérer les informations sur les commandes à confectionner du logiciel.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class EcranConfection {

	//la liste des contenus des écrans de confection, comme ça on peut en avoir plusieurs et les mettre à jour
	private static final List<HBox> LISTE_CONFECTION = new ArrayList<HBox>();

	/**
	 * Crée une nouvelle fenêtre de confection, c'est à dire une fenêtre permettant aux membres à récupérer les informations des commandes.
	 * 
	 * @param ecranPrincipal le {@code Stage} de l'écran principal.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final void ecranConfection(Stage ecranPrincipal){

		BorderPane ecran = new BorderPane();

		Label titre = new Label("En cours de confection…");
		ecran.setTop(titre);

		HBox confection = new HBox();
		confection.minWidthProperty().bind(ecran.widthProperty());
		confection.maxWidthProperty().bind(confection.minWidthProperty());
		LISTE_CONFECTION.add(confection); //on ajoute la HBox à la liste des contenus de fenêtre

		mettreEcransAJour();

		ecran.setCenter(confection);

		Scene scene = new Scene(ecran, App.LARGEUR_MIN_FENETRE, App.HAUTEUR_MIN_FENETRE);
		Stage theatre = new Stage();
		theatre.setMinWidth(App.LARGEUR_MIN_FENETRE + 16);
		theatre.setMinHeight(App.HAUTEUR_MIN_FENETRE + 39);
		theatre.setTitle("Écran de confection des commandes");
		theatre.setScene(scene);
		theatre.show();

		ecranPrincipal.showingProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(ecranPrincipal.isShowing() == false){
					theatre.close();
				}
			}
		});

		theatre.showingProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(theatre.isShowing() == false){
					LISTE_CONFECTION.remove(confection);
				}
			}
		});
	}

	/**
	 * Crée une {@code Region} affichant le contenu d'une commande étant en train d'être confectionnée.
	 * 
	 * @param numero la position du membre dans la liste des membres confection du service (commence à 0).
	 * 
	 * @return une commande en train d'être réalisée.
	 */
	public static final Region commandeRealisation(int numero){

		VBox commandeBox = new VBox();

		Membre membre = Core.getService().getConfection().get(numero);

		CommandeAssignee commande = null;

		for(Commande commandeBoucle : Core.getService().getCommandes()){
			if(commandeBoucle instanceof CommandeAssignee){
				CommandeAssignee commandeAssignee = (CommandeAssignee)commandeBoucle;
				if(commandeAssignee.getMembre().equals(membre)){
					if(!commandeAssignee.getEstRealisee()){
						commande = commandeAssignee;
					}
				}
			}
		}

		Label nomMembre = new Label(membre.getBlaze());
		commandeBox.getChildren().add(nomMembre);

		if(commande != null){

			HBox numeroEtPlat = new HBox();

			Label numeroCommande = new Label("" + commande.getNumero());
			numeroCommande.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
			numeroCommande.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
			numeroCommande.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
			numeroCommande.getStyleClass().add(App.NUMERO_COMMANDE_AJOUTEE);

			Label plat = new Label(commande.getPlat().getNom().toUpperCase());
			plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
			plat.setMinHeight(Control.USE_PREF_SIZE);
			plat.setMaxHeight(Control.USE_PREF_SIZE);
			plat.getStyleClass().add(App.PLAT_COMMANDE);

			numeroEtPlat.getChildren().add(numeroCommande);
			numeroEtPlat.getChildren().add(plat);

			commandeBox.getChildren().add(numeroEtPlat);

			Label sauces = new Label("Sauces : ");
			commandeBox.getChildren().add(sauces);

			for(Sauce sauce : commande.getSauces()){
				Label label = new Label(sauce.getNom());
				commandeBox.getChildren().add(label);
			}

			Label ingredients = new Label("Ingredients : ");
			commandeBox.getChildren().add(ingredients);

			for(Ingredient ingredient : commande.getIngredients()){
				Label label = new Label(ingredient.getNom());
				commandeBox.getChildren().add(label);
			}

			Label boisson = new Label("Boisson : ");
			Label boissonLabel = new Label(commande.getBoisson().getNom() + " + " + commande.getSupplementBoisson().getNom());

			commandeBox.getChildren().add(boisson);
			commandeBox.getChildren().add(boissonLabel);

			Label dessert = new Label("Dessert : ");
			Label dessertLabel = new Label(commande.getDessert().getNom());

			commandeBox.getChildren().add(dessert);
			commandeBox.getChildren().add(dessertLabel);

		} else {
			Label rien = new Label("Ce membre ne réalise actuellement aucune commande");

			commandeBox.getChildren().add(rien);
		}

		return(commandeBox);
	}

	/**
	 * Met les écrans de confection à jour des commandes qui doivent être confectionnées.
	 */
	public static final void mettreEcransAJour(){

		for(HBox confection : LISTE_CONFECTION){
			confection.getChildren().clear();

			int i;
			for(i = 0; i < Core.getService().getConfection().size(); i++){
				confection.getChildren().add(commandeRealisation(i));
			}
		}
	}
}
