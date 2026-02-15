package com.haushekmiva.service;

import com.haushekmiva.dto.MoveResult;
import com.haushekmiva.model.OngoingMatchScore;

import java.util.UUID;

public class OngoingMatchOrchestratorImpl implements OngoingMatchOrchestrator{

    private final OngoingMatchRepository ongoingMatchRepository;
    private final MatchScoreCalculator matchScoreCalculator;
    private final FinishedMatchPersistence finishedMatchPersistence;

    public OngoingMatchOrchestratorImpl(OngoingMatchRepository ongoingMatchRepository,
                                        MatchScoreCalculator matchScoreCalculator,
                                        FinishedMatchPersistence finishedMatchPersistence
    ) {
        this.ongoingMatchRepository = ongoingMatchRepository;
        this.matchScoreCalculator = matchScoreCalculator;
        this.finishedMatchPersistence = finishedMatchPersistence;
    }

    @Override
    public MoveResult processMove(UUID matchUuid, Long playerId) {
        OngoingMatchScore ongoingMatchScore = ongoingMatchRepository.getMatch(matchUuid);

        matchScoreCalculator.doMove(ongoingMatchScore, playerId);

        if (!ongoingMatchScore.isMatchFinished()) {
            return new MoveResult(false, ongoingMatchScore);
        } else {
            finishedMatchPersistence.saveFinishedMatch(ongoingMatchScore);
            ongoingMatchRepository.removeMatch(matchUuid);
            return new MoveResult(true, ongoingMatchScore);
        }
    }
}
