package kfetinfo.ui;

import java.util.Date;
import java.util.Locale;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kfetinfo.core.Core;
import kfetinfo.core.Dessert;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Plat;
import kfetinfo.core.Sauce;
import kfetinfo.core.BaseDonnees;
import kfetinfo.core.Boisson;
import kfetinfo.core.Commande;
import kfetinfo.core.CommandeAssignee;


public class App extends Application{
	public static BorderPane root;

	public static final String NUMERO_COMMANDE_AJOUTEE = "numero-commande-ajoutee";
	public static final String NUMERO_COMMANDE_ASSIGNEE = "numero-commande-assignee";
	public static final String NUMERO_COMMANDE_REALISEE = "numero-commande-realisee";
	public static final String NUMERO_COMMANDE_DONNEE = "numero-commande-donnee";
	
	public static final String PLAT_COMMANDE = "plat-commande";

	public static final Double LARGEUR_MIN_FENETRE = 800.0;
	public static final Double HAUTEUR_MIN_FENETRE = 600.0;
	public static final Double TAILLE_PANNEAU_MENU = 25.0;
	public static final Double TAILLE_PANNEAU_COMMANDES = 250.0;
	public static final Double TAILLE_PANNEAU_RESULTAT = 173.0;
	public static final Double TAILLE_NUMERO_COMMANDE = 30.0;
	public static final Double ESPACE_NUMERO_PLAT = 5.0;

	public static void main(String[] args){
		launch(args);
	}

	public void start(Stage theatre){
		Locale.setDefault(Locale.FRENCH);
		Locale.setDefault(Locale.FRANCE);

		Core core = new Core();

		root = null;

		try {
			root = ecranPrincipal(core, theatre);
		} catch (Exception e){
			e.printStackTrace();
		}

		theatre.setTitle("K'Fet Info");

		theatre.setMinWidth(LARGEUR_MIN_FENETRE + 16);
		theatre.setMinHeight(HAUTEUR_MIN_FENETRE + 39);

		Scene scene = new Scene(root, LARGEUR_MIN_FENETRE, HAUTEUR_MIN_FENETRE);

		theatre.setScene(scene);

		scene.getStylesheets().add(this.getClass().getResource("../../Interface/Stylesheets/style.css").toExternalForm());

		theatre.show();
	}

	public BorderPane ecranPrincipal(Core core, Stage theatre){
		BorderPane racine = new BorderPane();

		Region haut = Menu.menu(core, theatre);

		Region droite = Commandes.commandes(core);

		Region centre = Selection.selection(racine, core);

		Region bas = Resultat.resultat(racine, core);

		racine.setTop(haut);
		racine.setRight(droite);
		racine.setBottom(bas);
		racine.setCenter(centre);
		
		return(racine);
	}

	public static void mettreSelectionAJour(Core core){
		root.setCenter(Selection.selection(root, core));
		Selection.resetSelection();
	}
}
