package com.logicbig.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

@Component
public class MyContextRefreshListener {
    @Autowired
    private DataSource appDataSource;

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        System.out.println("-- Context Refreshed --");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(appDataSource);
        execSql(jdbcTemplate, "SHOW TABLES");
        execSql(jdbcTemplate, "Select * from USERS");
        execSql(jdbcTemplate, "Select * from AUTHORITIES");
    }

    public static void execSql(JdbcTemplate jdbcTemplate, String sql) {
        System.out.printf("'%s'%n", sql);
        jdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                System.out.println();
                ResultSetMetaData metadata = rs.getMetaData();
                int columnCount = metadata.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metadata.getColumnName(i);
                    Object object = rs.getObject(i);
                    System.out.printf("%s = %s%n", columnName, object);
                }
            }
        });
        System.out.println("------------------");
    }
}