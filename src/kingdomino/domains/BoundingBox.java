package kingdomino.domains;

/**
 * Calcule la hauteur et la longueur d'une boite qu'on étend à l'aide de tuiles.
 * */
public class BoundingBox {
	private Coordinate left;
	private Coordinate top;
	private Coordinate right;
	private Coordinate bottom;
	
	/**
	 * Crée et retourne une boite d'origine {@code origin}.
	 * 
	 * Si {@code origin == null}, retourne une boite d'origine (0,0).
	 * */
	public static BoundingBox ofCoord(Coordinate origin) {
		if(origin == null) {
			return new BoundingBox(Coordinate.ofRowCol(0, 0));
		} else {
			return new BoundingBox(origin);
		}
	}
	
	private BoundingBox(Coordinate origin) {
		top = left = right = bottom = origin;
	}
	
	/**
	 * Retourne la largeur de cette boite qui correspond au nombre de colonnes.
	 * */
	public int getWidth() {
		return right.getCol() - left.getCol() + 1;
	}

	/**
	 * Retourne la hauteur de cette boite qui correspond au nombre de lignes.
	 * */
	public int getHeight() {
		return bottom.getRow() - top.getRow() + 1;
	}
	
	/**
	 * Retourne la largeur de cette boite si elle était contenait la coordonnée {@code c}.
	 * */
	public int getWidthExtendedBy(Coordinate c) {
		int extendedLeft = Math.min(c.getCol(), left.getCol());
		int extendedRight = Math.max(c.getCol(), right.getCol());
		
		return extendedRight - extendedLeft + 1;
	}
	
	/**
	 * Retourne la hauteur de cette boite si elle était contenait la coordonnée {@code c}.
	 * */
	public int getHeightExtendedBy(Coordinate c) {
		int extendedTop = Math.min(c.getRow(), top.getRow());
		int extendedBottom = Math.max(c.getRow(), bottom.getRow());
		
		return extendedBottom - extendedTop + 1;	
	}
	
	/**
	 * Étend cette boit en incluant la coordonnée {@code c}.
	 * */
	public void extendWith(Coordinate c) {
		if(c.getCol() < left.getCol()) {
			left = c;
		}
		if(c.getCol() > right.getCol()) {
			right = c;
		}
		if(c.getRow() < top.getRow()) {
			top = c;
		}
		if(c.getRow() > bottom.getRow()) {
			bottom =c;
		}		
	}
	/**
	 * 
	 * @return
	 * La coordonnée gauche de la BoudingBox
	 */
	public Coordinate getLeft() {
		return left;
	}
	
	/**
	 * 
	 * @return
	 * La coordonnée supérieure de la BoudingBox
	 */
	public Coordinate getTop() {
		return top;
	}

	/**
	 * 
	 * @return
	 * La coordonnée droite de la BoudingBox
	 */
	public Coordinate getRight() {
		return right;
	}
	
	/**
	 * 
	 * @return
	 * La coordonnée inférieure de la BoundingBox
	 */
	public Coordinate getBottom() {
		return bottom;
	}
	



}
