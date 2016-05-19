package me.qunwei.other;

import me.qunwei.other.Q118;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by qunwei on 16-4-16.
 */
public class Q118Test {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testMethod() throws Exception {
		// System.out.println(Q118.method(new int[]{1,2,3,4,5,6,7,8,9,10}));

        new Q118();

		ArrayList<ArrayList<Integer>> result = Q118.method(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

		for (ArrayList<Integer> temp : result) {
			System.out.println(temp);
		}

	}

	@Test
	public void testParse() throws Exception {
		String b = "123.45";
		String a = "12.23";

		try {
			System.out.println("a:" + Double.parseDouble(a));
			System.out.println("b:" + Double.parseDouble(b));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}