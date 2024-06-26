package kingdomino.domains;

import java.util.*;

/**
 * Maintient et met à jour l'état d'une partie.
 * */
public final class KingDominoGame {
	private final List<Player> players;
	private final DominoPile pile;
	private DrawLine actual;
	private DrawLine next;
	private Domino nextDomino;
	
	public KingDominoGame(Collection<Player> players, DominoPile pile) {		
		this.players = List.copyOf(players);				
		this.pile = pile;
		this.actual = DrawLine.EMPTY;
		this.next = DrawLine.EMPTY;
		this.nextDomino = Domino.UNKNOWN;
	}
	
	private DrawLine newDrawLine() {
		var drawCount = players.size() % 2 == 0 ? 4 : 3;
		return new DrawLine(pile.next(drawCount));
	}	
	
	public void start() {
		this.actual = newDrawLine();
		this.actual.affectRandomly(players);
		this.next = newDrawLine();
		this.nextDomino = next.getTopDomino();
	}
	

	public DrawLine getCurrentLine() {
		return this.actual;
	}
	
	public DrawLine getNextLine() {
		return this.next;
	}

	public Domino getCurrentDomino() {
		return actual.getTopDomino();
	}

	public Collection<Player> getPlayers() {
		return List.copyOf(this.players);
	}
	
	public Player getCurrentPlayer() {
		return actual.getTopPlayer();
	}
	
	public boolean isNextDomino(Domino d) {
		return this.nextDomino.equals(d);
	}
	
	
	public KingDominoActionResult setCurrentDominoAt(Coordinate tilePos1, Coordinate tilePos2) {
		boolean dominoSet = getCurrentPlayer().setDominoAt(getCurrentDomino(), tilePos1, tilePos2);
		if(dominoSet) {
			return decideNextState();
		} else {
			return KingDominoActionResult.INVALID;
		}
	}

	public KingDominoActionResult skip() {
		return decideNextState();
	}

	private KingDominoActionResult decideNextState() {
		if(hasDominoToSelect()) {
			return KingDominoActionResult.SELECT_NEXT_DOMINO;
		} else if(moveToNextPlayer()) {
			return KingDominoActionResult.NEXT_PLAYER;
		} else {
			return KingDominoActionResult.GAME_OVER;
		}
	}
	
	private boolean hasDominoToSelect() {
		return next.getFreeDominosCount() > 0;
	}
	
	public void moveToNextSelectableDomino() {
		this.nextDomino = this.next.getNextFreeFrom(nextDomino);
	}
	
	private boolean hasNextPlayer() {
		return this.actual.getCount() > 1 || this.next.getCount() > 0;
	}

	public void setNextDomino() {
		this.next.affectDominoToPlayer(nextDomino, getCurrentPlayer());
		this.moveToNextPlayer();
	}

	private boolean moveToNextPlayer() {
		boolean canMove = this.hasNextPlayer();
		if(canMove) {
			this.actual.removeFirst();
			this.updateLines();
		}
		return canMove;
	}
	
	private void updateLines() {
		if(actual.getCount() == 0) {
			actual = next;
			next = newDrawLine();
			nextDomino = next.getTopDomino();
		} else {
			nextDomino = next.getNextFreeFrom(nextDomino);
		}
	}

}
