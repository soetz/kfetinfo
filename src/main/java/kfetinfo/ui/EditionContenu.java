package kfetinfo.ui;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.UnaryOperator;

import javax.swing.text.NumberFormatter;

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
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
import kfetinfo.core.BaseDonnees;
import kfetinfo.core.Boisson;
import kfetinfo.core.ContenuCommande;
import kfetinfo.core.Core;
import kfetinfo.core.CreateurBase;
import kfetinfo.core.Dessert;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Parametres;
import kfetinfo.core.Plat;
import kfetinfo.core.Sauce;
import kfetinfo.core.SupplementBoisson;

public class EditionContenu {
	static VBox infosPlats = new VBox();
	static VBox infosIngredients = new VBox();
	static VBox infosSauces = new VBox();
	static VBox infosBoissons = new VBox();
	static VBox infosSupplementsBoisson = new VBox();
	static VBox infosDesserts = new VBox();
	static ListView<ContenuCommande> listeViewPlats;
	static ListView<ContenuCommande> listeViewIngredients;
	static ListView<ContenuCommande> listeViewSauces;
	static ListView<ContenuCommande> listeViewBoissons;
	static ListView<ContenuCommande> listeViewSupplementsBoisson;
	static ListView<ContenuCommande> listeViewDesserts;
	static NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.FRENCH);
	static boolean modifie;

	public static void ecran(Core core){
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
		tabDivers.setContent(tabDivers(core));

		tabPane.getTabs().setAll(tabPlats, tabIngredients, tabSauces, tabBoissons, tabSupplementsBoisson, tabDesserts, tabDivers);

		Scene scene = new Scene(tabPane, App.LARGEUR_MIN_FENETRE, App.HAUTEUR_MIN_FENETRE);
		theatre.setAlwaysOnTop(true);
		theatre.setResizable(false);
		theatre.initModality(Modality.APPLICATION_MODAL);
		theatre.setMinWidth(App.LARGEUR_MIN_FENETRE + 16);
		theatre.setMinHeight(App.HAUTEUR_MIN_FENETRE + 39);
		theatre.setTitle("Écran de modification du menu");
		theatre.setScene(scene);
		theatre.show();

		theatre.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we){
				if(modifie){
					App.mettreSelectionAJour(core);
				}
			}
		});
	}

	public static Region tabPlats(Stage theatre){
		HBox tab = new HBox();
		Button supprimer = new Button("Supprimer");
		supprimer.setDisable(true);

		Plat nouveau = new Plat("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0, 0, 0, false, 0);
		List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
		liste.add(nouveau);
		for(Plat plat : BaseDonnees.getPlats()){
			if(!plat.getId().equals("ff56da46-bddd-4e4f-a871-6fa03b0e814b")){
				liste.add(plat);
			}
		}

		listeViewPlats = generateurListView(liste);

		listeViewPlats.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(listeViewPlats.getSelectionModel().getSelectedItem() != null){
					infosPlats.getChildren().clear();
					infosPlats.getChildren().addAll(generateurInfosPlat((Plat)listeViewPlats.getSelectionModel().getSelectedItem()));
					((TextField)((HBox)infosPlats.getChildren().get(1)).getChildren().get(1)).requestFocus();
					if(listeViewPlats.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						supprimer.setDisable(true);
					} else {
						supprimer.setDisable(false);
					}
				}
			}
		});

		infosPlats.getChildren().clear();
		infosPlats.getChildren().addAll(generateurInfosPlat((Plat)listeViewPlats.getSelectionModel().getSelectedItem()));

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
					float cout = (!(((TextField)(((HBox)infosPlats.getChildren().get(2)).getChildren().get(1))).getText()).equals("")) ? numberFormatter.parse(((TextField)(((HBox)infosPlats.getChildren().get(2)).getChildren().get(1))).getText()).floatValue() : 0;
					float prix = (!(((TextField)(((HBox)infosPlats.getChildren().get(2)).getChildren().get(3))).getText()).equals("")) ? numberFormatter.parse(((TextField)(((HBox)infosPlats.getChildren().get(2)).getChildren().get(3))).getText()).floatValue() : 0;
					if(listeViewPlats.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						CreateurBase.creerPlat(
							((TextField)((HBox)infosPlats.getChildren().get(1)).getChildren().get(1)).getText(),
							cout,
							true,
							((Integer)((Spinner)(((HBox)infosPlats.getChildren().get(3)).getChildren().get(1))).getValue()).intValue(),
							prix,
							((Integer)((Spinner)(((HBox)(infosPlats.getChildren().get(4))).getChildren().get(1))).getValue()).intValue(),
							((Integer)((Spinner)(((HBox)(infosPlats.getChildren().get(5))).getChildren().get(1))).getValue()).intValue(),
							((CheckBox)((HBox)infosPlats.getChildren().get(6)).getChildren().get(0)).isSelected());
					} else {
						CreateurBase.mettrePlatAJour(new Plat(
							listeViewPlats.getSelectionModel().getSelectedItem().getId(),
							((TextField)((HBox)infosPlats.getChildren().get(1)).getChildren().get(1)).getText(),
							cout,
							listeViewPlats.getSelectionModel().getSelectedItem().getDisponible(),
							listeViewPlats.getSelectionModel().getSelectedItem().getNbUtilisations(),
							((Integer)((Spinner)(((HBox)infosPlats.getChildren().get(3)).getChildren().get(1))).getValue()).intValue(),
							((Integer)((Spinner)(((HBox)(infosPlats.getChildren().get(4))).getChildren().get(1))).getValue()).intValue(),
							((Integer)((Spinner)(((HBox)(infosPlats.getChildren().get(5))).getChildren().get(1))).getValue()).intValue(),
							((CheckBox)((HBox)infosPlats.getChildren().get(6)).getChildren().get(0)).isSelected(),
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
		AnchorPane.setTopAnchor(infosPlats, 0.0);
		AnchorPane.setLeftAnchor(infosPlats, 0.0);
		AnchorPane.setRightAnchor(boutons, 0.0);
		AnchorPane.setBottomAnchor(boutons, 0.0);

		disposition.getChildren().addAll(infosPlats, boutons);

		tab.getChildren().addAll(listeViewPlats, disposition);

		return(tab);
	}

	public static Region tabIngredients(Stage theatre){
		HBox tab = new HBox();
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
					infosIngredients.getChildren().clear();
					infosIngredients.getChildren().addAll(generateurInfos((Ingredient)listeViewIngredients.getSelectionModel().getSelectedItem()));
					((TextField)((HBox)infosIngredients.getChildren().get(1)).getChildren().get(1)).requestFocus();
					if(listeViewIngredients.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						supprimer.setDisable(true);
					} else {
						supprimer.setDisable(false);
					}
				}
			}
		});

		infosIngredients.getChildren().clear();
		infosIngredients.getChildren().addAll(generateurInfos((Ingredient)listeViewIngredients.getSelectionModel().getSelectedItem()));

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
					float cout = (!(((TextField)(((HBox)infosIngredients.getChildren().get(2)).getChildren().get(1))).getText()).equals("")) ? numberFormatter.parse(((TextField)(((HBox)infosIngredients.getChildren().get(2)).getChildren().get(1))).getText()).floatValue() : 0;
					if(listeViewIngredients.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						CreateurBase.creerIngredient(
								((TextField)((HBox)infosIngredients.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								true,
								((Integer)((Spinner)(((HBox)infosIngredients.getChildren().get(3)).getChildren().get(1))).getValue()).intValue());
					} else {
						CreateurBase.mettreIngredientAJour(new Ingredient(
								listeViewIngredients.getSelectionModel().getSelectedItem().getId(),
								((TextField)((HBox)infosIngredients.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								listeViewIngredients.getSelectionModel().getSelectedItem().getDisponible(),
								listeViewIngredients.getSelectionModel().getSelectedItem().getNbUtilisations(),
								((Integer)((Spinner)(((HBox)infosIngredients.getChildren().get(3)).getChildren().get(1))).getValue()).intValue()));
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
		AnchorPane.setTopAnchor(infosIngredients, 0.0);
		AnchorPane.setLeftAnchor(infosIngredients, 0.0);
		AnchorPane.setRightAnchor(boutons, 0.0);
		AnchorPane.setBottomAnchor(boutons, 0.0);

		disposition.getChildren().addAll(infosIngredients, boutons);

		tab.getChildren().addAll(listeViewIngredients, disposition);

		return(tab);
	}

	public static Region tabSauces(Stage theatre){
		HBox tab = new HBox();
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
					infosSauces.getChildren().clear();
					infosSauces.getChildren().addAll(generateurInfos((Sauce)listeViewSauces.getSelectionModel().getSelectedItem()));
					((TextField)((HBox)infosSauces.getChildren().get(1)).getChildren().get(1)).requestFocus();
					if(listeViewSauces.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						supprimer.setDisable(true);
					} else {
						supprimer.setDisable(false);
					}
				}
			}
		});

		infosSauces.getChildren().clear();
		infosSauces.getChildren().addAll(generateurInfos((Sauce)listeViewSauces.getSelectionModel().getSelectedItem()));

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
					float cout = (!(((TextField)(((HBox)infosSauces.getChildren().get(2)).getChildren().get(1))).getText()).equals("")) ? numberFormatter.parse(((TextField)(((HBox)infosSauces.getChildren().get(2)).getChildren().get(1))).getText()).floatValue() : 0;
					if(listeViewSauces.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						CreateurBase.creerSauce(
								((TextField)((HBox)infosSauces.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								true,
								((Integer)((Spinner)(((HBox)infosSauces.getChildren().get(3)).getChildren().get(1))).getValue()).intValue());
					} else {
						CreateurBase.mettreSauceAJour(new Sauce(
								listeViewSauces.getSelectionModel().getSelectedItem().getId(),
								((TextField)((HBox)infosSauces.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								listeViewSauces.getSelectionModel().getSelectedItem().getDisponible(),
								listeViewSauces.getSelectionModel().getSelectedItem().getNbUtilisations(),
								((Integer)((Spinner)(((HBox)infosSauces.getChildren().get(3)).getChildren().get(1))).getValue()).intValue()));
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
		AnchorPane.setTopAnchor(infosSauces, 0.0);
		AnchorPane.setLeftAnchor(infosSauces, 0.0);
		AnchorPane.setRightAnchor(boutons, 0.0);
		AnchorPane.setBottomAnchor(boutons, 0.0);

		disposition.getChildren().addAll(infosSauces, boutons);

		tab.getChildren().addAll(listeViewSauces, disposition);

		return(tab);
	}

	public static Region tabBoissons(Stage theatre){
		HBox tab = new HBox();
		Button supprimer = new Button("Supprimer");
		supprimer.setDisable(true);

		Boisson nouveau = new Boisson("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0);
		List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
		liste.add(nouveau);
		for(Boisson boisson : BaseDonnees.getBoissons()){
			liste.add(boisson);
		}

		listeViewBoissons = generateurListView(liste);

		listeViewBoissons.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(listeViewBoissons.getSelectionModel().getSelectedItem() != null){
					infosBoissons.getChildren().clear();
					infosBoissons.getChildren().addAll(generateurInfos((Boisson)listeViewBoissons.getSelectionModel().getSelectedItem()));
					((TextField)((HBox)infosBoissons.getChildren().get(1)).getChildren().get(1)).requestFocus();
					if(listeViewBoissons.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						supprimer.setDisable(true);
					} else {
						supprimer.setDisable(false);
					}
				}
			}
		});

		infosBoissons.getChildren().clear();
		infosBoissons.getChildren().addAll(generateurInfos((Boisson)listeViewBoissons.getSelectionModel().getSelectedItem()));

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
					float cout = (!(((TextField)(((HBox)infosBoissons.getChildren().get(2)).getChildren().get(1))).getText()).equals("")) ? numberFormatter.parse(((TextField)(((HBox)infosBoissons.getChildren().get(2)).getChildren().get(1))).getText()).floatValue() : 0;
					if(listeViewBoissons.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						CreateurBase.creerBoisson(
								((TextField)((HBox)infosBoissons.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								true,
								((Integer)((Spinner)(((HBox)infosBoissons.getChildren().get(3)).getChildren().get(1))).getValue()).intValue());
					} else {
						CreateurBase.mettreBoissonAJour(new Boisson(
								listeViewBoissons.getSelectionModel().getSelectedItem().getId(),
								((TextField)((HBox)infosBoissons.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								listeViewBoissons.getSelectionModel().getSelectedItem().getDisponible(),
								listeViewBoissons.getSelectionModel().getSelectedItem().getNbUtilisations(),
								((Integer)((Spinner)(((HBox)infosBoissons.getChildren().get(3)).getChildren().get(1))).getValue()).intValue()));
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
		AnchorPane.setTopAnchor(infosBoissons, 0.0);
		AnchorPane.setLeftAnchor(infosBoissons, 0.0);
		AnchorPane.setRightAnchor(boutons, 0.0);
		AnchorPane.setBottomAnchor(boutons, 0.0);

		disposition.getChildren().addAll(infosBoissons, boutons);

		tab.getChildren().addAll(listeViewBoissons, disposition);

		return(tab);
	}

	public static Region tabSupplementsBoisson(Stage theatre){
		HBox tab = new HBox();
		Button supprimer = new Button("Supprimer");
		supprimer.setDisable(true);

		SupplementBoisson nouveau = new SupplementBoisson("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0, 0);
		List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
		liste.add(nouveau);
		for(SupplementBoisson supplementBoisson : BaseDonnees.getSupplementsBoisson()){
			liste.add(supplementBoisson);
		}

		listeViewSupplementsBoisson = generateurListView(liste);

		listeViewSupplementsBoisson.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(listeViewSupplementsBoisson.getSelectionModel().getSelectedItem() != null){
					infosSupplementsBoisson.getChildren().clear();
					infosSupplementsBoisson.getChildren().addAll(generateurInfosSupplementBoisson((SupplementBoisson)listeViewSupplementsBoisson.getSelectionModel().getSelectedItem()));
					((TextField)((HBox)infosSupplementsBoisson.getChildren().get(1)).getChildren().get(1)).requestFocus();
					if(listeViewSupplementsBoisson.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						supprimer.setDisable(true);
					} else {
						supprimer.setDisable(false);
					}
				}
			}
		});

		infosSupplementsBoisson.getChildren().clear();
		infosSupplementsBoisson.getChildren().addAll(generateurInfosSupplementBoisson((SupplementBoisson)listeViewSupplementsBoisson.getSelectionModel().getSelectedItem()));

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
					float cout = (!(((TextField)(((HBox)infosSupplementsBoisson.getChildren().get(2)).getChildren().get(1))).getText()).equals("")) ? numberFormatter.parse(((TextField)(((HBox)infosSupplementsBoisson.getChildren().get(2)).getChildren().get(1))).getText()).floatValue() : 0;
					float prix = (!(((TextField)(((HBox)infosSupplementsBoisson.getChildren().get(2)).getChildren().get(3))).getText()).equals("")) ? numberFormatter.parse(((TextField)(((HBox)infosSupplementsBoisson.getChildren().get(2)).getChildren().get(3))).getText()).floatValue() : 0;
					if(listeViewSupplementsBoisson.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						CreateurBase.creerSupplementBoisson(
								((TextField)((HBox)infosSupplementsBoisson.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								true,
								((Integer)((Spinner)(((HBox)infosSupplementsBoisson.getChildren().get(3)).getChildren().get(1))).getValue()).intValue(),
								prix);
					} else {
						CreateurBase.mettreSupplementBoissonAJour(new SupplementBoisson(
								listeViewSupplementsBoisson.getSelectionModel().getSelectedItem().getId(),
								((TextField)((HBox)infosSupplementsBoisson.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								listeViewSupplementsBoisson.getSelectionModel().getSelectedItem().getDisponible(),
								listeViewSupplementsBoisson.getSelectionModel().getSelectedItem().getNbUtilisations(),
								((Integer)((Spinner)(((HBox)infosSupplementsBoisson.getChildren().get(3)).getChildren().get(1))).getValue()).intValue(),
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
		AnchorPane.setTopAnchor(infosSupplementsBoisson, 0.0);
		AnchorPane.setLeftAnchor(infosSupplementsBoisson, 0.0);
		AnchorPane.setRightAnchor(boutons, 0.0);
		AnchorPane.setBottomAnchor(boutons, 0.0);

		disposition.getChildren().addAll(infosSupplementsBoisson, boutons);

		tab.getChildren().addAll(listeViewSupplementsBoisson, disposition);

		return(tab);
	}

	public static Region tabDesserts(Stage theatre){
		HBox tab = new HBox();
		Button supprimer = new Button("Supprimer");
		supprimer.setDisable(true);

		Dessert nouveau = new Dessert("Sera généré au moment de l'enregistrement", "", 0, true, 0, 0, 0);
		List<ContenuCommande> liste = new ArrayList<ContenuCommande>();
		liste.add(nouveau);
		for(Dessert dessert : BaseDonnees.getDesserts()){
			liste.add(dessert);
		}

		listeViewDesserts = generateurListView(liste);

		listeViewDesserts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(listeViewDesserts.getSelectionModel().getSelectedItem() != null){
					infosDesserts.getChildren().clear();
					infosDesserts.getChildren().addAll(generateurInfosDessert((Dessert)listeViewDesserts.getSelectionModel().getSelectedItem()));
					((TextField)((HBox)infosDesserts.getChildren().get(1)).getChildren().get(1)).requestFocus();
					if(listeViewDesserts.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						supprimer.setDisable(true);
					} else {
						supprimer.setDisable(false);
					}
				}
			}
		});

		infosDesserts.getChildren().clear();
		infosDesserts.getChildren().addAll(generateurInfosDessert((Dessert)listeViewDesserts.getSelectionModel().getSelectedItem()));

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
					float cout = (!(((TextField)(((HBox)infosDesserts.getChildren().get(2)).getChildren().get(1))).getText()).equals("")) ? numberFormatter.parse(((TextField)(((HBox)infosDesserts.getChildren().get(2)).getChildren().get(1))).getText()).floatValue() : 0;
					float prix = (!(((TextField)(((HBox)infosDesserts.getChildren().get(2)).getChildren().get(3))).getText()).equals("")) ? numberFormatter.parse(((TextField)(((HBox)infosDesserts.getChildren().get(2)).getChildren().get(3))).getText()).floatValue() : 0;
					if(listeViewDesserts.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						CreateurBase.creerDessert(
								((TextField)((HBox)infosDesserts.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								true,
								((Integer)((Spinner)(((HBox)infosDesserts.getChildren().get(3)).getChildren().get(1))).getValue()).intValue(),
								prix);
					} else {
						CreateurBase.mettreDessertAJour(new Dessert(
								listeViewDesserts.getSelectionModel().getSelectedItem().getId(),
								((TextField)((HBox)infosDesserts.getChildren().get(1)).getChildren().get(1)).getText(),
								cout,
								listeViewDesserts.getSelectionModel().getSelectedItem().getDisponible(),
								listeViewDesserts.getSelectionModel().getSelectedItem().getNbUtilisations(),
								((Integer)((Spinner)(((HBox)infosDesserts.getChildren().get(3)).getChildren().get(1))).getValue()).intValue(),
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
		AnchorPane.setTopAnchor(infosDesserts, 0.0);
		AnchorPane.setLeftAnchor(infosDesserts, 0.0);
		AnchorPane.setRightAnchor(boutons, 0.0);
		AnchorPane.setBottomAnchor(boutons, 0.0);

		disposition.getChildren().addAll(infosDesserts, boutons);

		tab.getChildren().addAll(listeViewDesserts, disposition);

		return(tab);
	}

	public static Region tabDivers(Core core){
		AnchorPane pane = new AnchorPane();

		VBox divers = new VBox();

		HBox prixIngredientSupplementaireBox = new HBox();
		Label prixIngredientSupplementaireLabel = new Label("Prix d'un ingrédient supplémentaire : ");
		TextField prixIngredientSupplementaireField = new TextField();
		prixIngredientSupplementaireLabel.setLabelFor(prixIngredientSupplementaireField);
		prixIngredientSupplementaireField.setPromptText("Prix d'un ingrédient supplémentaire");
        prixIngredientSupplementaireField.setText("" + numberFormatter.format(Parametres.getPrixIngredientSupp()));

        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {

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
		TextField prixBoissonField = new TextField();
		prixBoissonLabel.setLabelFor(prixBoissonField);
		prixBoissonField.setPromptText("Prix d'une boisson");
        prixBoissonField.setText("" + numberFormatter.format(Parametres.getPrixBoisson()));

        prixBoissonField.setTextFormatter(new TextFormatter<>(filter));

		prixBoissonBox.getChildren().addAll(prixBoissonLabel, prixBoissonField);

		HBox reducMenuBox = new HBox();
		Label reducMenuLabel = new Label("Montant de la réduction accordée pour un menu : ");
		TextField reducMenuField = new TextField();
		reducMenuLabel.setLabelFor(reducMenuField);
		reducMenuField.setPromptText("Montant de la réduction accordée pour un menu");
        reducMenuField.setText("" + numberFormatter.format(Parametres.getReducMenu()));

        reducMenuField.setTextFormatter(new TextFormatter<>(filter));

		reducMenuBox.getChildren().addAll(reducMenuLabel, reducMenuField);

		HBox coutPainBox = new HBox();
		Label coutPainLabel = new Label("Coût d'une baguette de pain : ");
		TextField coutPainField = new TextField();
		coutPainLabel.setLabelFor(coutPainField);
		coutPainField.setPromptText("Coût d'une baguette de pain");
        coutPainField.setText("" + numberFormatter.format(Parametres.getCoutPain()));

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
					float prixIngredientSupplementaire = (!prixIngredientSupplementaireField.getText().equals("")) ? numberFormatter.parse(prixIngredientSupplementaireField.getText()).floatValue() : Parametres.PRIX_INGREDIENT_SUPP_DEFAUT;
					float prixBoisson = (!prixBoissonField.getText().equals("")) ? numberFormatter.parse(prixBoissonField.getText()).floatValue() : Parametres.PRIX_BOISSON_DEFAUT;
					float reducMenu = (!reducMenuField.getText().equals("")) ? numberFormatter.parse(reducMenuField.getText()).floatValue() : Parametres.REDUC_MENU_DEFAUT;
					float coutPain = (!reducMenuField.getText().equals("")) ? numberFormatter.parse(coutPainField.getText()).floatValue() : Parametres.COUT_PAIN_DEFAUT;
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

	public static ListView<ContenuCommande> generateurListView(List<ContenuCommande> liste){
		ListView<ContenuCommande> listView = new ListView();

		listView.getItems().setAll(liste);
		listView.getSelectionModel().select(liste.get(0));

		listView.setCellFactory(lv -> new ContenuCommandeCell());

		listView.setMaxWidth(150);
		listView.setMinWidth(150);
		listView.setPrefHeight(500);

		return(listView);
	}

	public static List<Node> generateurInfos(ContenuCommande contenu){
		List<Node> nodes = new ArrayList<Node>();

		HBox idBox = new HBox();
		Label idLabel = new Label("Identificateur : ");
		TextField idField = new TextField();
		idLabel.setLabelFor(idField);
		idField.setText(contenu.getId());
		idField.setDisable(true);
		Button idCopierBouton = new Button("Copier");
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
		TextField nomField = new TextField();
		nomLabel.setLabelFor(nomField);
		nomField.setPromptText("Nom");
		nomField.setText(contenu.getNom());

		nomBox.getChildren().addAll(nomLabel, nomField);

		HBox coutBox = new HBox();
		Label coutLabel = new Label("Coût : ");
		TextField coutField = new TextField();
		coutLabel.setLabelFor(coutField);
		coutField.setPromptText("Coût");
        coutField.setText("" + numberFormatter.format(contenu.getCout()));

        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {

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
		Spinner<Integer> prioriteSpinner = new Spinner<Integer>();
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

	public static List<Node> generateurInfosPlat(Plat plat){
		List<Node> nodes = generateurInfos(plat);

        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {

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
		TextField prixField = new TextField();
		prixLabel.setLabelFor(prixField);
		prixField.setPromptText("Prix");
        prixField.setText("" + numberFormatter.format(plat.getPrix()));

        prixField.setTextFormatter(new TextFormatter<>(filter));

		HBox coutPrixBox = (HBox)nodes.get(2);
		coutPrixBox.getChildren().addAll(prixLabel, prixField);

		HBox nbMaxIngredientsBox = new HBox();
		Label nbMaxIngredientsLabel = new Label("Nombre maximal d'ingrédients : ");
		Spinner<Integer> nbMaxIngredientsSpinner = new Spinner<Integer>();
		nbMaxIngredientsLabel.setLabelFor(nbMaxIngredientsSpinner);
		SpinnerValueFactory<Integer> usineAValeursIngredients = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, plat.getNbMaxIngredients());
		nbMaxIngredientsSpinner.setValueFactory(usineAValeursIngredients);

		nbMaxIngredientsBox.getChildren().addAll(nbMaxIngredientsLabel, nbMaxIngredientsSpinner);

		HBox nbMaxSaucesBox = new HBox();
		Label nbMaxSaucesLabel = new Label("Nombre maximal de sauces : ");
		Spinner<Integer> nbMaxSaucesSpinner = new Spinner<Integer>();
		nbMaxSaucesLabel.setLabelFor(nbMaxSaucesSpinner);
		SpinnerValueFactory<Integer> usineAValeursSauces = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, plat.getNbMaxSauces());
		nbMaxSaucesSpinner.setValueFactory(usineAValeursSauces);

		nbMaxSaucesBox.getChildren().addAll(nbMaxSaucesLabel, nbMaxSaucesSpinner);

		HBox utilisePainBox = new HBox();
		CheckBox utilisePainCheckBox = new CheckBox();
		if(plat.getUtilisePain()){
			utilisePainCheckBox.setSelected(true);
		}
		Label utilisePainLabel = new Label("Utilise du pain");
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

	public static List<Node> generateurInfosSupplementBoisson(SupplementBoisson supplementBoisson){
		List<Node> nodes = generateurInfos(supplementBoisson);

        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {

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
		TextField prixField = new TextField();
		prixLabel.setLabelFor(prixField);
		prixField.setPromptText("Prix");
        prixField.setText("" + numberFormatter.format(supplementBoisson.getPrix()));

        prixField.setTextFormatter(new TextFormatter<>(filter));

		HBox coutPrixBox = (HBox)nodes.get(2);
		coutPrixBox.getChildren().addAll(prixLabel, prixField);
		
		return(nodes);
	}

	public static List<Node> generateurInfosDessert(Dessert dessert){
		List<Node> nodes = generateurInfos(dessert);

        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {

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
		TextField prixField = new TextField();
		prixLabel.setLabelFor(prixField);
		prixField.setPromptText("Prix");
        prixField.setText("" + numberFormatter.format(dessert.getPrix()));

        prixField.setTextFormatter(new TextFormatter<>(filter));

		HBox coutPrixBox = (HBox)nodes.get(2);
		coutPrixBox.getChildren().addAll(prixLabel, prixField);
		
		return(nodes);
	}
}
