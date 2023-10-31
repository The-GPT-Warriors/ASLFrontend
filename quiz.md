---
layout: default
title: Quiz
permalink: /quiz
---
<style>
    .selected {
        background-color: rgb(12, 141, 243);
    }
    
</style>
<html>
<head>
        <title>Space Quiz</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <h1><center>Space Quiz</center></h1>
    <div id="quiz-container">
        <div id="question-container"><center>
            <p id="question-text"></p>
        </center></div>
        <div id="answers-container"><center>
            <button class="answer-btn" id="answerA"></button>
            <button class="answer-btn" id="answerB"></button>
            <button class="answer-btn" id="answerC"></button>
            <button class="answer-btn" id="answerD"></button>
        </center></div>
    </div>
    <center><p id="score-display"></p></center>
    <br>
    <center>
        <button id="next-btn">Next</button>
    </center>
    <br>
    <div id="username-input"><center>
        <input type="text" id="username" placeholder="Enter your username">
        <button id="generate-btn">Generate</button>
        </center></div>
    <br>
    <h1><center>Leadeboard</center></h1>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        const apiUrl = "http://localhost:8027/api/quiz/";
        const updateScoreUrl = "http://localhost:8027/api/quiz/addScore/";
        const options = {
            method: 'GET',
            mode: 'cors',
            cache: 'default',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json'
            }
        };
        let score = 0;
        let questions = [];
        let currentQuestionIndex = 0;
        let correctAnswers = [];
        const questionContainer = document.getElementById("question-text");
        const answerButtons = document.querySelectorAll(".answer-btn");
        const nextButton = document.getElementById("next-btn");
        const usernameInput = document.getElementById("username");
        const generateButton = document.getElementById("generate-btn");
        function loadQuestion(questionIndex) {
            nextButton.disabled = true;
            answerButtons.forEach(button => button.classList.remove("selected"));
            const question = questions[questionIndex];
            questionContainer.textContent = question.question;
            answerButtons.forEach((button, index) => {
                button.textContent = String.fromCharCode(65 + index) + ". " + question.choices[index];
            });
        }
        function handleAnswerClick(event) {
            nextButton.disabled = false;
            let temp;
            answerButtons.forEach(button => button.classList.remove("selected"));
            event.target.classList.add("selected");
            answerButtons.forEach((button, index) => {
                const choice = String.fromCharCode(65 + index).toLowerCase();
                if (event.target === button) {
                    temp = choice;
                }
            });
            const answerChoice = temp
            const currentCorrectAnswer = correctAnswers[currentQuestionIndex];
            if (answerChoice === currentCorrectAnswer) {
                score += 10;
            }
        }
        function updateScoreDisplay() {
            const scoreDisplay = document.getElementById("score-display");
            scoreDisplay.textContent = "Your score is " + score + "/50!";
        }
        function error(err) {
            console.error(err);
        }
        fetch(apiUrl, options)
            .then(response => {
                if (response.status !== 200) {
                    error('GET API response failure: ' + response.status);
                    return;
                }
                response.json().then(data => {
                    const questionData = data.slice(0, 5);
                    const answerChoices = data.slice(5, 10);
                    questions = questionData.map((question, index) => ({
                        question: question,
                        choices: answerChoices[index].split(', '),
                    }));
                    correctAnswers = answerChoices.map(choice => choice.charAt(choice.length - 1));
                    loadQuestion(currentQuestionIndex);
                });
            })
            .catch(err => {
                error(err + ": " + apiUrl);
            });
        answerButtons.forEach(button => {
            button.addEventListener("click", handleAnswerClick);
        });
        nextButton.addEventListener("click", () => {
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.length) {
                loadQuestion(currentQuestionIndex);
            } else {
                questionContainer.textContent = "Quiz completed!";
                updateScoreDisplay();
                answerButtons.forEach(button => button.style.display = "none");
                nextButton.style.display = "none";
            }
        });
        generateButton.addEventListener("click", () => {
            const username = usernameInput.value;
            fetch()
        })
    </script>
</body>
</html>
