package scp.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import scp.common.Gap;
import scp.common.IPlaceableObject;
import scp.common.PlacedShape;

@SuppressWarnings("serial")
public class RightDrawingPane extends JPanel {

	private ArrayList<IPlaceableObject> rightList = new ArrayList<IPlaceableObject>();

	public RightDrawingPane(ArrayList<IPlaceableObject> rightList) {
		this.rightList = rightList;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (IPlaceableObject obj : rightList) {
			if (obj instanceof PlacedShape) {
				g.setColor(((ColoredPlacedShape) obj).getColor());
				g.fillRect(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
				g.setColor(Color.black);
				g.drawRect(obj.getX(), obj.getY(), obj.getWidth() - 1, obj.getHeight() - 1);
			} else if (obj instanceof Gap) {
				g.setColor(Color.orange);
				int[] xPoints = { obj.getX(), obj.getX(), obj.getX() + ((Gap) obj).getWidth() - 1, obj.getX() + ((Gap) obj).getWidth() - 1 };
				int[] yPoints = { obj.getY() + ((Gap) obj).getLeftHeight(), obj.getY(), obj.getY(), obj.getY() + ((Gap) obj).getRightHeight() };
				g.drawPolyline(xPoints, yPoints, 4);
			}
		}
		revalidate();
	}
}