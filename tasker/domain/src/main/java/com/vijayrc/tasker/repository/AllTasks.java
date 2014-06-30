package com.vijayrc.tasker.repository;

import com.vijayrc.tasker.domain.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Scope("singleton")
public class AllTasks {
    private static Logger log = LogManager.getLogger(AllTasks.class);
    private JdbcTemplate template;

    public AllTasks(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public List<Task> all(){
        List<Task> tasks = template.query("select * from tasks", new TaskMapper());
        log.info("tasks size:" + tasks.size());
       return tasks;
    }
    public Task getFor(String id) {
        log.info("fetching for: "+id);
        Task task = all().stream().filter(t -> t.hasId(id)).findFirst().get();
        log.info("task: "+task);
        return task;
    }

    private static final class TaskMapper implements RowMapper<Task>{
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Task(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("summary"),
                    rs.getDate("startBy"),
                    rs.getDate("endBy"));
        }
    }
}
