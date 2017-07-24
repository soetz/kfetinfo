package kfetinfo;

import java.io.File;

public class Parametres {
	static String path = new File("").getAbsolutePath();

	float prixIngredientSupp;
	float prixBoisson;
	float reducMenu;

	public Parametres(){
		File f = new File(path + "\\src\\main\\resources\\Base de Données\\Paramètres\\paramètres.json");
		if(f.exists() && !f.isDirectory()){
			Parametres old = LecteurBase.lireParametres();
			prixIngredientSupp = old.getPrixIngredientSupp();
			prixBoisson = old.getPrixBoisson();
			reducMenu = old.getReducMenu();
		}
		else {
			prixIngredientSupp = 0.3f;
			prixBoisson = 0.5f;
			reducMenu = 0.3f;
		}

		ecrireFichier();
	}

	public Parametres(float prixIngredientSupp, float prixBoisson, float reducMenu){
		this.prixIngredientSupp = prixIngredientSupp;
		this.prixBoisson = prixBoisson;
		this.reducMenu = reducMenu;
	}

	public float getPrixIngredientSupp(){
		return(prixIngredientSupp);
	}

	public float getPrixBoisson(){
		return(prixBoisson);
	}

	public float getReducMenu(){
		return(reducMenu);
	}

	public void ecrireFichier(){
		CreateurBase.ajouterParametres(prixIngredientSupp, prixBoisson, reducMenu);
	}
}
