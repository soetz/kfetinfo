package kfetinfo.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kfetinfo.core.BaseDonnees;
import kfetinfo.core.Boisson;
import kfetinfo.core.Core;
import kfetinfo.core.CreateurBase;
import kfetinfo.core.Dessert;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Plat;
import kfetinfo.core.Sauce;
import kfetinfo.core.SupplementBoisson;

public class Stocks {
	static List<CheckBox> checkBoxesPlats;
	static float nbBaguettesAchetees;
	static float nbBaguettesReservees;

	public static void ecranStocks(Core core, Stage ecranPrincipal){
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

		Label nbUtilisees = new Label("" + core.getService().getNbBaguettesUtilisees());

		nbBaguettesAchetees = core.getService().getNbBaguettesAchetees();
		nbBaguettesReservees = core.getService().getNbBaguettesReservees();

		Spinner<Float> spinnerAchetees = new Spinner(new SpinnerValueFactory() {
			{
				setValue(core.getService().getNbBaguettesAchetees());
			}

			public void decrement(int steps){
				final Float value = ((Number)getValue()).floatValue();
				final float val = (value == null) ? 0 : value;
				if(val - 0.5*steps >= nbBaguettesReservees + core.getService().getNbBaguettesUtilisees()){
					setValue(val - 0.5*steps);
				}
			}

			public void increment(int steps){
				final Float value = ((Number)getValue()).floatValue();
				final float val = (value == null) ? 0 : value;
				setValue(val + 0.5*steps);
			}
		});

		spinnerAchetees.focusedProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(!spinnerAchetees.isFocused()){
					Float nbBaguettesAcheteesV = ((Number)spinnerAchetees.getValue()).floatValue();
					nbBaguettesAchetees = (nbBaguettesAcheteesV == null) ? 0 : nbBaguettesAcheteesV;
				}
			}
		});

		Spinner<Float> spinnerReservees = new Spinner(new SpinnerValueFactory() {
			{
				setValue(core.getService().getNbBaguettesReservees());
			}

			public void decrement(int steps){
				final Float value = ((Number)getValue()).floatValue();
				final float val = (value == null) ? 0 : value;
				if(val > 0){
					setValue(val - 0.5*steps);
				}
			}

			public void increment(int steps){
				final Float value = ((Number)getValue()).floatValue();
				final float val = (value == null) ? 0 : value;
				if(val + 0.5*steps <= nbBaguettesAchetees - core.getService().getNbBaguettesUtilisees()){
					setValue(val + 0.5*steps);
				}
			}
		});

		spinnerReservees.focusedProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(!spinnerReservees.isFocused()){
					Float nbBaguettesReserveesV = ((Number)spinnerReservees.getValue()).floatValue();
					nbBaguettesReservees = (nbBaguettesReserveesV == null) ? 0 : nbBaguettesReserveesV;
				}
			}
		});

		Button appliquer = new Button("Appliquer");
		appliquer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				core.getService().setNbBaguettesAchetees(nbBaguettesAchetees);
				core.getService().setNbBaguettesReservees(nbBaguettesReservees);
				for(CheckBox check : checkBoxesPlats){
					check.setSelected(!check.isSelected());
					check.setSelected(!check.isSelected());
				}
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
		theatre.initModality(Modality.APPLICATION_MODAL);
		theatre.setMinWidth(App.LARGEUR_MIN_FENETRE + 16);
		theatre.setMinHeight(App.HAUTEUR_MIN_FENETRE + 39);
		theatre.setTitle("Écran de sélection des éléments disponibles");
		theatre.setScene(scene);
		theatre.show();
	}

	public static VBox groupePlats(Region parent){
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
						if(plat.getUtilisePain()){
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

	public static VBox groupeIngredients(Region parent){
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

	public static VBox groupeSauces(Region parent){
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

	public static VBox groupeBoissons(Region parent){
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

	public static VBox groupeSupplementsBoisson(Region parent){
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

	public static VBox groupeDesserts(Region parent){
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
