package kfetinfo.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kfetinfo.core.BaseDonnees;
import kfetinfo.core.Core;
import kfetinfo.core.Membre;

public class SelectionMembre {
	private static Membre membreSelectionne;
	public static void selectionOrdi(Core core){
		VBox root = new VBox();

		final TableView<Membre> table = new TableView();
		Membre personne = new Membre();
		List<Membre> membres = new ArrayList<Membre>();
		membres.add(personne);
		membres.addAll(BaseDonnees.getMembres());
		table.getItems().setAll(membres);
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
				Resultat.mettreOrdiAJour();
			}
		});

		root.getChildren().add(table);

		Scene scene = new Scene(root, 250, 350);
		Stage stage = new Stage();

		stage.setResizable(false);
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();
	}

	public static void selectionCommis1(Core core){
		VBox root = new VBox();

		List<Membre> commisDebut = core.getService().getCommis();

		final TableView<Membre> table = new TableView();
		Membre personne = new Membre();
		List<Membre> membres = new ArrayList<Membre>();
		membres.add(personne);
		membres.addAll(BaseDonnees.getMembres());
		table.getItems().setAll(membres);
		final TableColumn<Membre, String> nomColonne = new TableColumn<>("Nom");
		nomColonne.setCellValueFactory(param -> {
			final Membre membre = param.getValue();
			return new SimpleStringProperty(membre.getBlaze());
		});
		table.getColumns().setAll(nomColonne);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				List<Membre> commis = new ArrayList<Membre>();
				if(commisDebut.size() >= 2){
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")){
						commis.add(table.getSelectionModel().getSelectedItem());
					}
					commis.add(commisDebut.get(1));
				} else {
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")){
						commis.add(table.getSelectionModel().getSelectedItem());
					}
				}
				core.getService().setCommis(commis);
				Resultat.mettreCommisAJour();
			}
		});

		root.getChildren().add(table);

		Scene scene = new Scene(root, 250, 350);
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();
	}

	public static void selectionCommis2(Core core){
		VBox root = new VBox();

		List<Membre> commisDebut = core.getService().getCommis();

		final TableView<Membre> table = new TableView();
		Membre personne = new Membre();
		List<Membre> membres = new ArrayList<Membre>();
		membres.add(personne);
		membres.addAll(BaseDonnees.getMembres());
		table.getItems().setAll(membres);
		final TableColumn<Membre, String> nomColonne = new TableColumn<>("Nom");
		nomColonne.setCellValueFactory(param -> {
			final Membre membre = param.getValue();
			return new SimpleStringProperty(membre.getBlaze());
		});
		table.getColumns().setAll(nomColonne);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				List<Membre> commis = new ArrayList<Membre>();
				if(commisDebut.size() >= 1){
					commis.add(commisDebut.get(0));
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")){
						commis.add(table.getSelectionModel().getSelectedItem());
					}
				} else {
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")){
						commis.add(table.getSelectionModel().getSelectedItem());
					}
				}
				core.getService().setCommis(commis);
				Resultat.mettreCommisAJour();
			}
		});

		root.getChildren().add(table);

		Scene scene = new Scene(root, 250, 350);
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();
	}

	public static Stage selectionConfection1(Core core){
		VBox root = new VBox();

		List<Membre> confectionDebut = core.getService().getConfection();

		final TableView<Membre> table = new TableView();
		Membre personne = new Membre();
		List<Membre> membres = new ArrayList<Membre>();
		membres.add(personne);
		membres.addAll(BaseDonnees.getMembres());
		table.getItems().setAll(membres);
		final TableColumn<Membre, String> nomColonne = new TableColumn<>("Nom");
		nomColonne.setCellValueFactory(param -> {
			final Membre membre = param.getValue();
			return new SimpleStringProperty(membre.getBlaze());
		});
		table.getColumns().setAll(nomColonne);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				List<Membre> confection = new ArrayList<Membre>();
				if(confectionDebut.size() >= 3){
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
					confection.add(confectionDebut.get(1));
					confection.add(confectionDebut.get(2));
				} else if(confectionDebut.size() >= 2){
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
					confection.add(confectionDebut.get(1));
				} else {
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
				}
				core.getService().setConfection(confection);
				Resultat.mettreConfectionAJour();
				EcranConfection.mettreEcransAJour();
			}
		});

		root.getChildren().add(table);

		Scene scene = new Scene(root, 250, 350);
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();

		return(stage);
	}

	public static Stage selectionConfection2(Core core){
		VBox root = new VBox();

		List<Membre> confectionDebut = core.getService().getConfection();

		final TableView<Membre> table = new TableView();
		Membre personne = new Membre();
		List<Membre> membres = new ArrayList<Membre>();
		membres.add(personne);
		membres.addAll(BaseDonnees.getMembres());
		table.getItems().setAll(membres);
		final TableColumn<Membre, String> nomColonne = new TableColumn<>("Nom");
		nomColonne.setCellValueFactory(param -> {
			final Membre membre = param.getValue();
			return new SimpleStringProperty(membre.getBlaze());
		});
		table.getColumns().setAll(nomColonne);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				List<Membre> confection = new ArrayList<Membre>();
				if(confectionDebut.size() >= 3){
					confection.add(confectionDebut.get(0));
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
					confection.add(confectionDebut.get(2));
				} else if(confectionDebut.size() >= 1){
					confection.add(confectionDebut.get(0));
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
				} else {
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
				}
				core.getService().setConfection(confection);
				Resultat.mettreConfectionAJour();
				EcranConfection.mettreEcransAJour();
			}
		});

		root.getChildren().add(table);

		Scene scene = new Scene(root, 250, 350);
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();

		return(stage);
	}

	public static Stage selectionConfection3(Core core){
		VBox root = new VBox();

		List<Membre> confectionDebut = core.getService().getConfection();

		final TableView<Membre> table = new TableView();
		Membre personne = new Membre();
		List<Membre> membres = new ArrayList<Membre>();
		membres.add(personne);
		membres.addAll(BaseDonnees.getMembres());
		table.getItems().setAll(membres);
		final TableColumn<Membre, String> nomColonne = new TableColumn<>("Nom");
		nomColonne.setCellValueFactory(param -> {
			final Membre membre = param.getValue();
			return new SimpleStringProperty(membre.getBlaze());
		});
		table.getColumns().setAll(nomColonne);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				List<Membre> confection = new ArrayList<Membre>();
				if(confectionDebut.size() >= 2){
					confection.add(confectionDebut.get(0));
					confection.add(confectionDebut.get(1));
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
				} else if(confectionDebut.size() >= 1){
					confection.add(confectionDebut.get(0));
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
				} else {
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
				}
				core.getService().setConfection(confection);
				Resultat.mettreConfectionAJour();
				EcranConfection.mettreEcransAJour();
			}
		});

		root.getChildren().add(table);

		Scene scene = new Scene(root, 250, 350);
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setAlwaysOnTop(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();

		return(stage);
	}
}
