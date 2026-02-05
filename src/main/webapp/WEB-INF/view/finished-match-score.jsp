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
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="#">Home</a>
                <a class="nav-link" href="#">Matches</a>
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
                    <th class="table-text">Set 1</th>
                    <th class="table-text">Set 2</th>
                    <th class="table-text">Set 3</th>
                </tr>
                </thead>
                <tbody>
                <tr class="player1">
                    <td class="table-text">${finishedMatchDto.firstPlayerName}</td>
                    <td class="table-text">${finishedMatchDto.setScores[0].firstPlayerResult}</td>
                    <td class="table-text">${finishedMatchDto.setScores[1].firstPlayerResult}</td>

                    <c:choose>
                        <c:when test="${not empty finishedMatchDto.setScores and finishedMatchDto.setScores.size() == 3}">
                            <td class="table-text">${finishedMatchDto.setScores[2].firstPlayerResult}</td>
                        </c:when>
                        <c:otherwise>
                            <td class="table-text">-</td>
                        </c:otherwise>
                    </c:choose>
                    <td class="table-text">
                        <c:if test="${finishedMatchDto.winnerNumber == 'FIRST'}">
                            <p>WINNER</p>
                        </c:if>

                    </td>
                </tr>
                <tr class="player2">
                    <td class="table-text">${finishedMatchDto.secondPlayerName}</td>
                    <td class="table-text">${finishedMatchDto.setScores[0].secondPlayerResult}</td>
                    <td class="table-text">${finishedMatchDto.setScores[1].secondPlayerResult}</td>
                    <c:choose>
                        <c:when test="${not empty finishedMatchDto.setScores and finishedMatchDto.setScores.size() == 3}">
                            <td class="table-text">${finishedMatchDto.setScores[2].secondPlayerResult}</td>
                        </c:when>
                        <c:otherwise>
                            <td class="table-text">-</td>
                        </c:otherwise>
                    </c:choose>

                    <td class="table-text">
                        <c:if test="${finishedMatchDto.winnerNumber == 'SECOND'}">
                            <p>WINNER</p>
                        </c:if>
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
