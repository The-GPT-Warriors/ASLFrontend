<body>
  <div class="container">
    <div class="header">
      <img src="https://github.com/The-GPT-Warriors/ai-front/assets/109186517/8f289636-ccc8-402f-9bf0-1f466ef96436" alt="Logo" class="logo">
      <h1 class="title">ASL Recognition</h1>
    </div>
    <div class="main">
      <div class="camera"></div>
      <input class="text">Predictions from the model will go here</input>
    </div>
    <div class="footer">
      <p class="footer-text">Powered by The GPT Warriors</p>
    </div>
  </div>
  <script>
    const video = document.createElement('video');
    const constraints = {
      video: true
    };
    navigator.mediaDevices.getUserMedia(constraints)
      .then((stream) => {
        video.srcObject = stream;
        video.onloadedmetadata = () => {
          video.play();
        };
        document.querySelector('.camera').appendChild(video);
      })
      .catch((err) => {
        console.log(err);
      });
  </script>
</body>
