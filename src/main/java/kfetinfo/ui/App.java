package kfetinfo.ui;

import java.util.Locale;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import kfetinfo.core.Core;

/**
 * <p>App est la classe principale du logiciel. Elle hérite de {@code Application}, du package {@code javafx.application}. C'est ici que tout commence, que le node racine du SceneGraph est créé et que l'instruction est donnée au Core de démarrer la base de données.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public final class App extends Application{

	//le node racine du SceneGraph
	private static BorderPane root;

	//classes de style pour l'utilisation du CSS
	public static final String NUMERO_COMMANDE_AJOUTEE = "numero-commande-ajoutee";
	public static final String NUMERO_COMMANDE_ASSIGNEE = "numero-commande-assignee";
	public static final String NUMERO_COMMANDE_REALISEE = "numero-commande-realisee";
	public static final String NUMERO_COMMANDE_DONNEE = "numero-commande-donnee";
	public static final String PLAT_COMMANDE = "plat-commande";

	//constantes pour l'affichage
	public static final Double LARGEUR_MIN_FENETRE = 800.0;
	public static final Double HAUTEUR_MIN_FENETRE = 600.0;
	public static final Double TAILLE_PANNEAU_MENU = 25.0;
	public static final Double TAILLE_PANNEAU_COMMANDES = 250.0;
	public static final Double TAILLE_PANNEAU_RESULTAT = 173.0;
	public static final Double TAILLE_NUMERO_COMMANDE = 30.0;
	public static final Double ESPACE_NUMERO_PLAT = 5.0;

	/**
	 * Première méthode appelée par le programme. On démarre l'interface graphique. Il est déconseillé d'y mettre autre chose.
	 * 
	 * @param args les arguments au cas où le logiciel soit lancé en lignes de commande ou avec « Ouvrir avec… ».
	 */
	public static void main(String[] args){
		launch(args);
	}

	/**
	 * Crée une nouvelle fenêtre avec l'écran principal du logiciel. {@code launch(args}} l'appelle automatiquement au démarrage de l'interface graphique.
	 */
	public void start(Stage theatre){

		Locale.setDefault(Locale.FRANCE);
		Locale.setDefault(Locale.FRENCH);

		Core core = new Core(); //on démarre le système K'Fet et la base de données

		root = null;

		try {
			root = ecranPrincipal(core, theatre);
		} catch (Exception e){
			e.printStackTrace();
		}

		theatre.setTitle("K'Fet Info");

		theatre.setMinWidth(LARGEUR_MIN_FENETRE + 16); //je sais pas pourquoi mais il faut ajouter ce nombre de pixels à la taille de la fenêtre sinon on peut redimensionner en dessous des tailles standard
		theatre.setMinHeight(HAUTEUR_MIN_FENETRE + 39);

		Scene scene = new Scene(root, LARGEUR_MIN_FENETRE, HAUTEUR_MIN_FENETRE);

		theatre.setScene(scene);

		scene.getStylesheets().add(this.getClass().getResource("../../Interface/Stylesheets/style.css").toExternalForm());

		theatre.show();
	}

	/**
	 * <p>Renvoie un {@code BorderPane} avec tous les éléments de la fenêtre principale du logiciel :
	 * <ul><li>la partie sélection de la commande,</li>
	 * <li>la partie liste des commandes,</li>
	 * <li>la partie résultat (prévisualisation de la commande, gestion de la caisse, sélection de l'équipe), et</li>
	 * <li>le menu permettant de faire apparaître les autres fenêtres du logiciel (confection, stocks, menu, administration, graphiques).</li></ul></p>
	 * 
	 * @param core le core du système K'Fet.
	 * @param theatre la fenêtre principale du logiciel.
	 * 
	 * @return le panneau de l'écran principal.
	 */
	private static final BorderPane ecranPrincipal(Core core, Stage theatre){

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

	/**
	 * Recrée le panneau sélection au cas où il y ait eu des modifications de la base de données des contenus commandes.
	 * 
	 * @param core le core du système K'Fet.
	 */
	public static final void mettreSelectionAJour(Core core){

		root.setCenter(Selection.selection(root, core));
		Selection.resetSelection();
	}
}
