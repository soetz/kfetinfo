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

import kfetinfo.core.BaseDonnees;
import kfetinfo.core.Commande;
import kfetinfo.core.CommandeAssignee;
import kfetinfo.core.Core;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Membre;
import kfetinfo.core.Sauce;

import java.util.List;
import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <p>EcranConfection est une classe constituée uniquement d'attributs et de méthodes statiques relatifs à l'affichage de l'écran servant aux membres confectionnant les commandes à récupérer les informations sur les commandes à confectionner du logiciel.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.1
 */
public class EcranConfection {

	//classes de style pour l'utilisation du CSS
	private static final String ECRAN = "ec-confection-ecran";
	private static final String TITRE = "ec-confection-titre";
	private static final String MEMBRE = "ec-confection-membre";
	private static final String CONFECTION = "ec-confection-confection";
	private static final String AUCUNE_REALISATION = "ec-confection-aucune-realisation";
	private static final String CATEGORIE_CONTENU = "ec-confection-categorie-contenu";
	private static final String CONTENU_CONFECTION = "ec-confection-confection-contenu";
	private static final String AUTRE = "ec-confection-autre";
	private static final String AUTRE_MEMBRE = "ec-confection-autre-membre";
	private static final String CONTENU_AUTRE = "ec-confection-autre-contenu";
	private static final String ESPACEMENT = "ec-confection-espacement";

	//constantes pour l'affichage
	private static final Double ESPACE_CATEGORIES = 10.0;
	private static final Double ESPACE_CONFECTION = 6.0;
	private static final Double ESPACE_CONTENU_CONFECTION = 2.0;
	private static final Double DECALAGE_CATEGORIE_CONTENU = 8.0;
	private static final Double DECALAGE_CONTENU = 16.0;
	private static final Double ESPACE_FIN_COMMANDE = 4.0;
	private static final Double ESPACE_AUTRE = 6.0;
	private static final Double DECALAGE_CONTENU_AUTRE = 2.0;
	private static final Double LARGEUR_COMMANDE = 200.0;
	private static final Double HAUTEUR_COMMANDE = 100.0;
	

	//la liste des contenus des écrans de confection, comme ça on peut en avoir plusieurs et les mettre à jour
	private static final List<HBox> LISTE_REALISATION = new ArrayList<HBox>();

	//la liste des contenus des écrans pour commis, pour les mêmes raisons
	private static final List<FlowPane> LISTE_AUTRE = new ArrayList<FlowPane>();

	//la liste des fenêtres, pour les mêmes raisons
	private static final List<Stage> LISTE_FENETRES = new ArrayList<Stage>();

	/**
	 * Crée une nouvelle fenêtre de confection, c'est à dire une fenêtre permettant aux membres à récupérer les informations des commandes.
	 * 
	 * @param ecranPrincipal le {@code Stage} de l'écran principal.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final void ecranConfection(Stage ecranPrincipal){

		VBox ecran = new VBox();
		ecran.getStyleClass().add(ECRAN);
		ecran.setSpacing(ESPACE_CATEGORIES);

		Label titre = new Label("En cours de confection…");
		titre.setMaxWidth(Double.MAX_VALUE);
		titre.getStyleClass().add(TITRE);

		ecran.getChildren().add(titre);

		HBox realisation = new HBox();
		realisation.setSpacing(ESPACE_CONFECTION);
		realisation.minWidthProperty().bind(ecran.widthProperty());
		realisation.maxWidthProperty().bind(realisation.minWidthProperty());
		LISTE_REALISATION.add(realisation); //on ajoute la HBox à la liste des contenus des écrans de confection

		ecran.getChildren().add(realisation);

		FlowPane autre = new FlowPane();
		autre.setHgap(ESPACE_AUTRE);
		autre.setVgap(ESPACE_AUTRE);
		autre.minWidthProperty().bind(ecran.widthProperty());
		autre.maxWidthProperty().bind(autre.minWidthProperty());
		LISTE_AUTRE.add(autre); //on ajoute le FlowPane à la liste des contenus des écrans pour commis

		ecran.getChildren().add(autre);

		Scene scene = new Scene(ecran, App.LARGEUR_MIN_FENETRE, App.HAUTEUR_MIN_FENETRE);

		scene.getStylesheets().add(EcranConfection.class.getResource("../../Interface/Stylesheets/general.css").toExternalForm());
		scene.getStylesheets().add(EcranConfection.class.getResource("../../Interface/Stylesheets/ecran confection.css").toExternalForm());

		Stage theatre = new Stage();
		theatre.getIcons().setAll(new Image(EcranConfection.class.getResource("../../Interface/Images/Icons/App/AppIcon_16.png").toExternalForm()),
				new Image(EcranConfection.class.getResource("../../Interface/Images/Icons/App/AppIcon_24.png").toExternalForm()),
				new Image(EcranConfection.class.getResource("../../Interface/Images/Icons/App/AppIcon_32.png").toExternalForm()),
				new Image(EcranConfection.class.getResource("../../Interface/Images/Icons/App/AppIcon_48.png").toExternalForm()),
				new Image(EcranConfection.class.getResource("../../Interface/Images/Icons/App/AppIcon_64.png").toExternalForm()),
				new Image(EcranConfection.class.getResource("../../Interface/Images/Icons/App/AppIcon_128.png").toExternalForm()),
				new Image(EcranConfection.class.getResource("../../Interface/Images/Icons/App/AppIcon_256.png").toExternalForm()),
				new Image(EcranConfection.class.getResource("../../Interface/Images/Icons/App/AppIcon_512.png").toExternalForm()));
		LISTE_FENETRES.add(theatre);

		theatre.setMinWidth(App.LARGEUR_MIN_FENETRE + 16);
		theatre.setMinHeight(App.HAUTEUR_MIN_FENETRE + 39);
		theatre.setTitle("Écran de confection des commandes");
		theatre.setScene(scene);
		theatre.show();

		mettreEcransAJour();

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
					LISTE_REALISATION.remove(realisation);
					LISTE_AUTRE.remove(autre);
					LISTE_FENETRES.remove(theatre);
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
	private static final Region commandeRealisation(int numero, int fenetre){

		VBox commandeBox = new VBox();
		commandeBox.setSpacing(ESPACE_CONTENU_CONFECTION);
		commandeBox.getStyleClass().add(CONFECTION);

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
		nomMembre.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		nomMembre.getStyleClass().add(MEMBRE);
		commandeBox.getChildren().add(nomMembre);

		if(commande != null){

			HBox numeroEtPlat = new HBox();
			numeroEtPlat.setTranslateY(-ESPACE_CONTENU_CONFECTION);

			Label numeroCommande = new Label("" + commande.getNumero());
			numeroCommande.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
			numeroCommande.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
			numeroCommande.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
			numeroCommande.getStyleClass().add(App.NUMERO_COMMANDE_ASSIGNEE);

			Label plat = new Label(commande.getPlat().getNom().toUpperCase());
			plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
			plat.setMinHeight(Control.USE_PREF_SIZE);
			plat.setMaxHeight(Control.USE_PREF_SIZE);
			plat.setTranslateX(App.ESPACE_NUMERO_PLAT);
			plat.getStyleClass().add(App.PLAT_COMMANDE);

			numeroEtPlat.getChildren().add(numeroCommande);
			numeroEtPlat.getChildren().add(plat);

			commandeBox.getChildren().add(numeroEtPlat);

			Label sauces = new Label("Sauces :");
			sauces.setTranslateX(DECALAGE_CATEGORIE_CONTENU);
			sauces.getStyleClass().add(CATEGORIE_CONTENU);
			commandeBox.getChildren().add(sauces);

			for(Sauce sauce : commande.getSauces()){
				Label label = new Label(sauce.getNom());
				label.setTranslateX(DECALAGE_CONTENU);
				label.getStyleClass().add(CONTENU_CONFECTION);
				commandeBox.getChildren().add(label);
			}

			Label ingredients = new Label("Ingredients :");
			ingredients.setTranslateX(DECALAGE_CATEGORIE_CONTENU);
			ingredients.getStyleClass().add(CATEGORIE_CONTENU);
			commandeBox.getChildren().add(ingredients);

			for(Ingredient ingredient : commande.getIngredients()){
				Label label = new Label(ingredient.getNom());
				label.setTranslateX(DECALAGE_CONTENU);
				label.getStyleClass().add(CONTENU_CONFECTION);
				commandeBox.getChildren().add(label);
			}

			Label boisson = new Label("Boisson :");
			boisson.setTranslateX(DECALAGE_CATEGORIE_CONTENU);
			boisson.getStyleClass().add(CATEGORIE_CONTENU);
			Label boissonLabel = new Label(commande.getBoisson().getNom() + " + " + commande.getSupplementBoisson().getNom());
			boissonLabel.setTranslateX(DECALAGE_CONTENU);
			boissonLabel.getStyleClass().add(CONTENU_CONFECTION);

			commandeBox.getChildren().add(boisson);
			commandeBox.getChildren().add(boissonLabel);

			Label dessert = new Label("Dessert :");
			dessert.setTranslateX(DECALAGE_CATEGORIE_CONTENU);
			dessert.getStyleClass().add(CATEGORIE_CONTENU);
			Label dessertLabel = new Label(commande.getDessert().getNom());
			dessertLabel.setTranslateX(DECALAGE_CONTENU);
			dessertLabel.getStyleClass().add(CONTENU_CONFECTION);

			commandeBox.getChildren().add(dessert);
			commandeBox.getChildren().add(dessertLabel);

		} else {
			Label rien = new Label("Ce membre ne réalise actuellement aucune commande");
			rien.setMaxWidth(Double.MAX_VALUE);
			rien.getStyleClass().add(CATEGORIE_CONTENU);
			rien.getStyleClass().add(AUCUNE_REALISATION);

			commandeBox.getChildren().add(rien);
		}

		Region espaceFin = new Region();
		espaceFin.setMinHeight(ESPACE_FIN_COMMANDE);
		espaceFin.setMaxWidth(1);
		espaceFin.getStyleClass().add(ESPACEMENT);
		commandeBox.getChildren().add(espaceFin);

		commandeBox.maxWidthProperty().bind(LISTE_FENETRES.get(fenetre).widthProperty().divide(Core.getService().getConfection().size()).subtract(4*ESPACE_CONFECTION/Core.getService().getConfection().size() + (Core.getService().getConfection().size() - 1)*ESPACE_CONFECTION));
		commandeBox.minWidthProperty().bind(commandeBox.maxWidthProperty());

		return(commandeBox);
	}

	/**
	 * Crée un {@code AnchorPane} affichant le contenu d'une commande n'étant pas en train d'être confectionnée.
	 * 
	 * @param numero le numéro de la commande.
	 * @param fenetre le numéro de la fenêtre où l'on souhaite ajouter la commande.
	 * 
	 * @return une commande.
	 */
	private static final AnchorPane commandeAutre(int numero, int fenetre){

		Commande commande = Core.getService().getCommande(numero);

		boolean assignee = false;
		CommandeAssignee commandeAssignee = null;

		if(commande instanceof CommandeAssignee){
			assignee = true;
			commandeAssignee = (CommandeAssignee)commande;
		}

		IntegerProperty nbDivisions = new SimpleIntegerProperty();
		nbDivisions.bind(LISTE_FENETRES.get(fenetre).widthProperty().divide(LARGEUR_COMMANDE));
		DoubleProperty reste = new SimpleDoubleProperty();
		reste.bind(LISTE_FENETRES.get(fenetre).widthProperty().subtract(nbDivisions.multiply(LARGEUR_COMMANDE).add(nbDivisions.subtract(1).multiply(ESPACE_AUTRE))).subtract(2));

		AnchorPane commandePane = new AnchorPane();
		commandePane.getStyleClass().add(AUTRE);

		commandePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		commandePane.setPrefHeight(HAUTEUR_COMMANDE);
		commandePane.prefWidthProperty().bind(reste.divide(nbDivisions).add(LARGEUR_COMMANDE).subtract(nbDivisions.subtract(1).multiply(ESPACE_AUTRE).divide(nbDivisions)));
		commandePane.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

		Label numeroCommande = new Label("" + numero);
		numeroCommande.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
		numeroCommande.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		numeroCommande.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

		if(assignee){
			if(commandeAssignee.getEstDonnee()){ //donnée
				numeroCommande.getStyleClass().add(App.NUMERO_COMMANDE_DONNEE);
			} else { //réalisée
				numeroCommande.getStyleClass().add(App.NUMERO_COMMANDE_REALISEE);
			}
		} else { //ajoutée
			numeroCommande.getStyleClass().add(App.NUMERO_COMMANDE_AJOUTEE);
		}

		Label plat = new Label(commande.getPlat().getNom().toUpperCase());
		plat.setTranslateX(DECALAGE_CONTENU_AUTRE);
		plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
		plat.setMinHeight(Control.USE_PREF_SIZE);
		plat.setMaxHeight(Control.USE_PREF_SIZE);
		plat.setMaxWidth(Double.MAX_VALUE);
		plat.getStyleClass().add(App.PLAT_COMMANDE);

		Label confection = new Label();
		if(assignee){
			confection.setText(commandeAssignee.getMembre().getBlazeCourt()); //on inscrit le blaze court de la personne à qui elle était affectée
			confection.getStyleClass().add(AUTRE_MEMBRE);
		}

		VBox contenuCommande = new VBox();

		Label lbIngredients = lbIngredients(commande);
		lbIngredients.maxWidthProperty().bind(commandePane.widthProperty().subtract(DECALAGE_CONTENU_AUTRE));
		lbIngredients.setTranslateX(DECALAGE_CONTENU_AUTRE);
		lbIngredients.getStyleClass().add(CONTENU_AUTRE);
		Label lbSauces = lbSauces(commande);
		lbSauces.setTranslateX(DECALAGE_CONTENU_AUTRE);
		lbSauces.getStyleClass().add(CONTENU_AUTRE);
		Label lbBoisson = new Label();
		lbBoisson.setTranslateX(DECALAGE_CONTENU_AUTRE);
		lbBoisson.getStyleClass().add(CONTENU_AUTRE);
		if(commande.getSupplementBoisson().getId().equals(BaseDonnees.ID_RIEN_SUPPLEMENT_BOISSON)){
			lbBoisson.setText(commande.getBoisson().getNom());
		} else {
			lbBoisson.setText(commande.getBoisson().getNom() + " + " + commande.getSupplementBoisson().getNom());
		}
		Label lbDessert = new Label(commande.getDessert().getNom());
		lbDessert.setTranslateX(DECALAGE_CONTENU_AUTRE);
		lbDessert.getStyleClass().add(CONTENU_AUTRE);
		
		contenuCommande.getChildren().addAll(lbIngredients, lbSauces, lbBoisson, lbDessert);

		AnchorPane.setTopAnchor(numeroCommande, 0.0);
		AnchorPane.setLeftAnchor(numeroCommande, 0.0);
		AnchorPane.setTopAnchor(plat, 0.0);
		AnchorPane.setLeftAnchor(plat, App.TAILLE_NUMERO_COMMANDE + App.ESPACE_NUMERO_PLAT);
		AnchorPane.setTopAnchor(confection, 0.0);
		AnchorPane.setRightAnchor(confection, 2.0);
		AnchorPane.setTopAnchor(contenuCommande, App.TAILLE_NUMERO_COMMANDE);
		AnchorPane.setLeftAnchor(contenuCommande, 3.0);

		commandePane.getChildren().add(numeroCommande);
		commandePane.getChildren().add(plat);
		commandePane.getChildren().add(confection);
		commandePane.getChildren().add(contenuCommande);

		return(commandePane);
	}

	/**
	 * Renvoie un {@code Label} affichant la liste des ingrédients de la commande passée en paramètres sur une ligne.
	 * 
	 * @param commande la commande dont on souhaite extraire la liste des ingrédients.
	 * 
	 * @return un label avec la liste des ingrédients sur une ligne.
	 */
	private static final Label lbIngredients(Commande commande){

		Label lbIngredients = new Label();

		String nomsIngredients = "";
		if(commande.getIngredients().size()!=0){
			nomsIngredients += commande.getIngredients().get(0).getNom();
			for(Ingredient ingredient : commande.getIngredients()){
				if(!(ingredient.equals(commande.getIngredients().get(0)))){
					nomsIngredients += " - " + ingredient.getNom();
				}
			}
		} else {
			nomsIngredients = "Rien";
		}
		lbIngredients.setText(nomsIngredients);

		return(lbIngredients);
	}

	/**
	 * Renvoie un {@code Label} affichant la liste des sauces de la commande passée en paramètres sur une ligne.
	 * 
	 * @param commande la commande dont on souhaite extraire la liste des sauces.
	 * 
	 * @return un label avec la liste des sauces sur une ligne.
	 */
	private static final Label lbSauces(Commande commande){
		Label lbSauces = new Label();

		String nomsSauces = "";
		if(commande.getSauces().size()!=0){
			nomsSauces += commande.getSauces().get(0).getNom();
			for(Sauce sauce : commande.getSauces()){
				if(!(sauce.equals(commande.getSauces().get(0)))){
					nomsSauces += " - " + sauce.getNom();
				}
			}
		} else {
			nomsSauces = "Rien";
		}
		lbSauces.setText(nomsSauces);

		return(lbSauces);
	}

	/**
	 * Met les écrans de confection à jour des commandes qui doivent être confectionnées.
	 */
	public static final void mettreEcransAJour(){

		for(HBox box : LISTE_REALISATION){
			box.getChildren().clear();

			Region gauche = new Region();
			gauche.setMinWidth(ESPACE_CONFECTION);
			gauche.setMaxHeight(1);
			gauche.getStyleClass().add(ESPACEMENT);

			box.getChildren().add(gauche);

			for(Membre membreConf : Core.getService().getConfection()){
				box.getChildren().add(commandeRealisation(Core.getService().getConfection().indexOf(membreConf), LISTE_REALISATION.indexOf(box)));
			}

			Region droite = new Region();
			droite.setMinWidth(ESPACE_CONFECTION);
			droite.setMaxHeight(1);
			droite.getStyleClass().add(ESPACEMENT);

			box.getChildren().add(droite);
		}

		List<Integer> ajoutees = new ArrayList<Integer>();
		List<Integer> realisees = new ArrayList<Integer>();
		List<Integer> donnees = new ArrayList<Integer>();

		for(Commande commande : Core.getService().getCommandes()){
			if(commande instanceof CommandeAssignee){
				CommandeAssignee commandeAssignee = (CommandeAssignee)commande;
				if(commandeAssignee.getEstRealisee()){
					if(commandeAssignee.getEstDonnee()){ //la commande est donnée
						donnees.add(commande.getNumero());
					} else { //la commande est réalisée
						realisees.add(commande.getNumero());
					}
				}
			} else { //la commande est ajoutée
				ajoutees.add(commande.getNumero());
			}
		}

		for(FlowPane flow : LISTE_AUTRE){
			flow.getChildren().clear();

			for(Integer numCommandeRealisee : realisees){
				flow.getChildren().add(commandeAutre(numCommandeRealisee, LISTE_AUTRE.indexOf(flow)));
			}

			for(Integer numCommandeAjoutee : ajoutees){
				flow.getChildren().add(commandeAutre(numCommandeAjoutee, LISTE_AUTRE.indexOf(flow)));
			}

			for(Integer numCommandeDonnee : donnees){
				flow.getChildren().add(commandeAutre(numCommandeDonnee, LISTE_AUTRE.indexOf(flow)));
			}
		}
	}
}
