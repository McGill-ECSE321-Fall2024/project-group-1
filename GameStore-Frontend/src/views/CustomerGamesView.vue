<template>
  <div class="customer-games-view">
    <!-- Top Menu -->
    <nav class="top-menu">
      <button @click="goToCartView" class="nav-button">Cart</button>
      <button @click="goToWishlistView" class="nav-button">Wishlist</button>
      <button @click="goToOrderHistoryView" class="nav-button">Order History</button>
      <button @click="logout" class="nav-button logout-button">Logout</button>
    </nav>

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
          <tr v-for="game in truegames" :key="game.id">
            <td>{{ game.id }}</td>
            <td>{{ game.name }}</td>
            <td>{{ game.description }}</td>
            <td>${{ game.price.toFixed(2) }}</td>
            <td>{{ game.quantity }}</td>
            <td>{{ game.date }}</td>
            <td>{{ game.status }}</td>
            <td>{{ game.category.name }}</td>
            <td>
              <button class="btn-primary" @click="addToCart(game)">Add to Cart</button>
              <button class="btn-secondary" @click="addToWishlist(game)">Add to Wishlist</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from "axios";

//export customer = null;
const axiosClient = axios.create({
  baseURL: "http://localhost:8080",
});

let customer = null//JSON.parse(sessionStorage.getItem("user")).data;
export default {
  name: "CustomerGamesView",
  data() {
    return {
      truegames: [],
      cart: [],
      wishlist: [],
    };
  },
  async created() {
    try {
      const response = await axiosClient.get("/videogame");
      console.log(response.data.videoGames);
      this.truegames = response.data.videoGames;
    } catch (e) {
      alert(e.response?.data?.error || "Failed to fetch games.");
    }
  },
  methods: {
    goToCartView() {
      this.$router.push("/cart");
    },
    goToWishlistView() {
      this.$router.push("/wishlist");
    },
    goToOrderHistoryView() {
      this.$router.push("/orderhistory");
    },
    logout() {
      window.location.href = "http://localhost:8087";
      sessionStorage.setItem("user", null)
    },
    async addToCart(game) {
      try {
        console.log(`/customer/${customer.id}/cart/${game.id}/quantity/1`);
        await axiosClient.put(`/customer/${customer.id}/cart/${game.id}/quantity/1`);
      } catch (e) {
        console.log(e);
        alert(e?.response?.data?.error);
      }
    },
    async addToWishlist(game) {
      try {
        await axiosClient.put(`/customer/${customer.id}/wishlist/${game.id}`);
      } catch (e) {
        alert(e?.response?.data?.error);
      }
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

/* Table Styling */
.table-container table {
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

button {
  padding: 0.5rem 1rem;
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
</style>