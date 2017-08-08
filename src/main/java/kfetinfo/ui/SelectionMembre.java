package kfetinfo.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kfetinfo.core.BaseDonnees;
import kfetinfo.core.Core;
import kfetinfo.core.Membre;

public class SelectionMembre {
	private static Membre membreSelectionne;
	public static void selectionOrdi(Core core){
		VBox root = null;

		root = new VBox();

		final TableView<Membre> table = new TableView();
		table.getItems().setAll(BaseDonnees.getMembres());
		final TableColumn<Membre, String> nomColonne = new TableColumn<>("Nom");
		nomColonne.setCellValueFactory(param -> {
			final Membre membre = param.getValue();
			return new SimpleStringProperty(membre.getBlaze());
		});
		table.getColumns().setAll(nomColonne);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				core.getService().setOrdi(table.getSelectionModel().getSelectedItem());
				Resultat.mettreOrdiAJour(core);
			}
		});

		root.getChildren().add(table);

		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();
	}

	public static void selectionCommis1(Core core){
		VBox root = null;

		root = new VBox();

		final TableView<Membre> table = new TableView();
		table.getItems().setAll(BaseDonnees.getMembres());
		final TableColumn<Membre, String> nomColonne = new TableColumn<>("Nom");
		nomColonne.setCellValueFactory(param -> {
			final Membre membre = param.getValue();
			return new SimpleStringProperty(membre.getBlaze());
		});
		table.getColumns().setAll(nomColonne);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				Membre commis2 = core.getService().getCommis().get(1);
				List<Membre> commis = new ArrayList<Membre>();
				commis.add(table.getSelectionModel().getSelectedItem());
				commis.add(commis2);
				core.getService().setCommis(commis);
				Resultat.mettreCommisAJour(core);
			}
		});

		root.getChildren().add(table);

		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();
	}

	public static void selectionCommis2(Core core){
		VBox root = null;

		root = new VBox();

		final TableView<Membre> table = new TableView();
		table.getItems().setAll(BaseDonnees.getMembres());
		final TableColumn<Membre, String> nomColonne = new TableColumn<>("Nom");
		nomColonne.setCellValueFactory(param -> {
			final Membre membre = param.getValue();
			return new SimpleStringProperty(membre.getBlaze());
		});
		table.getColumns().setAll(nomColonne);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				Membre commis1 = core.getService().getCommis().get(0);
				List<Membre> commis = new ArrayList<Membre>();
				commis.add(commis1);
				commis.add(table.getSelectionModel().getSelectedItem());
				core.getService().setCommis(commis);
				Resultat.mettreCommisAJour(core);
			}
		});

		root.getChildren().add(table);

		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();
	}

	public static void selectionConfection1(Core core){
		VBox root = null;

		root = new VBox();

		final TableView<Membre> table = new TableView();
		table.getItems().setAll(BaseDonnees.getMembres());
		final TableColumn<Membre, String> nomColonne = new TableColumn<>("Nom");
		nomColonne.setCellValueFactory(param -> {
			final Membre membre = param.getValue();
			return new SimpleStringProperty(membre.getBlaze());
		});
		table.getColumns().setAll(nomColonne);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				Membre confection2 = core.getService().getConfection().get(1); //TODO vérifier la taille de la liste
				Membre confection3 = core.getService().getConfection().get(2);
				List<Membre> confection = new ArrayList<Membre>();
				confection.add(table.getSelectionModel().getSelectedItem());
				confection.add(confection2);
				confection.add(confection3);
				core.getService().setConfection(confection);
				Resultat.mettreConfectionAJour(core);
			}
		});

		root.getChildren().add(table);

		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();
	}

	public static void selectionConfection2(Core core){
		VBox root = null;

		root = new VBox();

		final TableView<Membre> table = new TableView();
		table.getItems().setAll(BaseDonnees.getMembres());
		final TableColumn<Membre, String> nomColonne = new TableColumn<>("Nom");
		nomColonne.setCellValueFactory(param -> {
			final Membre membre = param.getValue();
			return new SimpleStringProperty(membre.getBlaze());
		});
		table.getColumns().setAll(nomColonne);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				Membre confection1 = core.getService().getConfection().get(0); //TODO vérifier la taille de la liste
				Membre confection3 = core.getService().getConfection().get(2);
				List<Membre> confection = new ArrayList<Membre>();
				confection.add(confection1);
				confection.add(table.getSelectionModel().getSelectedItem());
				confection.add(confection3);
				core.getService().setConfection(confection);
				Resultat.mettreConfectionAJour(core);
			}
		});

		root.getChildren().add(table);

		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();
	}

	public static void selectionConfection3(Core core){
		VBox root = null;

		root = new VBox();

		final TableView<Membre> table = new TableView();
		table.getItems().setAll(BaseDonnees.getMembres());
		final TableColumn<Membre, String> nomColonne = new TableColumn<>("Nom");
		nomColonne.setCellValueFactory(param -> {
			final Membre membre = param.getValue();
			return new SimpleStringProperty(membre.getBlaze());
		});
		table.getColumns().setAll(nomColonne);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				Membre confection1 = core.getService().getConfection().get(0); //TODO vérifier la taille de la liste
				Membre confection2 = core.getService().getConfection().get(1);
				List<Membre> confection = new ArrayList<Membre>();
				confection.add(confection1);
				confection.add(confection2);
				confection.add(table.getSelectionModel().getSelectedItem());
				core.getService().setConfection(confection);
				Resultat.mettreConfectionAJour(core);
			}
		});

		root.getChildren().add(table);

		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();
	}
}
