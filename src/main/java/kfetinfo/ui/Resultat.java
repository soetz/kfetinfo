package kfetinfo.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class Resultat {
	public static Region resultat(){
		GridPane resultat = new GridPane();

		Label bas = new Label();
		bas.setId("bas");
		bas.setText("Resultat");
		bas.getStyleClass().add(App.SHOWCASE);
		bas.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		bas.setMinHeight(App.TAILLE_PANNEAU_RESULTAT);

		return(bas);
	}
}
