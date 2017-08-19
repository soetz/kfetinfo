package kfetinfo.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kfetinfo.core.BaseDonnees;
import kfetinfo.core.Core;
import kfetinfo.core.Membre;

/**
 * <p>SelectionMembre est une classe constituée uniquement d'attributs et de méthodes statiques relatifs à la création de fenêtres permettant de sélectionner les membres participant au service.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public final class SelectionMembre {

	/**
	 * Crée une fenêtre permettant de sélectionner le membre qui s'occupera de prendre les commandes pendant le service.
	 * 
	 * @param core le core du système K'Fet.
	 */
	public static final void selectionOrdi(Core core){

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
		stage.initModality(Modality.APPLICATION_MODAL); //il faut fermer cette fenêtre pour revenir à l'écran principal
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Crée une fenêtre permettant de sélectionner le premier commis du service.
	 * 
	 * @param core le core du système K'Fet.
	 */
	public static final void selectionCommis1(Core core){

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
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")&&BaseDonnees.getMembres().contains(table.getSelectionModel().getSelectedItem())){
						commis.add(table.getSelectionModel().getSelectedItem());
					}
					commis.add(commisDebut.get(1));
				} else {
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")&&BaseDonnees.getMembres().contains(table.getSelectionModel().getSelectedItem())){
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
		stage.initModality(Modality.APPLICATION_MODAL); //il faut fermer cette fenêtre pour revenir à l'écran principal
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Crée une fenêtre permettant de sélectionner le second commis du service.
	 * 
	 * @param core le core du système K'Fet.
	 */
	public static final void selectionCommis2(Core core){

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
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")&&BaseDonnees.getMembres().contains(table.getSelectionModel().getSelectedItem())){
						commis.add(table.getSelectionModel().getSelectedItem());
					}
				} else {
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")&&BaseDonnees.getMembres().contains(table.getSelectionModel().getSelectedItem())){
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
		stage.initModality(Modality.APPLICATION_MODAL); //il faut fermer cette fenêtre pour revenir à l'écran principal
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Crée une fenêtre permettant de sélectionner le premier confection du service.
	 * 
	 * @param core le core du système K'Fet.
	 */
	public static final Stage selectionConfection1(Core core){

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
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")&&BaseDonnees.getMembres().contains(table.getSelectionModel().getSelectedItem())){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
					confection.add(confectionDebut.get(1));
					confection.add(confectionDebut.get(2));
				} else if(confectionDebut.size() >= 2){
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")&&BaseDonnees.getMembres().contains(table.getSelectionModel().getSelectedItem())){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
					confection.add(confectionDebut.get(1));
				} else {
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")&&BaseDonnees.getMembres().contains(table.getSelectionModel().getSelectedItem())){
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
		stage.initModality(Modality.APPLICATION_MODAL); //il faut fermer cette fenêtre pour revenir à l'écran principal
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();

		return(stage);
	}

	/**
	 * Crée une fenêtre permettant de sélectionner le deuxième confection du service.
	 * 
	 * @param core le core du système K'Fet.
	 */
	public static final Stage selectionConfection2(Core core){

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
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")&&BaseDonnees.getMembres().contains(table.getSelectionModel().getSelectedItem())){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
					confection.add(confectionDebut.get(2));
				} else if(confectionDebut.size() >= 1){
					confection.add(confectionDebut.get(0));
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")&&BaseDonnees.getMembres().contains(table.getSelectionModel().getSelectedItem())){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
				} else {
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")&&BaseDonnees.getMembres().contains(table.getSelectionModel().getSelectedItem())){
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
		stage.initModality(Modality.APPLICATION_MODAL); //il faut fermer cette fenêtre pour revenir à l'écran principal
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();

		return(stage);
	}

	/**
	 * Crée une fenêtre permettant de sélectionner le troisième confection du service.
	 * 
	 * @param core le core du système K'Fet.
	 */
	public static final Stage selectionConfection3(Core core){

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
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")&&BaseDonnees.getMembres().contains(table.getSelectionModel().getSelectedItem())){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
				} else if(confectionDebut.size() >= 1){
					confection.add(confectionDebut.get(0));
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")&&BaseDonnees.getMembres().contains(table.getSelectionModel().getSelectedItem())){
						confection.add(table.getSelectionModel().getSelectedItem());
					}
				} else {
					if(!table.getSelectionModel().getSelectedItem().getId().equals("f38aa97b-2c4b-491e-be10-884e48fbb6c2")&&BaseDonnees.getMembres().contains(table.getSelectionModel().getSelectedItem())){
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
		stage.initModality(Modality.APPLICATION_MODAL); //il faut fermer cette fenêtre pour revenir à l'écran principal
		stage.setTitle("Selection du membre");
		stage.setScene(scene);
		stage.show();

		return(stage);
	}
}
