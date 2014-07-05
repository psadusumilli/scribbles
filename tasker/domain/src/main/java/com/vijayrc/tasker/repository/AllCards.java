package com.vijayrc.tasker.repository;

import com.vijayrc.tasker.domain.Card;
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
import java.util.List;

@Repository
@Scope("singleton")
public class AllCards {
    private static Logger log = LogManager.getLogger(AllCards.class);
    private JdbcTemplate template;

    @Autowired
    public AllCards(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }
    public List<Card> all(){
        List<Card> cards = template.query("select * from cards", new CardMapper());
        cards.forEach(log::info);
        log.info("|size=" + cards.size());
       return cards;
    }
    public Card getFor(String id) {
        log.info("|id="+id);
        List<Card> cards = template.query("select * from cards where id = ?", new Object[]{id}, new CardMapper());
        Card card = cards.isEmpty()? null: cards.get(0);
        log.info(card);
        return card;
    }
    public void remove(String id) {
        template.execute("delete from cards where id = "+id);
        log.info("|deleted: "+id);
    }

    private static final class CardMapper implements RowMapper<Card>{
        @Override
        public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Card(
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("summary"),
                    rs.getDate("startBy"),
                    rs.getDate("endBy"));
        }
    }
}
