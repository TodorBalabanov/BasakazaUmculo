package eu.veldsoft.basakaza.umculo.base;

import java.util.Comparator;

/**
 * Compare notes according their offset from the beginning of the melody.
 *
 * @param <T>
 *            Type of the note objects.
 * 
 * @author Todor Balabanov
 * 
 * @email todor.balabanov@gmail.com
 * 
 * @date 11 Nov 2015
 */
class NoteOffsetComparator<T> implements Comparator<Note> {

	/**
	 * Compare two notes.
	 * 
	 * @param a
	 *            Left hand side parameter.
	 * 
	 * @param b
	 *            Right hand side parameter.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 11 Nov 2015
	 */
	@Override
	public int compare(Note a, Note b) {
		// TODO Should be tested, because it is possible a and b to be in wrong
		// order.
		return a.getOffset() - b.getOffset();
	}

}
