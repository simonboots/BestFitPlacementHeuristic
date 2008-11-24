package scp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LeftDrawingPane extends JPanel {

  private ArrayList<ColoredShape> leftlist = new ArrayList<ColoredShape>();
  //List-Settings
  private static final int MAX = 50;         // length of longest shape side
  private static final int VGAP = 40;         // vertical gap between shapes
  private static final int INITX = 20;        // initial x-coordinate
  private static final int INITY = 20;        // initial y-coordinate
  private static final int PANELWIDTH = 250;

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

    setPreferredSize(new Dimension(0, 0));

    for (ColoredShape shape : leftlist) {

      // height && width < MAX
      if ((shape.getHeight() <= MAX) && (shape.getWidth() <= MAX)) {
        scaledWidth = shape.getWidth();
        scaledHeight = shape.getHeight();
      // width > height && width > MAX
      } else if ((shape.getWidth() >= shape.getHeight()) && (shape.getWidth() > MAX)) {
        scaledWidth = MAX;
        scaledHeight = ((float) (shape.getHeight()) / (float) (shape.getWidth())) * MAX;
      // height > width && height > MAX
      } else if ((shape.getHeight() > shape.getWidth()) && (shape.getHeight() > MAX)) {
        scaledHeight = MAX;
        scaledWidth = ((float) (shape.getWidth()) / (float) (shape.getHeight())) * MAX;
      }

      g.setColor(shape.getColor());
      g.fillRect(PANELWIDTH - INITX - (int) scaledWidth, yCoord, (int) scaledWidth, (int) scaledHeight);

      g.setColor(Color.black);
      g.drawRect(PANELWIDTH - INITX - (int) scaledWidth, yCoord, (int) scaledWidth - 1, (int) scaledHeight - 1);

      g.drawString("SID: " + shape.getId(), xCoord, yCoord + 9);
      g.drawString(shape.getHeight() + " x " + shape.getWidth(), xCoord, yCoord + 25);

      g.setColor(Color.lightGray);
      g.drawLine(0, (int) scaledHeight + yCoord + VGAP / 2, PANELWIDTH, (int) scaledHeight + yCoord + VGAP / 2);

      yCoord += scaledHeight + VGAP;

      setPreferredSize(new Dimension(0, yCoord));

    }
    revalidate();
  }
}