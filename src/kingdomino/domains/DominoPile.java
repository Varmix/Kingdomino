package kingdomino.domains;

import java.util.*;

/**
 * Collectionne des dominos sous la forme d'une file.
 * Mélange des dominos.
 * Ecarte des dominos.
 * Extrait des sous-listes de dominos.
 * 
 * [ALGO] Type de collection
 * On doit pouvoir mélanger la pile, écarter un certains nombres de
 * dominos, extraire des sous-listes pour créer les lignes de tirage,
 * nous avons le choix entre : 
 *  - Une liste : on utilisera la méthode Collections.shuffle(List) pour le mélange, et subList() pour extraire et écarter.
 *  - Une Deque en stratégie LIFO si on mélange une liste de dominos via Collections.shuffle(), puis qu'on utilise cette liste mélangée pour  construire la Deque. 
 * 
 * Pour ces deux derniers cas, on utilisera alors pollFirst() pour extraire des  dominos un par un, et pour en écarter au début du jeu. 
 * 
 * [ALGO] Implémentation de collection
 * Si la List a été choisie, l'ArrayList est le meilleur choix car
 *  - collection de petite taille (max 48 dominos)
 *  - accès direct à un indice est de CTT constante.
 * Néanmoins, shuffle() est de CTT linéaire tant pour l'ArrayList que la LinkedList 
 * (voir https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Collections.html#shuffle(java.util.List))
 * car si l'accès direct n'est pas efficace, la liste est recopiée dans un tableau.
 * 
 * Si la Deque en stratégie LIFO est choisie, la LinkedList est le choix le plus
 * fréquent car enlever l'élément via pollFirst() sera de CTT constante.
 * L'ArrayDeque gère un tableau en liste circulaire donc pollFirst est aussi
 * de CTT constante et la taille du tableau peut être fixée à la construction, 
 * mais ce serait beaucoup demander aux étudiants d'expliquer cela (pas vu au cours)
 * Il faudra néanmoins d'abord mélanger les éléments d'une liste (une seule 
 * fois, à la construction), ce qui est de CTT O(N), voir ci-dessus. 
 * */
public class DominoPile {
	private final List<Domino> dominoes;
	private int threshold;
	private int pos;
	
	public DominoPile(Collection<Domino> dominoes, int threshold) {
		this.dominoes = new ArrayList<>(dominoes);
		Collections.shuffle(this.dominoes);
		this.threshold = Math.min(dominoes.size(), threshold);
	}
	
	public Collection<Domino> next(int count) {
		if(!hasNext()) {
			return List.of();
		}
		
		int finalCount = Math.min(count, threshold - pos);
		var result = dominoes.subList(pos, pos + finalCount);
		pos += finalCount;
		
		return result;
	}
	
	private boolean hasNext() {
		return pos < this.threshold;
	}
}
