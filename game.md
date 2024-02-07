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
    </div>
    <div class="main">
      <div class="camera"></div>
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
    'images/J.png',
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
    'images/Y.png',
    'images/Z.png'
  ];
  
  function startGame() {
      initializeWebcam(() => {
          setTimeout(displayRandomASLSymbol, 1000); // Start by showing webcam, then display symbol after 2 seconds
      });
  }
  
  function initializeWebcam(callback) {
      const video = document.createElement('video');
      const constraints = { video: true };
      navigator.mediaDevices.getUserMedia(constraints)
        .then((stream) => {
          video.srcObject = stream;
          video.onloadedmetadata = () => {
            video.play();
          };
          document.querySelector('.camera').innerHTML = '';
          document.querySelector('.camera').appendChild(video);
          callback(); // Call the callback function to continue the game flow
        })
        .catch((err) => {
          console.error(err);
        });
  }
  
  function displayRandomASLSymbol() {
      const randomIndex = Math.floor(Math.random() * aslSymbols.length);
      const symbolPath = aslSymbols[randomIndex];
      // Replace webcam with image
      document.querySelector('.camera').innerHTML = `<img src="${symbolPath}" alt="ASL Symbol" style="width: 100%;">`;
      setTimeout(() => {
          initializeWebcam(captureImageForRecognition); // Switch back to webcam and prepare for gesture capture
      }, 2000); // Display symbol for 5 seconds
  }
  
  function captureImageForRecognition() {
      console.log("Ready to capture user's gesture");
      // Logic to capture the gesture goes here
      // For now, simulate recognition result after a delay
      setTimeout(() => {
          checkRecognitionResult({ isCorrect: Math.random() > 0.5 }); // Randomly decide if the gesture was correct
      }, 5000); // Give user time to replicate symbol
  }
  
  function checkRecognitionResult(result) {
      if (result.isCorrect) {
          score += 10;
          streak += 1;
      } else {
          streak = 0; // Reset streak if wrong
      }
      updateScoreboard();
      setTimeout(startGame, 1000); // Wait a bit before starting the next round
  }
  
  function updateScoreboard() {
      document.getElementById('score').textContent = score;
      document.getElementById('streak').textContent = streak;
  }
  
  startGame();
</script>
  <style>
  body, .container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  .camera {
    width: 640px;
    height: 480px;
    background: #000;
    margin: 20px;
    display: flex; /* Ensure the camera div can properly align its children */
    justify-content: center; /* Center-align children horizontally */
    align-items: center; /* Center-align children vertically */
    overflow: hidden; /* Hide parts of the image that exceed the div dimensions */
  }

  .camera img {
    width: 100%;  /* Match the width of the camera div */
    height: 100%; /* Match the height of the camera div */
    object-fit: contain; /* Ensure the image maintains its aspect ratio */
  }

  .scoreboard {
    margin: 20px;
  }

  /* Add more styles as needed */
</style>
</body>
