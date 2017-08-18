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

package kfetinfo.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Core {
	static Service service;

	public Core(){
		init();

		Parametres.initialiser();
		service = new Service(new Date());
	}

	private void init(){
		CreateurBase.initialiserBase();
		BaseDonnees.chargerMenu();
		BaseDonnees.chargerMembres();
	}

	public static Service getService(){
		return(service);
	}
}
