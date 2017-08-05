package kfetinfo.ui;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import kfetinfo.core.BaseDonnees;
import kfetinfo.core.Commande;
import kfetinfo.core.CommandeAssignee;
import kfetinfo.core.Core;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Sauce;

public class Commandes {
	public static final String COMMANDE = "commande";
	public static final String PANNEAU_COMMANDES = "panneau-commandes";
	public static final String PLAT_LISTE_COMMANDES = "plat-liste-commandes";

	public static final Double HAUTEUR_COMMANDE_FERMEE = 60.0;
	public static final Double HAUTEUR_COMMANDE_DEVELOPPEE = 130.0;

	static VBox commandes;

	static List<Region> commandesPanes = new ArrayList<Region>();
	static List<Boolean> developpes = new ArrayList<Boolean>();
	static List<VBox> contenusCommandes = new ArrayList<VBox>();
	
	public static Region commandes(Core core){
		ScrollPane panneauCommandes = new ScrollPane();
		panneauCommandes.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		panneauCommandes.setMinWidth(App.TAILLE_PANNEAU_COMMANDES);
		panneauCommandes.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		panneauCommandes.setHbarPolicy(ScrollBarPolicy.NEVER);
		panneauCommandes.getStyleClass().add(PANNEAU_COMMANDES);

		commandes = new VBox();

		commandes.setSpacing(-1.0);

		for(Commande commande : core.getService().getCommandes()){
			ajouterCommande(commande);
		}

		core.getService().nouvelleCommandePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				ajouterCommande(core.getService().getNouvelleCommande());
			}
		});

		panneauCommandes.setContent(commandes);

		return(panneauCommandes);
	}

	private static void ajouterCommande(Commande commande){
		AnchorPane commandePane = new AnchorPane();
		commandePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE);
		commandePane.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		commandePane.getStyleClass().add(COMMANDE);

		developpes.add(false);

		Label numero = new Label("" + commande.getNumero());
		numero.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
		numero.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		numero.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		numero.getStyleClass().add(App.NUMERO_COMMANDE);

		Label plat = new Label(commande.getPlat().getNom().toUpperCase());
		plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
		plat.setMinHeight(Control.USE_PREF_SIZE);
		plat.setMaxHeight(Control.USE_PREF_SIZE);
		plat.setMaxWidth(Double.MAX_VALUE);
		plat.setPrefWidth(App.TAILLE_PANNEAU_COMMANDES - App.TAILLE_NUMERO_COMMANDE - 30);
		plat.getStyleClass().add(App.PLAT_COMMANDE);
		plat.getStyleClass().add(PLAT_LISTE_COMMANDES);

		VBox contenuCommande = new VBox();
		contenuCommande.setVisible(false);
		contenusCommandes.add(contenuCommande);

		Label lbIngredients = lbIngredients(commande);
		lbIngredients.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 36);
		Label lbSauces = lbSauces(commande);
		lbSauces.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
		Label lbBoisson = new Label(commande.getBoisson().getNom());
		lbBoisson.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
		Label lbDessert = new Label(commande.getDessert().getNom());
		lbDessert.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);

		contenuCommande.getChildren().addAll(lbIngredients, lbSauces, lbBoisson, lbDessert);

		AnchorPane.setTopAnchor(numero, -1.0);
		AnchorPane.setLeftAnchor(numero, -1.0);
		AnchorPane.setTopAnchor(plat, 0.0);
		AnchorPane.setLeftAnchor(plat, App.TAILLE_NUMERO_COMMANDE + App.ESPACE_NUMERO_PLAT);
		AnchorPane.setTopAnchor(contenuCommande, App.TAILLE_NUMERO_COMMANDE);
		AnchorPane.setLeftAnchor(contenuCommande, 3.0);

		commandePane.getChildren().add(numero);
		commandePane.getChildren().add(plat);
		commandePane.getChildren().add(contenuCommande);

		commandesPanes.add(commandePane);

		plat.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event e){
				developpes.set(commandesPanes.indexOf(commandePane), !developpes.get(commandesPanes.indexOf(commandePane)));
				if(developpes.get(commandesPanes.indexOf(commandePane))){
					commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_DEVELOPPEE);
					contenusCommandes.get(commandesPanes.indexOf(commandePane)).setVisible(true);
				} else {
					commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE);
					contenusCommandes.get(commandesPanes.indexOf(commandePane)).setVisible(false);
				}
			}
		});

		commandes.getChildren().add(commandePane);
	}

	private static Label lbIngredients(Commande commande){
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

	private static Label lbSauces(Commande commande){
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
}
