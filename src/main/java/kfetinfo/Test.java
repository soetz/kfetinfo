/*
 * kfetinfo - Logiciel pour la K'Fet du BDE Info de l'IUT Lyon 1
 *  Copyright (C) 2017 Simon Lecutiez

 *  This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package kfetinfo;

import java.io.File;

import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

public class Test /*extends Application*/ {
	static Service service = new Service(new Date());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init();
		
//		service.setOrdi(BaseDonnees.getMembreNomPrenom("lecutiez", "simon"));
//		service.addCommis(BaseDonnees.getMembreNomPrenom("cohen", "justine"));
//		service.addCommis(BaseDonnees.getMembreNomPrenom("glasson", "emma"));
//		service.addConfection(BaseDonnees.getMembreNomPrenom("decaderincourt", "kilian"));
//		service.addConfection(BaseDonnees.getMembreNomPrenom("henrionnet", "antoine"));
//		service.addConfection(BaseDonnees.getMembreNomPrenom("blettery", "damien"));
//
//		service.ecrireFichier();
		
		service.affMembres();

		/*launch(args);*/
	}

	public static void init(){
		CreateurBase.initialiserBase();
		BaseDonnees.chargerMenu();
		BaseDonnees.chargerMembres();
		service.recharger();
	}

	public static Service getService(){
		return(service);
	}

	/*public void start(Stage args) throws Exception {
		
	}*/

}
