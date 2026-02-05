package com.haushekmiva.mapper;

import com.haushekmiva.dto.FinishedMatchDto;
import com.haushekmiva.model.OngoingMatchScore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

@Mapper
public interface FinishedMatchMapper {

    FinishedMatchMapper INSTANCE = Mappers.getMapper(FinishedMatchMapper.class);

    @Mapping(target = "firstPlayerName", expression = "java(ongoingMatchScore.getFirstPlayerName())")
    @Mapping(target = "secondPlayerName", expression = "java(ongoingMatchScore.getSecondPlayerName())")
    @Mapping(target = "winnerNumber", source = ".", qualifiedByName = "getWinnerNumber")
    @Mapping(target = "setScores", expression = "java(ongoingMatchScore.getSetScores())")
    FinishedMatchDto ongoingMatchScoreToFinishedMatchDto(OngoingMatchScore ongoingMatchScore);

    @Named("getWinnerNumber")
    default PlayerNumbers getWinnerNumber(OngoingMatchScore score) {
        if (Objects.equals(score.getFirstPlayerId(), score.getWinnerId())) {
            return PlayerNumbers.FIRST;
        }

        return PlayerNumbers.SECOND;
    }
}
