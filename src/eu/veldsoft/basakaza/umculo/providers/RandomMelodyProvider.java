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

import eu.veldsoft.basakaza.umculo.base.Melody;
import eu.veldsoft.basakaza.umculo.base.Note;

/**
 * Random melody provider class is responsible to generate melody with random
 * length, random timber and random notes.
 * 
 * @author Todor Balabanov
 * 
 * @email todor.balabanov@gmail.com
 * 
 * @date 29 May 2014
 */
class RandomMelodyProvider implements MelodyProvider {
	/**
	 * Min melody sequence size for random generation.
	 */
	private static final int MIN_RANDOM_SEQUENCE = 3;

	/**
	 * Max melody sequence size for random generation.
	 */
	private static final int MAX_RANDOM_SEQUENCE = 15;

	/**
	 * Create random melody as sequence of random music notes. By this way
	 * system can be easy populated with a lot of melodies, but melodies with no
	 * sense.
	 * 
	 * @return Melody constructed.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 29 May 2014
	 */
	@Override
	public Melody provide() {
		return (provide(MIN_RANDOM_SEQUENCE + (int) (Math.random() * (MAX_RANDOM_SEQUENCE - MIN_RANDOM_SEQUENCE + 1))));
	}

	/**
	 * Create random melody as sequence of random music notes. By this way
	 * system can be easy populated with a lot of melodies, but melodies with no
	 * sense. The difference with the previous method is only that length is
	 * specified by input parameter.
	 * 
	 * @param length
	 *            The length of the melody.
	 * 
	 * @return Melody constructed.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 29 May 2014
	 */
	@Override
	public Melody provide(int length) {
		Melody melody = new Melody();

		for (int i = 0; i < length; i++)
			melody.addNote(Note.provideRandom());

		melody.setTimber(1 + (int) (Math.random() * 128));
		melody.setId(Melody.getUniqueId());
		melody.setScore(0);

		melody.sort();

		return (melody);
	}
}
