package me.qunwei.other.XMU2016;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner in = new Scanner(System.in);


        while(in.hasNext()){

/*			int a = in.nextInt();
			int b = in.nextInt();
			int m = in.nextInt();
			int n = in.nextInt();*/

/*			int a = Integer.valueOf(in.next());
			int b = Integer.valueOf(in.next());
			int m = Integer.valueOf(in.next());
			int n = Integer.valueOf(in.next());*/

            BigInteger a = BigInteger.valueOf(in.nextLong());
            BigInteger b = BigInteger.valueOf(in.nextLong());
            BigInteger m = BigInteger.valueOf(in.nextLong());
            BigInteger n = BigInteger.valueOf(in.nextLong());

            BigInteger m1 = getBig(a,b,m);
            BigInteger n1 = getBig(a,b,n);


            System.out.println(m1.gcd(n1));
        }


    }

    private static BigInteger getBig(BigInteger a, BigInteger b, BigInteger up) {
        // TODO Auto-generated method stub
        BigInteger a1 = a.modPow(up, BigInteger.valueOf(1000000007));
        BigInteger b1 = b.modPow(up, BigInteger.valueOf(1000000007));

        if ((a.compareTo(b)==-1)&&(a1.compareTo(b1)==1)){
            b1.add(BigInteger.valueOf(1000000007));
        }else if ((a.compareTo(b)==1)&&(a1.compareTo(b1)==-1)){
            a1.add(BigInteger.valueOf(1000000007));
        }


/*
		while(up.byteValue()!=1){

			a1 = a1.multiply(a);
			b1 = b1.multiply(b);

			up = up.add(BigInteger.valueOf(-1));

		}*/


        return (a1.add(b1.negate()));
    }

}