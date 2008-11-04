package scp.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JPanel;
import scp.common.PlacedShape;

public class RightDrawingPane extends JPanel {

  private HashMap<Integer, PlacedShape> sortedList = new HashMap<Integer, PlacedShape>();

  public RightDrawingPane(HashMap<Integer, PlacedShape> sortedList) {
    this.sortedList = sortedList;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    Iterator itr = sortedList.keySet().iterator();
    
    while(itr.hasNext()) {
      Object key = itr.next();
      g.setColor(Color.lightGray);
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
    repaint();
  }
}