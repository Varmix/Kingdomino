package kingdomino.domains.dummies;

import kingdomino.domains.*;

public class DummyDominoFactory {
	public static Domino createDomino(int id) {
		return new Domino(id, new Tile(Terrain.FIELD,0), new Tile(Terrain.FIELD,0));
	}
}
