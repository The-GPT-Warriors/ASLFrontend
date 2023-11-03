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
            display: none; /* Set it to 'none' to hide initially */
            margin: 0 auto;
        }
        #header {
            margin: 0 auto;
        }
        #header th {
            background-color: #8F0CD9;
            color: #fff;
            padding: 10px;
            width: 200px;
            text-align: center;
        }
        #header td {
            padding: 8px;
            text-align: center;
            border: 1px solid #ccc;
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
        label {
            font-weight: bold;
        }
        input[type="text"] {
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
        #message {
            text-align: center;
        }
        canvas, #playAgainButton {
            display: none;
        }
        #playAgainButton {
            display: none;
            background-color: #662d91;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold; 
        }
        #createPlayerForm {
            background-color: #662d91; 
            border-radius: 5px;
            padding: 20px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            display: block;
        }
    </style>
</head>
<body>
    <canvas id="gameCanvas" width="600" height="425"></canvas>
    <h1>Create Player</h1>
    <form id="createPlayerForm">
        <label for="user">Username:</label>
        <input type="text" id="user" name="user" required>
        <br>
        <input type="submit" value="Start Game">
    </form>
    <p id="message"></p>
    <button id="playAgainButton" style="display: none;">Play Again</button>
    <br>
    <button id="create-btn">Add to Leaderboard</button>
    <br>
    <div id="timer" style="position: absolute; top: 10px; right: 10px; font-size: 20px; color: white;"></div>
    <br>
    <center><button id="generate-btn">Generate Leaderboard</button></center>
    <h1>Scoreboard</h1>
    <table id="header" style="margin: 0 auto;">
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
    <script>
        let timeLeft = 60;
        const generateButton = document.getElementById("generate-btn");
        const createButton = document.getElementById("create-btn");
        const input = document.getElementById("user");
        const leaderboardTable = document.getElementById('player-list');
        function startGame() {
            canvas.style.display = 'block';
            document.querySelector('h1').style.display = 'block';
            document.querySelector('table').style.display = 'block';
            // Clear the canvas
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            // Reset game variables
            isGameOver = false;
            score = 0;
            timeLeft = 60;
            // Reset player position
            player.x = canvas.width / 2;
            player.y = canvas.height - 40;
            // Add any other variable resets here
            // Start the game loop
            draw();
        }
        // Display the "Create Player" form when the Start Game button is clicked
        document.getElementById("createPlayerForm").addEventListener("submit", function (e) {
            e.preventDefault();
            const user = document.getElementById("user").value;
            // Display a success message
            document.getElementById("createPlayerForm").style.display = "none"; // Hide the form
            // Show the canvas, heading, and table
            canvas.style.display = 'block';
            document.querySelector('h1').style.display = 'block';
            document.querySelector('table').style.display = 'block';
            // Clear the canvas
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            // Call your game start function here
            startGame();
            spawnMiniBoss();
        });
        // Initialize the canvas and game parameters
        const canvas = document.getElementById('gameCanvas'); // create canvas element
        const ctx = canvas.getContext('2d'); // 2d rendering of canvas
        const player = { // define player properties
            x: canvas.width / 2,
            y: canvas.height - 40,
            width: 40,
            height: 40,
            speed: 15,
            angle: 0
        };
        const bullets = []; // create an array to store bullets
        const enemy = { // define enemy properties
            x: canvas.width / 2,
            y: 0,
            width: 40,
            height: 40,
            speed: 2
        };
        let isGameOver = false;
        let score = 0;
        const playerImage = new Image();
        playerImage.src = 'https://github.com/TayKimmy/CSA_Repo/assets/107821010/28c3e277-b292-43f0-bcef-5460b19689b7'; // making the player a spaceship image
        const enemyImage = new Image();
        enemyImage.src = 'https://github.com/Ant11234/student/assets/40652645/b7e15072-5d72-48f9-ad28-fdb227f2b20d'; // making the enemy a ufo image
        const minibossImage = new Image();
        minibossImage.src = 'https://github.com/Ant11234/student/assets/40652645/3bf0b840-7b21-428d-858b-3c668db352f6'; // making a miniboss
        playerImage.onload = () => {
            draw(); // execute draw() when the player's image is loaded
        };
        // draw the player's spaceship
        function drawPlayer() {
            ctx.save(); // Save the canvas state
            ctx.translate(player.x + player.width / 2, player.y + player.height / 2); // Translate to the center of the player
            ctx.rotate(player.angle); // Rotate the canvas based on player's angle
            ctx.drawImage(playerImage, -player.width / 2, -player.height / 2, player.width, player.height); // Draw the player image
            ctx.restore();
        }
        // draw the enemy's spaceship
        function drawEnemy() {
            ctx.drawImage(enemyImage, enemy.x, enemy.y, enemy.width, enemy.height);
        }
        // draw bullets on the canvas
        function drawBullets() {
            for (let i = 0; i < bullets.length; i++) {
                ctx.beginPath();
                ctx.rect(bullets[i].x, bullets[i].y, bullets[i].width, bullets[i].height);
                ctx.fillStyle = "orange";
                ctx.fill();
                ctx.closePath();
            }
        }
        // move bullets upward
        function moveBullets() {
            for (let i = 0; i < bullets.length; i++) {
                bullets[i].y -= 5;
                if (bullets[i].y < 0) {
                    bullets.splice(i, 1);
                }
            }
        }
        // check for collisions between player, bullets, and enemy
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
                    score += 10; // increment the score when an enemy is hit
                }
            }
        }
        const miniBoss = {
            x: 0,
            y: 0,
            width: 100,
            height: 100,
            speed: 15,
        };
        // Function to spawn the mini-boss
        // Function to spawn the mini-boss
        function spawnMiniBoss() {
            let minDistance = 300; // Minimum distance between player and mini-boss
            let validSpawn = false;
            while (!validSpawn) {
                miniBoss.x = Math.random() * (canvas.width - miniBoss.width);
                miniBoss.y = Math.random() * (canvas.height - miniBoss.height);
                // Calculate the distance between player and mini-boss
                let distance = Math.sqrt(Math.pow(player.x - miniBoss.x, 2) + Math.pow(player.y - miniBoss.y, 2));
                if (distance >= minDistance) {
                    validSpawn = true;
                }
            }
        }
        // Draw the mini-boss
        function drawMiniBoss() {
            ctx.save();
            ctx.translate(miniBoss.x + miniBoss.width / 2, miniBoss.y + miniBoss.height / 2);
            ctx.rotate(Math.PI); // Rotate by 180 degrees (π radians)
            ctx.drawImage(minibossImage, -miniBoss.width / 2, -miniBoss.height / 2, miniBoss.width, miniBoss.height);
            ctx.restore();
        }
        // Update the mini-boss
        function updateMiniBoss() {
            miniBoss.x += (Math.random() - 0.5) * miniBoss.speed;
            miniBoss.y += (Math.random() - 0.5) * miniBoss.speed;
            miniBoss.x = Math.max(0, Math.min(canvas.width - miniBoss.width, miniBoss.x));
            miniBoss.y = Math.max(0, Math.min(canvas.height - miniBoss.height, miniBoss.y));
        }
        setInterval(() => {
            miniBoss.speed += 1; // Increase miniboss speed by 1 (you can adjust the increment as needed)
        }, 5000);
        // Check for collisions with the mini-boss
        function checkMiniBossCollision() {
            if (
                player.x < miniBoss.x + miniBoss.width &&
                player.x + player.width > miniBoss.x &&
                player.y < miniBoss.y + miniBoss.height &&
                player.y + player.height > miniBoss.y
            ) {
                isGameOver = true;
            }
            for (let i = 0; i < bullets.length; i++) {
                if (
                    bullets[i].x < miniBoss.x + miniBoss.width &&
                    bullets[i].x + bullets[i].width > miniBoss.x &&
                    bullets[i].y < miniBoss.y + miniBoss.height &&
                    bullets[i].y + bullets[i].height > miniBoss.y
                ) {
                    spawnMiniBoss(); // Respawn the mini-boss
                    bullets.splice(i, 1);
                    score += 20; // Increment the score when mini-boss is hit
                }
            }
        }
        const playAgainButton = document.getElementById("playAgainButton");
        playAgainButton.addEventListener("click", function () {
            // Reset game variables
            isGameOver = false;
            score = 0;
            // Clear the canvas
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            // Hide the "Create Player" form and show the canvas
            document.getElementById("createPlayerForm").style.display = "block"; // Show the "Create Player" form
            canvas.style.display = 'none'; // Hide the canvas
            document.querySelector('h1').style.display = 'none'; // Hide the heading
            document.querySelector('table').style.display = 'none'; // Hide the table
            input.value = ""; // Clear the input field
            input.disabled = false; // Enable the input field
            // Call your game start function again
        });
        // draw on the canvas
        function draw() {
            ctx.clearRect(0, 0, canvas.width, canvas.height); // clear the canvas
            if (!isGameOver && timeLeft > 0) { // if it is not game over and time is left
                drawPlayer();
                drawEnemy();
                drawMiniBoss(); // Draw the mini-boss
                updateMiniBoss();
                drawBullets();
                moveBullets();
                checkCollision();
                checkMiniBossCollision();
                requestAnimationFrame(draw);
                // display the score and time on the canvas
                ctx.font = "20px Arial";
                ctx.fillStyle = "white";
                ctx.fillText("Score: " + score, 10, 30);
                ctx.fillText("Time Left: " + timeLeft + "s", 10, 60);
                // console.log(timeLeft);
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
            } if (isGameOver) {
                // Display game over message and score
                ctx.font = "30px Arial";
                ctx.fillStyle = "red";
                ctx.fillText("Game Over", canvas.width / 2 - 80, canvas.height / 2);
                ctx.fillText("Score: " + score, canvas.width / 2 - 60, canvas.height / 2 + 40);
                // Show the "Play Again" button
                console.log("Setting button to block");
                playAgainButton.style.display = "block";
                console.log("Button style: " + playAgainButton.style.display);
            }
        }
        const playerImages = [
            'https://github.com/Ant11234/student/assets/40652645/15b4e3de-f9c4-4c70-adc9-d696cffd6ad7',
            'https://github.com/Ant11234/student/assets/40652645/97854bf1-907b-4a51-9de1-7064b7f296da',
            'https://github.com/Ant11234/student/assets/40652645/2122f367-aea5-4a97-bb5d-ace685929f77'
        ];
        let currentPlayerImageIndex = 0;
        function changePlayerImage() {
            // Update the player image source with the next image in the array
            playerImage.src = playerImages[currentPlayerImageIndex];
            // Increment the index or reset it to 0 if it exceeds the array length
            currentPlayerImageIndex = (currentPlayerImageIndex + 1) % playerImages.length;
        }
        // Call the changePlayerImage function every second (1000 milliseconds)
        setInterval(changePlayerImage, 10000);
        // function to handle user input (keyboard presses)
        const keysPressed = {}; // Keep track of keys that are currently pressed
        document.addEventListener("keydown", (e) => {
            keysPressed[e.key] = true; // Mark the key as pressed
            handleKeyPresses();
        });
        document.addEventListener("keyup", (e) => {
            delete keysPressed[e.key]; // Mark the key as released
            handleKeyPresses();
        });
        function handleKeyPresses() {
            if (keysPressed["ArrowRight"] || keysPressed["Right"]) {
                if (player.x + player.width < canvas.width) {
                    player.x += player.speed;
                    player.angle = Math.PI / 2;
                }
            } else if (keysPressed["ArrowLeft"] || keysPressed["Left"]) {
                if (player.x > 0) {
                    player.x -= player.speed;
                    player.angle = -Math.PI / 2;
                }
            }
            if (keysPressed[" "] || keysPressed["Space"]) {
                bullets.push({
                    x: player.x + player.width / 2 - 2.5,
                    y: player.y,
                    width: 5,
                    height: 10
                });
            }
        }
        // add keydown event listener to the document
        // Initial call to the draw() function
        draw();
        // Set up a timer interval to decrement timeLef
        const timerDisplay = document.getElementById('timer');
        function updateTimerDisplay() {
            timerDisplay.innerText = timeLeft + 's';
        }
        const timerInterval = setInterval(function() {
            if (!isGameOver && timeLeft > 0) {
                timeLeft--; // Decrease time left
                updateTimerDisplay(); // Update the timer display
            } else {
                clearInterval(timerInterval); // Clear interval if the game is over
            }
        }, 1000);
        createButton.addEventListener("click", () => {
            username = input.value;
            const postData = {
                leaderboard: username,
                score: score,
            };
            fetch(`http://localhost:8027/api/leaderboard/post/${username}/${score}`, {
                    method: 'POST',
                    mode: 'cors',
                    cache: 'default',
                    credentials: 'same-origin',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer my-token'
                    },
                    body: JSON.stringify(postData)
            })
            .then(response => response.json())
            .then(data => {
                //
            })
            .catch(error => {
                console.error("Error: " + error);
            });
        });
        generateButton.addEventListener("click", () => {
            fetch("http://localhost:8027/api/leaderboard/")
            .then(response => response.json())
            .then(data => {
                leaderboardTable.innerHTML = ""; // Clear previous data
                data.forEach(item => {
                    const row = leaderboardTable.insertRow(-1);
                    const cell1 = row.insertCell(0);
                    const cell2 = row.insertCell(1);
                    cell1.innerHTML = item.leaderboard;
                    cell2.innerHTML = item.score;
                    console.log(item.score);
                });
            })
            .catch(error => {
                console.error("Error:", error);
            });
        });
    </script>
</body>
</html>