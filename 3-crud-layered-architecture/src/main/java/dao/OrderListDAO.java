package dao;

import connection.ConnectionFactory;
import model.OrderList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Clasa OrderListDAO extinde clasa AbstractDAO și furnizează interogări specifice pentru tabela "OrderList".
 * Implementează operațiile CRUD de bază moștenite de la clasa părinte.
 */
public class OrderListDAO extends AbstractDAO<OrderList>{

    /**
     * Șterge înregistrările din tabela "OrderList" în funcție de idul comenzii.
     *
     * @param idorder Idul comenzii pentru care se efectuează ștergerea.
     * @return Numărul de înregistrări afectate de ștergere.
     */
    public int deleteByIdOrder(int idorder) {
        Connection connection = null;
        PreparedStatement statement = null;
        int resultSet = 0;
        String query = createDeleteWhereQuery("idorder");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, idorder);
            resultSet = statement.executeUpdate();

            return resultSet;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, getType().getName() + "DAO:deleteByIdOrder " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }
}
