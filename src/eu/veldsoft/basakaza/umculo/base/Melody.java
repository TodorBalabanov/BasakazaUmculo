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

package eu.veldsoft.basakaza.umculo.base;

import java.io.Serializable;
import java.util.Vector;

/**
 * Melody is a sequence of music notes.
 * 
 * @author Todor Balabanov
 * 
 * @email todor.balabanov@gmail.com
 * 
 * @date 22 May 2014
 */
public class Melody implements Cloneable, Serializable {
	/**
	 * Default serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Max time used during melody construction.
	 */
	private static final long MAX_TIME_MIDI_CONSTRUCTION = 100000L;

	/**
	 * Sequence of music notes.
	 */
	private Vector<Note> sequence;

	/**
	 * Melody timber or number of patch playing.
	 */
	private int timber;

	/**
	 * Score given by the users for this melody.
	 */
	private int score;

	/**
	 * Identifier for the unique melodies.
	 */
	private long id;

	/**
	 * Genre as number from predefined constants.
	 */
	private int genre;

	/**
	 * Convert long number to MIDI variable length array. Supporting method for
	 * data type conversion needed by MIDI specification of data representation.
	 * 
	 * @param num
	 *            Regular long number.
	 * 
	 * @return Array with MIDI var length values.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	private char[] long2var(long num) {
		char res[] = new char[4];

		num = ((num & 0xffffff80) << 1) | num & 0x0000007f;
		num = ((num & 0xffff8000) << 1) | num & 0x00007fff;
		num = ((num & 0xff800000) << 1) | num & 0x007fffff;
		num = ((num & 0x80000000) << 1) | num & 0x7fffffff;

		num &= 0xffffff7f;
		num |= 0x00008000;
		num |= 0x00800000;
		num |= 0x80000000;

		/*
		 * 4 bytes mask for MIDI variable length value.
		 */
		res[0] = (char) ((num & 0x7f000000) >> 48);
		res[1] = (char) ((num & 0x007f0000) >> 32);
		res[2] = (char) ((num & 0x00007f00) >> 16);
		res[3] = (char) ((num & 0x0000007f) >> 0);
		res[0] |= 0x80;
		res[1] |= 0x80;
		res[2] |= 0x80;
		res[3] &= 0x7f;

		return (res);
	}

	/**
	 * Provide unique identifier based on the time stamp and random number.
	 * 
	 * @return Unique identifier.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 30 May 2014
	 */
	static public long getUniqueId() {
		return ((System.currentTimeMillis() << 3 | (long) (Math.random() * 8)));
	}

	/**
	 * Sorting of the music notes into the melody according time offset from the
	 * beginning of the melody. Sorting is needed because some melodies can be
	 * disordered like notes starting in not proper moments.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public void sort() {
		Note a, b;
		boolean done = false;

		while (done == false) {
			done = true;

			for (int i = 0; i < sequence.size() - 1; i++) {
				a = sequence.elementAt(i);
				b = sequence.elementAt(i + 1);

				if (a.getOffset() > b.getOffset()) {
					sequence.removeElementAt(i);
					sequence.insertElementAt(b, i);
					sequence.removeElementAt(i + 1);
					sequence.insertElementAt(a, i + 1);
					done = false;
				}
			}
		}
	}

	/**
	 * Constructor without parameters. Constructor is needed to create internal
	 * data structures of the object.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public Melody() {
		super();

		sequence = new Vector<Note>();
		timber = 1;
		score = 0;
	}

	/**
	 * Melody length.
	 * 
	 * @return Length of the melody in number of notes.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 27 May 2014
	 */
	int length() {
		return (sequence.size());
	}

	/**
	 * Melody timber getter.
	 * 
	 * @return Melody timber.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 27 May 2014
	 */
	public int getTimber() {
		return (timber);
	}

	/**
	 * Melody timber setter.
	 * 
	 * @param timber
	 *            Melody timber.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 27 May 2014
	 */
	public void setTimber(int timber) {
		if (timber < 1) {
			timber = 1;
		}

		if (timber > 128) {
			timber = 128;
		}

		this.timber = timber;
	}

	/**
	 * Melody score getter.
	 * 
	 * @return Melody score.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public int getScore() {
		return (score);
	}

	/**
	 * Melody score setter.
	 * 
	 * @param score
	 *            Melody score.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Melody score plus one.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 26 May 2014
	 */
	public void scoreUp() {
		score++;
	}

	/**
	 * Melody score minus one.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 26 May 2014
	 */
	public void scoreDown() {
		score--;
	}

	/**
	 * Melody identifier getter.
	 * 
	 * @return Melody identifier.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public long getId() {
		return (id);
	}

	/**
	 * Melody identifier setter.
	 * 
	 * @param id
	 *            Melody identifier.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Melody genre getter.
	 * 
	 * @return Numeric index of genre.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 03 Jun 2014
	 */
	public int getGenre() {
		return (genre);
	}

	/**
	 * Melody genre setter.
	 * 
	 * @param genre
	 *            Numeric index of genre.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 03 Jun 2014
	 */
	public void setGenre(int genre) {
		this.genre = genre;
	}

	/**
	 * Provide string representation of melody notes as group of numbers. This
	 * representation is very useful for database melody storing. All numbers
	 * are separated by space symbol. Amount of numbers in a group depends of
	 * note properties available.
	 * 
	 * @return String representation of the melody notes as group of numbers.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 27 Jun 2014
	 */
	public String getNotesInNumbers() {
		String result = "";
		Note note = null;

		for (int i = 0; i < sequence.size(); i++) {
			note = (Note) sequence.elementAt(i).clone();

			result += note.getNote();
			result += " ";
			result += note.getOffset();
			result += " ";
			result += note.getDuration();
			result += " ";
			result += note.getVelocity();
			result += " ";
		}

		result = result.trim();

		return (result);
	}

	/**
	 * Add music note to the sequence. Sequence of notes can be extended by
	 * simply adding new note. This process may need notes sort ordering.
	 * 
	 * @param note
	 *            Music note.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public void addNote(Note note) {
		sequence.add(note);
	}

	/**
	 * Get note on specific moment in time.
	 * 
	 * @param position
	 *            Percent of moment in time between 0 and 1.
	 * 
	 * @return Note in this special moment or null.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 15 Jun 2014
	 */
	public Note getNoteOn(double position) {
		Note note = null;
		Note result = null;

		double length = 0.0;
		for (int i = 0; i < sequence.size(); i++) {
			note = (Note) sequence.elementAt(i).clone();

			if (note.getOffset() + note.getDuration() > length) {
				length = note.getOffset() + note.getDuration();
			}
		}

		if (length > 0.0) {
			double distance = 1.0;
			for (int i = 0; i < sequence.size(); i++) {
				note = (Note) sequence.elementAt(i).clone();

				if (Math.abs(note.getOffset() / length - position) < distance) {
					distance = Math.abs(note.getOffset() / length - position);
					result = note;
				}

				if (Math.abs((note.getOffset() + note.getDuration()) / length - position) < distance) {
					distance = Math.abs(note.getOffset() / length - position);
					result = note;
				}
			}
		}

		return (result);
	}

	/**
	 * Calculates melody differential. Difference vector is needed during
	 * evolution process and is done by simply calculate difference of all
	 * parameters in each music note.
	 * 
	 * @param val
	 *            Subtractor.
	 * 
	 * @return Differential.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public int[][] getDiffertial(Melody val) {
		int res[][] = new int[sequence.size()][4];
		Note a, b;

		for (int i = 0; i < sequence.size(); i++) {
			a = sequence.elementAt(i);
			b = val.sequence.elementAt(i % val.sequence.size());

			res[i][0] = a.getNote() - b.getNote();
			if (a.getOffset() == b.getOffset())
				res[i][1] = 0;
			else if (a.getOffset() < b.getOffset())
				res[i][1] = -1;
			else if (a.getOffset() > b.getOffset())
				res[i][1] = +1;
			res[i][2] = a.getDuration() - b.getDuration();
			res[i][3] = a.getVelocity() - b.getVelocity();
		}

		return (res);
	}

	/**
	 * Update melody according differential vector. It is needed during
	 * evolution process and difference vector is added note by note in each
	 * properties of the note. By this way new generation is created.
	 * 
	 * @param differential
	 *            Differential vector.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public void update(int differential[][]) {
		Note note = null;

		for (int i = 0; i < sequence.size(); i++) {
			note = sequence.elementAt(i);
			note.setNote(note.getNote() + differential[i % differential.length][0]);
			note.setOffset(note.getOffset() + differential[i % differential.length][1]);
			note.setDuration(note.getDuration() + differential[i % differential.length][2]);
			note.setVelocity(note.getVelocity() + differential[i % differential.length][3]);
			note = null;
		}
	}

	/**
	 * Convert the music melody into MIDI byte file sequence. It is needed
	 * because of the internal data representation and the possibilities Java
	 * API to play MIDI stream.
	 * 
	 * http://www.sonicspot.com/guide/midifiles.html
	 * 
	 * http://jedi.ks.uiuc.edu/~johns/links/music/midifile.html
	 * 
	 * @return Byte sequence.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public char[] toMidiBytes() {
		int size = 0;

		/*
		 * MIDI header size.
		 */
		size += 14;

		/*
		 * Track header size.
		 */
		size += 8;

		/*
		 * Copyrights size.
		 */
		size += 24;

		/*
		 * Select instrument.
		 */
		size += 11;

		/*
		 * Start and end note by number of notes by variable start time plus
		 * event bytes.
		 */
		size += 2 * sequence.size() * (4 + 3);

		/*
		 * Track end event.
		 */
		size += 4;

		char res[] = new char[size];

		for (int i = 0; i < res.length; i++)
			res[i] = 0;

		/*
		 * MIDI header.
		 */
		res[0] = 'M';
		res[1] = 'T';
		res[2] = 'h';
		res[3] = 'd';

		/*
		 * MIDI header size.
		 */
		res[4] = 0;
		res[5] = 0;
		res[6] = 0;
		res[7] = 6;

		/*
		 * MIDI file type.
		 */
		res[8] = 0;
		res[9] = 0;

		/*
		 * MIDI number of tracks.
		 */
		res[10] = 0;
		res[11] = 1;

		/*
		 * MIDI ticks per beat.
		 */
		res[12] = 0;
		res[13] = 120;

		/*
		 * Track header.
		 */
		res[14] = 'M';
		res[15] = 'T';
		res[16] = 'r';
		res[17] = 'k';

		/*
		 * Track size.
		 */
		res[18] = 0;
		res[19] = 0;
		res[20] = 0;
		res[21] = 0;

		/*
		 * Copyrights meta event.
		 */
		res[22] = 0x00;
		res[23] = 0xff;
		res[24] = 0x02;
		res[25] = 0x80;
		res[26] = 0x80;
		res[27] = 0x80;
		res[28] = 0x11;
		res[29] = 'T';
		res[30] = 'o';
		res[31] = 'd';
		res[32] = 'o';
		res[33] = 'r';
		res[34] = ' ';
		res[35] = 'B';
		res[36] = 'a';
		res[37] = 'l';
		res[38] = 'a';
		res[39] = 'b';
		res[40] = 'a';
		res[41] = 'n';
		res[42] = 'o';
		res[43] = 'v';
		res[44] = ' ';
		res[45] = 0xa9;

		/*
		 * Bank select MSB=0, time 0, controller on channel 1.
		 */
		res[46] = 0x00;
		res[47] = 0xb0;
		res[48] = 0x00;
		res[49] = 0x00;

		/*
		 * Bank select LSB=3, time 0, controller on channel 1.
		 */
		res[50] = 0x00;
		res[51] = 0xb0;
		res[52] = 0x20;
		res[53] = 0x03;

		/*
		 * Program change on channel 1, time 0, patches from 1 to 128.
		 */
		res[54] = 0x00;
		res[55] = 0xc0;
		res[56] = (char) timber;

		/*
		 * Music events.
		 */
		int pos = 57;
		for (long time = 0, last = 0; time < MAX_TIME_MIDI_CONSTRUCTION; time++) {
			for (int i = 0; i < sequence.size(); i++) {
				Note note = sequence.elementAt(i);
				/*
				 * Note start on channel.
				 */
				if (time == note.getOffset()) {
					char[] offset = long2var(time - last);
					res[pos++] = offset[0];
					res[pos++] = offset[1];
					res[pos++] = offset[2];
					res[pos++] = offset[3];
					res[pos++] = 0x90 | 0x00;
					res[pos++] = (char) (note.getNote() & 0x7f);
					res[pos++] = (char) (note.getVelocity() & 0x7f);
					last = time;
				}
				/*
				 * Note end on channel.
				 */
				if (time == (note.getOffset() + note.getDuration())) {
					char[] offset = long2var(time - last);
					res[pos++] = offset[0];
					res[pos++] = offset[1];
					res[pos++] = offset[2];
					res[pos++] = offset[3];
					res[pos++] = 0x80 | 0x00;
					res[pos++] = (char) (note.getNote() & 0x7f);
					res[pos++] = (char) (note.getVelocity() & 0x7f);
					last = time;
				}
			}
		}

		/*
		 * The end of the track.
		 */
		res[pos++] = 0x00;
		res[pos++] = 0xff;
		res[pos++] = 0x2f;
		res[pos++] = 0x00;

		/*
		 * Track size. Subtract size of the header from the current position.
		 */
		long truckSize = pos - 22;
		res[18] = (char) ((truckSize & 0xff000000) >> 24);
		res[19] = (char) ((truckSize & 0x00ff0000) >> 16);
		res[20] = (char) ((truckSize & 0x0000ff00) >> 8);
		res[21] = (char) ((truckSize & 0x000000ff) >> 0);

		return (res);
	}

	/**
	 * Compare two melodies. Because the environment is distributed it is very
	 * common one melody to appear twice or more times. Because of that
	 * comparison method is needed.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public boolean equals(Object obj) {
		Melody melody = null;

		try {
			melody = (Melody) obj;
		} catch (Exception ex) {
			return (false);
		}

		if (melody == null)
			return (false);

		if (this.sequence.size() != melody.sequence.size())
			return (false);

		for (int i = 0; i < this.sequence.size(); i++)
			if ((this.sequence.elementAt(i)).equals(melody.sequence.elementAt(i)) == false)
				return (false);

		return (true);
	}

	/**
	 * Clone the melody object. The most clean way to make identical copy of
	 * complex data structure.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public Object clone() {
		Melody melody = new Melody();

		melody.sequence = new Vector<Note>();

		for (int i = 0; i < sequence.size(); i++)
			melody.sequence.add((Note) (sequence.elementAt(i)).clone());
		melody.timber = timber;
		melody.score = score;
		melody.id = id;

		return (melody);
	}

	/**
	 * Transform melody properties into string.
	 * 
	 * @return String representation of the melody properties.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 27 May 2014
	 */
	public String toString() {
		return ("Melody id [" + getId() + "] Score [" + getScore() + "] Timber [" + getTimber() + "] Length [" + length() + "]");
	}
}
