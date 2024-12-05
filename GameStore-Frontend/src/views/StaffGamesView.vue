<template>
  <div class="staff-games-view">
    <!-- Top Menu -->
    <nav class="top-menu">
      <button @click="logout" class="nav-button logout-button">Logout</button>
    </nav>

    <!-- Add/Edit Game Section -->
    <div class="card create-form">
      <h2>{{ isEditing ? "Edit Game" : "Add New Game" }}</h2>
      <div class="form-group">
        <input type="text" v-model="currentGame.name" placeholder="Game Name" />
        <textarea v-model="currentGame.description" placeholder="Description"></textarea>
        <input type="number" step="0.01" v-model="currentGame.price" placeholder="Price ($)" />
        <input type="number" v-model="currentGame.quantity" placeholder="Quantity" />
        <input type="date" v-model="currentGame.date" placeholder="Release Date" />
        <select v-model="currentGame.category" class="styled-select">
          <option value="" disabled>Select Category</option>
          <option v-for="category in categories" :key="category.id" :value="category.name">
            {{ category.name }}
          </option>
        </select>
      </div>
      <div class="buttons">
        <button class="btn-primary" @click="isEditing ? saveEdit() : addGame()">
          {{ isEditing ? "Save Changes" : "Add Game" }}
        </button>
        <button class="btn-secondary" @click="isEditing ? cancelEdit() : resetNewGameForm()">
          {{ isEditing ? "Cancel" : "Clear" }}
        </button>
      </div>
    </div>

    <!-- Games Table -->
    <div class="card table-container">
      <h2>Available Games</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Date</th>
            <th>Status</th>
            <th>Category</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="game in games" :key="game.id">
            <td>{{ game.id }}</td>
            <td>{{ game.name }}</td>
            <td>{{ game.description }}</td>
            <td>${{ game.price.toFixed(2) }}</td>
            <td>{{ game.quantity }}</td>
            <td>{{ game.date }}</td>
            <td>{{ game.status }}</td>
            <td>{{ game.category }}</td>
            <td>
              <button class="btn-primary" @click="editGame(game)">Edit</button>
              <button class="btn-danger" @click="removeGame(game.id)">Remove</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: "StaffGamesView",
  data() {
    return {
      games: [
        {
          id: 1,
          name: "The Legend of Zelda",
          description: "Adventure game",
          price: 59.99,
          quantity: 10,
          date: "2023-11-01",
          status: "Active",
          category: "Action",
        },
        {
          id: 2,
          name: "Minecraft",
          description: "Sandbox game",
          price: 19.99,
          quantity: 50,
          date: "2023-10-10",
          status: "Active",
          category: "Sandbox",
        },
      ],
      categories: [
        { id: 1, name: "Action" },
        { id: 2, name: "Adventure" },
        { id: 3, name: "RPG" },
      ],
      newGame: {
        name: "",
        description: "",
        price: null,
        quantity: null,
        date: "",
        status: "Pending",
        category: "",
      },
      isEditing: false,
      editingGame: null,
    };
  },
  computed: {
    currentGame() {
      return this.isEditing ? this.editingGame : this.newGame;
    },
  },
  methods: {
    logout() {
      window.location.href = "http://localhost:8087";
      sessionStorage.setItem("user", null)
    },
    addGame() {
      if (!this.currentGame.category) {
        alert("Please select a category.");
        return;
      }
      const newId = this.games.length > 0 ? Math.max(...this.games.map((g) => g.id)) + 1 : 1;
      const newGame = { id: newId, ...this.newGame, status: "Pending" };

      this.games.push(newGame);
      this.resetNewGameForm();
    },
    resetNewGameForm() {
      this.newGame = {
        name: "",
        description: "",
        price: null,
        quantity: null,
        date: "",
        status: "Pending",
        category: "",
      };
    },
    removeGame(id) {
      this.games = this.games.filter((game) => game.id !== id);
    },
    editGame(game) {
      this.isEditing = true;
      this.editingGame = { ...game };
    },
    saveEdit() {
      if (!this.currentGame.category) {
        alert("Please select a category.");
        return;
      }
      const index = this.games.findIndex((g) => g.id === this.editingGame.id);
      if (index !== -1) {
        this.games[index] = { ...this.editingGame };
        this.cancelEdit();
      }
    },
    cancelEdit() {
      this.isEditing = false;
      this.editingGame = null;
    },
  },
};
</script>

<style scoped>
/* Top Menu */
.top-menu {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-bottom: 1rem;
  padding: 1rem;
  background: linear-gradient(135deg, #2d88ff, #6a5eff);
  color: white;
}

.nav-button {
  background: white;
  color: #2d88ff;
  padding: 0.5rem 1rem;
  font-size: 1rem;
  font-weight: bold;
  border-radius: 5px;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
}

.nav-button:hover {
  background-color: #f2f2f2;
  transform: scale(1.1);
}

.logout-button {
  background: #ff6b6b;
  color: white;
}

.logout-button:hover {
  background: #ff4c4c;
}

/* Card Design */
.card {
  background: white;
  border-radius: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 2rem;
  margin-bottom: 2rem;
}

h2 {
  font-size: 1.75rem;
  margin-bottom: 1rem;
  color: #333;
}

/* Form Styling */
.form-group input,
.form-group textarea,
.form-group select {
  width: 100%;
  padding: 0.75rem;
  margin-bottom: 1rem;
  border-radius: 5px;
  border: 1px solid #ddd;
  background: #f9f9f9;
  transition: border-color 0.3s, box-shadow 0.3s;
}

.form-group select {
  appearance: none;
  background: url('data:image/svg+xml;charset=US-ASCII,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 4 5"><path fill="gray" d="M2 0L0 2h4zm0 5L0 3h4z"/></svg>')
    no-repeat right 0.75rem center;
  background-size: 0.75rem 0.75rem;
}

.form-group input:focus,
.form-group textarea:focus,
.form-group select:focus {
  border-color: #2d88ff;
  box-shadow: 0 0 5px rgba(45, 136, 255, 0.5);
}

.buttons {
  display: flex;
  gap: 1rem;
}

button {
  padding: 0.75rem 1.5rem;
  border-radius: 5px;
  font-weight: bold;
  cursor: pointer;
  border: none;
  transition: transform 0.2s, background-color 0.3s;
}

button:hover {
  transform: scale(1.05);
}

.btn-primary {
  background: #2d88ff;
  color: white;
}

.btn-secondary {
  background: #f0f0f0;
  color: #333;
}

.btn-danger {
  background: #ff6b6b;
  color: white;
}

/* Table Styling */
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
}

table th,
table td {
  padding: 1rem;
  text-align: left;
}

th {
  background: linear-gradient(135deg, #2d88ff, #6a5eff);
  color: white;
}

tbody tr:hover {
  background: #f9f9f9;
}
</style>