package com.school.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class Week05Jdbc  {

    public static String url =  "jdbc:mysql://l27.0.0.1:3306/bdn-service";
    public static String username = "root";
    public static String pssword = "123456";
    public static String sql = "select user_name from  yufei_tb limit 1 ";
    public static String insertSql = "insert  into  yufei_tb(user_name,value) values('myName','111') ";
    public static String updateSql = "update   yufei_tb  set user_name ='yourName' ";
    public static String deleteSql  = "delete from  yufei_tb ";
    public static String driverClass  = "com.mysql.jdbc.Driver";

    public static void main(String[] args) throws Exception{
//        jdbcDataSourece();
    }

    public static void jdbcQuery() throws Exception{
        Class<?> aClass = Class.forName(driverClass);
        try (
                Connection connection = DriverManager.getConnection(url,username,pssword); Statement statement = connection
                .createStatement(); ResultSet dataset = statement.executeQuery(sql)  ){
            String user = null;
            if(dataset.next()){
                user = dataset.getString(1);
            }
            System.out.println("查出的结果:"+user);

        }
    }
    public static void jdbcInsert() throws Exception{
        Class<?> aClass = Class.forName(driverClass);
        try (
                Connection connection = DriverManager.getConnection(url,username,pssword); Statement statement = connection
                .createStatement();   ){
            int i = statement.executeUpdate(insertSql);
            System.out.println("影响的行数:"+i);
        }
    }
    public static void jdbcUpdate() throws Exception{
        Class<?> aClass = Class.forName(driverClass);
        try (
                Connection connection = DriverManager.getConnection(url,username,pssword); Statement statement = connection
                .createStatement();   ){
            int i =  statement.executeUpdate(updateSql);
            System.out.println("影响的行数:"+i);
        }
    }
    public static void jdbcDelete() throws Exception{
        Class<?> aClass = Class.forName(driverClass);
        try (
                Connection connection = DriverManager.getConnection(url,username,pssword); Statement statement = connection
                .createStatement();   ){
            int i = statement.executeUpdate(deleteSql);
            System.out.println("影响的行数:"+i);

        }
    }

    public static void jdbcQueryPre() throws Exception{
        Class<?> aClass = Class.forName(driverClass);
        try (
                Connection connection = DriverManager.getConnection(url,username,pssword); PreparedStatement statement = connection
                .prepareStatement(sql); ResultSet dataset = statement.executeQuery()  ){
            String user = null;
            if(dataset.next()){
                user = dataset.getString(1);
            }
            System.out.println("查出的结果:"+user);
        }
    }

    public static void jdbcPreBatch() {
        try {
            Class<?> aClass = Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection(url, username, pssword);
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(deleteSql);
            statement.addBatch(insertSql);
            statement.addBatch(updateSql);
            statement.executeUpdate();
            statement.executeBatch();
            connection.commit();
        }catch (Exception e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void jdbcDataSourece() {
        final HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClass);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(pssword);
        final HikariDataSource ds = new HikariDataSource(config);

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection =ds.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(deleteSql);
            statement.addBatch(insertSql);
            statement.addBatch(updateSql);
            statement.executeUpdate();
            statement.executeBatch();
            connection.commit();
        }catch (Exception e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }



}
