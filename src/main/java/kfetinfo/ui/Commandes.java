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

public class Commandes {
	public static final String COMMANDE = "commande";

	public static final Double HAUTEUR_COMMANDE_FERMEE = 60.0;
	public static final Double HAUTEUR_COMMANDE_DEVELOPPEE = 200.0;

	static VBox commandes;
	
	public static Region commandes(Core core){
		ScrollPane panneauCommandes = new ScrollPane();
		panneauCommandes.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		panneauCommandes.setMinWidth(App.TAILLE_PANNEAU_COMMANDES);
		panneauCommandes.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		panneauCommandes.setHbarPolicy(ScrollBarPolicy.NEVER);

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

		Label numero = new Label("" + commande.getNumero());
		numero.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
		numero.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		numero.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		numero.getStyleClass().add(App.NUMERO_COMMANDE);

		Label plat = new Label(commande.getPlat().getNom().toUpperCase());
		plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
		plat.setMinHeight(Control.USE_PREF_SIZE);
		plat.setMaxHeight(Control.USE_PREF_SIZE);
		plat.getStyleClass().add(App.PLAT_COMMANDE);

		AnchorPane.setTopAnchor(numero, -1.0);
		AnchorPane.setLeftAnchor(numero, -1.0);
		AnchorPane.setTopAnchor(plat, 0.0);
		AnchorPane.setLeftAnchor(plat, App.TAILLE_NUMERO_COMMANDE + App.ESPACE_NUMERO_PLAT);

		commandePane.getChildren().add(numero);
		commandePane.getChildren().add(plat);

		commandes.getChildren().add(commandePane);
	}
}
