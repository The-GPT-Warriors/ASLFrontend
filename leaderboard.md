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
        <th>Highest Streak</th>
      </tr>
    </thead>
    <tbody>
      <!-- Leaderboard entries will be inserted here by JavaScript -->
    </tbody>
  </table>
  <p id="error" style="display: none; color: red;">Could not load the leaderboard. Please try again later.</p>
</div>

<script>
  // Example static data for demonstration
  const exampleLeaderboardData = [
    { playerName: "Emu", score: 110, highestStreak: 4 },
    { playerName: "Tay Kim", score: 90, highestStreak: 9 },
    { playerName: "Ethan Tran", score: 80, highestStreak: 8 },
    { playerName: "Anthony Bazhenov", score: 70, highestStreak: 7 },
    { playerName: "Test", score: 0, highestStreak: 0 }
  ];

  // Immediately invoke this function to render the example data
  (function renderExampleLeaderboard() {
    const leaderboardTable = document.getElementById('leaderboardTable').getElementsByTagName('tbody')[0];
    exampleLeaderboardData.forEach((player, index) => {
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
  })();
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