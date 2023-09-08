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
    <script>
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
        const enemies = [];
        let isGameOver = false;
        let score = 0;
        let enemyCanShoot = true;
        let isKeyDown = false;
        function drawPlayer() {
            ctx.beginPath();
            ctx.rect(player.x, player.y, player.width, player.height);
            ctx.fillStyle = "purple";
            ctx.fill();
            ctx.closePath();
        }
        function drawBullet(bullet) {
            ctx.beginPath();
            ctx.rect(bullet.x, bullet.y, bullet.width, bullet.height);
            ctx.fillStyle = "orange";
            ctx.fill();
            ctx.closePath();
        }
        function drawEnemy(enemy) {
            ctx.beginPath();
            ctx.rect(enemy.x, enemy.y, enemy.width, enemy.height);
            ctx.fillStyle = "red";
            ctx.fill();
            ctx.closePath();
        }
        function moveBullets() {
            for (let i = 0; i < bullets.length; i++) {
                bullets[i].y -= 5;
                if (bullets[i].y < 0) {
                    bullets.splice(i, 1);
                }
            }
        }
        function moveEnemies() {
            for (let i = 0; i < enemies.length; i++) {
                enemies[i].x += enemies[i].speedX;
                if (enemies[i].x < 0 || enemies[i].x + enemies[i].width > canvas.width) {
                    enemies[i].speedX *= -1; // Reverse direction when hitting canvas boundary
                }
                // Add a condition to limit enemy firing frequency
                if (enemyCanShoot && Math.random() < 0.01) {
                    bullets.push({
                        x: enemies[i].x + enemies[i].width / 2 - 2.5,
                        y: enemies[i].y + enemies[i].height,
                        width: 5,
                        height: 10
                    });
                    enemyCanShoot = false;
                    setTimeout(() => {
                        enemyCanShoot = true;
                    }, 2000);
                }
            }
        }
        function checkCollisions() {
            for (let i = 0; i < bullets.length; i++) {
                for (let j = 0; j < enemies.length; j++) {
                    const bullet = bullets[i];
                    const enemy = enemies[j];
                    if (
                        bullet.x < enemy.x + enemy.width &&
                        bullet.x + bullet.width > enemy.x &&
                        bullet.y < enemy.y + enemy.height &&
                        bullet.y + bullet.height > enemy.y
                    ) {
                        bullets.splice(i, 1);
                        enemies.splice(j, 1);
                        score += 1;
                    }
                }
            }
            for (let i = 0; i < enemies.length; i++) {
                const enemy = enemies[i];
                if (
                    player.x < enemy.x + enemy.width &&
                    player.x + player.width > enemy.x &&
                    player.y < enemy.y + enemy.height &&
                    player.y + player.height > enemy.y
                ) {
                    isGameOver = true;
                }
            }
        }
        function draw() {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            if (!isGameOver) {
                drawPlayer();
                moveBullets();
                moveEnemies();
                checkCollisions();
                for (const bullet of bullets) {
                    drawBullet(bullet);
                }
                for (const enemy of enemies) {
                    drawEnemy(enemy);
                }
                requestAnimationFrame(draw);
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
            if (e.key === "Right" || e.key === "ArrowRight") {
                if (player.x + player.width < canvas.width) {
                    player.x += player.speed;
                }
            } else if (e.key === "Left" || e.key === "ArrowLeft") {
                if (player.x > 0) {
                    player.x -= player.speed;
                }
            } else if (e.key === " " && !isKeyDown) {
                bullets.push({
                    x: player.x + player.width / 2 - 2.5,
                    y: player.y,
                    width: 5,
                    height: 10
                });
                isKeyDown = true;
            }
        }
        function keyUpHandler(e) {
            if (e.key === " ") {
                isKeyDown = false;
            }
        }
        document.addEventListener("keydown", keyDownHandler, false);
        document.addEventListener("keyup", keyUpHandler, false);
        // Initialize the first enemy
        enemies.push({
            x: Math.random() * (canvas.width - enemy.width),
            y: 10,
            width: 30,
            height: 30,
            speedX: 2
        });
        draw();
    </script>
</body>
</html>