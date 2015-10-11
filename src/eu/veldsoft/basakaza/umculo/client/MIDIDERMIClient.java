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

package eu.veldsoft.basakaza.umculo.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

import javax.swing.JApplet;
import javax.swing.JFrame;

import eu.veldsoft.basakaza.umculo.common.MIDIDERMIInterface;
import eu.veldsoft.basakaza.umculo.common.MIDIDERMITask;

/**
 * RMI client luncher. All remote side calculations are done here. This class is
 * responsible to request calculating task from the server. In parallel of the
 * calculation process user evaluation is done.
 * 
 * @author Todor Balabanov
 * 
 * @email todor.balabanov@gmail.com
 * 
 * @date 22 May 2014
 */
public class MIDIDERMIClient extends JApplet implements KeyListener, Runnable {
	/**
	 * Default serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instance of the remote server object.
	 */
	MIDIDERMIInterface simpleServerObject = null;

	/**
	 * Handle to task in progress to proceed keyboard events.
	 */
	private MIDIDERMITask task = null;

	/**
	 * Perform music playing and scoring.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 26 May 2014
	 */
	private void perform() {
		try {
			if (task == null) {
				task = simpleServerObject.request();

				Graphics g = this.getGraphics();
				g.setClip(this.getX(), this.getY(), this.getWidth(), this.getHeight());
				task.setGraphics(g);

				(new Thread(this)).start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Standard key listener key pressed method.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 26 May 2014
	 */
	public void keyPressed(KeyEvent event) {
	}

	/**
	 * Standard key listener key released method. By pressing up or down arrow
	 * user is able to change the score of the melody.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 26 May 2014
	 */
	public void keyReleased(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_UP) {
			task.scoreUp();
		}

		if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			task.scoreDown();
		}
	}

	/**
	 * Standard key listener key typed method.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 26 May 2014
	 */
	public void keyTyped(KeyEvent event) {
	}

	/**
	 * Obtain RMI object.
	 * 
	 * @param address
	 *            Address of RMI remote server.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 26 May 2014
	 */
	public void obtainRmiObject(String address) {
		System.setSecurityManager(new RMISecurityManager());

		try {
			simpleServerObject = (MIDIDERMIInterface) Naming.lookup("rmi://" + address + "/MIDIDERMIImplementInstance");
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (RemoteException ex) {
			ex.printStackTrace();
		} catch (NotBoundException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Standard applet init method.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 06 Jun 2014
	 */
	public void init() {
		this.addKeyListener(this);

		setSize(640, 480);
		setLocation(0, 0);

		try {
			String address = getParameter("rmi-server-address");
			if (address != null) {
				obtainRmiObject(address);
			}
		} catch (Exception ex) {
			/*
			 * Do not print anything because information is provided in main()
			 * when code is executed as stand-alone application.
			 */
		}

		setBackground(Color.BLACK);
		requestFocus();

		perform();
	}

	/**
	 * Standard thread method run. It is needed because class is runnable.
	 * 
	 * http://java.sys-con.com/read/46096.htm
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 07 Jun 2014
	 */
	public void run() {
		task.calculate();

		try {
			simpleServerObject.response(task);
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}

		task = null;
	}

	/**
	 * Main starting point of the program. In this method remote instance is
	 * invoked and calculations are done in a loop.
	 * 
	 * @param args
	 *            Command line parameters.
	 * 
	 * @author Todor Balabanov
	 * 
	 * @email todor.balabanov@gmail.com
	 * 
	 * @date 22 May 2014
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("MIDIDERMI client ...");
		frame.setSize(640, 480);

		MIDIDERMIClient applet = new MIDIDERMIClient();
		applet.obtainRmiObject(args[0]);

		frame.add(applet);
		frame.setVisible(true);

		applet.init();
		applet.start();
	}
}
