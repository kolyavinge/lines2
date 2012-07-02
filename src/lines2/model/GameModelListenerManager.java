package lines2.model;

import java.util.ArrayList;
import java.util.Collection;

class GameModelListenerManager {

	private Collection<GameModelListener> listeners;

	public GameModelListenerManager() {
		listeners = new ArrayList<GameModelListener>();
	}

	public void addListener(GameModelListener listener) {
		listeners.add(listener);
	}

	public void removeListener(GameModelListener listener) {
		listeners.remove(listener);
	}

	protected void raiseOnLevelChanged() {
		for (GameModelListener listener : listeners)
			listener.onLevelChanged();
	}

	protected void raiseOnGameOver() {
		for (GameModelListener listener : listeners)
			listener.onGameOver();
	}

	protected void raiseOnGameComplete() {
		for (GameModelListener listener : listeners)
			listener.onGameComplete();
	}
}
