package lambda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Lambda10 {
public static void main(String[] args) throws IOException{
	//read the text from LambdaFile01
	Files.lines(Paths.get("src/lambda/Lambda01.txt"))
	.forEach(System.out::println);
	
	System.out.println("=======================");
	
   

   //read the text from Lambda01 in upper cases
   Files.lines(Paths.get("src/lambda/Lambda01.txt"))
                           .map(String::toUpperCase)//to update the data we use map method
                           .forEach(System.out::println);

   System.out.println("=======================");
   //read the just first row from lambda in lower cases
  //1st way
   Files.lines(Paths.get("src/lambda/Lambda01.txt")).limit(1)
   .map(String::toLowerCase).forEach(System.out::println);
   //2nd way
   System.out.println(Files.lines(Paths.get("src/lambda/Lambda01.txt"))
		   .map(String::toLowerCase).findFirst());
   
   System.out.println("=======================");
   //read just the 3rd row in uppercases
   //1. way
   Files.lines(Paths.get("src/lambda/Lambda01.txt")).skip(2).limit(1)
   .map(String::toUpperCase).forEach(System.out::println);
   //2. way
   System.out.println(Files.lines(Paths.get("src/lambda/Lambda01.txt")).skip(2)
   .map(String::toUpperCase).findFirst());
   
   //find the number of lines in which word lambda is used ignorecase
   
  System.out.println( Files.lines(Paths.get("src/lambda/Lambda01.txt"))
   .filter(t->t.toLowerCase().contains("lambda")).count());

   //print all different words from the file in a list
 System.out.println(Files.lines(Paths.get("src/lambda/Lambda01.txt"))
               .map(t->t.toLowerCase().split(" ")) //split method returns array
               .flatMap(Arrays::stream)
               .distinct()
               .collect(Collectors.toList()));
   //count the different words used in file
            
 System.out.println(
		 Files.lines(Paths.get("src/lambda/Lambda01.txt"))
		 .map(t->t.toLowerCase().split(" "))
		 .flatMap(Arrays::stream)
		 .distinct()
		 .count()
		 
		 );
  
 System.out.println("=========================================");
 //find the number of the word lambda ignorecase
System.out.println( Files.lines(Paths.get("src/lambda/Lambda01.txt"))
 .map(t->t.toLowerCase().replace("_"," ").split(" "))
 .flatMap(Arrays::stream)
 .map(t->t.replaceAll("\\W",""))
 .filter(t->t.equals("expressions"))
 .count());

System.out.println("=========================================");
  }
}