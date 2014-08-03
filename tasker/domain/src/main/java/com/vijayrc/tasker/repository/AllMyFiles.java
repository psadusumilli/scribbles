package com.vijayrc.tasker.repository;

import com.vijayrc.tasker.domain.MyFile;
import com.vijayrc.tasker.error.FileNotFound;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import javax.sql.DataSource;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;
import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
public class AllMyFiles {
    private static Logger log = LogManager.getLogger(AllMyFiles.class);
    private JdbcTemplate template;
    private String location;

    @Autowired
    public AllMyFiles(DataSource dataSource, @Value("#{systemProperties['user.dir']}") String location) {
        this.template = new JdbcTemplate(dataSource);
        this.location = location;
    }

    public MyFile fetch(String id) throws FileNotFound {
        List<MyFile> myFiles = template.query("select * from files where id = ?", new Object[]{id}, new MyFileMapper());
        if(isEmpty(myFiles)) throw new FileNotFound();
        MyFile myFile = myFiles.get(0);
        log.info("fetched|"+myFile);
        return myFile;
    }

    public Integer create(MyFile myFile) throws Exception {
        String path = location + "/files/" + myFile.name();
        myFile.writeTo(path);
        log.info("saved|"+path);

        template.update("insert into files (card,path) values (?,?)", myFile.card(),path);
        Integer id = template.queryForObject("select max(id) from files", Integer.class);
        log.info("create|"+id);
        return id;
    }

    private static class MyFileMapper implements RowMapper<MyFile>{
        @Override
        public MyFile mapRow(ResultSet rs, int rowNum) throws SQLException {
            return MyFile.readInstance(rs.getString("id"),rs.getString("card"),rs.getString("path"));
        }
    }

}
