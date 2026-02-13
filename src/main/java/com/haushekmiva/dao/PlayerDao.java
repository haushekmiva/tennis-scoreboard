package com.haushekmiva.dao;

import com.haushekmiva.model.Player;

import java.util.Optional;

public interface PlayerDao {
    Optional<Player> findByName(String name);
    Long save(Player entity);
    Player getReferenceById(Long id);
}
