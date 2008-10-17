package scp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import scp.common.Shape;

public class LeftDrawingPane extends JPanel {

  private ArrayList<Shape> shapeList = new ArrayList<Shape>();

  public LeftDrawingPane(ArrayList<Shape> shapeList) {
    this.shapeList = shapeList;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int x = 20;
    int y = 20;

    for (Shape s : shapeList) {
      g.setColor(Color.black);
      g.drawString("ID: " + s.getId(), x, y + 9);

      g.fillRect(260 - s.getWidth(), y, s.getWidth(), s.getHeight());

      g.setColor(Color.lightGray);
      g.drawLine(20, s.getHeight() + y + 20, 260, s.getHeight() + y + 20);

      y += s.getHeight() + 40;

      setPreferredSize(new Dimension(0, y));
      revalidate();
      repaint();
    }
  }
}