package com.haushekmiva.service;

import com.haushekmiva.dao.PlayerDao;
import com.haushekmiva.dao.PlayerHibernateDao;
import com.haushekmiva.dto.MatchParticipantIds;
import com.haushekmiva.model.Player;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class PlayerCheckService {

    private final PlayerDao playerDao;

    public PlayerCheckService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public MatchParticipantIds getPlayerIds(String firstPlayerName, String secondPlayerName) {
        Long firstPlayerId = getOrCreateId(firstPlayerName);
        Long secondPlayerId = getOrCreateId(secondPlayerName);

        return new MatchParticipantIds(firstPlayerId, secondPlayerId);
    }

    private Long getOrCreateId(String playerName) {
        Optional<Player> playerRaw = playerDao.findByName(playerName);

        if (playerRaw.isPresent()) {
            return playerRaw.get().getId();
        }

        Player player = new Player(playerName);
        return playerDao.save(player);
    }
}
