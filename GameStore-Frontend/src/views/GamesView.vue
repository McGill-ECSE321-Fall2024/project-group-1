<template>
  <div class="games-view">
    <!-- Top Menu -->
    <nav class="top-menu">
      <button @click="goToStaffView" class="nav-button">Staff</button>
      <button @click="goToCustomersView" class="nav-button">Customers</button>
      <button @click="goToCategoriesView" class="nav-button">Categories</button>
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
        <select v-model="currentGame.status">
          <option value="Pending">Pending</option>
          <option value="Active">Active</option>
          <option value="Inactive">Inactive</option>
        </select>
        <input type="text" v-model="currentGame.category" placeholder="Category" />
      </div>
      <div class="buttons">
        <button class="btn-primary" @click="isEditing ? saveEdit() : createVideoGame()">
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
import axios from "axios";

const axiosClient = axios.create({
	baseURL: "http://localhost:8080"
});

export default {
  name: "GamesView",
  data() {
    return {
      games: [
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
      editingGame: null,
      isEditing: false,
    };
  },
  async created() {
    try {
      const response = await axiosClient.get("/videogame");
      console.log(response);
      const transformedGames = response.data.videoGames.map(game => ({
        id: game.id,
        name: game.name,
        description: game.description,
        price: game.price,
        quantity: game.quantity,
        date: game.date,
        status: game.status,
        category: game.category.name,
      }));
      console.log(transformedGames);
      this.games = transformedGames;
    }
    catch (e) {
      console.error("Failed to fetch video games.");
    }
  },
  computed: {
    currentGame() {
      return this.isEditing ? this.editingGame : this.newGame;
    },
  },
  methods: {
    goToStaffView() {
      this.$router.push("/staff");
    },
    goToCustomersView() {
      this.$router.push("/customers");
    },
    goToCategoriesView() {
      this.$router.push("/categories");
    },
    logout() {
      window.location.href = "http://localhost:8087";
    },
    async createVideoGame() {
      const allCategories = await axiosClient.get("/category");
      console.log(allCategories);

      const videoGameToCreate = {
        name: this.newGame.name,
        description: this.newGame.description,
        price: this.newGame.price,
        quantity: this.newGame.quantity,
        date: this.newGame.date,
        status: this.newGame.status,
        categoryId: this.newGame.category
      };
      try {
        const response = await axiosClient.post("/videogame", videoGameToCreate);
        // console.log(response);
        const parsedResponse = {
          id: response.data.id,
          name: response.data.name,
          description: response.data.description,
          price: response.data.price,
          quantity: response.data.quantity,
          date: response.data.date,
          status: response.data.status,
          category: response.data.category.name
        };
        this.games.push(parsedResponse);
      } catch (e) {
        console.error("Failed to create video game.");
      }
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
    async removeGame(id) {
      try {
        const url = `/videogame/${id}`
        await axiosClient.delete(url);
        const index = this.games.findIndex(game => game.id === id);
        this.games.splice(index, 1);
        console.log("Successfully removed game");
      } catch (e) {
        console.error("Failed to delete video game.")
      }
    },
    editGame(game) {
      this.isEditing = true;
      this.editingGame = { ...game }; // Clone the game object
    },
    saveEdit() {
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
  transition: border-color 0.3s, box-shadow 0.3s;
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