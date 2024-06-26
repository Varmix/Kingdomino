package kingdomino.domains;

/**
 * Une tuile est un objet immuable composé d'un terrain et d'un nombre de couronnes.
 * */
public final class Tile {
	public static Tile empty = new Tile(Terrain.EMPTY, 0);
	public static Tile castle = new Tile(Terrain.CASTLE, 0);
	
	private final Terrain terrain;
	private final int crownsCount;

	/**
	 * Construit une nouvelle tuile de terrain {@code terrain} et comptant {@code crownsCount} couronnes.
	 * 
	 * @param terrain un terrain éventuellement {@code null}, le terrain dans ce cas sera {@code Terrain.EMPTY}.
	 * @param crownsCount un nombre de couronnes que le constructeur réduit à l'intervalle de valeurs {@code [0; 3]}.
	 * */
	public Tile(Terrain terrain, int crownsCount) {
		this.terrain = terrain == null ? Terrain.EMPTY : terrain ;
		this.crownsCount = Math.max(Math.min(crownsCount, 3), 0);
	}

	/**
	 * @return le territoire de cette tuile.
	 */
	public Terrain getTerrain() {
		return terrain;
	}

	/**
	 * @return le nombre de couronnes compris entre 0 et 3 inclus.
	 */
	public int getCrownsCount() {
		return crownsCount;
	}
	
	/**
	 * @return {@code true} ssi le terrain de cette tuile est le même que celui de {@code t} ou si un des terrains est de type {@code Terrain.CASTLE}.
	 * */
	public boolean isTerrainCompatibleWith(Terrain t) {
		return terrain.isCompatibleWith(t);
	}

	/**
	 * @return {@code true} ssi le terrain de cette tuile n'est ni {@code Terrain.CASTLE}, ni {@code Terrain.EMPTY}.
	 * */
	public boolean isTerrainStandard() {
		return terrain.isStandard();
	}
	
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		
		if(!(o instanceof Tile)) {
			return false;
		}
		
		Tile that = (Tile)o;
		return this.terrain == that.terrain && this.crownsCount == that.crownsCount;
	}

	public String getTerrainAsString() {
		return terrain.toString();
	}
}
