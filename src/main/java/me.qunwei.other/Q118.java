package me.qunwei.other;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by qunwei on 16-4-16.
 */
public class Q118 {

	public static ArrayList<ArrayList<Integer>> method(int[] origin) {

		Arrays.sort(origin);

		ArrayList<ArrayList<Integer>> resSet = new ArrayList<ArrayList<Integer>>();

		resSet.add(new ArrayList<Integer>());

        for (int i = 0; i < origin.length; i++) {
            ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
            for (ArrayList<Integer> list:resSet){
                temp.add(list);
                ArrayList<Integer> clone = new ArrayList<Integer>(list);
                clone.add(origin[i]);
                temp.add(clone);
            }
            resSet = temp;
        }

		return resSet;
	}

}
