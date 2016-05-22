package ru.webarch.jstudy.addressbook.tests;

import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.GroupData;
import ru.webarch.jstudy.addressbook.model.GroupSet;

import java.sql.*;

public class DbConnectionTest {

    @Test
    public void testDbConnection() {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/addressbook_loc?user=root&password=&useSSL=false"
            );

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT " +
                            "group_id, " +
                            "group_name, " +
                            "group_header, " +
                            "group_footer " +
                            "FROM group_list"
            );
            GroupSet groups = new GroupSet();
            while (resultSet.next()) {
                groups.add(
                        new GroupData()
                                .withId(resultSet.getInt("group_id"))
                                .withName(resultSet.getString("group_name"))
                                .withHeader(resultSet.getString("group_header"))
                                .withFooter(resultSet.getString("group_footer"))
                );
            }
            resultSet.close();
            statement.close();
            conn.close();

            System.out.println(groups);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }

}
