package scp.common.actionchain;

import java.util.List;
import scp.common.*;

public interface IShapeMagazine {
	
	public void loadMagazine(List<Shape> shapelist);
	public void highlightShape(Shape s);
	public void unhighlightAllShapes();
	public void removeShape(Shape s);
	public void insertShape(Shape s);
}
