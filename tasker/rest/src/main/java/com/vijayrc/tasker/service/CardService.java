package com.vijayrc.tasker.service;

import com.vijayrc.tasker.repository.AllCards;
import com.vijayrc.tasker.view.CardView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private AllCards allCards;

    public List<CardView> getAll(){
        List<CardView> views = new ArrayList<>();
        allCards.all().forEach(t -> views.add(CardView.map(t)));
        return views;
    }
    public CardView getFor(String id) {
        return CardView.map(allCards.getFor(id));
    }
    public void remove(String id) {
        allCards.remove(id);
    }
    public CardView create(CardView cardView) {
        return CardView.map(allCards.create(cardView.toCard()));
    }
}
