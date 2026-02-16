package com.haushekmiva.mapper;

import com.haushekmiva.dto.OngoingMatchScoreDto;
import com.haushekmiva.model.OngoingMatchScore;
import com.haushekmiva.model.PointDisplayState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MatchMapper {

    MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);

    @Mapping(target = "firstPlayerName", expression = "java(ongoingMatchScore.getFirstPlayerName())")
    @Mapping(target = "firstPlayerId", expression = "java(String.valueOf(ongoingMatchScore.getFirstPlayerId()))")
    @Mapping(source = ".", target = "firstPlayerPoints", qualifiedByName = "getFirstPlayerPoints")
    @Mapping(target = "firstPlayerGames", expression = "java(String.valueOf(ongoingMatchScore.getFirstPlayerGames()))")
    @Mapping(target = "firstPlayerSets", expression = "java(String.valueOf(ongoingMatchScore.getFirstPlayerSets()))")
    @Mapping(target = "secondPlayerName", expression = "java(ongoingMatchScore.getSecondPlayerName())")
    @Mapping(target = "secondPlayerId", expression = "java(String.valueOf(ongoingMatchScore.getSecondPlayerId()))")
    @Mapping(source = ".", target = "secondPlayerPoints", qualifiedByName = "getSecondPlayerPoints")
    @Mapping(target = "secondPlayerGames", expression = "java(String.valueOf(ongoingMatchScore.getSecondPlayerGames()))")
    @Mapping(target = "secondPlayerSets", expression = "java(String.valueOf(ongoingMatchScore.getSecondPlayerSets()))")
    OngoingMatchScoreDto ongoingMatchScoreToDto(OngoingMatchScore ongoingMatchScore);

    @Named("getFirstPlayerPoints")
    default String getFirstPlayerPoints(OngoingMatchScore score) {
        return getPlayerPoints(score, PlayerNumbers.FIRST);
    }

    @Named("getSecondPlayerPoints")
    default String getSecondPlayerPoints(OngoingMatchScore score) {
        return getPlayerPoints(score, PlayerNumbers.SECOND);
    }

    default String getPlayerPoints(OngoingMatchScore score, PlayerNumbers playerNumber) {
        String ADVANTAGE_SYMBOL = "AD";
        int POINT_COUNT_TO_GET_FORTY = 3;
        String[] values = {"0", "15", "30", "40"};
        PointDisplayState pointDisplayState = score.getPointDisplayState();

        Long playerId = switch (playerNumber) {
            case FIRST -> score.getFirstPlayerId();
            case SECOND -> score.getSecondPlayerId();
        };

        return switch (pointDisplayState) {
            case NORMAL -> values[score.getPlayerPoints(playerId)];
            case DEUCE -> values[3];
            case ADVANTAGE_FIRST -> (playerNumber == PlayerNumbers.FIRST) ? ADVANTAGE_SYMBOL : values[POINT_COUNT_TO_GET_FORTY];
            case ADVANTAGE_SECOND -> (playerNumber == PlayerNumbers.SECOND) ? ADVANTAGE_SYMBOL : values[POINT_COUNT_TO_GET_FORTY];
            case TIE_BREAK -> String.valueOf(score.getPlayerPoints(playerId));
        };
    }
}