package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

/**
 * @Description: Clasa AbstractDAO implementeaza operatiile (insert, update, deleteByID, deleteALL, findByID, findALL)
 * Este o clasa abstracta ce rezolva interogarile de mai sus pentru orice clasa<T> ce o mosteneste si care face maparea unui tabel.
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */


public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Class<T> getType() {
        return type;
    }

    //------------------------------------------------------------------------------------------------------------------------QUERYS_FACTORY
    private String createSelectWhereQuery(String field) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT").append(" * ").append("FROM ");
        query.append(type.getSimpleName());
        query.append(" WHERE ").append(field).append(" =?");
        return query.toString();
    }

    private String createSelectAllQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT").append(" * ").append("FROM ");
        query.append(type.getSimpleName());
        return query.toString();
    }

    //-------------------------------------------------------------------------------------------------------------------------------SELECT
    /**
     * Returnează toate înregistrările din tabela corespunzătoare clasei T din baza de date.
     * @return Lista de obiecte de tip T
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Caută și returnează înregistrarea cu ID-ul specificat din tabela corespunzătoare clasei T din baza de date.
     *
     * @param id ID-ul înregistrării căutate
     * @return Obiectul de tip T cu ID-ul specificat
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectWhereQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    //----------------------------------------------------------------------------------------------------------------------------------DELETE
    /**
     * Construiește interogarea SQL pentru ștergerea înregistrării cu un anumit ID din tabela corespunzătoare clasei T.
     * @param field Numele câmpului după care se face ștergerea (de obicei "id")
     * @return Interogarea SQL pentru ștergerea înregistrării
     */
    String createDeleteWhereQuery(String field) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE ").append("FROM ");
        query.append(type.getSimpleName());
        query.append(" WHERE ").append(field).append(" =?");
        System.out.println(query);
        return query.toString();
    }

    /**
     * Construiește interogarea SQL pentru ștergerea tuturor înregistrărilor din tabela corespunzătoare clasei T.
     *
     * @return Interogarea SQL pentru ștergerea înregistrărilor
     */
    private String createDeleteAllQuery() {
        StringBuilder query = new StringBuilder();
        query.append("DELETE ").append("FROM ");
        query.append(type.getSimpleName());
        System.out.println(query);
        return query.toString();
    }

    /**
     * Șterge înregistrarea cu un anumit ID din tabela corespunzătoare clasei T.
     *
     * @param id ID-ul înregistrării de șters
     * @return Numărul de înregistrări afectate (1 pentru succes, 0 pentru eșec)
     */
    public int deleteById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        int resultSet = 0;
        String query = createDeleteWhereQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeUpdate();

            return resultSet;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteById " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

    /**
     * Șterge toate înregistrările din tabela corespunzătoare clasei T.
     *
     * @return Numărul de înregistrări afectate (numărul de înregistrări șterse)
     */
    public int deleteAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        int resultSet = 0;
        String query = createDeleteAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeUpdate();

            return resultSet;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteALL " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

    //----------------------------------------------------------------------------------------------------------------------------------INSERT
    /**
     * Construiește interogarea SQL pentru inserarea unui obiect de tip T în tabela corespunzătoare.
     *
     * @param t Obiectul de tip T de inserat
     * @return Interogarea SQL pentru inserare
     */
    private String createInsertQuery(T t) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT ").append("INTO ");
        query.append(type.getSimpleName()).append(" VALUES ");

        query.append("(");
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;

            try {
                value = field.get(t);
                if(field.getName().equals("id") && (int)value != -1) {
                    query.append(value);
                } else if (field.getName().equals("date")) {
                    SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String curr_date = "'" + DateFormat.format(value) + "'";
                    query.append(", ");
                    query.append(curr_date);
                } else {
                    query.append(", ");
                    query.append(value);
                }

            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        query.append(");");
        System.out.println(query);
        return query.toString();
    }

    /**
     * Inserează un obiect de tip T în tabela corespunzătoare.
     *
     * @param t Obiectul de tip T de inserat
     * @return Obiectul de tip T inserat (null în caz de eșec)
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        int resultSet = 0;
        String query = createInsertQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeUpdate();

            return (resultSet != 0 ? t : null);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    //----------------------------------------------------------------------------------------------------------------------------------UPDATE
    /**
     * Construiește interogarea SQL pentru actualizarea unui obiect de tip T în tabela corespunzătoare.
     *
     * @param t Obiectul de tip T de actualizat
     * @return Interogarea SQL pentru actualizare
     */
    private String createUpdateQuery(T t) {
        int id = -1;
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ");
        query.append(type.getSimpleName()).append(" SET ");

        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;

            try {
                value = field.get(t);
                if(field.getName().equals("id")) {
                    id = (int)value;
                    continue;
                }

                if (field.getName().equals("date")) {
                    SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String curr_date = "'" + DateFormat.format(value) + "'";
                    query.append(field.getName()).append("=").append(curr_date).append(", ");
                }
                else {
                    query.append(field.getName()).append("=").append(value).append(", ");
                }


            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        query.deleteCharAt(query.lastIndexOf(","));
        query.append(" WHERE id =").append(id);

        System.out.println(query);
        return query.toString();
    }

    /**
     * Actualizează un obiect de tip T în tabela corespunzătoare.
     *
     * @param t Obiectul de tip T de actualizat
     * @return Obiectul de tip T actualizat (null în caz de eșec)
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        int resultSet = 0;
        String query = createUpdateQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeUpdate();

            return (resultSet != 0 ? t : null);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    //--------------------------------------------------------------------------------------------------------------------------------SOLVE_RESULT
    /**
     * Creație obiecte de tip T bazate pe rezultatul unui ResultSet.
     *
     * @param resultSet ResultSet-ul rezultat dintr-o interogare SELECT
     * @return Lista de obiecte de tip T create
     */
    public List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | SQLException | IllegalAccessException | SecurityException |
                 IllegalArgumentException | InvocationTargetException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }


}
