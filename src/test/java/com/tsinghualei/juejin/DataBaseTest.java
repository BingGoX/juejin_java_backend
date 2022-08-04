package com.tsinghualei.juejin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        jdbcall();
    }

    @Test
    public static void jdbcall() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");//加载驱动类
        String url="jdbc:mysql://127.0.0.1:3306/jujindb?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";
//        String url = "jdbc:mysql://127.0.0.1:3306/jujindb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useServerPrepStmts=true&cachePrepStmts=true";
        String username="root";
        String password="root";
        Connection conn= DriverManager.getConnection(url,username,password);//用参数得到连接对象
        System.out.println("连接成功！");
        System.out.println(conn);
    }

}
