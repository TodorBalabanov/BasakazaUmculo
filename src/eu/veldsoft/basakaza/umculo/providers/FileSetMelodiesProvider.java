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

import java.io.File;
import java.util.Vector;

import eu.veldsoft.basakaza.umculo.base.Melody;

/**
 * Provide set of melodies from a given melody list.
 * 
 * @author Todor Balabanov
 * 
 * @email todor.balabanov@gmail.com
 * 
 * @date 30 May 2014
 */
public class FileSetMelodiesProvider implements MelodySetProvider {
	/**
	 * Project sub folder for melody files.
	 */
	private String textMelodiesFolder = "";

	public FileSetMelodiesProvider(String textMelodiesFolder) {
		super();
		this.textMelodiesFolder = textMelodiesFolder;
	}

	/**
	 * Provide set of melodies from list of files.
	 * 
	 * @return Set of melodies from list of files.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 04 Jun 2014
	 */
	@Override
	public Vector<Melody> provide() {
		Vector<Melody> melodies = new Vector<Melody>();

		String filesList[] = (new File(textMelodiesFolder)).list();

		Melody melody = null;
		for (int i = 0; filesList != null && i < filesList.length; i++) {
			if ((new File(textMelodiesFolder + filesList[i])).isFile() == true) {
				try {
					melody = (new FileMelodyProvider(textMelodiesFolder + filesList[i])).provide();
					melody.setId(Melody.getUniqueId());
					melodies.add(melody);
				} catch (NotValidDescriptorFileException ex) {
					ex.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		return (melodies);
	}

	@Override
	public Vector<Melody> provide(int size) {
		return provide();
	}
}
