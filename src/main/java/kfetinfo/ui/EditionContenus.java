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
import kfetinfo.core.ContenuCommande;
import kfetinfo.core.CreateurBase;
import kfetinfo.core.Dessert;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Parametres;
import kfetinfo.core.Plat;
import kfetinfo.core.Sauce;
import kfetinfo.core.SupplementBoisson;

import java.util.List;
import java.util.ArrayList;

import java.util.Locale;

import java.util.Optional;
import java.util.function.UnaryOperator;

import java.text.NumberFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * <p>EditionContenu est une classe constituée uniquement d'attributs et de méthodes statiques relatifs à l'édition des informations des plats, ingrédients, sauces, boissons, suppléments boisson et desserts de la base de données.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @author karimsqualli96 de GitHub
 * @version 1.0
 */
public final class EditionContenus {

	//classes de style pour l'utilisation du CSS
	private static final String FOND = "ec-menu-fond";
	private static final String LABEL = "ec-menu-label";

	//constantes pour l'affichage
	private static final Double ESPACEMENT_ELEMENTS = 8.0;

	//listes des informations pour chaque type de contenu (regénéré lorsqu'un contenu est sélectionné)
	private static final VBox INFOS_PLATS = new VBox();
	private static final VBox INFOS_INGREDIENTS = new VBox();
	private static final VBox INFOS_SAUCES = new VBox();
	private static final VBox INFOS_BOISSONS = new VBox();
	private static final VBox INFOS_SUPPLEMENTS_BOISSON = new VBox();
	private static final VBox INFOS_DESSERTS = new VBox();

	//listes de chaque contenu pour les sélectionner
	private static ListView<ContenuCommande> listeViewPlats;
	private static ListView<ContenuCommande> listeViewIngredients;
	private static ListView<ContenuCommande> listeViewSauces;
	private static ListView<ContenuCommande> listeViewBoissons;
	private static ListView<ContenuCommande> listeViewSupplementsBoisson;
	private static ListView<ContenuCommande> listeViewDesserts;

	//pour écrire les nombres en français (avec la virgule au lieu du point)
	private static final NumberFormat NUMBER_FORMATTER = NumberFormat.getNumberInstance(Locale.FRENCH);

	//variable qu'on mettra à true pour savoir si on doit mettre à jour l'écran de sélection
	private static boolean modifie;

	/**
	 * Crée une fenêtre permettant de modifier les information des contenus commandes de la base de données.
	 */
	public static void ecran(){

		modifie = false;

		Stage theatre = new Stage();

		TabPane tabPane = new TabPane();

		Tab tabPlats = new Tab("Plats");
		tabPlats.setClosable(false);
		tabPlats.setContent(tabPlats(theatre));

		Tab tabIngredients = new Tab("Ingrédients");
		tabIngredients.setClosable(false);
		tabIngredients.setContent(tabIngredients(theatre));

		Tab tabSauces = new Tab("Sauces");
		tabSauces.setClosable(false);
		tabSauces.setContent(tabSauces(theatre));

		Tab tabBoissons = new Tab("Boissons");
		tabBoissons.setClosable(false);
		tabBoissons.setContent(tabBoissons(theatre));

		Tab tabSupplementsBoisson = new Tab("Suppléments boisson");

		tabSupplementsBoisson.setClosable(false);
		tabSupplementsBoisson.setContent(tabSupplementsBoisson(theatre));

		Tab tabDesserts = new Tab("Desserts");
		tabDesserts.setClosable(false);
		tabDesserts.setContent(tabDesserts(theatre));

		Tab tabDivers = new Tab("Divers");
		tabDivers.setClosable(false);
		tabDivers.setContent(tabDivers());

		tabPane.getTabs().setAll(tabPlats, tabIngredients, tabSauces, tabBoissons, tabSupplementsBoisson, tabDesserts, tabDivers);

		Scene scene = new Scene(tabPane, App.LARGEUR_MIN_FENETRE, App.HAUTEUR_MIN_FENETRE);

		scene.getStylesheets().add(EditionContenus.class.getResource("../../Interface/Stylesheets/general.css").toExternalForm());
		scene.getStylesheets().add(EditionContenus.class.getResource("../../Interface/Stylesheets/edition contenus.css").toExternalForm());

		theatre.setAlwaysOnTop(true);
		theatre.setResizable(false);
		theatre.initModality(Modality.APPLICATION_MODAL); //il faut fermer cette fenêtre pour revenir à l'écran principal
		theatre.setMinWidth(App.LARGEUR_MIN_FENETRE + 16);
		theatre.setMinHeight(App.HAUTEUR_MIN_FENETRE + 39);
		theatre.setTitle("Écran de modification du menu");
		theatre.setScene(scene);
		theatre.show();

		theatre.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we){
				if(modifie){
					App.mettreSelectionAJour();
				}
			}
		});
	}

	/**
	 * Crée l'onglet plats de l'écran de modification des contenus commandes. Celui-ci permet de modifier les informations relatives aux plats.
	 * 
	 * @param theatre le {@code Stage} de l'écran de modification des contenus commande.
	 * 
	 * @return l'onglet de modification des plats.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final Region tabPlats(Stage theatre){

		INFOS_PLATS.setSpacing(ESPACEMENT_ELEMENTS);

		HBox tab = new HBox();
		tab.getStyleClass().add(FOND);
		Button supprimer = new Button("Supprimer");
		supprimer.setDisable(true);

		Plat nouveau = new Plat("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0, 0, 0, false, 0);
		List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
		liste.add(nouveau);
		for(Plat plat : BaseDonnees.getPlats()){
			if(!plat.getId().equals(BaseDonnees.ID_RIEN_PLAT)){
				liste.add(plat);
			}
		}

		listeViewPlats = generateurListView(liste);

		listeViewPlats.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(listeViewPlats.getSelectionModel().getSelectedItem() != null){
					INFOS_PLATS.getChildren().clear();
					INFOS_PLATS.getChildren().addAll(generateurInfosPlat((Plat)listeViewPlats.getSelectionModel().getSelectedItem()));
					((TextField)((HBox)INFOS_PLATS.getChildren().get(1)).getChildren().get(1)).requestFocus();
					if(listeViewPlats.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						supprimer.setDisable(true);
					} else {
						supprimer.setDisable(false);
					}
				}
			}
		});

		INFOS_PLATS.getChildren().clear();
		INFOS_PLATS.getChildren().addAll(generateurInfosPlat((Plat)listeViewPlats.getSelectionModel().getSelectedItem()));

		HBox boutons = new HBox();
		supprimer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
				confirmation.initOwner(theatre);
				confirmation.setTitle("Supprimer le plat ?");
				confirmation.setHeaderText("Vous êtes sur le point de supprimer le plat « " + listeViewPlats.getSelectionModel().getSelectedItem().getNom() + " ».");
				confirmation.setContentText("Êtes-vous certain de vouloir supprimer le plat ? Vous ne pourrez plus récupérer les données.");
				Optional<ButtonType> resultat = confirmation.showAndWait();
				resultat.ifPresent(button -> {
					if(button == ButtonType.OK) {
						modifie = true;
						CreateurBase.supprimerPlat(listeViewPlats.getSelectionModel().getSelectedItem());
						BaseDonnees.chargerMenu();
						Plat nouveau = new Plat("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0, 0, 0, false, 0);
						List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
						liste.add(nouveau);
						for(Plat plat : BaseDonnees.getPlats()){
							if(!plat.getId().equals("ff56da46-bddd-4e4f-a871-6fa03b0e814b")){
								liste.add(plat);
							}
						}

						listeViewPlats.getItems().setAll(liste);
						listeViewPlats.getSelectionModel().select(0);
					}
				});
			}
		});

		Button enregistrer = new Button("Enregistrer");
		enregistrer.setDefaultButton(true);
		enregistrer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				try {
					modifie = true;
					float cout = (!(((TextField)(((HBox)INFOS_PLATS.getChildren().get(2)).getChildren().get(1))).getText()).equals("")) ? NUMBER_FORMATTER.parse(((TextField)(((HBox)INFOS_PLATS.getChildren().get(2)).getChildren().get(1))).getText()).floatValue() : 0;
					float prix = (!(((TextField)(((HBox)INFOS_PLATS.getChildren().get(2)).getChildren().get(3))).getText()).equals("")) ? NUMBER_FORMATTER.parse(((TextField)(((HBox)INFOS_PLATS.getChildren().get(2)).getChildren().get(3))).getText()).floatValue() : 0;
					if(listeViewPlats.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						CreateurBase.creerPlat(
							((TextField)((HBox)INFOS_PLATS.getChildren().get(1)).getChildren().get(1)).getText(),
							cout,
							true,
							((Integer)((Spinner)(((HBox)INFOS_PLATS.getChildren().get(3)).getChildren().get(1))).getValue()).intValue(),
							prix,
							((Integer)((Spinner)(((HBox)(INFOS_PLATS.getChildren().get(4))).getChildren().get(1))).getValue()).intValue(),
							((Integer)((Spinner)(((HBox)(INFOS_PLATS.getChildren().get(5))).getChildren().get(1))).getValue()).intValue(),
							((CheckBox)((HBox)INFOS_PLATS.getChildren().get(6)).getChildren().get(0)).isSelected());
					} else {
						CreateurBase.mettrePlatAJour(new Plat(
							listeViewPlats.getSelectionModel().getSelectedItem().getId(),
							((TextField)((HBox)INFOS_PLATS.getChildren().get(1)).getChildren().get(1)).getText(),
							cout,
							listeViewPlats.getSelectionModel().getSelectedItem().getDisponible(),
							listeViewPlats.getSelectionModel().getSelectedItem().getNbUtilisations(),
							((Integer)((Spinner)(((HBox)INFOS_PLATS.getChildren().get(3)).getChildren().get(1))).getValue()).intValue(),
							((Integer)((Spinner)(((HBox)(INFOS_PLATS.getChildren().get(4))).getChildren().get(1))).getValue()).intValue(),
							((Integer)((Spinner)(((HBox)(INFOS_PLATS.getChildren().get(5))).getChildren().get(1))).getValue()).intValue(),
							((CheckBox)((HBox)INFOS_PLATS.getChildren().get(6)).getChildren().get(0)).isSelected(),
							prix));
					}
				} catch(Exception e){
					e.printStackTrace();
				}

				BaseDonnees.chargerMenu();
				Plat nouveau = new Plat("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0, 0, 0, false, 0);
				List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
				liste.add(nouveau);
				for(Plat plat : BaseDonnees.getPlats()){
					if(!plat.getId().equals("ff56da46-bddd-4e4f-a871-6fa03b0e814b")){
						liste.add(plat);
					}
				}

				listeViewPlats.getItems().setAll(liste);
				listeViewPlats.getSelectionModel().select(0);
			}
		});

		boutons.getChildren().addAll(supprimer, enregistrer);

		AnchorPane disposition = new AnchorPane();
		AnchorPane.setTopAnchor(INFOS_PLATS, 0.0);
		AnchorPane.setLeftAnchor(INFOS_PLATS, 0.0);
		AnchorPane.setRightAnchor(boutons, 0.0);
		AnchorPane.setBottomAnchor(boutons, 0.0);

		disposition.getChildren().addAll(INFOS_PLATS, boutons);

		tab.getChildren().addAll(listeViewPlats, disposition);

		return(tab);
	}

	/**
	 * Crée l'onglet ingrédients de l'écran de modification des contenus commandes. Celui-ci permet de modifier les informations relatives aux ingrédients.
	 * 
	 * @param theatre le {@code Stage} de l'écran de modification des contenus commande.
	 * 
	 * @return l'onglet de modification des ingrédients.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final Region tabIngredients(Stage theatre){

		HBox tab = new HBox();
		tab.getStyleClass().add(FOND);
		Button supprimer = new Button("Supprimer");
		supprimer.setDisable(true);

		Ingredient nouveau = new Ingredient("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0);
		List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
		liste.add(nouveau);
		for(Ingredient ingredient : BaseDonnees.getIngredients()){
			liste.add(ingredient);
		}

		listeViewIngredients = generateurListView(liste);

		listeViewIngredients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(listeViewIngredients.getSelectionModel().getSelectedItem() != null){
					INFOS_INGREDIENTS.getChildren().clear();
					INFOS_INGREDIENTS.getChildren().addAll(generateurInfos((Ingredient)listeViewIngredients.getSelectionModel().getSelectedItem()));
					((TextField)((HBox)INFOS_INGREDIENTS.getChildren().get(1)).getChildren().get(1)).requestFocus();
					if(listeViewIngredients.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						supprimer.setDisable(true);
					} else {
						supprimer.setDisable(false);
					}
				}
			}
		});

		INFOS_INGREDIENTS.getChildren().clear();
		INFOS_INGREDIENTS.getChildren().addAll(generateurInfos((Ingredient)listeViewIngredients.getSelectionModel().getSelectedItem()));

		HBox boutons = new HBox();
		supprimer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
				confirmation.initOwner(theatre);
				confirmation.setTitle("Supprimer l'ingrédient ?");
				confirmation.setHeaderText("Vous êtes sur le point de supprimer l'ingrédient « " + listeViewIngredients.getSelectionModel().getSelectedItem().getNom() + " ».");
				confirmation.setContentText("Êtes-vous certain de vouloir supprimer l'ingrédient ? Vous ne pourrez plus récupérer les données.");
				Optional<ButtonType> resultat = confirmation.showAndWait();
				resultat.ifPresent(button -> {
					if(button == ButtonType.OK) {
						modifie = true;
						CreateurBase.supprimerIngredient(listeViewIngredients.getSelectionModel().getSelectedItem());
						BaseDonnees.chargerMenu();
						Ingredient nouveau = new Ingredient("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0);
						List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
						liste.add(nouveau);
						for(Ingredient ingredient : BaseDonnees.getIngredients()){
							liste.add(ingredient);
						}

						listeViewIngredients.getItems().setAll(liste);
						listeViewIngredients.getSelectionModel().select(0);
					}
				});
			}
		});

		Button enregistrer = new Button("Enregistrer");
		enregistrer.setDefaultButton(true);
		enregistrer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				try {
					modifie = true;
					float cout = (!(((TextField)(((HBox)INFOS_INGREDIENTS.getChildren().get(2)).getChildren().get(1))).getText()).equals("")) ? NUMBER_FORMATTER.parse(((TextField)(((HBox)INFOS_INGREDIENTS.getChildren().get(2)).getChildren().get(1))).getText()).floatValue() : 0;
					if(listeViewIngredients.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						CreateurBase.creerIngredient(
								((TextField)((HBox)INFOS_INGREDIENTS.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								true,
								((Integer)((Spinner)(((HBox)INFOS_INGREDIENTS.getChildren().get(3)).getChildren().get(1))).getValue()).intValue());
					} else {
						CreateurBase.mettreIngredientAJour(new Ingredient(
								listeViewIngredients.getSelectionModel().getSelectedItem().getId(),
								((TextField)((HBox)INFOS_INGREDIENTS.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								listeViewIngredients.getSelectionModel().getSelectedItem().getDisponible(),
								listeViewIngredients.getSelectionModel().getSelectedItem().getNbUtilisations(),
								((Integer)((Spinner)(((HBox)INFOS_INGREDIENTS.getChildren().get(3)).getChildren().get(1))).getValue()).intValue()));
					}
				} catch(Exception e){
					e.printStackTrace();
				}

				BaseDonnees.chargerMenu();
				Ingredient nouveau = new Ingredient("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0);
				List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
				liste.add(nouveau);
				for(Ingredient ingredient : BaseDonnees.getIngredients()){
					liste.add(ingredient);
				}

				listeViewIngredients.getItems().setAll(liste);
				listeViewIngredients.getSelectionModel().select(0);
			}
		});

		boutons.getChildren().addAll(supprimer, enregistrer);

		AnchorPane disposition = new AnchorPane();
		AnchorPane.setTopAnchor(INFOS_INGREDIENTS, 0.0);
		AnchorPane.setLeftAnchor(INFOS_INGREDIENTS, 0.0);
		AnchorPane.setRightAnchor(boutons, 0.0);
		AnchorPane.setBottomAnchor(boutons, 0.0);

		disposition.getChildren().addAll(INFOS_INGREDIENTS, boutons);

		tab.getChildren().addAll(listeViewIngredients, disposition);

		return(tab);
	}

	/**
	 * Crée l'onglet sauces de l'écran de modification des contenus commandes. Celui-ci permet de modifier les informations relatives aux sauces.
	 * 
	 * @param theatre le {@code Stage} de l'écran de modification des contenus commande.
	 * 
	 * @return l'onglet de modification des sauces.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final Region tabSauces(Stage theatre){

		HBox tab = new HBox();
		tab.getStyleClass().add(FOND);
		Button supprimer = new Button("Supprimer");
		supprimer.setDisable(true);

		Sauce nouveau = new Sauce("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0);
		List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
		liste.add(nouveau);
		for(Sauce sauce : BaseDonnees.getSauces()){
			liste.add(sauce);
		}

		listeViewSauces = generateurListView(liste);

		listeViewSauces.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(listeViewSauces.getSelectionModel().getSelectedItem() != null){
					INFOS_SAUCES.getChildren().clear();
					INFOS_SAUCES.getChildren().addAll(generateurInfos((Sauce)listeViewSauces.getSelectionModel().getSelectedItem()));
					((TextField)((HBox)INFOS_SAUCES.getChildren().get(1)).getChildren().get(1)).requestFocus();
					if(listeViewSauces.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						supprimer.setDisable(true);
					} else {
						supprimer.setDisable(false);
					}
				}
			}
		});

		INFOS_SAUCES.getChildren().clear();
		INFOS_SAUCES.getChildren().addAll(generateurInfos((Sauce)listeViewSauces.getSelectionModel().getSelectedItem()));

		HBox boutons = new HBox();
		supprimer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
				confirmation.initOwner(theatre);
				confirmation.setTitle("Supprimer la sauce ?");
				confirmation.setHeaderText("Vous êtes sur le point de supprimer la sauce « " + listeViewSauces.getSelectionModel().getSelectedItem().getNom() + " ».");
				confirmation.setContentText("Êtes-vous certain de vouloir supprimer la sauce ? Vous ne pourrez plus récupérer les données.");
				Optional<ButtonType> resultat = confirmation.showAndWait();
				resultat.ifPresent(button -> {
					if(button == ButtonType.OK) {
						modifie = true;
						CreateurBase.supprimerSauce(listeViewSauces.getSelectionModel().getSelectedItem());
						BaseDonnees.chargerMenu();
						Sauce nouveau = new Sauce("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0);
						List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
						liste.add(nouveau);
						for(Sauce sauce : BaseDonnees.getSauces()){
							liste.add(sauce);
						}

						listeViewSauces.getItems().setAll(liste);
						listeViewSauces.getSelectionModel().select(0);
					}
				});
			}
		});

		Button enregistrer = new Button("Enregistrer");
		enregistrer.setDefaultButton(true);
		enregistrer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				try {
					modifie = true;
					float cout = (!(((TextField)(((HBox)INFOS_SAUCES.getChildren().get(2)).getChildren().get(1))).getText()).equals("")) ? NUMBER_FORMATTER.parse(((TextField)(((HBox)INFOS_SAUCES.getChildren().get(2)).getChildren().get(1))).getText()).floatValue() : 0;
					if(listeViewSauces.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						CreateurBase.creerSauce(
								((TextField)((HBox)INFOS_SAUCES.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								true,
								((Integer)((Spinner)(((HBox)INFOS_SAUCES.getChildren().get(3)).getChildren().get(1))).getValue()).intValue());
					} else {
						CreateurBase.mettreSauceAJour(new Sauce(
								listeViewSauces.getSelectionModel().getSelectedItem().getId(),
								((TextField)((HBox)INFOS_SAUCES.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								listeViewSauces.getSelectionModel().getSelectedItem().getDisponible(),
								listeViewSauces.getSelectionModel().getSelectedItem().getNbUtilisations(),
								((Integer)((Spinner)(((HBox)INFOS_SAUCES.getChildren().get(3)).getChildren().get(1))).getValue()).intValue()));
					}
				} catch(Exception e){
					e.printStackTrace();
				}

				BaseDonnees.chargerMenu();
				Sauce nouveau = new Sauce("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0);
				List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
				liste.add(nouveau);
				for(Sauce sauce : BaseDonnees.getSauces()){
					liste.add(sauce);
				}

				listeViewSauces.getItems().setAll(liste);
				listeViewSauces.getSelectionModel().select(0);
			}
		});

		boutons.getChildren().addAll(supprimer, enregistrer);

		AnchorPane disposition = new AnchorPane();
		AnchorPane.setTopAnchor(INFOS_SAUCES, 0.0);
		AnchorPane.setLeftAnchor(INFOS_SAUCES, 0.0);
		AnchorPane.setRightAnchor(boutons, 0.0);
		AnchorPane.setBottomAnchor(boutons, 0.0);

		disposition.getChildren().addAll(INFOS_SAUCES, boutons);

		tab.getChildren().addAll(listeViewSauces, disposition);

		return(tab);
	}

	/**
	 * Crée l'onglet boissons de l'écran de modification des contenus commandes. Celui-ci permet de modifier les informations relatives aux boissons.
	 * 
	 * @param theatre le {@code Stage} de l'écran de modification des contenus commande.
	 * 
	 * @return l'onglet de modification des boissons.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final Region tabBoissons(Stage theatre){

		HBox tab = new HBox();
		tab.getStyleClass().add(FOND);
		Button supprimer = new Button("Supprimer");
		supprimer.setDisable(true);

		Boisson nouveau = new Boisson("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0);
		List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
		liste.add(nouveau);
		for(Boisson boisson : BaseDonnees.getBoissons()){
			if(!boisson.getId().equals(BaseDonnees.ID_RIEN_BOISSON)){
				liste.add(boisson);
			}
		}

		listeViewBoissons = generateurListView(liste);

		listeViewBoissons.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(listeViewBoissons.getSelectionModel().getSelectedItem() != null){
					INFOS_BOISSONS.getChildren().clear();
					INFOS_BOISSONS.getChildren().addAll(generateurInfos((Boisson)listeViewBoissons.getSelectionModel().getSelectedItem()));
					((TextField)((HBox)INFOS_BOISSONS.getChildren().get(1)).getChildren().get(1)).requestFocus();
					if(listeViewBoissons.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						supprimer.setDisable(true);
					} else {
						supprimer.setDisable(false);
					}
				}
			}
		});

		INFOS_BOISSONS.getChildren().clear();
		INFOS_BOISSONS.getChildren().addAll(generateurInfos((Boisson)listeViewBoissons.getSelectionModel().getSelectedItem()));

		HBox boutons = new HBox();
		supprimer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
				confirmation.initOwner(theatre);
				confirmation.setTitle("Supprimer la boisson ?");
				confirmation.setHeaderText("Vous êtes sur le point de supprimer la boisson « " + listeViewBoissons.getSelectionModel().getSelectedItem().getNom() + " ».");
				confirmation.setContentText("Êtes-vous certain de vouloir supprimer la boisson ? Vous ne pourrez plus récupérer les données.");
				Optional<ButtonType> resultat = confirmation.showAndWait();
				resultat.ifPresent(button -> {
					if(button == ButtonType.OK) {
						modifie = true;
						CreateurBase.supprimerBoisson(listeViewBoissons.getSelectionModel().getSelectedItem());
						BaseDonnees.chargerMenu();
						Boisson nouveau = new Boisson("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0);
						List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
						liste.add(nouveau);
						for(Boisson boisson : BaseDonnees.getBoissons()){
							liste.add(boisson);
						}

						listeViewBoissons.getItems().setAll(liste);
						listeViewBoissons.getSelectionModel().select(0);
					}
				});
			}
		});

		Button enregistrer = new Button("Enregistrer");
		enregistrer.setDefaultButton(true);
		enregistrer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				try {
					modifie = true;
					float cout = (!(((TextField)(((HBox)INFOS_BOISSONS.getChildren().get(2)).getChildren().get(1))).getText()).equals("")) ? NUMBER_FORMATTER.parse(((TextField)(((HBox)INFOS_BOISSONS.getChildren().get(2)).getChildren().get(1))).getText()).floatValue() : 0;
					if(listeViewBoissons.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						CreateurBase.creerBoisson(
								((TextField)((HBox)INFOS_BOISSONS.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								true,
								((Integer)((Spinner)(((HBox)INFOS_BOISSONS.getChildren().get(3)).getChildren().get(1))).getValue()).intValue());
					} else {
						CreateurBase.mettreBoissonAJour(new Boisson(
								listeViewBoissons.getSelectionModel().getSelectedItem().getId(),
								((TextField)((HBox)INFOS_BOISSONS.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								listeViewBoissons.getSelectionModel().getSelectedItem().getDisponible(),
								listeViewBoissons.getSelectionModel().getSelectedItem().getNbUtilisations(),
								((Integer)((Spinner)(((HBox)INFOS_BOISSONS.getChildren().get(3)).getChildren().get(1))).getValue()).intValue()));
					}
				} catch(Exception e){
					e.printStackTrace();
				}

				BaseDonnees.chargerMenu();
				Boisson nouveau = new Boisson("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0);
				List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
				liste.add(nouveau);
				for(Boisson boisson : BaseDonnees.getBoissons()){
					liste.add(boisson);
				}

				listeViewBoissons.getItems().setAll(liste);
				listeViewBoissons.getSelectionModel().select(0);
			}
		});

		boutons.getChildren().addAll(supprimer, enregistrer);

		AnchorPane disposition = new AnchorPane();
		AnchorPane.setTopAnchor(INFOS_BOISSONS, 0.0);
		AnchorPane.setLeftAnchor(INFOS_BOISSONS, 0.0);
		AnchorPane.setRightAnchor(boutons, 0.0);
		AnchorPane.setBottomAnchor(boutons, 0.0);

		disposition.getChildren().addAll(INFOS_BOISSONS, boutons);

		tab.getChildren().addAll(listeViewBoissons, disposition);

		return(tab);
	}

	/**
	 * Crée l'onglet suppléments boisson de l'écran de modification des contenus commandes. Celui-ci permet de modifier les informations relatives aux suppléments boisson.
	 * 
	 * @param theatre le {@code Stage} de l'écran de modification des contenus commande.
	 * 
	 * @return l'onglet de modification des suppléments boisson.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final Region tabSupplementsBoisson(Stage theatre){

		HBox tab = new HBox();
		tab.getStyleClass().add(FOND);
		Button supprimer = new Button("Supprimer");
		supprimer.setDisable(true);

		SupplementBoisson nouveau = new SupplementBoisson("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0, 0);
		List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
		liste.add(nouveau);
		for(SupplementBoisson supplementBoisson : BaseDonnees.getSupplementsBoisson()){
			if(!supplementBoisson.getId().equals(BaseDonnees.ID_RIEN_SUPPLEMENT_BOISSON)){
				liste.add(supplementBoisson);
			}
		}

		listeViewSupplementsBoisson = generateurListView(liste);

		listeViewSupplementsBoisson.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(listeViewSupplementsBoisson.getSelectionModel().getSelectedItem() != null){
					INFOS_SUPPLEMENTS_BOISSON.getChildren().clear();
					INFOS_SUPPLEMENTS_BOISSON.getChildren().addAll(generateurInfosSupplementBoisson((SupplementBoisson)listeViewSupplementsBoisson.getSelectionModel().getSelectedItem()));
					((TextField)((HBox)INFOS_SUPPLEMENTS_BOISSON.getChildren().get(1)).getChildren().get(1)).requestFocus();
					if(listeViewSupplementsBoisson.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						supprimer.setDisable(true);
					} else {
						supprimer.setDisable(false);
					}
				}
			}
		});

		INFOS_SUPPLEMENTS_BOISSON.getChildren().clear();
		INFOS_SUPPLEMENTS_BOISSON.getChildren().addAll(generateurInfosSupplementBoisson((SupplementBoisson)listeViewSupplementsBoisson.getSelectionModel().getSelectedItem()));

		HBox boutons = new HBox();
		supprimer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
				confirmation.initOwner(theatre);
				confirmation.setTitle("Supprimer le supplément ?");
				confirmation.setHeaderText("Vous êtes sur le point de supprimer le supplément « " + listeViewSupplementsBoisson.getSelectionModel().getSelectedItem().getNom() + " ».");
				confirmation.setContentText("Êtes-vous certain de vouloir supprimer le supplément ? Vous ne pourrez plus récupérer les données.");
				Optional<ButtonType> resultat = confirmation.showAndWait();
				resultat.ifPresent(button -> {
					if(button == ButtonType.OK) {
						modifie = true;
						CreateurBase.supprimerSupplementBoisson(listeViewSupplementsBoisson.getSelectionModel().getSelectedItem());
						BaseDonnees.chargerMenu();
						SupplementBoisson nouveau = new SupplementBoisson("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0, 0);
						List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
						liste.add(nouveau);
						for(SupplementBoisson supplementBoisson : BaseDonnees.getSupplementsBoisson()){
							liste.add(supplementBoisson);
						}

						listeViewSupplementsBoisson.getItems().setAll(liste);
						listeViewSupplementsBoisson.getSelectionModel().select(0);
					}
				});
			}
		});

		Button enregistrer = new Button("Enregistrer");
		enregistrer.setDefaultButton(true);
		enregistrer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				try {
					modifie = true;
					float cout = (!(((TextField)(((HBox)INFOS_SUPPLEMENTS_BOISSON.getChildren().get(2)).getChildren().get(1))).getText()).equals("")) ? NUMBER_FORMATTER.parse(((TextField)(((HBox)INFOS_SUPPLEMENTS_BOISSON.getChildren().get(2)).getChildren().get(1))).getText()).floatValue() : 0;
					float prix = (!(((TextField)(((HBox)INFOS_SUPPLEMENTS_BOISSON.getChildren().get(2)).getChildren().get(3))).getText()).equals("")) ? NUMBER_FORMATTER.parse(((TextField)(((HBox)INFOS_SUPPLEMENTS_BOISSON.getChildren().get(2)).getChildren().get(3))).getText()).floatValue() : 0;
					if(listeViewSupplementsBoisson.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						CreateurBase.creerSupplementBoisson(
								((TextField)((HBox)INFOS_SUPPLEMENTS_BOISSON.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								true,
								((Integer)((Spinner)(((HBox)INFOS_SUPPLEMENTS_BOISSON.getChildren().get(3)).getChildren().get(1))).getValue()).intValue(),
								prix);
					} else {
						CreateurBase.mettreSupplementBoissonAJour(new SupplementBoisson(
								listeViewSupplementsBoisson.getSelectionModel().getSelectedItem().getId(),
								((TextField)((HBox)INFOS_SUPPLEMENTS_BOISSON.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								listeViewSupplementsBoisson.getSelectionModel().getSelectedItem().getDisponible(),
								listeViewSupplementsBoisson.getSelectionModel().getSelectedItem().getNbUtilisations(),
								((Integer)((Spinner)(((HBox)INFOS_SUPPLEMENTS_BOISSON.getChildren().get(3)).getChildren().get(1))).getValue()).intValue(),
								prix));
					}
				} catch(Exception e){
					e.printStackTrace();
				}

				BaseDonnees.chargerMenu();
				SupplementBoisson nouveau = new SupplementBoisson("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0, 0);
				List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
				liste.add(nouveau);
				for(SupplementBoisson supplementBoisson : BaseDonnees.getSupplementsBoisson()){
					liste.add(supplementBoisson);
				}

				listeViewSupplementsBoisson.getItems().setAll(liste);
				listeViewSupplementsBoisson.getSelectionModel().select(0);
			}
		});

		boutons.getChildren().addAll(supprimer, enregistrer);

		AnchorPane disposition = new AnchorPane();
		AnchorPane.setTopAnchor(INFOS_SUPPLEMENTS_BOISSON, 0.0);
		AnchorPane.setLeftAnchor(INFOS_SUPPLEMENTS_BOISSON, 0.0);
		AnchorPane.setRightAnchor(boutons, 0.0);
		AnchorPane.setBottomAnchor(boutons, 0.0);

		disposition.getChildren().addAll(INFOS_SUPPLEMENTS_BOISSON, boutons);

		tab.getChildren().addAll(listeViewSupplementsBoisson, disposition);

		return(tab);
	}

	/**
	 * Crée l'onglet desserts de l'écran de modification des contenus commandes. Celui-ci permet de modifier les informations relatives aux desserts.
	 * 
	 * @param theatre le {@code Stage} de l'écran de modification des contenus commande.
	 * 
	 * @return l'onglet de modification des desserts.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final Region tabDesserts(Stage theatre){

		HBox tab = new HBox();
		tab.getStyleClass().add(FOND);
		Button supprimer = new Button("Supprimer");
		supprimer.setDisable(true);

		Dessert nouveau = new Dessert("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0, 0);
		List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
		liste.add(nouveau);
		for(Dessert dessert : BaseDonnees.getDesserts()){
			if(!dessert.getId().equals(BaseDonnees.ID_RIEN_DESSERT)){
				liste.add(dessert);
			}
		}

		listeViewDesserts = generateurListView(liste);

		listeViewDesserts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(listeViewDesserts.getSelectionModel().getSelectedItem() != null){
					INFOS_DESSERTS.getChildren().clear();
					INFOS_DESSERTS.getChildren().addAll(generateurInfosDessert((Dessert)listeViewDesserts.getSelectionModel().getSelectedItem()));
					((TextField)((HBox)INFOS_DESSERTS.getChildren().get(1)).getChildren().get(1)).requestFocus();
					if(listeViewDesserts.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						supprimer.setDisable(true);
					} else {
						supprimer.setDisable(false);
					}
				}
			}
		});

		INFOS_DESSERTS.getChildren().clear();
		INFOS_DESSERTS.getChildren().addAll(generateurInfosDessert((Dessert)listeViewDesserts.getSelectionModel().getSelectedItem()));

		HBox boutons = new HBox();
		supprimer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
				confirmation.initOwner(theatre);
				confirmation.setTitle("Supprimer le dessert ?");
				confirmation.setHeaderText("Vous êtes sur le point de supprimer le dessert « " + listeViewDesserts.getSelectionModel().getSelectedItem().getNom() + " ».");
				confirmation.setContentText("Êtes-vous certain de vouloir supprimer le dessert ? Vous ne pourrez plus récupérer les données.");
				Optional<ButtonType> resultat = confirmation.showAndWait();
				resultat.ifPresent(button -> {
					if(button == ButtonType.OK) {
						modifie = true;
						CreateurBase.supprimerDessert(listeViewDesserts.getSelectionModel().getSelectedItem());
						BaseDonnees.chargerMenu();
						Dessert nouveau = new Dessert("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0, 0);
						List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
						liste.add(nouveau);
						for(Dessert dessert : BaseDonnees.getDesserts()){
							liste.add(dessert);
						}

						listeViewDesserts.getItems().setAll(liste);
						listeViewDesserts.getSelectionModel().select(0);
					}
				});
			}
		});

		Button enregistrer = new Button("Enregistrer");
		enregistrer.setDefaultButton(true);
		enregistrer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				try {
					modifie = true;
					float cout = (!(((TextField)(((HBox)INFOS_DESSERTS.getChildren().get(2)).getChildren().get(1))).getText()).equals("")) ? NUMBER_FORMATTER.parse(((TextField)(((HBox)INFOS_DESSERTS.getChildren().get(2)).getChildren().get(1))).getText()).floatValue() : 0;
					float prix = (!(((TextField)(((HBox)INFOS_DESSERTS.getChildren().get(2)).getChildren().get(3))).getText()).equals("")) ? NUMBER_FORMATTER.parse(((TextField)(((HBox)INFOS_DESSERTS.getChildren().get(2)).getChildren().get(3))).getText()).floatValue() : 0;
					if(listeViewDesserts.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						CreateurBase.creerDessert(
								((TextField)((HBox)INFOS_DESSERTS.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								true,
								((Integer)((Spinner)(((HBox)INFOS_DESSERTS.getChildren().get(3)).getChildren().get(1))).getValue()).intValue(),
								prix);
					} else {
						CreateurBase.mettreDessertAJour(new Dessert(
								listeViewDesserts.getSelectionModel().getSelectedItem().getId(),
								((TextField)((HBox)INFOS_DESSERTS.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								listeViewDesserts.getSelectionModel().getSelectedItem().getDisponible(),
								listeViewDesserts.getSelectionModel().getSelectedItem().getNbUtilisations(),
								((Integer)((Spinner)(((HBox)INFOS_DESSERTS.getChildren().get(3)).getChildren().get(1))).getValue()).intValue(),
								prix));
					}
				} catch(Exception e){
					e.printStackTrace();
				}

				BaseDonnees.chargerMenu();
				Dessert nouveau = new Dessert("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0, 0);
				List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
				liste.add(nouveau);
				for(Dessert dessert : BaseDonnees.getDesserts()){
					liste.add(dessert);
				}

				listeViewDesserts.getItems().setAll(liste);
				listeViewDesserts.getSelectionModel().select(0);
			}
		});

		boutons.getChildren().addAll(supprimer, enregistrer);

		AnchorPane disposition = new AnchorPane();
		AnchorPane.setTopAnchor(INFOS_DESSERTS, 0.0);
		AnchorPane.setLeftAnchor(INFOS_DESSERTS, 0.0);
		AnchorPane.setRightAnchor(boutons, 0.0);
		AnchorPane.setBottomAnchor(boutons, 0.0);

		disposition.getChildren().addAll(INFOS_DESSERTS, boutons);

		tab.getChildren().addAll(listeViewDesserts, disposition);

		return(tab);
	}

	/**
	 * Crée l'onglet divers de l'écran de modification des contenus commandes. Celui-ci permet de modifier les informations relatives aux paramètres basiques de l'application comme le prix d'une boisson ou la réduction accordée pour un menu.
	 * @param theatre le {@code Stage} de l'écran de modification des contenus commande.
	 * 
	 * @return l'onglet de modification des divers.
	 */
	private static final Region tabDivers(){

		AnchorPane pane = new AnchorPane();
		pane.getStyleClass().add(FOND);

		VBox divers = new VBox();

		HBox prixIngredientSupplementaireBox = new HBox();
		Label prixIngredientSupplementaireLabel = new Label("Prix d'un ingrédient supplémentaire : ");
		prixIngredientSupplementaireLabel.getStyleClass().add(LABEL);
		TextField prixIngredientSupplementaireField = new TextField();
		prixIngredientSupplementaireField.getStyleClass().add(App.FIELD);
		prixIngredientSupplementaireLabel.setLabelFor(prixIngredientSupplementaireField);
		prixIngredientSupplementaireField.setPromptText("Prix d'un ingrédient supplémentaire");
        prixIngredientSupplementaireField.setText("" + NUMBER_FORMATTER.format(Parametres.getPrixIngredientSupp()));

        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>(){ //ça c'est pas de moi (https://gist.github.com/karimsqualli96/f8d4c2995da8e11496ed) et je l'ai un peu modifié, ça permet de restreindre l'écriture dans le field aux nombres réels en français - merci à karimsqualli96

            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {

                if (t.isReplaced()) 
                    if(t.getText().matches("[^0-9]"))
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                

                if (t.isAdded()) {
                    if (t.getControlText().contains(",")) {
                        if (t.getText().matches("[^0-9]")) {
                            t.setText("");
                        }
                    } else if (t.getText().matches("[^0-9,]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };

        prixIngredientSupplementaireField.setTextFormatter(new TextFormatter<>(filter));

		prixIngredientSupplementaireBox.getChildren().addAll(prixIngredientSupplementaireLabel, prixIngredientSupplementaireField);

		HBox prixBoissonBox = new HBox();
		Label prixBoissonLabel = new Label("Prix d'une boisson : ");
		prixBoissonLabel.getStyleClass().add(LABEL);
		TextField prixBoissonField = new TextField();
		prixBoissonField.getStyleClass().add(App.FIELD);
		prixBoissonLabel.setLabelFor(prixBoissonField);
		prixBoissonField.setPromptText("Prix d'une boisson");
        prixBoissonField.setText("" + NUMBER_FORMATTER.format(Parametres.getPrixBoisson()));

        prixBoissonField.setTextFormatter(new TextFormatter<>(filter));

		prixBoissonBox.getChildren().addAll(prixBoissonLabel, prixBoissonField);

		HBox reducMenuBox = new HBox();
		Label reducMenuLabel = new Label("Montant de la réduction accordée pour un menu : ");
		reducMenuLabel.getStyleClass().add(LABEL);
		TextField reducMenuField = new TextField();
		reducMenuField.getStyleClass().add(App.FIELD);
		reducMenuLabel.setLabelFor(reducMenuField);
		reducMenuField.setPromptText("Montant de la réduction accordée pour un menu");
        reducMenuField.setText("" + NUMBER_FORMATTER.format(Parametres.getReducMenu()));

        reducMenuField.setTextFormatter(new TextFormatter<>(filter));

		reducMenuBox.getChildren().addAll(reducMenuLabel, reducMenuField);

		HBox coutPainBox = new HBox();
		Label coutPainLabel = new Label("Coût d'une baguette de pain : ");
		coutPainLabel.getStyleClass().add(LABEL);
		TextField coutPainField = new TextField();
		coutPainField.getStyleClass().add(App.FIELD);
		coutPainLabel.setLabelFor(coutPainField);
		coutPainField.setPromptText("Coût d'une baguette de pain");
        coutPainField.setText("" + NUMBER_FORMATTER.format(Parametres.getCoutPain()));

        coutPainField.setTextFormatter(new TextFormatter<>(filter));

		coutPainBox.getChildren().addAll(coutPainLabel, coutPainField);

		divers.getChildren().add(prixIngredientSupplementaireBox);
		divers.getChildren().add(prixBoissonBox);
		divers.getChildren().add(reducMenuBox);
		divers.getChildren().add(coutPainBox);

		Button enregistrer = new Button("Enregistrer");
		enregistrer.setDefaultButton(true);
		enregistrer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				try {
					float prixIngredientSupplementaire = (!prixIngredientSupplementaireField.getText().equals("")) ? NUMBER_FORMATTER.parse(prixIngredientSupplementaireField.getText()).floatValue() : Parametres.PRIX_INGREDIENT_SUPP_DEFAUT;
					float prixBoisson = (!prixBoissonField.getText().equals("")) ? NUMBER_FORMATTER.parse(prixBoissonField.getText()).floatValue() : Parametres.PRIX_BOISSON_DEFAUT;
					float reducMenu = (!reducMenuField.getText().equals("")) ? NUMBER_FORMATTER.parse(reducMenuField.getText()).floatValue() : Parametres.REDUC_MENU_DEFAUT;
					float coutPain = (!reducMenuField.getText().equals("")) ? NUMBER_FORMATTER.parse(coutPainField.getText()).floatValue() : Parametres.COUT_PAIN_DEFAUT;
					Parametres.setPrixIngredientSupp(prixIngredientSupplementaire);
					Parametres.setPrixBoisson(prixBoisson);
					Parametres.setReducMenu(reducMenu);
					Parametres.setCoutPain(coutPain);
					Parametres.ecrireFichier();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		AnchorPane.setTopAnchor(divers, 0.0);
		AnchorPane.setLeftAnchor(divers, 0.0);
		AnchorPane.setRightAnchor(enregistrer, 0.0);
		AnchorPane.setBottomAnchor(enregistrer, 0.0);

		pane.getChildren().addAll(divers, enregistrer);
		
		return(pane);
	}

	/**
	 * Renvoie un {@code ListeView<ContenuCommande>} permettant de sélectionner le contenu à modifier.
	 * 
	 * @param liste la liste de contenus commande qu'on utilisera pour créer la liste sélectionnable.
	 * 
	 * @return la liste de contenus sélectionnable.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final ListView<ContenuCommande> generateurListView(List<ContenuCommande> liste){

		ListView<ContenuCommande> listView = new ListView();
		listView.getStyleClass().add(App.LISTVIEW);

		listView.getItems().setAll(liste);
		listView.getSelectionModel().select(liste.get(0));

		listView.setCellFactory(lv -> new ContenuCommandeCell());

		listView.setMaxWidth(150);
		listView.setMinWidth(150);
		listView.setPrefHeight(500);

		return(listView);
	}

	/**
	 * Renvoie la liste des informations relatives au contenu commande passé en paramètres modifiables communes à tous les contenus commande ({@code id}, {@code nom}, {@code cout} et {@code priorite}).
	 * 
	 * @param contenu le contenu commande à modifier.
	 * 
	 * @return la liste des informations modifiables du contenu commande.
	 */
	private static final List<Node> generateurInfos(ContenuCommande contenu){

		List<Node> nodes = new ArrayList<Node>();

		HBox idBox = new HBox();
		Label idLabel = new Label("Identificateur : ");
		idLabel.getStyleClass().add(LABEL);
		TextField idField = new TextField();
		idField.getStyleClass().add(App.FIELD);
		idField.getStyleClass().add(App.CODE);
		idLabel.setLabelFor(idField);
		idField.setText(contenu.getId());
		idField.setDisable(true);
		Button idCopierBouton = new Button("Copier");
		idCopierBouton.getStyleClass().add(App.BOUTON);
		if(contenu.getId().equals("Sera généré au moment de l'enregistrement")){
			idCopierBouton.setDisable(true);
		}
		idCopierBouton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Clipboard pressePapier = Clipboard.getSystemClipboard();
				ClipboardContent contenu = new ClipboardContent();
				contenu.put(DataFormat.PLAIN_TEXT, idField.getText());
				pressePapier.setContent(contenu);
			}
		});

		idBox.getChildren().addAll(idLabel, idField, idCopierBouton);

		HBox nomBox = new HBox();
		Label nomLabel = new Label("Nom : ");
		nomLabel.getStyleClass().add(LABEL);
		TextField nomField = new TextField();
		nomField.getStyleClass().add(App.FIELD);
		nomLabel.setLabelFor(nomField);
		nomField.setPromptText("Nom");
		nomField.setText(contenu.getNom());

		nomBox.getChildren().addAll(nomLabel, nomField);

		HBox coutBox = new HBox();
		Label coutLabel = new Label("Coût : ");
		coutLabel.getStyleClass().add(LABEL);
		TextField coutField = new TextField();
		coutField.getStyleClass().add(App.FIELD);
		coutLabel.setLabelFor(coutField);
		coutField.setPromptText("Coût");
        coutField.setText("" + NUMBER_FORMATTER.format(contenu.getCout()));

        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>(){ //ça c'est pas de moi (https://gist.github.com/karimsqualli96/f8d4c2995da8e11496ed) et je l'ai un peu modifié, ça permet de restreindre l'écriture dans le field aux nombres réels en français - merci à karimsqualli96

            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {

                if (t.isReplaced()) 
                    if(t.getText().matches("[^0-9]"))
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                

                if (t.isAdded()) {
                    if (t.getControlText().contains(",")) {
                        if (t.getText().matches("[^0-9]")) {
                            t.setText("");
                        }
                    } else if (t.getText().matches("[^0-9,]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };

        coutField.setTextFormatter(new TextFormatter<>(filter));

		coutBox.getChildren().addAll(coutLabel, coutField);

		HBox prioriteBox = new HBox();
		Label prioriteLabel = new Label("Priorité : ");
		prioriteLabel.getStyleClass().add(LABEL);
		Spinner<Integer> prioriteSpinner = new Spinner<Integer>();
		prioriteSpinner.getStyleClass().add(App.SPINNER);
		prioriteLabel.setLabelFor(prioriteSpinner);
		SpinnerValueFactory<Integer> usineAValeurs = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, contenu.getPriorite());
		prioriteSpinner.setValueFactory(usineAValeurs);

		prioriteBox.getChildren().addAll(prioriteLabel, prioriteSpinner);

		nodes.add(idBox);
		nodes.add(nomBox);
		nodes.add(coutBox);
		nodes.add(prioriteBox);
		
		return(nodes);
	}

	/**
	 * Renvoie la liste des informations relatives au plat passé en paramètres modifiables communes à tous les plats ({@code id}, {@code nom}, {@code cout}, {@code priorite}, {@code prix}, {@code nbMaxIngredients}, {@code nbMaxSauces}, et {@code utilisePain}).
	 * 
	 * @param plat le plat à modifier.
	 * 
	 * @return la liste des informations modifiables du plat.
	 */
	private static final List<Node> generateurInfosPlat(Plat plat){

		List<Node> nodes = generateurInfos(plat);

        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>(){ //ça c'est pas de moi (https://gist.github.com/karimsqualli96/f8d4c2995da8e11496ed) et je l'ai un peu modifié, ça permet de restreindre l'écriture dans le field aux nombres réels en français - merci à karimsqualli96

            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {

                if (t.isReplaced()) 
                    if(t.getText().matches("[^0-9]"))
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                

                if (t.isAdded()) {
                    if (t.getControlText().contains(",")) {
                        if (t.getText().matches("[^0-9]")) {
                            t.setText("");
                        }
                    } else if (t.getText().matches("[^0-9,]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };

		Label prixLabel = new Label("Prix : ");
		prixLabel.getStyleClass().add(LABEL);
		TextField prixField = new TextField();
		prixField.getStyleClass().add(App.FIELD);
		prixLabel.setLabelFor(prixField);
		prixField.setPromptText("Prix");
        prixField.setText("" + NUMBER_FORMATTER.format(plat.getPrix()));

        prixField.setTextFormatter(new TextFormatter<>(filter));

		HBox coutPrixBox = (HBox)nodes.get(2);
		coutPrixBox.getChildren().addAll(prixLabel, prixField);

		HBox nbMaxIngredientsBox = new HBox();
		Label nbMaxIngredientsLabel = new Label("Nombre maximal d'ingrédients : ");
		nbMaxIngredientsLabel.getStyleClass().add(LABEL);
		Spinner<Integer> nbMaxIngredientsSpinner = new Spinner<Integer>();
		nbMaxIngredientsSpinner.getStyleClass().add(App.SPINNER);
		nbMaxIngredientsLabel.setLabelFor(nbMaxIngredientsSpinner);
		SpinnerValueFactory<Integer> usineAValeursIngredients = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, plat.getNbMaxIngredients());
		nbMaxIngredientsSpinner.setValueFactory(usineAValeursIngredients);

		nbMaxIngredientsBox.getChildren().addAll(nbMaxIngredientsLabel, nbMaxIngredientsSpinner);

		HBox nbMaxSaucesBox = new HBox();
		Label nbMaxSaucesLabel = new Label("Nombre maximal de sauces : ");
		nbMaxSaucesLabel.getStyleClass().add(LABEL);
		Spinner<Integer> nbMaxSaucesSpinner = new Spinner<Integer>();
		nbMaxSaucesSpinner.getStyleClass().add(App.SPINNER);
		nbMaxSaucesLabel.setLabelFor(nbMaxSaucesSpinner);
		SpinnerValueFactory<Integer> usineAValeursSauces = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, plat.getNbMaxSauces());
		nbMaxSaucesSpinner.setValueFactory(usineAValeursSauces);

		nbMaxSaucesBox.getChildren().addAll(nbMaxSaucesLabel, nbMaxSaucesSpinner);

		HBox utilisePainBox = new HBox();
		CheckBox utilisePainCheckBox = new CheckBox();
		utilisePainCheckBox.getStyleClass().add(App.CHECKBOX);
		if(plat.getUtilisePain()){
			utilisePainCheckBox.setSelected(true);
		}
		Label utilisePainLabel = new Label("Utilise du pain");
		utilisePainLabel.getStyleClass().add(LABEL);
		utilisePainLabel.setLabelFor(utilisePainCheckBox);
		utilisePainLabel.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event e){
				utilisePainCheckBox.setSelected(!utilisePainCheckBox.isSelected());
			}
		});

		utilisePainBox.getChildren().addAll(utilisePainCheckBox, utilisePainLabel);

		nodes.add(nbMaxIngredientsBox);
		nodes.add(nbMaxSaucesBox);
		nodes.add(utilisePainBox);
		
		return(nodes);
	}

	/**
	 * Renvoie la liste des informations relatives au supplément boisson passé en paramètres modifiables communes à tous les suppléments boisson ({@code id}, {@code nom}, {@code cout}, {@code priorite}, et {@code prix}).
	 * 
	 * @param supplementBoisson le supplément boisson à modifier.
	 * 
	 * @return la liste des informations modifiables du supplément boisson.
	 */
	private static final List<Node> generateurInfosSupplementBoisson(SupplementBoisson supplementBoisson){

		List<Node> nodes = generateurInfos(supplementBoisson);

        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>(){ //ça c'est pas de moi (https://gist.github.com/karimsqualli96/f8d4c2995da8e11496ed) et je l'ai un peu modifié, ça permet de restreindre l'écriture dans le field aux nombres réels en français - merci à karimsqualli96

            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {

                if (t.isReplaced()) 
                    if(t.getText().matches("[^0-9]"))
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                

                if (t.isAdded()) {
                    if (t.getControlText().contains(",")) {
                        if (t.getText().matches("[^0-9]")) {
                            t.setText("");
                        }
                    } else if (t.getText().matches("[^0-9,]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };

		Label prixLabel = new Label("Prix : ");
		prixLabel.getStyleClass().add(LABEL);
		TextField prixField = new TextField();
		prixField.getStyleClass().add(App.FIELD);
		prixLabel.setLabelFor(prixField);
		prixField.setPromptText("Prix");
        prixField.setText("" + NUMBER_FORMATTER.format(supplementBoisson.getPrix()));

        prixField.setTextFormatter(new TextFormatter<>(filter));

		HBox coutPrixBox = (HBox)nodes.get(2);
		coutPrixBox.getChildren().addAll(prixLabel, prixField);
		
		return(nodes);
	}

	/**
	 * Renvoie la liste des informations relatives au dessert passé en paramètres modifiables communes à tous les desserts ({@code id}, {@code nom}, {@code cout}, {@code priorite}, et {@code prix}).
	 * 
	 * @param dessert le dessert à modifier.
	 * 
	 * @return la liste des informations modifiables du dessert.
	 */
	private static final List<Node> generateurInfosDessert(Dessert dessert){

		List<Node> nodes = generateurInfos(dessert);

        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>(){ //ça c'est pas de moi (https://gist.github.com/karimsqualli96/f8d4c2995da8e11496ed) et je l'ai un peu modifié, ça permet de restreindre l'écriture dans le field aux nombres réels en français - merci à karimsqualli96

            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {

                if (t.isReplaced()) 
                    if(t.getText().matches("[^0-9]"))
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                

                if (t.isAdded()) {
                    if (t.getControlText().contains(",")) {
                        if (t.getText().matches("[^0-9]")) {
                            t.setText("");
                        }
                    } else if (t.getText().matches("[^0-9,]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };

		Label prixLabel = new Label("Prix : ");
		prixLabel.getStyleClass().add(LABEL);
		TextField prixField = new TextField();
		prixField.getStyleClass().add(App.FIELD);
		prixLabel.setLabelFor(prixField);
		prixField.setPromptText("Prix");
        prixField.setText("" + NUMBER_FORMATTER.format(dessert.getPrix()));

        prixField.setTextFormatter(new TextFormatter<>(filter));

		HBox coutPrixBox = (HBox)nodes.get(2);
		coutPrixBox.getChildren().addAll(prixLabel, prixField);
		
		return(nodes);
	}
}
