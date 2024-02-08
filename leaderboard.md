---
layout: default
title: Leaderboard
permalink: /leaderboard
---

<div id="leaderboardContainer">
  <h2>Leaderboard</h2>
  <table id="leaderboardTable">
    <thead>
      <tr>
        <th>Rank</th>
        <th>Player Name</th>
        <th>Score</th>
        <th>Most Recent Streak</th>
      </tr>
    </thead>
    <tbody>
      <!-- Static Leaderboard entries will be replaced by dynamic entries -->
    </tbody>
  </table>
  <p id="error" style="display: none; color: red;">Could not load the leaderboard. Please try again later.</p>
</div>

<script>
  // Initially display static data
  const exampleLeaderboardData = [
    { playerName: "Emu", score: 110, highestStreak: 4 },
    { playerName: "Tay Kim", score: 90, highestStreak: 9 },
    { playerName: "Ethan Tran", score: 80, highestStreak: 8 },
    { playerName: "Anthony Bazhenov", score: 70, highestStreak: 7 },
    { playerName: "Test", score: 50, highestStreak: 5 }
  ];

  function displayLeaderboard(data) {
    const leaderboardTable = document.getElementById('leaderboardTable').getElementsByTagName('tbody')[0];
    leaderboardTable.innerHTML = ''; // Clear existing entries
    data.forEach((player, index) => {
      let row = leaderboardTable.insertRow();
      let rankCell = row.insertCell(0);
      let nameCell = row.insertCell(1);
      let scoreCell = row.insertCell(2);
      let streakCell = row.insertCell(3);

      rankCell.innerHTML = index + 1;
      nameCell.innerHTML = player.playerName;
      scoreCell.innerHTML = player.score;
      streakCell.innerHTML = player.highestStreak;
    });
  }

  // Display static data immediately
  displayLeaderboard(exampleLeaderboardData);

  // Fetch dynamic data and display it
  fetch('http://localhost:8085/api/leaderboard/')
    .then(response => response.json())
    .then(data => {
      displayLeaderboard(data); // Replace static data with dynamic data
    })
    .catch(error => {
      console.error('Error fetching leaderboard:', error);
      document.getElementById('error').style.display = 'block';
    });
</script>

<style>
  #leaderboardContainer {
    text-align: center;
  }
  #leaderboardTable {
    margin: auto;
    width: 80%;
    border-collapse: collapse;
  }
  #leaderboardTable, #leaderboardTable th, #leaderboardTable td {
    border: 1px solid black;
    padding: 8px;
    text-align: left;
  }
  #leaderboardTable th {
    background-color: #f2f2f2;
  }
</style>