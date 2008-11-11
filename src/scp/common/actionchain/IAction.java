package scp.common.actionchain;

public interface IAction {
	
	public void execute(IShapeMagazine magazine, IShapePlacer placer);
	public IAction getReverseAction();
}
