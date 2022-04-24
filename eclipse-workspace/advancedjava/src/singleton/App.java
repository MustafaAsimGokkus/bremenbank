package singleton;

public class App {

  private App() {};

 
  private static App app;
 
  public static App getInstance() { //lazy instantiation
	if(app == null) {
		app=new App();
	}
	return app;
  }

}