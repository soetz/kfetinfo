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
import kfetinfo.core.Core;
import kfetinfo.core.Membre;

import java.util.List;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * <p>SelectionMembre est une classe constituée uniquement d'attributs et de méthodes statiques relatifs à la création de fenêtres permettant de sélectionner les membres participant au service.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.1
 */
public final class SelectionMembre {

	/**
	 * Crée une fenêtre permettant de sélectionner le membre qui s'occupera de prendre les commandes pendant le service.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final void selectionOrdi(){

		Membre personne = new Membre();
		List<Membre> membres = new ArrayList<Membre>();
		membres.add(personne);
		membres.addAll(BaseDonnees.getMembres());

		ListView<Membre> listeMembres = new ListView();
		listeMembres.getItems().setAll(membres);

		listeMembres.setCellFactory(lv -> new MembreModificationCell());

		listeMembres.setMaxWidth(250);
		listeMembres.setMinWidth(250);
		listeMembres.setPrefHeight(350);

		listeMembres.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				Core.getService().setOrdi(listeMembres.getSelectionModel().getSelectedItem());
				Resultat.mettreOrdiAJour();
			}
		});

		Scene scene = new Scene(listeMembres, 250, 350);

		scene.getStylesheets().add(SelectionMembre.class.getResource("../../Interface/Stylesheets/general.css").toExternalForm());
		scene.getStylesheets().add(SelectionMembre.class.getResource("../../Interface/Stylesheets/selection membre.css").toExternalForm());

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
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final void selectionCommis1(){

		List<Membre> commisDebut = Core.getService().getCommis();

		Membre personne = new Membre();
		List<Membre> membres = new ArrayList<Membre>();
		membres.add(personne);
		membres.addAll(BaseDonnees.getMembres());

		ListView<Membre> listeMembres = new ListView();
		listeMembres.getItems().setAll(membres);

		listeMembres.setCellFactory(lv -> new MembreModificationCell());

		listeMembres.setMaxWidth(250);
		listeMembres.setMinWidth(250);
		listeMembres.setPrefHeight(350);

		listeMembres.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				List<Membre> commis = new ArrayList<Membre>();
				if(commisDebut.size() >= 2){
					if(!listeMembres.getSelectionModel().getSelectedItem().getId().equals(Membre.ID_MEMBRE_DEFAUT)&&BaseDonnees.getMembres().contains(listeMembres.getSelectionModel().getSelectedItem())){
						commis.add(listeMembres.getSelectionModel().getSelectedItem());
					}
					commis.add(commisDebut.get(1));
				} else {
					if(!listeMembres.getSelectionModel().getSelectedItem().getId().equals(Membre.ID_MEMBRE_DEFAUT)&&BaseDonnees.getMembres().contains(listeMembres.getSelectionModel().getSelectedItem())){
						commis.add(listeMembres.getSelectionModel().getSelectedItem());
					}
				}
				Core.getService().setCommis(commis);
				Resultat.mettreCommisAJour();
			}
		});

		Scene scene = new Scene(listeMembres, 250, 350);
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
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final void selectionCommis2(){

		List<Membre> commisDebut = Core.getService().getCommis();

		Membre personne = new Membre();
		List<Membre> membres = new ArrayList<Membre>();
		membres.add(personne);
		membres.addAll(BaseDonnees.getMembres());

		ListView<Membre> listeMembres = new ListView();
		listeMembres.getItems().setAll(membres);

		listeMembres.setCellFactory(lv -> new MembreModificationCell());

		listeMembres.setMaxWidth(250);
		listeMembres.setMinWidth(250);
		listeMembres.setPrefHeight(350);

		listeMembres.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				List<Membre> commis = new ArrayList<Membre>();
				if(commisDebut.size() >= 1){
					commis.add(commisDebut.get(0));
					if(!listeMembres.getSelectionModel().getSelectedItem().getId().equals(Membre.ID_MEMBRE_DEFAUT)&&BaseDonnees.getMembres().contains(listeMembres.getSelectionModel().getSelectedItem())){
						commis.add(listeMembres.getSelectionModel().getSelectedItem());
					}
				} else {
					if(!listeMembres.getSelectionModel().getSelectedItem().getId().equals(Membre.ID_MEMBRE_DEFAUT)&&BaseDonnees.getMembres().contains(listeMembres.getSelectionModel().getSelectedItem())){
						commis.add(listeMembres.getSelectionModel().getSelectedItem());
					}
				}
				Core.getService().setCommis(commis);
				Resultat.mettreCommisAJour();
			}
		});

		Scene scene = new Scene(listeMembres, 250, 350);
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
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Stage selectionConfection1(){

		List<Membre> confectionDebut = Core.getService().getConfection();

		Membre personne = new Membre();
		List<Membre> membres = new ArrayList<Membre>();
		membres.add(personne);
		membres.addAll(BaseDonnees.getMembres());

		ListView<Membre> listeMembres = new ListView();
		listeMembres.getItems().setAll(membres);

		listeMembres.setCellFactory(lv -> new MembreModificationCell());

		listeMembres.setMaxWidth(250);
		listeMembres.setMinWidth(250);
		listeMembres.setPrefHeight(350);

		listeMembres.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				List<Membre> confection = new ArrayList<Membre>();
				if(confectionDebut.size() >= 3){
					if(!listeMembres.getSelectionModel().getSelectedItem().getId().equals(Membre.ID_MEMBRE_DEFAUT)&&BaseDonnees.getMembres().contains(listeMembres.getSelectionModel().getSelectedItem())){
						confection.add(listeMembres.getSelectionModel().getSelectedItem());
					}
					confection.add(confectionDebut.get(1));
					confection.add(confectionDebut.get(2));
				} else if(confectionDebut.size() >= 2){
					if(!listeMembres.getSelectionModel().getSelectedItem().getId().equals(Membre.ID_MEMBRE_DEFAUT)&&BaseDonnees.getMembres().contains(listeMembres.getSelectionModel().getSelectedItem())){
						confection.add(listeMembres.getSelectionModel().getSelectedItem());
					}
					confection.add(confectionDebut.get(1));
				} else {
					if(!listeMembres.getSelectionModel().getSelectedItem().getId().equals(Membre.ID_MEMBRE_DEFAUT)&&BaseDonnees.getMembres().contains(listeMembres.getSelectionModel().getSelectedItem())){
						confection.add(listeMembres.getSelectionModel().getSelectedItem());
					}
				}
				Core.getService().setConfection(confection);
				Resultat.mettreConfectionAJour();
				EcranConfection.mettreEcransAJour();
			}
		});

		Scene scene = new Scene(listeMembres, 250, 350);
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
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final Stage selectionConfection2(){

		List<Membre> confectionDebut = Core.getService().getConfection();

		Membre personne = new Membre();
		List<Membre> membres = new ArrayList<Membre>();
		membres.add(personne);
		membres.addAll(BaseDonnees.getMembres());

		ListView<Membre> listeMembres = new ListView();
		listeMembres.getItems().setAll(membres);

		listeMembres.setCellFactory(lv -> new MembreModificationCell());

		listeMembres.setMaxWidth(250);
		listeMembres.setMinWidth(250);
		listeMembres.setPrefHeight(350);

		listeMembres.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				List<Membre> confection = new ArrayList<Membre>();
				if(confectionDebut.size() >= 3){
					confection.add(confectionDebut.get(0));
					if(!listeMembres.getSelectionModel().getSelectedItem().getId().equals(Membre.ID_MEMBRE_DEFAUT)&&BaseDonnees.getMembres().contains(listeMembres.getSelectionModel().getSelectedItem())){
						confection.add(listeMembres.getSelectionModel().getSelectedItem());
					}
					confection.add(confectionDebut.get(2));
				} else if(confectionDebut.size() >= 1){
					confection.add(confectionDebut.get(0));
					if(!listeMembres.getSelectionModel().getSelectedItem().getId().equals(Membre.ID_MEMBRE_DEFAUT)&&BaseDonnees.getMembres().contains(listeMembres.getSelectionModel().getSelectedItem())){
						confection.add(listeMembres.getSelectionModel().getSelectedItem());
					}
				} else {
					if(!listeMembres.getSelectionModel().getSelectedItem().getId().equals(Membre.ID_MEMBRE_DEFAUT)&&BaseDonnees.getMembres().contains(listeMembres.getSelectionModel().getSelectedItem())){
						confection.add(listeMembres.getSelectionModel().getSelectedItem());
					}
				}
				Core.getService().setConfection(confection);
				Resultat.mettreConfectionAJour();
				EcranConfection.mettreEcransAJour();
			}
		});

		Scene scene = new Scene(listeMembres, 250, 350);
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
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final Stage selectionConfection3(){

		List<Membre> confectionDebut = Core.getService().getConfection();

		Membre personne = new Membre();
		List<Membre> membres = new ArrayList<Membre>();
		membres.add(personne);
		membres.addAll(BaseDonnees.getMembres());

		ListView<Membre> listeMembres = new ListView();
		listeMembres.getItems().setAll(membres);

		listeMembres.setCellFactory(lv -> new MembreModificationCell());

		listeMembres.setMaxWidth(250);
		listeMembres.setMinWidth(250);
		listeMembres.setPrefHeight(350);

		listeMembres.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				List<Membre> confection = new ArrayList<Membre>();
				if(confectionDebut.size() >= 2){
					confection.add(confectionDebut.get(0));
					confection.add(confectionDebut.get(1));
					if(!listeMembres.getSelectionModel().getSelectedItem().getId().equals(Membre.ID_MEMBRE_DEFAUT)&&BaseDonnees.getMembres().contains(listeMembres.getSelectionModel().getSelectedItem())){
						confection.add(listeMembres.getSelectionModel().getSelectedItem());
					}
				} else if(confectionDebut.size() >= 1){
					confection.add(confectionDebut.get(0));
					if(!listeMembres.getSelectionModel().getSelectedItem().getId().equals(Membre.ID_MEMBRE_DEFAUT)&&BaseDonnees.getMembres().contains(listeMembres.getSelectionModel().getSelectedItem())){
						confection.add(listeMembres.getSelectionModel().getSelectedItem());
					}
				} else {
					if(!listeMembres.getSelectionModel().getSelectedItem().getId().equals(Membre.ID_MEMBRE_DEFAUT)&&BaseDonnees.getMembres().contains(listeMembres.getSelectionModel().getSelectedItem())){
						confection.add(listeMembres.getSelectionModel().getSelectedItem());
					}
				}
				Core.getService().setConfection(confection);
				Resultat.mettreConfectionAJour();
				EcranConfection.mettreEcransAJour();
			}
		});

		Scene scene = new Scene(listeMembres, 250, 350);
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
