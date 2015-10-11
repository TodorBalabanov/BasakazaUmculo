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

package eu.veldsoft.basakaza.umculo.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Vector;

import eu.veldsoft.basakaza.umculo.base.Melody;

/**
 * Produce MIDI files in specified folder.
 * 
 * @author Todor Balabanov
 * 
 * @email todor.balabanov@gmail.com
 * 
 * @date 04 Jun 2014
 */
public class MidiFileProducer {
	/**
	 * Project subfolder for saving files.
	 */
	private static String MIDI_SAVE_FOLDER = "../midis/";

	/**
	 * Produce MIDI files in the specified internal application folder.
	 * 
	 * @param melodies
	 *            Vector of melodies.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 04 Jun 2014
	 */
	public static void produce(Vector<Melody> melodies) {
		for (int i = 0; i < melodies.size(); i++) {
			String fileName = MIDI_SAVE_FOLDER;
			Melody melody = melodies.elementAt(i);

			fileName += melody.getId();
			fileName += "_";
			fileName += melody.getGenre();
			fileName += "_";
			fileName += melody.getTimber();
			fileName += "_";
			fileName += System.currentTimeMillis();
			fileName += ".mid";

			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
				// TODO Something is wrong with saving binary information.
				out.write(melody.toMidiBytes());
				out.close();
			} catch (Exception ex) {
				System.err.println(ex);
			}
		}
	}
}
