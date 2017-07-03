package kfetinfo;

public class Plat extends ContenuCommande {
	int nbMaxIngredients;
	int nbMaxSauces;
	float prix;

	public Plat(String id, String nom, float cout, boolean estDisponible, int nbTotalUtilisations, int priorite, int nbMaxIngredients, int nbMaxSauces, float prix){
		super(id, nom, cout, estDisponible, nbTotalUtilisations, priorite);
		this.nbMaxIngredients = nbMaxIngredients;
		this.nbMaxSauces = nbMaxSauces;
		this.prix = prix;
	}
}
