package kfetinfo.core;

import java.io.File;

public class Parametres {
	float prixIngredientSupp;
	float prixBoisson;
	float reducMenu;

	public Parametres(){
		File dossier = null;
		try {
			dossier = new File(LecteurBase.class.getResource("../../Base de Données/Paramètres/").toURI());
		} catch (Exception e){
			e.printStackTrace();
		}

		File f = new File(dossier + "/" + "paramètres.json");
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

	public void setPrixIngredientSupp(float prix){
		prixIngredientSupp = prix;
	}

	public void setPrixBoisson(float prix){
		prixBoisson = prix;
	}

	public void setReducMenu(float reduc){
		reducMenu = reduc;
	}

	public void ecrireFichier(){
		CreateurBase.ajouterParametres(prixIngredientSupp, prixBoisson, reducMenu);
	}
}
