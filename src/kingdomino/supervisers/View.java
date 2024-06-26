package kingdomino.supervisers;

public interface View {
	/**
	 * Retourne le nom de cet écran.
	 * */
	String getName();
	
	/**
	 * Méthode appelée par un superviseur pour demander de naviguer de cet écran vers l'ecran {@code screenName}.
	 * */
	void goTo(String screenName);
	
	/**
	 * Méthode appelée par la fenêtre principale avant que cet écran ne devienne l'écran courant.
	 * */
	void onEnter(String fromScreen);
	
	/**
	 * Méthode appelée par la fenêtre principale avant que cet écran ne soit plus l'écran courant.
	 * */
	void onLeave(String toScreen);
	
	/**
	 * Méthode appelée pour confirmer une demande de quitter
	 * */
	void onQuitConfirmed(String fromScreen);
}
