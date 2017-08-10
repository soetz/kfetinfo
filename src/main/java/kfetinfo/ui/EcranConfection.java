package kfetinfo.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kfetinfo.core.Commande;
import kfetinfo.core.CommandeAssignee;
import kfetinfo.core.Core;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Membre;
import kfetinfo.core.Sauce;

public class EcranConfection {
	public static List<HBox> listeConfection = new ArrayList<HBox>();

	public static void ecranConfection(Core core, Stage ecranPrincipal){
		BorderPane ecran = new BorderPane();

		Label titre = new Label("En cours de confection…");
		ecran.setTop(titre);

		HBox confection = new HBox();
		confection.minWidthProperty().bind(ecran.widthProperty());
		confection.maxWidthProperty().bind(confection.minWidthProperty());
		listeConfection.add(confection);

		mettreEcransAJour(core);

		ecran.setCenter(confection);

		Scene scene = new Scene(ecran, App.LARGEUR_MIN_FENETRE, App.HAUTEUR_MIN_FENETRE);
		Stage theatre = new Stage();
		theatre.setMinWidth(App.LARGEUR_MIN_FENETRE + 16);
		theatre.setMinHeight(App.HAUTEUR_MIN_FENETRE + 39);
		theatre.setTitle("Écran de confection des commandes");
		theatre.setScene(scene);
		theatre.show();

		ecranPrincipal.showingProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(ecranPrincipal.isShowing()==false){
					theatre.close();
				}
			}
		});

		theatre.showingProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(theatre.isShowing()==false){
					listeConfection.remove(confection);
				}
			}
		});
	}

	public static Region commandeRealisation(Core core, int numero){
		VBox commandeBox = new VBox();

		Membre membre = core.getService().getConfection().get(numero);

		CommandeAssignee commande = null;

		for(Commande commandeBoucle : core.getService().getCommandes()){
			if(commandeBoucle instanceof CommandeAssignee){
				CommandeAssignee commandeAssignee = (CommandeAssignee)commandeBoucle;
				if(commandeAssignee.getMembre().equals(membre)){
					if(!commandeAssignee.getEstRealisee()){
						commande = commandeAssignee;
					}
				}
			}
		}

		Label nomMembre = new Label(membre.getBlaze());
		commandeBox.getChildren().add(nomMembre);

		if(commande != null){

			HBox numeroEtPlat = new HBox();

			Label numeroCommande = new Label("" + commande.getNumero());
			numeroCommande.setPrefSize(App.TAILLE_NUMERO_COMMANDE, App.TAILLE_NUMERO_COMMANDE);
			numeroCommande.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
			numeroCommande.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
			numeroCommande.getStyleClass().add(App.NUMERO_COMMANDE);

			Label plat = new Label(commande.getPlat().getNom().toUpperCase());
			plat.setPrefHeight(App.TAILLE_NUMERO_COMMANDE);
			plat.setMinHeight(Control.USE_PREF_SIZE);
			plat.setMaxHeight(Control.USE_PREF_SIZE);
			plat.getStyleClass().add(App.PLAT_COMMANDE);

			numeroEtPlat.getChildren().add(numeroCommande);
			numeroEtPlat.getChildren().add(plat);

			commandeBox.getChildren().add(numeroEtPlat);

			Label sauces = new Label("Sauces : ");
			commandeBox.getChildren().add(sauces);

			for(Sauce sauce : commande.getSauces()){
				Label label = new Label(sauce.getNom());
				commandeBox.getChildren().add(label);
			}

			Label ingredients = new Label("Ingredients : ");
			commandeBox.getChildren().add(ingredients);

			for(Ingredient ingredient : commande.getIngredients()){
				Label label = new Label(ingredient.getNom());
				commandeBox.getChildren().add(label);
			}

			Label boisson = new Label("Boisson : ");
			Label boissonLabel = new Label(commande.getBoisson().getNom() + " + " + commande.getSupplementBoisson().getNom());

			commandeBox.getChildren().add(boisson);
			commandeBox.getChildren().add(boissonLabel);

			Label dessert = new Label("Dessert : ");
			Label dessertLabel = new Label(commande.getDessert().getNom());

			commandeBox.getChildren().add(dessert);
			commandeBox.getChildren().add(dessertLabel);

		} else {
			Label rien = new Label("Ce membre ne réalise actuellement aucune commande");

			commandeBox.getChildren().add(rien);
		}

		return(commandeBox);
	}

	public static void mettreEcransAJour(Core core){
		for(HBox confection : listeConfection){
			confection.getChildren().clear();

			int i;
			for(i = 0; i < core.getService().getConfection().size(); i++){
				confection.getChildren().add(commandeRealisation(core, i));
			}
		}
	}
}
