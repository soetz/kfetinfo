package kfetinfo.ui;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import kfetinfo.core.BaseDonnees;
import kfetinfo.core.Boisson;
import kfetinfo.core.Dessert;
import kfetinfo.core.Ingredient;
import kfetinfo.core.Plat;
import kfetinfo.core.Sauce;

public class Selection {
	public static final String LISTE_CONTENU_COMMANDE = "liste-contenu-commande";
	public static final String TITRE_GROUPE = "titre-groupe-contenu-commande";

	public static final Double ESPACE_GROUPES = 4.0;
	public static final Double PADDING_SELECTION = 3.0;

	public static final boolean DEV = true;
    private static final long[] frameTimes = new long[100];
    private static int frameTimeIndex = 0 ;
    private static boolean arrayFilled = false ;

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

		VBox listePlats = new VBox();

		for(Plat plat : BaseDonnees.getPlats()){
			HBox box = new HBox();

			RadioButton radio = new RadioButton();

			Label label = new Label(plat.getNom());

			box.getChildren().add(radio);
			box.getChildren().add(label);

			listePlats.getChildren().add(box);
		}

		listePlats.getStyleClass().add(LISTE_CONTENU_COMMANDE);
		listePlats.minWidthProperty().bind(parent.widthProperty().subtract(ESPACE_GROUPES*4 + PADDING_SELECTION*2).divide(5));
		listePlats.maxWidthProperty().bind(listePlats.minWidthProperty());
		listePlats.setMaxHeight(Control.USE_PREF_SIZE);

		Label titrePlats = new Label("PLAT");
		titrePlats.getStyleClass().add(TITRE_GROUPE);
		titrePlats.prefWidthProperty().bind(groupePlats.widthProperty());

		groupePlats.getChildren().add(titrePlats);
		groupePlats.getChildren().add(listePlats);

		return(groupePlats);
	}

	public static VBox groupeIngredients(Region parent){
		VBox groupeIngredients = new VBox();

		VBox listeIngredients = new VBox();

		for(Ingredient ingredient : BaseDonnees.getIngredients()){
			HBox box = new HBox();

			CheckBox checkBox = new CheckBox();

			Label label = new Label(ingredient.getNom());

			box.getChildren().add(checkBox);
			box.getChildren().add(label);

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

			Label label = new Label(sauce.getNom());

			box.getChildren().add(checkBox);
			box.getChildren().add(label);

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

		VBox listeBoissons = new VBox();

		for(Boisson boisson : BaseDonnees.getBoissons()){
			HBox box = new HBox();

			RadioButton radio = new RadioButton();

			Label label = new Label(boisson.getNom());

			box.getChildren().add(radio);
			box.getChildren().add(label);

			listeBoissons.getChildren().add(box);
		}

		listeBoissons.getStyleClass().add(LISTE_CONTENU_COMMANDE);
		listeBoissons.minWidthProperty().bind(parent.widthProperty().subtract(ESPACE_GROUPES*4 + PADDING_SELECTION*2).divide(5));
		listeBoissons.maxWidthProperty().bind(listeBoissons.minWidthProperty());
		listeBoissons.setMaxHeight(Control.USE_PREF_SIZE);

		Label titreBoissons = new Label("BOISSON");
		titreBoissons.getStyleClass().add(TITRE_GROUPE);
		titreBoissons.prefWidthProperty().bind(groupeBoissons.widthProperty());

		groupeBoissons.getChildren().add(titreBoissons);
		groupeBoissons.getChildren().add(listeBoissons);

		return(groupeBoissons);
	}

	public static VBox groupeDesserts(Region parent){
		VBox groupeDesserts = new VBox();

		VBox listeDesserts = new VBox();

		for(Dessert dessert : BaseDonnees.getDesserts()){
			HBox box = new HBox();

			RadioButton radio = new RadioButton();

			Label label = new Label(dessert.getNom());

			box.getChildren().add(radio);
			box.getChildren().add(label);

			listeDesserts.getChildren().add(box);
		}

		listeDesserts.getStyleClass().add(LISTE_CONTENU_COMMANDE);
		listeDesserts.minWidthProperty().bind(parent.widthProperty().subtract(ESPACE_GROUPES*4 + PADDING_SELECTION*2).divide(5));
		listeDesserts.maxWidthProperty().bind(listeDesserts.minWidthProperty());
		listeDesserts.setMaxHeight(Control.USE_PREF_SIZE);

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
}
