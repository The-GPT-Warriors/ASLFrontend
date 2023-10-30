<!-- ---
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
            background-color: gray;
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
            color: black;
        }
        input[type="submit"] {
            background-color: gray;
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
    <h1>Create Player</h1>
    <form id="createPlayerForm">
        <label for="user">Username:</label>
        <input type="text" id="user" name="user" required>
        <br>
        <label for "score">Score:</label>
        <input type="number" id="score" name="score" required>
        <br>
        <input type="submit" value="Submit">
    </form>
    <script>
        // Fetch player data from our backend API
        function fetchPlayerData() {
            fetch('http://172.31.132.100:8086/api/players/')
                .then(response => response.json())
                .then(data => {
                    // player-list tbody element
                    const playerList = document.getElementById('player-list');
                    // Clear any existing data
                    playerList.innerHTML = '';
                    // Loop through the player data and create table rows
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
                        throw an Error(errorData.message);
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
        const canvas = document.getElementById('gameCanvas'); // Create a canvas element
        const ctx = canvas.getContext('2d'); // 2D rendering context of the canvas
        const player = { // Define player properties
            x: canvas.width / 2,
            y: canvas.height - 40,
            width: 40,
            height: 40,
            speed: 10,
            angle: 0,
        };
        const bullets = []; // Create an array to store bullets
        const enemy = { // Define enemy properties
            x: canvas.width / 2,
            y: 0,
            width: 40,
            height: 40,
            speed: 2
        };
        let isGameOver = false;
        let score = 0;
        let timeLeft = 30;
        const playerImage = new Image();
        playerImage.src = 'https://github.com/TayKimmy/CSA_Repo/assets/107821010/28c3e277-b292-43f0-bcef-5460b19689b7'; // Use a spaceship image for the player
        const enemyImage = new Image();
        enemyImage.src = 'https://github.com/Ant11234/student/assets/40652645/b7e15072-5d72-48f9-ad28-fdb227f2b20d'; // Use a UFO image for the enemy
        playerImage.onload = () => {
            draw(); // Execute draw() when the player's image is loaded
        };
        // Draw the player's spaceship
        function drawPlayer() {
            ctx.save(); // Save the current context state
            ctx.translate(player.x + player.width / 2, player.y + player.height / 2); // Move the context origin to the center of the player
            ctx.rotate(player.angle); // Rotate the context based on the player's angle
            ctx.drawImage(playerImage, -player.width / 2, -player.height / 2, player.width, player.height); // Draw the player centered
            ctx.restore(); // Restore the context to the previous state
        }
        // Draw the enemy's spaceship
        function drawEnemy() {
            ctx.drawImage(enemyImage, enemy.x, enemy.y, enemy.width, enemy.height);
        }
        // Draw bullets on the canvas
        function drawBullets() {
            for (let i = 0; i < bullets.length; i++) {
                ctx.beginPath();
                ctx.rect(bullets[i].x, bullets[i].y, bullets[i].width, bullets[i].height);
                ctx.fillStyle = "orange";
                ctx.fill();
                ctx.closePath();
            }
        }
        // Move bullets upward
        function moveBullets() {
            for (let i = 0; i < bullets.length; i++) {
                bullets[i].y -= 5;
                if (bullets[i].y < 0) {
                    bullets.splice(i, 1);
                }
            }
        }
        // Check for collisions between player, bullets, and enemy
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
        // Draw on the canvas
        function draw() {
            ctx.clearRect(0, 0, canvas.width, canvas.height); // Clear the canvas
            if (!isGameOver && timeLeft > 0) { // If it is not game over and time is left
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
        // Function to handle user input (keyboard presses)
        function keyDownHandler(e) {
            if (e.key == "Right" || e.key == "ArrowRight") { // If the right key is pushed
                if (player.x + player.width < canvas.width) { // If the player is not on the very far right
                    player.x += player.speed; // Move with defined speed to the right
                    player.angle = 0; // Set the angle to face right
                }
            } else if (e.key == "Left" || e.key == "ArrowLeft") { // If the left key is pushed
                if (player.x > 0) { // If the player is not on the very far left
                    player.x -= player.speed; // Move with defined speed to the left
                    player.angle = Math.PI; // Set the angle to face left
                }
            } else if (e.key == " ") { // If space is pushed
                bullets.push({ // Show bullets
                    x: player.x + player.width / 2 - 2.5, // From the middle of the player's icon
                    y: player.y, // From player's height
                    width: 5,
                    height: 10
                });
            }
        }
        // Add keydown event listener to the document
        document.addEventListener("keydown", keyDownHandler, false);
        // Initial call to the draw() function
        draw();
        // Set up a timer interval to decrement timeLeft
        const timerInterval = setInterval(function() {
            if (!isGameOver && timeLeft > 0) { // If time and game are not over
                timeLeft--; // Decrease time left
            } else {
                clearInterval(timerInterval); // Clear the interval if the game is over
            }
        }, 1000); // 1000 ms - making sure it is every 1 second
    </script>
</body>
</html> -->
