package inf101.util;

import inf101.util.generators.IntGenerator;
import inf101.util.generators.StringGenerator;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
		Random r = new Random();
		IGenerator<String> strGen = new StringGenerator(5,15);
		System.out.println("Tilfeldig streng: " + strGen.generate(r));
		IGenerator<Integer> intGen = new IntGenerator(0, 1000);
		System.out.println("Tilfeldig heltall mellom 0 og 1000: " + intGen.generate(r));
		
		long sum = 0;
		for(int i = 0; i < 10000000; i++)
			sum += intGen.generate();
		System.out.println("average: " + sum/10000000);
	}

}
