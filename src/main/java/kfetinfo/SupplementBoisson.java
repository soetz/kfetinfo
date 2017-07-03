package kfetinfo;

public class SupplementBoisson extends ContenuCommande {
	float prix;

	public SupplementBoisson(String id, String nom, float cout, boolean estDisponible, int nbTotalUtilisations, int priorite, float prix){
		super(id, nom, cout, estDisponible, nbTotalUtilisations, priorite);
		this.prix = prix;
	}
}
