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
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

/**
 * Differential evolution population class. Implementation of genetic algorithm
 * based optimization approach. All melodies are presented as chromosomes
 * (vectors). During evolution period recombination of chromosomes is applied
 * and human evaluation of the melody quality.
 * 
 * @author Todor Balabanov
 * 
 * @email todor.balabanov@gmail.com
 * 
 * @date 22 May 2014
 */
public class Population implements Cloneable, Serializable {

	/**
	 * Default serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Min population size for random generation.
	 */
	public static final int MIN_RANDOM_POPULATION = 3;

	/**
	 * Max population size for random generation.
	 */
	public static final int MAX_RANDOM_POPULATION = 5;

	/**
	 * Min population size for fractal generation.
	 */
	public static final int MIN_FRACTAL_POPULATION = 3;

	/**
	 * Max population size for fractal generation.
	 */
	public static final int MAX_FRACTAL_POPULATION = 5;

	/**
	 * Size of the DE population.
	 */
	private int size = 0;

	/**
	 * Chromosomes set of DE.
	 */
	private Vector<Melody> offspring;

	/**
	 * Handle to the melody which is playing.
	 */
	private Melody melodyPaying = null;

	/**
	 * Melodies comparator according their fitness value.
	 */
	private Comparator<Melody> comparator = new MelodyScoreComparator<Melody>();

	/**
	 * Constructor without parameters. Internal structure is created during
	 * constructor execution.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public Population() {
		super();

		offspring = new Vector<Melody>();
	}

	/**
	 * Current playing melody setter.
	 * 
	 * @param melodyPaying
	 *            Reference to the melody which plays at a specific moment.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 17 Nov 2015
	 */
	public void setMelodyPaying(Melody melodyPaying) {
		this.melodyPaying = melodyPaying;
	}

	/**
	 * Population size getter.
	 * 
	 * @return Population size without new generation.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 30 May 2014
	 */
	public int getSize() {
		return (size);
	}

	/**
	 * Population size setter.
	 * 
	 * @param size
	 *            Population size without new generation.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 30 May 2014
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * All population melodies getter.
	 * 
	 * @return All melodies.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public Vector<Melody> getMelodies() {
		return offspring;
	}

	/**
	 * Add melody. Population can be extended during evolution process.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public void add(Melody melody) {
		melody.setId(offspring.size());
		offspring.add(melody);

		size++;
	}

	/**
	 * Recombination of the chromosomes according to DE rules. Choosing
	 * chromosomes and do differential vector addition.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public void recombine() {
		Melody melody = null;
		int size = offspring.size();

		for (int i = 0; i < size; i++) {
			melody = (Melody) (offspring.elementAt(i)).clone();
			melody.update((offspring.elementAt(i)).getDiffertial(offspring.elementAt((int) (Math.random() * size))));
			melody.setId(offspring.size());

			offspring.add(melody);
		}
	}

	/**
	 * Sort the population according to the fitness value. One of the possible
	 * ways to select parents for the next generation.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public void sort() {
		Collections.sort(offspring, comparator);
	}

	/**
	 * Remove extra population chromosomes. New generation is added as part of
	 * the old generation, after selection process only limited amount of
	 * chormosomes should survive.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public void shrink() {
		for (int i = offspring.size() - 1; i >= size; i--) {
			offspring.removeElementAt(i);
		}
	}

	/**
	 * Population epochs. Calculates given number of epochs as part of
	 * population evolution.
	 * 
	 * @param number
	 *            How many epochs.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public void epoches(int number) {
		for (int i = 0; i < number; i++) {
			recombine();
			sort();
			shrink();
		}
	}

	/**
	 * Score up of the currently playing melody. Method is provided as
	 * connection between graphic user interface and real melody object.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 26 May 2014
	 */
	public void scoreUp() {
		melodyPaying.scoreUp();

		System.out.println(melodyPaying);
	}

	/**
	 * Score down of the currently playing melody. Method is provided as
	 * connection between graphic user interface and real melody object.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 26 May 2014
	 */
	public void scoreDown() {
		melodyPaying.scoreDown();

		System.out.println(melodyPaying);
	}

	/**
	 * Clone the population object. The most clean way to make identical copy of
	 * complex data structure.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public Object clone() {
		Population population = new Population();

		population.offspring = new Vector<Melody>();

		for (int i = 0; i < offspring.size(); i++) {
			population.offspring.add((Melody) (offspring.elementAt(i)).clone());
		}

		population.size = size;

		return (population);
	}
}
