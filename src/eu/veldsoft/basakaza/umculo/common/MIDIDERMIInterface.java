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

import java.rmi.Remote;

/**
 * Remote interface functionality. Methods which server provides to the remote
 * clients.
 * 
 * @author Todor Balabanov
 * 
 * @email todor.balabanov@gmail.com
 * 
 * @date 22 May 2014
 */
public interface MIDIDERMIInterface extends Remote {
	/**
	 * Request the task for calculation. By this method remote client request
	 * task for calculation.
	 * 
	 * @return Task.
	 * 
	 * @throws java.rmi.RemoteException
	 *             RMI should be safe.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public MIDIDERMITask request() throws java.rmi.RemoteException;

	/**
	 * Return the calculated task. The result of the calculation on the remote
	 * side is returned by the requested task structure.
	 * 
	 * @param val
	 *            Task.
	 * 
	 * @throws java.rmi.RemoteException
	 *             RMI should be safe.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public void response(MIDIDERMITask val) throws java.rmi.RemoteException;
}
