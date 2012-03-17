package inf101.games;

import java.util.Arrays;

import inf101.games.gui.GUI;
import inf101.games.life.Life;

public class Main {
	public static void main(String[] args) {
		IGame life = new Life(15, 17);
		GUI games = new GUI(Arrays.asList(life)); 
	}

}
