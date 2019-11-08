import mvc.Controller;
import mvc.View;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        view.setVisible(true);
        Controller controller = new Controller(view);
    }
}
