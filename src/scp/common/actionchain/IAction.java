package scp.common.actionchain;

/**
 * Interface for Action
 * @author Simon Stiefel
 *
 */
public interface IAction {
	
	/**
	 * Execute action
	 * @param magazine
	 * @param placer
	 */
	public void execute(IShapeMagazine magazine, IShapePlacer placer);
	
	/**
	 * @return reverse action (undo action)
	 */
	public IAction getReverseAction();
}
