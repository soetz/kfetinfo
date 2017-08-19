package kfetinfo.ui;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import kfetinfo.core.BaseDonnees;
import kfetinfo.core.Boisson;
import kfetinfo.core.Commande;
import kfetinfo.core.Core;
import kfetinfo.core.Dessert;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Plat;
import kfetinfo.core.Sauce;
import kfetinfo.core.SupplementBoisson;

/**
 * <p>Selection est une classe constituée uniquement d'attributs et de méthodes statiques relatifs à l'affichage de la section « sélection du contenu de la commande » du logiciel.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @author James_D de StackOverflow
 * @version 1.0
 */
public final class Selection {

	//classes de style pour l'utilisation du CSS
	public static final String LISTE_CONTENU_COMMANDE = "liste-contenu-commande";
	public static final String TITRE_GROUPE = "titre-groupe-contenu-commande";

	//constantes pour l'affichage
	private static final Double ESPACE_GROUPES = 4.0;
	private static final Double PADDING_SELECTION = 3.0;

	//variables utiles pour l'affichage du panneau de développement
	private static final boolean DEV = true;
	private static final long[] FRAME_TIMES = new long[100];
	private static int frameTimeIndex = 0 ;
	private static boolean arrayFilled = false ;

	//Label du compteur de baguettes restantes
	private static Label compteurBaguettes = new Label();

	//propriétés et listes décrivant les contenus commandes sélectionnés
	private static final ObjectProperty<Plat> PLAT_SELECTIONNE = new SimpleObjectProperty<Plat>();
	private static List<Ingredient> ingredientsSelectionnes;
	private static final BooleanProperty INGREDIENT_CHANGE = new SimpleBooleanProperty(); //propriété indiquant uniquement que la liste des ingrédients sélectionnés a changé
	private static List<Sauce> saucesSelectionnees;
	private static final BooleanProperty SAUCE_CHANGEE = new SimpleBooleanProperty(); //propriété indiquant uniquement que la liste des sauces sélectionnées a changé
	private static final ObjectProperty<Boisson> BOISSON_SELECTIONNEE = new SimpleObjectProperty<Boisson>();
	private static final ObjectProperty<SupplementBoisson> SUPPLEMENT_BOISSON_SELECTIONNE = new SimpleObjectProperty<SupplementBoisson>();
	private static final ObjectProperty<Dessert> DESSERT_SELECTIONNE = new SimpleObjectProperty<Dessert>();
	private static final BooleanProperty COMMANDE_CHANGEE = new SimpleBooleanProperty(); //propriété indiquant uniquement que le contenu de la commande a changé

	//listes utilisées pour détecter quel contenu est sélectionné (on vient chercher son index dans la liste)
	private static List<RadioButton> radiosPlats;
	private static List<CheckBox> checksIngredients;
	private static List<CheckBox> checksSauces;
	private static List<RadioButton> radiosBoissons;
	private static List<RadioButton> radiosSupplementsBoisson;
	private static List<RadioButton> radiosDesserts;

	//listes utilisées pour griser les contenus
	private static List<HBox> boxesPlats;
	private static List<HBox> boxesIngredients;
	private static VBox listeIngredients;
	private static List<HBox> boxesSauces;
	private static List<HBox> boxesBoissons;
	private static List<HBox> boxesSupplementsBoisson;
	private static List<HBox> boxesDesserts;

	//listes de booléens utilisées pour sauvegarder quels ingrédients et sauces sont disponibles (ceux-ci ayant des mécanismes de grisage différents)
	private static List<Boolean> ingredientsDispo;
	private static List<Boolean> saucesDispo;

	//liste permettant de ne pas utiliser deux fois une même touche mnémonique
	private static List<String> mnemoniquesUtilisees;

	//booléen indiquant que le chargement des contenus est terminé et qu'on peut commencer à déclencher des évènements lors de la sélection
	private static boolean chargementTermine;

	/**
	 * Crée une {@code Region} permettant de sélectionner le contenu de la nouvelle commande.
	 * 
	 * @param root le parent du panneau.
	 * @param core le core du système K'Fet.
	 * 
	 * @return le panneau de sélection de contenus commande.
	 */
	public static final Region selection(Region root, Core core){

		chargementTermine = false;

		ingredientsSelectionnes = new ArrayList<Ingredient>();
		saucesSelectionnees = new ArrayList<Sauce>();

		radiosPlats = new ArrayList<RadioButton>();
		checksIngredients = new ArrayList<CheckBox>();
		checksSauces = new ArrayList<CheckBox>();
		radiosBoissons = new ArrayList<RadioButton>();
		radiosSupplementsBoisson = new ArrayList<RadioButton>();
		radiosDesserts = new ArrayList<RadioButton>();

		boxesPlats = new ArrayList<HBox>();
		boxesIngredients = new ArrayList<HBox>();
		listeIngredients = new VBox();
		boxesSauces = new ArrayList<HBox>();
		boxesBoissons = new ArrayList<HBox>();
		boxesSupplementsBoisson = new ArrayList<HBox>();
		boxesDesserts = new ArrayList<HBox>();

		ingredientsDispo = ingredientsDispo();
		saucesDispo = saucesDispo();

		mnemoniquesUtilisees = new ArrayList<String>();
		mnemoniquesUtilisees.add("J"); //mnémonique pour le bouton aJouter la commande
		mnemoniquesUtilisees.add("C"); //mnémonique pour le menu Confection
		mnemoniquesUtilisees.add("T"); //mnémonique pour le menu sTocks
		mnemoniquesUtilisees.add("M"); //mnémonique pour le menu Menu
		mnemoniquesUtilisees.add("D"); //mnémonique pour le menu aDministration
		mnemoniquesUtilisees.add("G"); //mnémonique pour le menu Graphiques

		StackPane superposition = new StackPane(); //StackPane pour pouvoir avoir le compte de baguettes restantes au dessus du panneau de sélection

		HBox selection = new HBox();

		selection.minWidthProperty().bind(root.widthProperty().subtract(App.TAILLE_PANNEAU_COMMANDES));
		selection.maxWidthProperty().bind(selection.minWidthProperty());

		VBox groupePlats = groupePlats(selection); //on crée les 5 groupes correspondant aux 5 types de contenus commande (les suppléments boisson étant avec les boissons)

		VBox groupeIngredients = groupeIngredients(selection);

		VBox groupeSauces = groupeSauces(selection);

		VBox groupeBoissons = groupeBoissons(selection);

		VBox groupeDesserts = groupeDesserts(selection);

		selection.getChildren().add(groupePlats);
		selection.getChildren().add(groupeIngredients);
		selection.getChildren().add(groupeSauces);
		selection.getChildren().add(groupeBoissons);
		selection.getChildren().add(groupeDesserts);

		selection.setSpacing(ESPACE_GROUPES);
		selection.getStyleClass().add("selection");
		selection.setPadding(new Insets(PADDING_SELECTION));

		AnchorPane baguettes = compteurBaguettesRestantes(root, core);

		superposition.getChildren().addAll(baguettes, selection);

		chargementTermine = true;
		grisageIngredients();
		grisageSauces();

		return(superposition);
	}

	/**
	 * Crée une {@code VBox} correspondant la liste des plats de la base de données adjoints de boutons radio afin de sélectionner le plat de la commande.
	 * 
	 * @param parent le node parent de celui-ci.
	 * 
	 * @return un panneau permettant de sélectionner le plat de la commande.
	 */
	public static final VBox groupePlats(Region parent){

		VBox groupePlats = new VBox();

		ToggleGroup platSelection = new ToggleGroup(); //un ToggleGroup permet de n'autoriser qu'un des Toggles (pour le coup des boutons radio) à être sélectionné en même temps

		VBox listePlats = new VBox();

		int i = 0;
		for(Plat plat : BaseDonnees.getPlats()){
			HBox box = new HBox();

			RadioButton radio = new RadioButton();
			radio.setToggleGroup(platSelection);
			radiosPlats.add(radio);

			Label label = new Label();
			if(mnemoniquesUtilisees.contains(plat.getNom().toUpperCase().substring(0, 1))){ //si la mnémonique n'est pas déjà utilisée, on l'ajoute
				label.setText(plat.getNom());
				label.setMnemonicParsing(false);
			} else {
				label.setText("_" + plat.getNom());
				label.setMnemonicParsing(true);
			}

			//lorsque l'utilisateur clique sur le Label de nom du plat, on sélectionne le radio correspondant
			label.setOnMouseClicked(new EventHandler<Event>() {
				public void handle(Event event) {
					platSelection.selectToggle(radio);
					radio.requestFocus();
				}
			});

			label.setLabelFor(radio);
			box.getChildren().add(radio);
			box.getChildren().add(label);
			boxesPlats.add(box);

			boolean grise = false;
			if(plat.getUtilisePain()){ //on détermine si le plat doit être grisé ou non
				if(Core.getService().getNbBaguettesRestantes() <= 0){
					grise = true;
				}
			}

			if(!plat.getDisponible()){
				grise = true;
			}

			Selection.platDisponible(i, !grise);

			listePlats.getChildren().add(box);

			i++;
		}

		listePlats.getStyleClass().add(LISTE_CONTENU_COMMANDE);
		listePlats.minWidthProperty().bind(parent.widthProperty().subtract(ESPACE_GROUPES*4 + PADDING_SELECTION*2).divide(5));
		listePlats.maxWidthProperty().bind(listePlats.minWidthProperty());
		listePlats.setMaxHeight(Control.USE_PREF_SIZE);

		//lorsque l'élément sélectionné change, on sélectionne le plat de la base de données correspondant et on indique que la commande a changé
		platSelection.selectedToggleProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				for(RadioButton radio : radiosPlats){
					if(newVal.equals(radio)){
						PLAT_SELECTIONNE.set(BaseDonnees.getPlats().get(radiosPlats.indexOf(radio))); //on sélectionne le plat correspondant au bouton,
						COMMANDE_CHANGEE.set(!COMMANDE_CHANGEE.get()); //on indique que le contenu de la commande a changé
						if(chargementTermine){
							grisageIngredients(); //et on grise les ingrédients ou les sauces suivant les spécificités du plat
							grisageSauces();
						}
					}
				}
			}
		});

		platSelection.selectToggle(radiosPlats.get(radiosPlats.size() - 1)); //on sélectionne le dernier élément de la liste (« Rien »)
		PLAT_SELECTIONNE.set(BaseDonnees.getPlats().get(radiosPlats.size() - 1));

		Label titrePlats = new Label("PLAT");
		titrePlats.getStyleClass().add(TITRE_GROUPE);
		titrePlats.prefWidthProperty().bind(groupePlats.widthProperty());

		groupePlats.getChildren().add(titrePlats);
		groupePlats.getChildren().add(listePlats);

		return(groupePlats);
	}

	/**
	 * Crée une {@code VBox} correspondant la liste des ingrédients de la base de données adjoints de checkboxes afin de sélectionner l'ingrédient de la commande.
	 * 
	 * @param parent le node parent de celui-ci.
	 * 
	 * @return un panneau permettant de sélectionner l'ingrédient de la commande.
	 */
	public static final VBox groupeIngredients(Region parent){

		VBox groupeIngredients = new VBox();

		for (Ingredient ingredient : BaseDonnees.getIngredients()){
			HBox box = new HBox();

			CheckBox checkBox = new CheckBox();
			checksIngredients.add(checkBox);

			//lorsque l'état de sélection d'un élément change, on ajoute ou on retire l'ingrédient de la base de données correspondant à la liste des ingrédients sélectionnés, et on indique que le dernier ingrédient sélectionné ainsi que la commande ont changé
			checkBox.selectedProperty().addListener(new ChangeListener() {
				public void changed(ObservableValue o, Object oldVal, Object newVal){
					if(checkBox.isSelected()){
						ingredientsSelectionnes.add(BaseDonnees.getIngredients().get(checksIngredients.indexOf(checkBox)));
						INGREDIENT_CHANGE.set(!INGREDIENT_CHANGE.get());
						COMMANDE_CHANGEE.set(!COMMANDE_CHANGEE.get());
					} else {
						ingredientsSelectionnes.remove(BaseDonnees.getIngredients().get(checksIngredients.indexOf(checkBox)));
						INGREDIENT_CHANGE.set(!INGREDIENT_CHANGE.get());
						COMMANDE_CHANGEE.set(!COMMANDE_CHANGEE.get());
					}
				}
			});

			Label label = new Label(ingredient.getNom());

			//lorsque l'utilisateur clique sur le Label de nom de l'ingrédient, on sélectionne la checkbox correspondante
			label.setOnMouseClicked(new EventHandler<Event>() {
				public void handle(Event event){
					checkBox.setSelected(!checkBox.isSelected());
					checkBox.requestFocus();
				}
			});

			box.getChildren().add(checkBox);
			box.getChildren().add(label);
			boxesIngredients.add(box);

			listeIngredients.getChildren().add(box);
		}

		listeIngredients.getStyleClass().add(LISTE_CONTENU_COMMANDE);
		listeIngredients.minWidthProperty().bind(parent.widthProperty().subtract(ESPACE_GROUPES*4 + PADDING_SELECTION*2).divide(5));
		listeIngredients.maxWidthProperty().bind(listeIngredients.minWidthProperty());
		listeIngredients.setMaxHeight(Control.USE_PREF_SIZE);

		Label titreIngredients = new Label("INGRÉDIENTS");
		titreIngredients.getStyleClass().add(TITRE_GROUPE);
		titreIngredients.prefWidthProperty().bind(groupeIngredients.widthProperty());

		groupeIngredients.getChildren().add(titreIngredients);
		groupeIngredients.getChildren().add(listeIngredients);

		return(groupeIngredients);
	}

	/**
	 * Crée une {@code VBox} correspondant la liste des sauces de la base de données adjointes de checkboxes afin de sélectionner la sauce de la commande.
	 * 
	 * @param parent le node parent de celui-ci.
	 * 
	 * @return un panneau permettant de sélectionner la sauce de la commande.
	 */
	public static final VBox groupeSauces(Region parent){

		VBox groupeSauces = new VBox();

		VBox listeSauces = new VBox();

		for(Sauce sauce : BaseDonnees.getSauces()){
			HBox box = new HBox();

			CheckBox checkBox = new CheckBox();
			checksSauces.add(checkBox);

			//lorsque l'état de sélection d'un élément change, on ajoute ou on retire la sauce de la base de données correspondant à la liste des sauces sélectionnées, et on indique que la dernière sauce sélectionnée ainsi que la commande ont changé
			checkBox.selectedProperty().addListener(new ChangeListener() {
				public void changed(ObservableValue o, Object oldVal, Object newVal){
					if(checkBox.isSelected()){
						saucesSelectionnees.add(BaseDonnees.getSauces().get(checksSauces.indexOf(checkBox)));
						SAUCE_CHANGEE.set(!SAUCE_CHANGEE.get());
						COMMANDE_CHANGEE.set(!COMMANDE_CHANGEE.get());
						grisageSauces();
					} else {
						saucesSelectionnees.remove(BaseDonnees.getSauces().get(checksSauces.indexOf(checkBox)));
						SAUCE_CHANGEE.set(!SAUCE_CHANGEE.get());
						COMMANDE_CHANGEE.set(!COMMANDE_CHANGEE.get());
						grisageSauces();
					}
				}
			});

			Label label = new Label(sauce.getNom());
			label.setOnMouseClicked(new EventHandler<Event>() {
				public void handle(Event event){
					checkBox.setSelected(!checkBox.isSelected());
					checkBox.requestFocus();
				}
			});

			box.getChildren().add(checkBox);
			box.getChildren().add(label);
			boxesSauces.add(box);

			listeSauces.getChildren().add(box);
		}

		listeSauces.getStyleClass().add(LISTE_CONTENU_COMMANDE);
		listeSauces.minWidthProperty().bind(parent.widthProperty().subtract(ESPACE_GROUPES*4 + PADDING_SELECTION*2).divide(5));
		listeSauces.maxWidthProperty().bind(listeSauces.minWidthProperty());
		listeSauces.setMaxHeight(Control.USE_PREF_SIZE);

		Label titreSauces = new Label("SAUCES");
		titreSauces.getStyleClass().add(TITRE_GROUPE);
		titreSauces.prefWidthProperty().bind(groupeSauces.widthProperty());

		groupeSauces.getChildren().add(titreSauces);
		groupeSauces.getChildren().add(listeSauces);

		return(groupeSauces);
	}

	/**
	 * Crée une {@code VBox} correspondant la liste des boissons ainsi qu'à la liste des suppléments boisson de la base de données adjointes de boutons radio afin de sélectionner la boisson et le supplément boisson de la commande.
	 * 
	 * @param parent le node parent de celui-ci.
	 * 
	 * @return un panneau permettant de sélectionner la boisson et le supplément boisson de la commande.
	 */
	public static final VBox groupeBoissons(Region parent){

		VBox groupeBoissons = new VBox();

		ToggleGroup boissonSelection = new ToggleGroup(); //un ToggleGroup permet de n'autoriser qu'un des Toggles (pour le coup des boutons radio) à être sélectionné en même temps

		VBox listeBoissons = new VBox();

		for(Boisson boisson : BaseDonnees.getBoissons()){
			HBox box = new HBox();

			RadioButton radio = new RadioButton();
			radio.setToggleGroup(boissonSelection);
			radiosBoissons.add(radio);
			radio.setSelected(true);

			Label label = new Label(boisson.getNom());

			//lorsque l'utilisateur clique sur le Label de nom de la boisson, on sélectionne le radio correspondant
			label.setOnMouseClicked(new EventHandler<Event>() {
				public void handle(Event event){
					boissonSelection.selectToggle(radio);
					radio.requestFocus();
				}
			});

			box.getChildren().add(radio);
			box.getChildren().add(label);
			boxesBoissons.add(box);

			box.setDisable(!boisson.getDisponible()); //on grise la boisson si jamais elle n'est pas disponible

			listeBoissons.getChildren().add(box);
		}

		Line separation = new Line(); //on ajoute une ligne de séparation entre boissons et suppléments boisson
		separation.setStartX(0);
		separation.setStartY(0);
		separation.endXProperty().bind(groupeBoissons.widthProperty().subtract(10));
		separation.setEndY(0);
		separation.setTranslateX(5);
		listeBoissons.getChildren().add(separation);

		ToggleGroup supplementBoissonSelection = new ToggleGroup(); //un ToggleGroup permet de n'autoriser qu'un des Toggles (pour le coup des boutons radio) à être sélectionné en même temps

		for(SupplementBoisson supplementBoisson : BaseDonnees.getSupplementsBoisson()){
			HBox box = new HBox();

			RadioButton radio = new RadioButton();
			radio.setToggleGroup(supplementBoissonSelection);
			radiosSupplementsBoisson.add(radio);
			radio.setSelected(true);

			//lorsque l'utilisateur clique sur le Label de nom du supplément boisson, on sélectionne le radio correspondant
			Label label = new Label(supplementBoisson.getNom());
			label.setOnMouseClicked(new EventHandler<Event>() {
				public void handle(Event event){
					supplementBoissonSelection.selectToggle(radio);
					radio.requestFocus();
				}
			});

			box.getChildren().add(radio);
			box.getChildren().add(label);
			boxesSupplementsBoisson.add(box);

			box.setDisable(!supplementBoisson.getDisponible()); //on grise le supplément boisson si jamais il n'est pas disponible

			listeBoissons.getChildren().add(box);
		}

		listeBoissons.getStyleClass().add(LISTE_CONTENU_COMMANDE);
		listeBoissons.minWidthProperty().bind(parent.widthProperty().subtract(ESPACE_GROUPES*4 + PADDING_SELECTION*2).divide(5));
		listeBoissons.maxWidthProperty().bind(listeBoissons.minWidthProperty());
		listeBoissons.setMaxHeight(Control.USE_PREF_SIZE);

		//lorsque l'élément sélectionné change, on sélectionne la boisson de la base de données correspondante et on indique que la commande a changé
		boissonSelection.selectedToggleProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				for(RadioButton radio : radiosBoissons){
					if(newVal.equals(radio)){
						BOISSON_SELECTIONNEE.set(BaseDonnees.getBoissons().get(radiosBoissons.indexOf(radio))); //on sélectionne la boisson correspondant au bouton,
						COMMANDE_CHANGEE.set(!COMMANDE_CHANGEE.get()); //et on indique que le contenu de la commande a changé
					}
				}
			}
		});

		//lorsque l'élément sélectionné change, on sélectionne le supplément boisson de la base de données correspondant et on indique que la commande a changé
		supplementBoissonSelection.selectedToggleProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				for(RadioButton radio : radiosSupplementsBoisson){
					if(newVal.equals(radio)){
						SUPPLEMENT_BOISSON_SELECTIONNE.set(BaseDonnees.getSupplementsBoisson().get(radiosSupplementsBoisson.indexOf(radio))); //on sélectionne le supplément boisson correspondant au bouton,
						COMMANDE_CHANGEE.set(!COMMANDE_CHANGEE.get()); //et on indique que le contenu de la commande a changé
					}
				}
			}
		});

		boissonSelection.selectToggle(radiosBoissons.get(radiosBoissons.size() - 1)); //on sélectionne le dernier élément de la liste (« Rien »)
		BOISSON_SELECTIONNEE.set(BaseDonnees.getBoissons().get(radiosBoissons.size() - 1));
		supplementBoissonSelection.selectToggle(radiosSupplementsBoisson.get(radiosSupplementsBoisson.size() - 1)); //on sélectionne le dernier élément de la liste (« Rien »)
		SUPPLEMENT_BOISSON_SELECTIONNE.set(BaseDonnees.getSupplementsBoisson().get(radiosSupplementsBoisson.size() - 1));

		Label titreBoissons = new Label("BOISSON");
		titreBoissons.getStyleClass().add(TITRE_GROUPE);
		titreBoissons.prefWidthProperty().bind(groupeBoissons.widthProperty());

		groupeBoissons.getChildren().add(titreBoissons);
		groupeBoissons.getChildren().add(listeBoissons);

		return(groupeBoissons);
	}

	/**
	 * Crée une {@code VBox} correspondant la liste des desserts de la base de données adjoints de boutons radio afin de sélectionner le dessert de la commande.
	 * 
	 * @param parent le node parent de celui-ci.
	 * 
	 * @return un panneau permettant de sélectionner le dessert de la commande.
	 */
	public static final VBox groupeDesserts(Region parent){

		VBox groupeDesserts = new VBox();

		ToggleGroup dessertSelection = new ToggleGroup(); //un ToggleGroup permet de n'autoriser qu'un des Toggles (pour le coup des boutons radio) à être sélectionné en même temps

		VBox listeDesserts = new VBox();

		for(Dessert dessert : BaseDonnees.getDesserts()){
			HBox box = new HBox();

			RadioButton radio = new RadioButton();
			radio.setToggleGroup(dessertSelection);
			radiosDesserts.add(radio);
			radio.setSelected(true);

			Label label = new Label(dessert.getNom());

			//lorsque l'utilisateur clique sur le Label de nom du plat, on sélectionne le radio correspondant
			label.setOnMouseClicked(new EventHandler<Event>() {
				public void handle(Event event){
					dessertSelection.selectToggle(radio);
					radio.requestFocus();
				}
			});

			box.getChildren().add(radio);
			box.getChildren().add(label);
			boxesDesserts.add(box);

			box.setDisable(!dessert.getDisponible()); //on grise le dessert si jamais il n'est pas disponible

			listeDesserts.getChildren().add(box);
		}

		listeDesserts.getStyleClass().add(LISTE_CONTENU_COMMANDE);
		listeDesserts.minWidthProperty().bind(parent.widthProperty().subtract(ESPACE_GROUPES*4 + PADDING_SELECTION*2).divide(5));
		listeDesserts.maxWidthProperty().bind(listeDesserts.minWidthProperty());
		listeDesserts.setMaxHeight(Control.USE_PREF_SIZE);

		//lorsque l'élément sélectionné change, on sélectionne le dessert de la base de données correspondant et on indique que la commande a changé
		dessertSelection.selectedToggleProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				for(RadioButton radio : radiosDesserts){
					if(newVal.equals(radio)){
						DESSERT_SELECTIONNE.set(BaseDonnees.getDesserts().get(radiosDesserts.indexOf(radio))); //on sélectionne le dessert correspondant au bouton,
						COMMANDE_CHANGEE.set(!COMMANDE_CHANGEE.get()); //et on indique que le contenu de la commande a changé
					}
				}
			}
		});

		dessertSelection.selectToggle(radiosDesserts.get(radiosDesserts.size() - 1)); //on sélectionne le dernier élément de la liste (« Rien »)
		DESSERT_SELECTIONNE.set(BaseDonnees.getDesserts().get(radiosDesserts.size() - 1));

		Label titreDesserts = new Label("DESSERT");
		titreDesserts.getStyleClass().add(TITRE_GROUPE);
		titreDesserts.prefWidthProperty().bind(groupeDesserts.widthProperty());

		groupeDesserts.getChildren().add(titreDesserts);
		groupeDesserts.getChildren().add(listeDesserts);

		return(groupeDesserts);
	}

	/**
	 * Crée un {@code AnchorPane} avec un compteur de baguettes de pain restantes placé en bas à gauche. Ce panneau affiche également des informations utiles au développement si celles-ci sont activées.
	 * 
	 * @param root le node racine de la fenêtre dans lequel ce panneau est placé.
	 * @param core le core du système K'Fet.
	 * 
	 * @return un panneau avec un compteur de baguettes restantes.
	 */
	public static final AnchorPane compteurBaguettesRestantes(Region root, Core core){

		AnchorPane compteurBaguettesPane = new AnchorPane();

		if(DEV){
			Label dev = new Label(); //alors ce qui se passe ici je l'ai trouvé sur internet (https://stackoverflow.com/questions/28287398/what-is-the-preferred-way-of-getting-the-frame-rate-of-a-javafx-application) et je serais bien incapable de l'expliquer
			AnimationTimer frameRateMeter = new AnimationTimer(){ //anyway merci à James_D et tout ce qu'il faut savoir c'est que ça fait un compteur de framerate (auquel j'ai ajouté la taille de la fenêtre)
				
				@Override
				public void handle(long now) {
					long oldFrameTime = FRAME_TIMES[frameTimeIndex] ;
					FRAME_TIMES[frameTimeIndex] = now ;
					frameTimeIndex = (frameTimeIndex + 1) % FRAME_TIMES.length ;
					if (frameTimeIndex == 0) {
						arrayFilled = true ;
					}
					if (arrayFilled) {
						long elapsedNanos = now - oldFrameTime ;
						long elapsedNanosPerFrame = elapsedNanos / FRAME_TIMES.length ;
						double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
						dev.textProperty().bind(root.widthProperty().asString().concat(" x ").concat(root.heightProperty().asString()).concat(String.format(" - %.1f fps", frameRate)));
					}
				}
			};

			frameRateMeter.start();

			AnchorPane.setBottomAnchor(dev, 14.0);
			AnchorPane.setLeftAnchor(dev, 4.0);
			compteurBaguettesPane.getChildren().add(dev);
		}

		refreshCompteurBaguettes();

		AnchorPane.setBottomAnchor(compteurBaguettes, 0.0);
		AnchorPane.setLeftAnchor(compteurBaguettes, 4.0);

		compteurBaguettesPane.getChildren().add(compteurBaguettes);

		return(compteurBaguettesPane);
	}

	/**
	 * Remet la sélection des contenus commande sur rien pour chaque type de contenu commande.
	 */
	public static final void resetSelection(){

		radiosPlats.get(radiosPlats.size() - 1).setSelected(true); //on remet tout à 0,

		ingredientsSelectionnes = new ArrayList<Ingredient>();

		for(CheckBox check : checksIngredients){
			check.setSelected(false);
		}

		INGREDIENT_CHANGE.set(!INGREDIENT_CHANGE.get());

		saucesSelectionnees = new ArrayList<Sauce>();

		for(CheckBox check : checksSauces){
			check.setSelected(false);
		}

		SAUCE_CHANGEE.set(!SAUCE_CHANGEE.get());

		radiosBoissons.get(radiosBoissons.size() - 1).setSelected(true);

		radiosSupplementsBoisson.get(radiosSupplementsBoisson.size() - 1).setSelected(true);

		radiosDesserts.get(radiosDesserts.size() - 1).setSelected(true);

		COMMANDE_CHANGEE.set(!COMMANDE_CHANGEE.get()); //on indique que le contenu de la commande a changé,

		grisageIngredients(); //et on remet à jour le grisage des ingrédients et sauces
		grisageSauces();
	}

	/**
	 * Remet à jour le compteur de baguettes restantes.
	 * 
	 * @param core le core du système K'Fet.
	 */
	public static final void refreshCompteurBaguettes(){

		NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.FRENCH);
		compteurBaguettes.setText("Nombre de baguettes restantes : " + numberFormatter.format(Core.getService().getNbBaguettesRestantes()));
	}

	/**
	 * Grise les ingrédients qui doivent être grisés, et dégrise ceux qui ne doivent pas l'être. Un ingrédient doit être grisé quand il n'est pas disponible et la liste entière des ingrédients doit être grisée si le nombre max d'ingrédients du plat sélectionné est 0.
	 */
	private static final void grisageIngredients(){

		if(PLAT_SELECTIONNE.get().getNbMaxIngredients() == 0){ //si le nombre max d'ingrédients du plat est 0,
			for(CheckBox checkBox : checksIngredients){
				checkBox.setSelected(false);
			}
			listeIngredients.setDisable(true); //on grise toute la liste d'ingrédients
		} else {
			listeIngredients.setDisable(false); //sinon on la dégrise,

			for(HBox box : boxesIngredients){
				box.setDisable(false); //on dégrise tous les ingrédients individuellement,
			}

			int i = 0;
			for(boolean disponible : ingredientsDispo){ //et pour chaque ingrédient, s'il nest pas disponible on le grise
				if(!disponible){
					checksIngredients.get(i).setSelected(false);
					boxesIngredients.get(i).setDisable(true);
				}
				i++;
			}
		}
	}

	/**
	 * Grise les sauces qui doivent l'être, et dégrise celles qui ne doivent pas l'être. Une sauce doit être grisée quand elle n'est pas disponible, quand le nombre max de sauces du plat sélectionné est atteint et qu'elle n'est pas déjà sélectionnée, et la liste entière des sauces doit être grisée si le nombre max de sauces du plat sélectionné est 0.
	 */
	private static final void grisageSauces(){

		if(PLAT_SELECTIONNE.get().getNbMaxSauces() == 0){ //si le nombre max de sauces du plat est 0,
			for(CheckBox checkBox : checksSauces){ //on décoche tous les checkboxes et on grise toutes les sauces
				checkBox.setSelected(false);
			}
			for(HBox box : boxesSauces){
				box.setDisable(true);
			}
		} else {
			if(saucesSelectionnees.size() == PLAT_SELECTIONNE.get().getNbMaxSauces()){ //sinon si le nombre de sauces sélectionnées est égal au max du plat, on grise tous les checkboxes non sélectionnés
				for(CheckBox checkBox : checksSauces){
					if(!checkBox.isSelected()){
						boxesSauces.get(checksSauces.indexOf(checkBox)).setDisable(true);
					}
				}
			} else if(saucesSelectionnees.size() > PLAT_SELECTIONNE.get().getNbMaxSauces()){ //sinon si le nombre de sauces sélectionnées est supérieur au max du plat (ce qui peut arriver lorsqu'on change de plat sélectionné), on décoche progressivement les sauces jusqu'à avoir le bon nombre de sauces cochées
				while(saucesSelectionnees.size() > PLAT_SELECTIONNE.get().getNbMaxSauces()){
					boolean decoche = false;
					for(CheckBox check : checksSauces){
						if(check.isSelected()&&!decoche){
							check.setSelected(false);
							decoche = true;
						}
					}
				}
			} else { //et sinon on les décoche toutes
				for(CheckBox checkBox : checksSauces){
					boxesSauces.get(checksSauces.indexOf(checkBox)).setDisable(false);
				}
			}	
		}

		int i = 0;
		for(boolean disponible : saucesDispo){ //et pour chaque sauce, si elle n'est pas disponible on la grise
			if(!disponible){
				checksSauces.get(i).setSelected(false);
				boxesSauces.get(i).setDisable(true);
			}
			i++;
		}
	}

	/**
	 * Utilisé pour indiquer si un {@code Plat} est disponible, c'est à dire s'il devrait être grisé.
	 * 
	 * @param position la position du plat dans la liste des plats.
	 * @param disponible le fait que le plat soit disponible.
	 */
	public static final void platDisponible(int position, boolean disponible){

		if(!disponible){
			if(radiosPlats.get(position).isSelected()){
				radiosPlats.get(BaseDonnees.getPlats().size() - 1).setSelected(true);
			}
		}

		boxesPlats.get(position).setDisable(!disponible);
	}

	/**
	 * Utilisé pour indiquer si un {@code Ingredient} est disponible, c'est à dire s'il devrait être grisé.
	 * 
	 * @param position la position de l'ingrédient dans la liste des ingrédients.
	 * @param disponible le fait que l'ingrédient soit disponible.
	 */
	public static final void ingredientDisponible(int position, boolean disponible){

		ingredientsDispo.remove(position);
		ingredientsDispo.add(position, disponible);
		grisageIngredients();
	}

	/**
	 * Utilisé pour indiquer si une {@code Sauce} est disponible, c'est à dire si elle devrait être grisée.
	 * 
	 * @param position la position de la sauce dans la liste des sauces.
	 * @param disponible le fait que la sauce soit disponible.
	 */
	public static final void sauceDisponible(int position, boolean disponible){

		saucesDispo.remove(position);
		saucesDispo.add(position, disponible);
		grisageSauces();
	}

	/**
	 * Utilisé pour indiquer si une {@code Boisson} est disponible, c'est à dire si elle devrait être grisée.
	 * 
	 * @param position la position de la boisson dans la liste des boissons.
	 * @param disponible le fait que la boisson soit disponible.
	 */
	public static final void boissonDisponible(int position, boolean disponible){

		if(!disponible){
			if(radiosBoissons.get(position).isSelected()){
				radiosBoissons.get(BaseDonnees.getBoissons().size() - 1).setSelected(true);
			}
		}
		boxesBoissons.get(position).setDisable(!disponible);
	}

	/**
	 * Utilisé pour indiquer si un {@code SupplementBoisson} est disponible, c'est à dire s'il devrait être grisé.
	 * 
	 * @param position la position du supplément boisson dans la liste des suppléments boisson.
	 * @param disponible le fait que le supplément boisson soit disponible.
	 */
	public static final void supplementBoissonDisponible(int position, boolean disponible){

		if(!disponible){
			if(radiosSupplementsBoisson.get(position).isSelected()){
				radiosSupplementsBoisson.get(BaseDonnees.getSupplementsBoisson().size() - 1).setSelected(true);
			}
		}
		boxesSupplementsBoisson.get(position).setDisable(!disponible);
	}

	/**
	 * Utilisé pour indiquer si un {@code Dessert} est disponible, c'est à dire s'il devrait être grisé.
	 * 
	 * @param position la dessert du plat dans la liste des desserts.
	 * @param disponible le fait que le dessert soit disponible.
	 */
	public static final void dessertDisponible(int position, boolean disponible){

		if(!disponible){
			if(radiosDesserts.get(position).isSelected()){
				radiosDesserts.get(BaseDonnees.getDesserts().size() - 1).setSelected(true);
			}
		}
		boxesDesserts.get(position).setDisable(!disponible);
	}

	/**
	 * Initialise la {@code List<Boolean>} décrivant la disponibilité de chaque ingrédient.
	 * 
	 * @return une liste de booléens qui indique pour chaque ingrédient s'il est disponible.
	 */
	private static final List<Boolean> ingredientsDispo(){

		List<Boolean> liste = new ArrayList<Boolean>();
		for(Ingredient ingredient : BaseDonnees.getIngredients()){
			liste.add(ingredient.getDisponible());
		}

		return(liste);
	}

	/**
	 * Initialise la {@code List<Boolean>} décrivant la disponibilité de chaque sauce.
	 * 
	 * @return une liste de booléens qui indique pour chaque sauce si elle est disponible.
	 */
	private static final List<Boolean> saucesDispo(){

		List<Boolean> liste = new ArrayList<Boolean>();
		for(Sauce sauce : BaseDonnees.getSauces()){
			liste.add(sauce.getDisponible());
		}

		return(liste);
	}

	/**
	 * Renvoie le prix de la commande actuellement sélectionnée.
	 *
	 * @return le prix de la commande avec la sélection actuelle.
	 */
	public static final float getPrixCommande(){

		return(Commande.prixCommande(getPlatSelectionne(), getIngredientsSelectionnes(), getBoissonSelectionnee(), getSupplementBoissonSelectionne(), getDessertSelectionne()));
	}

	/**
	 * Renvoie la valeur de la propriété javafx {@code platSelectionne}.
	 * 
	 * @return la valeur de la propriété plat sélectionné.
	 */
	public static final Plat getPlatSelectionne(){

		return(PLAT_SELECTIONNE.get());
	}

	/**
	 * Modifie la valeur de la propriété javafx {@code platSelectionne}.
	 * 
	 * @param plat le {@code Plat} à affecter à la valeur de la propriété.
	 */
	public static final void setPlatSelectionne(Plat plat){

		PLAT_SELECTIONNE.set(plat);
	}

	/**
	 * Renvoie la propriété javafx {@code platSelectionne}.
	 * 
	 * @return la propriété plat sélectionné.
	 */
	public static final ObjectProperty<Plat> platSelectionnePropriete(){

		return(PLAT_SELECTIONNE);
	}

	/**
	 * Renvoie la propriété javafx {@code ingredientChange}.
	 * 
	 * @return la propriété ingredient changé.
	 */
	public static final BooleanProperty ingredientChangePropriete(){

		return(INGREDIENT_CHANGE);
	}

	/**
	 * Renvoie la {@code List<Ingredient>} décrivant les ingrédients actuellement sélectionnés.
	 * 
	 * @return les ingrédients sélectionnés.
	 */
	public static final List<Ingredient> getIngredientsSelectionnes(){

		return(ingredientsSelectionnes);
	}

	/**
	 * Renvoie la propriété javafx {@code sauceChangee}.
	 * 
	 * @return la propriété sauce changée.
	 */
	public static final BooleanProperty sauceChangeePropriete(){

		return(SAUCE_CHANGEE);
	}

	/**
	 * Renvoie la {@code List<Sauce>} décrivant les sauces actuellement sélectionnées.
	 * 
	 * @return les sauces sélectionnées.
	 */
	public static final List<Sauce> getSaucesSelectionnees(){

		return(saucesSelectionnees);
	}

	/**
	 * Renvoie la valeur de la propriété javafx {@code boissonSelectionnee}.
	 * 
	 * @return la valeur de la propriété boisson sélectionnée.
	 */
	public static final Boisson getBoissonSelectionnee(){

		return(BOISSON_SELECTIONNEE.get());
	}

	/**
	 * Modifie la valeur de la propriété javafx {@code boissonSelectionnee}.
	 * 
	 * @param boisson la {@code Boisson} à affecter à la valeur de la propriété.
	 */
	public static final void setBoissonSelectionnee(Boisson boisson){

		BOISSON_SELECTIONNEE.set(boisson);
	}

	/**
	 * Renvoie la propriété javafx {@code boissonSelectionnee}.
	 * 
	 * @return la propriété boisson sélectionnée.
	 */
	public static final ObjectProperty<Boisson> boissonSelectionneePropriete(){

		return(BOISSON_SELECTIONNEE);
	}

	/**
	 * Renvoie la valeur de la propriété javafx {@code supplementBoissonSelectionne}.
	 * 
	 * @return la valeur de la propriété supplément boisson sélectionné.
	 */
	public static final SupplementBoisson getSupplementBoissonSelectionne(){

		return(SUPPLEMENT_BOISSON_SELECTIONNE.get());
	}

	/**
	 * Modifie la valeur de la propriété javafx {@code supplementBoissonSelectionne}.
	 * 
	 * @param supplementBoisson le {@code SupplementBoisson} à affecter à la valeur de la propriété.
	 */
	public static final void setSupplementBoissonSelectionne(SupplementBoisson supplementBoisson){

		SUPPLEMENT_BOISSON_SELECTIONNE.set(supplementBoisson);
	}

	/**
	 * Renvoie la propriété javafx {@code supplementBoissonSelectionne}.
	 * 
	 * @return la propriété supplément boisson sélectionné.
	 */
	public static final ObjectProperty<SupplementBoisson> supplementBoissonSelectionnePropriete(){

		return(SUPPLEMENT_BOISSON_SELECTIONNE);
	}

	/**
	 * Renvoie la valeur de la propriété javafx {@code dessertSelectionne}.
	 * 
	 * @return la valeur de la propriété dessert sélectionné.
	 */
	public static final Dessert getDessertSelectionne(){

		return(DESSERT_SELECTIONNE.get());
	}

	/**
	 * Modifie la valeur de la propriété javafx {@code dessertSelectionne}.
	 * 
	 * @param dessert le {@code Dessert} à affecter à la valeur de la propriété.
	 */
	public static final void setDessertSelectionne(Dessert dessert){

		DESSERT_SELECTIONNE.set(dessert);
	}

	/**
	 * Renvoie la propriété javafx {@code dessertSelectionne}.
	 * 
	 * @return la propriété dessert sélectionné.
	 */
	public static final ObjectProperty<Dessert> dessertSelectionnePropriete(){

		return(DESSERT_SELECTIONNE);
	}

	/**
	 * Renvoie la propriété javafx {@code nouvelleCommande}.
	 * 
	 * @return la propriété nouvelle commande.
	 */
	public static final BooleanProperty commandeChangeePropriete(){

		return(COMMANDE_CHANGEE);
	}
}
