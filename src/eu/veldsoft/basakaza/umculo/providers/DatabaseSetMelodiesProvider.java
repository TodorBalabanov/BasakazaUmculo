/*******************************************************************************
 * Basakaza Umculo, Version 1.0                                                *
 * Faculty of Mathematics and Informatics, Sofia University                    *
 *                                                                             *
 * Copyright (c) 2014-2015 Todor Balabanov                                     *
 *                                                                             *
 * todor.balabanov@gmail.com                                                   *
 *                                                                             *
 * This program is free software; you can redistribute it and/or modify        *
 * it under the terms of the GNU General Public License as published by        *
 * the Free Software Foundation; either version 3 of the License, or           *
 * (at your option) any later version.                                         *
 *                                                                             *
 * This program is distributed in the hope that it will be useful,             *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of              *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the               *
 * GNU General Public License for more details.                                *
 *                                                                             *
 * You should have received a copy of the GNU General Public License along     *
 * with this program; if not, write to the Free Software Foundation, Inc.,     *
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.                 *
 ******************************************************************************/

package eu.veldsoft.basakaza.umculo.providers;

import java.util.Vector;

import eu.veldsoft.basakaza.umculo.base.Melody;
import eu.veldsoft.basakaza.umculo.database.DatabaseMediator;

/**
 * Provide set of melodies from a given melody list.
 * 
 * @author Todor Balabanov
 * 
 * @email todor.balabanov@gmail.com
 * 
 * @date 30 May 2014
 */
public class DatabaseSetMelodiesProvider implements MelodySetProvider {
	/**
	 * Provide set of melodies from database.
	 * 
	 * @return Set of melodies from database.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 24 Jun 2014
	 */
	@Override
	public Vector<Melody> provide() {
		return ((new DatabaseMediator()).loadMelodies());
	}

	@Override
	public Vector<Melody> provide(int size) {
		return provide();
	}
}
