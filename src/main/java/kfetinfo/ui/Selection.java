package kfetinfo.ui;

import java.util.ArrayList;
import java.util.List;

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
import kfetinfo.core.Dessert;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Plat;
import kfetinfo.core.Sauce;
import kfetinfo.core.SupplementBoisson;

public class Selection {
	public static final String LISTE_CONTENU_COMMANDE = "liste-contenu-commande";
	public static final String TITRE_GROUPE = "titre-groupe-contenu-commande";

	public static final Double ESPACE_GROUPES = 4.0;
	public static final Double PADDING_SELECTION = 3.0;

	public static final boolean DEV = true;
	private static final long[] frameTimes = new long[100];
	private static int frameTimeIndex = 0 ;
	private static boolean arrayFilled = false ;

	private static final ObjectProperty<Plat> platSelectionne = new SimpleObjectProperty<Plat>();
	private static List<Ingredient> ingredientsSelectionnes = new ArrayList<Ingredient>();
	private static final BooleanProperty ingredientChange = new SimpleBooleanProperty();
	private static List<Sauce> saucesSelectionnees = new ArrayList<Sauce>();
	private static final BooleanProperty sauceChangee = new SimpleBooleanProperty();
	private static final ObjectProperty<Boisson> boissonSelectionnee = new SimpleObjectProperty<Boisson>();
	private static final ObjectProperty<SupplementBoisson> supplementBoissonSelectionne = new SimpleObjectProperty<SupplementBoisson>();
	private static final ObjectProperty<Dessert> dessertSelectionne = new SimpleObjectProperty<Dessert>();
	private static final BooleanProperty commandeChangee = new SimpleBooleanProperty();

	private static List<RadioButton> radiosPlats = new ArrayList<RadioButton>();
	private static List<CheckBox> checksIngredients = new ArrayList<CheckBox>();
	private static VBox listeIngredients = new VBox();
	private static List<CheckBox> checksSauces = new ArrayList<CheckBox>();
	private static List<HBox> checksAndLabelsSauces = new ArrayList<HBox>();
	private static List<RadioButton> radiosBoissons = new ArrayList<RadioButton>();
	private static List<RadioButton> radiosSupplementsBoisson = new ArrayList<RadioButton>();
	private static List<RadioButton> radiosDesserts = new ArrayList<RadioButton>();

	private static List<HBox> boxesPlats = new ArrayList<HBox>();
	private static List<HBox> boxesIngredients = new ArrayList<HBox>();
	private static List<HBox> boxesSauces = new ArrayList<HBox>();
	private static List<HBox> boxesBoissons = new ArrayList<HBox>();
	private static List<HBox> boxesSupplementsBoisson = new ArrayList<HBox>();
	private static List<HBox> boxesDesserts = new ArrayList<HBox>();

	private static List<Boolean> ingredientsDispo = ingredientsDispo();
	private static List<Boolean> saucesDispo = saucesDispo();

	public static Region selection(Region root){
		StackPane superposition = new StackPane();

		HBox selection = new HBox();

		selection.minWidthProperty().bind(root.widthProperty().subtract(App.TAILLE_PANNEAU_COMMANDES));
		selection.maxWidthProperty().bind(selection.minWidthProperty());

		VBox groupePlats = groupePlats(selection);

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

		AnchorPane utilDev = utilDev(root);

		superposition.getChildren().addAll(utilDev, selection);

		if(DEV){
			return(superposition);
		} else {
			return(selection);
		}
	}

	public static VBox groupePlats(Region parent){
		VBox groupePlats = new VBox();

		ToggleGroup platSelection = new ToggleGroup();

		VBox listePlats = new VBox();

		for(Plat plat : BaseDonnees.getPlats()){
			HBox box = new HBox();

			RadioButton radio = new RadioButton();
			radio.setToggleGroup(platSelection);
			radiosPlats.add(radio);

			Label label = new Label(plat.getNom());
			label.setOnMouseClicked(new EventHandler<Event>() {
				public void handle(Event event) {
					platSelection.selectToggle(radio);
					radio.requestFocus();
				}
			});

			box.getChildren().add(radio);
			box.getChildren().add(label);
			boxesPlats.add(box);
			
			box.setDisable(!plat.getDisponible());

			listePlats.getChildren().add(box);
		}

		listePlats.getStyleClass().add(LISTE_CONTENU_COMMANDE);
		listePlats.minWidthProperty().bind(parent.widthProperty().subtract(ESPACE_GROUPES*4 + PADDING_SELECTION*2).divide(5));
		listePlats.maxWidthProperty().bind(listePlats.minWidthProperty());
		listePlats.setMaxHeight(Control.USE_PREF_SIZE);

		platSelection.selectedToggleProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				for(RadioButton radio : radiosPlats){
					if(newVal.equals(radio)){
						platSelectionne.set(BaseDonnees.getPlats().get(radiosPlats.indexOf(radio)));
						commandeChangee.set(!commandeChangee.get());
						grisageIngredients();
						grisageSauces();
					}
				}
			}
		});

		platSelection.selectToggle(radiosPlats.get(radiosPlats.size() - 1));
		platSelectionne.set(BaseDonnees.getPlats().get(radiosPlats.size() - 1));

		Label titrePlats = new Label("PLAT");
		titrePlats.getStyleClass().add(TITRE_GROUPE);
		titrePlats.prefWidthProperty().bind(groupePlats.widthProperty());

		groupePlats.getChildren().add(titrePlats);
		groupePlats.getChildren().add(listePlats);

		return(groupePlats);
	}

	public static VBox groupeIngredients(Region parent){
		VBox groupeIngredients = new VBox();

		for (Ingredient ingredient : BaseDonnees.getIngredients()){
			HBox box = new HBox();

			CheckBox checkBox = new CheckBox();
			checksIngredients.add(checkBox);

			checkBox.selectedProperty().addListener(new ChangeListener() {
				public void changed(ObservableValue o, Object oldVal, Object newVal){
					if(checkBox.isSelected()){
						ingredientsSelectionnes.add(BaseDonnees.getIngredients().get(checksIngredients.indexOf(checkBox)));
						ingredientChange.set(!ingredientChange.get());
						commandeChangee.set(!commandeChangee.get());
					} else {
						ingredientsSelectionnes.remove(BaseDonnees.getIngredients().get(checksIngredients.indexOf(checkBox)));
						ingredientChange.set(!ingredientChange.get());
						commandeChangee.set(!commandeChangee.get());
					}
				}
			});

			Label label = new Label(ingredient.getNom());
			label.setOnMouseClicked(new EventHandler<Event>() {
				public void handle(Event event){
					checkBox.setSelected(!checkBox.isSelected());
					checkBox.requestFocus();
				}
			});

			box.getChildren().add(checkBox);
			box.getChildren().add(label);
			boxesIngredients.add(box);

			grisageIngredients();
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

	public static VBox groupeSauces(Region parent){
		VBox groupeSauces = new VBox();

		VBox listeSauces = new VBox();

		for(Sauce sauce : BaseDonnees.getSauces()){
			HBox box = new HBox();

			CheckBox checkBox = new CheckBox();
			checksSauces.add(checkBox);

			checkBox.selectedProperty().addListener(new ChangeListener() {
				public void changed(ObservableValue o, Object oldVal, Object newVal){
					if(checkBox.isSelected()){
						saucesSelectionnees.add(BaseDonnees.getSauces().get(checksSauces.indexOf(checkBox)));
						sauceChangee.set(!sauceChangee.get());
						commandeChangee.set(!commandeChangee.get());
						grisageSauces();
					} else {
						saucesSelectionnees.remove(BaseDonnees.getSauces().get(checksSauces.indexOf(checkBox)));
						sauceChangee.set(!sauceChangee.get());
						commandeChangee.set(!commandeChangee.get());
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
			checksAndLabelsSauces.add(box);
			boxesSauces.add(box);

			grisageSauces();
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

	public static VBox groupeBoissons(Region parent){
		VBox groupeBoissons = new VBox();

		ToggleGroup boissonSelection = new ToggleGroup();

		VBox listeBoissons = new VBox();

		for(Boisson boisson : BaseDonnees.getBoissons()){
			HBox box = new HBox();

			RadioButton radio = new RadioButton();
			radio.setToggleGroup(boissonSelection);
			radiosBoissons.add(radio);
			radio.setSelected(true);

			Label label = new Label(boisson.getNom());
			label.setOnMouseClicked(new EventHandler<Event>() {
				public void handle(Event event){
					boissonSelection.selectToggle(radio);
					radio.requestFocus();
				}
			});

			box.getChildren().add(radio);
			box.getChildren().add(label);
			boxesBoissons.add(box);

			box.setDisable(!boisson.getDisponible());

			listeBoissons.getChildren().add(box);
		}

		Line separation = new Line();
		separation.setStartX(0);
		separation.setStartY(0);
		separation.endXProperty().bind(groupeBoissons.widthProperty().subtract(10));
		separation.setEndY(0);
		separation.setTranslateX(5);
		listeBoissons.getChildren().add(separation);

		ToggleGroup supplementBoissonSelection = new ToggleGroup();

		for(SupplementBoisson supplementBoisson : BaseDonnees.getSupplementsBoisson()){
			HBox box = new HBox();

			RadioButton radio = new RadioButton();
			radio.setToggleGroup(supplementBoissonSelection);
			radiosSupplementsBoisson.add(radio);
			radio.setSelected(true);

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

			box.setDisable(!supplementBoisson.getDisponible());

			listeBoissons.getChildren().add(box);
		}

		listeBoissons.getStyleClass().add(LISTE_CONTENU_COMMANDE);
		listeBoissons.minWidthProperty().bind(parent.widthProperty().subtract(ESPACE_GROUPES*4 + PADDING_SELECTION*2).divide(5));
		listeBoissons.maxWidthProperty().bind(listeBoissons.minWidthProperty());
		listeBoissons.setMaxHeight(Control.USE_PREF_SIZE);

		boissonSelection.selectedToggleProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				for(RadioButton radio : radiosBoissons){
					if(newVal.equals(radio)){
						boissonSelectionnee.set(BaseDonnees.getBoissons().get(radiosBoissons.indexOf(radio)));
						commandeChangee.set(!commandeChangee.get());
					}
				}
			}
		});

		supplementBoissonSelection.selectedToggleProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				for(RadioButton radio : radiosSupplementsBoisson){
					if(newVal.equals(radio)){
						supplementBoissonSelectionne.set(BaseDonnees.getSupplementsBoisson().get(radiosSupplementsBoisson.indexOf(radio)));
						commandeChangee.set(!commandeChangee.get());
					}
				}
			}
		});

		boissonSelection.selectToggle(radiosBoissons.get(radiosBoissons.size() - 1));
		boissonSelectionnee.set(BaseDonnees.getBoissons().get(radiosBoissons.size() - 1));
		supplementBoissonSelection.selectToggle(radiosSupplementsBoisson.get(radiosSupplementsBoisson.size() - 1));
		supplementBoissonSelectionne.set(BaseDonnees.getSupplementsBoisson().get(radiosSupplementsBoisson.size() - 1));

		Label titreBoissons = new Label("BOISSON");
		titreBoissons.getStyleClass().add(TITRE_GROUPE);
		titreBoissons.prefWidthProperty().bind(groupeBoissons.widthProperty());

		groupeBoissons.getChildren().add(titreBoissons);
		groupeBoissons.getChildren().add(listeBoissons);

		return(groupeBoissons);
	}

	public static VBox groupeDesserts(Region parent){
		VBox groupeDesserts = new VBox();

		ToggleGroup dessertSelection = new ToggleGroup();

		VBox listeDesserts = new VBox();

		for(Dessert dessert : BaseDonnees.getDesserts()){
			HBox box = new HBox();

			RadioButton radio = new RadioButton();
			radio.setToggleGroup(dessertSelection);
			radiosDesserts.add(radio);
			radio.setSelected(true);

			Label label = new Label(dessert.getNom());
			label.setOnMouseClicked(new EventHandler<Event>() {
				public void handle(Event event){
					dessertSelection.selectToggle(radio);
					radio.requestFocus();
				}
			});

			box.getChildren().add(radio);
			box.getChildren().add(label);
			boxesDesserts.add(box);

			box.setDisable(!dessert.getDisponible());

			listeDesserts.getChildren().add(box);
		}

		listeDesserts.getStyleClass().add(LISTE_CONTENU_COMMANDE);
		listeDesserts.minWidthProperty().bind(parent.widthProperty().subtract(ESPACE_GROUPES*4 + PADDING_SELECTION*2).divide(5));
		listeDesserts.maxWidthProperty().bind(listeDesserts.minWidthProperty());
		listeDesserts.setMaxHeight(Control.USE_PREF_SIZE);

		dessertSelection.selectedToggleProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue o, Object oldVal, Object newVal){
				for(RadioButton radio : radiosDesserts){
					if(newVal.equals(radio)){
						dessertSelectionne.set(BaseDonnees.getDesserts().get(radiosDesserts.indexOf(radio)));
						commandeChangee.set(!commandeChangee.get());
					}
				}
			}
		});

		dessertSelection.selectToggle(radiosDesserts.get(radiosDesserts.size() - 1));
		dessertSelectionne.set(BaseDonnees.getDesserts().get(radiosDesserts.size() - 1));

		Label titreDesserts = new Label("DESSERT");
		titreDesserts.getStyleClass().add(TITRE_GROUPE);
		titreDesserts.prefWidthProperty().bind(groupeDesserts.widthProperty());

		groupeDesserts.getChildren().add(titreDesserts);
		groupeDesserts.getChildren().add(listeDesserts);

		return(groupeDesserts);
	}

	public static AnchorPane utilDev(Region root){
		AnchorPane devPane = new AnchorPane();

		Label label = new Label();
		AnimationTimer frameRateMeter = new AnimationTimer() {
			
            @Override
            public void handle(long now) {
                long oldFrameTime = frameTimes[frameTimeIndex] ;
                frameTimes[frameTimeIndex] = now ;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
                if (frameTimeIndex == 0) {
                    arrayFilled = true ;
                }
                if (arrayFilled) {
                    long elapsedNanos = now - oldFrameTime ;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
                    label.textProperty().bind(root.widthProperty().asString().concat(" x ").concat(root.heightProperty().asString()).concat(String.format(" - %.1f fps", frameRate)));
                }
            }
        };

        frameRateMeter.start();

        AnchorPane.setBottomAnchor(label, 2.0);
        AnchorPane.setLeftAnchor(label, 2.0);

        devPane.getChildren().add(label);
		
		return(devPane);
	}

	public static void reset(){
		radiosPlats.get(radiosPlats.size() - 1).setSelected(true);
		ingredientsSelectionnes = new ArrayList<Ingredient>();
		for(CheckBox check : checksIngredients){
			check.setSelected(false);
		}
		ingredientChange.set(!ingredientChange.get());
		saucesSelectionnees = new ArrayList<Sauce>();
		for(CheckBox check : checksSauces){
			check.setSelected(false);
		}
		sauceChangee.set(!sauceChangee.get());
		radiosBoissons.get(radiosBoissons.size() - 1).setSelected(true);
		radiosDesserts.get(radiosDesserts.size() - 1).setSelected(true);
		commandeChangee.set(!commandeChangee.get());
		grisageIngredients();
		grisageSauces();
	}

	private static void grisageIngredients(){
		if(platSelectionne.get().getNbMaxIngredients() == 0){
			for(CheckBox checkBox : checksIngredients){
				checkBox.setSelected(false);
			}
			listeIngredients.setDisable(true);
		} else {
			listeIngredients.setDisable(false);

			for(HBox box : boxesIngredients){
				box.setDisable(false);
			}

			int i = 0;
			for(boolean disponible : ingredientsDispo){
				if(!disponible){
					checksIngredients.get(i).setSelected(false);
					boxesIngredients.get(i).setDisable(true);
				}
				i++;
			}
		}
	}

	private static void grisageSauces(){
		if(platSelectionne.get().getNbMaxSauces() == 0){
			for(CheckBox checkBox : checksSauces){
				checkBox.setSelected(false);
			}
			for(HBox checksAndLabels : checksAndLabelsSauces){
				checksAndLabels.setDisable(true);
			}
		} else {
			if(saucesSelectionnees.size() >= platSelectionne.get().getNbMaxSauces()){
				for(CheckBox checkBox : checksSauces){
					if(!checkBox.isSelected()){
						checksAndLabelsSauces.get(checksSauces.indexOf(checkBox)).setDisable(true);
					}
				}
			} else {
				for(CheckBox checkBox : checksSauces){
					checksAndLabelsSauces.get(checksSauces.indexOf(checkBox)).setDisable(false);
				}
			}	
		}

		int i = 0;
		for(boolean disponible : saucesDispo){
			if(!disponible){
				checksSauces.get(i).setSelected(false);
				boxesSauces.get(i).setDisable(true);
			}
			i++;
		}
	}

	public static void platDisponible(int position, boolean disponible){
		if(!disponible){
			if(radiosPlats.get(position).isSelected()){
				radiosPlats.get(BaseDonnees.getPlats().size() - 1).setSelected(true);
			}
		}
		boxesPlats.get(position).setDisable(!disponible);
	}

	public static void ingredientDisponible(int position, boolean disponible){
		ingredientsDispo.remove(position);
		ingredientsDispo.add(position, disponible);
		grisageIngredients();
	}

	public static void sauceDisponible(int position, boolean disponible){
		saucesDispo.remove(position);
		saucesDispo.add(position, disponible);
		grisageSauces();
	}

	public static void boissonDisponible(int position, boolean disponible){
		if(!disponible){
			if(radiosBoissons.get(position).isSelected()){
				radiosBoissons.get(BaseDonnees.getBoissons().size() - 1).setSelected(true);
			}
		}
		boxesBoissons.get(position).setDisable(!disponible);
	}

	public static void supplementBoissonDisponible(int position, boolean disponible){
		if(!disponible){
			if(radiosSupplementsBoisson.get(position).isSelected()){
				radiosSupplementsBoisson.get(BaseDonnees.getSupplementsBoisson().size() - 1).setSelected(true);
			}
		}
		boxesSupplementsBoisson.get(position).setDisable(!disponible);
	}

	public static void dessertDisponible(int position, boolean disponible){
		if(!disponible){
			if(radiosDesserts.get(position).isSelected()){
				radiosDesserts.get(BaseDonnees.getDesserts().size() - 1).setSelected(true);
			}
		}
		boxesDesserts.get(position).setDisable(!disponible);
	}

	public static List<Boolean> ingredientsDispo(){
		List<Boolean> liste = new ArrayList<Boolean>();
		for(Ingredient ingredient : BaseDonnees.getIngredients()){
			liste.add(ingredient.getDisponible());
		}

		return(liste);
	}

	public static List<Boolean> saucesDispo(){
		List<Boolean> liste = new ArrayList<Boolean>();
		for(Sauce sauce : BaseDonnees.getSauces()){
			liste.add(sauce.getDisponible());
		}

		return(liste);
	}

	public static Plat getPlatSelectionne(){
		return(platSelectionne.get());
	}

	public static void setPlatSelectionne(Plat plat){
		platSelectionne.set(plat);
	}

	public static ObjectProperty<Plat> platSelectionnePropriete(){
		return(platSelectionne);
	}

	public static BooleanProperty ingredientChangePropriete(){
		return(ingredientChange);
	}

	public static List<Ingredient> getIngredientsSelectionnes(){
		return(ingredientsSelectionnes);
	}

	public static BooleanProperty sauceChangeePropriete(){
		return(sauceChangee);
	}

	public static List<Sauce> getSaucesSelectionnees(){
		return(saucesSelectionnees);
	}

	public static Boisson getBoissonSelectionnee(){
		return(boissonSelectionnee.get());
	}

	public static void setBoissonSelectionnee(Boisson boisson){
		boissonSelectionnee.set(boisson);
	}

	public static ObjectProperty<Boisson> boissonSelectionneePropriete(){
		return(boissonSelectionnee);
	}

	public static SupplementBoisson getSupplementBoissonSelectionne(){
		return(supplementBoissonSelectionne.get());
	}

	public static void setSupplementBoissonSelectionne(SupplementBoisson supplementBoisson){
		supplementBoissonSelectionne.set(supplementBoisson);
	}

	public static ObjectProperty<SupplementBoisson> supplementBoissonSelectionnePropriete(){
		return(supplementBoissonSelectionne);
	}

	public static Dessert getDessertSelectionne(){
		return(dessertSelectionne.get());
	}

	public static void setDessertSelectionne(Dessert dessert){
		dessertSelectionne.set(dessert);
	}

	public static ObjectProperty<Dessert> dessertSelectionnePropriete(){
		return(dessertSelectionne);
	}

	public static BooleanProperty commandeChangeePropriete(){
		return(commandeChangee);
	}

	public static float getPrixCommande(){
		return(Commande.prixCommande(getPlatSelectionne(), getIngredientsSelectionnes(), getSaucesSelectionnees(), getBoissonSelectionnee(), getSupplementBoissonSelectionne(), getDessertSelectionne()));
	}
}
