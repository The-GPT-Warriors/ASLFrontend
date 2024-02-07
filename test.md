<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Webcam Capture</title>
</head>
<body>

<video autoplay playsinline></video>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        navigator.mediaDevices.getUserMedia({ video: true })
            .then(function (stream) {
                var video = document.querySelector('video');
                video.srcObject = stream;
                video.onloadedmetadata = function (e) {
                    video.play();
                };
                setInterval(function () {
                    var canvas = document.createElement('canvas');
                    canvas.width = video.videoWidth;
                    canvas.height = video.videoHeight;
                    var ctx = canvas.getContext('2d');
                    ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
                    var dataURL = canvas.toDataURL('image/jpeg');
                    
                    // Display the image on the page for testing
                    var img = document.createElement('img');
                    img.src = dataURL;
                    document.body.appendChild(img);
                }, 5000);
            })
            .catch(function (err) {
                console.log('Error: ' + err);
            });
    });
</script>

</body>
</html>
