package eu.veldsoft.basakaza.umculo.base;

import java.util.Comparator;

/**
 * Compare melodies according their score in the population.
 *
 * @param <T>
 *            Type of the melody objects.
 * 
 * @author Todor Balabanov
 * 
 * @email todor.balabanov@gmail.com
 * 
 * @date 11 Nov 2015
 */
class MelodyScoreComparator<T> implements Comparator<Melody> {

	/**
	 * Compare two melodies.
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
	public int compare(Melody a, Melody b) {
		// TODO Should be tested, because it is possible a and b to be in wrong
		// order.
		return b.getScore() - a.getScore();
	}

}
