---
layout: default
title: Sign up
permalink: /signup
---
<body>
    <div class="main">
        <div class="form-container">
            <form id="signup_form">
                <input type="text" placeholder="Name" id="name" required>
                <input type="date" placeholder="Birthday" id="dob" required>
                <input type="text" placeholder="Username" id="username" required>
                <input type="email" placeholder="Email" id="email" required>
                <input type="password" placeholder="Password" id="password" required>
                <button type="submit">Sign Up</button>
            </form>
        </div>
    </div>
    <script>
        document.getElementById("signup_form").addEventListener("submit", function (event) {
            event.preventDefault();
            signup_user();
        });
        function signup_user() {
            var requestOptions = {
                method: 'POST',
                mode: 'cors', // Corrected 'mod' to 'mode'
                cache: 'no-cache'
            };
            let email = document.getElementById("email").value;
            let password = document.getElementById("password").value;
            let name = document.getElementById("name").value;
            let dobInput = document.getElementById("dob").value;
            let dob = formateDate(dobInput);
            let username = document.getElementById("username").value;
            let requestURL = `https://asl.stu.nighthawkcodingsociety.com/api/person/post?email=${email}&password=${password}&name=${name}&dob=${dob}&username=${username}`;
            console.log(requestURL);
            fetch(requestURL, requestOptions)
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => Promise.reject(text));
                }
                return response.text(); // Or response.json() if your server returns JSON
            })
            .then(data => {
                alert("Signup Complete");
                window.location.href = "https://the-gpt-warriors.github.io/ASLFrontend/login";
            })
            .catch(error => {
                alert('An unexpected error occurred: ' + error);
            });
        }
        function formatDate(dateString) {
            const date = new Date(dateString);
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            const year = date.getFullYear();
            return `${month}-${day}-${year}`;
        }
    </script>
</body>
</html>