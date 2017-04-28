package com.cat.jq.util;

import com.alibaba.druid.pool.DruidDataSource;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class ConnectionUtils {

    private static final DataSource dataSource;
    private static final DSLContext dslContext;

    static {
        dataSource = dataSource();
        dslContext = context();
    }

    private static DruidDataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        final String url = "jdbc:mysql://localhost:3306/hb?useSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";

        druidDataSource.setUrl(url);
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");

        return druidDataSource;
    }

    private static DSLContext context() {
        Configuration configuration = new DefaultConfiguration();
        configuration.set(dataSource).set(SQLDialect.MYSQL);

        return DSL.using(configuration);
    }

    public static Connection connection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
//        dslContext.insertInto()
    }
}
