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

    // подумай о том что делает геттеры
    @Mapping(target = "firstPlayerName", expression = "java(score.getFirstPlayerName())")
    @Mapping(source = ".", target = "firstPlayerPoints", qualifiedByName = "getFirstPlayerPoints")
    @Mapping(target = "firstPlayerGames", expression = "java(String.valueOf(score.getFirstPlayerGames()))")
    @Mapping(target = "firstPlayerSets", expression = "java(String.valueOf(score.getFirstPlayerSets()))")
    @Mapping(target = "secondPlayerScore.playerName", expression = "java(score.getSecondPlayerName())")
    @Mapping(source = ".", target = "secondPlayerPoints", qualifiedByName = "getSecondPlayerPoints")
    @Mapping(target = "secondPlayerGames", expression = "java(String.valueOf(score.getSecondPlayerGames()))")
    @Mapping(target = "secondPlayerSets", expression = "java(String.valueOf(score.getSecondPlayerSets()))")
    OngoingMatchScoreDto ongoingMatchScoreToDto(OngoingMatchScore ongoingMatchScore);

    @Named("getFirstPlayerPoints")
    default String getFirstPlayerPoints(OngoingMatchScore score) {
        return getPlayerPoints(score, PlayerNumbers.FIRST);
    }
    @Named("getSecondPlayerPoints")
    default String getSecondPlayerPoints(OngoingMatchScore score) {
        return getPlayerPoints(score, PlayerNumbers.SECOND);
    }

    private String getPlayerPoints(OngoingMatchScore score, PlayerNumbers playerNumber) {
        String[] values = {"0", "15", "30", "40"};
        PointDisplayState pointDisplayState = score.getPointDisplayState();

        Long playerId = switch (playerNumber) {
            case FIRST -> score.getFirstPlayerId();
            case SECOND -> score.getSecondPlayerId();
        };

        return switch (pointDisplayState) {
            case NORMAL -> values[score.getPlayerPoints(playerId)];
            case DEUCE -> values[3];
            case ADVANTAGE_FIRST -> (playerNumber == PlayerNumbers.FIRST) ? "AD" : values[3];
            case ADVANTAGE_SECOND -> (playerNumber == PlayerNumbers.SECOND) ? "AD" : values[3];
        };
    }
}