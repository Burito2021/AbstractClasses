package net.education.kyivstar.repositories;

import net.education.kyivstar.config.HikariConnectionManager;
import net.education.kyivstar.services.user.UserService;
import net.education.kyivstar.services.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private HikariConnectionManager hikari;

    public TeacherRepository(HikariConnectionManager hikari) {
        this.hikari = hikari;
    }

    public void addTeacher(String surname, String name, int age) {
        try (var conn = hikari.connect();
             var ps = conn.prepareStatement("INSERT INTO TEACHERS (SURNAME,NAME,AGE,ENTRY_DATE) VALUES(?,?,?,?)")) {

            ps.setString(1, surname);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, Utils.currentDateTime());
            int insert = ps.executeUpdate();
            logger.info("Insert " + insert + " " + ps);
            logger.info(String.valueOf(ps));
        } catch (SQLException e) {
            throw new RuntimeException("error: " + e);
        }
    }

    public List<Object> extractTeacherBySurname(String surname) {
        var resultTasks = new ArrayList<>();

        try (var conn = hikari.connect();
             var ps = conn.prepareStatement("SELECT SURNAME,NAME,AGE FROM TEACHERS r WHERE SURNAME =?")) {

            ps.setString(1, surname);
            ps.executeUpdate();

            var rs = ps.executeQuery();
            while (rs.next()) {
                resultTasks.add(rs.getString("SURNAME"));
                resultTasks.add(rs.getString("NAME"));
                resultTasks.add(rs.getInt("AGE"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("error: "+e);
        }
        return resultTasks;
    }

    public List<Object> extractAllTeachers() {
        var resultTasks = new ArrayList<>();

        try (Connection conn = hikari.connect();
             var ps = conn.prepareStatement(
                     "SELECT SURNAME,NAME,AGE FROM TEACHERS r")) {

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

    public void updateTeacherBySurname(String surname, String surnameToReplace, String nameToReplace, int ageToReplace) {
        try (var conn = hikari.connect();
             var ps = conn.prepareStatement("UPDATE TEACHERS SET SURNAME = ?, NAME =?, AGE =? WHERE SURNAME =?")) {

            ps.setString(1, surnameToReplace);
            ps.setString(2, nameToReplace);
            ps.setInt(3, ageToReplace);
            ps.setString(4, surname);
            ps.executeUpdate();
            logger.info(String.valueOf(ps));
        } catch (SQLException e) {
            throw new RuntimeException("error: " + e);
        }
    }

    public void removeTeacherBySurname(String surname) {
        try (var conn = hikari.connect();
             var ps = conn.prepareStatement("DELETE FROM TEACHERS WHERE SURNAME =?");) {

            ps.setString(1, surname);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("error: " + e);
        }
    }
}
