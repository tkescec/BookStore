package com.codetome.bookstore.repository.log;

import com.codetome.bookstore.domain.Category;
import com.codetome.bookstore.domain.Log;
import com.codetome.bookstore.domain.User;
import com.codetome.bookstore.dto.InvoiceDto;
import com.codetome.bookstore.dto.LogDto;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Repository
public class LogRepositoryJdbc implements LogRepository {

    private static final String LOG_TABLE_NAME = "log";
    private static final String LOG_TABLE_NAME_ID = "IDLog";
    private static final String SELECT_ALL_LOGS =
                            "SELECT * FROM `log` as l " +
                            "INNER JOIN `user` as u " +
                            "ON l.UserId = u.IdUser " +
                            "ORDER BY l.LastLogin DESC";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public LogRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(LOG_TABLE_NAME)
                .usingGeneratedKeyColumns(LOG_TABLE_NAME_ID);
    }

    @Override
    public List<Log> getAllLogs() {
        return jdbcTemplate.query(SELECT_ALL_LOGS, this::mapRowToLog);
    }

    @Override
    public Number saveNewLog(LogDto log) throws IOException {
        return saveNewLogDetails(log);
    }

    private Log mapRowToLog(ResultSet result, int rowNum) throws SQLException {
        return new Log(
                result.getInt(LOG_TABLE_NAME_ID),
                result.getString("Ip"),
                result.getTimestamp("LastLogin"),
                new User(
                        result.getInt("IDUser"),
                        result.getString("username"),
                        result.getString("FirstName"),
                        result.getString("LastName")
                )
        );
    }

    private Number saveNewLogDetails(LogDto log) throws IOException {
        Map<String, Object> logDetails = new HashMap<>();

        logDetails.put(LOG_TABLE_NAME_ID, log.getIDLog());
        logDetails.put("Ip", log.getIp());
        logDetails.put("LastLogin", log.getLastLogin());
        logDetails.put("UserID", log.getUserID());

        return simpleJdbcInsert.executeAndReturnKey(logDetails).intValue();
    }
}
