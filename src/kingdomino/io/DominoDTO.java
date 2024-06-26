package kingdomino.io;

public class DominoDTO {
	private final String terrain1;
	private final String terrain2;
	private final int crownsCount1;
	private final int crownsCount2;
	private final int id;
	
	public DominoDTO(int id, String terrain1, int crownsCount1, String terrain2, int crownsCount2) {
		this.id = id;
		this.terrain1 = terrain1;
		this.crownsCount1 = crownsCount1;
		this.terrain2 = terrain2;
		this.crownsCount2 = crownsCount2;
	}
	
	public String getTerrain1() {
		return terrain1;
	}
	
	public String getTerrain2() {
		return terrain2;
	}
	
	public int getCrownsCount1() {
		return crownsCount1;
	}
	
	public int getCrownsCount2() {
		return crownsCount2;
	}
	
	public int getId() {
		return id;
	}
}
