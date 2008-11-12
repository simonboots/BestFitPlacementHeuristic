package scp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import javax.swing.JPanel;

public class LeftDrawingPane extends JPanel {

  private HashMap<Integer, ColoredShape> leftList = new HashMap<Integer, ColoredShape>();
  //List-Settings
  private static final int MAX = 50;         // length of longest shape side
  private static final int VGAP = 40;         // vertical gap between shapes
  private static final int INITX = 20;        // initial x-coordinate
  private static final int INITY = 20;        // initial y-coordinate
  private static final int PANELWIDTH = 250;

  public LeftDrawingPane(HashMap<Integer, ColoredShape> leftList) {
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

    for (int i = 0; i < leftList.size(); i++) {
      // height && width < MAX
      if ((leftList.get(i).getHeight() <= MAX) && (leftList.get(i).getWidth() <= MAX)) {
        scaledWidth = leftList.get(i).getWidth();
        scaledHeight = leftList.get(i).getHeight();
      // width > height && width > MAX
      } else if ((leftList.get(i).getWidth() >= leftList.get(i).getHeight()) && (leftList.get(i).getWidth() > MAX)) {
        scaledWidth = MAX;
        scaledHeight = ((float) (leftList.get(i).getHeight()) / (float) (leftList.get(i).getWidth())) * MAX;
      // height > width && height > MAX
      } else if ((leftList.get(i).getHeight() > leftList.get(i).getWidth()) && (leftList.get(i).getHeight() > MAX)) {
        scaledHeight = MAX;
        scaledWidth = ((float) (leftList.get(i).getWidth()) / (float) (leftList.get(i).getHeight())) * MAX;
      }

      g.setColor(leftList.get(i).getColor());
      g.fillRect(PANELWIDTH - INITX - (int) scaledWidth, yCoord, (int) scaledWidth, (int) scaledHeight);

      g.setColor(Color.black);
      g.drawRect(PANELWIDTH - INITX - (int) scaledWidth, yCoord, (int) scaledWidth - 1, (int) scaledHeight - 1);

      g.drawString("SID: " + leftList.get(i).getId(), xCoord, yCoord + 9);
      g.drawString(leftList.get(i).getHeight() + " x " + leftList.get(i).getWidth(), xCoord, yCoord + 25);

      g.setColor(Color.lightGray);
      g.drawLine(0, (int) scaledHeight + yCoord + VGAP / 2, PANELWIDTH, (int) scaledHeight + yCoord + VGAP / 2);

      yCoord += scaledHeight + VGAP;

      setPreferredSize(new Dimension(0, yCoord));

    }
    revalidate();
  }
}