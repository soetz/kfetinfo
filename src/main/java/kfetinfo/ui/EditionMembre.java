package kfetinfo.ui;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kfetinfo.core.BaseDonnees;
import kfetinfo.core.ContenuCommande;
import kfetinfo.core.Core;
import kfetinfo.core.CreateurBase;
import kfetinfo.core.Membre;
import kfetinfo.core.Plat;

public class EditionMembre {

	public static void ecran(Core core){
		Stage theatre = new Stage();

		HBox pane = new HBox();

		Membre nouveau = new Membre("Sera généré au moment de l'enregistrement", "", "", "", "", new Date(), 0, 0, 0);
		List<Membre> liste = new ArrayList<Membre>();
		liste.add(nouveau);
		for(Membre membre : BaseDonnees.getMembres()){
			liste.add(membre);
		}
		ListView<Membre> listeMembres = new ListView();
		listeMembres.getItems().setAll(liste);
		listeMembres.getSelectionModel().select(0);

		listeMembres.setCellFactory(lv -> new MembreCell());

		listeMembres.setMaxWidth(250);
		listeMembres.setMinWidth(250);
		listeMembres.setPrefHeight(500);

		VBox infosMembre = new VBox();

		HBox idBox = new HBox();
		Label idLabel = new Label("Identificateur : ");
		TextField idField = new TextField();
		idLabel.setLabelFor(idField);
		idField.setText(listeMembres.getSelectionModel().getSelectedItem().getId());
		idField.setDisable(true);
		Button idCopierBouton = new Button("Copier");
		if(listeMembres.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
			idCopierBouton.setDisable(true);
		}
		idCopierBouton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Clipboard pressePapier = Clipboard.getSystemClipboard();
				ClipboardContent contenu = new ClipboardContent();
				contenu.put(DataFormat.PLAIN_TEXT, idField.getText());
				pressePapier.setContent(contenu);
			}
		});

		idBox.getChildren().setAll(idLabel, idField, idCopierBouton);

		HBox surnomBox = new HBox();
		Label surnomLabel = new Label("Surnom : ");
		TextField surnomField = new TextField();
		surnomLabel.setLabelFor(surnomField);
		surnomField.setPromptText("Surnom");
		surnomField.setText(listeMembres.getSelectionModel().getSelectedItem().getSurnom());

		surnomBox.getChildren().setAll(surnomLabel, surnomField);

		HBox prenomNomBox = new HBox();
		Label prenomLabel = new Label("Prénom : ");
		TextField prenomField = new TextField();
		prenomLabel.setLabelFor(prenomField);
		prenomField.setPromptText("Prénom");
		prenomField.setText(listeMembres.getSelectionModel().getSelectedItem().getPrenom());
		Label nomLabel = new Label("Nom : ");
		TextField nomField = new TextField();
		nomLabel.setLabelFor(nomField);
		nomField.setPromptText("Nom");
		nomField.setText(listeMembres.getSelectionModel().getSelectedItem().getNom());

		prenomNomBox.getChildren().setAll(prenomLabel, prenomField, nomLabel, nomField);

		HBox posteBox = new HBox();
		Label posteLabel = new Label("Poste : ");
		TextField posteField = new TextField();
		posteLabel.setLabelFor(posteField);
		posteField.setPromptText("Poste");
		posteField.setText(listeMembres.getSelectionModel().getSelectedItem().getPoste());

		posteBox.getChildren().setAll(posteLabel, posteField);

		HBox dateNaissanceBox = new HBox();
		Label dateNaissanceLabel = new Label("Date de naissance : ");
		DatePicker dateNaissancePicker = new DatePicker();
		dateNaissanceLabel.setLabelFor(dateNaissancePicker);
		LocalDate dateInitiale = listeMembres.getSelectionModel().getSelectedItem().getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		dateNaissancePicker.setValue(dateInitiale);

		dateNaissanceBox.getChildren().setAll(dateNaissanceLabel, dateNaissancePicker);

		infosMembre.getChildren().setAll(idBox, surnomBox, prenomNomBox, posteBox, dateNaissanceBox);

		HBox boutons = new HBox();
		Button supprimer = new Button("Supprimer");
		if(listeMembres.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
			supprimer.setDisable(true);
		}
		supprimer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
				confirmation.initOwner(theatre);
				confirmation.setTitle("Supprimer le membre ?");
				confirmation.setHeaderText("Vous êtes sur le point de supprimer le membre « " + listeMembres.getSelectionModel().getSelectedItem().getPrenom() + " " + listeMembres.getSelectionModel().getSelectedItem().getNom() + " ».");
				confirmation.setContentText("Êtes-vous certain de vouloir supprimer le membre ? Vous ne pourrez plus récupérer les données.");
				Optional<ButtonType> resultat = confirmation.showAndWait();
				resultat.ifPresent(button -> {
					if(button == ButtonType.OK) {
						CreateurBase.supprimerMembre(listeMembres.getSelectionModel().getSelectedItem());
						BaseDonnees.chargerMembres();

						Membre nouveau = new Membre("Sera généré au moment de l'enregistrement", "", "", "", "", new Date(), 0, 0, 0);
						List<Membre> liste = new ArrayList<Membre>();
						liste.add(nouveau);
						for(Membre membre : BaseDonnees.getMembres()){
							liste.add(membre);
						}
						listeMembres.getItems().setAll(liste);
						listeMembres.getSelectionModel().select(0);
					}
				});
			}
		});

		Button enregistrer = new Button("Enregistrer");
		enregistrer.setDefaultButton(true);
		enregistrer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				Instant instant = dateNaissancePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
				Date dateNaissance = Date.from(instant);
				if(listeMembres.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
					CreateurBase.creerMembre(
						nomField.getText(),
						prenomField.getText(),
						surnomField.getText(),
						posteField.getText(),
						dateNaissance);
				} else {
					CreateurBase.mettreMembreAJour(new Membre(
						listeMembres.getSelectionModel().getSelectedItem().getId(),
						nomField.getText(),
						prenomField.getText(),
						surnomField.getText(),
						posteField.getText(),
						dateNaissance,
						listeMembres.getSelectionModel().getSelectedItem().getNbCommandes(),
						listeMembres.getSelectionModel().getSelectedItem().getNbServices(),
						listeMembres.getSelectionModel().getSelectedItem().getTempsMoyenCommande()));
				}

				BaseDonnees.chargerMembres();
				Membre nouveau = new Membre("Sera généré au moment de l'enregistrement", "", "", "", "", new Date(), 0, 0, 0);
				List<Membre> liste = new ArrayList<Membre>();
				liste.add(nouveau);
				for(Membre membre : BaseDonnees.getMembres()){
					liste.add(membre);
				}
				listeMembres.getItems().setAll(liste);
				listeMembres.getSelectionModel().select(0);
			}
		});

		boutons.getChildren().addAll(supprimer, enregistrer);

		AnchorPane disposition = new AnchorPane();
		AnchorPane.setTopAnchor(infosMembre, 0.0);
		AnchorPane.setLeftAnchor(infosMembre, 0.0);
		AnchorPane.setRightAnchor(boutons, 0.0);
		AnchorPane.setBottomAnchor(boutons, 0.0);

		disposition.getChildren().addAll(infosMembre, boutons);

		listeMembres.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				if(listeMembres.getSelectionModel().getSelectedItem() != null){
					idField.setText(listeMembres.getSelectionModel().getSelectedItem().getId());
					surnomField.setText(listeMembres.getSelectionModel().getSelectedItem().getSurnom());
					prenomField.setText(listeMembres.getSelectionModel().getSelectedItem().getPrenom());
					nomField.setText(listeMembres.getSelectionModel().getSelectedItem().getNom());
					posteField.setText(listeMembres.getSelectionModel().getSelectedItem().getPoste());
					LocalDate dateInitiale = listeMembres.getSelectionModel().getSelectedItem().getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					dateNaissancePicker.setValue(dateInitiale);
					surnomField.requestFocus();
					if(listeMembres.getSelectionModel().getSelectedItem().getId().equals("Sera généré au moment de l'enregistrement")){
						idCopierBouton.setDisable(true);
						supprimer.setDisable(true);
					} else {
						idCopierBouton.setDisable(false);
						supprimer.setDisable(false);
					}
				}
			}
		});

		pane.getChildren().addAll(listeMembres, disposition);

		Scene scene = new Scene(pane, App.LARGEUR_MIN_FENETRE, App.HAUTEUR_MIN_FENETRE);
		theatre.setAlwaysOnTop(true);
		theatre.setResizable(false);
		theatre.initModality(Modality.APPLICATION_MODAL);
		theatre.setMinWidth(App.LARGEUR_MIN_FENETRE + 16);
		theatre.setMinHeight(App.HAUTEUR_MIN_FENETRE + 39);
		theatre.setTitle("Écran de modification de la liste des membres");
		theatre.setScene(scene);
		theatre.show();
	}
}
