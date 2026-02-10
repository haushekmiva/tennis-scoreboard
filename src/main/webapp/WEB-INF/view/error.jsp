<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Error ${errorCode}</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
    <script src="<c:url value='/js/app.js'/>"></script>
    <style>
        .error-container {
            text-align: center;
            padding: 80px 20px;
        }

        .error-code {
            font-size: 180px;
            font-weight: 700;
            color: #ff8c42;
            line-height: 1;
            margin: 0;
            text-shadow: 3px 3px 6px rgba(255, 140, 66, 0.3);
        }

        .error-message {
            font-size: 32px;
            font-weight: 500;
            color: #333;
            margin: 20px 0 40px;
        }

        .error-description {
            font-size: 19px;
            color: #666;
            margin: 20px auto;
            max-width: 600px;
            line-height: 1.6;
        }

        .error-image {
            border-radius: 30px;
            margin: 40px auto;
            background: linear-gradient(135deg, #ff8c42 0%, #ffb366 100%);
            height: 300px;
            max-width: 800px;
            display: flex;
            align-items: center;
            justify-content: center;
            box-shadow: 0 10px 30px rgba(255, 140, 66, 0.2);
        }

        .error-image::before {
            content: "\26A0";
            font-size: 120px;
            opacity: 0.3;
        }

        .error-actions {
            margin-top: 50px;
            display: flex;
            gap: 20px;
            justify-content: center;
            flex-wrap: wrap;
        }

        .error-btn {
            text-decoration: none;
            display: inline-block;
        }

        .error-btn button {
            min-width: 200px;
        }

        @media (max-width: 768px) {
            .error-code {
                font-size: 120px;
            }

            .error-message {
                font-size: 24px;
            }

            .error-description {
                font-size: 17px;
            }

            .error-image {
                height: 200px;
            }

            .error-actions {
                flex-direction: column;
                align-items: center;
            }

            .error-btn {
                width: 100%;
                max-width: 300px;
            }
        }

        @media (max-width: 576px) {
            .error-code {
                font-size: 90px;
            }

            .error-message {
                font-size: 20px;
            }

            .error-image::before {
                font-size: 80px;
            }
        }
    </style>
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
        <div class="error-container">
            <h1 class="error-code">${errorDto.errorCode}</h1>
            <h2 class="error-message">${errorDto.errorMessage}</h2>
            
            <c:choose>
                <c:when test="${errorDto.errorCode == 404}">
                    <p class="error-description">
                        The page you're looking for doesn't exist. It might have been moved or deleted.
                    </p>
                </c:when>
                <c:when test="${errorDto.errorCode == 500}">
                    <p class="error-description">
                        Something went wrong on our end. Please try again later or contact support if the problem persists.
                    </p>
                </c:when>
                <c:when test="${errorDto.errorCode == 403}">
                    <p class="error-description">
                        You don't have permission to access this resource.
                    </p>
                </c:when>
                <c:otherwise>
                    <p class="error-description">
                        An unexpected error occurred. Please try again or return to the homepage.
                    </p>
                </c:otherwise>
            </c:choose>

            <div class="error-image"></div>

            <div class="error-actions">
                <a class="error-btn" href="<c:url value='/'/>">
                    <button class="btn start-match">
                        Go to Homepage
                    </button>
                </a>
                <a class="error-btn" href="<c:url value='/matches'/>">
                    <button class="btn view-results">
                        View Matches
                    </button>
                </a>
            </div>
        </div>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a> roadmap.</p>
    </div>
</footer>
</body>
</html>
