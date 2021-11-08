package com.yatsenglobal.week04;

import cn.hutool.core.io.FileUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Unit test for simple App.
 */
public class Week07 {

    public static String url =  "jdbc:mysql://localhost:3306/bdn-service?rewriteBatchedStatement=ture";
    public static String username = "root";
    public static String pssword = "123456";
    public static String insertSql = "insert  into  order_info(order_no,order_status,user_id,created_time,update_time,total_money,address,aceeptor,phone) values(?,?,?,?,?,?,?,?,?) ";
    public static String insertValuesSql = "insert  into  order_info(order_no,order_status,user_id,created_time,update_time,total_money,address,aceeptor,phone) values ";
    public static String driverClass  = "com.mysql.jdbc.Driver";

    public static String comma  = ",";

    public static void main(String[] args) throws Exception{
        long x = System.currentTimeMillis();
        jdbcInsertBatch();
        long y = System.currentTimeMillis();
        System.out.println((y-x) / 1000);

    }




    public static void jdbcInsert() {


        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class<?> aClass = Class.forName(driverClass);
            connection = DriverManager.getConnection(url, username, pssword);
            statement = connection.prepareStatement(insertSql);
            int batchSize = 100;
//            List<OrderInfo> orderInfoList = produceOrderInfos();
            for (int i = 1; i <= 1000000; i++) {
                statement.setString(1, UUID.randomUUID().toString());
                statement.setByte(2, new Byte("1"));
                statement.setLong(3, 11122233l);
                long l = System.currentTimeMillis();
                statement.setLong(4, l);
                statement.setLong(5, l);
                statement.setBigDecimal(6,new BigDecimal("121"));
                statement.setString(7,"广东广州天河中山大道233号");
                statement.setString(8, "liuyufei");
                statement.setString(9, "18813299819");
                statement.execute();
            }

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

    public static void jdbcInsertBatch() {


        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class<?> aClass = Class.forName(driverClass);
             connection = DriverManager.getConnection(url, username, pssword);
            statement = connection.prepareStatement(insertSql);
            int batchSize = 100;
//            List<OrderInfo> orderInfoList = produceOrderInfos();
            for (int i = 1; i <= 1000000; i++) {
                statement.setString(1, UUID.randomUUID().toString());
                statement.setByte(2, new Byte("1"));
                statement.setLong(3, 11122233l);
                long l = System.currentTimeMillis();
                statement.setLong(4, l);
                statement.setLong(5, l);
                statement.setBigDecimal(6,new BigDecimal("121"));
                statement.setString(7,"广东广州天河中山大道233号");
                statement.setString(8, "liuyufei");
                statement.setString(9, "18813299819");
                statement.addBatch();
                if(i%batchSize==0){
                   statement.executeBatch();
                   statement.clearBatch();
                }
            }
            statement.executeBatch();
            statement.clearBatch();
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

    public static void jdbcInsertValues() {


        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class<?> aClass = Class.forName(driverClass);
             connection = DriverManager.getConnection(url, username, pssword);

            int batchSize = 100;

//            List<OrderInfo> orderInfoList = produceOrderInfos();
            for (int i = 1; i <= 1000000; i++) {
                long l = System.currentTimeMillis();
                StringBuffer sql = new StringBuffer(insertValuesSql);
                sql.append( "('").append( UUID.randomUUID().toString()).append("'").append(comma);
                sql.append(new Byte("1")).append(comma);
                sql.append(11122233l).append(comma);
                sql.append(l).append(comma);
                sql.append(l).append(comma);
                sql.append(new BigDecimal("121")).append(comma);
                sql.append("'广东广州天河中山大道233号'").append(comma);
                sql.append("'liuyufei'").append(comma);
                sql.append("'18813299819'").append(" ) ");

                if(i%batchSize==0){
                    statement = connection.prepareStatement(sql.toString());
                   statement.execute();
                }
                if(i != 1000000){
                    sql.append(",");
                }else {
                    statement = connection.prepareStatement(sql.toString());
                    statement.execute();
                }

            }

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

    public static void jdbcInsertUpload() {


        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class<?> aClass = Class.forName(driverClass);
             connection = DriverManager.getConnection(url, username, pssword);
            int batchSize = 1000;
            String insertSql= "LOAD DATA local  INFILE 'yyy.txt'   INTO TABLE order_info FIELDS  TERMINATED BY ','   LINES TERMINATED BY ';' (order_no,order_status,user_id,created_time,update_time,total_money,address,aceeptor,phone)";
            statement = connection.prepareStatement(insertSql);
            File fileExcel = new File("yyy.txt");
            BufferedWriter cw = new BufferedWriter(new FileWriter(fileExcel));

                StringBuilder sql = new StringBuilder();
                for (int i = 1; i <= 100000; i++) {
                    Long l = System.currentTimeMillis();
                    sql.append(i);
                    sql.append(comma);
                    sql.append( new Byte("1"));
                    sql.append(comma);
                    sql.append("11122233");
                    sql.append(comma);
                    sql.append(l.toString());
                    sql.append(comma);
                    sql.append(l.toString());
                    sql.append(comma);
                    sql.append("121");
                    sql.append(comma);
                    sql.append("广东广州天河中山大道233号");
                    sql.append(comma);
                    sql.append("liuyufei");
                    sql.append(comma);
                    sql.append("18813299819");
                    sql.append(";");
                    if(i%batchSize==0){
                        cw.write(sql.toString());
                        sql = new StringBuilder();
                    }
            }
            cw.write(sql.toString());
            cw.flush();
            BufferedInputStream inputStream = FileUtil.getInputStream(fileExcel);
            com.mysql.jdbc.PreparedStatement unwrap = statement.unwrap(com.mysql.jdbc.PreparedStatement.class);
            unwrap.setLocalInfileInputStream(inputStream);
//            unwrap.setLocalInfileInputStream(inputStream);
            unwrap.executeLargeUpdate();
//            unwrap.execute();
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
