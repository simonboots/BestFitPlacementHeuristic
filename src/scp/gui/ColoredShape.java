package scp.gui;

import java.awt.Color;
import scp.common.Shape;

public class ColoredShape extends Shape {

  private Color color;

  public ColoredShape(Shape s) {
    super(s.getId(), s.getHeight(), s.getWidth());
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}