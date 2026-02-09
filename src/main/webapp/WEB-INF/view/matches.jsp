<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">

    <script src="<c:url value='/js/app.js'/>"></script>
</head>

<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="<c:url value='/images/menu.png'/>" alt="Logo" class="logo">
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
        <h1>Matches</h1>
        <form method="get" action="<c:url value='/matches'/>">
            <div class="input-container">
                <input name="filter_by_player_name" class="input-filter"
                       value="<c:out value='${finishedMatchSearchDto.playerName}' default='' />"
                       placeholder="Filter by name" type="text"/>
                <div>
                    <button type="submit" class="btn-filter">Search</button>
                </div>
            </div>
        </form>
        <table class="table-matches">
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            <c:forEach var="match" items="${finishedMatchSearchDto.matches}">
                <tr>
                    <td>${match.firstPlayer.name}</td>
                    <td>${match.secondPlayer.name}</td>
                    <td><span class="winner-name-td">${match.winner.name}</span></td>
                </tr>
            </c:forEach>
        </table>

        <c:if test="${finishedMatchSearchDto.pageCount != 0}">
            <div class="pagination">

                <c:url value="/matches" var="prevPagePath">
                    <c:param name="page" value="${finishedMatchSearchDto.prevPage}"/>
                    <c:if test="${not empty finishedMatchSearchDto.playerName}">
                        <c:param name="filter_by_player_name" value="${finishedMatchSearchDto.playerName}"/>
                    </c:if>
                </c:url>

                <c:url value="/matches" var="nextPagePath">
                    <c:param name="page" value="${finishedMatchSearchDto.nextPage}"/>
                    <c:if test="${not empty finishedMatchSearchDto.playerName}">
                        <c:param name="filter_by_player_name" value="${finishedMatchSearchDto.playerName}"/>
                    </c:if>
                </c:url>

                <a class="prev" href="${prevPagePath}"> < </a>
                <c:forEach var="i" begin="1" end="${finishedMatchSearchDto.pageCount}">

                    <c:url value="/matches" var="pagePath">
                        <c:param name="page" value="${i}"/>
                        <c:if test="${not empty finishedMatchSearchDto.playerName}">
                            <c:param name="filter_by_player_name" value="${finishedMatchSearchDto.playerName}"/>
                        </c:if>
                    </c:url>

                    <c:choose>
                        <c:when test="${i == finishedMatchSearchDto.currentPage}">
                            <a class="num-page current" href="${pagePath}">${i}</a>
                        </c:when>
                        <c:otherwise>
                            <a class="num-page" href="${pagePath}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <a class="next" href="${nextPagePath}"> > </a>
            </div>
        </c:if>
    </div>

</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>
