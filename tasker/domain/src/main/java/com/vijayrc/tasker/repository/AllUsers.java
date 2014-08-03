package com.vijayrc.tasker.repository;

import com.vijayrc.tasker.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AllUsers {
    private static Logger log = LogManager.getLogger(AllCards.class);
    private JdbcTemplate template;

    @Autowired
    public AllUsers(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }
    public User get(String username) {
        List<User> users = template.query("select * from users where name = ?", new Object[]{username}, new UserMapper());
        User user = users.isEmpty() ? null : users.get(0);
        log.info("fetch: "+user);
        return user;
    }

    private class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getString("name"),rs.getString("password"),rs.getString("roles"));
        }
    }
}
