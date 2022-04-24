package generics_enum;

public class Enum01Class {
   public static void main(String[] args) {
	
	 Enum01 browserName = Enum01.CHROME;  
	 
	 
	 switch(browserName) {
	 case CHROME:
		 System.out.println("Chrome browser is running");
	 case SAFARI:
		 System.out.println("Safari browser is running");	 
	 case IE:
		 System.out.println("IE browser is running");	 
	 case FIREFOX:
		 System.out.println("Firefoxbrowser is running"); 
	 case OPERA:
		 System.out.println("Opera browser is running");	 
	 default:
		System.out.println("Invalid browser name");
		 
	 }
	 
	 
	 
}
   
}
