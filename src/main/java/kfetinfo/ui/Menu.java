package kfetinfo.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import kfetinfo.core.Core;

public class Menu {
	public static final String BOUTON_MENU = "bouton-menu";

	public static HBox menu(Core core, Stage theatre){
		HBox menu = new HBox();

		Button confection = new Button("Confection");
		confection.getStyleClass().add(BOUTON_MENU);
		confection.prefHeightProperty().bind(menu.heightProperty());
		confection.prefWidthProperty().bind(menu.widthProperty().divide(5));
		confection.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				EcranConfection.ecranConfection(core, theatre);
			}
		});

		Button stocks = new Button("Stocks");
		stocks.getStyleClass().add(BOUTON_MENU);
		stocks.prefHeightProperty().bind(menu.heightProperty());
		stocks.prefWidthProperty().bind(menu.widthProperty().divide(5));
		stocks.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				Stocks.ecranStocks(core, theatre);
			}
		});

		Button menuBouton = new Button("Menu");
		menuBouton.getStyleClass().add(BOUTON_MENU);
		menuBouton.prefHeightProperty().bind(menu.heightProperty());
		menuBouton.prefWidthProperty().bind(menu.widthProperty().divide(5));

		Button administration = new Button("Administration");
		administration.getStyleClass().add(BOUTON_MENU);
		administration.prefHeightProperty().bind(menu.heightProperty());
		administration.prefWidthProperty().bind(menu.widthProperty().divide(5));

		Button graphiques = new Button("Graphiques");
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
