package com.zensar.assignment4;

import com.zensar.assignment4.utils.DBUtil;

import java.io.InputStream;
import java.sql.*;

public class JDBCAsgnmtMain {

    public static void main(String[] args) throws SQLException {

        System.out.println("1. Copy all records from employee table & insert them into employee_copy table. Use PreparedStatement.");
        jdbcAssignment1(DBUtil.getConnection());
        System.out.println("\n==========================================================================================\n");

        System.out.println("2.Save an image into database table & read it back. Use BLOB.");
        jdbcAssignment2(DBUtil.getConnection());

        System.out.println("\n==========================================================================================\n");

        System.out.println("3. Using batch updates, copy all records from employee table & insert them into employee_copy table. Also use transaction & prepared statement.");
        jdbcAssignment3(DBUtil.getConnection());

        System.out.println("\n==========================================================================================\n");
    }

    // 3
    private static void jdbcAssignment3(Connection dbConnection) throws SQLException {
        if (dbConnection != null) {
            dbConnection.setAutoCommit(false);

            PreparedStatement pStmt = dbConnection.prepareStatement("CREATE TABLE employee_copy SELECT * FROM employee");
            PreparedStatement pStmt1 = dbConnection.prepareStatement("UPDATE employee_copy SET name=?, role=? where id = ?");
            try {
                Statement stmt = dbConnection.createStatement();
                stmt.executeUpdate("drop table employee_copy");
                stmt.close();

                pStmt.execute();

                Statement stmt1 = dbConnection.createStatement();
                ResultSet rs1 = stmt1.executeQuery("select * from employee_copy");
                while (rs1.next()) {
                    pStmt1.setString(1, rs1.getString("role"));
                    pStmt1.setString(2, rs1.getString("name"));
                    pStmt1.setInt(3, rs1.getInt("id"));
                    // To create exception to check Transactions
//                    if (rs1.getInt("id") > 5) {
//                        dbConnection.close();
//                    }
                    pStmt1.execute();
                }

                rs1.close();
                stmt1.close();

                Statement stmt2 = dbConnection.createStatement();
                ResultSet rs2 = stmt2.executeQuery("select * from employee_copy");
                System.out.println("ID \t\t NAME \t\t ROLE");
                while (rs2.next()) {
                    System.out.println(rs2.getInt("id") + " \t\t " + rs2.getString("name") + " \t\t " + rs2.getString("role"));
                }
                rs2.close();
                stmt2.close();

                dbConnection.commit();
            } catch (Exception e) {
                dbConnection.rollback();
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                pStmt.close();
                pStmt1.close();
                dbConnection.close();
            }
        } else {
            System.out.println("Database is not connected.");
        }
    }

    // 2
    private static void jdbcAssignment2(Connection dbConnection) throws SQLException {
        if (dbConnection != null) {
            PreparedStatement pStmt = dbConnection.prepareStatement("UPDATE employee_copy SET profile_pic=? where id=id % ? = 0");
            try {
                InputStream file = JDBCAsgnmtMain.class.getClassLoader().getResourceAsStream("image/github.png");
                pStmt.setBlob(1, file);
                pStmt.setInt(2, 2);
                pStmt.execute();
                Statement stmt = dbConnection.createStatement();
                ResultSet rs = stmt.executeQuery("select * from employee_copy");
                while (rs.next()) {
                    System.out.println(rs.getBlob("profile_pic"));
                }
                rs.close();
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                pStmt.close();
                dbConnection.close();
            }
        } else {
            System.out.println("Database is not connected.");
        }
    }

    // 1
    private static void jdbcAssignment1(Connection dbConnection) throws SQLException {
        if (dbConnection != null) {
            PreparedStatement pStmt = dbConnection.prepareStatement("CREATE TABLE employee_copy SELECT * FROM employee");
            try {
                Statement stmt = dbConnection.createStatement();
                stmt.executeUpdate("drop table employee_copy");
                stmt.close();

                pStmt.execute();
                PreparedStatement pstmt = dbConnection.prepareStatement("select * from employee_copy");
                ResultSet rs = pstmt.executeQuery();
                System.out.println("ID \t\t NAME \t\t ROLE");
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " \t\t " + rs.getString("name") + " \t\t " + rs.getString("role"));
                }
                rs.close();
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                pStmt.close();
                dbConnection.close();
            }
        } else {
            System.out.println("Database is not connected.");
        }
    }
}
