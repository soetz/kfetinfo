package kfetinfo;

public class Boisson extends ContenuCommande {
	SupplementBoisson supplement;

	public Boisson(String id, String nom, float cout, boolean estDisponible, int nbTotalUtilisations, int priorite, SupplementBoisson supplement){
		super(id, nom, cout, estDisponible, nbTotalUtilisations, priorite);
		this.supplement = supplement;
	}
}
