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

import kfetinfo.core.Commande;
import kfetinfo.core.CommandeAssignee;
import kfetinfo.core.Core;
import kfetinfo.core.Membre;

import java.io.File;
import java.util.Locale;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

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
	public static final String BOUTON = "bouton";
	public static final String CHECKBOX = "checkbox";
	public static final String SELECTION_BOX = "selection-box";
	public static final String SELECTION_ELEMENT_LABEL = "selection-element-label";
	public static final String SELECTION_GAUCHE_ELEMENT_S = "selection-gauche-element";
	public static final String FIELD = "field";
	public static final String SPINNER = "cus-spinner";
	public static final String LISTVIEW = "listview";
	public static final String CODE = "code";
	private static final String ROOT = "root";
	private static final String FOND = "fond";

	//constantes pour l'affichage
	public static final Double LARGEUR_MIN_FENETRE = 800.0;
	public static final Double HAUTEUR_MIN_FENETRE = 700.0;
	public static final Double TAILLE_PANNEAU_MENU = 25.0;
	public static final Double TAILLE_PANNEAU_COMMANDES = 250.0;
	public static final Double TAILLE_PANNEAU_RESULTAT = 195.0;
	public static final Double TAILLE_NUMERO_COMMANDE = 38.0;
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

		@SuppressWarnings("unused")
		Core core = new Core(); //on démarre le système K'Fet et la base de données

		root = null;

		try {
			root = ecranPrincipal(theatre);
		} catch (Exception e){
			e.printStackTrace();
		}

		theatre.setTitle("K'Fet Info");

		try {
			theatre.getIcons().setAll(new Image("file:" + Core.recupererFichier("/Interface/Images/Icons/App/AppIcon_16.png").getAbsolutePath()),
					new Image("file:" + Core.recupererFichier("/Interface/Images/Icons/App/AppIcon_24.png").getAbsolutePath()),
					new Image("file:" + Core.recupererFichier("/Interface/Images/Icons/App/AppIcon_32.png").getAbsolutePath()),
					new Image("file:" + Core.recupererFichier("/Interface/Images/Icons/App/AppIcon_48.png").getAbsolutePath()),
					new Image("file:" + Core.recupererFichier("/Interface/Images/Icons/App/AppIcon_64.png").getAbsolutePath()),
					new Image("file:" + Core.recupererFichier("/Interface/Images/Icons/App/AppIcon_128.png").getAbsolutePath()),
					new Image("file:" + Core.recupererFichier("/Interface/Images/Icons/App/AppIcon_256.png").getAbsolutePath()),
					new Image("file:" + Core.recupererFichier("/Interface/Images/Icons/App/AppIcon_512.png").getAbsolutePath()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		theatre.setMinWidth(LARGEUR_MIN_FENETRE + 16); //je sais pas pourquoi mais il faut ajouter ce nombre de pixels à la taille de la fenêtre sinon on peut redimensionner en dessous des tailles standard
		theatre.setMinHeight(HAUTEUR_MIN_FENETRE + 39);

		Scene scene = new Scene(root, LARGEUR_MIN_FENETRE, HAUTEUR_MIN_FENETRE);

		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> { //gestion de l'appui par les confection sur leurs touches pour indiquer la réalisation d'une commande
			if(key.getCode()==KeyCode.W) {
				if(Core.getService().getConfection().size() >= 1){
					Membre confection = Core.getService().getConfection().get(0);
					CommandeAssignee commandeARealiser = null;
					for(Commande commande : Core.getService().getCommandes()){
						if(commande instanceof CommandeAssignee){
							CommandeAssignee commandeAssignee = (CommandeAssignee)commande;
							if((commandeAssignee.getMembre().equals(confection))&&(!commandeAssignee.getEstRealisee())){
								commandeARealiser = commandeAssignee;
							}
						}
					}

					commandeARealiser.realisee(Core.getService());
				}
			}
		});

		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> { //gestion de l'appui par les confection sur leurs touches pour indiquer la réalisation d'une commande
			if(key.getCode()==KeyCode.V) {
				if(Core.getService().getConfection().size() >= 2){
					Membre confection = Core.getService().getConfection().get(1);
					CommandeAssignee commandeARealiser = null;
					for(Commande commande : Core.getService().getCommandes()){
						if(commande instanceof CommandeAssignee){
							CommandeAssignee commandeAssignee = (CommandeAssignee)commande;
							if((commandeAssignee.getMembre().equals(confection))&&(!commandeAssignee.getEstRealisee())){
								commandeARealiser = commandeAssignee;
							}
						}
					}

					commandeARealiser.realisee(Core.getService());
				}
			}
		});

		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> { //gestion de l'appui par les confection sur leurs touches pour indiquer la réalisation d'une commande
			if(key.getCode()==KeyCode.COMMA) {
				if(Core.getService().getConfection().size() >= 3){
					Membre confection = Core.getService().getConfection().get(2);
					CommandeAssignee commandeARealiser = null;
					for(Commande commande : Core.getService().getCommandes()){
						if(commande instanceof CommandeAssignee){
							CommandeAssignee commandeAssignee = (CommandeAssignee)commande;
							if((commandeAssignee.getMembre().equals(confection))&&(!commandeAssignee.getEstRealisee())){
								commandeARealiser = commandeAssignee;
							}
						}
					}

					commandeARealiser.realisee(Core.getService());
				}
			}
		});

		theatre.setScene(scene);

		File f = Core.recupererFichier("/Interface/Stylesheets/general.css");
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		f = Core.recupererFichier("/Interface/Stylesheets/menu.css");
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		f = Core.recupererFichier("/Interface/Stylesheets/commandes.css");
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		f = Core.recupererFichier("/Interface/Stylesheets/selection.css");
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		f = Core.recupererFichier("/Interface/Stylesheets/resultat.css");
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		f = Core.recupererFichier("/Interface/Stylesheets/graphiques.css");
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

		root.getStyleClass().add(ROOT);
		root.getStyleClass().add(FOND);

		theatre.show();
	}

	/**
	 * <p>Renvoie un {@code BorderPane} avec tous les éléments de la fenêtre principale du logiciel :
	 * <ul><li>la partie sélection de la commande,</li>
	 * <li>la partie liste des commandes,</li>
	 * <li>la partie résultat (prévisualisation de la commande, gestion de la caisse, sélection de l'équipe), et</li>
	 * <li>le menu permettant de faire apparaître les autres fenêtres du logiciel (confection, stocks, menu, administration, graphiques).</li></ul></p>
	 * @param theatre la fenêtre principale du logiciel.
	 * 
	 * @return le panneau de l'écran principal.
	 */
	private static final BorderPane ecranPrincipal(Stage theatre){

		BorderPane racine = new BorderPane();

		Region droite = Commandes.commandes();
		droite.setTranslateY(1);

		Region centre = Selection.selection(racine);

		Region bas = Resultat.resultat(racine);

		Region haut = Menu.menu(theatre);

		racine.setTop(haut);
		racine.setRight(droite);
		racine.setBottom(bas);
		racine.setCenter(centre);
		
		return(racine);
	}

	/**
	 * Recrée le panneau sélection au cas où il y ait eu des modifications de la base de données des contenus commandes.
	 */
	public static final void mettreSelectionAJour(){

		root.setCenter(Selection.selection(root));
		Selection.resetSelection();
	}
}
