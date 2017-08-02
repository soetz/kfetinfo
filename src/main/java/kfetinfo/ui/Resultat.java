package kfetinfo.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import kfetinfo.core.BaseDonnees;
import kfetinfo.core.Commande;
import kfetinfo.core.Core;

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

	public static Region resultat(Region root, Core core){
		BorderPane resultat = new BorderPane();

		resultat.minWidthProperty().bind(root.widthProperty());
		resultat.maxWidthProperty().bind(resultat.minWidthProperty());

		Region gauche = gauche(resultat, core);
		Region milieu = milieu(core);
		Region droite = new Region();

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

		Label numero = new Label("XX");
		numero.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
		numero.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		numero.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		numero.getStyleClass().add(App.NUMERO_COMMANDE);

		Label plat = new Label("Nom du plat".toUpperCase());
		plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
		plat.setMinHeight(Control.USE_PREF_SIZE);
		plat.setMaxHeight(Control.USE_PREF_SIZE);
		plat.getStyleClass().add(App.PLAT_COMMANDE);

		numeroEtPlat.getChildren().addAll(numero, plat);

		Label ingredients = new Label("Ingrédient #1 - Ingrédient #2 - Ingrédient #3");
		ingredients.getStyleClass().add(CONTENU_COMMANDE_PREVIEW);
		ingredients.maxWidthProperty().bind(commande.widthProperty().subtract(MARGIN_PREVIEW));
		ingredients.setTranslateX(MARGIN_PREVIEW);

		Label sauces = new Label("Sauce #1 - Sauce #2");
		sauces.getStyleClass().add(CONTENU_COMMANDE_PREVIEW);
		sauces.maxWidthProperty().bind(commande.widthProperty().subtract(MARGIN_PREVIEW));
		sauces.setTranslateX(MARGIN_PREVIEW);

		Label boisson = new Label("Boisson + Supplément Boisson");
		boisson.getStyleClass().add(CONTENU_COMMANDE_PREVIEW);
		boisson.maxWidthProperty().bind(commande.widthProperty().subtract(MARGIN_PREVIEW));
		boisson.setTranslateX(MARGIN_PREVIEW);

		Label dessert = new Label("Dessert");
		dessert.getStyleClass().add(CONTENU_COMMANDE_PREVIEW);
		dessert.maxWidthProperty().bind(commande.widthProperty().subtract(MARGIN_PREVIEW));
		dessert.setTranslateX(MARGIN_PREVIEW);

		commande.getChildren().add(numeroEtPlat);
		commande.getChildren().add(ingredients);
		commande.getChildren().add(sauces);
		commande.getChildren().add(boisson);
		commande.getChildren().add(dessert);

		AnchorPane fixationPrix = new AnchorPane();

		Label prix = new Label("XX,XX€");
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
		Label nbUnCent = new Label("xX");
		Label nbDeuxCent = new Label("xX");
		Label nbCinqCent = new Label("xX");
		Label nbDixCent = new Label("xX");
		Label nbVingtCent = new Label("xX");
		Label nbCinquanteCent = new Label("xX");
		Label nbUnEuro = new Label("xX");
		Label nbDeuxEuros = new Label("xX");
		Label nbCinqEuros = new Label("xX");
		Label nbDixEuros = new Label("xX");
		Label nbVingtEuros = new Label("xX");

		nbUnCent.getStyleClass().add(NOMBRE_PIECES);
		nbDeuxCent.getStyleClass().add(NOMBRE_PIECES);
		nbCinqCent.getStyleClass().add(NOMBRE_PIECES);
		nbDixCent.getStyleClass().add(NOMBRE_PIECES);
		nbVingtCent.getStyleClass().add(NOMBRE_PIECES);
		nbCinquanteCent.getStyleClass().add(NOMBRE_PIECES);
		nbUnEuro.getStyleClass().add(NOMBRE_PIECES);
		nbDeuxEuros.getStyleClass().add(NOMBRE_PIECES);
		nbCinqEuros.getStyleClass().add(NOMBRE_PIECES);
		nbDixEuros.getStyleClass().add(NOMBRE_PIECES);
		nbVingtEuros.getStyleClass().add(NOMBRE_PIECES);

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
		pieces.add(nbUnCent, 0, 7, 3, 1);
		pieces.add(nbDeuxCent, 2, 7, 3, 1);
		pieces.add(nbCinqCent, 4, 7, 3, 1);
		pieces.add(nbDixCent, 0, 5, 3, 1);
		pieces.add(nbVingtCent, 2, 5, 3, 1);
		pieces.add(nbCinquanteCent, 4, 5, 3, 1);
		pieces.add(nbUnEuro, 0, 3, 4, 1);
		pieces.add(nbDeuxEuros, 3, 3, 4, 1);
		pieces.add(nbCinqEuros, 0, 1, 3, 1);
		pieces.add(nbDixEuros, 2, 1, 3, 1);
		pieces.add(nbVingtEuros, 4, 1, 3, 1);

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
		nbUnCent.setMaxWidth(Double.MAX_VALUE);
		nbDeuxCent.setMaxWidth(Double.MAX_VALUE);
		nbCinqCent.setMaxWidth(Double.MAX_VALUE);
		nbDixCent.setMaxWidth(Double.MAX_VALUE);
		nbVingtCent.setMaxWidth(Double.MAX_VALUE);
		nbCinquanteCent.setMaxWidth(Double.MAX_VALUE);
		nbUnEuro.setMaxWidth(Double.MAX_VALUE);
		nbDeuxEuros.setMaxWidth(Double.MAX_VALUE);
		nbCinqEuros.setMaxWidth(Double.MAX_VALUE);
		nbDixEuros.setMaxWidth(Double.MAX_VALUE);
		nbVingtEuros.setMaxWidth(Double.MAX_VALUE);

		VBox aRendreAjouter = new VBox();

		aRendreAjouter.setTranslateX(PADDING_HAUT_TABLEAU);
		aRendreAjouter.setSpacing(ESPACE_A_RENDRE);
		aRendreAjouter.minWidthProperty().bind(separation.widthProperty().subtract(pieces.widthProperty()));
		aRendreAjouter.maxWidthProperty().bind(aRendreAjouter.minWidthProperty());

		Button boutonReset = new Button("Reset");
		boutonReset.minWidthProperty().bind(aRendreAjouter.widthProperty().subtract(4));
		boutonReset.maxWidthProperty().bind(boutonReset.minWidthProperty());

		Label aRendre = new Label("À rendre :");
		aRendre.getStyleClass().add(A_RENDRE);
		aRendre.setMaxWidth(Double.MAX_VALUE);

		Label nbARendre = new Label("XX,XX€");
		nbARendre.getStyleClass().add(A_RENDRE);
		nbARendre.setMaxWidth(Double.MAX_VALUE);	

		Button ajouterBouton = new Button("Ajouter");
		ajouterBouton.minWidthProperty().bind(boutonReset.minWidthProperty());
		ajouterBouton.maxWidthProperty().bind(ajouterBouton.minWidthProperty());
		ajouterBouton.setMinHeight(App.TAILLE_PANNEAU_RESULTAT - 55 - 3*ESPACE_A_RENDRE - ESPACE_BAS_BOUTON_AJOUTER);
		ajouterBouton.setMaxHeight(App.TAILLE_PANNEAU_RESULTAT - 55 - 3*ESPACE_A_RENDRE - ESPACE_BAS_BOUTON_AJOUTER);	

		ajouterBouton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Commande commande = new Commande(BaseDonnees.getRienPlat(), BaseDonnees.getRienDessert(), BaseDonnees.getRienBoisson(), BaseDonnees.getRienSupplementBoisson());
				commande.setPlat(BaseDonnees.getPlatNom("sandwich"));
				commande.addIngredient(BaseDonnees.getIngredientNom("chèvre"));
				commande.addIngredient(BaseDonnees.getIngredientNom("jambon"));
				commande.addSauce(BaseDonnees.getSauceNom("tartare"));
				commande.setBoisson(BaseDonnees.getBoissonNom("7up"));
				commande.setDessert(BaseDonnees.getDessertNom("m&m's"));

				core.getService().ajouterCommande(commande);
			}
		});

		aRendreAjouter.getChildren().addAll(boutonReset, aRendre, nbARendre, ajouterBouton);

		separation.setLeft(pieces);
		separation.setRight(aRendreAjouter);

		return(separation);
	}
}
