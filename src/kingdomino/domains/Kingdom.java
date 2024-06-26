package kingdomino.domains;

import java.util.*;

/**
 * Associe des tuiles à des coordonnées données par rapport au chateau.
 * Connait le nombre de couronnes associé à une coordonnée.
 * Connait le terrain associé à une coordonnée.
 * 
 * [Algo] Conditions à respecter pour qu'un royaume soit centré
 * Les conditions à respecter afin qu'un royaume soit centré selon la stratégie mise en place sont :
 * 1. Que la distance entre la colonne de gauche (se trouvant dans la BoudingBox) et la colonne du château soit équivalente à la distance entre la colonne de droite et la
 * colonne du château.
 * 2. Que la distance entre la ligne du dessus (se trouvant dans la BoudingBox) et la ligne du château soit équivalente à la distance 
 * entre la ligne du dessous (se trouvant dans la BoudingBox) et la ligne du château.
 * 
 * Dans mon cas, le terme distance se définit comme la différence entre les coordonnées du centre (coordonnées du château) et les coordonnées du bord de la BoundingBox.
 * Afin de calculer cette distance, la BoudingBox fournira toujours les coordonnées se trouvant aux extrêmités vu qu'elle s'adapte constamment
 * en fonction des tuiles posées.
 * Lorsque je stipule "les coordonnées du centre", une seule cordonnée sera utilisée à la fois en fonction des deux cas présentés ci-dessus. 
 * Je travaillerai avec la coordonnée de la colonne du château afin de vérifier l'égalité gauche/droite. Complémentairement, la coordonnée de la ligne du château interviendra dans l'égalité haut/bas. 
 * 
 * [Algo] Choix de collections pour l'algorithme
 * J'ai choisi la collection Set pour représenter les cases jouables pour un domino. Cette dernière n'autorise pas les doublons, ce qui est propice à la situation
 * car les coordonnées sont toujours uniques, il ne risque pas d'y avoir de doublons. Je n'ai pas pris la collection SortedSet car les coordonnées
 * n'ont pas besoin d'être maintenues triées (peu importe l'ordre des coordonnées, les cases jouables seront affichées). De plus, le SortedSet,
 * n'autorise que l'implémentation TreeSet qui ne m'intéresse pas dans ce cas (voir justification implémentation de la Collection). Je n'ai pas choisi 
 * une List car elle autorise des doublons, dans ce cas, elle ne sera pas nécessaire grâce à l'unicité des coordonnées. J'ai décidé 	
 * également de ne pas intégrer la collection Map car l'association clé-valeur ne serait pas utilisée  à son plein potentiel, vu que 
 * la valeur retenue sera toujours une tuile vide. Cela va justement être justifié par l'implémentation de la Collection.
 * 
 * Concernant l'implémentation de la Collection, j'ai décidé d'implémenter HashSet, qui est le même principe que pour la HashMap mais sans paire
 * clé/valeur, ici on a juste une clé, associée à une valeur nulle. Ce qui est intéressant dans le cas des cases jouables, la clé sera toujours
 * les coordonnées de la tuile de la case jouable. Lorsqu'on va itérer sur nos tuiles pour dessiner le royaume, si les coordonnées sont libres
 * la valeur de la coordonnée se verra affecter automatiquement une tuile vide via la méthode getTerrainAt(). De plus, dans la méthode permettant
 * de connaître les cases jouables, je vais ajouter ces cases dans le Set, cette méthode a une CTT constante O(1) ce qui est très efficace.
 * Contraitement, à l'implémention TreeSet où lorsqu'on souhaite ajouter l'élément, il faut remanier constamment l'arbre, ce qui amène à 
 * des complexités supérieures au HashSet. Également, TreeSet garantit un ordre qui n'est pas nécessaire dans ce cas, je n'ai pas 
 * besoin de maintenir des coordonnées triées pour les afficher. Tout ce qui m'intéresse est d'afficher les cases libres voisines, il n'y a pas 
 * d'ordre. 
 * 
 * 
 * 
 * 
 * */
public final class Kingdom implements Iterable<Coordinate>{
	private static final Coordinate CASTLE_COORD = Coordinate.ofRowCol(4, 4);
	public static final Kingdom EMPTY = new Kingdom(0);
	
	private Map<Coordinate, Tile> tiles;
	private BoundingBox box;
	private final int capacity;
	
	private Kingdom(int capacity) {		
		tiles = new HashMap<>();
		box = BoundingBox.ofCoord(CASTLE_COORD);
		this.capacity = capacity;
	}

	/**
	 * Retourne un nouveau royaume pouvant contenir jusque à {@code capacity} tuile en largeur et en hauteur.
	 * 
	 * @param capacity le nombre de tuiles que le royaume peut contenir en largeur et en hauteur. Ce nombre doit être positif et impair, sinon il est remplacé par 5.
	 * */
	public static Kingdom ofCapacity(int capacity) {
		int validCapacity = capacity < 1 || capacity%2==0 ? 5 : capacity;
		
		return new Kingdom(validCapacity);
	}

	public int getCapacity() {
		return capacity;
	}
	
	public Collection<Coordinate> getCoordinates() {
		var coords = new HashSet<>(this.tiles.keySet());
		coords.add(CASTLE_COORD);
		return coords;
	}

	public Tile getTileAt(Coordinate c) {
		if(c == null) {
			return Tile.empty;
		}
		
		if(c.getRow() == 4 && c.getCol() == 4) {
			return Tile.castle;
		} else if(tiles.containsKey(c)){
			return tiles.get(c);
		} else {
			return Tile.empty;
		}
	}
	
	public Terrain getTerrainAt(Coordinate coord) {
		return this.getTileAt(coord).getTerrain();
	}

	public int getCrownsCountAt(Coordinate coord) {
		return this.getTileAt(coord).getCrownsCount();
	}

	public List<Coordinate> getNeighbors(Coordinate c) {
		var candidates = c.getNeighbors();
		var filtered = new ArrayList<Coordinate>(candidates.size());
		for(var candidate : candidates) {
			if(getTerrainAt(candidate).isStandard()) {
				filtered.add(candidate);
			}
		}
		
		return filtered;
	}
	
	/**
	 * [Algo] CTT de la recherche des cases jouables :
	 * O(N + C*(L + L + P * L)) où :
	 * N équivaut au nombre de tuiles dans la Map
	 * C équivaut au nombre de tuiles situé dans le Set
	 * L équivaut au nombre de coordonnées (4 voisines)
	 * P équivaut au nombre de coordonnées se trouvant dans le Set
	 * 
	 * @param domino
	 * Le domino prêt à être posé
	 * @return
	 * Retourne les coordonnées  des cases jouables avec la domino courant en respectant la taille du royaume.
	 */
	public Set<Coordinate> playableTiles(Domino domino) {
		var tilesWithKingdom = new HashMap<>(tiles); // O(N)
		var coordinates = tilesWithKingdom.entrySet();
		Set<Coordinate> playableTiles = new HashSet<>();
		Set<Coordinate> playableTilesSortie = new HashSet<>();
		tilesWithKingdom.put(CASTLE_COORD, Tile.castle);
		for (var coordinate : coordinates) { // O(C)
			if((coordinate.getValue().getTerrain() == domino.getTerrain(0) || coordinate.getValue().getTerrain() == domino.getTerrain(1) || coordinate.getKey() == CASTLE_COORD)) {
				var neighborsCoord = coordinate.getKey().getNeighbors(); //O(L)
				for (var coordPlayable : neighborsCoord) { // O(L)
					if(keepSize(coordPlayable) && (!tilesWithKingdom.containsKey(coordPlayable))) {
						playableTiles.add(coordPlayable);
					}
				}
				for(var d : playableTiles) { // O(P)
					if(isValid(domino, d, d)) { // O(L) + O(L)
						playableTilesSortie.add(d);
					}
				}
			}
		}
		return playableTilesSortie;
	}
	
	private boolean keepSize(Coordinate tile1Pos) {
		return box.getHeightExtendedBy(tile1Pos) <= capacity &&
			   box.getHeightExtendedBy(tile1Pos) <= capacity;

	}


	/**
	 * Détermine si un domino peut être posé aux coordonnées renseignées.
	 * 
	 * Un domino peut être posé si :
	 * - les coordonées renseignées sont libres
	 * - qu'il existe au moins une tuile adajacente aux coordonnées renseignées qui soient compatibles
	 * - que la taille du royaume modifié ne dépasse pas sa capacité en largeur ou en hauteur.
	 * 
	 * @return true ssi les tuiles du {@code domino} peuvent être placées aux coordonnées {@code tile1Pos} et  {@code tile2Pos}. 
	 * */
	public boolean isValid(Domino domino, Coordinate tile1Pos, Coordinate tile2Pos) {
		if(anyNull(domino, tile1Pos, tile2Pos)) {
			return false;
		}
	
		return allPosFree(tile1Pos, tile2Pos) 
				&& (doesConnect(domino.getTile(0), tile1Pos) || doesConnect(domino.getTile(1), tile2Pos))
				&& keepSize(tile1Pos, tile2Pos);
	}

	private boolean anyNull(Object...objects) {
		for(var o : objects) {
			if( o == null) {
				return true;
			}
		}
		return false;
	}

	private boolean allPosFree(Coordinate pos1, Coordinate pos2) {
		return getTerrainAt(pos1) == Terrain.EMPTY && getTerrainAt(pos2) == Terrain.EMPTY;
	}

	private boolean keepSize(Coordinate tile1Pos, Coordinate tile2Pos) {
		return box.getHeightExtendedBy(tile1Pos) <= capacity &&
			   box.getHeightExtendedBy(tile2Pos) <= capacity &&
			   box.getWidthExtendedBy(tile1Pos) <= capacity &&
			   box.getWidthExtendedBy(tile2Pos) <= capacity;
	}

	private boolean doesConnect(Tile tile, Coordinate pos) {
		boolean connectionFound = false;
		for(var neighbour : pos.getNeighbors()) {
			connectionFound |= tile.isTerrainCompatibleWith(getTerrainAt(neighbour));
		}		
		return connectionFound;
	}


	/**
	 * Pose un sur ce royaume, après avoir validé la validité du placement.
	 * */
	public void setDominoAt(Domino domino, Coordinate tilePos1, Coordinate tilePos2) {
		if(isValid(domino, tilePos1, tilePos2)) {
			this.tiles.put(tilePos1, domino.getTile(0));
			this.tiles.put(tilePos2, domino.getTile(1));
			this.box.extendWith(tilePos1);
			this.box.extendWith(tilePos2);
		}
		
	}

	@Override
	public Iterator<Coordinate> iterator() {
		return getCoordinates().iterator();
	}
	
	/**
	 * Détermine si un château est centré
	 * @return
	 * true si le château est centré, sinon false
	 */
	public boolean kingdomIsCenter() {
		return (((CASTLE_COORD.getCol() - box.getLeft().getCol()) == (box.getRight().getCol() - CASTLE_COORD.getCol())) && ((CASTLE_COORD.getRow() - box.getTop().getRow()) == (box.getBottom().getRow() - CASTLE_COORD.getRow()))); 
	}
	

	/**
	 * Détermine si un royaume est complet en termes de tuile (aucune case vide)
	 * @return
	 * true si le royaume est complet sinon false
	 */
	public boolean kingdomIsFull() {
		boolean kingdomIsFull = false;
		if(capacity == 3 && tiles.size() == 8) {
			kingdomIsFull = true;
		} else if(capacity == 5 && tiles.size() == 24) {
			kingdomIsFull = true;
		}
		return kingdomIsFull;
	}
	
	
}
