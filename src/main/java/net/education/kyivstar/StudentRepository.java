package net.education.kyivstar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository extends DbConnector {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private Connection conn = null;

    public StudentRepository() {
    }

    private void openConnection() {
        conn = connectDb();
    }

    public void addStudent(String surname, String name, int age) throws SQLException {
        openConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO STUDENTS (SURNAME,NAME,AGE,ENTRY_DATE) VALUES(?,?,?,?)");
            ps.setString(1, surname);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, Utils.currentDateTime());
            int insert = ps.executeUpdate();
            logger.info("Insert " + insert + " " + String.valueOf(ps));
            logger.info(String.valueOf(ps));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            conn.close();
        }

    }

    public List<Object> extractStudentsBySurname(String surname) throws SQLException {

        openConnection();
        PreparedStatement ps = null;
        List<Object> resultTasks = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT SURNAME,NAME,AGE FROM STUDENTS r WHERE SURNAME =?");
            ps.setString(1, surname);
            ps.executeUpdate();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultTasks.add(rs.getString("SURNAME"));
                resultTasks.add(rs.getString("NAME"));
                resultTasks.add(rs.getInt("AGE"));
            }
            System.out.println(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            conn.close();
        }
        System.out.println(resultTasks);
        return resultTasks;
    }

    public List<Object> extractAllStudents() throws SQLException {

        openConnection();
        PreparedStatement ps = null;
        List<Object> resultTasks = new ArrayList<>();
        try {
            ps = conn.prepareStatement("" +
                    "SELECT SURNAME,NAME,AGE, GROUP_ID FROM STUDENTS r");

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

    public void updateStudentBySurname(String surname, String surnameToReplace, String nameToReplace, int ageToReplace) throws SQLException {
        openConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE STUDENTS SET SURNAME = ?, NAME =?, AGE =? WHERE SURNAME =?");
            ps.setString(1, surnameToReplace);
            ps.setString(2, nameToReplace);
            ps.setInt(3, ageToReplace);
            ps.setString(4, surname);
            ps.executeUpdate();
            logger.info(String.valueOf(ps));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            conn.close();
        }
    }

    public void removeStudentBySurname(String surname) throws SQLException {
        openConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM STUDENTS WHERE SURNAME =?");
            ps.setString(1, surname);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            conn.close();
        }
    }
}

