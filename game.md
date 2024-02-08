---
layout: default
title: ASL Recognition Game
permalink: /game
---

<body>
    <div class="container">
    <div class="header">
      <img src="https://github.com/The-GPT-Warriors/ai-front/assets/109186517/8f289636-ccc8-402f-9bf0-1f466ef96436" alt="Logo" class="logo">
      <h1 class="title">ASL Recognition Game</h1>
      <div id="timer" style="position: absolute; top: 20px; right: 20px; font-size: 20px; color: black; display: none;">60</div>
    </div>
    <div class="main">
      <div class="camera" id="camera">
        <!-- Start Game button will be initially visible -->
        <button id="startButton" style="font-size: 20px; padding: 10px 20px;">Start Game</button>
      </div>
      <div id="prediction">Predictions from the model will go here</div>
    </div>
    <div class="scoreboard">
      <p>Score: <span id="score">0</span></p>
      <p>Streak: <span id="streak">0</span></p>
    </div>
    <div class="footer">
      <p class="footer-text">Powered by The GPT Warriors</p>
    </div>
  </div>

<script>
  let score = 0;
  let streak = 0;
  let gameTimerId;
  let isGameStarted = false;

  const aslSymbols = [
    'images/A.png',
    'images/B.png',
    'images/C.png',
    'images/D.png',
    'images/E.png',
    'images/F.png',
    'images/G.png',
    'images/H.png',
    'images/I.png',
    'images/K.png',
    'images/L.png',
    'images/M.png',
    'images/N.png',
    'images/O.png',
    'images/P.png',
    'images/Q.png',
    'images/R.png',
    'images/S.png',
    'images/T.png',
    'images/U.png',
    'images/V.png',
    'images/W.png',
    'images/X.png',
    'images/Y.png'
  ];

  const startButton = document.getElementById('startButton');
  startButton.addEventListener('click', startGameFlow);

  function startGameFlow() {
    startButton.style.display = 'none'; // Hide the start button
    startInitialCountdown();
  }


  function startInitialCountdown() {
    let countdown = 3;
    updateCameraDisplay(`<span style="color: white; font-size: 48px;">${countdown}</span>`);
    let countdownTimerId = setInterval(() => {
      countdown--;
      if (countdown > 0) {
        updateCameraDisplay(`<span style="color: white; font-size: 48px;">${countdown}</span>`);
      } else {
        clearInterval(countdownTimerId);
        if (!isGameStarted) {
          isGameStarted = true;
          startGameTimer();
          displayRandomASLSymbol();
        }
      }
    }, 1000);
  }

  function displayRandomASLSymbol() {
    const randomIndex = Math.floor(Math.random() * aslSymbols.length);
    const symbolPath = aslSymbols[randomIndex];
    document.querySelector('.camera').innerHTML = `<img src="${symbolPath}" alt="ASL Symbol" style="width: 640px; height: 480px;">`;
    setTimeout(initializeWebcam, 500); // Wait 2 seconds before initializing the webcam
  }

  function initializeWebcam() {
    const video = document.createElement('video');
    video.style.width = '640px';
    video.style.height = '480px';
    const constraints = { video: true };
    navigator.mediaDevices.getUserMedia(constraints)
      .then((stream) => {
        video.srcObject = stream;
        video.onloadedmetadata = () => {
          video.play();
          document.querySelector('.camera').innerHTML = '';
          document.querySelector('.camera').appendChild(video);
          setTimeout(captureGestureAndCheckResult, 1500); // 2 seconds to capture user's gesture
        };
      })
      .catch((err) => {
        console.error('Error initializing webcam:', err);
      });
  }

  function captureGestureAndCheckResult() {
    // Placeholder for gesture capture logic
    console.log("Capture user's gesture and check result");
    const isCorrect = Math.random() > 0.5; // Simulated result
    checkRecognitionResult({ isCorrect });
  }

  function checkRecognitionResult(result) {
    if (result.isCorrect) {
      score += 10;
      streak += 1;
    } else {
      streak = 0;
    }
    updateScoreboard();
    if (isGameStarted) {
      setTimeout(displayRandomASLSymbol, 1000); // Loop to display another symbol
    }
  }

  function updateScoreboard() {
    document.getElementById('score').textContent = score;
    document.getElementById('streak').textContent = streak;
  }

  function startGameTimer() {
    let timeLeft = 60;
    document.getElementById('timer').style.display = 'block';
    document.getElementById('timer').textContent = timeLeft;
    gameTimerId = setInterval(() => {
      timeLeft--;
      document.getElementById('timer').textContent = timeLeft;
      if (timeLeft <= 0) {
        clearInterval(gameTimerId);
        endGame();
      }
    }, 1000);
  }

  function startGame() {
    if (!isGameStarted) {
      isGameStarted = true;
      startInitialCountdown();
      // startGameTimer(); // Consider when to start the game timer
    }
  }

  function endGame() {
    isGameStarted = false; // Prevent new symbols from being displayed
    updateCameraDisplay(`<button id="restartButton" style="font-size: 20px; padding: 10px 20px;">Restart Game</button>`);
    const restartButton = document.getElementById('restartButton');
    restartButton.addEventListener('click', () => {
      score = 0; // Reset score
      streak = 0; // Reset streak
      updateScoreboard(); // Update scoreboard to show reset values
      startGameFlow(); // Restart the game flow
    });
    alert("Time's up! Your score is " + score + " with a streak of " + streak + ".");
  }

  function updateCameraDisplay(content) {
    const cameraDiv = document.querySelector('.camera');
    cameraDiv.innerHTML = content;
  }

  function updateScoreAndStreak(score, streak) {
  let userName = sessionStorage.getItem('userName');
  console.log(`Updating score for ${userName} with score: ${score}, streak: ${streak}`); // Diagnostic log
  fetch(`http://localhost:8085/api/leaderboard/update/${userName}/${score}/${streak}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${sessionStorage.getItem('token')}`
    },
    body: JSON.stringify({ score, streak })
  })
  .then(response => {
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
  })
  .then(data => {
    console.log('Score updated', data);
    // Call to refresh the leaderboard here
  })
  .catch(error => console.error('Error updating score:', error));
}

</script>
  <style>
  body, .container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

   #startButton {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    cursor: pointer;
  }


  .camera {
    width: 640px;
    height: 480px;
    background: #000;
    margin: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
  }

  .camera img {
    width: 100%;
    height: 100%;
    object-fit: contain;
  }

  .scoreboard {
    margin: 20px;
  }

  #timer {
    position: absolute;
    top: 20px;
    right: 20px;
    font-size: 20px;
    background-color: #fff;
    padding: 5px;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  }
  /* Additional styles as needed */
</style>
</body>