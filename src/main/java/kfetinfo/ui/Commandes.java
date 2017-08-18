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

	public static final Double HAUTEUR_COMMANDE_FERMEE = 30.0;
	public static final Double HAUTEUR_COMMANDE_DEVELOPPEE = 134.0;
	public static final Double HAUTEUR_AJOUT_BOUTONS = 36.0;

	static VBox commandesRealisees = new VBox();
	static List<Integer> listeCommandesRealisees = new ArrayList<Integer>();
	static List<Boolean> devCommandesRealisees = new ArrayList<Boolean>();
	static List<VBox> contenusCommandesRealisees = new ArrayList<VBox>();
	static VBox commandesAssignees = new VBox();
	static List<Integer> listeCommandesAssignees = new ArrayList<Integer>();
	static List<Boolean> devCommandesAssignees = new ArrayList<Boolean>();
	static List<VBox> contenusCommandesAssignees = new ArrayList<VBox>();
	static VBox commandesAjoutees = new VBox();
	static List<Integer> listeCommandesAjoutees = new ArrayList<Integer>();
	static List<Boolean> devCommandesAjoutees = new ArrayList<Boolean>();
	static List<VBox> contenusCommandesAjoutees = new ArrayList<VBox>();
	static VBox commandesDonnees = new VBox();
	static List<Integer> listeCommandesDonnees = new ArrayList<Integer>();
	static List<Boolean> devCommandesDonnees = new ArrayList<Boolean>();
	static List<VBox> contenusCommandesDonnees = new ArrayList<VBox>();

	public static Region commandes(Core core){
		ScrollPane panneauCommandes = new ScrollPane();
		panneauCommandes.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		panneauCommandes.setMinWidth(App.TAILLE_PANNEAU_COMMANDES);
		panneauCommandes.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		panneauCommandes.setHbarPolicy(ScrollBarPolicy.NEVER);
		panneauCommandes.getStyleClass().add(PANNEAU_COMMANDES);

		for(Commande commande : core.getService().getCommandes()){
			if(commande instanceof CommandeAssignee){
				CommandeAssignee commandeAssignee = (CommandeAssignee)commande;
				if(commandeAssignee.getEstRealisee()){
					if(commandeAssignee.getEstDonnee()){ //commande donnée
						listeCommandesDonnees.add(commandeAssignee.getNumero());

						AnchorPane commandePane = new AnchorPane();
						commandePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
						commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE);
						commandePane.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
						commandePane.getStyleClass().add(COMMANDE);
				
						Label numero = new Label("" + commandeAssignee.getNumero());
						numero.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
						numero.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
						numero.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
						numero.getStyleClass().add(App.NUMERO_COMMANDE_DONNEE);
				
						Label plat = new Label(commandeAssignee.getPlat().getNom().toUpperCase());
						plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
						plat.setMinHeight(Control.USE_PREF_SIZE);
						plat.setMaxHeight(Control.USE_PREF_SIZE);
						plat.setMaxWidth(Double.MAX_VALUE);
						plat.getStyleClass().add(App.PLAT_COMMANDE);
						plat.getStyleClass().add(PLAT_LISTE_COMMANDES);

						Label confection = new Label();
						confection.setText(commandeAssignee.getMembre().getBlazeCourt());
				
						VBox contenuCommande = new VBox();
						contenuCommande.setVisible(false);
				
						Label lbIngredients = lbIngredients(commandeAssignee);
						lbIngredients.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 36);
						Label lbSauces = lbSauces(commandeAssignee);
						lbSauces.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
						Label lbBoisson = new Label();
						if(commandeAssignee.getSupplementBoisson().getId().equals("fa03180b-95ad-4a5b-84f2-cbdc2beae920")){
							lbBoisson.setText(commandeAssignee.getBoisson().getNom());
						} else {
							lbBoisson.setText(commandeAssignee.getBoisson().getNom() + " + " + commandeAssignee.getSupplementBoisson().getNom());
						}
						lbBoisson.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
						Label lbDessert = new Label(commandeAssignee.getDessert().getNom());
						lbDessert.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);

						Button retirer = new Button("Retirer");
				
						contenuCommande.getChildren().addAll(lbIngredients, lbSauces, lbBoisson, lbDessert, retirer);
						contenusCommandesDonnees.add(contenuCommande);
						devCommandesDonnees.add(false);

						AnchorPane.setTopAnchor(numero, -1.0);
						AnchorPane.setLeftAnchor(numero, -1.0);
						AnchorPane.setTopAnchor(plat, 0.0);
						AnchorPane.setLeftAnchor(plat, App.TAILLE_NUMERO_COMMANDE + App.ESPACE_NUMERO_PLAT);
						AnchorPane.setTopAnchor(confection, 0.0);
						AnchorPane.setRightAnchor(confection, 2.0);
						AnchorPane.setTopAnchor(contenuCommande, App.TAILLE_NUMERO_COMMANDE);
						AnchorPane.setLeftAnchor(contenuCommande, 3.0);

						commandePane.getChildren().add(numero);
						commandePane.getChildren().add(plat);
						commandePane.getChildren().add(confection);
						commandePane.getChildren().add(contenuCommande);

						plat.setOnMouseClicked(new EventHandler<Event>() {
							public void handle(Event e){
								devCommandesDonnees.set(commandesDonnees.getChildren().indexOf(commandePane), !devCommandesDonnees.get(commandesDonnees.getChildren().indexOf(commandePane)));
								if(devCommandesDonnees.get(commandesDonnees.getChildren().indexOf(commandePane))){
									commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_DEVELOPPEE);
									contenusCommandesDonnees.get(commandesDonnees.getChildren().indexOf(commandePane)).setVisible(true);
								} else {
									commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE);
									contenusCommandesDonnees.get(commandesDonnees.getChildren().indexOf(commandePane)).setVisible(false);
								}}});

						retirer.setOnAction(new EventHandler<ActionEvent>(){
							public void handle(ActionEvent a){
								core.getService().retirerCommande(commande);
								EcranConfection.mettreEcransAJour(core);
								Selection.refreshCompteurBaguettes();
							}
						});

						commandesDonnees.getChildren().add(commandePane);
					} else { //commande réalisée
						listeCommandesRealisees.add(commandeAssignee.getNumero());

						AnchorPane commandePane = new AnchorPane();
						commandePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
						commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE + HAUTEUR_AJOUT_BOUTONS);
						commandePane.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
						commandePane.getStyleClass().add(COMMANDE);
				
						Label numero = new Label("" + commandeAssignee.getNumero());
						numero.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
						numero.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
						numero.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
						numero.getStyleClass().add(App.NUMERO_COMMANDE_REALISEE);
				
						Label plat = new Label(commandeAssignee.getPlat().getNom().toUpperCase());
						plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
						plat.setMinHeight(Control.USE_PREF_SIZE);
						plat.setMaxHeight(Control.USE_PREF_SIZE);
						plat.setMaxWidth(Double.MAX_VALUE);
						plat.getStyleClass().add(App.PLAT_COMMANDE);
						plat.getStyleClass().add(PLAT_LISTE_COMMANDES);

						Label confection = new Label();
						confection.setText(commandeAssignee.getMembre().getBlazeCourt());
				
						VBox contenuCommande = new VBox();
						contenuCommande.setVisible(false);
				
						Label lbIngredients = lbIngredients(commandeAssignee);
						lbIngredients.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 36);
						Label lbSauces = lbSauces(commandeAssignee);
						lbSauces.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
						Label lbBoisson = new Label();
						if(commandeAssignee.getSupplementBoisson().getId().equals("fa03180b-95ad-4a5b-84f2-cbdc2beae920")){
							lbBoisson.setText(commandeAssignee.getBoisson().getNom());
						} else {
							lbBoisson.setText(commandeAssignee.getBoisson().getNom() + " + " + commandeAssignee.getSupplementBoisson().getNom());
						}
						lbBoisson.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
						Label lbDessert = new Label(commandeAssignee.getDessert().getNom());
						lbDessert.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
				
						Button retirer = new Button("Retirer");
						
						contenuCommande.getChildren().addAll(lbIngredients, lbSauces, lbBoisson, lbDessert, retirer);
						contenusCommandesRealisees.add(contenuCommande);
						devCommandesRealisees.add(false);

						Button boutonDonnee = new Button("Donnée");

						AnchorPane.setTopAnchor(numero, -1.0);
						AnchorPane.setLeftAnchor(numero, -1.0);
						AnchorPane.setTopAnchor(plat, 0.0);
						AnchorPane.setLeftAnchor(plat, App.TAILLE_NUMERO_COMMANDE + App.ESPACE_NUMERO_PLAT);
						AnchorPane.setTopAnchor(confection, 0.0);
						AnchorPane.setRightAnchor(confection, 2.0);
						AnchorPane.setTopAnchor(contenuCommande, App.TAILLE_NUMERO_COMMANDE);
						AnchorPane.setLeftAnchor(contenuCommande, 3.0);
						AnchorPane.setRightAnchor(boutonDonnee, 50.0);
						AnchorPane.setBottomAnchor(boutonDonnee, 7.0);

						commandePane.getChildren().add(numero);
						commandePane.getChildren().add(plat);
						commandePane.getChildren().add(confection);
						commandePane.getChildren().add(contenuCommande);
						commandePane.getChildren().add(boutonDonnee);

						plat.setOnMouseClicked(new EventHandler<Event>() {
							public void handle(Event e){
								devCommandesRealisees.set(commandesRealisees.getChildren().indexOf(commandePane), !devCommandesRealisees.get(commandesRealisees.getChildren().indexOf(commandePane)));
								if(devCommandesRealisees.get(commandesRealisees.getChildren().indexOf(commandePane))){
									commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_DEVELOPPEE + HAUTEUR_AJOUT_BOUTONS);
									contenusCommandesRealisees.get(commandesRealisees.getChildren().indexOf(commandePane)).setVisible(true);
								} else {
									commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE + HAUTEUR_AJOUT_BOUTONS);
									contenusCommandesRealisees.get(commandesRealisees.getChildren().indexOf(commandePane)).setVisible(false);
								}}});

						retirer.setOnAction(new EventHandler<ActionEvent>(){
							public void handle(ActionEvent a){
								core.getService().retirerCommande(commande);
								EcranConfection.mettreEcransAJour(core);
								Selection.refreshCompteurBaguettes();
							}
						});

						boutonDonnee.setOnAction(new EventHandler<ActionEvent>(){
							public void handle(ActionEvent a){
								commandeAssignee.donnee(core.getService());
							}
						});

						commandesRealisees.getChildren().add(commandePane);
					}
				} else { //commande assignée
					listeCommandesAssignees.add(commandeAssignee.getNumero());

					AnchorPane commandePane = new AnchorPane();
					commandePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
					commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE + HAUTEUR_AJOUT_BOUTONS);
					commandePane.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
					commandePane.getStyleClass().add(COMMANDE);
			
					Label numero = new Label("" + commandeAssignee.getNumero());
					numero.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
					numero.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
					numero.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
					numero.getStyleClass().add(App.NUMERO_COMMANDE_ASSIGNEE);
			
					Label plat = new Label(commandeAssignee.getPlat().getNom().toUpperCase());
					plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
					plat.setMinHeight(Control.USE_PREF_SIZE);
					plat.setMaxHeight(Control.USE_PREF_SIZE);
					plat.setMaxWidth(Double.MAX_VALUE);
					plat.getStyleClass().add(App.PLAT_COMMANDE);
					plat.getStyleClass().add(PLAT_LISTE_COMMANDES);

					Label confection = new Label();
					confection.setText(commandeAssignee.getMembre().getBlazeCourt());
			
					VBox contenuCommande = new VBox();
					contenuCommande.setVisible(false);
			
					Label lbIngredients = lbIngredients(commandeAssignee);
					lbIngredients.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 36);
					Label lbSauces = lbSauces(commandeAssignee);
					lbSauces.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
					Label lbBoisson = new Label();
					if(commandeAssignee.getSupplementBoisson().getId().equals("fa03180b-95ad-4a5b-84f2-cbdc2beae920")){
						lbBoisson.setText(commandeAssignee.getBoisson().getNom());
					} else {
						lbBoisson.setText(commandeAssignee.getBoisson().getNom() + " + " + commandeAssignee.getSupplementBoisson().getNom());
					}
					lbBoisson.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
					Label lbDessert = new Label(commandeAssignee.getDessert().getNom());
					lbDessert.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
			
					Button retirer = new Button("Retirer");
					
					contenuCommande.getChildren().addAll(lbIngredients, lbSauces, lbBoisson, lbDessert, retirer);
					contenusCommandesAssignees.add(contenuCommande);
					devCommandesAssignees.add(false);

					Button boutonRealisee = new Button("Réalisée");
					Button boutonDonnee = new Button("Donnée");

					AnchorPane.setTopAnchor(numero, -1.0);
					AnchorPane.setLeftAnchor(numero, -1.0);
					AnchorPane.setTopAnchor(plat, 0.0);
					AnchorPane.setLeftAnchor(plat, App.TAILLE_NUMERO_COMMANDE + App.ESPACE_NUMERO_PLAT);
					AnchorPane.setTopAnchor(confection, 0.0);
					AnchorPane.setRightAnchor(confection, 2.0);
					AnchorPane.setTopAnchor(contenuCommande, App.TAILLE_NUMERO_COMMANDE);
					AnchorPane.setLeftAnchor(contenuCommande, 3.0);
					AnchorPane.setLeftAnchor(boutonRealisee, 50.0);
					AnchorPane.setBottomAnchor(boutonRealisee, 7.0);
					AnchorPane.setRightAnchor(boutonDonnee, 50.0);
					AnchorPane.setBottomAnchor(boutonDonnee, 7.0);

					commandePane.getChildren().add(numero);
					commandePane.getChildren().add(plat);
					commandePane.getChildren().add(confection);
					commandePane.getChildren().add(contenuCommande);
					commandePane.getChildren().add(boutonRealisee);
					commandePane.getChildren().add(boutonDonnee);

					plat.setOnMouseClicked(new EventHandler<Event>() {
						public void handle(Event e){
							devCommandesAssignees.set(commandesAssignees.getChildren().indexOf(commandePane), !devCommandesAssignees.get(commandesAssignees.getChildren().indexOf(commandePane)));
							if(devCommandesAssignees.get(commandesAssignees.getChildren().indexOf(commandePane))){
								commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_DEVELOPPEE + HAUTEUR_AJOUT_BOUTONS);
								contenusCommandesAssignees.get(commandesAssignees.getChildren().indexOf(commandePane)).setVisible(true);
							} else {
								commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE + HAUTEUR_AJOUT_BOUTONS);
								contenusCommandesAssignees.get(commandesAssignees.getChildren().indexOf(commandePane)).setVisible(false);
							}}});

					retirer.setOnAction(new EventHandler<ActionEvent>(){
						public void handle(ActionEvent a){
							core.getService().retirerCommande(commande);
							EcranConfection.mettreEcransAJour(core);
							Selection.refreshCompteurBaguettes();
						}
					});

					boutonRealisee.setOnAction(new EventHandler<ActionEvent>(){
						public void handle(ActionEvent a){
							commandeAssignee.realisee(core.getService());
							EcranConfection.mettreEcransAJour(core);
						}
					});

					boutonDonnee.setOnAction(new EventHandler<ActionEvent>(){
						public void handle(ActionEvent a){
							commandeAssignee.donnee(core.getService());
						}
					});

					commandesAssignees.getChildren().add(commandePane);
				}
			} else { //commande ajoutée
				listeCommandesAjoutees.add(commande.getNumero());

				AnchorPane commandePane = new AnchorPane();
				commandePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE);
				commandePane.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
				commandePane.getStyleClass().add(COMMANDE);
		
				Label numero = new Label("" + commande.getNumero());
				numero.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
				numero.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
				numero.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
				numero.getStyleClass().add(App.NUMERO_COMMANDE_AJOUTEE);
		
				Label plat = new Label(commande.getPlat().getNom().toUpperCase());
				plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
				plat.setMinHeight(Control.USE_PREF_SIZE);
				plat.setMaxHeight(Control.USE_PREF_SIZE);
				plat.setMaxWidth(Double.MAX_VALUE);
				plat.getStyleClass().add(App.PLAT_COMMANDE);
				plat.getStyleClass().add(PLAT_LISTE_COMMANDES);
		
				VBox contenuCommande = new VBox();
				contenuCommande.setVisible(false);
		
				Label lbIngredients = lbIngredients(commande);
				lbIngredients.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 36);
				Label lbSauces = lbSauces(commande);
				lbSauces.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
				Label lbBoisson = new Label();
				if(commande.getSupplementBoisson().getId().equals("fa03180b-95ad-4a5b-84f2-cbdc2beae920")){
					lbBoisson.setText(commande.getBoisson().getNom());
				} else {
					lbBoisson.setText(commande.getBoisson().getNom() + " + " + commande.getSupplementBoisson().getNom());
				}
				lbBoisson.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
				Label lbDessert = new Label(commande.getDessert().getNom());
				lbDessert.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
		
				Button retirer = new Button("Retirer");
				
				contenuCommande.getChildren().addAll(lbIngredients, lbSauces, lbBoisson, lbDessert, retirer);
				contenusCommandesAjoutees.add(contenuCommande);
				devCommandesAjoutees.add(false);
		
				AnchorPane.setTopAnchor(numero, -1.0);
				AnchorPane.setLeftAnchor(numero, -1.0);
				AnchorPane.setTopAnchor(plat, 0.0);
				AnchorPane.setLeftAnchor(plat, App.TAILLE_NUMERO_COMMANDE + App.ESPACE_NUMERO_PLAT);
				AnchorPane.setTopAnchor(contenuCommande, App.TAILLE_NUMERO_COMMANDE);
				AnchorPane.setLeftAnchor(contenuCommande, 3.0);

				commandePane.getChildren().add(numero);
				commandePane.getChildren().add(plat);
				commandePane.getChildren().add(contenuCommande);

				plat.setOnMouseClicked(new EventHandler<Event>() {
					public void handle(Event e){
						devCommandesAjoutees.set(commandesAjoutees.getChildren().indexOf(commandePane), !devCommandesAjoutees.get(commandesAjoutees.getChildren().indexOf(commandePane)));
						if(devCommandesAjoutees.get(commandesAjoutees.getChildren().indexOf(commandePane))){
							commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_DEVELOPPEE);
							contenusCommandesAjoutees.get(commandesAjoutees.getChildren().indexOf(commandePane)).setVisible(true);
						} else {
							commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE);
							contenusCommandesAjoutees.get(commandesAjoutees.getChildren().indexOf(commandePane)).setVisible(false);
						}}});

				retirer.setOnAction(new EventHandler<ActionEvent>(){
					public void handle(ActionEvent a){
						core.getService().retirerCommande(commande);
						EcranConfection.mettreEcransAJour(core);
						Selection.refreshCompteurBaguettes();
					}
				});

				commandesAjoutees.getChildren().add(commandePane);
			}
		}

		commandesRealisees.setSpacing(-1.0);
		commandesAssignees.setSpacing(-1.0);
		commandesAjoutees.setSpacing(-1.0);
		commandesDonnees.setSpacing(-1.0);
		VBox commandes = new VBox();
		commandes.getChildren().addAll(commandesRealisees, commandesAssignees, commandesAjoutees, commandesDonnees);
		panneauCommandes.setContent(commandes);

		core.getService().nouvelleCommandePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				Commande commande = (newVal != null) ? (Commande)newVal : new Commande();
				listeCommandesAjoutees.add(commande.getNumero());

				AnchorPane commandePane = new AnchorPane();
				commandePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE);
				commandePane.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
				commandePane.getStyleClass().add(COMMANDE);
		
				Label numero = new Label("" + commande.getNumero());
				numero.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
				numero.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
				numero.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
				numero.getStyleClass().add(App.NUMERO_COMMANDE_AJOUTEE);
		
				Label plat = new Label(commande.getPlat().getNom().toUpperCase());
				plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
				plat.setMinHeight(Control.USE_PREF_SIZE);
				plat.setMaxHeight(Control.USE_PREF_SIZE);
				plat.setMaxWidth(Double.MAX_VALUE);
				plat.getStyleClass().add(App.PLAT_COMMANDE);
				plat.getStyleClass().add(PLAT_LISTE_COMMANDES);
		
				VBox contenuCommande = new VBox();
				contenuCommande.setVisible(false);
		
				Label lbIngredients = lbIngredients(commande);
				lbIngredients.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 36);
				Label lbSauces = lbSauces(commande);
				lbSauces.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
				Label lbBoisson = new Label();
				if(commande.getSupplementBoisson().getId().equals("fa03180b-95ad-4a5b-84f2-cbdc2beae920")){
					lbBoisson.setText(commande.getBoisson().getNom());
				} else {
					lbBoisson.setText(commande.getBoisson().getNom() + " + " + commande.getSupplementBoisson().getNom());
				}
				lbBoisson.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
				Label lbDessert = new Label(commande.getDessert().getNom());
				lbDessert.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
		
				Button retirer = new Button("Retirer");
				
				contenuCommande.getChildren().addAll(lbIngredients, lbSauces, lbBoisson, lbDessert, retirer);
				contenusCommandesAjoutees.add(contenuCommande);
				devCommandesAjoutees.add(false);
		
				AnchorPane.setTopAnchor(numero, -1.0);
				AnchorPane.setLeftAnchor(numero, -1.0);
				AnchorPane.setTopAnchor(plat, 0.0);
				AnchorPane.setLeftAnchor(plat, App.TAILLE_NUMERO_COMMANDE + App.ESPACE_NUMERO_PLAT);
				AnchorPane.setTopAnchor(contenuCommande, App.TAILLE_NUMERO_COMMANDE);
				AnchorPane.setLeftAnchor(contenuCommande, 3.0);

				commandePane.getChildren().add(numero);
				commandePane.getChildren().add(plat);
				commandePane.getChildren().add(contenuCommande);

				plat.setOnMouseClicked(new EventHandler<Event>() {
					public void handle(Event e){
						devCommandesAjoutees.set(commandesAjoutees.getChildren().indexOf(commandePane), !devCommandesAjoutees.get(commandesAjoutees.getChildren().indexOf(commandePane)));
						if(devCommandesAjoutees.get(commandesAjoutees.getChildren().indexOf(commandePane))){
							commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_DEVELOPPEE);
							contenusCommandesAjoutees.get(commandesAjoutees.getChildren().indexOf(commandePane)).setVisible(true);
						} else {
							commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE);
							contenusCommandesAjoutees.get(commandesAjoutees.getChildren().indexOf(commandePane)).setVisible(false);
						}}});

				retirer.setOnAction(new EventHandler<ActionEvent>(){
					public void handle(ActionEvent a){
						core.getService().retirerCommande(commande);
						EcranConfection.mettreEcransAJour(core);
						Selection.refreshCompteurBaguettes();
					}
				});

				commandesAjoutees.getChildren().add(commandePane);
			}
		});

		//###################################################################

		core.getService().nouvelleCommandeAssigneePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				CommandeAssignee commande = (newVal != null) ? (CommandeAssignee)newVal : null;
				if(commande != null){
					int n = -1;
					for(Integer numero : listeCommandesAjoutees){
						if(numero == commande.getNumero()){
							commandesAjoutees.getChildren().remove(listeCommandesAjoutees.indexOf(numero));
							devCommandesAjoutees.remove(listeCommandesAjoutees.indexOf(numero));
							contenusCommandesAjoutees.remove(listeCommandesAjoutees.indexOf(numero));
							n = listeCommandesAjoutees.indexOf(numero);
						}
					}

					if(n != -1){
						listeCommandesAjoutees.remove(n);
					}

					listeCommandesAssignees.add(commande.getNumero());

					AnchorPane commandePane = new AnchorPane();
					commandePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
					commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE + HAUTEUR_AJOUT_BOUTONS);
					commandePane.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
					commandePane.getStyleClass().add(COMMANDE);
			
					Label numero = new Label("" + commande.getNumero());
					numero.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
					numero.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
					numero.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
					numero.getStyleClass().add(App.NUMERO_COMMANDE_ASSIGNEE);
			
					Label plat = new Label(commande.getPlat().getNom().toUpperCase());
					plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
					plat.setMinHeight(Control.USE_PREF_SIZE);
					plat.setMaxHeight(Control.USE_PREF_SIZE);
					plat.setMaxWidth(Double.MAX_VALUE);
					plat.getStyleClass().add(App.PLAT_COMMANDE);
					plat.getStyleClass().add(PLAT_LISTE_COMMANDES);

					Label confection = new Label();
					confection.setText(commande.getMembre().getBlazeCourt());
			
					VBox contenuCommande = new VBox();
					contenuCommande.setVisible(false);
			
					Label lbIngredients = lbIngredients(commande);
					lbIngredients.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 36);
					Label lbSauces = lbSauces(commande);
					lbSauces.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
					Label lbBoisson = new Label();
					if(commande.getSupplementBoisson().getId().equals("fa03180b-95ad-4a5b-84f2-cbdc2beae920")){
						lbBoisson.setText(commande.getBoisson().getNom());
					} else {
						lbBoisson.setText(commande.getBoisson().getNom() + " + " + commande.getSupplementBoisson().getNom());
					}
					lbBoisson.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
					Label lbDessert = new Label(commande.getDessert().getNom());
					lbDessert.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
			
					Button retirer = new Button("Retirer");
					
					contenuCommande.getChildren().addAll(lbIngredients, lbSauces, lbBoisson, lbDessert, retirer);
					contenusCommandesAssignees.add(contenuCommande);
					devCommandesAssignees.add(false);

					Button boutonRealisee = new Button("Réalisée");
					Button boutonDonnee = new Button("Donnée");

					AnchorPane.setTopAnchor(numero, -1.0);
					AnchorPane.setLeftAnchor(numero, -1.0);
					AnchorPane.setTopAnchor(plat, 0.0);
					AnchorPane.setLeftAnchor(plat, App.TAILLE_NUMERO_COMMANDE + App.ESPACE_NUMERO_PLAT);
					AnchorPane.setTopAnchor(confection, 0.0);
					AnchorPane.setRightAnchor(confection, 2.0);
					AnchorPane.setTopAnchor(contenuCommande, App.TAILLE_NUMERO_COMMANDE);
					AnchorPane.setLeftAnchor(contenuCommande, 3.0);
					AnchorPane.setLeftAnchor(boutonRealisee, 50.0);
					AnchorPane.setBottomAnchor(boutonRealisee, 7.0);
					AnchorPane.setRightAnchor(boutonDonnee, 50.0);
					AnchorPane.setBottomAnchor(boutonDonnee, 7.0);

					commandePane.getChildren().add(numero);
					commandePane.getChildren().add(plat);
					commandePane.getChildren().add(confection);
					commandePane.getChildren().add(contenuCommande);
					commandePane.getChildren().add(boutonRealisee);
					commandePane.getChildren().add(boutonDonnee);

					plat.setOnMouseClicked(new EventHandler<Event>() {
						public void handle(Event e){
							devCommandesAssignees.set(commandesAssignees.getChildren().indexOf(commandePane), !devCommandesAssignees.get(commandesAssignees.getChildren().indexOf(commandePane)));
							if(devCommandesAssignees.get(commandesAssignees.getChildren().indexOf(commandePane))){
								commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_DEVELOPPEE + HAUTEUR_AJOUT_BOUTONS);
								contenusCommandesAssignees.get(commandesAssignees.getChildren().indexOf(commandePane)).setVisible(true);
							} else {
								commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE + HAUTEUR_AJOUT_BOUTONS);
								contenusCommandesAssignees.get(commandesAssignees.getChildren().indexOf(commandePane)).setVisible(false);
							}}});

					retirer.setOnAction(new EventHandler<ActionEvent>(){
						public void handle(ActionEvent a){
							core.getService().retirerCommande(commande);
							EcranConfection.mettreEcransAJour(core);
							Selection.refreshCompteurBaguettes();
						}
					});

					boutonRealisee.setOnAction(new EventHandler<ActionEvent>(){
						public void handle(ActionEvent a){
							commande.realisee(core.getService());
							EcranConfection.mettreEcransAJour(core);
						}
					});

					boutonDonnee.setOnAction(new EventHandler<ActionEvent>(){
						public void handle(ActionEvent a){
							commande.donnee(core.getService());
						}
					});

					commandesAssignees.getChildren().add(commandePane);
				}
			}
		});

		//###################################################################

		core.getService().nouvelleCommandeRealiseePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				CommandeAssignee commande = (newVal != null) ? (CommandeAssignee)newVal : null;
				if(commande != null){
					int n = -1;
					for(Integer numero : listeCommandesAssignees){
						if(numero == commande.getNumero()){
							commandesAssignees.getChildren().remove(listeCommandesAssignees.indexOf(numero));
							devCommandesAssignees.remove(listeCommandesAssignees.indexOf(numero));
							contenusCommandesAssignees.remove(listeCommandesAssignees.indexOf(numero));
							n = listeCommandesAssignees.indexOf(numero);
						}
					}

					if(n != -1){
						listeCommandesAssignees.remove(n);
					}

					listeCommandesRealisees.add(commande.getNumero());

					AnchorPane commandePane = new AnchorPane();
					commandePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
					commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE + HAUTEUR_AJOUT_BOUTONS);
					commandePane.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
					commandePane.getStyleClass().add(COMMANDE);
			
					Label numero = new Label("" + commande.getNumero());
					numero.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
					numero.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
					numero.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
					numero.getStyleClass().add(App.NUMERO_COMMANDE_REALISEE);
			
					Label plat = new Label(commande.getPlat().getNom().toUpperCase());
					plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
					plat.setMinHeight(Control.USE_PREF_SIZE);
					plat.setMaxHeight(Control.USE_PREF_SIZE);
					plat.setMaxWidth(Double.MAX_VALUE);
					plat.getStyleClass().add(App.PLAT_COMMANDE);
					plat.getStyleClass().add(PLAT_LISTE_COMMANDES);

					Label confection = new Label();
					confection.setText(commande.getMembre().getBlazeCourt());
			
					VBox contenuCommande = new VBox();
					contenuCommande.setVisible(false);
			
					Label lbIngredients = lbIngredients(commande);
					lbIngredients.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 36);
					Label lbSauces = lbSauces(commande);
					lbSauces.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
					Label lbBoisson = new Label();
					if(commande.getSupplementBoisson().getId().equals("fa03180b-95ad-4a5b-84f2-cbdc2beae920")){
						lbBoisson.setText(commande.getBoisson().getNom());
					} else {
						lbBoisson.setText(commande.getBoisson().getNom() + " + " + commande.getSupplementBoisson().getNom());
					}
					lbBoisson.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
					Label lbDessert = new Label(commande.getDessert().getNom());
					lbDessert.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
			
					Button retirer = new Button("Retirer");
					
					contenuCommande.getChildren().addAll(lbIngredients, lbSauces, lbBoisson, lbDessert, retirer);
					contenusCommandesRealisees.add(contenuCommande);
					devCommandesRealisees.add(false);

					Button boutonDonnee = new Button("Donnée");

					AnchorPane.setTopAnchor(numero, -1.0);
					AnchorPane.setLeftAnchor(numero, -1.0);
					AnchorPane.setTopAnchor(plat, 0.0);
					AnchorPane.setLeftAnchor(plat, App.TAILLE_NUMERO_COMMANDE + App.ESPACE_NUMERO_PLAT);
					AnchorPane.setTopAnchor(confection, 0.0);
					AnchorPane.setRightAnchor(confection, 2.0);
					AnchorPane.setTopAnchor(contenuCommande, App.TAILLE_NUMERO_COMMANDE);
					AnchorPane.setLeftAnchor(contenuCommande, 3.0);
					AnchorPane.setRightAnchor(boutonDonnee, 50.0);
					AnchorPane.setBottomAnchor(boutonDonnee, 7.0);

					commandePane.getChildren().add(numero);
					commandePane.getChildren().add(plat);
					commandePane.getChildren().add(confection);
					commandePane.getChildren().add(contenuCommande);
					commandePane.getChildren().add(boutonDonnee);

					plat.setOnMouseClicked(new EventHandler<Event>() {
						public void handle(Event e){
							devCommandesRealisees.set(commandesRealisees.getChildren().indexOf(commandePane), !devCommandesRealisees.get(commandesRealisees.getChildren().indexOf(commandePane)));
							if(devCommandesRealisees.get(commandesRealisees.getChildren().indexOf(commandePane))){
								commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_DEVELOPPEE + HAUTEUR_AJOUT_BOUTONS);
								contenusCommandesRealisees.get(commandesRealisees.getChildren().indexOf(commandePane)).setVisible(true);
							} else {
								commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE + HAUTEUR_AJOUT_BOUTONS);
								contenusCommandesRealisees.get(commandesRealisees.getChildren().indexOf(commandePane)).setVisible(false);
							}}});

					retirer.setOnAction(new EventHandler<ActionEvent>(){
						public void handle(ActionEvent a){
							core.getService().retirerCommande(commande);
							EcranConfection.mettreEcransAJour(core);
							Selection.refreshCompteurBaguettes();
						}
					});

					boutonDonnee.setOnAction(new EventHandler<ActionEvent>(){
						public void handle(ActionEvent a){
							commande.donnee(core.getService());
						}
					});

					commandesRealisees.getChildren().add(commandePane);
				}
			}
		});

		//###################################################################

		core.getService().nouvelleCommandeDonneePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				CommandeAssignee commande = (newVal != null) ? (CommandeAssignee)newVal : null;
				if(commande != null){
					int n = -1;
					for(Integer numero : listeCommandesAssignees){
						if(numero == commande.getNumero()){
							commandesAssignees.getChildren().remove(listeCommandesAssignees.indexOf(numero));
							devCommandesAssignees.remove(listeCommandesAssignees.indexOf(numero));
							contenusCommandesAssignees.remove(listeCommandesAssignees.indexOf(numero));
							n = listeCommandesAssignees.indexOf(numero);
						}
					}

					if(n != -1){
						listeCommandesAssignees.remove(n);
					}

					int m = -1;
					for(Integer numero : listeCommandesRealisees){
						if(numero == commande.getNumero()){
							commandesRealisees.getChildren().remove(listeCommandesRealisees.indexOf(numero));
							devCommandesRealisees.remove(listeCommandesRealisees.indexOf(numero));
							contenusCommandesRealisees.remove(listeCommandesRealisees.indexOf(numero));
							m = listeCommandesRealisees.indexOf(numero);
						}
					}

					if(m != 1){
						listeCommandesRealisees.remove(m);
					}

					listeCommandesDonnees.add(commande.getNumero());

					AnchorPane commandePane = new AnchorPane();
					commandePane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
					commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE);
					commandePane.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
					commandePane.getStyleClass().add(COMMANDE);
			
					Label numero = new Label("" + commande.getNumero());
					numero.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
					numero.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
					numero.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
					numero.getStyleClass().add(App.NUMERO_COMMANDE_DONNEE);
			
					Label plat = new Label(commande.getPlat().getNom().toUpperCase());
					plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
					plat.setMinHeight(Control.USE_PREF_SIZE);
					plat.setMaxHeight(Control.USE_PREF_SIZE);
					plat.setMaxWidth(Double.MAX_VALUE);
					plat.getStyleClass().add(App.PLAT_COMMANDE);
					plat.getStyleClass().add(PLAT_LISTE_COMMANDES);

					Label confection = new Label();
					confection.setText(commande.getMembre().getBlazeCourt());
			
					VBox contenuCommande = new VBox();
					contenuCommande.setVisible(false);
			
					Label lbIngredients = lbIngredients(commande);
					lbIngredients.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 36);
					Label lbSauces = lbSauces(commande);
					lbSauces.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
					Label lbBoisson = new Label();
					if(commande.getSupplementBoisson().getId().equals("fa03180b-95ad-4a5b-84f2-cbdc2beae920")){
						lbBoisson.setText(commande.getBoisson().getNom());
					} else {
						lbBoisson.setText(commande.getBoisson().getNom() + " + " + commande.getSupplementBoisson().getNom());
					}
					lbBoisson.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
					Label lbDessert = new Label(commande.getDessert().getNom());
					lbDessert.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
			
					Button retirer = new Button("Retirer");
					
					contenuCommande.getChildren().addAll(lbIngredients, lbSauces, lbBoisson, lbDessert, retirer);
					contenusCommandesDonnees.add(contenuCommande);
					devCommandesDonnees.add(false);

					AnchorPane.setTopAnchor(numero, -1.0);
					AnchorPane.setLeftAnchor(numero, -1.0);
					AnchorPane.setTopAnchor(plat, 0.0);
					AnchorPane.setLeftAnchor(plat, App.TAILLE_NUMERO_COMMANDE + App.ESPACE_NUMERO_PLAT);
					AnchorPane.setTopAnchor(confection, 0.0);
					AnchorPane.setRightAnchor(confection, 2.0);
					AnchorPane.setTopAnchor(contenuCommande, App.TAILLE_NUMERO_COMMANDE);
					AnchorPane.setLeftAnchor(contenuCommande, 3.0);

					commandePane.getChildren().add(numero);
					commandePane.getChildren().add(plat);
					commandePane.getChildren().add(confection);
					commandePane.getChildren().add(contenuCommande);

					plat.setOnMouseClicked(new EventHandler<Event>() {
						public void handle(Event e){
							devCommandesDonnees.set(commandesDonnees.getChildren().indexOf(commandePane), !devCommandesDonnees.get(commandesDonnees.getChildren().indexOf(commandePane)));
							if(devCommandesDonnees.get(commandesDonnees.getChildren().indexOf(commandePane))){
								commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_DEVELOPPEE);
								contenusCommandesDonnees.get(commandesDonnees.getChildren().indexOf(commandePane)).setVisible(true);
							} else {
								commandePane.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE);
								contenusCommandesDonnees.get(commandesDonnees.getChildren().indexOf(commandePane)).setVisible(false);
							}}});

					retirer.setOnAction(new EventHandler<ActionEvent>(){
						public void handle(ActionEvent a){
							core.getService().retirerCommande(commande);
							EcranConfection.mettreEcransAJour(core);
							Selection.refreshCompteurBaguettes();
						}
					});

					commandesDonnees.getChildren().add(commandePane);
				}
			}
		});

		//###################################################################

		core.getService().nouvelleCommandeRetireePropriete().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				Commande commande = (newVal != null) ? (Commande)newVal : null;
				if(commande != null){
					int n = -1;
					for(Integer numero : listeCommandesAjoutees){
						if(numero == commande.getNumero()){
							commandesAjoutees.getChildren().remove(listeCommandesAjoutees.indexOf(numero));
							devCommandesAjoutees.remove(listeCommandesAjoutees.indexOf(numero));
							contenusCommandesAjoutees.remove(listeCommandesAjoutees.indexOf(numero));
							n = listeCommandesAjoutees.indexOf(numero);
						}
					}

					if(n != -1){
						listeCommandesAjoutees.remove(n);
					}

					int m = -1;
					for(Integer numero : listeCommandesAssignees){
						if(numero == commande.getNumero()){
							commandesAssignees.getChildren().remove(listeCommandesAssignees.indexOf(numero));
							devCommandesAssignees.remove(listeCommandesAssignees.indexOf(numero));
							contenusCommandesAssignees.remove(listeCommandesAssignees.indexOf(numero));
							m = listeCommandesAssignees.indexOf(numero);
						}
					}

					if(m != -1){
						listeCommandesAssignees.remove(m);
					}

					int l = -1;
					for(Integer numero : listeCommandesRealisees){
						if(numero == commande.getNumero()){
							commandesRealisees.getChildren().remove(listeCommandesRealisees.indexOf(numero));
							devCommandesRealisees.remove(listeCommandesRealisees.indexOf(numero));
							contenusCommandesRealisees.remove(listeCommandesRealisees.indexOf(numero));
							l = listeCommandesRealisees.indexOf(numero);
						}
					}

					if(l != -1){
						listeCommandesRealisees.remove(l);
					}

					int k = -1;
					for(Integer numero : listeCommandesDonnees){
						if(numero == commande.getNumero()){
							commandesDonnees.getChildren().remove(listeCommandesDonnees.indexOf(numero));
							devCommandesDonnees.remove(listeCommandesDonnees.indexOf(numero));
							contenusCommandesDonnees.remove(listeCommandesDonnees.indexOf(numero));
							k = listeCommandesDonnees.indexOf(numero);
						}
					}

					if(k != -1){
						listeCommandesDonnees.remove(k);
					}
				}
			}});

		return(panneauCommandes);
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
