package net.education.kyivstar.repositories;

import net.education.kyivstar.config.DbConnector;
import net.education.kyivstar.config.HikariConfiguration;
import net.education.kyivstar.services.user.UserService;
import net.education.kyivstar.services.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private Connection conn = null;
    private DbConnector dbConnector;
    private HikariConfiguration hikari;

    public ReviserRepository(HikariConfiguration hikari) {
        this.hikari = hikari;
    }

    public ReviserRepository(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    private void openConnection() throws SQLException {
        conn = hikari.connect();
    }
    /*private void openConnection() {
        conn = dbConnector.connectMariaDb(true);
    }*/

    public void addReviser(String surname, String name, int age) throws SQLException {
        openConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO REVISERS (SURNAME,NAME,AGE,ENTRY_DATE) VALUES(?,?,?,?)");
            ps.setString(1, surname);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, Utils.currentDateTime());
            int insert = ps.executeUpdate();
            logger.info("Insert " + insert + " " + String.valueOf(ps));
            logger.info(String.valueOf(ps));
        } catch (SQLException e) {
            throw new RuntimeException("error: "+e);
        } finally {
            ps.close();
            conn.close();
        }

    }

    public List<Object> extractAllRevisers() throws SQLException {

        openConnection();
        PreparedStatement ps = null;
        List<Object> resultTasks = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT SURNAME,NAME,AGE FROM REVISERS r");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultTasks.add(rs.getString("SURNAME"));
                resultTasks.add(rs.getString("NAME"));
                resultTasks.add(rs.getInt("AGE"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("error: "+e);
        } finally {
            ps.close();
            conn.close();
        }
        return resultTasks;
    }

    public List<Object> extractRevisersBySurname(String surname) throws SQLException {

        openConnection();
        PreparedStatement ps = null;
        List<Object> resultTasks = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT SURNAME,NAME,AGE FROM REVISERS r WHERE SURNAME =?");
            ps.setString(1, surname);
            ps.executeUpdate();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultTasks.add(rs.getString("SURNAME"));
                resultTasks.add(rs.getString("NAME"));
                resultTasks.add(rs.getInt("AGE"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("error: "+e);
        } finally {
            ps.close();
            conn.close();
        }
        return resultTasks;
    }

    public void updateReviserBySurname(String surname, String surnameToReplace, String nameToReplace, int ageToReplace) throws SQLException {
        openConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE REVISERS SET SURNAME = ?, NAME =?, AGE =? WHERE SURNAME =?");
            ps.setString(1, surnameToReplace);
            ps.setString(2, nameToReplace);
            ps.setInt(3, ageToReplace);
            ps.setString(4, surname);
            ps.executeUpdate();
            logger.info(String.valueOf(ps));
        } catch (SQLException e) {
            throw new RuntimeException("error: "+e);
        } finally {
            ps.close();
            conn.close();
        }
    }

    public void removeReviserBySurname(String surname) throws SQLException {
        openConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM REVISERS WHERE SURNAME =?");
            ps.setString(1, surname);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("error: "+e);
        } finally {
            ps.close();
            conn.close();
        }
    }
}