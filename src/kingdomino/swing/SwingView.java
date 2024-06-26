package kingdomino.swing;

import java.util.function.Consumer;

import javax.swing.JPanel;

import kingdomino.supervisers.View;


/**
 * Classe de base des écrans.
 * Tout écran possède un nom l'identifiant.
 * */
public abstract class SwingView extends JPanel implements View {
	private static final long serialVersionUID = 225420598267162189L;
	protected Consumer<String> router;

	/**
	 * Initialise l'écran avec un titre.
	 * Les dimensions de l'écran sont tirées du thème.
	 * 
	 * @param name le titre de l'écran.
	 * */
	public SwingView(String name) {
		super(null);
		
		setBackground(Theme.PRIMARY_BACKGROUND_COLOR);
		setSize(Theme.PANEL_WIDTH, Theme.PANEL_HEIGHT);
		
		this.setName(name);
	}

	/**
	 * Méthode appelée par la fenêtre principale avant que cet écran ne devienne l'écran courant.
	 * */
	@Override
	public void onEnter(String fromScreen) {}
	
	/**
	 * Méthode appelée par la fenêtre principale avant que cet écran ne soit plus l'écran courant.
	 * */
	@Override
	public void onLeave(String toScreen) {}

	@Override
	public void onQuitConfirmed(String fromScreen) {
		System.exit(0);
	}
	
	/**
	 * Méthode appelée quand l'utilisateur appuie sur une touche.
	 * @param keyCode le code virtuel correspondant à la touche.
	 * */
	public void onKeyTyped(int keyCode) {
	}

	protected void setRouter(Consumer<String> router) {
		this.router = router;
	}

	@Override
	public void goTo(String screenName) {
		this.router.accept(screenName);		
	}
}
