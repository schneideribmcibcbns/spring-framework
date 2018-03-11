package com.logicbig.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class PrintDbInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private DataSource appDataSource;

    @PostConstruct
    public void init() {
        printPersistentLoginTable("On startup");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                @Nullable Exception ex) throws Exception {
        System.out.println("Session id: " + request.getSession().getId());
        printPersistentLoginTable("After Completion");
    }

    private void printPersistentLoginTable(String msg) {
        System.out.printf("-- %s --%n", msg);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(appDataSource);
        execSql(jdbcTemplate, "Select * from PERSISTENT_LOGINS");

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