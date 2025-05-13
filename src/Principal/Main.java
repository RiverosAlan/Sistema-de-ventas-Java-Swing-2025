package Principal;

import Vistas.Login;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Login login = new Login();
        login.setVisible(true);
    }

}
