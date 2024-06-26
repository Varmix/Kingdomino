package kingdomino.io;

public class PlayerDTO {
	private final String name;
	private final String hexColor;
	
	public PlayerDTO(String name, String hexColor) {
		this.name = name;
		this.hexColor = hexColor;
	}


	public String getHexColor() {
		return hexColor;
	}

	public String getName() {
		return name;
	}
}
