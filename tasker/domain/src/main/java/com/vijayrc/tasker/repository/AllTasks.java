package com.vijayrc.tasker.repository;

import com.vijayrc.tasker.domain.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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
        List<Task> tasks = template.query("select * from tasks where id = ?", new Object[]{id}, new TaskMapper());
        Task task = tasks.isEmpty()? null:tasks.get(0);
        log.info(task);
        return task;
    }
    public void remove(String id) {
        template.update("delete from tasks where id = ?",id);
        log.info("deleted: "+id);
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
