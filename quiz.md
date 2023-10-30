---
layout: default
title: Quiz
permalink: /quiz
---
<html>
<head>
    <title>Quiz Application</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h1>Quiz Application</h1>
    <div id="quiz-container">
        <div id="question-container">
            <p id="question-text"></p>
        </div>
        <div id="answers-container">
            <button class="answer-btn" id="answerA"></button>
            <button class="answer-btn" id="answerB"></button>
            <button class="answer-btn" id="answerC"></button>
            <button class="answer-btn" id="answerD"></button>
        </div>
    </div>
    <button id="next-btn">Next</button>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            var currentQuestion = 0;
            var questions = [];
            // Function to load a new question
            function loadQuestion(index) {
                if (index < questions.length) {
                    var question = questions[index];
                    $("#question-text").text(question.question);
                    $("#answerA").text("A. " + question.answerA);
                    $("#answerB").text("B. " + question.answerB);
                    $("#answerC").text("C. " + question.answerC);
                    $("#answerD").text("D. " + question.answerD);
                }
            }
            // Function to handle the answer button click
            $(".answer-btn").click(function() {
                var selectedAnswer = $(this).attr("id").slice(-1).toLowerCase();
                // Send the selected answer to the backend
                $.post("/api/quiz/updateScore/" + questions[currentQuestion].id, { selectedAnswer: selectedAnswer }, function(response) {
                    // Handle the response from the server if needed
                });
                currentQuestion++;
                loadQuestion(currentQuestion);
            });
            // Function to load the next question
            $("#next-btn").click(function() {
                currentQuestion++;
                loadQuestion(currentQuestion);
            });
            // Load questions from the backend when the page loads
            $.get("/api/quiz/", function(data) {
                questions = data;
                loadQuestion(currentQuestion);
            });
        });
    </script>
</body>
</html>
