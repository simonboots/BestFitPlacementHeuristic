/**
 *     Copyright (C) 2008 Benjamin Clauss, Simon Stiefel 
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package scp.common.actionchain;

import java.util.ArrayList;
import java.util.List;

import scp.common.actionchain.actions.UnhighlightGapAction;

/**
 * Executes Actions on ShapeMagazine and ShapePlacer
 * 
 * @author Simon Stiefel
 * 
 */
public class ActionExecutor {

	protected List<IAction> doQueue = null;
	protected List<IAction> undoQueue = null;
	protected IShapeMagazine magazine = null;
	protected IShapePlacer placer = null;

	/**
	 * @param doQueue queue with action to be executed
	 * @param magazine shape magazine with unplaced shapes
	 * @param placer shape placer with methods to place shape on stock roll
	 */
	public ActionExecutor(List<IAction> doQueue, IShapeMagazine magazine, IShapePlacer placer) {
		this.doQueue = doQueue;
		this.undoQueue = new ArrayList<IAction>();
		this.magazine = magazine;
		this.placer = placer;
	}

	/**
	 * Executes next action in doQueue
	 */
	public void executeNextAction() {
		if (hasNextAction()) {
			IAction nextAction = doQueue.get(0);
			nextAction.execute(magazine, placer);

			IAction undoAction = nextAction.getReverseAction();
			if (undoAction != null) {
				undoQueue.add(undoAction);
			}

			doQueue.remove(nextAction);
		}
	}

	/**
	 * Executes previous action in undoQueue
	 */
	public void executePreviousAction() {
		while (hasPreviousAction()) {
			IAction previousAction = undoQueue.get(undoQueue.size() - 1);
			if (!(previousAction instanceof UnhighlightGapAction)) {
				previousAction.execute(magazine, placer);
			}

			IAction doAction = previousAction.getReverseAction();
			if (doAction != null) {
				doQueue.add(0, doAction);
			}

			undoQueue.remove(previousAction);
			
			if (!(previousAction instanceof UnhighlightGapAction)) {
				break;
			}
		}
	}

	/**
	 * Checks if executor has more next actions
	 * @return has more next actions
	 */
	public boolean hasNextAction() {
		return numOfActionsLeft() > 0;
	}

	/**
	 * Checks if executor has more previous actions
	 * @return has more previous actions
	 */
	public boolean hasPreviousAction() {
		return numOfActionsExecuted() > 0;
	}

	/**
	 * @return amount of total actions
	 */
	public int numOfTotalActions() {
		return numOfActionsLeft() + numOfActionsExecuted();
	}

	/**
	 * @return amount of actions to be executed
	 */
	public int numOfActionsLeft() {
		return doQueue.size();
	}

	/**
	 * @return amount of actions already executed
	 */
	public int numOfActionsExecuted() {
		return undoQueue.size();
	}
}
