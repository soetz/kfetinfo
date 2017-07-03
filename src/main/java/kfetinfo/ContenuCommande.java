package kfetinfo;

public class ContenuCommande {
	String id;
	String nom;
	float cout;
	boolean estDisponible;
	int nbTotalUtilisations;
	int priorite;

	public ContenuCommande(String id, String nom, float cout, boolean estDisponible, int nbTotalUtilisations, int priorite){
		this.id = id;
		this.nom = nom;
		this.cout = cout;
		this.estDisponible = estDisponible;
		this.nbTotalUtilisations = nbTotalUtilisations;
		this.priorite = priorite;
	}
}
