package scp.common.actionchain;

import java.util.ArrayList;
import java.util.List;
import scp.common.PlacedShape;
import scp.common.Shape;
import scp.common.actionchain.actions.OptimizeShapeAction;
import scp.common.actionchain.actions.PlaceShapeAction;
import scp.common.actionchain.actions.ShowShapeListAction;
import scp.common.actionchain.actions.ShowSortedShapeListAction;
import scp.gui.ColoredPlacedShape;

public class ActionGenerator {

  private List<Shape> shapelist = null;
  private List<Shape> sortedlist = null;
  private List<PlacedShape> placedlist = null;
  private List<PlacedShape> optimizedlist = null;
  private List<IAction> doQueue = null;

  public ActionGenerator(List<Shape> shapelist, List<Shape> sortedlist, List<PlacedShape> placedlist, List<PlacedShape> optimizedlist) {
    this.shapelist = shapelist;
    this.sortedlist = sortedlist;
    this.placedlist = placedlist;
    this.optimizedlist = optimizedlist;
  }

  public List<IAction> getDoQueue() {
    doQueue = new ArrayList<IAction>();
    // first step: load unorderd shapes
    doQueue.add(new ShowShapeListAction(shapelist));
    // second step: sort shapes by size
    doQueue.add(new ShowSortedShapeListAction(sortedlist));
    // add all shapes to the doQueue
    for (PlacedShape s : placedlist) {
      doQueue.add(new PlaceShapeAction(s));
    }
    // add optimized shapes
    for (PlacedShape s : optimizedlist) {
      doQueue.add(new OptimizeShapeAction(s));
    }
    return doQueue;
  }
}