package scp.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import scp.common.Shape;

public class LeftDrawingPane extends JPanel {
  private ArrayList<Shape> shapeList;
  
  public LeftDrawingPane(ArrayList<Shape> shapeList) {
    this.shapeList = shapeList;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    for (Shape s : shapeList) {
      g.setColor(Color.black);
      g.fillRect(s.getX(), s.getY(), s.getWidth(), s.getHeight());
      revalidate();
      repaint();
    }
  }
}