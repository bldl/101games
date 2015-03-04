package inf101.games.life;

import inf101.games.IGame;
import inf101.games.life.brett.*;
import inf101.grid.IGrid2D;
import inf101.grid.MyGrid2D;
import inf101.grid.Torus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Life implements IGame {
	private IGrid2D<Boolean> brett;
	private int bredde;
	private int høyde;
	private static final String dead = "life/images/dead";
	private static final String alive = "life/images/alive";
	private final List<IPattern> patterns = new ArrayList<IPattern>();
	private final Random random = new Random();
	private String pattern = "Random";

	public Life(int x, int y){
		bredde = x;
		høyde = y;
		newGame();
		patterns.add(new Desert());
		patterns.add(new Toad());
		patterns.add(new Beacon());
		patterns.add(new QuadProp());
		patterns.add(new Glider());
		patterns.add(new LightweightSpaceship());
		patterns.add(new Pulsar());
		patterns.add(new Acorn());
	}

	@Override
	public void newGame() {
		if(pattern.equals("Random")) {
			brett = new MyGrid2D<Boolean>(new Torus(bredde, høyde));
			for(int x = 0; x < bredde; x++)
				for(int y = 0; y < høyde; y++)
					brett.set(x, y, random.nextInt(5) == 0);
		}
		else {
			for(IPattern p : patterns) {
				if(p.getName().equals(pattern)) {
					if(bredde < p.getWidth())
						bredde = p.getWidth();
					if(høyde < p.getHeight())
						høyde = p.getHeight();
					brett = new MyGrid2D<Boolean>(new Torus(bredde, høyde));

					for(int x = 0; x < bredde; x++)
						for(int y = 0; y < høyde; y++)
							brett.set(x, y, Boolean.FALSE);

					// finn midten
					int xOffset = (bredde - p.getWidth()) / 2;
					int yOffset = (høyde - p.getHeight()) / 2;

					// kopier mønsteret inn på brettet
					for(int x = 0; x < p.getWidth(); x++)
						for(int y = 0; y < p.getHeight(); y++)
							brett.set(xOffset + x, yOffset + y, p.isAlive(x, y));
					return;
				}
			}
		}
	}

	/**
	 * Metode for å beregne antall naboer til cellen med koord (x,y)
	 * 
	 * @param x
	 * 			x-koordinat på brett
	 * @param y
	 * 			y-koordinat på brett
	 * @return 
	 * 			antall naboer
	 */
	public int neighbours(int x, int y){
		int ant=0;
		if(brett.get(x-1, y))
			ant++;
		if(brett.get(x-1, y-1))
			ant++;
		if(brett.get(x-1, y+1))
			ant++;
		if(brett.get(x, y-1))
			ant++;
		if(brett.get(x, y+1))
			ant++;
		if(brett.get(x+1, y-1))
			ant++;
		if(brett.get(x+1, y))
			ant++;
		if(brett.get(x+1, y+1))
			ant++;
		return ant;
	}

	/**
	 * Metode for neste steg på brettet
	 * Kalles for hver oppdater
	 * 
	 * 	 */
	@Override
	public void timeStep(){
		// oppretter nyttBrett siden brett må brukes til sjekk i neighbours()

		IGrid2D<Boolean> nyttBrett = new MyGrid2D<Boolean>(new Torus(bredde, høyde));
		for(int m=0; m < bredde; m++){
			for(int n=0; n < høyde; n++){
				nyttBrett.set(m, n, false);
			}
		}
		for(int i=0; i < bredde; i++){
			for (int j=0; j < høyde; j++){
				int k=neighbours(i,j);
				if ((k==2 || k==3) && brett.get(i, j))
					nyttBrett.set(i, j, true);
				if (k==3 && !(brett.get(i, j)))
					nyttBrett.set(i, j, true);
			}
		}
		brett = nyttBrett;
	} 


	/**
	 * Kalles ved valg av celle (når du trykker på brettet)
	 * Setter boolean til false (død) dersom true fra før, og motsatt
	 * 
	 * @param x
	 * 			x-verdi på brett
	 * @param y
	 * 			y-verdi på brett
	 */
	@Override
	public void select(int x, int y){
		if (!(brett.get(x, y)))
			brett.set(x, y, true);
		else if(brett.get(x, y))
			brett.set(x, y, false);
	}


	@Override
	public void setSize(int nyBredde, int nyHøyde) {
		if(bredde == nyBredde && høyde == nyHøyde)
			return;

		IGrid2D<Boolean> nyttBrett = new MyGrid2D<Boolean>(new Torus(nyBredde, nyHøyde));
		for(int x = 0; x < nyBredde; x++)
			for(int y = 0; y < nyHøyde; y++)
				nyttBrett.set(x, y, Boolean.FALSE);

		for(int x = 0; x < Math.min(bredde, nyBredde); x++)
			for(int y = 0; y < Math.min(høyde, nyHøyde); y++)
				nyttBrett.set(x, y, brett.get(x, y));
		bredde = nyBredde;
		høyde = nyHøyde;
		brett = nyttBrett;
	}

	@Override
	public int getWidth() {
		return bredde;
	}

	@Override
	public int getHeight() {
		return høyde;
	}


	@Override
	public boolean hasStepButton() {
		return true;
	}

	@Override
	public boolean hasStartStopButtons() {
		return true;
	}

	@Override
	public String getName() {
		return "Game of Life";
	}

	@Override
	public String getIconAt(int x, int y) {
		if(brett.get(x, y))
			return alive;
		else
			return dead;
	}

	@Override
	public List<String> getBoardSizes() {
		return null;
	}

	@Override
	public boolean canChangeSize() {
		return true;
	}

	@Override
	public List<String> getMenuChoices() {
		List<String> result = new ArrayList<String>();
		result.add("Random");
		for(IPattern pattern : patterns)
			result.add(pattern.getName());
		return result;
	}

	@Override
	public void setMenuChoice(String s) {
		pattern = s;
		newGame();
	}
}
