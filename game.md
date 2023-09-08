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
        function drawPlayer() {
            ctx.beginPath();
            ctx.rect(player.x, player.y, player.width, player.height);
            ctx.fillStyle = "purple";
            ctx.fill();
            ctx.closePath();
        }
        function drawEnemy() {
            ctx.beginPath();
            ctx.rect(enemy.x, enemy.y, enemy.width, enemy.height);
            ctx.fillStyle = "red";
            ctx.fill();
            ctx.closePath();
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
            if (!isGameOver) {
                drawPlayer();
                drawEnemy();
                drawBullets();
                moveBullets();
                checkCollision();
                requestAnimationFrame(draw);
                // Display the score on the canvas
                ctx.font = "20px Arial";
                ctx.fillStyle = "white";
                ctx.fillText("Score: " + score, 10, 30);
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
    </script>
</body>
</html>
