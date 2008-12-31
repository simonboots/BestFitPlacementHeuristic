package scp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URLClassLoader;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * About screen with some information about the program
 * @author Benjamin Clauss
 */
@SuppressWarnings("serial")
public class AboutScreen extends JWindow {

	URLClassLoader cl = (URLClassLoader) this.getClass().getClassLoader();
	private ImageIcon hs_esslingen = new ImageIcon(Toolkit.getDefaultToolkit().getImage(cl.findResource("hs_esslingen.jpg")));

	private JPanel main;
	private JLabel copyright;
	private JLabel title;
	private JLabel hs;

	public AboutScreen() {

		setAlwaysOnTop(true);

		setSize(400, 300);

		// center splashscreen
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;

		setLocation(x, y);

		// main panel
		main = new JPanel(new BorderLayout(10, 10));
		main.setBorder(BorderFactory.createLineBorder(Color.black));
		main.setBackground(Color.white);

		hs = new JLabel(hs_esslingen);

		copyright = new JLabel("(c)2008-2009 by Benjamin Clauss & Simon Stiefel", JLabel.CENTER);
		copyright.setFont(new Font("Arial", Font.PLAIN, 12));
		
		title = new JLabel("<html>The Stock-Cutting Problem</html>", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 25));

		main.add(hs, BorderLayout.NORTH);
		main.add(title, BorderLayout.CENTER);
		main.add(copyright, BorderLayout.SOUTH);
		add(main);

		// close splashscreen on mouseclick
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				setVisible(false);
				dispose();
			}
		});

		setVisible(true);

	}
}