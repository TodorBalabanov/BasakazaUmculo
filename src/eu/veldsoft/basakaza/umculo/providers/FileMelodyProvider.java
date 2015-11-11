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

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

import eu.veldsoft.basakaza.umculo.base.Melody;
import eu.veldsoft.basakaza.umculo.base.Note;

/**
 * File melody provider class is responsible to generate melody with length,
 * timber and notes specified in a descriptor file.
 * 
 * The format is [octave][note][duration].
 * 
 * http://www.harmony-central.com/MIDI/Doc/table2.html
 * 
 * [octave] is number between -1 and 9.
 * 
 * [note] is symbol(s): A, A#, B, C, C#, D, D#, E, F, F#, G, G#.
 * 
 * [duration] is in milliseconds.
 * 
 * @author Todor Balabanov
 * 
 * @email todor.balabanov@gmail.com
 * 
 * @date 29 May 2014
 */
class FileMelodyProvider implements MelodyProvider {
	/**
	 * Name of the file with input information.
	 */
	private String fileName = "";

	/**
	 * Parse note from text to object.
	 * 
	 * @param string
	 *            Note as text.
	 * 
	 * @return Note as object.
	 * 
	 * @throws NotValidDescriptorFileException
	 *             Wrong input data.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 11 Nov 2015
	 */
	private static Note parseNote(String string) throws NotValidDescriptorFileException {
		String token = string;

		int octave = -2;
		if (string.charAt(0) == '-' && string.charAt(1) == '1') {
			octave = -1;
			string = string.substring(2);
		} else if (string.charAt(0) == '0') {
			octave = 0;
			string = string.substring(1);
		} else if (string.charAt(0) == '1') {
			octave = 1;
			string = string.substring(1);
		} else if (string.charAt(0) == '2') {
			octave = 2;
			string = string.substring(1);
		} else if (string.charAt(0) == '3') {
			octave = 3;
		} else if (string.charAt(0) == '4') {
			octave = 4;
			string = string.substring(1);
		} else if (string.charAt(0) == '5') {
			octave = 5;
			string = string.substring(1);
		} else if (string.charAt(0) == '6') {
			octave = 6;
			string = string.substring(1);
		} else if (string.charAt(0) == '7') {
			octave = 7;
			string = string.substring(1);
		} else if (string.charAt(0) == '8') {
			octave = 8;
			string = string.substring(1);
		} else if (string.charAt(0) == '9') {
			octave = 9;
			string = string.substring(1);
		} else {
			throw (new NotValidDescriptorFileException("Octave number is not correct [" + token + "]!"));
		}

		int note = 0;
		if (string.charAt(0) == 'A' && string.charAt(1) != '#') {
			note += (octave + 1) * 12 + 9;
			string = string.substring(1);
		} else if (string.charAt(0) == 'A' && string.charAt(1) == '#') {
			note += (octave + 1) * 12 + 10;
			string = string.substring(2);
		} else if (string.charAt(0) == 'B' && string.charAt(1) != '#') {
			note += (octave + 1) * 12 + 11;
			string = string.substring(1);
		} else if (string.charAt(0) == 'C' && string.charAt(1) != '#') {
			note += (octave + 1) * 12 + 0;
			string = string.substring(1);
		} else if (string.charAt(0) == 'C' && string.charAt(1) == '#') {
			note += (octave + 1) * 12 + 0;
			string = string.substring(2);
		} else if (string.charAt(0) == 'D' && string.charAt(1) != '#') {
			note += (octave + 1) * 12 + 2;
			string = string.substring(1);
		} else if (string.charAt(0) == 'D' && string.charAt(1) == '#') {
			note += (octave + 1) * 12 + 3;
			string = string.substring(2);
		} else if (string.charAt(0) == 'E' && string.charAt(1) != '#') {
			note += (octave + 1) * 12 + 4;
			string = string.substring(1);
		} else if (string.charAt(0) == 'F' && string.charAt(1) != '#') {
			note += (octave + 1) * 12 + 5;
			string = string.substring(1);
		} else if (string.charAt(0) == 'F' && string.charAt(1) == '#') {
			note += (octave + 1) * 12 + 6;
			string = string.substring(2);
		} else if (string.charAt(0) == 'G' && string.charAt(1) != '#') {
			note += (octave + 1) * 12 + 7;
			string = string.substring(1);
		} else if (string.charAt(0) == 'G' && string.charAt(1) == '#') {
			note += (octave + 1) * 12 + 8;
			string = string.substring(2);
		} else {
			throw (new NotValidDescriptorFileException("Note number is not correct [" + token + "]!"));
		}

		int duration = 0;
		try {
			duration = Integer.parseInt(string);
		} catch (NumberFormatException ex) {
			throw (new NotValidDescriptorFileException("Note duration is not correct number [" + token + "]!"));
		}

		// TODO Better way to select note velocity is needed.
		return (new Note(note, 0, duration, 64));
	}

	/**
	 * Constructor with parameter of input file.
	 * 
	 * @param fileName
	 *            Name of the input file.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 29 May 2014
	 */
	public FileMelodyProvider(String fileName) {
		super();
		this.fileName = fileName;
	}

	/**
	 * Create melody as sequence of music notes by parsing descriptor file. By
	 * this way system can be easy populated with a lot of melodies composed by
	 * human. Descriptor file name is taken as parameter. If descriptor file is
	 * not valid exception is thrown.
	 * 
	 * http://www.harmony-central.com/MIDI/Doc/table2.html
	 * 
	 * @param fileName
	 *            File name of descriptor file.
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
		// TODO Better file format description as documentation is needed.
		Melody melody = new Melody();

		String melodyText = "";

		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String line = null;
			while ((line = in.readLine()) != null) {
				melodyText += line;
			}
			in.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		StringTokenizer tokenizer = new StringTokenizer(melodyText, " ", false);

		/*
		 * First number into the file format is timber number.
		 */
		if (tokenizer.hasMoreTokens() == false) {
			throw (new NotValidDescriptorFileException("Timber is not available!"));
		}

		/*
		 * Timber should be positive integer number.
		 */
		int timber = 0;
		try {
			timber = Integer.parseInt(tokenizer.nextToken());
		} catch (NumberFormatException ex) {
			throw (new NotValidDescriptorFileException("Timber is not correct number!"));
		}

		/*
		 * Timber should be between 1 and 128.
		 */
		if (timber < 1 || timber > 128) {
			throw (new NotValidDescriptorFileException("Timber is not between 1 and 128!"));
		} else {
			melody.setTimber(timber);
		}
		melody.setId(Melody.getUniqueId());
		melody.setScore(0);

		int timeCounter = 0;

		/*
		 * All notes are fowling after timber.
		 */
		while (tokenizer.hasMoreTokens() == true) {
			Note note = parseNote(tokenizer.nextToken());

			// TODO It is not clear how much time is between two notes.
			note.setOffset(timeCounter);

			melody.addNote(note);

			timeCounter += note.getDuration();
		}

		return (melody);
	}

	@Override
	public Melody provide(int length) {
		return provide();
	}
}
