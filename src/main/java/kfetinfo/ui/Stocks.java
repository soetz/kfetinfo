package kfetinfo.ui;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import kfetinfo.core.BaseDonnees;
import kfetinfo.core.Boisson;
import kfetinfo.core.Core;
import kfetinfo.core.Dessert;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Plat;
import kfetinfo.core.Sauce;
import kfetinfo.core.SupplementBoisson;

/**
 * <p>Stocks est une classe constituée uniquement d'attributs et de méthodes statiques relatifs à l'affichage de l'écran permettant de sélectionner les contenus commande en stocks ainsi que les quantités de pain achetées et réservées aux membres pour le service.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public final class Stocks {

	//variables transversales qui seront utilisées lors de l'enregistrement
	private static List<CheckBox> checkBoxesPlats;
	private static float nbBaguettesAchetees;
	private static float nbBaguettesReservees;

	/**
	 * Crée une fenêtre permettant de gérer les stocks de contenus commande et de pain.
	 * 
	 * @param ecranPrincipal le {@code Stage} de la fenêtre principal du logiciel.
	 */
	public static final void ecranStocks(Stage ecranPrincipal){

		BorderPane pane = new BorderPane();

		HBox selection = new HBox();

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

		GridPane layout = new GridPane();

		Label baguettesUtilisees = new Label("Nombre de baguettes utilisées");
		Label baguettesAchetees = new Label("Nombre de baguettes achetées");
		Label baguettesMembres = new Label("Nombre de baguettes réservées aux membres");

		NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.FRENCH);
		Label nbUtilisees = new Label("" + numberFormatter.format(Core.getService().getNbBaguettesUtilisees()));

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

		spinnerReservees.focusedProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(!spinnerReservees.isFocused()){ //lorsque le focus est perdu, on affecte la valeur du spinner à la variable nbBaguettesAchetees
					Float nbBaguettesReserveesV = ((Number)spinnerReservees.getValue()).floatValue();
					nbBaguettesReservees = (nbBaguettesReserveesV == null) ? 0 : nbBaguettesReserveesV;
				}
			}
		});

		Button appliquer = new Button("Appliquer");
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

		AnchorPane.setLeftAnchor(layout, 3.0);
		AnchorPane.setBottomAnchor(layout, 3.0);

		baguettes.getChildren().add(layout);

		pane.setCenter(selection);
		pane.setBottom(baguettes);

		Scene scene = new Scene(pane, App.LARGEUR_MIN_FENETRE, App.HAUTEUR_MIN_FENETRE);
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
	public static final VBox groupePlats(Region parent){

		VBox groupePlats = new VBox();

		VBox listePlats = new VBox();

		int i = 0;
		for(Plat plat : BaseDonnees.getPlats()){
			if(!plat.equals(BaseDonnees.getRienPlat())){
				HBox box = new HBox();

				final int numero = i;

				CheckBox checkBox = new CheckBox();
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
						}

						plat.setDisponible(checkBox.isSelected());
						Selection.platDisponible(numero, !grise);
					}
				});

				checkBoxesPlats.add(checkBox);

				if(plat.getDisponible()){
					checkBox.setSelected(true);
				}

				Label label = new Label(plat.getNom());
				label.setOnMouseClicked(new EventHandler<Event>() {
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

		Label titrePlats = new Label("PLATS");

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
	public static final VBox groupeIngredients(Region parent){

		VBox groupeIngredients = new VBox();

		VBox listeIngredients = new VBox();

		int i = 0;
		for(Ingredient ingredient : BaseDonnees.getIngredients()){
			HBox box = new HBox();

			final int numero = i;

			CheckBox checkBox = new CheckBox();
			checkBox.selectedProperty().addListener(new ChangeListener() {
				public void changed(ObservableValue o, Object oldVal, Object newVal){
					ingredient.setDisponible(checkBox.isSelected());
					Selection.ingredientDisponible(numero, checkBox.isSelected());
				}
			});

			if(ingredient.getDisponible()){
				checkBox.setSelected(true);
			}

			Label label = new Label(ingredient.getNom());
			label.setOnMouseClicked(new EventHandler<Event>() {
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

		Label titreIngredients = new Label("INGREDIENTS");

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
	public static final VBox groupeSauces(Region parent){

		VBox groupeSauces = new VBox();

		VBox listeSauces = new VBox();

		int i = 0;
		for(Sauce sauce : BaseDonnees.getSauces()){
			HBox box = new HBox();

			final int numero = i;

			CheckBox checkBox = new CheckBox();
			checkBox.selectedProperty().addListener(new ChangeListener() {
				public void changed(ObservableValue o, Object oldVal, Object newVal){
					sauce.setDisponible(checkBox.isSelected());
					Selection.sauceDisponible(numero, checkBox.isSelected());
				}
			});

			if(sauce.getDisponible()){
				checkBox.setSelected(true);
			}

			Label label = new Label(sauce.getNom());
			label.setOnMouseClicked(new EventHandler<Event>() {
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

		Label titreSauces = new Label("SAUCES");

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
	public static final VBox groupeBoissons(Region parent){

		VBox groupeBoissons = new VBox();

		VBox listeBoissons = new VBox();

		int i = 0;
		for(Boisson boisson : BaseDonnees.getBoissons()){
			if(!boisson.equals(BaseDonnees.getRienBoisson())){
				HBox box = new HBox();

				final int numero = i;

				CheckBox checkBox = new CheckBox();
				checkBox.selectedProperty().addListener(new ChangeListener() {
					public void changed(ObservableValue o, Object oldVal, Object newVal){
						boisson.setDisponible(checkBox.isSelected());
						Selection.boissonDisponible(numero, checkBox.isSelected());
					}
				});

				if(boisson.getDisponible()){
					checkBox.setSelected(true);
				}

				Label label = new Label(boisson.getNom());
				label.setOnMouseClicked(new EventHandler<Event>() {
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

		Label titreBoissons = new Label("BOISSONS");

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
	public static final VBox groupeSupplementsBoisson(Region parent){

		VBox groupeSupplementsBoisson = new VBox();

		VBox listeSupplementsBoisson = new VBox();

		int i = 0;
		for(SupplementBoisson supplementBoisson : BaseDonnees.getSupplementsBoisson()){
			if(!supplementBoisson.equals(BaseDonnees.getRienSupplementBoisson())){
				HBox box = new HBox();

				final int numero = i;

				CheckBox checkBox = new CheckBox();
				checkBox.selectedProperty().addListener(new ChangeListener() {
					public void changed(ObservableValue o, Object oldVal, Object newVal){
						supplementBoisson.setDisponible(checkBox.isSelected());
						Selection.supplementBoissonDisponible(numero, checkBox.isSelected());
					}
				});

				if(supplementBoisson.getDisponible()){
					checkBox.setSelected(true);
				}

				Label label = new Label(supplementBoisson.getNom());
				label.setOnMouseClicked(new EventHandler<Event>() {
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

		Label titreSupplementsBoisson = new Label("SUPPLÉMENTS BOISSON");

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
	public static final VBox groupeDesserts(Region parent){

		VBox groupeDesserts = new VBox();

		VBox listeDesserts = new VBox();

		int i = 0;
		for(Dessert dessert : BaseDonnees.getDesserts()){
			if(!dessert.equals(BaseDonnees.getRienDessert())){
				HBox box = new HBox();

				final int numero = i;

				CheckBox checkBox = new CheckBox();
				checkBox.selectedProperty().addListener(new ChangeListener() {
					public void changed(ObservableValue o, Object oldVal, Object newVal){
						dessert.setDisponible(checkBox.isSelected());
						Selection.dessertDisponible(numero, checkBox.isSelected());
					}
				});

				if(dessert.getDisponible()){
					checkBox.setSelected(true);
				}

				Label label = new Label(dessert.getNom());
				label.setOnMouseClicked(new EventHandler<Event>() {
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

		Label titreDesserts = new Label("DESSERTS");

		groupeDesserts.getChildren().add(titreDesserts);
		groupeDesserts.getChildren().add(listeDesserts);

		return(groupeDesserts);
	}
}
