package com.vijayrc.tasker.repository;

import com.vijayrc.tasker.domain.Card;
import com.vijayrc.tasker.error.CardNotFound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
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
        log.info("|fetch size|" + cards.size());
       return cards;
    }

    public Card fetch(String id) throws CardNotFound {
        List<Card> cards = template.query("select * from cards where id = ?", new Object[]{id}, new CardMapper());
        if(isEmpty(cards)) throw new CardNotFound();
        Card card = cards.get(0);
        log.info("|fetch|"+card);
        return card;
    }

    public void remove(String id) {
        template.execute("delete from cards where id = "+id);
        log.info("|delete| "+id);
    }

    public Card create(Card card) {
        template.update("insert into cards(title,summary,startBy,endBy) values (?,?,?,?)",
                card.title(), card.summary(), card.startBy(), card.endBy());
        Integer id = template.queryForObject("select max(id) from cards", Integer.class);
        Card cardDb = template.query("select * from cards where id = ?", new Object[]{id}, new CardMapper()).get(0);
        log.info("|create|"+cardDb);
        return cardDb;
    }

    public Card update(Card card) throws CardNotFound {
        Card cardDb = fetch(card.id());
        cardDb.merge(card);
        template.update("update cards set title = ?, summary = ?, startBy = ?, endBy = ? where id = ?",
                cardDb.title(), cardDb.summary(), cardDb.startBy(), cardDb.endBy(), cardDb.id());
        log.info("|update|" + cardDb);
        return cardDb;
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
