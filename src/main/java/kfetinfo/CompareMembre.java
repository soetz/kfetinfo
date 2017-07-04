package kfetinfo;

import java.util.Comparator;

public class CompareMembre implements Comparator<Membre> {

	public int compare(Membre arg0, Membre arg1) {
		return(arg0.getNom().compareTo(arg1.getNom()));
	}

}
