---
layout: default
title: Game
permalink: /game
---
<html>
<head>
    <title>Cosmic Carnage</title>
    <style>
        canvas {
        background-color: black;
        display: block;
        margin: 0 auto;
    }
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        h1 {
            text-align: center;
        }
        #createPlayerForm {
            background-color: #fff;
            border-radius: 5px;
            padding: 20px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        label {
            font-weight: bold;
        }
        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: grey;
            color: black;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <canvas id="gameCanvas" width="400" height="400"></canvas>
<html>
<head>
    <title>Player List</title>
</head>
<body>
    <h1>Scoreboard</h1>
    <table>
        <thead>
            <tr>
                <th>User</th>
                <th>Score</th>
            </tr>
        </thead>
    <h1>Create Player</h1>
    <form id="createPlayerForm">
        <label for="user">Username:</label>
        <input type="text" id="user" name="user" required>
        <br>
        <label for="score">Score:</label>
        <input type="number" id="score" name="score" required>
        <br>
        <input type="submit" value="Submit">
    </form>
        <tbody id="player-list">
            <!-- Player data will be inserted here -->
        </tbody>
    </table>
</body>
</html>
    <script>
        // fetch player data from our backend API
        function fetchPlayerData() {
            fetch('http://172.31.132.100:8086/api/players/')
                .then(response => response.json())
                .then(data => {
                    // player-list tbody element
                    const playerList = document.getElementById('player-list');
                    // clear any existing data
                    playerList.innerHTML = '';
                    // loop through the player data and create table rows
                    data.forEach(player => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                            <td>${player.user}</td>
                            <td>${player.score}</td>
                        `;
                        playerList.appendChild(row);
                    });
                })
                .catch(error => {
                    console.error('Error fetching player data:', error);
                });
        }
        fetchPlayerData();
        document.getElementById("createPlayerForm").addEventListener("submit", function (e) {
            e.preventDefault();
            const user = document.getElementById("user").value;
            const score = parseInt(document.getElementById("score").value);
            // Create a player object
            const playerData = {
                user: user,
                score: score
            };
            fetch('http://172.31.132.100:8086/api/players/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(playerData),
            })
            .then(response => {
                if (response.status === 201) {
                    return response.json(); // Successfully created a player
                } else {
                    return response.json().then(errorData => {
                        throw new Error(errorData.message);
                    });
                }
            })
            .then(player => {
                // Display a success message or redirect to another page
                document.getElementById("message").innerHTML = `Player ${player.user} created with score ${player.score}`;
            })
            .catch(error => {
                // Display an error message
                document.getElementById("message").innerHTML = `Error: ${error.message}`;
            });
        });
        const canvas = document.getElementById('gameCanvas');
        const ctx = canvas.getContext('2d');
        const player = {
            x: canvas.width / 2,
            y: canvas.height - 30,
            width: 30,
            height: 30,
            speed: 5
        };
        const bullets = [];
        const enemy = {
            x: canvas.width / 2,
            y: 10,
            width: 30,
            height: 30,
            speed: 2
        };
        let isGameOver = false;
        let score = 0; // Initialize the score variable
        let timeLeft = 30; // 30 seconds timer
        const playerImage = new Image();
        playerImage.src = 'https://github.com/TayKimmy/CSA_Repo/assets/107821010/28c3e277-b292-43f0-bcef-5460b19689b7';
        const enemyImage = new Image();
        enemyImage.src = 'https://github.com/TayKimmy/CSA_Repo/assets/107821010/89f4c0d6-a856-4233-bc17-ea8821bc7a10'; 
        playerImage.onload = () => {
            draw();
        };
        function drawPlayer() {
            ctx.drawImage(playerImage, player.x, player.y, player.width, player.height);
        }
        function drawEnemy() {
            ctx.drawImage(enemyImage, enemy.x, enemy.y, enemy.width, enemy.height);
        }
        function drawBullets() {
            for (let i = 0; i < bullets.length; i++) {
                ctx.beginPath();
                ctx.rect(bullets[i].x, bullets[i].y, bullets[i].width, bullets[i].height);
                ctx.fillStyle = "orange";
                ctx.fill();
                ctx.closePath();
            }
        }
        function moveBullets() {
            for (let i = 0; i < bullets.length; i++) {
                bullets[i].y -= 5;
                if (bullets[i].y < 0) {
                    bullets.splice(i, 1);
                }
            }
        }
        function checkCollision() {
            if (
                player.x < enemy.x + enemy.width &&
                player.x + player.width > enemy.x &&
                player.y < enemy.y + enemy.height &&
                player.y + player.height > enemy.y
            ) {
                isGameOver = true;
            }
            for (let i = 0; i < bullets.length; i++) {
                if (
                    bullets[i].x < enemy.x + enemy.width &&
                    bullets[i].x + bullets[i].width > enemy.x &&
                    bullets[i].y < enemy.y + enemy.height &&
                    bullets[i].y + bullets[i].height > enemy.y
                ) {
                    enemy.x = Math.random() * (canvas.width - enemy.width);
                    enemy.y = 10;
                    bullets.splice(i, 1);
                    score += 1; // Increment the score when an enemy is hit
                }
            }
        }
        function draw() {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            if (!isGameOver && timeLeft > 0) {
                drawPlayer();
                drawEnemy();
                drawBullets();
                moveBullets();
                checkCollision();
                requestAnimationFrame(draw);
                // Display the score and time on the canvas
                ctx.font = "20px Arial";
                ctx.fillStyle = "white";
                ctx.fillText("Score: " + score, 10, 30);
                ctx.fillText("Time Left: " + timeLeft + "s", 10, 60);
            } else if (!isGameOver && timeLeft === 0) {
                isGameOver = true;
                ctx.font = "30px Arial";
                ctx.fillStyle = "red";
                ctx.fillText("Time's Up!", canvas.width / 2 - 80, canvas.height / 2);
                ctx.fillText("Score: " + score, canvas.width / 2 - 60, canvas.height / 2 + 40);
            } else {
                ctx.font = "30px Arial";
                ctx.fillStyle = "red";
                ctx.fillText("Game Over", canvas.width / 2 - 80, canvas.height / 2);
                ctx.fillText("Score: " + score, canvas.width / 2 - 60, canvas.height / 2 + 40);
            }
        }
        function keyDownHandler(e) {
            if (e.key == "Right" || e.key == "ArrowRight") {
                if (player.x + player.width < canvas.width) {
                    player.x += player.speed;
                }
            } else if (e.key == "Left" || e.key == "ArrowLeft") {
                if (player.x > 0) {
                    player.x -= player.speed;
                }
            } else if (e.key == " ") {
                bullets.push({
                    x: player.x + player.width / 2 - 2.5,
                    y: player.y,
                    width: 5,
                    height: 10
                });
            }
        }
        document.addEventListener("keydown", keyDownHandler, false);
        draw();
        const timerInterval = setInterval(function() {
            if (!isGameOver && timeLeft > 0) {
                timeLeft--;
            } else {
                clearInterval(timerInterval);
            }
        }, 1000); // Decrease timeLeft by 1 second every 1000ms (1 second)
    </script>
</body>
</html>
