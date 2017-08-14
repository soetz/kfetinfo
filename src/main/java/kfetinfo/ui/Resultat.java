package kfetinfo.ui;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import kfetinfo.core.BaseDonnees;
import kfetinfo.core.Commande;
import kfetinfo.core.Core;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Plat;
import kfetinfo.core.Sauce;

public class Resultat {
	public static final String COMMANDE_PREVIEW = "commande-preview";
	public static final String FOND_COMMANDE_PREVIEW = "fond-commande-preview";
	public static final String CONTENU_COMMANDE_PREVIEW = "contenu-commande-preview";
	public static final String PRIX_COMMANDE_PREVIEW = "prix-commande-preview";
	public static final String NOMBRE_PIECES = "nombre-pieces";
	public static final String A_RENDRE = "a-rendre";

	public static final Double PADDING_PREVIEW = 4.0;
	public static final Double MARGIN_PREVIEW = 5.0;
	public static final Double ESPACE_CONTENUS_COMMANDE = 8.0;
	public static final Double LARGEUR_CELLULE_TABLEAU = 20.0;
	public static final Double PADDING_HAUT_TABLEAU = 3.0;
	public static final Double ESPACE_A_RENDRE = 5.0;
	public static final Double ESPACE_BAS_BOUTON_AJOUTER = 10.0;

	private static int nbUnCent = 0;
	private static int nbDeuxCent = 0;
	private static int nbCinqCent = 0;
	private static int nbDixCent = 0;
	private static int nbVingtCent = 0;
	private static int nbCinquanteCent = 0;
	private static int nbUnEuro = 0;
	private static int nbDeuxEuros = 0;
	private static int nbCinqEuros = 0;
	private static int nbDixEuros = 0;
	private static int nbVingtEuros = 0;

	private static Label lbNbUnCent = new Label("x0");
	private static Label lbNbDeuxCent = new Label("x0");
	private static Label lbNbCinqCent = new Label("x0");
	private static Label lbNbDixCent = new Label("x0");
	private static Label lbNbVingtCent = new Label("x0");
	private static Label lbNbCinquanteCent = new Label("x0");
	private static Label lbNbUnEuro = new Label("x0");
	private static Label lbNbDeuxEuros = new Label("x0");
	private static Label lbNbCinqEuros = new Label("x0");
	private static Label lbNbDixEuros = new Label("x0");
	private static Label lbNbVingtEuros = new Label("x0");

	private static Label nbARendre;
	private static Button ajouterBouton;

	private static Label membreOrdi;
	private static Label membreCommis1;
	private static Label membreCommis2;
	private static Label membreConfection1;
	private static Label membreConfection2;
	private static Label membreConfection3;

	public static Region resultat(Region root, Core core){
		BorderPane resultat = new BorderPane();

		resultat.minWidthProperty().bind(root.widthProperty());
		resultat.maxWidthProperty().bind(resultat.minWidthProperty());

		Region gauche = gauche(resultat, core);
		Region milieu = milieu(core);
		Region droite = droite(resultat, core);

		gauche.setMaxHeight(Double.MAX_VALUE);
		milieu.setMaxHeight(Double.MAX_VALUE);
		droite.setMaxHeight(Double.MAX_VALUE);

		gauche.setMinHeight(App.TAILLE_PANNEAU_RESULTAT);
		milieu.setMinHeight(App.TAILLE_PANNEAU_RESULTAT);
		droite.setMinHeight(App.TAILLE_PANNEAU_RESULTAT);

		gauche.minWidthProperty().bind(resultat.widthProperty().divide(3));
		gauche.maxWidthProperty().bind(gauche.minWidthProperty());
		milieu.minWidthProperty().bind(resultat.widthProperty().divide(3));
		milieu.maxWidthProperty().bind(gauche.minWidthProperty());
		droite.minWidthProperty().bind(resultat.widthProperty().divide(3));
		droite.maxWidthProperty().bind(gauche.minWidthProperty());

		resultat.setLeft(gauche);
		resultat.setCenter(milieu);
		resultat.setRight(droite);

		return(resultat);
	}

	public static Region gauche(Region parent, Core core){
		StackPane commandePreview = new StackPane();

		commandePreview.setPadding(new Insets(PADDING_PREVIEW));
		commandePreview.maxWidthProperty().bind(parent.widthProperty().divide(3).subtract(2*PADDING_PREVIEW));
		commandePreview.getStyleClass().add(COMMANDE_PREVIEW);

		Region fond = new Region();

		fond.getStyleClass().add(FOND_COMMANDE_PREVIEW);

		VBox commande = new VBox();
		commande.maxWidthProperty().bind(commandePreview.widthProperty());
		commande.setSpacing(ESPACE_CONTENUS_COMMANDE);

		HBox numeroEtPlat = new HBox();
		numeroEtPlat.setSpacing(App.ESPACE_NUMERO_PLAT);

		Label numero = new Label("" + (core.getService().getNbCommandes() + 1));
		core.getService().nouvelleCommandePropriete().addListener(new ChangeListener(){
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				numero.setText("" + (core.getService().getNbCommandes() + 1));
			}
		});
		numero.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
		numero.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		numero.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		numero.getStyleClass().add(App.NUMERO_COMMANDE);

		Label plat = new Label("Rien".toUpperCase());
		Selection.platSelectionnePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				plat.setText(Selection.getPlatSelectionne().getNom().toUpperCase());
			}
		});

		plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
		plat.setMinHeight(Control.USE_PREF_SIZE);
		plat.setMaxHeight(Control.USE_PREF_SIZE);
		plat.getStyleClass().add(App.PLAT_COMMANDE);

		numeroEtPlat.getChildren().addAll(numero, plat);

		Label ingredients = new Label("Rien");
		Selection.ingredientChangePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				String nomsIngredients = "";
				if(Selection.getIngredientsSelectionnes().size()!=0){
					nomsIngredients += Selection.getIngredientsSelectionnes().get(0).getNom();
					for(Ingredient ingredient : Selection.getIngredientsSelectionnes()){
						if(!(ingredient.equals(Selection.getIngredientsSelectionnes().get(0)))){
							nomsIngredients += " - " + ingredient.getNom();
						}
					}
				} else {
					nomsIngredients = "Rien";
				}
				ingredients.setText(nomsIngredients);
			}
		});
		ingredients.getStyleClass().add(CONTENU_COMMANDE_PREVIEW);
		ingredients.maxWidthProperty().bind(commande.widthProperty().subtract(MARGIN_PREVIEW));
		ingredients.setTranslateX(MARGIN_PREVIEW);

		Label sauces = new Label("Rien");
		Selection.sauceChangeePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				String nomsSauces = "";
				if(Selection.getSaucesSelectionnees().size()!=0){
					nomsSauces += Selection.getSaucesSelectionnees().get(0).getNom();
					for(Sauce sauce : Selection.getSaucesSelectionnees()){
						if(!(sauce.equals(Selection.getSaucesSelectionnees().get(0)))){
							nomsSauces += " - " + sauce.getNom();
						}
					}
				} else {
					nomsSauces = "Rien";
				}
				sauces.setText(nomsSauces);
			}
		});
		sauces.getStyleClass().add(CONTENU_COMMANDE_PREVIEW);
		sauces.maxWidthProperty().bind(commande.widthProperty().subtract(MARGIN_PREVIEW));
		sauces.setTranslateX(MARGIN_PREVIEW);

		Label boisson = new Label("Rien");
		Selection.boissonSelectionneePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(Selection.getSupplementBoissonSelectionne().getId().equals("fa03180b-95ad-4a5b-84f2-cbdc2beae920")){
					boisson.setText(Selection.getBoissonSelectionnee().getNom());
				} else {
					boisson.setText(Selection.getBoissonSelectionnee().getNom() + " + " + Selection.getSupplementBoissonSelectionne().getNom());
				}
			}
		});

		Selection.supplementBoissonSelectionnePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(Selection.getSupplementBoissonSelectionne().getId().equals("fa03180b-95ad-4a5b-84f2-cbdc2beae920")){
					boisson.setText(Selection.getBoissonSelectionnee().getNom());
				} else {
					boisson.setText(Selection.getBoissonSelectionnee().getNom() + " + " + Selection.getSupplementBoissonSelectionne().getNom());
				}
			}
		});

		boisson.getStyleClass().add(CONTENU_COMMANDE_PREVIEW);
		boisson.maxWidthProperty().bind(commande.widthProperty().subtract(MARGIN_PREVIEW));
		boisson.setTranslateX(MARGIN_PREVIEW);

		Label dessert = new Label("Rien");
		Selection.dessertSelectionnePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				dessert.setText(Selection.getDessertSelectionne().getNom());
			}
		});
		dessert.getStyleClass().add(CONTENU_COMMANDE_PREVIEW);
		dessert.maxWidthProperty().bind(commande.widthProperty().subtract(MARGIN_PREVIEW));
		dessert.setTranslateX(MARGIN_PREVIEW);

		commande.getChildren().add(numeroEtPlat);
		commande.getChildren().add(ingredients);
		commande.getChildren().add(sauces);
		commande.getChildren().add(boisson);
		commande.getChildren().add(dessert);

		AnchorPane fixationPrix = new AnchorPane();

		Label prix = new Label("- €");
		Selection.commandeChangeePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				String affPrix = "- €";
				if(Selection.getPrixCommande()!=0f){
					NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.FRENCH);
					affPrix = numberFormatter.format(Selection.getPrixCommande()) + "€";
				}
				prix.setText(affPrix);

				mettreCompteurAJour();
			}
		});
		prix.getStyleClass().add(PRIX_COMMANDE_PREVIEW);

		AnchorPane.setBottomAnchor(prix, MARGIN_PREVIEW);
		AnchorPane.setRightAnchor(prix, MARGIN_PREVIEW);

		fixationPrix.getChildren().add(prix);

		commandePreview.getChildren().add(fond);
		commandePreview.getChildren().add(commande);
		commandePreview.getChildren().add(fixationPrix);

		return(commandePreview);
	}

	public static Region milieu(Core core){
		BorderPane separation = new BorderPane();

		GridPane pieces = new GridPane();

		pieces.setTranslateY(PADDING_HAUT_TABLEAU);
		pieces.setHgap(LARGEUR_CELLULE_TABLEAU);

		Button boutonUnCent = new Button("1¢");
		Button boutonDeuxCent = new Button("2¢");
		Button boutonCinqCent = new Button("5¢");
		Button boutonDixCent = new Button("10¢");
		Button boutonVingtCent = new Button("20¢");
		Button boutonCinquanteCent = new Button("50¢");
		Button boutonUnEuro = new Button("1€");
		Button boutonDeuxEuros = new Button("2€");
		Button boutonCinqEuros = new Button("5€");
		Button boutonDixEuros = new Button("10€");
		Button boutonVingtEuros = new Button("20€");

		boutonUnCent.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				nbUnCent += 1;
				lbNbUnCent.setText("x" + nbUnCent);
				mettreCompteurAJour();
			}
		});

		boutonDeuxCent.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				nbDeuxCent += 1;
				lbNbDeuxCent.setText("x" + nbDeuxCent);
				mettreCompteurAJour();
			}
		});

		boutonCinqCent.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				nbCinqCent += 1;
				lbNbCinqCent.setText("x" + nbCinqCent);
				mettreCompteurAJour();
			}
		});

		boutonDixCent.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				nbDixCent += 1;
				lbNbDixCent.setText("x" + nbDixCent);
				mettreCompteurAJour();
			}
		});

		boutonVingtCent.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				nbVingtCent += 1;
				lbNbVingtCent.setText("x" + nbVingtCent);
				mettreCompteurAJour();
			}
		});

		boutonCinquanteCent.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				nbCinquanteCent += 1;
				lbNbCinquanteCent.setText("x" + nbCinquanteCent);
				mettreCompteurAJour();
			}
		});

		boutonUnEuro.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				nbUnEuro += 1;
				lbNbUnEuro.setText("x" + nbUnEuro);
				mettreCompteurAJour();
			}
		});

		boutonDeuxEuros.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				nbDeuxEuros += 1;
				lbNbDeuxEuros.setText("x" + nbDeuxEuros);
				mettreCompteurAJour();
			}
		});

		boutonCinqEuros.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				nbCinqEuros += 1;
				lbNbCinqEuros.setText("x" + nbCinqEuros);
				mettreCompteurAJour();
			}
		});

		boutonDixEuros.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				nbDixEuros += 1;
				lbNbDixEuros.setText("x" + nbDixEuros);
				mettreCompteurAJour();
			}
		});

		boutonVingtEuros.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				nbVingtEuros += 1;
				lbNbVingtEuros.setText("x" + nbVingtEuros);
				mettreCompteurAJour();
			}
		});

		lbNbUnCent.getStyleClass().add(NOMBRE_PIECES);
		lbNbDeuxCent.getStyleClass().add(NOMBRE_PIECES);
		lbNbCinqCent.getStyleClass().add(NOMBRE_PIECES);
		lbNbDixCent.getStyleClass().add(NOMBRE_PIECES);
		lbNbVingtCent.getStyleClass().add(NOMBRE_PIECES);
		lbNbCinquanteCent.getStyleClass().add(NOMBRE_PIECES);
		lbNbUnEuro.getStyleClass().add(NOMBRE_PIECES);
		lbNbDeuxEuros.getStyleClass().add(NOMBRE_PIECES);
		lbNbCinqEuros.getStyleClass().add(NOMBRE_PIECES);
		lbNbDixEuros.getStyleClass().add(NOMBRE_PIECES);
		lbNbVingtEuros.getStyleClass().add(NOMBRE_PIECES);

		pieces.add(boutonUnCent, 0, 6, 3, 1);
		pieces.add(boutonDeuxCent, 2, 6, 3, 1);
		pieces.add(boutonCinqCent, 4, 6, 3, 1);
		pieces.add(boutonDixCent, 0, 4, 3, 1);
		pieces.add(boutonVingtCent, 2, 4, 3, 1);
		pieces.add(boutonCinquanteCent, 4, 4, 3, 1);
		pieces.add(boutonUnEuro, 0, 2, 4, 1);
		pieces.add(boutonDeuxEuros, 3, 2, 4, 1);
		pieces.add(boutonCinqEuros, 0, 0, 3, 1);
		pieces.add(boutonDixEuros, 2, 0, 3, 1);
		pieces.add(boutonVingtEuros, 4, 0, 3, 1);
		pieces.add(lbNbUnCent, 0, 7, 3, 1);
		pieces.add(lbNbDeuxCent, 2, 7, 3, 1);
		pieces.add(lbNbCinqCent, 4, 7, 3, 1);
		pieces.add(lbNbDixCent, 0, 5, 3, 1);
		pieces.add(lbNbVingtCent, 2, 5, 3, 1);
		pieces.add(lbNbCinquanteCent, 4, 5, 3, 1);
		pieces.add(lbNbUnEuro, 0, 3, 4, 1);
		pieces.add(lbNbDeuxEuros, 3, 3, 4, 1);
		pieces.add(lbNbCinqEuros, 0, 1, 3, 1);
		pieces.add(lbNbDixEuros, 2, 1, 3, 1);
		pieces.add(lbNbVingtEuros, 4, 1, 3, 1);

		boutonUnCent.setMaxWidth(Double.MAX_VALUE);
		boutonDeuxCent.setMaxWidth(Double.MAX_VALUE);
		boutonCinqCent.setMaxWidth(Double.MAX_VALUE);
		boutonDixCent.setMaxWidth(Double.MAX_VALUE);
		boutonVingtCent.setMaxWidth(Double.MAX_VALUE);
		boutonCinquanteCent.setMaxWidth(Double.MAX_VALUE);
		boutonUnEuro.setMaxWidth(Double.MAX_VALUE);
		boutonDeuxEuros.setMaxWidth(Double.MAX_VALUE);
		boutonCinqEuros.setMaxWidth(Double.MAX_VALUE);
		boutonDixEuros.setMaxWidth(Double.MAX_VALUE);
		boutonVingtEuros.setMaxWidth(Double.MAX_VALUE);
		lbNbUnCent.setMaxWidth(Double.MAX_VALUE);
		lbNbDeuxCent.setMaxWidth(Double.MAX_VALUE);
		lbNbCinqCent.setMaxWidth(Double.MAX_VALUE);
		lbNbDixCent.setMaxWidth(Double.MAX_VALUE);
		lbNbVingtCent.setMaxWidth(Double.MAX_VALUE);
		lbNbCinquanteCent.setMaxWidth(Double.MAX_VALUE);
		lbNbUnEuro.setMaxWidth(Double.MAX_VALUE);
		lbNbDeuxEuros.setMaxWidth(Double.MAX_VALUE);
		lbNbCinqEuros.setMaxWidth(Double.MAX_VALUE);
		lbNbDixEuros.setMaxWidth(Double.MAX_VALUE);
		lbNbVingtEuros.setMaxWidth(Double.MAX_VALUE);

		VBox aRendreAjouter = new VBox();

		aRendreAjouter.setTranslateX(PADDING_HAUT_TABLEAU);
		aRendreAjouter.setSpacing(ESPACE_A_RENDRE);
		aRendreAjouter.minWidthProperty().bind(separation.widthProperty().subtract(pieces.widthProperty()));
		aRendreAjouter.maxWidthProperty().bind(aRendreAjouter.minWidthProperty());

		Button boutonReset = new Button("Reset");
		boutonReset.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				resetPieces();
			}
		});
		boutonReset.minWidthProperty().bind(aRendreAjouter.widthProperty().subtract(4));
		boutonReset.maxWidthProperty().bind(boutonReset.minWidthProperty());

		Label aRendre = new Label("À rendre :");
		aRendre.getStyleClass().add(A_RENDRE);
		aRendre.setMaxWidth(Double.MAX_VALUE);

		nbARendre = new Label("- €");
		nbARendre.getStyleClass().add(A_RENDRE);
		nbARendre.setMaxWidth(Double.MAX_VALUE);

		ajouterBouton = new Button("A_jouter");
		ajouterBouton.setMnemonicParsing(true);
		ajouterBouton.setDefaultButton(true);
		ajouterBouton.setDisable(true);
		ajouterBouton.minWidthProperty().bind(boutonReset.minWidthProperty());
		ajouterBouton.maxWidthProperty().bind(ajouterBouton.minWidthProperty());
		ajouterBouton.setMinHeight(App.TAILLE_PANNEAU_RESULTAT - 55 - 3*ESPACE_A_RENDRE - ESPACE_BAS_BOUTON_AJOUTER);
		ajouterBouton.setMaxHeight(App.TAILLE_PANNEAU_RESULTAT - 55 - 3*ESPACE_A_RENDRE - ESPACE_BAS_BOUTON_AJOUTER);	

		ajouterBouton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Commande commande = new Commande(
						new Date(),
						Selection.getPlatSelectionne(),
						Selection.getIngredientsSelectionnes(),
						Selection.getSaucesSelectionnees(),
						Selection.getDessertSelectionne(),
						Selection.getBoissonSelectionnee(),
						Selection.getSupplementBoissonSelectionne(),
						core.getService());

						core.getService().ajouterCommande(commande);
						EcranConfection.mettreEcransAJour(core);

						if(Selection.getPlatSelectionne().getUtilisePain()){
							if(core.getService().getNbBaguettesRestantes() <= 0){
								int i = 0;
								for(Plat plat : BaseDonnees.getPlats()){
									if(plat.getUtilisePain()){
										Selection.platDisponible(i, false);
									}

									i++;
								}
							}
						}

						Selection.reset();

						resetPieces();
			}
		});

		aRendreAjouter.getChildren().addAll(boutonReset, aRendre, nbARendre, ajouterBouton);

		separation.setLeft(pieces);
		separation.setRight(aRendreAjouter);

		return(separation);
	}

	public static Region droite(Region parent, Core core){
		AnchorPane layout = new AnchorPane();

		GridPane membres = new GridPane();
		membres.setHgap(5.0);
		membres.setVgap(3.0);

		Label ordi = new Label("Ordi");
		Label commis1 = new Label("Commis #1");
		Label commis2 = new Label("Commis #2");
		Label confection1 = new Label("Confection #1");
		Label confection2 = new Label("Confection #2");
		Label confection3 = new Label("Confection #3");
		membreOrdi = new Label("---");
		membreCommis1 = new Label("---");
		membreCommis2 = new Label("---");
		membreConfection1 = new Label("---");
		membreConfection2 = new Label("---");
		membreConfection3 = new Label("---");

		Button boutonOrdi = new Button("Modifier");
		boutonOrdi.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a){
				SelectionMembre.selectionOrdi(core);
			}
		});

		Button boutonCommis1 = new Button("Modifier");
		boutonCommis1.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a){
				SelectionMembre.selectionCommis1(core);
			}
		});

		Button boutonCommis2 = new Button("Modifier");
		boutonCommis2.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a){
				SelectionMembre.selectionCommis2(core);
			}
		});

		Button boutonConfection1 = new Button("Modifier");
		boutonConfection1.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a){
				SelectionMembre.selectionConfection1(core).setOnCloseRequest(new EventHandler<WindowEvent>() {
					public void handle(WindowEvent e){
						core.getService().assignation();
						EcranConfection.mettreEcransAJour(core);
					}
				});
			}
		});

		Button boutonConfection2 = new Button("Modifier");
		boutonConfection2.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a){
				SelectionMembre.selectionConfection2(core).setOnCloseRequest(new EventHandler<WindowEvent>() {
					public void handle(WindowEvent e){
						core.getService().assignation();
						EcranConfection.mettreEcransAJour(core);
					}
				});
			}
		});

		Button boutonConfection3 = new Button("Modifier");
		boutonConfection3.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a){
				SelectionMembre.selectionConfection3(core).setOnCloseRequest(new EventHandler<WindowEvent>() {
					public void handle(WindowEvent e){
						core.getService().assignation();
						EcranConfection.mettreEcransAJour(core);
					}
				});
			}
		});

		mettreOrdiAJour(core);

		mettreCommisAJour(core);

		mettreConfectionAJour(core);

		membres.add(ordi, 0, 0);
		membres.add(membreOrdi, 1, 0);
		membres.add(boutonOrdi, 2, 0);
		membres.add(commis1, 0, 1);
		membres.add(membreCommis1, 1, 1);
		membres.add(boutonCommis1, 2, 1);
		membres.add(commis2, 0, 2);
		membres.add(membreCommis2, 1, 2);
		membres.add(boutonCommis2, 2, 2);
		membres.add(confection1, 0, 3);
		membres.add(membreConfection1, 1, 3);
		membres.add(boutonConfection1, 2, 3);
		membres.add(confection2, 0, 4);
		membres.add(membreConfection2, 1, 4);
		membres.add(boutonConfection2, 2, 4);
		membres.add(confection3, 0, 5);
		membres.add(membreConfection3, 1, 5);
		membres.add(boutonConfection3, 2, 5);

		ColumnConstraints nePasGrow = new ColumnConstraints();
		nePasGrow.setHgrow(Priority.NEVER);
		ColumnConstraints grow = new ColumnConstraints();
		grow.setHgrow(Priority.ALWAYS);
		membres.getColumnConstraints().add(nePasGrow);
		membres.getColumnConstraints().add(grow);
		membres.getColumnConstraints().add(nePasGrow);

		AnchorPane.setLeftAnchor(membres, 5.0);
		AnchorPane.setRightAnchor(membres, 2.0);
		AnchorPane.setTopAnchor(membres, 3.0);

		layout.getChildren().add(membres);

		return(layout);
	}

	public static void resetPieces(){
		nbUnCent = 0;
		nbDeuxCent = 0;
		nbCinqCent = 0;
		nbDixCent = 0;
		nbVingtCent = 0;
		nbCinquanteCent = 0;
		nbUnEuro = 0;
		nbDeuxEuros = 0;
		nbCinqEuros = 0;
		nbDixEuros = 0;
		nbVingtEuros = 0;

		lbNbUnCent.setText("x0");
		lbNbDeuxCent.setText("x0");
		lbNbCinqCent.setText("x0");
		lbNbDixCent.setText("x0");
		lbNbVingtCent.setText("x0");
		lbNbCinquanteCent.setText("x0");
		lbNbUnEuro.setText("x0");
		lbNbDeuxEuros.setText("x0");
		lbNbCinqEuros.setText("x0");
		lbNbDixEuros.setText("x0");
		lbNbVingtEuros.setText("x0");

		mettreCompteurAJour();
	}

	public static void mettreCompteurAJour(){
		float totalCommande = Selection.getPrixCommande();
		float pris = 0f;
		String affARendre = "- €";

		pris += 0.01*nbUnCent;
		pris += 0.02*nbDeuxCent;
		pris += 0.05*nbCinqCent;
		pris += 0.1*nbDixCent;
		pris += 0.2*nbVingtCent;
		pris += 0.5*nbCinquanteCent;
		pris += nbUnEuro;
		pris += 2*nbDeuxEuros;
		pris += 5*nbCinqEuros;
		pris += 10*nbDixEuros;
		pris += 20*nbVingtEuros;

		if((totalCommande != 0)||(pris != 0)){
			if(pris >= totalCommande){
				NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.FRENCH);
				affARendre = numberFormatter.format(pris - totalCommande) + "€";
			}
		}

		if(totalCommande == 0){
			ajouterBouton.setDisable(true);
		} else {
			ajouterBouton.setDisable(false);
		}

		nbARendre.setText(affARendre);
	}

	public static void mettreOrdiAJour(Core core){
		if(!core.getService().getOrdi().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")){
			membreOrdi.setText(core.getService().getOrdi().getBlazeCourt().toUpperCase());
		} else {
			membreOrdi.setText("---");
		}
	}

	public static void mettreCommisAJour(Core core){
		if(core.getService().getCommis().size() > 0){
			membreCommis1.setText(core.getService().getCommis().get(0).getBlazeCourt().toUpperCase());
			if(core.getService().getCommis().size() > 1){
				membreCommis2.setText(core.getService().getCommis().get(1).getBlazeCourt().toUpperCase());
			} else {
				membreCommis2.setText("---");
			}
		} else {
			membreCommis1.setText("---");
		}
	}

	public static void mettreConfectionAJour(Core core){
		if(core.getService().getConfection().size() > 0){
			membreConfection1.setText(core.getService().getConfection().get(0).getBlazeCourt().toUpperCase());
			if(core.getService().getConfection().size() > 1){
				membreConfection2.setText(core.getService().getConfection().get(1).getBlazeCourt().toUpperCase());
				if(core.getService().getConfection().size() > 2){
					membreConfection3.setText(core.getService().getConfection().get(2).getBlazeCourt().toUpperCase());
				} else {
					membreConfection3.setText("---");
				}
			} else {
				membreConfection2.setText("---");
			}
		} else {
			membreConfection1.setText("---");
		}
	}
}
