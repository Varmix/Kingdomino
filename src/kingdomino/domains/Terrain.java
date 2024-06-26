package kingdomino.domains;

/**
 * Un terrain est le type de surface qui peut recouvrir une tuile.
 * */
public enum Terrain {
	EMPTY, CASTLE, FIELD, GRASSLANDS, FOREST, MINE, LAKE, SWAMP;
	
	public boolean isStandard() {
		return this != EMPTY && this != CASTLE;
	}
	
	
	public boolean isCompatibleWith(Terrain t) {
		return this == t || Terrain.CASTLE == this || Terrain.CASTLE == t;
	}
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
