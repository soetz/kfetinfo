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

package kfetinfo.core;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;

import java.util.List;

import org.apache.commons.io.FilenameUtils;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Service {
	Date date;
	List<Commande> commandes;
	private final ObjectProperty<Commande> nouvelleCommande = new SimpleObjectProperty<Commande>();
	private final ObjectProperty<CommandeAssignee> nouvelleCommandeAssignee = new SimpleObjectProperty<CommandeAssignee>();
	private final ObjectProperty<CommandeAssignee> nouvelleCommandeRealisee = new SimpleObjectProperty<CommandeAssignee>();
	private final ObjectProperty<CommandeAssignee> nouvelleCommandeDonnee = new SimpleObjectProperty<CommandeAssignee>();	
	private final ObjectProperty<Commande> nouvelleCommandeRetiree = new SimpleObjectProperty<Commande>();
	float nbBaguettesBase;
	float nbBaguettesAchetees;
	float nbBaguettesReservees;
	float nbBaguettesUtilisees;
	Membre ordi;
	List<Membre> commis;
	List<Membre> confection;

	public Service(Date date){
		SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		SimpleDateFormat mois = new SimpleDateFormat("MM");
		SimpleDateFormat jour = new SimpleDateFormat("dd");

		File dossier = null;
		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Services/").toURI());
		} catch (Exception e){
			e.printStackTrace();
		}

		File fichier = new File(dossier + "/" + annee.format(date) + "/" + mois.format(date) + "/" + jour.format(date) + "/" + "_service.json");
		if(fichier.exists()){
			Service vieux = LecteurBase.lireService(date);
			this.date = date;
			commandes = new ArrayList<Commande>();
			nbBaguettesUtilisees = vieux.getNbBaguettesUtilisees();
			nbBaguettesAchetees = vieux.getNbBaguettesAchetees();
			nbBaguettesReservees = vieux.getNbBaguettesReservees();
			nbBaguettesBase = nbBaguettesAchetees - nbBaguettesReservees;
			ordi = vieux.getOrdi();
			commis = vieux.getCommis();
			confection = vieux.getConfection();
		} else {
			this.date = date;
			commandes = new ArrayList<Commande>();
			nbBaguettesUtilisees = 0;
			nbBaguettesAchetees = 0;
			nbBaguettesReservees = 0;
			nbBaguettesBase = nbBaguettesAchetees - nbBaguettesReservees;
			ordi = getOrdiDernierService();
			commis = getCommisDernierService();
			confection = getConfectionDernierService();
		}

		try {
			Files.createDirectories(Paths.get(dossier + "/" + annee.format(date) + "/" + mois.format(date) + "/" + jour.format(date) + "/"));
		} catch (Exception e){
			e.printStackTrace();
		}

		recharger();
		ecrireFichier();
	}

	public Service(Date date, List<Commande> commandes, float nbBaguettesUtilisees, float nbBaguettesAchetees, float nbBaguettesReservees, Membre ordi, List<Membre> commis, List<Membre> confection){
		SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		SimpleDateFormat mois = new SimpleDateFormat("MM");
		SimpleDateFormat jour = new SimpleDateFormat("dd");

		File dossier = null;
		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Services/").toURI());
		} catch (Exception e){
			e.printStackTrace();
		}

		try {
			Files.createDirectories(Paths.get(dossier + "/" + annee.format(date) + "\\" + mois.format(date) + "\\" + jour.format(date) + "\\"));
		} catch (Exception e){
			e.printStackTrace();
		}

		this.date = date;
		this.commandes = commandes;
		this.nbBaguettesBase = nbBaguettesAchetees - nbBaguettesReservees;
		this.nbBaguettesUtilisees = nbBaguettesUtilisees;
		this.nbBaguettesAchetees = nbBaguettesAchetees;
		this.nbBaguettesReservees = nbBaguettesReservees;
		this.ordi = ordi;
		this.commis = commis;
		this.confection = confection;
	}

	public float getNbBaguettesBase(){
		return(nbBaguettesBase);
	}

	public float getNbBaguettesUtilisees(){
		return(nbBaguettesUtilisees);
	}

	public float getNbBaguettesAchetees(){
		return(nbBaguettesAchetees);
	}

	public float getNbBaguettesReservees(){
		return(nbBaguettesReservees);
	}

	public Date getDate(){
		return(date);
	}

	public float getCout(){
		float cout = 0;
		for(Commande commande : commandes){
			cout += commande.getCout();
		}

		return(cout);
	}

	public float getRevenu(){
		float revenu = 0;
		for(Commande commande : commandes){
			revenu += commande.getPrix();
		}

		return(revenu);
	}

	public Commande getCommande(int numero){
		Commande commande = null;

		for(Commande commandeListe : commandes){
			if(commandeListe.getNumero() == numero){
				commande = commandeListe;
			}
		}

		return(commande);
	}

	public List<Commande> getCommandes(){

		return(commandes);
	}

	public int getNbCommandes(){
		return(commandes.size());
	}

	public Membre getOrdi(){
		return(ordi);
	}

	public List<Membre> getCommis(){
		return(commis);
	}

	public List<Membre> getConfection(){
		return(confection);
	}

	private Date getDateDernierService(){
		Date dateDernierService = new Date(0);

		File dossierServices = null;
		try {
			dossierServices = new File(LecteurBase.class.getResource("../../Base de Données/Services/").toURI());
		} catch (Exception e){
			e.printStackTrace();
		}

		File[] contenuDossierServices = dossierServices.listFiles();

		String anneeRecente = "0";

		for(File contenu : contenuDossierServices){
			String nom = contenu.getName();

			if(Integer.parseInt(nom) > Integer.parseInt(anneeRecente)){
				anneeRecente = nom;
			}
		}

		File dossierAnnee = null;
		try {
			dossierAnnee = new File(LecteurBase.class.getResource("../../Base de Données/Services/" + anneeRecente + "/").toURI());
		} catch (Exception e){
			e.printStackTrace();
		}

		File[] contenuDossierAnnee = dossierAnnee.listFiles();

		String moisRecent = "0";

		for(File contenu : contenuDossierAnnee){
			String nom = contenu.getName();

			if(Integer.parseInt(nom) > Integer.parseInt(moisRecent)){
				moisRecent = nom;
			}
		}

		File dossierMois = null;
		try {
			dossierMois = new File(LecteurBase.class.getResource("../../Base de Données/Services/" + anneeRecente + "/" + moisRecent + "/").toURI());
		} catch (Exception e){
			e.printStackTrace();
		}

		File[] contenuDossierMois = dossierMois.listFiles();

		String jourRecent = "0";

		for(File contenu : contenuDossierMois){
			String nom = contenu.getName();

			if(Integer.parseInt(nom) > Integer.parseInt(jourRecent)){
				jourRecent = nom;
			}
		}

		if(!((anneeRecente.equals("0"))&&(moisRecent.equals("0"))&&(jourRecent.equals("0")))){
			Calendar calendrier = GregorianCalendar.getInstance();
			calendrier.set(Integer.parseInt(anneeRecente), Integer.parseInt(moisRecent) - 1, Integer.parseInt(jourRecent));
			dateDernierService = calendrier.getTime();
		}

		return(dateDernierService);
	}

	public Membre getOrdiDernierService(){
		Membre ordiDernierService = new Membre();

		Date dateDernierService = getDateDernierService();

		if(!dateDernierService.equals(new Date(0))){
			Service dernierService = LecteurBase.lireService(dateDernierService);
			ordiDernierService = dernierService.getOrdi();
		}

		return(ordiDernierService);
	}

	public List<Membre> getCommisDernierService(){
		List<Membre> commisDernierService = new ArrayList<Membre>();

		Date dateDernierService = getDateDernierService();

		if(!dateDernierService.equals(new Date(0))){
			Service dernierService = LecteurBase.lireService(dateDernierService);
			commisDernierService = dernierService.getCommis();
		}

		return(commisDernierService);
	}

	public List<Membre> getConfectionDernierService(){
		List<Membre> confectionDernierService = new ArrayList<Membre>();

		Date dateDernierService = getDateDernierService();

		if(!dateDernierService.equals(new Date(0))){
			Service dernierService = LecteurBase.lireService(dateDernierService);
			confectionDernierService = dernierService.getConfection();
		}

		return(confectionDernierService);
	}

	public float getNbBaguettesRestantes(){
		float restantes = nbBaguettesBase - nbBaguettesUtilisees;
		if(restantes <= 0){
			return(0);
		}
		else {
			return(restantes);
		}
	}

	public int getDernierNumeroCommande(){
		int dernier = 0;
		for(Commande commande : commandes){
			if(commande.getNumero() > dernier){
				dernier = commande.getNumero();
			}
		}
		return(dernier);
	}

	public void setOrdi(Membre membre){
		ordi = membre;

		ecrireFichier();
	}

	public void setCommis(List<Membre> commis){
		this.commis = commis;

		ecrireFichier();
	}

	public void setConfection(List<Membre> confection){
		this.confection = confection;

		ecrireFichier();
	}

	public void setNbBaguettesAchetees(float nb){
		nbBaguettesAchetees = nb;
		nbBaguettesBase = nbBaguettesAchetees - nbBaguettesReservees;

		ecrireFichier();
	}

	public void setNbBaguettesReservees(float nb){
		nbBaguettesReservees = nb;
		nbBaguettesBase = nbBaguettesAchetees - nbBaguettesReservees;

		ecrireFichier();
	}

	public void addCommis(Membre membre){
		commis.add(membre);

		ecrireFichier();
	}

	public void addConfection(Membre membre){
		confection.add(membre);

		ecrireFichier();
	}

	public void retirerCommande(Commande commande){
		CreateurBase.retirerCommande(commande);
		commandes.remove(commande);
		nouvelleCommandeRetiree.set(commande);
	}

	public void commandeDonnee(CommandeAssignee commande){
		nouvelleCommandeDonnee.set(commande);
	}

	public void commandeRealisee(CommandeAssignee commande){
		nouvelleCommandeRealisee.set(commande);
	}

	public void assignerCommande(int numero, Membre membre){
		Commande commande = LecteurBase.lireCommande(date, numero);
		CommandeAssignee commandeAssignee = new CommandeAssignee(commande, membre, new Date(), false, new Date(0), false);

		CreateurBase.ajouterCommandeAssignee(commandeAssignee);

		commandes.remove(commande);
		commandes.add(LecteurBase.lireCommandeAssignee(date, commande.getNumero()));
		Collections.sort(commandes, new CompareCommande());
		nouvelleCommandeAssignee.set(commandeAssignee);

		ecrireFichier();
	}

	public void assignation(){
		List<Membre> membresOccupes = new ArrayList<Membre>();

		for(Commande commande : commandes){
			if(commande.getClass().equals(CommandeAssignee.class)){
				CommandeAssignee commandeAssignee = (CommandeAssignee)commande;
				if(!commandeAssignee.getEstRealisee()){
					membresOccupes.add(commandeAssignee.getMembre());
				}
			}
		}

		for(Membre confection : confection){
			if(!membresOccupes.contains(confection)){
				Commande commandeNonAssignee = null;
				int numero = Integer.MAX_VALUE;

				for(Commande commande : commandes){
					if(!(commande instanceof CommandeAssignee)){
						if(commande.getNumero() < numero){
							commandeNonAssignee = commande;
							numero = commande.getNumero();
						}
					}
				}

				if(commandeNonAssignee!=null){
					assignerCommande(commandeNonAssignee.getNumero(), confection);
					membresOccupes.add(confection);
				}
			}
		}
	}

	public void ajouterCommande(Commande commande){
		CreateurBase.ajouterCommande(commande);

		commandes.add(commande);
		nouvelleCommande.set(commande);

		if(commande.getPlat().getUtilisePain()){
			nbBaguettesUtilisees += 0.5;
		}

		assignation();

		ecrireFichier();
	}

	private void chargerCommandes(){
		commandes = new ArrayList<Commande>();

		SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		SimpleDateFormat mois = new SimpleDateFormat("MM");
		SimpleDateFormat jour = new SimpleDateFormat("dd");

		File dossier = null;
		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Services/").toURI());
		} catch (Exception e){
			e.printStackTrace();
		}

		try {
			Files.createDirectories(Paths.get(dossier + "/" + annee.format(date) + "/" + mois.format(date) + "/" + jour.format(date) + "/"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		File dossierCommandes = new File(dossier + "/" + annee.format(date) + "/" + mois.format(date) + "/" + jour.format(date) + "/");
		File[] listOfFiles = dossierCommandes.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()&&(FilenameUtils.getExtension(listOfFiles[i].getName()).equals("json"))&&!(FilenameUtils.removeExtension(listOfFiles[i].getName()).equals("_service"))) {
				String nom = FilenameUtils.removeExtension(listOfFiles[i].getName());
				int numero = Integer.parseInt(nom);

				if(LecteurBase.estAssignee(date, numero)){
					CommandeAssignee commande = LecteurBase.lireCommandeAssignee(date, numero);
					commandes.add(commande);
				}
				else {
					Commande commande = LecteurBase.lireCommande(date, numero);
					commandes.add(commande);
				}
	    	}
		}

		Collections.sort(commandes, new CompareCommande());
	}

	public void recharger(){
		chargerCommandes();

		nbBaguettesUtilisees = 0;
		for(Commande commande : commandes){
			if(commande.getPlat().getUtilisePain()){
				nbBaguettesUtilisees += 0.5;
			}
		}
	}

	public void ecrireFichier(){
		CreateurBase.ajouterService(this);
	}

	public void affCommandes(){
		for(Commande commande : commandes){
			System.out.println(commande);
		}
	}

	public void	affMembres(){
		System.out.println("Ordi : " + ordi);
		System.out.println("Commis :");
		for(Membre membreCommis : commis){
			System.out.println("- " + membreCommis);
		}
		System.out.println("Confection :");
		for(Membre membreConfection : confection){
			System.out.println("- " + membreConfection);
		}
	}

	public final Commande getNouvelleCommande(){
		return(nouvelleCommande.get());
	}

	public final void setNouvelleCommande(Commande commande){
		nouvelleCommande.set(commande);
	}

	public final ObjectProperty<Commande> nouvelleCommandePropriete(){
		return(nouvelleCommande);
	}

	public final CommandeAssignee getNouvelleCommandeAssignee(){
		return(nouvelleCommandeAssignee.get());
	}

	public final void setNouvelleCommandeAssignee(CommandeAssignee commande){
		nouvelleCommandeAssignee.set(commande);
	}

	public final ObjectProperty<CommandeAssignee> nouvelleCommandeAssigneePropriete(){
		return(nouvelleCommandeAssignee);
	}

	public final CommandeAssignee getNouvelleCommandeRealisee(){
		return(nouvelleCommandeRealisee.get());
	}

	public final void setNouvelleCommandeRealisee(CommandeAssignee commande){
		nouvelleCommandeRealisee.set(commande);
	}

	public final ObjectProperty<CommandeAssignee> nouvelleCommandeRealiseePropriete(){
		return(nouvelleCommandeRealisee);
	}

	public final CommandeAssignee getNouvelleCommandeDonnee(){
		return(nouvelleCommandeDonnee.get());
	}

	public final void setNouvelleCommandeDonnee(CommandeAssignee commande){
		nouvelleCommandeDonnee.set(commande);
	}

	public final ObjectProperty<CommandeAssignee> nouvelleCommandeDonneePropriete(){
		return(nouvelleCommandeDonnee);
	}

	public final Commande getNouvelleCommandeRetiree(){
		return(nouvelleCommandeRetiree.get());
	}

	public final void setNouvelleCommandeRetiree(Commande commande){
		nouvelleCommandeRetiree.set(commande);
	}

	public final ObjectProperty<Commande> nouvelleCommandeRetireePropriete(){
		return(nouvelleCommandeRetiree);
	}
}
