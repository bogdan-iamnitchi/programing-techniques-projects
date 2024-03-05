package start;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Clasa SolveOutput oferă funcționalități pentru extragerea proprietăților unui obiect folosind reflexia.
 */
public class SolveOutput {

    /**
     * Extrage proprietățile unui obiect dat și le returnează sub formă de vector.
     *
     * @param object Obiectul pentru care se extrag proprietățile
     * @return Vectorul de proprietăți al obiectului
     */
    public static Vector<String> retrieveProperties(Object object) {
        Vector<String> row = new Vector<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
                row.add(value.toString());

            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return row;
    }
}



