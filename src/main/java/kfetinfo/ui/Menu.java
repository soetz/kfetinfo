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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * <p>Menu est une classe constituée uniquement d'attributs et de méthodes statiques relatifs à l'affichage du menu permettant de faire apparaître les fenêtres secondaires du logiciel.</p>
 * <p>Il permet de faire apparaître les fenêtres suivantes :
 * <ul><li>le ou les écrans de confection,</li>
 * <li>l'écran des stocks,</li>
 * <li>l'écran de modification du menu,</li>
 * <li>l'écran d'administration, et</li>
 * <li>l'écran des graphiques (non implémenté pour l'instant).</li></ul></p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public final class Menu {

	//classe de style pour l'utilisation du CSS
	public static final String BOUTON = "menu-bouton";

	/**
	 * Crée une {@code HBox} munie de boutons permettant de faire apparaître les fenêtres secondaires du logiciel.
	 * @param theatre le {@code Stage} de la fenêtre principal du logiciel.
	 * 
	 * @return le panneau de menu.
	 */
	public static final HBox menu(Stage theatre){

		HBox menu = new HBox();

		Button confection = new Button("_Confection"); //C est une touche mnémonique pour ce bouton
		confection.setMnemonicParsing(true);
		confection.getStyleClass().add(BOUTON);
		confection.getStyleClass().add(App.BOUTON);
		confection.prefHeightProperty().bind(menu.heightProperty());
		confection.prefWidthProperty().bind(menu.widthProperty().divide(5));
		confection.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				EcranConfection.ecranConfection(theatre);
			}
		});

		Button stocks = new Button("S_tocks"); //T est une touche mnémonique pour ce bouton
		stocks.setMnemonicParsing(true);
		stocks.getStyleClass().add(BOUTON);
		stocks.getStyleClass().add(App.BOUTON);
		stocks.prefHeightProperty().bind(menu.heightProperty());
		stocks.prefWidthProperty().bind(menu.widthProperty().divide(5));
		stocks.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				Stocks.ecranStocks(theatre);
			}
		});

		Button menuBouton = new Button("_Menu"); //M est une touche mnémonique pour ce bouton
		menuBouton.setMnemonicParsing(true);
		menuBouton.getStyleClass().add(BOUTON);
		menuBouton.getStyleClass().add(App.BOUTON);
		menuBouton.prefHeightProperty().bind(menu.heightProperty());
		menuBouton.prefWidthProperty().bind(menu.widthProperty().divide(5));
		menuBouton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				EditionContenus.ecran();
			}
		});

		Button administration = new Button("A_dministration"); //D est une touche mnémonique pour ce bouton
		administration.setMnemonicParsing(true);
		administration.getStyleClass().add(BOUTON);
		administration.getStyleClass().add(App.BOUTON);
		administration.prefHeightProperty().bind(menu.heightProperty());
		administration.prefWidthProperty().bind(menu.widthProperty().divide(5));
		administration.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				EditionMembres.ecran();
			}
		});

		Button graphiques = new Button("_Graphiques"); //G est une touche mnémonique pour ce bouton
		graphiques.getStyleClass().add(BOUTON);
		graphiques.getStyleClass().add(App.BOUTON);
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
