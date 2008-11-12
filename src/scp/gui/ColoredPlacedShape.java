package scp.gui;

import java.awt.Color;
import scp.common.PlacedShape;

public class ColoredPlacedShape extends PlacedShape {

  private Color color;

  public ColoredPlacedShape(PlacedShape s) {
    super(s, s.getX(), s.getY());
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}