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
import kfetinfo.core.Boisson;
import kfetinfo.core.Core;
import kfetinfo.core.Dessert;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Plat;
import kfetinfo.core.Sauce;
import kfetinfo.core.SupplementBoisson;

import java.util.List;
import java.util.ArrayList;

import java.util.Locale;

import java.text.NumberFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * <p>Stocks est une classe constituée uniquement d'attributs et de méthodes statiques relatifs à l'affichage de l'écran permettant de sélectionner les contenus commande en stocks ainsi que les quantités de pain achetées et réservées aux membres pour le service.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.1
 */
public final class Stocks {

	//classes de style pour l'utilisation du CSS
	private static final String FOND = "stocks-fond";
	private static final String BAGUETTES = "stocks-baguettes";
	private static final String TEXTE_BAGUETTES = "stocks-texte-baguettes";
	private static final String BAGUETTES_UTILISEES = "stocks-baguettes-utilisees";
	private static final String GROUPE = "stocks-groupe";
	private static final String TITRE_GROUPE = "stocks-titre-groupe";
	private static final String BOUTON_APPLIQUER = "stocks-bouton-appliquer";
	private static final String VIDE = "stocks-vide";

	//pseudo classes de style
	protected static final PseudoClass BOX_DESELECTIONNEE = PseudoClass.getPseudoClass("box-deselectionnee");

	//constantes pour l'affichage
	private static final Double ESPACE_GROUPES = 6.0;
	private static final Double ESPACE_HAUT_BAS_LISTE = 4.0;
	private static final Double GAUCHE_ELEMENT = 4.0;
	private static final Double TAILLE_PANNEAU_BAGUETTES = 120.0;
	private static final Double GAUCHE_BAGUETTES = 15.0;
	private static final Double BAS_BAGUETTES = 15.0;
	private static final Double LARGREUR_SPINNERS = 70.0;
	private static final Double ESPACE_HORIZONTAL_BAGUETTES = 8.0;
	private static final Double ESPACE_VERTICAL_BAGUETTES = 4.0;

	//variables transversales qui seront utilisées lors de l'enregistrement
	private static List<CheckBox> checkBoxesPlats;
	private static float nbBaguettesAchetees;
	private static float nbBaguettesReservees;

	/**
	 * Crée une fenêtre permettant de gérer les stocks de contenus commande et de pain.
	 * 
	 * @param ecranPrincipal le {@code Stage} de la fenêtre principal du logiciel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final void ecranStocks(Stage ecranPrincipal){

		BorderPane pane = new BorderPane();

		HBox selection = new HBox();
		selection.setSpacing(ESPACE_GROUPES);
		selection.setTranslateX(ESPACE_GROUPES/2);

		checkBoxesPlats = new ArrayList<CheckBox>();

		VBox groupePlats = groupePlats(selection);

		VBox groupeIngredients = groupeIngredients(selection);

		VBox groupeSauces = groupeSauces(selection);

		VBox groupeBoissons = groupeBoissons(selection);

		VBox groupeSupplementsBoisson = groupeSupplementsBoisson(selection);

		VBox groupeDesserts = groupeDesserts(selection);

		selection.getChildren().add(groupePlats);
		selection.getChildren().add(groupeIngredients);
		selection.getChildren().add(groupeSauces);
		selection.getChildren().add(groupeBoissons);
		selection.getChildren().add(groupeSupplementsBoisson);
		selection.getChildren().add(groupeDesserts);

		AnchorPane baguettes = new AnchorPane();
		baguettes.setMinHeight(TAILLE_PANNEAU_BAGUETTES);
		baguettes.getStyleClass().add(BAGUETTES);

		GridPane layout = new GridPane();
		layout.setHgap(ESPACE_HORIZONTAL_BAGUETTES);
		layout.setVgap(ESPACE_VERTICAL_BAGUETTES);

		Label baguettesUtilisees = new Label("Nombre de baguettes utilisées");
		baguettesUtilisees.getStyleClass().add(TEXTE_BAGUETTES);
		Label baguettesAchetees = new Label("Nombre de baguettes achetées");
		baguettesAchetees.getStyleClass().add(TEXTE_BAGUETTES);
		Label baguettesMembres = new Label("Nombre de baguettes réservées aux membres");
		baguettesMembres.getStyleClass().add(TEXTE_BAGUETTES);

		NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.FRENCH);
		Label nbUtilisees = new Label("" + numberFormatter.format(Core.getService().getNbBaguettesUtilisees()));
		nbUtilisees.setMaxWidth(Double.MAX_VALUE);
		nbUtilisees.getStyleClass().add(TEXTE_BAGUETTES);
		nbUtilisees.getStyleClass().add(BAGUETTES_UTILISEES);

		nbBaguettesAchetees = Core.getService().getNbBaguettesAchetees();
		nbBaguettesReservees = Core.getService().getNbBaguettesReservees();

		Spinner<Float> spinnerAchetees = new Spinner(new SpinnerValueFactory() {
			{
				setValue(Core.getService().getNbBaguettesAchetees());
			}

			public void decrement(int steps){ //lorsque le spinner est décrémenté,
				final Float value = ((Number)getValue()).floatValue();
				final float val = (value == null) ? 0 : value;
				if(val - 0.5*steps >= nbBaguettesReservees + Core.getService().getNbBaguettesUtilisees()){ //la valeur diminue de 0,5 si celle-ci est supérieure ou égale au nombre de baguettes réservées + le nombre de baguettes utilisées + 0,5
					setValue(val - 0.5*steps);
				}
			}

			public void increment(int steps){ //lorsque le spinner est incrémenté,
				final Float value = ((Number)getValue()).floatValue();
				final float val = (value == null) ? 0 : value;
				setValue(val + 0.5*steps); //la valeur augmente de 0,5
			}
		});

		spinnerAchetees.focusedProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(!spinnerAchetees.isFocused()){ //lorsque le focus est perdu, on affecte la valeur du spinner à la variable nbBaguettesAchetees
					Float nbBaguettesAcheteesV = ((Number)spinnerAchetees.getValue()).floatValue();
					nbBaguettesAchetees = (nbBaguettesAcheteesV == null) ? 0 : nbBaguettesAcheteesV;
				}
			}
		});

		spinnerAchetees.setMaxWidth(LARGREUR_SPINNERS);
		spinnerAchetees.getStyleClass().add(App.SPINNER);

		Spinner<Float> spinnerReservees = new Spinner(new SpinnerValueFactory() {
			{
				setValue(Core.getService().getNbBaguettesReservees());
			}

			public void decrement(int steps){ //lorsque le spinner est décrémenté,
				final Float value = ((Number)getValue()).floatValue();
				final float val = (value == null) ? 0 : value;
				if(val >= 0.5){ //la valeur diminue de 0,5 si celle-ci est supérieure ou égale à 0,5
					setValue(val - 0.5*steps);
				}
			}

			public void increment(int steps){ //lorsque le spinner est incrémenté,
				final Float value = ((Number)getValue()).floatValue();
				final float val = (value == null) ? 0 : value;
				if(val + 0.5*steps <= nbBaguettesAchetees - Core.getService().getNbBaguettesUtilisees()){ //la valeur augmente de 0,5 si celle-ci est inférieure ou égale au nombre de baguettes achetées - moins le nombre de baguettes utilisées - 0,5
					setValue(val + 0.5*steps);
				}
			}
		});

		spinnerReservees.setMaxWidth(LARGREUR_SPINNERS);
		spinnerReservees.getStyleClass().add(App.SPINNER);

		spinnerReservees.focusedProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(!spinnerReservees.isFocused()){ //lorsque le focus est perdu, on affecte la valeur du spinner à la variable nbBaguettesAchetees
					Float nbBaguettesReserveesV = ((Number)spinnerReservees.getValue()).floatValue();
					nbBaguettesReservees = (nbBaguettesReserveesV == null) ? 0 : nbBaguettesReserveesV;
				}
			}
		});

		Button appliquer = new Button("Appliquer");
		appliquer.setDefaultButton(true);
		appliquer.getStyleClass().add(App.BOUTON);
		appliquer.getStyleClass().add(BOUTON_APPLIQUER);
		appliquer.setMaxHeight(Double.MAX_VALUE);
		appliquer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				Core.getService().setNbBaguettesAchetees(nbBaguettesAchetees);
				Core.getService().setNbBaguettesReservees(nbBaguettesReservees);
				for(CheckBox check : checkBoxesPlats){
					check.setSelected(!check.isSelected()); //ça c'est pour griser les plats qui utilisent du pain si jamais il n'y en a plus, y'a sûrement une meilleure manière mais ça marche et ça m'évite de réfléchir alors ¯\_(ツ)_/¯
					check.setSelected(!check.isSelected());
				}

				Selection.refreshCompteurBaguettes();
			}
		});

		layout.add(baguettesUtilisees, 0, 0);
		layout.add(baguettesAchetees, 0, 1);
		layout.add(baguettesMembres, 0, 2);
		layout.add(nbUtilisees, 1, 0);
		layout.add(spinnerAchetees, 1, 1);
		layout.add(spinnerReservees, 1, 2);
		layout.add(appliquer, 2, 1, 1, 2);

		AnchorPane.setLeftAnchor(layout, GAUCHE_BAGUETTES);
		AnchorPane.setBottomAnchor(layout, BAS_BAGUETTES);

		baguettes.getChildren().add(layout);

		pane.setCenter(selection);
		pane.setBottom(baguettes);
		pane.getStyleClass().add(FOND);

		Scene scene = new Scene(pane, App.LARGEUR_MIN_FENETRE, App.HAUTEUR_MIN_FENETRE);

		scene.getStylesheets().add(Stocks.class.getResource("../../Interface/Stylesheets/general.css").toExternalForm());
		scene.getStylesheets().add(Stocks.class.getResource("../../Interface/Stylesheets/stocks.css").toExternalForm());

		Stage theatre = new Stage();
		theatre.setAlwaysOnTop(true);
		theatre.initModality(Modality.APPLICATION_MODAL); //il faut fermer cette fenêtre pour revenir à l'écran principal
		theatre.setMinWidth(App.LARGEUR_MIN_FENETRE + 16);
		theatre.setMinHeight(App.HAUTEUR_MIN_FENETRE + 39);
		theatre.setTitle("Écran de sélection des éléments disponibles");
		theatre.setScene(scene);
		theatre.show();
	}

	/**
	 * Crée un panneau peuplé de checkboxes permettant de sélectionner les plats disponibles.
	 * 
	 * @param parent le node parent de celui-ci.
	 * 
	 * @return le panneau de sélection des plats disponibles.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final VBox groupePlats(Region parent){

		VBox groupePlats = new VBox();
		groupePlats.prefWidthProperty().bind(parent.widthProperty().subtract(7*ESPACE_GROUPES).divide(6));
		groupePlats.setMaxWidth(Double.MAX_VALUE);

		VBox listePlats = new VBox();
		listePlats.getStyleClass().add(GROUPE);

		Region haut = new Region();
		haut.getStyleClass().add(VIDE);
		haut.setPrefSize(1, ESPACE_HAUT_BAS_LISTE);
		haut.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		haut.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		listePlats.getChildren().add(haut);

		int i = 0;
		for(Plat plat : BaseDonnees.getPlats()){
			if(!plat.equals(BaseDonnees.getRienPlat())){
				HBox box = new HBox();
				box.getStyleClass().add(App.SELECTION_BOX);

				Region gauche = new Region();
				gauche.getStyleClass().add(App.SELECTION_GAUCHE_ELEMENT_S);
				gauche.setPrefSize(GAUCHE_ELEMENT, 1);
				gauche.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				gauche.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				box.getChildren().add(gauche);

				final int numero = i;

				CheckBox checkBox = new CheckBox();
				checkBox.getStyleClass().add(App.CHECKBOX);
				checkBox.selectedProperty().addListener(new ChangeListener() {
					public void changed(ObservableValue o, Object oldVal, Object newVal){
						boolean grise = false;
						if(plat.getUtilisePain()){ //si jamais le plat utilise du pain on le grise s'il n'y a plus de pain
							if(Core.getService().getNbBaguettesRestantes() <= 0){
								grise = true;
							}
						}

						if(!checkBox.isSelected()){
							grise = true;
							box.pseudoClassStateChanged(BOX_DESELECTIONNEE, true);
						} else {
							box.pseudoClassStateChanged(BOX_DESELECTIONNEE, false);
						}

						plat.setDisponible(checkBox.isSelected());
						Selection.platDisponible(numero, !grise);
					}
				});

				checkBoxesPlats.add(checkBox);

				if(plat.getDisponible()){
					checkBox.setSelected(true);
					box.pseudoClassStateChanged(BOX_DESELECTIONNEE, false);
				} else {
					box.pseudoClassStateChanged(BOX_DESELECTIONNEE, true);
				}

				Label label = new Label(plat.getNom());
				label.getStyleClass().add(App.SELECTION_ELEMENT_LABEL);
				box.setOnMouseClicked(new EventHandler<Event>() {
					public void handle(Event event) {
						checkBox.setSelected(!checkBox.isSelected());
						checkBox.requestFocus();
					}
				});

				box.getChildren().add(checkBox);
				box.getChildren().add(label);

				listePlats.getChildren().add(box);
				i++;
			}
		}

		Region bas = new Region();
		bas.getStyleClass().add(VIDE);
		bas.setPrefSize(1, ESPACE_HAUT_BAS_LISTE);
		bas.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		bas.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		listePlats.getChildren().add(bas);

		Label titrePlats = new Label("PLATS");
		titrePlats.setMaxWidth(Double.MAX_VALUE);
		titrePlats.getStyleClass().add(TITRE_GROUPE);

		groupePlats.getChildren().add(titrePlats);
		groupePlats.getChildren().add(listePlats);

		return(groupePlats);
	}

	/**
	 * Crée un panneau peuplé de checkboxes permettant de sélectionner les ingrédients disponibles.
	 * 
	 * @param parent le node parent de celui-ci.
	 * 
	 * @return le panneau de sélection des ingrédients disponibles.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final VBox groupeIngredients(Region parent){

		VBox groupeIngredients = new VBox();
		groupeIngredients.prefWidthProperty().bind(parent.widthProperty().subtract(7*ESPACE_GROUPES).divide(6));
		groupeIngredients.maxWidthProperty().bind(groupeIngredients.prefWidthProperty());
		groupeIngredients.minWidthProperty().bind(groupeIngredients.prefWidthProperty());

		VBox listeIngredients = new VBox();
		listeIngredients.getStyleClass().add(GROUPE);

		Region haut = new Region();
		haut.getStyleClass().add(VIDE);
		haut.setPrefSize(1, ESPACE_HAUT_BAS_LISTE);
		haut.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		haut.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		listeIngredients.getChildren().add(haut);

		int i = 0;
		for(Ingredient ingredient : BaseDonnees.getIngredients()){
			HBox box = new HBox();
			box.getStyleClass().add(App.SELECTION_BOX);

			Region gauche = new Region();
			gauche.getStyleClass().add(App.SELECTION_GAUCHE_ELEMENT_S);
			gauche.setPrefSize(GAUCHE_ELEMENT, 1);
			gauche.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
			gauche.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
			box.getChildren().add(gauche);

			final int numero = i;

			CheckBox checkBox = new CheckBox();
			checkBox.getStyleClass().add(App.CHECKBOX);
			checkBox.selectedProperty().addListener(new ChangeListener() {
				public void changed(ObservableValue o, Object oldVal, Object newVal){
					ingredient.setDisponible(checkBox.isSelected());
					Selection.ingredientDisponible(numero, checkBox.isSelected());
					if(checkBox.isSelected()){
						box.pseudoClassStateChanged(BOX_DESELECTIONNEE, false);
					} else {
						box.pseudoClassStateChanged(BOX_DESELECTIONNEE, true);
					}
				}
			});

			if(ingredient.getDisponible()){
				checkBox.setSelected(true);
				box.pseudoClassStateChanged(BOX_DESELECTIONNEE, false);
			} else {
				box.pseudoClassStateChanged(BOX_DESELECTIONNEE, true);
			}

			Label label = new Label(ingredient.getNom());
			label.getStyleClass().add(App.SELECTION_ELEMENT_LABEL);
			box.setOnMouseClicked(new EventHandler<Event>() {
				public void handle(Event event) {
					checkBox.setSelected(!checkBox.isSelected());
					checkBox.requestFocus();
				}
			});

			box.getChildren().add(checkBox);
			box.getChildren().add(label);

			listeIngredients.getChildren().add(box);
			i++;
		}

		Region bas = new Region();
		bas.getStyleClass().add(VIDE);
		bas.setPrefSize(1, ESPACE_HAUT_BAS_LISTE);
		bas.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		bas.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		listeIngredients.getChildren().add(bas);

		Label titreIngredients = new Label("INGRÉDIENTS");
		titreIngredients.setMaxWidth(Double.MAX_VALUE);
		titreIngredients.getStyleClass().add(TITRE_GROUPE);

		groupeIngredients.getChildren().add(titreIngredients);
		groupeIngredients.getChildren().add(listeIngredients);

		return(groupeIngredients);
	}

	/**
	 * Crée un panneau peuplé de checkboxes permettant de sélectionner les sauces disponibles.
	 * 
	 * @param parent le node parent de celui-ci.
	 * 
	 * @return le panneau de sélection des sauces disponibles.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final VBox groupeSauces(Region parent){

		VBox groupeSauces = new VBox();
		groupeSauces.prefWidthProperty().bind(parent.widthProperty().subtract(7*ESPACE_GROUPES).divide(6));
		groupeSauces.maxWidthProperty().bind(groupeSauces.prefWidthProperty());
		groupeSauces.minWidthProperty().bind(groupeSauces.prefWidthProperty());

		VBox listeSauces = new VBox();
		listeSauces.getStyleClass().add(GROUPE);

		Region haut = new Region();
		haut.getStyleClass().add(VIDE);
		haut.setPrefSize(1, ESPACE_HAUT_BAS_LISTE);
		haut.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		haut.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		listeSauces.getChildren().add(haut);

		int i = 0;
		for(Sauce sauce : BaseDonnees.getSauces()){
			HBox box = new HBox();
			box.getStyleClass().add(App.SELECTION_BOX);

			Region gauche = new Region();
			gauche.getStyleClass().add(App.SELECTION_GAUCHE_ELEMENT_S);
			gauche.setPrefSize(GAUCHE_ELEMENT, 1);
			gauche.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
			gauche.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
			box.getChildren().add(gauche);

			final int numero = i;

			CheckBox checkBox = new CheckBox();
			checkBox.getStyleClass().add(App.CHECKBOX);
			checkBox.selectedProperty().addListener(new ChangeListener() {
				public void changed(ObservableValue o, Object oldVal, Object newVal){
					sauce.setDisponible(checkBox.isSelected());
					Selection.sauceDisponible(numero, checkBox.isSelected());
					if(checkBox.isSelected()){
						box.pseudoClassStateChanged(BOX_DESELECTIONNEE, false);
					} else {
						box.pseudoClassStateChanged(BOX_DESELECTIONNEE, true);
					}
				}
			});

			if(sauce.getDisponible()){
				checkBox.setSelected(true);
				box.pseudoClassStateChanged(BOX_DESELECTIONNEE, false);
			} else {
				box.pseudoClassStateChanged(BOX_DESELECTIONNEE, true);
			}

			Label label = new Label(sauce.getNom());
			label.getStyleClass().add(App.SELECTION_ELEMENT_LABEL);
			box.setOnMouseClicked(new EventHandler<Event>() {
				public void handle(Event event) {
					checkBox.setSelected(!checkBox.isSelected());
					checkBox.requestFocus();
				}
			});

			box.getChildren().add(checkBox);
			box.getChildren().add(label);

			listeSauces.getChildren().add(box);
			i++;
		}

		Region bas = new Region();
		bas.getStyleClass().add(VIDE);
		bas.setPrefSize(1, ESPACE_HAUT_BAS_LISTE);
		bas.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		bas.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		listeSauces.getChildren().add(bas);

		Label titreSauces = new Label("SAUCES");
		titreSauces.setMaxWidth(Double.MAX_VALUE);
		titreSauces.getStyleClass().add(TITRE_GROUPE);

		groupeSauces.getChildren().add(titreSauces);
		groupeSauces.getChildren().add(listeSauces);

		return(groupeSauces);
	}

	/**
	 * Crée un panneau peuplé de checkboxes permettant de sélectionner les boissons disponibles.
	 * 
	 * @param parent le node parent de celui-ci.
	 * 
	 * @return le panneau de sélection des boissons disponibles.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final VBox groupeBoissons(Region parent){

		VBox groupeBoissons = new VBox();
		groupeBoissons.prefWidthProperty().bind(parent.widthProperty().subtract(7*ESPACE_GROUPES).divide(6));
		groupeBoissons.maxWidthProperty().bind(groupeBoissons.prefWidthProperty());
		groupeBoissons.minWidthProperty().bind(groupeBoissons.prefWidthProperty());

		VBox listeBoissons = new VBox();
		listeBoissons.getStyleClass().add(GROUPE);

		Region haut = new Region();
		haut.getStyleClass().add(VIDE);
		haut.setPrefSize(1, ESPACE_HAUT_BAS_LISTE);
		haut.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		haut.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		listeBoissons.getChildren().add(haut);

		int i = 0;
		for(Boisson boisson : BaseDonnees.getBoissons()){
			if(!boisson.equals(BaseDonnees.getRienBoisson())){
				HBox box = new HBox();
				box.getStyleClass().add(App.SELECTION_BOX);

				Region gauche = new Region();
				gauche.getStyleClass().add(App.SELECTION_GAUCHE_ELEMENT_S);
				gauche.setPrefSize(GAUCHE_ELEMENT, 1);
				gauche.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				gauche.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				box.getChildren().add(gauche);

				final int numero = i;

				CheckBox checkBox = new CheckBox();
				checkBox.getStyleClass().add(App.CHECKBOX);
				checkBox.selectedProperty().addListener(new ChangeListener() {
					public void changed(ObservableValue o, Object oldVal, Object newVal){
						boisson.setDisponible(checkBox.isSelected());
						Selection.boissonDisponible(numero, checkBox.isSelected());
						if(checkBox.isSelected()){
							box.pseudoClassStateChanged(BOX_DESELECTIONNEE, false);
						} else {
							box.pseudoClassStateChanged(BOX_DESELECTIONNEE, true);
						}
					}
				});

				if(boisson.getDisponible()){
					checkBox.setSelected(true);
					box.pseudoClassStateChanged(BOX_DESELECTIONNEE, false);
				} else {
					box.pseudoClassStateChanged(BOX_DESELECTIONNEE, true);
				}

				Label label = new Label(boisson.getNom());
				label.getStyleClass().add(App.SELECTION_ELEMENT_LABEL);
				box.setOnMouseClicked(new EventHandler<Event>() {
					public void handle(Event event) {
						checkBox.setSelected(!checkBox.isSelected());
						checkBox.requestFocus();
					}
				});

				box.getChildren().add(checkBox);
				box.getChildren().add(label);

				listeBoissons.getChildren().add(box);
				i++;
			}
		}

		Region bas = new Region();
		bas.getStyleClass().add(VIDE);
		bas.setPrefSize(1, ESPACE_HAUT_BAS_LISTE);
		bas.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		bas.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		listeBoissons.getChildren().add(bas);

		Label titreBoissons = new Label("BOISSONS");
		titreBoissons.setMaxWidth(Double.MAX_VALUE);
		titreBoissons.getStyleClass().add(TITRE_GROUPE);

		groupeBoissons.getChildren().add(titreBoissons);
		groupeBoissons.getChildren().add(listeBoissons);

		return(groupeBoissons);
	}

	/**
	 * Crée un panneau peuplé de checkboxes permettant de sélectionner les suppléments boisson disponibles.
	 * 
	 * @param parent le node parent de celui-ci.
	 * 
	 * @return le panneau de sélection des suppléments boisson disponibles.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final VBox groupeSupplementsBoisson(Region parent){

		VBox groupeSupplementsBoisson = new VBox();
		groupeSupplementsBoisson.prefWidthProperty().bind(parent.widthProperty().subtract(7*ESPACE_GROUPES).divide(6));
		groupeSupplementsBoisson.maxWidthProperty().bind(groupeSupplementsBoisson.prefWidthProperty());
		groupeSupplementsBoisson.minWidthProperty().bind(groupeSupplementsBoisson.prefWidthProperty());

		VBox listeSupplementsBoisson = new VBox();
		listeSupplementsBoisson.getStyleClass().add(GROUPE);

		Region haut = new Region();
		haut.getStyleClass().add(VIDE);
		haut.setPrefSize(1, ESPACE_HAUT_BAS_LISTE);
		haut.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		haut.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		listeSupplementsBoisson.getChildren().add(haut);

		int i = 0;
		for(SupplementBoisson supplementBoisson : BaseDonnees.getSupplementsBoisson()){
			if(!supplementBoisson.equals(BaseDonnees.getRienSupplementBoisson())){
				HBox box = new HBox();
				box.getStyleClass().add(App.SELECTION_BOX);

				Region gauche = new Region();
				gauche.getStyleClass().add(App.SELECTION_GAUCHE_ELEMENT_S);
				gauche.setPrefSize(GAUCHE_ELEMENT, 1);
				gauche.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				gauche.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				box.getChildren().add(gauche);

				final int numero = i;

				CheckBox checkBox = new CheckBox();
				checkBox.getStyleClass().add(App.CHECKBOX);
				checkBox.selectedProperty().addListener(new ChangeListener() {
					public void changed(ObservableValue o, Object oldVal, Object newVal){
						supplementBoisson.setDisponible(checkBox.isSelected());
						Selection.supplementBoissonDisponible(numero, checkBox.isSelected());
						if(checkBox.isSelected()){
							box.pseudoClassStateChanged(BOX_DESELECTIONNEE, false);
						} else {
							box.pseudoClassStateChanged(BOX_DESELECTIONNEE, true);
						}
					}
				});

				if(supplementBoisson.getDisponible()){
					checkBox.setSelected(true);
					box.pseudoClassStateChanged(BOX_DESELECTIONNEE, false);
				} else {
					box.pseudoClassStateChanged(BOX_DESELECTIONNEE, true);
				}

				Label label = new Label(supplementBoisson.getNom());
				label.getStyleClass().add(App.SELECTION_ELEMENT_LABEL);
				box.setOnMouseClicked(new EventHandler<Event>() {
					public void handle(Event event) {
						checkBox.setSelected(!checkBox.isSelected());
						checkBox.requestFocus();
					}
				});

				box.getChildren().add(checkBox);
				box.getChildren().add(label);

				listeSupplementsBoisson.getChildren().add(box);
				i++;
			}
		}

		Region bas = new Region();
		bas.getStyleClass().add(VIDE);
		bas.setPrefSize(1, ESPACE_HAUT_BAS_LISTE);
		bas.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		bas.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		listeSupplementsBoisson.getChildren().add(bas);

		Label titreSupplementsBoisson = new Label("SUPPLÉMENTS BOISSON");
		titreSupplementsBoisson.setMaxWidth(Double.MAX_VALUE);
		titreSupplementsBoisson.getStyleClass().add(TITRE_GROUPE);

		groupeSupplementsBoisson.getChildren().add(titreSupplementsBoisson);
		groupeSupplementsBoisson.getChildren().add(listeSupplementsBoisson);

		return(groupeSupplementsBoisson);
	}

	/**
	 * Crée un panneau peuplé de checkboxes permettant de sélectionner les desserts disponibles.
	 * 
	 * @param parent le node parent de celui-ci.
	 * 
	 * @return le panneau de sélection des desserts disponibles.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final VBox groupeDesserts(Region parent){

		VBox groupeDesserts = new VBox();
		groupeDesserts.prefWidthProperty().bind(parent.widthProperty().subtract(7*ESPACE_GROUPES).divide(6));
		groupeDesserts.setMaxWidth(Double.MAX_VALUE);

		VBox listeDesserts = new VBox();
		listeDesserts.getStyleClass().add(GROUPE);

		Region haut = new Region();
		haut.getStyleClass().add(VIDE);
		haut.setPrefSize(1, ESPACE_HAUT_BAS_LISTE);
		haut.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		haut.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		listeDesserts.getChildren().add(haut);

		int i = 0;
		for(Dessert dessert : BaseDonnees.getDesserts()){
			if(!dessert.equals(BaseDonnees.getRienDessert())){
				HBox box = new HBox();
				box.getStyleClass().add(App.SELECTION_BOX);

				Region gauche = new Region();
				gauche.getStyleClass().add(App.SELECTION_GAUCHE_ELEMENT_S);
				gauche.setPrefSize(GAUCHE_ELEMENT, 1);
				gauche.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				gauche.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				box.getChildren().add(gauche);

				final int numero = i;

				CheckBox checkBox = new CheckBox();
				checkBox.getStyleClass().add(App.CHECKBOX);
				checkBox.selectedProperty().addListener(new ChangeListener() {
					public void changed(ObservableValue o, Object oldVal, Object newVal){
						dessert.setDisponible(checkBox.isSelected());
						Selection.dessertDisponible(numero, checkBox.isSelected());
						if(checkBox.isSelected()){
							box.pseudoClassStateChanged(BOX_DESELECTIONNEE, false);
						} else {
							box.pseudoClassStateChanged(BOX_DESELECTIONNEE, true);
						}
					}
				});

				if(dessert.getDisponible()){
					checkBox.setSelected(true);
					box.pseudoClassStateChanged(BOX_DESELECTIONNEE, false);
				} else {
					box.pseudoClassStateChanged(BOX_DESELECTIONNEE, true);
				}

				Label label = new Label(dessert.getNom());
				label.getStyleClass().add(App.SELECTION_ELEMENT_LABEL);
				box.setOnMouseClicked(new EventHandler<Event>() {
					public void handle(Event event) {
						checkBox.setSelected(!checkBox.isSelected());
						checkBox.requestFocus();
					}
				});

				box.getChildren().add(checkBox);
				box.getChildren().add(label);

				listeDesserts.getChildren().add(box);
				i++;
			}
		}

		Region bas = new Region();
		bas.getStyleClass().add(VIDE);
		bas.setPrefSize(1, ESPACE_HAUT_BAS_LISTE);
		bas.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		bas.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		listeDesserts.getChildren().add(bas);

		Label titreDesserts = new Label("DESSERTS");
		titreDesserts.setMaxWidth(Double.MAX_VALUE);
		titreDesserts.getStyleClass().add(TITRE_GROUPE);

		groupeDesserts.getChildren().add(titreDesserts);
		groupeDesserts.getChildren().add(listeDesserts);

		return(groupeDesserts);
	}
}
