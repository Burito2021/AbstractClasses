package net.education.kyivstar.repositories;

import net.education.kyivstar.config.HikariConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HumanRepository {
    private static final Logger logger = LoggerFactory.getLogger(HumanRepository.class);

    private HikariConnectionManager hikari;

    public HumanRepository(HikariConnectionManager hikari) {
        this.hikari = hikari;
    }

    public void removeUserBySurname(String surname, String name) {
        try (var conn = hikari.connect();
             var st = conn.createStatement()) {

            st.addBatch("DELETE FROM STUDENTS WHERE SURNAME= '" + surname + "' AND NAME = '" + name + "'");
            st.addBatch("DELETE FROM TEACHERS WHERE SURNAME='" + surname + "' AND NAME = '" + name + "'");
            st.addBatch("DELETE FROM REVISERS WHERE SURNAME= '" + surname + "' AND NAME = '" + name + "'");
            st.executeBatch();
            logger.info("ROWS DELETED SUCCESSFULLY");
        } catch (SQLException e) {
            throw new RuntimeException("error: " + e);
        }
    }

    public HashMap<String, Integer> countUsersByCategory() {
        HashMap<String, Integer> resultTasks = new HashMap<>();

        try (var conn = hikari.connect();
             var ps = conn.prepareStatement("" +
                     "SELECT COUNT(*) AS COUNT, 'REVISERS' as TYPE FROM REVISERS r  \n" +
                     "UNION\n" +
                     "SELECT COUNT(*) AS COUNT, 'TEACHERS' as TYPE FROM TEACHERS t  \n" +
                     "UNION \n" +
                     "SELECT COUNT(*) AS COUNT, 'STUDENTS' as TYPE FROM STUDENTS s ")) {

            var rs = ps.executeQuery();
            while (rs.next()) {
                resultTasks.put(rs.getString("TYPE"), rs.getInt("COUNT"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("error: " + e);
        }

        return resultTasks;
    }

    public List<Object> extractAllUsers() {
        var resultTasks = new ArrayList<>();

        try (var conn = hikari.connect();
             var ps = conn.prepareStatement("" +
                     "SELECT SURNAME,NAME,AGE FROM REVISERS r  \n" +
                     "UNION\n" +
                     "SELECT SURNAME,NAME,AGE FROM TEACHERS t  \n" +
                     "UNION \n" +
                     "SELECT SURNAME,NAME,AGE FROM STUDENTS s ")) {

            var rs = ps.executeQuery();
            while (rs.next()) {
                resultTasks.add(rs.getString("SURNAME"));
                resultTasks.add(rs.getString("NAME"));
                resultTasks.add(rs.getInt("AGE"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("error: " + e);
        }

        return resultTasks;
    }

    public List<Object> extractBySurname(String surname) {
        var resultTasks = new ArrayList<>();

        try (var conn = hikari.connect();
             var ps = conn.prepareStatement("SELECT SURNAME,NAME,AGE FROM REVISERS r WHERE SURNAME =?\n" +
                     "UNION\n" +
                     "SELECT SURNAME,NAME,AGE FROM STUDENTS r WHERE SURNAME =?\n" +
                     "UNION \n" +
                     "SELECT SURNAME,NAME,AGE FROM TEACHERS r WHERE SURNAME =?");) {

            ps.setString(1, surname);
            ps.setString(2, surname);
            ps.setString(3, surname);
            ps.executeUpdate();

            var rs = ps.executeQuery();
            while (rs.next()) {
                resultTasks.add(rs.getString("SURNAME"));
                resultTasks.add(rs.getString("NAME"));
                resultTasks.add(rs.getInt("AGE"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("error: " + e);
        }

        return resultTasks;
    }

    public void removeAll() {
        try (var conn = hikari.connect();
             var st = conn.createStatement()) {

            st.addBatch("TRUNCATE TABLE REVISERS");
            st.addBatch("TRUNCATE TABLE STUDENTS");
            st.addBatch("TRUNCATE TABLE TEACHERS");
            st.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("error: " + e);
        }
    }
}
