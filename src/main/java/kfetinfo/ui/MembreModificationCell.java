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

package kfetinfo.ui;

import kfetinfo.core.Membre;

import javafx.scene.control.ListCell;

/**
 * <p>MembreMomedificationCell est une classe utilisée pour afficher des objets {@code Membre} dans des {@code ListView}. Elle hérite de {@code ListCell<Membre>}, du package {@code javafx.scene.control}.</p>
 * 
 * @author Simon Lecutiez - Sœtz
 * @version 1.0
 */
public final class MembreModificationCell extends ListCell<Membre> {

	@Override
	protected void updateItem(Membre item, boolean empty){

		super.updateItem(item, empty);
		setText(null);
		if(!empty && item != null){
			if(!item.getId().equals(EditionMembres.ID_NOUVEAU)){
				final String text = item.getBlaze();
				setText(text);
			} else {
				setText("Nouveau membre");
			}
		}
	}
}