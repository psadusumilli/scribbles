package com.vijayrc.tasker.view;

import com.vijayrc.meta.Bean;
import com.vijayrc.meta.ToString;
import com.vijayrc.tasker.domain.Card;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Bean
@ToString
public class CardView {
    private String id;
    private String title;
    private String summary;
    private String startBy;
    private String endBy;

    public static CardView createFrom(Card card){
        CardView cardView = new CardView();
        if(card != null){
            cardView.id = card.id();
            cardView.title = card.title();
            cardView.summary = card.summary();
            cardView.startBy = card.startBy();
            cardView.endBy = card.endBy();
        }
        return cardView;
    }

}
