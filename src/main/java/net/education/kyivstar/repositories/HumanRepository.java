package net.education.kyivstar.repositories;

import net.education.kyivstar.services.db.DbConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HumanRepository {
    private static final Logger logger = LoggerFactory.getLogger(HumanRepository.class);
    private Connection conn = null;
    private DbConnector dbConnector;

    public HumanRepository(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    private void openConnection() {
        conn = dbConnector.connectMariaDb(true);
    }

    public void removeUserBySurname(String surname, String name) throws SQLException {
        openConnection();
        Statement ps = null;
        try {
            ps = conn.createStatement();
            ps.addBatch("DELETE FROM STUDENTS WHERE SURNAME= '" + surname + "' AND NAME = '" + name + "'");
            ps.addBatch("DELETE FROM TEACHERS WHERE SURNAME='" + surname + "' AND NAME = '" + name + "'");
            ps.addBatch("DELETE FROM REVISERS WHERE SURNAME= '" + surname + "' AND NAME = '" + name + "'");
            ps.executeBatch();
        } catch (SQLException e) {

            logger.info("ROWS DELETED SUCCESSFULLY");
        } finally {
            ps.close();
            conn.close();
        }
    }

    public HashMap<String, Integer> countUsersByCategory() throws SQLException {
        openConnection();
        PreparedStatement ps = null;
        HashMap<String, Integer> resultTasks = new HashMap<>();
        try {
            ps = conn.prepareStatement("" +
                    "SELECT COUNT(*) AS COUNT, 'REVISERS' as TYPE FROM REVISERS r  \n" +
                    "UNION\n" +
                    "SELECT COUNT(*) AS COUNT, 'TEACHERS' as TYPE FROM TEACHERS t  \n" +
                    "UNION \n" +
                    "SELECT COUNT(*) AS COUNT, 'STUDENTS' as TYPE FROM STUDENTS s ");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultTasks.put(rs.getString("TYPE"), rs.getInt("COUNT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            conn.close();
        }
        return resultTasks;
    }

    public List<Object> extractAllUsers() throws SQLException {
        openConnection();
        PreparedStatement ps = null;
        List<Object> resultTasks = new ArrayList<>();
        try {
            ps = conn.prepareStatement("" +
                    "SELECT SURNAME,NAME,AGE FROM REVISERS r  \n" +
                    "UNION\n" +
                    "SELECT SURNAME,NAME,AGE FROM TEACHERS t  \n" +
                    "UNION \n" +
                    "SELECT SURNAME,NAME,AGE FROM STUDENTS s ");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultTasks.add(rs.getString("SURNAME"));
                resultTasks.add(rs.getString("NAME"));
                resultTasks.add(rs.getInt("AGE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            conn.close();
        }
        return resultTasks;
    }

    public List<Object> extractBySurname(String surname) throws SQLException {

        openConnection();
        PreparedStatement ps = null;
        List<Object> resultTasks = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT SURNAME,NAME,AGE FROM REVISERS r WHERE SURNAME =?\n" +
                    "UNION\n" +
                    "SELECT SURNAME,NAME,AGE FROM STUDENTS r WHERE SURNAME =?\n" +
                    "UNION \n" +
                    "SELECT SURNAME,NAME,AGE FROM TEACHERS r WHERE SURNAME =?");
            ps.setString(1, surname);
            ps.setString(2, surname);
            ps.setString(3, surname);
            ps.executeUpdate();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultTasks.add(rs.getString("SURNAME"));
                resultTasks.add(rs.getString("NAME"));
                resultTasks.add(rs.getInt("AGE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            conn.close();
        }
        return resultTasks;
    }

    public void removeAll() throws SQLException {
        openConnection();
        Statement st = conn.createStatement();
        try {
            st.addBatch("TRUNCATE TABLE REVISERS");
            st.addBatch("TRUNCATE TABLE STUDENTS");
            st.addBatch("TRUNCATE TABLE TEACHERS");
            st.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            st.close();
            conn.close();
        }
    }
}
