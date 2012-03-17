package inf101.games;

	
	public class Tabell2D<E> implements ITabell2D<E> {
		private E[][] data;
		private int antX;
		private int antY;
		
		/**
		 * 
		 * @param antx
		 * @param anty
		 */
		@SuppressWarnings("unchecked")
		public Tabell2D(int antx, int anty){
		data = (E[][]) new Object[antx][anty];
		antX=antx;
		antY=anty;
		}
		
		@Override
		public E hent(int x, int y) {
			if (x<0 )
				x=antX-1;
			if (y<0)
				y=antY-1;
			if (x==this.antX)
				x=0;
			if (y==this.antY)
				y=0;
			return  data[x][y];
		}
	
		@Override
		public void sett(int x, int y, E e) {
			if (x<0 || y<0 || x>this.antX || y>this.antY)
				throw new NullPointerException("Feil posisjon: " + x + ", " + y);
			data[x][y]=e;
		}
	
		@Override
		public int høyde() {
			return antY;
		}
	
		@Override
		public int bredde() {
			return antX;
		}
		
		public int antall(){
			return antY*antX;
		}
		
		@Override
		public ITabell2D<E> clone(){
		 Tabell2D<E> dataklone = new Tabell2D<E>(this.antX, this.antY);
		 for (int x=0; x<this.antX; x++){
			 for (int y=0; y<this.antY; y++){
				 dataklone.sett(x, y, this.hent(x, y));
			 }
		 }
			return dataklone;
		}
	}
