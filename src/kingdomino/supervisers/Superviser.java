package kingdomino.supervisers;

/**
 * Classe héritée par tous les superviseurs pour réagir aux événements de navigation.
 * */
public abstract class Superviser {
	/**
	 * Exécute des instructions avant que la fenêtre principale ne passe de la vue {@code fromView} à la vue
	 * de ce superviseur.
	 * 
	 * @param fromView le nom de la vue que l'application a quitté.
	 * */
	public void onEnter(String fromView) {
		
	}
	
	/**
	 * Exécute des instructions avant que la fenêtre principale ne quitte ce superviseur pour afficher la vue {@code toView}.
	 * 
	 * @param toView le nom de la vue que l'application va afficher.
	 * */
	public void onLeave(String toView) {
		
	}
}
