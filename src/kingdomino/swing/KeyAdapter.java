package kingdomino.swing;

import java.awt.event.KeyEvent;

class KeyAdapter extends java.awt.event.KeyAdapter {
	private final KeyTypedEventHandler handler;

	public KeyAdapter(KeyTypedEventHandler handler) {
		this.handler = handler;
	}
	
	public void keyReleased(KeyEvent evt) {
		this.handler.onKeyTyped(evt.getExtendedKeyCode());
	}

}

@FunctionalInterface
interface KeyTypedEventHandler {
	void onKeyTyped(int keyCode);
}