package scp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * JPanel for the magazine
 * @author Benjamin Clauss
 */
@SuppressWarnings("serial")
public class LeftDrawingPane extends JPanel {

	private ArrayList<ColoredShape> leftlist = new ArrayList<ColoredShape>();
	/**
	 * List-Settings
	 */
	private static final int MAX = 50; // length of longest shape side
	private static final int VGAP = 20; // vertical gap between shapes and line
	private static final int INITX = 20; // initial x-coordinate
	private static final int INITY = 20; // initial y-coordinate
	private static final int PANELWIDTH = 250;

	/**
	 * 
	 * @param leftlist list of Shapes for the magazine
	 */
	public LeftDrawingPane(ArrayList<ColoredShape> leftlist) {
		this.leftlist = leftlist;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int xCoord = INITX;
		int yCoord = INITY;
		float scaledWidth = 0;
		float scaledHeight = 0;

		for (ColoredShape cs : leftlist) {

			// square handling
			if(cs.getHeight() == cs.getWidth()) {
				if(cs.getHeight() > MAX) {
					scaledHeight = MAX;
					scaledWidth = MAX;
				} else if (cs.getHeight() >= MAX) {
					scaledHeight = cs.getHeight();
					scaledWidth = cs.getWidth();
				}
			// height < MAX && width < MAX
			} else if ((cs.getHeight() <= MAX) && (cs.getWidth() <= MAX)) {
				scaledWidth = cs.getWidth();
				scaledHeight = cs.getHeight();
			// width > height
			} else if (cs.getWidth() > cs.getHeight()) {
				scaledWidth = MAX;
				scaledHeight = ((float) (cs.getHeight()) / (float) (cs.getWidth())) * MAX;
			// height > width
			} else if (cs.getHeight() > cs.getWidth()) {
				scaledHeight = MAX;
				scaledWidth = ((float) (cs.getWidth()) / (float) (cs.getHeight())) * MAX;
			}

			// drawing zone
			g.setColor(cs.getColor());
			g.fillRect(PANELWIDTH - INITX - (int) scaledWidth, yCoord, (int) scaledWidth, (int) scaledHeight);

			g.setColor(Color.black);
			g.drawRect(PANELWIDTH - INITX - (int) scaledWidth, yCoord, (int) scaledWidth - 1, (int) scaledHeight - 1);

			g.drawString("SID: " + cs.getId(), xCoord, yCoord + 9);
			g.drawString(cs.getHeight() + " x " + cs.getWidth(), xCoord, yCoord + 25);

			g.setColor(Color.lightGray);
			g.drawLine(0, yCoord + MAX + VGAP, PANELWIDTH, yCoord + MAX + VGAP);

			yCoord += MAX + 2 * VGAP;

			setPreferredSize(new Dimension(0, yCoord));

		}
		revalidate();
	}
}