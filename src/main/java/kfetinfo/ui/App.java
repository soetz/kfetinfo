package kfetinfo.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kfetinfo.core.Core;

public class App extends Application{
	public static void main(String[] args){
		launch(args);
	}

	public void start(Stage theatre){
		Core core = new Core();

		Parent root = null;

		try {
			root = FXMLLoader.load(getClass().getResource("../../Interface/FXML/disposition.fxml"));
		} catch (Exception e){
			e.printStackTrace();
		}

		theatre.setTitle("K'Fet Info");

		Scene scene = new Scene(root, 800, 600);

		theatre.setScene(scene);

		scene.getStylesheets().add(this.getClass().getResource("../../Interface/Stylesheets/style.css").toExternalForm());
		theatre.show();
	}
}
