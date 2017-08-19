package kfetinfo.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import kfetinfo.core.Core;

/**
 * <p>Menu est une classe constituée uniquement d'attributs et de méthodes statiques relatifs à l'affichage du menu permettant de faire apparaître les fenêtres secondaires du logiciel.</p>
 * <p>Il permet de faire apparaître les fenêtres suivantes :
 * <ul><li>le ou les écrans de confection,</li>
 * <li>l'écran des stocks,</li>
 * <li>l'écran de modification du menu,</li>
 * <li>l'écran d'administration, et</li>
 * <li>l'écran des graphiques (non implémenté pour l'instant).</li></ul>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class Menu {

	//classe de style pour l'utilisation du CSS
	public static final String BOUTON_MENU = "bouton-menu";

	/**
	 * Crée un panneau muni de boutons permettant de faire apparaître les fenêtres secondaires du logiciel.
	 * 
	 * @param core le core du système K'Fet.
	 * @param theatre le {@code Stage} de la fenêtre principal du logiciel.
	 * 
	 * @return
	 */
	public static final HBox menu(Core core, Stage theatre){

		HBox menu = new HBox();

		Button confection = new Button("_Confection"); //C est une touche mnémonique pour ce bouton
		confection.setMnemonicParsing(true);
		confection.getStyleClass().add(BOUTON_MENU);
		confection.prefHeightProperty().bind(menu.heightProperty());
		confection.prefWidthProperty().bind(menu.widthProperty().divide(5));
		confection.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				EcranConfection.ecranConfection(theatre);
			}
		});

		Button stocks = new Button("S_tocks"); //T est une touche mnémonique pour ce bouton
		stocks.setMnemonicParsing(true);
		stocks.getStyleClass().add(BOUTON_MENU);
		stocks.prefHeightProperty().bind(menu.heightProperty());
		stocks.prefWidthProperty().bind(menu.widthProperty().divide(5));
		stocks.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				Stocks.ecranStocks(core, theatre);
			}
		});

		Button menuBouton = new Button("_Menu"); //M est une touche mnémonique pour ce bouton
		menuBouton.setMnemonicParsing(true);
		menuBouton.getStyleClass().add(BOUTON_MENU);
		menuBouton.prefHeightProperty().bind(menu.heightProperty());
		menuBouton.prefWidthProperty().bind(menu.widthProperty().divide(5));
		menuBouton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				EditionContenu.ecran(core);
			}
		});

		Button administration = new Button("A_dministration"); //D est une touche mnémonique pour ce bouton
		administration.setMnemonicParsing(true);
		administration.getStyleClass().add(BOUTON_MENU);
		administration.prefHeightProperty().bind(menu.heightProperty());
		administration.prefWidthProperty().bind(menu.widthProperty().divide(5));
		administration.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				EditionMembre.ecran(core);
			}
		});

		Button graphiques = new Button("_Graphiques"); //G est une touche mnémonique pour ce bouton
		graphiques.getStyleClass().add(BOUTON_MENU);
		graphiques.prefHeightProperty().bind(menu.heightProperty());
		graphiques.prefWidthProperty().bind(menu.widthProperty().divide(5));

		menu.getChildren().add(confection);
		menu.getChildren().add(stocks);
		menu.getChildren().add(menuBouton);
		menu.getChildren().add(administration);
		menu.getChildren().add(graphiques);

		menu.setId("haut");
		menu.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		menu.setMinHeight(App.TAILLE_PANNEAU_MENU);

		return(menu);
	}
}
