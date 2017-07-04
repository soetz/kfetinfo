package kfetinfo;

import java.util.Comparator;

public class CompareContenuCommande implements Comparator<ContenuCommande> {

	public int compare(ContenuCommande arg0, ContenuCommande arg1) {
		if(arg0.getPriorite() == (arg1.getPriorite())){
			return(arg1.getNom().compareTo(arg0.getNom()));
		}
		else {
			return(arg0.getPriorite() - arg1.getPriorite());
		}
	}

}
