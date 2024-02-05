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
      // Array of ASL symbol image paths or data
      // Example: 'path/to/image_for_A.png'
    ];

    function startGame() {
        displayRandomASLSymbol();
        setTimeout(switchToWebcam, 5000); // Display symbol for 5 seconds
    }

    function displayRandomASLSymbol() {
        const randomIndex = Math.floor(Math.random() * aslSymbols.length);
        const symbolPath = aslSymbols[randomIndex];
        document.querySelector('.camera').innerHTML = `<img src="${symbolPath}" alt="ASL Symbol" style="width: 100%;">`;
    }

    function switchToWebcam() {
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
            setTimeout(captureImageForRecognition, 5000); // Give user time to replicate symbol
          })
          .catch((err) => {
            console.error(err);
          });
    }

    function captureImageForRecognition() {
        // Placeholder for capturing the image and sending it to the recognition API
        console.log("Capture image and send for recognition");
        // Simulate recognition result
        checkRecognitionResult({ isCorrect: true }); // Placeholder for actual API call
    }

    function checkRecognitionResult(result) {
        if (result.isCorrect) {
            score += 10;
            streak += 1;
        } else {
            streak = 0; // Reset streak if wrong
        }
        updateScoreboard();
        startGame(); // Start the next round
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
    }

    .scoreboard {
      margin: 20px;
    }

    /* Add more styles as needed */
  </style>
</body>
