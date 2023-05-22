package com.codetome.bookstore.repository.user;

import com.codetome.bookstore.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Primary
@Repository
public class UserRepositoryJdbc implements UserRepository{
    private static final String USER_TABLE_NAME = "user";
    private static final String USER_TABLE_NAME_ID = "IDUser";
    private static final String SELECT_ALL_USERS = "SELECT * FROM `user` as u";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public UserRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(USER_TABLE_NAME)
                .usingGeneratedKeyColumns(USER_TABLE_NAME_ID);
    }
    @Override
    public Optional<User> findUserByUsername(String username) {
        String query = SELECT_ALL_USERS + " WHERE u.username = '" + username + "'";

        return jdbcTemplate.query(query, this::mapRowToUser).stream().findFirst();
    }

    private User mapRowToUser(ResultSet result, int rowNum) throws SQLException {
        return new User(
                result.getInt(USER_TABLE_NAME_ID),
                result.getString("username")
        );
    }
}
