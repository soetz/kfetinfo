package kfetinfo;

public class Boisson extends ContenuCommande {
	SupplementBoisson supplement;

	public Boisson(String id, String nom, float cout, boolean estDisponible, int nbTotalUtilisations, int priorite){
		super(id, nom, cout, estDisponible, nbTotalUtilisations, priorite);
	}
}
