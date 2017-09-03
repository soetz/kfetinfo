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

import kfetinfo.core.BaseDonnees;
import kfetinfo.core.CreateurBase;
import kfetinfo.core.Membre;

import java.util.List;
import java.util.ArrayList;

import java.util.Date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

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
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * <p>EditionMembres est une classe constituée uniquement d'attributs et de méthodes statiques relatifs à l'édition des informations des membres de la base de données.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class EditionMembres {

	//classes de style pour l'utilisation du CSS
	private static final String FOND = "ec-membres-fond";
	private static final String LABEL = "ec-membres-label";
	private static final String BOUTON_ENREGISTRER = "ec-membres-bouton-enregistrer";

	//constantes pour l'affichage
	private static final Double ESPACEMENT_ELEMENTS = 8.0;
	private static final Double ESPACEMENT_LABELS_FIELDS = 4.0;
	private static final Double TRANSLATION_HORIZONTALE_ELEMENTS = 10.0;
	private static final Double TRANSLATION_VERTICALE_ELEMENTS = 10.0;
	private static final Double TRANSLATION_VERTICALE_LABELS = 4.0;
	private static final Double LARGEUR_ID_FIELD = 350.0;
	private static final Double DECALAGE_HORIZONTAL_BOUTONS = 10.0;
	private static final Double DECALAGE_VERTICAL_BOUTONS = 10.0;

	//id par défaut des nouveaux membres
	public static final String ID_NOUVEAU = "Sera généré au moment de l'enregistrement";

	/**
	 * Crée une fenêtre permettant de modifier les membres de la base de données.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final void ecran(){

		Stage theatre = new Stage();
		theatre.getIcons().setAll(new Image(EditionMembres.class.getResource("../../Interface/Images/Icons/Membres/Membres_16.png").toExternalForm()),
				new Image(EditionMembres.class.getResource("../../Interface/Images/Icons/Membres/Membres_24.png").toExternalForm()),
				new Image(EditionMembres.class.getResource("../../Interface/Images/Icons/Membres/Membres_32.png").toExternalForm()),
				new Image(EditionMembres.class.getResource("../../Interface/Images/Icons/Membres/Membres_48.png").toExternalForm()),
				new Image(EditionMembres.class.getResource("../../Interface/Images/Icons/Membres/Membres_64.png").toExternalForm()),
				new Image(EditionMembres.class.getResource("../../Interface/Images/Icons/Membres/Membres_128.png").toExternalForm()),
				new Image(EditionMembres.class.getResource("../../Interface/Images/Icons/Membres/Membres_256.png").toExternalForm()),
				new Image(EditionMembres.class.getResource("../../Interface/Images/Icons/Membres/Membres_512.png").toExternalForm()));

		HBox pane = new HBox();
		pane.getStyleClass().add(FOND);

		Membre nouveau = new Membre(ID_NOUVEAU, "", "", "", "", new Date(), 0, 0, 0);
		List<Membre> liste = new ArrayList<Membre>();
		liste.add(nouveau);
		liste.addAll(BaseDonnees.getMembres());

		ListView<Membre> listeMembres = new ListView();
		listeMembres.getStyleClass().add(App.LISTVIEW);
		listeMembres.getItems().setAll(liste);
		listeMembres.getSelectionModel().select(0);

		listeMembres.setCellFactory(lv -> new MembreModificationCell());

		listeMembres.setMaxWidth(250);
		listeMembres.setMinWidth(250);
		listeMembres.setPrefHeight(500);

		VBox infosMembre = new VBox();
		infosMembre.setSpacing(ESPACEMENT_ELEMENTS);
		infosMembre.setTranslateX(TRANSLATION_HORIZONTALE_ELEMENTS);
		infosMembre.setTranslateY(TRANSLATION_VERTICALE_ELEMENTS);

		HBox idBox = new HBox();
		idBox.setSpacing(ESPACEMENT_LABELS_FIELDS);
		Label idLabel = new Label("Identificateur : ");
		idLabel.setTranslateY(TRANSLATION_VERTICALE_LABELS);
		idLabel.getStyleClass().add(LABEL);
		TextField idField = new TextField();
		idField.getStyleClass().add(App.FIELD);
		idField.getStyleClass().add(App.CODE);
		idField.setMinWidth(LARGEUR_ID_FIELD);
		idLabel.setLabelFor(idField);
		idField.setText(listeMembres.getSelectionModel().getSelectedItem().getId());
		idField.setDisable(true);
		Button idCopierBouton = new Button("Copier");
		idCopierBouton.getStyleClass().add(App.BOUTON);
		if(listeMembres.getSelectionModel().getSelectedItem().getId().equals(ID_NOUVEAU)){
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
		surnomBox.setSpacing(ESPACEMENT_LABELS_FIELDS);
		Label surnomLabel = new Label("Surnom : ");
		surnomLabel.getStyleClass().add(LABEL);
		surnomLabel.setTranslateY(TRANSLATION_VERTICALE_LABELS);
		TextField surnomField = new TextField();
		surnomField.getStyleClass().add(App.FIELD);
		surnomLabel.setLabelFor(surnomField);
		surnomField.setPromptText("Surnom");
		surnomField.setText(listeMembres.getSelectionModel().getSelectedItem().getSurnom());

		surnomBox.getChildren().setAll(surnomLabel, surnomField);

		HBox prenomNomBox = new HBox();
		prenomNomBox.setSpacing(ESPACEMENT_LABELS_FIELDS);
		Label prenomLabel = new Label("Prénom : ");
		prenomLabel.getStyleClass().add(LABEL);
		prenomLabel.setTranslateY(TRANSLATION_VERTICALE_LABELS);
		TextField prenomField = new TextField();
		prenomField.getStyleClass().add(App.FIELD);
		prenomLabel.setLabelFor(prenomField);
		prenomField.setPromptText("Prénom");
		prenomField.setText(listeMembres.getSelectionModel().getSelectedItem().getPrenom());
		Label nomLabel = new Label("Nom : ");
		nomLabel.getStyleClass().add(LABEL);
		nomLabel.setTranslateY(TRANSLATION_VERTICALE_LABELS);
		TextField nomField = new TextField();
		nomField.getStyleClass().add(App.FIELD);
		nomLabel.setLabelFor(nomField);
		nomField.setPromptText("Nom");
		nomField.setText(listeMembres.getSelectionModel().getSelectedItem().getNom());

		prenomNomBox.getChildren().setAll(prenomLabel, prenomField, nomLabel, nomField);

		HBox posteBox = new HBox();
		posteBox.setSpacing(ESPACEMENT_LABELS_FIELDS);
		Label posteLabel = new Label("Poste : ");
		posteLabel.getStyleClass().add(LABEL);
		posteLabel.setTranslateY(TRANSLATION_VERTICALE_LABELS);
		TextField posteField = new TextField();
		posteField.getStyleClass().add(App.FIELD);
		posteLabel.setLabelFor(posteField);
		posteField.setPromptText("Poste");
		posteField.setText(listeMembres.getSelectionModel().getSelectedItem().getPoste());

		posteBox.getChildren().setAll(posteLabel, posteField);

		HBox dateNaissanceBox = new HBox();
		dateNaissanceBox.setSpacing(ESPACEMENT_LABELS_FIELDS);
		Label dateNaissanceLabel = new Label("Date de naissance : ");
		dateNaissanceLabel.getStyleClass().add(LABEL);
		dateNaissanceLabel.setTranslateY(TRANSLATION_VERTICALE_LABELS);
		DatePicker dateNaissancePicker = new DatePicker();
		dateNaissanceLabel.setLabelFor(dateNaissancePicker);
		LocalDate dateInitiale = listeMembres.getSelectionModel().getSelectedItem().getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		dateNaissancePicker.setValue(dateInitiale);

		dateNaissanceBox.getChildren().setAll(dateNaissanceLabel, dateNaissancePicker);

		infosMembre.getChildren().setAll(idBox, surnomBox, prenomNomBox, posteBox, dateNaissanceBox);

		HBox boutons = new HBox();
		boutons.setSpacing(ESPACEMENT_ELEMENTS);
		Button supprimer = new Button("Supprimer");
		supprimer.getStyleClass().add(App.BOUTON);
		supprimer.setTranslateY(1);
		if(listeMembres.getSelectionModel().getSelectedItem().getId().equals(ID_NOUVEAU)){
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

						Membre nouveau = new Membre(ID_NOUVEAU, "", "", "", "", new Date(), 0, 0, 0);
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
		enregistrer.getStyleClass().add(App.BOUTON);
		enregistrer.getStyleClass().add(BOUTON_ENREGISTRER);
		enregistrer.setDefaultButton(true);
		enregistrer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae){
				Instant instant = dateNaissancePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
				Date dateNaissance = Date.from(instant);

				if(listeMembres.getSelectionModel().getSelectedItem().getId().equals(ID_NOUVEAU)){
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
				Membre nouveau = new Membre(ID_NOUVEAU, "", "", "", "", new Date(), 0, 0, 0);
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
		AnchorPane.setRightAnchor(boutons, DECALAGE_HORIZONTAL_BOUTONS);
		AnchorPane.setBottomAnchor(boutons, DECALAGE_VERTICAL_BOUTONS);

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
					if(listeMembres.getSelectionModel().getSelectedItem().getId().equals(ID_NOUVEAU)){
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

		scene.getStylesheets().add(EditionMembres.class.getResource("../../Interface/Stylesheets/general.css").toExternalForm());
		scene.getStylesheets().add(EditionMembres.class.getResource("../../Interface/Stylesheets/edition membres.css").toExternalForm());

		theatre.setAlwaysOnTop(true);
		theatre.setResizable(false);
		theatre.initModality(Modality.APPLICATION_MODAL); //il faut fermer cette fenêtre pour revenir à l'écran principal
		theatre.setMinWidth(App.LARGEUR_MIN_FENETRE + 16);
		theatre.setMinHeight(App.HAUTEUR_MIN_FENETRE + 39);
		theatre.setTitle("Écran de modification de la liste des membres");
		theatre.setScene(scene);
		theatre.show();
	}
}
