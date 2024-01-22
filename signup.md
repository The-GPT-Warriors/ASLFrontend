---
layout: default
title: Signup page
permalink: /signup
---
<style>
        body {
            background-color: #cfdbe8;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        .header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            background-color: white;
            padding: 10px;
        }

        .title {
            font-size: 36px;
            font-weight: bold;
            color: #96c9ff;
        }

        .main {
            display: flex;
            flex-direction: column;
            align-items: center;
            background-color: white;
            margin-top: 20px;
            padding: 20px;
        }

        .form-container {
            width: 100%;
            max-width: 400px;
            margin-top: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #96c9ff;
            border-radius: 5px;
            box-sizing: border-box;
        }

        button {
            background-color: #96c9ff;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
        }

        button:hover {
            background-color: #037dff;
        }

        .footer {
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: white;
            margin-top: 20px;
            padding: 10px;
        }

        .footer-text {
            font-size: 18px;
            color: #96c9ff;
        }
</style>
<html>
<body>
    <div class="main">
        <div class="form-container">
            <form>
                <input type="text" placeholder="Name" required>
                <input type="date" placeholder="Birthday" required>
                <input type="text" placeholder="Username" required>
                <input type="email" placeholder="Email" required>
                <input type="password" placeholder="Password" required>
                <button type="submit">Sign Up</button>
            </form>
        </div>
    </div>