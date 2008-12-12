package scp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JPanel;

import scp.common.Gap;
import scp.common.IPlaceableObject;
import scp.common.PlacedShape;

/**
 * JPanel for the stockroll
 * @author Benjamin Clauss
 */
@SuppressWarnings("serial")
public class RightDrawingPane extends JPanel {

	private static final int BOTTOMGAP = 50;
	
	private boolean scrollToRed;
	private IPlaceableObject scrollToObj = null;
	private ArrayList<IPlaceableObject> rightList = new ArrayList<IPlaceableObject>();

	/**
	 * 
	 * @param rightList list of PlaceableObjects for the stockroll
	 */
	public RightDrawingPane(ArrayList<IPlaceableObject> rightList) {
		this.rightList = rightList;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// AffineTransform at = new AffineTransform();
		//		
		// at.translate(0, 446);
		// at.scale(1, -1);
		//		
		// Graphics2D g2d = (Graphics2D)g;
		// g2d.setTransform(at);

		int maxY = 0;

		for (IPlaceableObject obj : rightList) {
			// check if obj is a shape or a gap
			if (obj instanceof PlacedShape) {
				if (((ColoredPlacedShape) obj).getColor().equals(Color.red)) {
					scrollToObj = obj;
				}
				g.setColor(((ColoredPlacedShape) obj).getColor());
				g.fillRect(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
				g.setColor(Color.black);
				g.drawRect(obj.getX(), obj.getY(), obj.getWidth() - 1, obj.getHeight() - 1);
			} else if (obj instanceof Gap) {
				g.setColor(Color.red);
				int[] xPoints = { obj.getX(), obj.getX(), obj.getX() + ((Gap) obj).getWidth() - 1, obj.getX() + ((Gap) obj).getWidth() - 1 };
				int[] yPoints = { obj.getY() + ((Gap) obj).getLeftHeight(), obj.getY(), obj.getY(), obj.getY() + ((Gap) obj).getRightHeight() };
				g.drawPolyline(xPoints, yPoints, 4);
			}
			if ((obj.getY() + obj.getHeight()) > maxY) {
				maxY = obj.getY() + obj.getHeight();
			}
		}

		setPreferredSize(new Dimension(0, maxY + BOTTOMGAP));

		revalidate();

		if ((scrollToRed) && (scrollToObj != null)) {
			scrollRectToVisible(new Rectangle(new Point(0, scrollToObj.getY() + scrollToObj.getHeight() + BOTTOMGAP)));
			setScrollToRed(false);
			revalidate();
		}
	}

	public void setScrollToRed(boolean scrollToRed) {
		this.scrollToRed = scrollToRed;
	}
}