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
import kfetinfo.core.Core;

public class Commandes {
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

		Button bouton = new Button("Ajouter");

		bouton.setOnAction(new EventHandler<ActionEvent>() {
			
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

		commandes.getChildren().add(bouton);

//		List<Commande> listeCommandes = core.getService().getCommandes();

//		int i;
//		for(i = 0; i < 4; i++){
//			Region region = new Region();
//			region.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//			region.setPrefSize(App.TAILLE_PANNEAU_COMMANDES - 15, HAUTEUR_COMMANDE_FERMEE);
//			region.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
//			region.getStyleClass().add("commande");
//			commandes.getChildren().add(region);
//		}

//		Label droite = new Label("Commandes");
//		droite.setId("droite");
//		droite.getStyleClass().add(App.SHOWCASE);
//		droite.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//		droite.setMinWidth(App.TAILLE_PANNEAU_COMMANDES);

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
		commandePane.getStyleClass().add("commande");

		Label numero = new Label("" + commande.getNumero());
		numero.setPrefSize(30.0, 30.0);
		numero.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		numero.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		numero.getStyleClass().add("numero-commande");

		AnchorPane.setTopAnchor(numero, 0.0);
		AnchorPane.setLeftAnchor(numero, 0.0);

		commandePane.getChildren().add(numero);

		commandes.getChildren().add(commandePane);
	}
}
