package kfetinfo;

import java.util.Comparator;

public class CompareCommande implements Comparator<Commande> {

	public int compare(Commande arg0, Commande arg1) {
		return(arg0.getNumero() - arg1.getNumero());
	}

}
