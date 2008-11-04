package scp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JPanel;
import scp.common.Shape;

public class LeftDrawingPane extends JPanel {

  private HashMap<Integer, Shape> leftList = new HashMap<Integer, Shape>();

  public LeftDrawingPane(HashMap<Integer, Shape> leftList) {
    this.leftList = leftList;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int x = 20;
    int y = 20;
    
    setPreferredSize(new Dimension(0, 0));
    
    Iterator itr = leftList.keySet().iterator();

    while(itr.hasNext()) {
      Object key = itr.next();
      
      g.setColor(Color.black);
      g.drawString("ID: " + leftList.get(key).getId(), x, y + 9);

      g.setColor(Color.lightGray);      
      g.fillRect(260 - leftList.get(key).getWidth(), y, leftList.get(key).getWidth(), leftList.get(key).getHeight());      
      g.drawLine(20, leftList.get(key).getHeight() + y + 20, 260, leftList.get(key).getHeight() + y + 20);
      
      g.setColor(Color.black);
      g.drawRect(260 - leftList.get(key).getWidth(), y, leftList.get(key).getWidth(), leftList.get(key).getHeight());

      y += leftList.get(key).getHeight() + 40;

      setPreferredSize(new Dimension(0, y));
      revalidate();
    }
    revalidate();
    repaint();
  }
}