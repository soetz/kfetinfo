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
import kfetinfo.core.Core;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Membre;
import kfetinfo.core.Plat;
import kfetinfo.core.Sauce;

import java.util.Date;

import java.util.Locale;

import java.text.NumberFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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

/**
 * <p>Resultat est une classe constituée uniquement d'attributs et de méthodes statiques relatifs à l'affichage des sections « prévisualisation de la commande », « caisse » et « équipe » du logiciel.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public final class Resultat {

	//classes de style pour l'utilisation du CSS
	public static final String FOND_COMMANDE_PREVIEW = "resultat-fond-commande-preview";
	public static final String CONTENU_COMMANDE_PREVIEW = "resultat-contenu-commande-preview";
	public static final String PRIX_COMMANDE_PREVIEW = "resultat-prix-commande-preview";
	public static final String NOMBRE_PIECES = "resultat-nombre-pieces";
	public static final String A_RENDRE = "resultat-a-rendre";

	//constantes pour l'affichage
	private static final Double PADDING_PREVIEW = 4.0;
	private static final Double MARGIN_PREVIEW = 5.0;
	private static final Double ESPACE_CONTENUS_COMMANDE = 8.0;
	private static final Double LARGEUR_CELLULE_TABLEAU = 20.0;
	private static final Double PADDING_HAUT_TABLEAU = 3.0;
	private static final Double ESPACE_A_RENDRE = 5.0;
	private static final Double ESPACE_BAS_BOUTON_AJOUTER = 10.0;

	//comptes de pièces
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

	//labels de comptes de pièces
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

	//label indiquant la quantité d'argent que l'ordi doit rendre au client
	private static Label lbARendre;

	//bouton permettant d'ajouter la commande au service
	private static Button ajouterBouton;

	//labels de membres
	private static Label membreOrdi;
	private static Label membreCommis1;
	private static Label membreCommis2;
	private static Label membreConfection1;
	private static Label membreConfection2;
	private static Label membreConfection3;

	/**
	 * Crée une {@code Region} permettant de prévisualiser la commande en train d'être formulée, et de gérer la caisse et l'équipe du service.
	 * 
	 * @param root le parent du panneau.
	 * @return le panneau de prévisualisation, de caisse et d'équipe.
	 */
	public static final Region resultat(Region root){

		BorderPane resultat = new BorderPane();

		resultat.minWidthProperty().bind(root.widthProperty());
		resultat.maxWidthProperty().bind(resultat.minWidthProperty());

		Region gauche = previsualisationCommande(resultat);
		Region milieu = caisse();
		Region droite = equipe(resultat);

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

	/**
	 * Crée une {@code Region} permettant de prévisualiser la commande en train d'être formulée. Apparaissent la liste des contenus commande de la commande, son numéro et son prix.
	 * 
	 * @param parent le node parent de celui-ci.
	 * @return le panneau de prévisualisation de la commande.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final Region previsualisationCommande(Region parent){

		StackPane commandePreview = new StackPane();

		commandePreview.setPadding(new Insets(PADDING_PREVIEW));
		commandePreview.maxWidthProperty().bind(parent.widthProperty().divide(3).subtract(2*PADDING_PREVIEW));

		Region fond = new Region();

		fond.getStyleClass().add(FOND_COMMANDE_PREVIEW);

		VBox commande = new VBox();
		commande.maxWidthProperty().bind(commandePreview.widthProperty());
		commande.setSpacing(ESPACE_CONTENUS_COMMANDE);

		HBox numeroEtPlat = new HBox();
		numeroEtPlat.setSpacing(App.ESPACE_NUMERO_PLAT);

		Label numero = new Label("" + (Core.getService().getDernierNumeroCommande() + 1));

		//le label numero montre le numéro de la prochaine commande quand une commande est ajoutée ou retirée
		Core.getService().nouvelleCommandePropriete().addListener(new ChangeListener(){
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				numero.setText("" + (Core.getService().getDernierNumeroCommande() + 1));
			}
		});
		Core.getService().nouvelleCommandeRetireePropriete().addListener(new ChangeListener(){
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				numero.setText("" + (Core.getService().getDernierNumeroCommande() + 1));
			}
		});

		numero.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
		numero.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		numero.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		numero.getStyleClass().add(App.NUMERO_COMMANDE_AJOUTEE);

		Label plat = new Label("Rien".toUpperCase());

		//le label plat montre le plat actuellement sélectionné
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

		//le label ingredients montre la liste des ingrédients, il est mis à jour chaque fois que la liste des ingrédients change
		Selection.ingredientChangePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				String nomsIngredients = "";
				if(Selection.getIngredientsSelectionnes().size() >= 1){
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

		//le label sauces montre la liste des sauces, il est mis à jour chaque fois que la liste des sauces change
		Selection.sauceChangeePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				String nomsSauces = "";
				if(Selection.getSaucesSelectionnees().size() >= 1){
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

		//le label boisson montre la boisson actuellement sélectionnée, ainsi que son supplément boisson, il est mis à jour chaque fois que l'un des deux change
		Selection.boissonSelectionneePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(Selection.getSupplementBoissonSelectionne().getId().equals(BaseDonnees.ID_RIEN_SUPPLEMENT_BOISSON)){
					boisson.setText(Selection.getBoissonSelectionnee().getNom());
				} else {
					boisson.setText(Selection.getBoissonSelectionnee().getNom() + " + " + Selection.getSupplementBoissonSelectionne().getNom());
				}
			}
		});
		Selection.supplementBoissonSelectionnePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(Selection.getSupplementBoissonSelectionne().getId().equals(BaseDonnees.ID_RIEN_SUPPLEMENT_BOISSON)){
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

		//le label plat montre le dessert actuellement sélectionné
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

		//le label prix montre le prix de la commande formulée
		Selection.commandeChangeePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				String affPrix = "- €";
				if(Selection.getPrixCommande() > 0f){
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

	/**
	 * Crée un panneau permettant de gérer la caisse. Il est composé de boutons correspondant aux pièces que donnent les clients et fournit un affichage de la quantité d'argent à rendre au client.
	 * 
	 * @return le panneau de gestion de la caisse.
	 */
	public static final Region caisse(){

		BorderPane separation = new BorderPane();

		GridPane pieces = new GridPane();

		pieces.setTranslateY(PADDING_HAUT_TABLEAU);
		pieces.setHgap(LARGEUR_CELLULE_TABLEAU);

		//les boutons du panneau
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

		//pour chaque bouton, on définit qu'un clic augmente de 1 le compte de nombre de pièces correspondant
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

		/* On ajoute les boutons de manière à former la forme du tableau - au passage ça fonctionne mais c'est pas comme ça que je pense que le GridPane fonctionne au niveau des colonnes et du span,
		 * à vrai dire j'ai aucune idée de pourquoi ça ça fonctionne alors que la manière à laquelle j'avais pensé à la base non… Enfin bref ça marche on va pas se plaindre ! */
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

		Button boutonReset = new Button("Reset"); //bouton permettant de remettre les comptes de pièces à zéro
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

		lbARendre = new Label("- €"); //label indiquant la quantité d'argent à rendre au client
		lbARendre.getStyleClass().add(A_RENDRE);
		lbARendre.setMaxWidth(Double.MAX_VALUE);

		ajouterBouton = new Button("A_jouter"); //J est une touche mnémonique pour ce bouton
		ajouterBouton.setMnemonicParsing(true);
		ajouterBouton.setDefaultButton(true);
		ajouterBouton.setDisable(true);
		ajouterBouton.minWidthProperty().bind(boutonReset.minWidthProperty());
		ajouterBouton.maxWidthProperty().bind(ajouterBouton.minWidthProperty());
		ajouterBouton.setMinHeight(App.TAILLE_PANNEAU_RESULTAT - 55 - 3*ESPACE_A_RENDRE - ESPACE_BAS_BOUTON_AJOUTER);
		ajouterBouton.setMaxHeight(App.TAILLE_PANNEAU_RESULTAT - 55 - 3*ESPACE_A_RENDRE - ESPACE_BAS_BOUTON_AJOUTER);	

		ajouterBouton.setOnAction(new EventHandler<ActionEvent>(){ //au déclenchement du bouton,
			public void handle(ActionEvent event) {
				Commande commande = new Commande( //on crée une nouvelle commande avec les données du panneau de sélection
						new Date(),
						Selection.getPlatSelectionne(),
						Selection.getIngredientsSelectionnes(),
						Selection.getSaucesSelectionnees(),
						Selection.getDessertSelectionne(),
						Selection.getBoissonSelectionnee(),
						Selection.getSupplementBoissonSelectionne(),
						Core.getService());

						Core.getService().ajouterCommande(commande); //on l'ajoute
						EcranConfection.mettreEcransAJour(); //on met les données de l'écran de confection à jour

						if(Selection.getPlatSelectionne().getUtilisePain()){ //s'il n'y a plus de pain, on grise les plats qui en utilisent
							if(Core.getService().getNbBaguettesRestantes() <= 0){
								int i = 0;
								for(Plat plat : BaseDonnees.getPlats()){
									if(plat.getUtilisePain()){
										Selection.platDisponible(i, false);
									}

									i++;
								}
							}
						}

						Selection.resetSelection(); //on remet la sélection et les pièces à zéro

						resetPieces();

						Selection.refreshCompteurBaguettes();
			}
		});

		aRendreAjouter.getChildren().addAll(boutonReset, aRendre, lbARendre, ajouterBouton);

		separation.setLeft(pieces);
		separation.setRight(aRendreAjouter);

		return(separation);
	}

	/**
	 * Crée un panneau permettant de gérer l'équipe actuelle du service, c'est à dire de visualiser les membres réalisant le service et de les sélectionner.
	 * 
	 * @param parent le node parent de celui-ci.
	 * @return le panneau de gestion de l'équipe.
	 */
	public static final Region equipe(Region parent){

		AnchorPane layout = new AnchorPane();

		GridPane membres = new GridPane();
		membres.setHgap(5.0); //TODO changer ça en constante
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
				SelectionMembre.selectionOrdi();
			}
		});

		Button boutonCommis1 = new Button("Modifier");
		boutonCommis1.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a){
				SelectionMembre.selectionCommis1();
			}
		});

		Button boutonCommis2 = new Button("Modifier");
		boutonCommis2.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a){
				SelectionMembre.selectionCommis2();
			}
		});

		Button boutonConfection1 = new Button("Modifier");
		boutonConfection1.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a){
				SelectionMembre.selectionConfection1().setOnCloseRequest(new EventHandler<WindowEvent>() {
					public void handle(WindowEvent e){
						Core.getService().assignation();
						EcranConfection.mettreEcransAJour();
					}
				});
			}
		});

		Button boutonConfection2 = new Button("Modifier");
		boutonConfection2.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a){
				SelectionMembre.selectionConfection2().setOnCloseRequest(new EventHandler<WindowEvent>() {
					public void handle(WindowEvent e){
						Core.getService().assignation();
						EcranConfection.mettreEcransAJour();
					}
				});
			}
		});

		Button boutonConfection3 = new Button("Modifier");
		boutonConfection3.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent a){
				SelectionMembre.selectionConfection3().setOnCloseRequest(new EventHandler<WindowEvent>() {
					public void handle(WindowEvent e){
						Core.getService().assignation();
						EcranConfection.mettreEcransAJour();
					}
				});
			}
		});

		mettreOrdiAJour();

		mettreCommisAJour();

		mettreConfectionAJour();

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

		ColumnConstraints nePasGrow = new ColumnConstraints(); //on indique que les colonnes à l'extérieur du tableau (les labels et les boutons) ne doivent pas grander si le tableau est redimensionné mais que la colonne du milieu (le blaze court du membre sélectionné) si
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

	/**
	 * Remet toutes les pièces de la caisse à zéro.
	 */
	public static final void resetPieces(){

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

	/**
	 * Met le compte d'argent à rendre à jour. Celui-ci fonctionne de la manière suivante : si le client n'a pas donné assez d'argent, alors le compte affiche « - € », s'il a donné pile la bonne quantité d'argent, il affiche « 0 € » et s'il a donné trop d'argent il indique le montant à rendre au client.
	 */
	public static final void mettreCompteurAJour(){

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

		if(totalCommande <= 0){ //si le membre ne doit rien payer, on désactive le bouton - manquerait plus qu'on soit gratuits
			ajouterBouton.setDisable(true);
		} else {
			ajouterBouton.setDisable(false);
		}

		lbARendre.setText(affARendre);
	}

	/**
	 * Met l'affichage du label correspondant au membre au poste ordi à jour.
	 */
	public static final void mettreOrdiAJour(){
		if(!Core.getService().getOrdi().getId().equals(Membre.ID_MEMBRE_DEFAUT)){
			membreOrdi.setText(Core.getService().getOrdi().getBlazeCourt().toUpperCase());
		} else {
			membreOrdi.setText("---");
		}
	}

	/**
	 * Met l'affichage des labels correspondant au membres au poste commis à jour.
	 */
	public static final void mettreCommisAJour(){
		if(Core.getService().getCommis().size() > 0){
			membreCommis1.setText(Core.getService().getCommis().get(0).getBlazeCourt().toUpperCase());
			if(Core.getService().getCommis().size() > 1){
				membreCommis2.setText(Core.getService().getCommis().get(1).getBlazeCourt().toUpperCase());
			} else {
				membreCommis2.setText("---");
			}
		} else {
			membreCommis1.setText("---");
		}
	}

	/**
	 * Met l'affichage des labels correspondant au membres au poste confection à jour.
	 */
	public static final void mettreConfectionAJour(){
		if(Core.getService().getConfection().size() > 0){
			membreConfection1.setText(Core.getService().getConfection().get(0).getBlazeCourt().toUpperCase());
			if(Core.getService().getConfection().size() > 1){
				membreConfection2.setText(Core.getService().getConfection().get(1).getBlazeCourt().toUpperCase());
				if(Core.getService().getConfection().size() > 2){
					membreConfection3.setText(Core.getService().getConfection().get(2).getBlazeCourt().toUpperCase());
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
