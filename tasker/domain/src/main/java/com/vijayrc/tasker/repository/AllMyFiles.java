package com.vijayrc.tasker.repository;

import com.vijayrc.tasker.domain.MyFile;
import com.vijayrc.tasker.error.FileNotFound;
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

import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
@Scope("singleton")
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

    public void create(MyFile myFile) throws Exception {
        String path = location + "/files/" + myFile.name();
        log.info("create|"+path);
        File fileDb = new File(path);
        FileCopyUtils.copy(myFile.file(), fileDb);
        template.update("insert into files (card,path) values (?)", myFile.card(),path);
        log.info("create|"+path);
    }

    private static class MyFileMapper implements RowMapper<MyFile>{
        @Override
        public MyFile mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new MyFile(rs.getString("id"),rs.getString("card"),rs.getString("path"));
        }
    }

}
