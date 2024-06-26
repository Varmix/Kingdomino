package kingdomino.domains.dummies;

import java.util.*;

import kingdomino.domains.Kingdom;
import kingdomino.domains.Player;

public class DummyPlayerFactory {

	public static Collection<Player> newPlayers(String...names) {
		var players = new ArrayList<Player>();
		for(var name : names) {
			players.add(newPlayer(name));
		}
		return players;
	}
	
	public static Player newPlayer(String name) {
		var p = new Player(name, "#aabbcc");
		p.setKingdom(Kingdom.ofCapacity(5));
		return p;
	}

}
