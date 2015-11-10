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

package eu.veldsoft.basakaza.umculo.common;

import java.awt.Graphics;
import java.io.Serializable;

import eu.veldsoft.basakaza.umculo.base.Population;

/**
 * Task to be calculated on the remote side. Task class presents working logic
 * of the melody evolution and melody user based evaluation.
 * 
 * @author Todor Balabanov
 * 
 * @email todor.balabanov@gmail.com
 * 
 * @date 22 May 2014
 */
public class MIDIDERMITask implements Serializable {
	/**
	 * Default serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Number of epochs to be calculated in one invocation.
	 */
	private int numberOfEpochs = 1;

	/**
	 * Population to evolve.
	 */
	private Population population;

	/**
	 * Remote task constructor. It is used only to create copy of the input
	 * parameter.
	 * 
	 * @param population
	 *            Population to evolve on the remote side.
	 * 
	 * @param numberOfEpochs
	 *            Number of epochs to be calculated on the client side.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public MIDIDERMITask(Population population, int numberOfEpochs) {
		super();

		this.population = (Population) population.clone();
		this.numberOfEpochs = numberOfEpochs;
	}

	/**
	 * Remote side calculations. Executes DE evolution process for given number
	 * of epochs.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public void calculate() {
		population.epoches(numberOfEpochs);
	}

	/**
	 * Result of the remote calculation. Clone and return current stated of the
	 * active population.
	 * 
	 * @return Remote calculated population.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public Population getResult() {
		return ((Population) population.clone());
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
		population.scoreUp();
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
		population.scoreDown();
	}

	/**
	 * Number of evolution epochs getter.
	 * 
	 * @return Number of epochs.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 27 Jun 2014
	 */
	public int getNumberOfEpochs() {
		return (numberOfEpochs);
	}

	/**
	 * Number of evolution epochs setter.
	 * 
	 * @param numberOfEpochs
	 *            Number of epochs.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 27 Jun 2014
	 */
	public void setNumberOfEpochs(int numberOfEpochs) {
		this.numberOfEpochs = numberOfEpochs;
	}
}
