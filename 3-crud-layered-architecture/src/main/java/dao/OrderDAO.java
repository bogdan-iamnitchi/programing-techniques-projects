package dao;

import connection.ConnectionFactory;
import model.Orders;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Clasa OrderDAO extinde clasa AbstractDAO și furnizează interogări specifice pentru tabela "Order".
 * Implementează operațiile CRUD de bază moștenite de la clasa părinte.
 */
public class OrderDAO extends AbstractDAO<Orders>{
    /**
     * Creează interogarea SQL pentru a găsi valoarea maximă a unui câmp specificat în tabela "Orders".
     *
     * @param field Câmpul pentru care se calculează valoarea maximă.
     * @return Șirul de caractere reprezentând interogarea SQL.
     */
    private String createMaxIDQuery(String field) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ").append("max(").append(field).append(") ");
        query.append("AS ").append("id ");
        query.append("FROM ").append(getType().getSimpleName());
        System.out.println(query);
        return query.toString();
    }

    /**
     * Găsește valoarea maximă a câmpului "id" în tabela "Orders".
     *
     * @return Valoarea maximă a câmpului "id", sau -1 în caz de eroare.
     */
    public int findMaxID() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createMaxIDQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, getType().getName() + "DAO:findMaxID " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return -1;
    }
}
