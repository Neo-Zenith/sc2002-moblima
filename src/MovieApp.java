import view.MovieAppView;

public class MovieApp {
    
    private static MovieAppView defaultView = new MovieAppView();
    public static void main(String args[]) {
        defaultView.appContent();
        System.out.println("Thank you for using MOBLIMA!");
    }
}
