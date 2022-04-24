package lambda;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class SomeCalculations {
  
	  public static void main(String[] args) {
//		System.out.println(sum1(20));
//		System.out.println(sum2(20));
//		System.out.println(sum4(7,13));
//		System.out.println(sum5());
//		System.out.println(sum5());
//		System.out.println(sum6());
//		System.out.println(powerOfThree(4));
//		System.out.println(anyPowerOfAnyNumber(2,7));
		System.out.println(anyPow(-6,2));
//		System.out.println(factorial1(12)); // if u create by int u can calculate 12! at most
//		System.out.println(factorial2(50));
	  
	  }
  
  //create a method to find sum of integers from 1 to x by using structured programming
  public static int sum1(int x) {
		int sum = 0;
		for (int i=1; i<=x; i++) {
		   sum += i;	   
		}
		return sum;
	}
//create a method to find sum of integers from 1 to x by using functional programming
  //1.way
  public static int sum2 (int x) {
	return  IntStream.range(1,x+1).sum();

  }
  //2.way
  public static int sum3 (int x) {
		return  IntStream.rangeClosed(1,x).sum();
  }
//create a method to find sum of even integers from int x to  int y by using functional programming
  
 public static int sum4(int x ,int y) {
	return IntStream.rangeClosed(x,y).filter(t->t%2==0).sum();
   }
//create a method to find sum of first 50 odd positive integers by using functional programming
 
 public static int sum5() {
	return IntStream.rangeClosed(1,99).filter(t->t%2==1).sum();
 }
 public static int sum6() {
	return IntStream.iterate(1,t->t+2).limit(50).sum(); 
 }
 //create a method to find the any power of 3 by using functional programming
 
 public static OptionalInt powerOfThree(int numOfPow) {
	return IntStream.iterate(3,t->t*3).limit(numOfPow).reduce((x,y)->y);
 }
 
   //  create a method to find the any power of any number by using functional programming
 public static OptionalInt anyPowerOfAnyNumber(int pow, int num) {
	 return IntStream.iterate(num, t -> t*num).limit(pow).reduce((a,b)->b);
 }
 //  create a method to find the any power(- or +) of any number by using functional programming
 
  public static Double anyPow(int pow, int num) {
	 int result = IntStream.iterate(num, t->t*num)
			 .limit(Math.abs(pow))
			 .reduce(Integer.MIN_VALUE,(a,b)->a>b?a:b);
	 return 1.0/result;
 }
 //create a method to calculate the factorial of any number
 //1st way
 public static OptionalInt factorial1 (int x) {
	return IntStream.rangeClosed(1,x).reduce(Math::multiplyExact);
 }
 //2. way works for all numbers
 
 public static BigInteger factorial2(int x) {
	return IntStream.rangeClosed(1,x).mapToObj(BigInteger::valueOf)
	.reduce(BigInteger.ONE,BigInteger::multiply) ;
 }








































} 


