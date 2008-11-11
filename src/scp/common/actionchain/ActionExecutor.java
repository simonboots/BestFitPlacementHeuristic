package scp.common.actionchain;

import java.util.ArrayList;
import java.util.List;

public class ActionExecutor {

	protected List<IAction> doQueue = null;
	protected List<IAction> undoQueue = null;
	protected IShapeMagazine magazine = null;
	protected IShapePlacer placer = null;
	
	public ActionExecutor(List<IAction> doQueue, IShapeMagazine magazine, IShapePlacer placer) {
		this.doQueue = doQueue;
		this.undoQueue = new ArrayList<IAction>();
		this.magazine = magazine;
		this.placer = placer;
	}
	
	public void executeNextAction() {
		if (hasNextAction()) {
			IAction nextAction = doQueue.get(0);
			nextAction.execute(magazine, placer);
			undoQueue.add(nextAction.getReverseAction());
			doQueue.remove(nextAction);
		}
	}
	
	public void executePreviousAction() {
		if (hasPreviousAction()) {
			IAction previousAction = undoQueue.get(undoQueue.size() -1);
			previousAction.execute(magazine, placer);
			doQueue.add(0, previousAction.getReverseAction());
			undoQueue.remove(previousAction);
		}
	}
	
	public boolean hasNextAction() {
		return numOfActionsLeft() > 0;
	}
	
	public boolean hasPreviousAction() {
		return numOfActionsExecuted() > 0;
	}
	
	public int numOfTotalActions() {
		return numOfActionsLeft() + numOfActionsExecuted();
	}
	
	public int numOfActionsLeft() {
		return doQueue.size();
	}
	
	public int numOfActionsExecuted() {
		return undoQueue.size();
	}
}
