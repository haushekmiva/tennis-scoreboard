package com.haushekmiva.dto.factory;

import com.haushekmiva.dto.FinishedMatchSearchDto;
import com.haushekmiva.model.Match;

import java.util.List;

public final class FinishedMatchSearchDtoFactory {

    private FinishedMatchSearchDtoFactory() {}

    public static FinishedMatchSearchDto buildFinishedMatchSearchDto(
            int pageNumber,
            int pageCount,
            String playerName,
            List<Match> matches) {

        int prevPage = (pageNumber <= 1)
                ? pageNumber
                : pageNumber - 1;

        int nextPage = (pageNumber >= pageCount)
                ? pageNumber
                : pageNumber + 1;

        return new FinishedMatchSearchDto(
                pageCount,
                pageNumber,
                prevPage,
                nextPage,
                playerName,
                matches
        );

    }


}
