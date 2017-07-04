package kfetinfo;

import java.util.Date;

import java.util.List;

public class Commande {
	Date moment;
	int numero;
	static int dernierNumero = 0;
	Plat plat;
	List<Ingredient> ingredients;
	List<Sauce> sauces;
	Boisson boisson;
	SupplementBoisson supplementBoisson;
	Dessert dessert;

	public Commande() {
		moment = new Date();
		numero = dernierNumero + 1;
		dernierNumero = numero;
	}

}
