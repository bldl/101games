package inf101.tests;
import static org.junit.Assert.*;

import java.util.List;

import inf101.util.IGenerator;
import inf101.util.generators.StringGenerator;

import org.junit.Test;

public class StringTest {
	private final IGenerator<String> strGen = new StringGenerator();
	private final int N = 100000;
	@Test
	public void stringTest1() {
		assertEquals("foo", "FOO".toLowerCase());
	}
	
	@Test
	public void stringTest2() {
		for(int i = 0; i < N; i++) {
			String s = strGen.generate();
			
			assertEquals(s + s, s.concat(s));
		}
			
	}
	
	public void transitiveProperty(String s1, String s2, String s3) {
		if(s1.equals(s2) && s2.equals(s3))
			assertEquals(s1, s3);
	}
	
	public void reflexiveProperty(String s) {
			assertEquals(s, s);
	}
	public void symmetricProperty(String s1, String s2) {
		if(s1.equals(s2) )
			assertEquals(s2, s1);
	}
	public void hashCodeProperty(String s1, String s2) {
		if(s1.equals(s2) )
			assertEquals(s1.hashCode(), s2.hashCode());
	}

	@Test
	public void transitiveTest1() {
		for(int i = 0; i < N; i++) {
			String s1 = strGen.generate();
			String s2 = strGen.generate();
			String s3 = strGen.generate();
			
			transitiveProperty(s1, s2, s3);
		}
	}
	
	@Test
	public void transitiveTest2() {
		for(int i = 0; i < N; i++) {
			List<String> ss = strGen.generateEquals(3);
			
			transitiveProperty(ss.get(0), ss.get(1), ss.get(2));
		}
	}
	
	@Test
	public void reflexiveTest() {
		for(int i = 0; i < N; i++) {
			String s = strGen.generate();
			
			reflexiveProperty(s);
		}
	}
	
	
	@Test
	public void symmetricTest1() {
		for(int i = 0; i < N; i++) {
			String s1 = strGen.generate();
			String s2 = strGen.generate();
			
			symmetricProperty(s1, s2);
		}
	}
	
	@Test
	public void symmetricTest2() {
		for(int i = 0; i < N; i++) {
			List<String> ss = strGen.generateEquals(2);
			
			symmetricProperty(ss.get(0), ss.get(1));
		}
	}

	@Test
	public void hashCodeTest1() {
		for(int i = 0; i < N; i++) {
			String s1 = strGen.generate();
			String s2 = strGen.generate();
			
			hashCodeProperty(s1, s2);
		}
	}
	
	@Test
	public void hashCodeTest2() {
		for(int i = 0; i < N; i++) {
			List<String> ss = strGen.generateEquals(2);
			
			hashCodeProperty(ss.get(0), ss.get(1));
		}
	}

}

