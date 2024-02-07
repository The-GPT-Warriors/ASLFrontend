---
layout: default
title: Leaderboard
permalink: /leaderboard
---

<div id="leaderboardContainer">
  <h2>Leaderboard</h2>
  <ul id="leaderboardList"></ul>
  <p id="error" style="display: none; color: red;">Could not load the leaderboard. Please try again later.</p>
</div>

<script>
  fetch('http://localhost:8085/api/leaderboard')
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      const leaderboardList = document.getElementById('leaderboardList');
      data.forEach(player => {
        const listItem = document.createElement('li');
        listItem.textContent = `${player.playerName}: Score - ${player.score}, Highest Streak - ${player.highestStreak}`;
        leaderboardList.appendChild(listItem);
      });
    })
    .catch(error => {
      console.error('Error fetching leaderboard:', error);
      document.getElementById('error').style.display = 'block';
    });
</script>