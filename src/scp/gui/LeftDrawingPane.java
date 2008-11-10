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
  //List-Settings
  private static final int MAX = 50;         // length of longest shape side
  private static final int VGAP = 40;         // vertical gap between shapes
  private static final int INITX = 20;        // initial x-coordinate
  private static final int INITY = 20;        // initial y-coordinate
  private static final int PANELWIDTH = 250;

  public LeftDrawingPane(HashMap<Integer, Shape> leftList) {
    this.leftList = leftList;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int xCoord = INITX;
    int yCoord = INITY;

    float scaledWidth = 0;
    float scaledHeight = 0;

    setPreferredSize(new Dimension(0, 0));

    Iterator itr = leftList.keySet().iterator();

    while (itr.hasNext()) {
      Object key = itr.next();

      System.out.println(key.toString());

      // height && width < MAX
      if ((leftList.get(key).getHeight() <= MAX) && (leftList.get(key).getWidth() <= MAX)) {
        scaledWidth = leftList.get(key).getWidth();
        scaledHeight = leftList.get(key).getHeight();
      // width > height && width > MAX
      } else if ((leftList.get(key).getWidth() >= leftList.get(key).getHeight()) && (leftList.get(key).getWidth() > MAX)) {
        scaledWidth = MAX;
        scaledHeight = ((float) (leftList.get(key).getHeight()) / (float) (leftList.get(key).getWidth())) * MAX;
      // height > width && height > MAX
      } else if ((leftList.get(key).getHeight() > leftList.get(key).getWidth()) && (leftList.get(key).getHeight() > MAX)) {
        scaledHeight = MAX;
        scaledWidth = ((float) (leftList.get(key).getWidth()) / (float) (leftList.get(key).getHeight())) * MAX;
      }

      g.setColor(Color.lightGray);
      g.fillRect(PANELWIDTH - INITX - (int) scaledWidth, yCoord, (int) scaledWidth, (int) scaledHeight);

      g.setColor(Color.black);
      g.drawRect(PANELWIDTH - INITX - (int) scaledWidth, yCoord, (int) scaledWidth - 1, (int) scaledHeight - 1);

      g.drawString("SID: " + leftList.get(key).getId(), xCoord, yCoord + 9);
      g.drawString(leftList.get(key).getHeight() + " x " + leftList.get(key).getWidth(), xCoord, yCoord + 25);

      g.setColor(Color.lightGray);
      g.drawLine(0, (int) scaledHeight + yCoord + VGAP / 2, PANELWIDTH, (int) scaledHeight + yCoord + VGAP / 2);

      yCoord += scaledHeight + VGAP;

      setPreferredSize(new Dimension(0, yCoord));

    }
    revalidate();
  }
}