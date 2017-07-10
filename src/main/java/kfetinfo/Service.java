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

import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Service {
	Date date;
	List<Commande> commandes;
	float nbBaguettesBase;
	float nbBaguettesUtilisees;
	Membre ordi;
	List<Membre> commis;
	List<Membre> confection;

	static String path = new File("").getAbsolutePath();

	public Service(Date date){
		SimpleDateFormat annee = new SimpleDateFormat("yyyy");
		SimpleDateFormat mois = new SimpleDateFormat("MM");
		SimpleDateFormat jour = new SimpleDateFormat("dd");

		try {
			Files.createDirectories(Paths.get(path + "\\src\\main\\resources\\Base de Données\\Services\\" + annee.format(date) + "\\" + mois.format(date) + "\\" + jour.format(date) + "\\"));
		} catch (Exception e){
			e.printStackTrace();
		}

		this.date = date;
		commandes = new ArrayList<Commande>();
		nbBaguettesBase = 0;
		nbBaguettesUtilisees = 0;
//		ordi = getOrdiDernierService();
//		commis = getCommisDernierService();
//		confection = getConfectionDernierService();
	}

	
}
