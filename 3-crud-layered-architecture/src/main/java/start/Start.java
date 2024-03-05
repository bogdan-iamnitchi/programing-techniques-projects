package start;

import java.sql.SQLException;
import java.util.logging.Logger;
import presentation.Controller;
import presentation.View;

/**
 * @Description: Clasa in care se face maparea corespunzatore tabelului Clients din baza de date.
 */
public class Start {
    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

    public static void main(String[] args) throws SQLException {

        View screen = new View();
        Controller controller = new Controller(screen);
        screen.setVisible(true);

    }

}
