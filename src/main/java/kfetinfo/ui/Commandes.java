package kfetinfo.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

/**
 * <p>Commandes est une classe constituée uniquement d'attributs et de méthodes statiques relatifs à l'affichage de la liste des commandes du service.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public final class Commandes {

	//classes de style pour l'utilisation du CSS
	public static final String COMMANDE = "commande";
	public static final String PANNEAU_COMMANDES = "panneau-commandes";
	public static final String PLAT_LISTE_COMMANDES = "plat-liste-commandes";

	//constantes pour l'affichage
	private static final Double HAUTEUR_COMMANDE_FERMEE = 30.0;
	private static final Double HAUTEUR_COMMANDE_DEVELOPPEE = 126.0;
	private static final Double HAUTEUR_AJOUT_BOUTONS = 36.0;

	//VBox de commandes dans chaque état
	private static VBox commandesRealisees = new VBox();
	private static VBox commandesAssignees = new VBox();
	private static VBox commandesAjoutees = new VBox();
	private static VBox commandesDonnees = new VBox();

	//listes des commandes des listes de chaque état
	private static List<Integer> listeCommandesRealisees = new ArrayList<Integer>();
	private static List<Integer> listeCommandesAssignees = new ArrayList<Integer>();
	private static List<Integer> listeCommandesAjoutees = new ArrayList<Integer>();
	private static List<Integer> listeCommandesDonnees = new ArrayList<Integer>();

	//listes des commandes développées pour chaque état
	private static List<Boolean> devCommandesRealisees = new ArrayList<Boolean>();
	private static List<Boolean> devCommandesAssignees = new ArrayList<Boolean>();
	private static List<Boolean> devCommandesAjoutees = new ArrayList<Boolean>();
	private static List<Boolean> devCommandesDonnees = new ArrayList<Boolean>();

	//listes des contenus de commandes pour chaque état (afin de les développer)
	private static List<VBox> contenusCommandesRealisees = new ArrayList<VBox>();
	private static List<VBox> contenusCommandesAssignees = new ArrayList<VBox>();
	private static List<VBox> contenusCommandesAjoutees = new ArrayList<VBox>();
	private static List<VBox> contenusCommandesDonnees = new ArrayList<VBox>();

	/**
	 * Crée une {@code Region} permettant d'afficher la liste des commandes formulées pendant le service ainsi que leurs état respectifs.
	 * 
	 * @param core le core du système K'Fet.
	 * 
	 * @return la liste des commandes.
	 */
	public static final Region commandes(Core core){

		ScrollPane panneauCommandes = new ScrollPane();
		panneauCommandes.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		panneauCommandes.setMinWidth(App.TAILLE_PANNEAU_COMMANDES);
		panneauCommandes.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		panneauCommandes.setHbarPolicy(ScrollBarPolicy.NEVER);
		panneauCommandes.getStyleClass().add(PANNEAU_COMMANDES);

		for(Commande commande : Core.getService().getCommandes()){ //pour chaque commande,
			if(commande instanceof CommandeAssignee){
				CommandeAssignee commandeAssignee = (CommandeAssignee)commande;
				if(commandeAssignee.getEstRealisee()){
					if(commandeAssignee.getEstDonnee()){ //si la commande est donnée,
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
						confection.setText(commandeAssignee.getMembre().getBlazeCourt()); //on inscrit le blaze court de la personne à qui elle était affectée
				
						VBox contenuCommande = new VBox();
						contenuCommande.setVisible(false);
				
						Label lbIngredients = lbIngredients(commandeAssignee);
						lbIngredients.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 36);
						Label lbSauces = lbSauces(commandeAssignee);
						lbSauces.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
						Label lbBoisson = new Label();
						if(commandeAssignee.getSupplementBoisson().getId().equals(BaseDonnees.ID_RIEN_SUPPLEMENT_BOISSON)){
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
								Core.getService().retirerCommande(commande);
								EcranConfection.mettreEcransAJour();
								Selection.refreshCompteurBaguettes();
							}
						});

						commandesDonnees.getChildren().add(commandePane);
					} else { //sinon si la commande est réalisée,
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
						confection.setText(commandeAssignee.getMembre().getBlazeCourt()); //on inscrit le blaze court de la personne à qui elle était affectée
				
						VBox contenuCommande = new VBox();
						contenuCommande.setVisible(false);
				
						Label lbIngredients = lbIngredients(commandeAssignee);
						lbIngredients.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 36);
						Label lbSauces = lbSauces(commandeAssignee);
						lbSauces.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
						Label lbBoisson = new Label();
						if(commandeAssignee.getSupplementBoisson().getId().equals(BaseDonnees.ID_RIEN_SUPPLEMENT_BOISSON)){
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

						//et on ajoute un bouton pour indiquer que la commande a été donnée
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
								Core.getService().retirerCommande(commande);
								EcranConfection.mettreEcransAJour();
								Selection.refreshCompteurBaguettes();
							}
						});

						boutonDonnee.setOnAction(new EventHandler<ActionEvent>(){
							public void handle(ActionEvent a){
								commandeAssignee.donnee(Core.getService());
							}
						});

						commandesRealisees.getChildren().add(commandePane);
					}
				} else { //sinon si la commande est assignée,
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
					confection.setText(commandeAssignee.getMembre().getBlazeCourt()); //on inscrit le blaze court de la personne à qui elle est affectée
			
					VBox contenuCommande = new VBox();
					contenuCommande.setVisible(false);
			
					Label lbIngredients = lbIngredients(commandeAssignee);
					lbIngredients.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 36);
					Label lbSauces = lbSauces(commandeAssignee);
					lbSauces.setMaxWidth(App.TAILLE_PANNEAU_COMMANDES - 6);
					Label lbBoisson = new Label();
					if(commandeAssignee.getSupplementBoisson().getId().equals(BaseDonnees.ID_RIEN_SUPPLEMENT_BOISSON)){
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

					//et on ajoute un bouton pour indiquer que la commande est réalisée, et un autre pour indiquer qu'elle est donnée
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
							Core.getService().retirerCommande(commande);
							EcranConfection.mettreEcransAJour();
							Selection.refreshCompteurBaguettes();
						}
					});

					boutonRealisee.setOnAction(new EventHandler<ActionEvent>(){
						public void handle(ActionEvent a){
							commandeAssignee.realisee(Core.getService());
							EcranConfection.mettreEcransAJour();
						}
					});

					boutonDonnee.setOnAction(new EventHandler<ActionEvent>(){
						public void handle(ActionEvent a){
							commandeAssignee.donnee(Core.getService());
						}
					});

					commandesAssignees.getChildren().add(commandePane);
				}
			} else { //sinon alors la commande est simplement ajoutée
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
				if(commande.getSupplementBoisson().getId().equals(BaseDonnees.ID_RIEN_SUPPLEMENT_BOISSON)){
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
						Core.getService().retirerCommande(commande);
						EcranConfection.mettreEcransAJour();
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

		//si une nouvelle commande est ajoutée, on l'ajoute à la liste des nouvelles commandes
		Core.getService().nouvelleCommandePropriete().addListener(new ChangeListener() {
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
				if(commande.getSupplementBoisson().getId().equals(BaseDonnees.ID_RIEN_SUPPLEMENT_BOISSON)){
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
						EcranConfection.mettreEcransAJour();
						Selection.refreshCompteurBaguettes();
					}
				});

				commandesAjoutees.getChildren().add(commandePane);
			}
		});

		//si une commande est nouvellement assignée, on la retire de la retire de la liste des commandes ajoutées et on l'ajoute à la liste des commandes assignées
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
					if(commande.getSupplementBoisson().getId().equals(BaseDonnees.ID_RIEN_SUPPLEMENT_BOISSON)){
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
							EcranConfection.mettreEcransAJour();
							Selection.refreshCompteurBaguettes();
						}
					});

					boutonRealisee.setOnAction(new EventHandler<ActionEvent>(){
						public void handle(ActionEvent a){
							commande.realisee(core.getService());
							EcranConfection.mettreEcransAJour();
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

		//si une commande est nouvellement réalisée, on la retire de la retire de la liste des commandes assignées et on l'ajoute à la liste des commandes réalisées
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
					if(commande.getSupplementBoisson().getId().equals(BaseDonnees.ID_RIEN_SUPPLEMENT_BOISSON)){
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
							EcranConfection.mettreEcransAJour();
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

		//si une commande est nouvellement donnée, on la retire de la retire de la liste des commandes assignées ou de celle des commandes réalisées et on l'ajoute à la liste des commandes données
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
					if(commande.getSupplementBoisson().getId().equals(BaseDonnees.ID_RIEN_SUPPLEMENT_BOISSON)){
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
							EcranConfection.mettreEcransAJour();
							Selection.refreshCompteurBaguettes();
						}
					});

					commandesDonnees.getChildren().add(commandePane);
				}
			}
		});

		//si une commande est nouvellement retirée, on la retire de la retire de toutes les listes
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

	/**
	 * Renvoie un {@code Label} affichant la liste des ingrédients de la commande passée en paramètres sur une ligne.
	 * 
	 * @param commande la commande dont on souhaite extraire la liste des ingrédients.
	 * 
	 * @return un label avec la liste des ingrédients sur une ligne.
	 */
	private static final Label lbIngredients(Commande commande){

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

	/**
	 * Renvoie un {@code Label} affichant la liste des sauces de la commande passée en paramètres sur une ligne.
	 * 
	 * @param commande la commande dont on souhaite extraire la liste des sauces.
	 * 
	 * @return un label avec la liste des sauces sur une ligne.
	 */
	private static final Label lbSauces(Commande commande){
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
