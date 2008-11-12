package scp.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JPanel;

public class RightDrawingPane extends JPanel {

  private HashMap<Integer, ColoredPlacedShape> sortedList = new HashMap<Integer, ColoredPlacedShape>();

  public RightDrawingPane(HashMap<Integer, ColoredPlacedShape> sortedList) {
    this.sortedList = sortedList;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Iterator itr = sortedList.keySet().iterator();

    while (itr.hasNext()) {
      Object key = itr.next();

      g.setColor(sortedList.get(key).getColor());
      g.fillRect(sortedList.get(key).getX(),
              sortedList.get(key).getY(),
              sortedList.get(key).getWidth(),
              sortedList.get(key).getHeight());

      g.setColor(Color.black);
      g.drawRect(sortedList.get(key).getX(),
              sortedList.get(key).getY(),
              sortedList.get(key).getWidth(),
              sortedList.get(key).getHeight());
    }
    revalidate();
  }
}