<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Match Score</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Mono:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">

    <script src="<c:url value='/js/app.js'/>"></script>
</head>
<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="<c:url value='images/menu.png'/>" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="<c:url value='/'/>">Home</a>
                <a class="nav-link" href="<c:url value='/matches'/>">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Current match</h1>
        <div class="current-match-image"></div>
        <section class="score">
            <table class="table">
                <thead class="result">
                <tr>
                    <th class="table-text">Player</th>
                    <th class="table-text">Sets</th>
                    <th class="table-text">Games</th>
                    <th class="table-text">Points</th>
                </tr>
                </thead>
                <tbody>
                <tr class="player1">
                    <td class="table-text">${ongoingMatchScoreDto.firstPlayerName}</td>
                    <td class="table-text">${ongoingMatchScoreDto.firstPlayerSets}</td>
                    <td class="table-text">${ongoingMatchScoreDto.firstPlayerGames}</td>
                    <td class="table-text">${ongoingMatchScoreDto.firstPlayerPoints}</td>
                    <td class="table-text">
                        <form method="post" action="">
                            <input type="hidden" name="playerId" value="${ongoingMatchScoreDto.firstPlayerId}" />
                            <button type="submit" class="score-btn">Score</button>
                        </form>
                    </td>
                </tr>
                <tr class="player2">
                    <td class="table-text">${ongoingMatchScoreDto.secondPlayerName}</td>
                    <td class="table-text">${ongoingMatchScoreDto.secondPlayerSets}</td>
                    <td class="table-text">${ongoingMatchScoreDto.secondPlayerGames}</td>
                    <td class="table-text">${ongoingMatchScoreDto.secondPlayerPoints}</td>
                    <td class="table-text">
                        <form method="post" action="">
                            <input type="hidden" name="playerId" value="${ongoingMatchScoreDto.secondPlayerId}" />
                            <button type="submit" class="score-btn">Score</button>
                        </form>
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a> roadmap.</p>
    </div>
</footer>
</body>
</html>
