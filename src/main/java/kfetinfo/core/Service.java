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

import org.apache.commons.io.FilenameUtils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <p>Service est une classe décrivant un service effectué à la K'Fet.</p>
 * <p>Un service est identifié par sa {@code date}. Un objet {@code Service} décrit les commandes qui ont été formulées pendant ce service, les stocks de pain dont le service disposait ainsi que les membres qui y ont participé et leurs postes.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public class Service {

	//propriétés javafx de la classe, que l'on utilise pour des raisons de performances
	private final ObjectProperty<Commande> nouvelleCommande = new SimpleObjectProperty<Commande>();
	private final ObjectProperty<CommandeAssignee> nouvelleCommandeAssignee = new SimpleObjectProperty<CommandeAssignee>();
	private final ObjectProperty<CommandeAssignee> nouvelleCommandeRealisee = new SimpleObjectProperty<CommandeAssignee>();
	private final ObjectProperty<CommandeAssignee> nouvelleCommandeDonnee = new SimpleObjectProperty<CommandeAssignee>();	
	private final ObjectProperty<Commande> nouvelleCommandeRetiree = new SimpleObjectProperty<Commande>();

	//attributs de la classe
	private Date date;
	private List<Commande> commandes;
	private float nbBaguettesBase;
	private float nbBaguettesAchetees;
	private float nbBaguettesReservees;
	private float nbBaguettesUtilisees;
	private Membre ordi;
	private List<Membre> commis;
	private List<Membre> confection;

	/**
	 * <p>Constructeur Service.</p>
	 * <p>S'il y a déjà un service à cette date dans la base de données :
	 * <ul><li>Constructeur qui initialise ce service avec les valeurs de l'ancien service qui se trouvait dans la base de données.</li></ul>
	 * Sinon :
	 * <ul><li>Constructeur qui initialise ce service avec des valeurs par défaut mais va également chercher dans la base de données le service le plus récent et s'il existe, en garde les valeurs pour la gestion du pain et de l'équipe.</li></ul></p>
	 * 
	 * @param date la date à laquelle est effectuée ce service (le plus souvent la date actuelle).
	 */
	public Service(Date date){

		final SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		final SimpleDateFormat mois = new SimpleDateFormat("MM");
		final SimpleDateFormat jour = new SimpleDateFormat("dd");

		File dossier = null;

		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Services/").toURI()); //on définit le dossier de lecture sur le dossier Services
		} catch (Exception e) {
			e.printStackTrace();
		}

		File fichier = new File(dossier + "/" + annee.format(date) + "/" + mois.format(date) + "/" + jour.format(date) + "/" + "_service.json"); //on crée une nouvelle référence vers le supposé fichier correspondant à ce service

		if(fichier.exists()){ //et s'il existe
			Service vieux = LecteurBase.lireService(date);

			this.date = date;
			commandes = new ArrayList<Commande>();
			nbBaguettesUtilisees = vieux.getNbBaguettesUtilisees(); //on récupère ses attributs pour les affecter à ce service
			nbBaguettesAchetees = vieux.getNbBaguettesAchetees();
			nbBaguettesReservees = vieux.getNbBaguettesReservees();
			nbBaguettesBase = nbBaguettesAchetees - nbBaguettesReservees;

			ordi = vieux.getOrdi();

			commis = new ArrayList<Membre>();
			for(Membre membre : vieux.getCommis()){
				if((!membre.getId().equals(Membre.ID_MEMBRE_DEFAUT))&&BaseDonnees.getMembres().contains(membre)){
					commis.add(membre);
				}
			}

			confection = new ArrayList<Membre>();
			for(Membre membre : vieux.getConfection()){
				if((!membre.getId().equals(Membre.ID_MEMBRE_DEFAUT))&&BaseDonnees.getMembres().contains(membre)){
					confection.add(membre);
				}
			}
		} else { //sinon
			this.date = date;
			commandes = new ArrayList<Commande>();
			nbBaguettesUtilisees = 0; //on initialise le service avec des attributs par défaut
			nbBaguettesAchetees = getNbBaguettesAcheteesDernierService(); //et on va chercher les valeurs du service le plus récent s'il existe pour d'autres attributs
			nbBaguettesReservees = getNbBaguettesReserveesDernierService();
			nbBaguettesBase = nbBaguettesAchetees - nbBaguettesReservees;
			ordi = getOrdiDernierService();
			commis = getCommisDernierService();
			confection = getConfectionDernierService();
		}

		try {
			Files.createDirectories(Paths.get(dossier + "/" + annee.format(date) + "/" + mois.format(date) + "/" + jour.format(date) + "/")); //on crée un dossier pour accueillir le fichier correspondant à ce service
		} catch (Exception e) {
			e.printStackTrace();
		}

		recharger(); //on recharge la liste des commandes et le nombre de baguettes utilisées
		ecrireFichier(); //on ajoute le fichier de ce service à la base de données
	}

	/**
	 * <p>Constructeur Service.</p>
	 * <p>Constructeur permettant d'initialiser le service avec des valeurs prédéfinies passées en paramètres.</p>
	 * <p>Tous les paramètres sont appliqués aux attributs de l'objet directement.</p>
	 * 
	 * @param date la date du service.
	 * @param commandes une {@code List<Commande>} décrivant l'ensemble des commandes formulées pendant le service.
	 * @param nbBaguettesUtilisees le nombre de baguettes utilisées pendant le service.
	 * @param nbBaguettesAchetees le nombre de baguettes achetées pour le service.
	 * @param nbBaguettesReservees le nombre de baguettes réservées par les membres participant au service.
	 * @param ordi le membre chargé de prendre les commandes pendant le service.
	 * @param commis une {@code List<Membre>} contenant les membres qui sont chargés d'assembler les commandes et de préparer les boissons et les desserts lors du service.
	 * @param confection une {@code List<Membre>} contenant les membres qui sont chargés de confectionner les plats lors du service.
	 */
	public Service(Date date, List<Commande> commandes, float nbBaguettesUtilisees, float nbBaguettesAchetees, float nbBaguettesReservees, Membre ordi, List<Membre> commis, List<Membre> confection){

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

	/**
	 * Renvoie le {@code nbBaguettesBase} de ce service.
	 * 
	 * @return le nombre de baguettes utilisables de ce service au début du service.
	 */
	public float getNbBaguettesBase(){

		return(nbBaguettesBase);
	}

	/**
	 * Renvoie le {@code nbBaguettesUtilisees} de ce service.
	 * 
	 * @return le nombre total de baguettes qui ont été utilisées au cours de ce service (pour l'instant).
	 */
	public float getNbBaguettesUtilisees(){

		return(nbBaguettesUtilisees);
	}

	/**
	 * Renvoie le {@code nbBaguettesAchetees} de ce service.
	 * 
	 * @return le nombre de baguettes qui ont été achetées afin de réaliser ce service.
	 */
	public float getNbBaguettesAchetees(){

		return(nbBaguettesAchetees);
	}

	/**
	 * Renvoie le {@code nbBaguettesReservees} de ce service.
	 * 
	 * @return le nombre de baguettes qui ont été réservées par les membres effectuant le service.
	 */
	public float getNbBaguettesReservees(){

		return(nbBaguettesReservees);
	}

	/**
	 * Renvoie la {@code date} de ce service.
	 * 
	 * @return la date de ce service.
	 */
	public Date getDate(){

		return(date);
	}

	/**
	 * Renvoie une estimation de la quantité d'argent qui a été nécessaire pour réaliser les commandes de ce service.
	 * 
	 * @return le coût de ce service.
	 */
	public float getCout(){

		float cout = 0;

		for(Commande commande : commandes){
			cout += commande.getCout();
		}

		cout += Parametres.getCoutPain()*getNbBaguettesRestantes(); //on oublie pas d'ajouter le coût des baguettes si jamais on ne les a pas toutes utilisées

		return(cout);
	}

	/**
	 * Renvoie une estimation de la quantité d'argent que le service a rapporté.
	 * 
	 * @return les revenus tirés du service.
	 */
	public float getRevenu(){

		float revenu = 0;

		for(Commande commande : commandes){
			revenu += commande.getPrix();
		}
		
		revenu -= getCout();

		return(revenu);
	}

	/**
	 * Renvoie le nombre de commandes formulées lors du service.
	 * 
	 * @return le nombre de commandes du service.
	 */
	public int getNbCommandes(){

		return(commandes.size());
	}

	/**
	 * Renvoie la {@code List<Commande>} décrivant la liste des commandes formulées pendant ce service.
	 * 
	 * @return la liste des commandes.
	 */
	public List<Commande> getCommandes(){

		return(commandes);
	}

	/**
	 * Renvoie la {@code Commande} du service ayant le numéro passé en paramètres.
	 * 
	 * @param numero le numéro de la commande à renvoyer.
	 * 
	 * @return la commande portant le numéro passé en paramètres.
	 */
	public Commande getCommande(int numero){

		Commande commande = null;

		for(Commande commandeListe : commandes){
			if(commandeListe.getNumero() == numero){
				commande = commandeListe;
			}
		}

		return(commande);
	}

	/**
	 * Renvoie l'{@code ordi} du service.
	 * 
	 * @return le membre chargé de prendre les commandes pendant le service.
	 */
	public Membre getOrdi(){

		return(ordi);
	}

	/**
	 * Renvoie la {@code List<Membre>} contenant les membres qui sont chargés d'assembler les commandes et de préparer les boissons et les desserts lors du service.
	 * 
	 * @return la liste des commis du service.
	 */
	public List<Membre> getCommis(){

		return(commis);
	}

	/**
	 * Renvoie la {@code List<Membre>} contenant les membres qui sont chargés de confectionner les plats lors du service.
	 * 
	 * @return la liste des confection du service.
	 */
	public List<Membre> getConfection(){
		return(confection);
	}

	/**
	 * Renvoie le nombre de baguettes restantes.
	 * 
	 * @return le nombre de baguettes restantes.
	 */
	public float getNbBaguettesRestantes(){

		float restantes = nbBaguettesBase - nbBaguettesUtilisees;

		if(restantes < 0){
			restantes = 0;
		}

		return(restantes);
	}

	/**
	 * Renvoie le plus grand numéro de commande de la liste du service.
	 * 
	 * @return le numéro de la dernière commande formulée.
	 */
	public int getDernierNumeroCommande(){

		int dernier = 0;

		for(Commande commande : commandes){
			if(commande.getNumero() > dernier){
				dernier = commande.getNumero();
			}
		}

		return(dernier);
	}

	/**
	 * Renvoie la date du dernier service de la base de données.
	 * 
	 * @return la date du dernier service.
	 */
	private Date getDateDernierService(){

		Date dateDernierService = new Date(0);

		File dossierServices = null;
		try {
			dossierServices = new File(LecteurBase.class.getResource("../../Base de Données/Services/").toURI()); //on définit le dossier de lecture sur le dossier Services
		} catch (Exception e) {
			e.printStackTrace();
		}

		File[] contenuDossierServices = dossierServices.listFiles();

		String anneeRecente = "0";

		for(File contenu : contenuDossierServices){ //parmis la liste des fichiers et dossiers contenus dans le dossier Services,
			String nom = contenu.getName();

			if(Integer.parseInt(nom) > Integer.parseInt(anneeRecente)){ //on affecte celui qui a le nom correspondant au plus grand nombre à la variable anneeRecente
				anneeRecente = nom;
			}
		}

		File dossierAnnee = null;
		try {
			dossierAnnee = new File(LecteurBase.class.getResource("../../Base de Données/Services/" + anneeRecente + "/").toURI()); //on définit le dossier de lecture sur le dossier Services/anneeRecente
		} catch (Exception e) {
			e.printStackTrace();
		}

		File[] contenuDossierAnnee = dossierAnnee.listFiles();

		String moisRecent = "0";

		for(File contenu : contenuDossierAnnee){ //parmis la liste des fichiers et dossiers contenus dans le dossier Services/anneeRecente,
			String nom = contenu.getName();

			if(Integer.parseInt(nom) > Integer.parseInt(moisRecent)){ //on affecte celui qui a le nom correspondant au plus grand nombre à la variable moisRecent
				moisRecent = nom;
			}
		}

		File dossierMois = null;
		try {
			dossierMois = new File(LecteurBase.class.getResource("../../Base de Données/Services/" + anneeRecente + "/" + moisRecent + "/").toURI()); //on définit le dossier de lecture sur le dossier Services/anneeRecente/moisRecent
		} catch (Exception e) {
			e.printStackTrace();
		}

		File[] contenuDossierMois = dossierMois.listFiles();

		String jourRecent = "0";

		for(File contenu : contenuDossierMois){ //parmis la liste des fichiers et dossiers contenus dans le dossier Services/anneeRecente/moisRecent,
			String nom = contenu.getName();

			if(Integer.parseInt(nom) > Integer.parseInt(jourRecent)){ //on affecte celui qui a le nom correspondant au plus grand nombre à la variable jourRecent
				jourRecent = nom;
			}
		}

		if(!((anneeRecente.equals("0"))&&(moisRecent.equals("0"))&&(jourRecent.equals("0")))){ //si aucune des variables n'est égale à 0, c'est à dire si un fichier ou dossier correspond à chaque étape,
			Calendar calendrier = GregorianCalendar.getInstance();
			calendrier.set(Integer.parseInt(anneeRecente), Integer.parseInt(moisRecent) - 1, Integer.parseInt(jourRecent)); //on définit le jour du calendrier sur anneeRecente/moisRecent/jourRecent (et oui on fait -1 au mois car Janvier est le mois 0)
			dateDernierService = calendrier.getTime(); //puis on extrait la date
		}

		return(dateDernierService);
	}

	/**
	 * Renvoie le {@code nbBaguettesAchetees} du dernier service.
	 * 
	 * @return le nombre de baguettes achetées pour le dernier service en date.
	 */
	private float getNbBaguettesAcheteesDernierService(){

		float nbBaguettesAcheteesDernierService = 0;

		Date dateDernierService = getDateDernierService();

		if(!dateDernierService.equals(new Date(0))){
			Service dernierService = LecteurBase.lireService(dateDernierService);
			nbBaguettesAcheteesDernierService = dernierService.getNbBaguettesAchetees();
		}

		return(nbBaguettesAcheteesDernierService);
	}

	/**
	 * Renvoie le {@code nbBaguettesReservees} du dernier service.
	 * 
	 * @return le nombre de baguettes reservées pour le dernier service en date.
	 */
	private float getNbBaguettesReserveesDernierService(){

		float nbBaguettesReserveesDernierService = 0;

		Date dateDernierService = getDateDernierService();

		if(!dateDernierService.equals(new Date(0))){
			Service dernierService = LecteurBase.lireService(dateDernierService);
			nbBaguettesReserveesDernierService = dernierService.getNbBaguettesReservees();
		}

		return(nbBaguettesReserveesDernierService);
	}

	/**
	 * Renvoie l'{@code ordi} du dernier service.
	 * 
	 * @return l'ordi du dernier service en date.
	 */
	private Membre getOrdiDernierService(){
		Membre ordiDernierService = new Membre();

		Date dateDernierService = getDateDernierService();

		if(!dateDernierService.equals(new Date(0))){
			Service dernierService = LecteurBase.lireService(dateDernierService);
			ordiDernierService = dernierService.getOrdi();
		}

		return(ordiDernierService);
	}

	/**
	 * Renvoie la {@code List<Membre>} correspondant à la liste des commis du dernier service.
	 * 
	 * @return la liste des commis du dernier service en date.
	 */
	private List<Membre> getCommisDernierService(){

		List<Membre> commisDernierService = new ArrayList<Membre>();

		Date dateDernierService = getDateDernierService();

		if(!dateDernierService.equals(new Date(0))){
			Service dernierService = LecteurBase.lireService(dateDernierService);
			
			for(Membre membre : dernierService.getCommis()){
				if((!membre.getId().equals(Membre.ID_MEMBRE_DEFAUT))&&(BaseDonnees.getMembres().contains(membre))){
					commisDernierService.add(membre);
				}
			}
		}

		return(commisDernierService);
	}

	/**
	 * Renvoie la {@code List<Membre>} correspondant à la liste des confection du dernier service.
	 * 
	 * @return la liste des confection du dernier service en date.
	 */
	private List<Membre> getConfectionDernierService(){

		List<Membre> confectionDernierService = new ArrayList<Membre>();

		Date dateDernierService = getDateDernierService();

		if(!dateDernierService.equals(new Date(0))){
			Service dernierService = LecteurBase.lireService(dateDernierService);

			for(Membre membre : dernierService.getConfection()){
				if((!membre.getId().equals(Membre.ID_MEMBRE_DEFAUT))&&(BaseDonnees.getMembres().contains(membre))){
					confectionDernierService.add(membre);
				}
			}
		}

		return(confectionDernierService);
	}

	/**
	 * Modifie le {@code nbBaguettesAchetees} du service en lui affectant la valeur passée en paramètres.
	 * 
	 * @param nb le nombre de baguettes achetées pour le service.
	 */
	public void setNbBaguettesAchetees(float nb){

		nbBaguettesAchetees = nb;
		nbBaguettesBase = nbBaguettesAchetees - nbBaguettesReservees;

		ecrireFichier();
	}

	/**
	 * Modifie le {@code nbBaguettesReservees} du service en lui affectant la valeur passée en paramètres.
	 * 
	 * @param nb le nombre de baguettes réservées avant le service.
	 */
	public void setNbBaguettesReservees(float nb){

		nbBaguettesReservees = nb;
		nbBaguettesBase = nbBaguettesAchetees - nbBaguettesReservees;

		ecrireFichier();
	}

	/**
	 * Modifie l'{@code ordi} du service en lui affectant la valeur passée en paramètres.
	 * 
	 * @param membre le nouvel ordi du service.
	 */
	public void setOrdi(Membre membre){

		ordi = membre;

		ecrireFichier();
	}

	/**
	 * Modifie la {@code List<Membre>} contenant les membres qui sont chargés d'assembler les commandes et de préparer les boissons et les desserts lors du service.
	 * 
	 * @param commis la liste des nouveaux commis du service.
	 */
	public void setCommis(List<Membre> commis){

		this.commis = commis;

		ecrireFichier();
	}

	/**
	 * Modifie la {@code List<Membre>} contenant les membres qui sont chargés de confectionner les plats lors du service.
	 * 
	 * @param commis la liste des nouveaux confection du service.
	 */
	public void setConfection(List<Membre> confection){

		this.confection = confection;

		ecrireFichier();
	}

	/**
	 * Ajoute un nouveau membre à la {@code List<Membre>} contenant les membres qui sont chargés d'assembler les commandes et de préparer les boissons et les desserts lors du service.
	 * 
	 * @param membre le membre à ajouter à la liste des commis.
	 */
	public void addCommis(Membre membre){

		commis.add(membre);

		ecrireFichier();
	}

	/**
	 * Ajoute un nouveau membre à la {@code List<Membre>} contenant les membres qui sont chargés de confectionner les plats lors du service.
	 * 
	 * @param membre le membre à ajouter à la liste des confection.
	 */
	public void addConfection(Membre membre){

		confection.add(membre);

		ecrireFichier();
	}

	/**
	 * Affiche la liste des {@code Commande} du service.
	 */
	public void affCommandes(){

		for(Commande commande : commandes){
			System.out.println(commande);
		}
	}

	/**
	 * Affiche la liste des {@code Membre} participant au service.
	 */
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

	/**
	 * Recharge la liste des commandes du service depuis la base de données.
	 */
	private void chargerCommandes(){

		commandes = new ArrayList<Commande>();

		SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		SimpleDateFormat mois = new SimpleDateFormat("MM");
		SimpleDateFormat jour = new SimpleDateFormat("dd");

		File dossier = null;
		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Services/").toURI()); //on définit le dossier de lecture sur le dossier Services
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Files.createDirectories(Paths.get(dossier + "/" + annee.format(date) + "/" + mois.format(date) + "/" + jour.format(date) + "/")); //on crée les dossiers correspondant à la date de ce service au cas où il n'existe pas
		} catch(Exception e) {
			e.printStackTrace();
		}

		File dossierCommandes = new File(dossier + "/" + annee.format(date) + "/" + mois.format(date) + "/" + jour.format(date) + "/");
		File[] listOfFiles = dossierCommandes.listFiles();

		for(File fichier : listOfFiles){ //pour chaque fichier ou dossier du dossier de ce service,
			if(fichier.isFile()&&(FilenameUtils.getExtension(fichier.getName()).equals("json"))&&!(FilenameUtils.removeExtension(fichier.getName()).equals("_service"))){ //s'il s'agit d'un fichier, que son extension est json et que son nom n'est pas _service,
				String nom = FilenameUtils.removeExtension(fichier.getName());
				int numero = Integer.parseInt(nom);

				if(LecteurBase.estAssignee(date, numero)){ //on la lit et on l'ajoute à la liste des commandes
					CommandeAssignee commande = LecteurBase.lireCommandeAssignee(date, numero);
					commandes.add(commande);
				} else {
					Commande commande = LecteurBase.lireCommande(date, numero);
					commandes.add(commande);
				}
	    	}
		}

		Collections.sort(commandes, new CompareCommande()); //on trie la liste des commandes afin qu'elles apparaissent dans l'ordre
	}

	/**
	 * Recharge la liste des commandes et met à jour le compte de baguettes utilisées du service.
	 */
	public void recharger(){

		chargerCommandes();

		nbBaguettesUtilisees = 0;
		for(Commande commande : commandes){
			if(commande.getPlat().getUtilisePain()){
				nbBaguettesUtilisees += 0.5;
			}
		}
	}

	/**
	 * Écrit ou met à jour le fichier correspondant à ce service dans la base de données.
	 */
	public void ecrireFichier(){

		CreateurBase.ajouterService(this);
	}

	/**
	 * Ajoute la {@code Commande} passée en paramètres à ce service.
	 * 
	 * @param commande la commande à ajouter au service.
	 */
	public void ajouterCommande(Commande commande){

		CreateurBase.ajouterCommande(commande); //on ajoute la commande à la base de données,

		commandes.add(commande); //et à la liste des commandes
		nouvelleCommande.set(commande); //on définit la propriété javafx de la nouvelle commande ajoutée sur cette commande

		if(commande.getPlat().getUtilisePain()){
			nbBaguettesUtilisees += 0.5;
		}

		assignation(); //on met à jour les commandes en train d'être réalisées

		ecrireFichier();
	}

	/**
	 * Transforme la {@code Commande} dont le numéro est passé en paramètres en {@code CommandeAssignee} assignée au membre passé en paramètres.
	 * 
	 * @param numero le numéro de la commande à assigner.
	 * @param membre le membre à qui on assigne la commande.
	 */
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

	/**
	 * Met à jour l'assignation des commandes du service, c'est à dire que s'il y a un membre participant au service qui n'a pas de commande d'assignée et s'il y a une commande qui n'est pas encore assignée, elle est assignée à ce membre.
	 */
	public void assignation(){

		List<Membre> membresOccupes = new ArrayList<Membre>();

		for(Commande commande : commandes){
			if(commande.getClass().equals(CommandeAssignee.class)){ //on établit déjà la liste des membres qui sont occupés
				CommandeAssignee commandeAssignee = (CommandeAssignee)commande;
				if(!commandeAssignee.getEstRealisee()){
					membresOccupes.add(commandeAssignee.getMembre());
				}
			}
		}

		for(Membre confection : confection){ //et pour chaque membre affecté à la confection des plats,
			if(!membresOccupes.contains(confection)){ //s'il n'est pas dans la liste des membres occupés,
				Commande commandeNonAssignee = null;
				int numero = Integer.MAX_VALUE;

				for(Commande commande : commandes){ //pour chaque commande de la liste des commandes,
					if(!(commande instanceof CommandeAssignee)){ //si elle n'est pas déjà assignée,
						if(commande.getNumero() < numero){ //si son numéro est plus petit que celui de la commande qui a le plus petit numéro qui remplit ces conditions, on l'affecte à la variable commandeNonAssignee
							commandeNonAssignee = commande;
							numero = commande.getNumero();
						}
					}
				}

				if(commandeNonAssignee != null){ //si commandeNonAssignee n'est pas null,
					assignerCommande(commandeNonAssignee.getNumero(), confection); //on l'assigne au membre et on ajoute le membre à la liste des membres occupés
					membresOccupes.add(confection);
				}
			}
		}
	}

	/**
	 * Définit une commande comme ayant été réalisée par le membre en charge de sa confection.
	 * 
	 * @param commande la commande réalisée.
	 */
	public void commandeRealisee(CommandeAssignee commande){

		nouvelleCommandeRealisee.set(commande); //on définit la propriété javafx de la nouvelle commande assignée ajoutée sur cette commande
	}

	/**
	 * Définit une commande comme ayant été donnée au client.
	 * 
	 * @param commande la commande donnée.
	 */
	public void commandeDonnee(CommandeAssignee commande){

		nouvelleCommandeDonnee.set(commande); //on définit la propriété javafx de la nouvelle commande donnée ajoutée sur cette commande
	}

	/**
	 * Retire une commande de la liste des commandes et de la base de données (à utiliser en cas d'erreur lors de l'ajout d'une commande).
	 * 
	 * @param commande la commande à retirer.
	 */
	public void retirerCommande(Commande commande){

		CreateurBase.retirerCommande(commande);

		commandes.remove(commande);
		nouvelleCommandeRetiree.set(commande);

		if(commande.getPlat().getUtilisePain()){
			nbBaguettesUtilisees -= 0.5;
		}
	}

	/**
	 * Renvoie la valeur de la propriété javafx {@code nouvelleCommande}.
	 * 
	 * @return la valeur de la propriété nouvelle commande.
	 */
	public final Commande getNouvelleCommande(){

		return(nouvelleCommande.get());
	}

	/**
	 * Modifie la valeur de la propriété javafx {@code nouvelleCommande}.
	 * 
	 * @param commande la {@code Commande} à affecter à la valeur de la propriété.
	 */
	public final void setNouvelleCommande(Commande commande){

		nouvelleCommande.set(commande);
	}

	/**
	 * Renvoie la propriété javafx {@code nouvelleCommande}.
	 * 
	 * @return la propriété nouvelle commande.
	 */
	public final ObjectProperty<Commande> nouvelleCommandePropriete(){

		return(nouvelleCommande);
	}

	/**
	 * Renvoie la valeur de la propriété javafx {@code nouvelleCommandeAssignee}.
	 * 
	 * @return la valeur de la propriété nouvelle commande assignée.
	 */
	public final CommandeAssignee getNouvelleCommandeAssignee(){

		return(nouvelleCommandeAssignee.get());
	}

	/**
	 * Modifie la valeur de la propriété javafx {@code nouvelleCommandeAssignee}.
	 * 
	 * @param commande la {@code CommandeAssignee} à affecter à la valeur de la propriété.
	 */
	public final void setNouvelleCommandeAssignee(CommandeAssignee commande){

		nouvelleCommandeAssignee.set(commande);
	}

	/**
	 * Renvoie la propriété javafx {@code nouvelleCommandeAssignee}.
	 * 
	 * @return la propriété nouvelle commande assignée.
	 */
	public final ObjectProperty<CommandeAssignee> nouvelleCommandeAssigneePropriete(){

		return(nouvelleCommandeAssignee);
	}

	/**
	 * Renvoie la valeur de la propriété javafx {@code nouvelleCommandeRealisee}.
	 * 
	 * @return la valeur de la propriété nouvelle commande réalisée.
	 */
	public final CommandeAssignee getNouvelleCommandeRealisee(){

		return(nouvelleCommandeRealisee.get());
	}

	/**
	 * Modifie la valeur de la propriété javafx {@code nouvelleCommandeRealisee}.
	 * 
	 * @param commande la {@code CommandeAssignee} à affecter à la valeur de la propriété.
	 */
	public final void setNouvelleCommandeRealisee(CommandeAssignee commande){

		nouvelleCommandeRealisee.set(commande);
	}

	/**
	 * Renvoie la propriété javafx {@code nouvelleCommandeRealisee}.
	 * 
	 * @return la propriété nouvelle commande réalisée.
	 */
	public final ObjectProperty<CommandeAssignee> nouvelleCommandeRealiseePropriete(){

		return(nouvelleCommandeRealisee);
	}

	/**
	 * Renvoie la valeur de la propriété javafx {@code nouvelleCommandeDonnee}.
	 * 
	 * @return la valeur de la propriété nouvelle commande donnée.
	 */
	public final CommandeAssignee getNouvelleCommandeDonnee(){

		return(nouvelleCommandeDonnee.get());
	}

	/**
	 * Modifie la valeur de la propriété javafx {@code nouvelleCommandeDonnee}.
	 * 
	 * @param commande la {@code CommandeAssignee} à affecter à la valeur de la propriété.
	 */
	public final void setNouvelleCommandeDonnee(CommandeAssignee commande){

		nouvelleCommandeDonnee.set(commande);
	}

	/**
	 * Renvoie la propriété javafx {@code nouvelleCommandeDonnee}.
	 * 
	 * @return la propriété nouvelle commande donnée.
	 */
	public final ObjectProperty<CommandeAssignee> nouvelleCommandeDonneePropriete(){

		return(nouvelleCommandeDonnee);
	}

	/**
	 * Renvoie la valeur de la propriété javafx {@code nouvelleCommandeRetiree}.
	 * 
	 * @return la valeur de la propriété nouvelle commande retirée.
	 */
	public final Commande getNouvelleCommandeRetiree(){

		return(nouvelleCommandeRetiree.get());
	}

	/**
	 * Modifie la valeur de la propriété javafx {@code nouvelleCommandeRetiree}.
	 * 
	 * @param commande la {@code Commande} à affecter à la valeur de la propriété.
	 */
	public final void setNouvelleCommandeRetiree(Commande commande){

		nouvelleCommandeRetiree.set(commande);
	}

	/**
	 * Renvoie la propriété javafx {@code nouvelleCommandeRetiree}.
	 * 
	 * @return la propriété nouvelle commande retirée.
	 */
	public final ObjectProperty<Commande> nouvelleCommandeRetireePropriete(){

		return(nouvelleCommandeRetiree);
	}
}
