<template>
  <div class="wishlist-view">
    <!-- Top Menu -->
    <nav class="top-menu">
      <button @click="goToCustomerGamesView" class="nav-button">Games</button>
      <button @click="goToCartView" class="nav-button">Cart</button>
      <button @click="goToOrderHistory" class="nav-button">Order History</button>
      <button @click="logout" class="nav-button logout-button">Logout</button>
    </nav>

    <!-- Wishlist Table -->
    <div class="card table-container">
      <h2>Your Wishlist</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Category</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="game in wishlist" :key="game.id">
            <td>{{ game.id }}</td>
            <td>{{ game.name }}</td>
            <td>{{ game.description }}</td>
            <td>${{ game.price.toFixed(2) }}</td>
            <td>{{ game.category }}</td>
            <td>
              <button class="btn-secondary" @click="addToCart(game)">Add to Cart</button>
              <button class="btn-danger" @click="removeFromWishlist(game)">Remove</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="wishlist.length === 0" class="empty-wishlist">
        <p>Your wishlist is empty. Add some games to keep track of your favorites!</p>
      </div>
    </div>
  </div>
</template>

<script>
let customer = null;
export default {
  name: "WishlistView",
  data() {
    customer = JSON.parse(sessionStorage.getItem("user")).data
    return {
      wishlist: [],
      cart: []
    };
  },
  methods: {
    goToCustomerGamesView() {
      this.$router.push("/customergames");
    },
    goToCartView() {
      this.$router.push("/cart");
    },
    goToOrderHistory() {
      this.$router.push("/orderhistory");
    },
    logout() {
      window.location.href = "http://localhost:8087";
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
    removeFromWishlist(game) {
      this.wishlist = this.wishlist.filter((item) => item.id !== game.id);
      alert(`${game.name} removed from the wishlist.`);
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

/* Empty Wishlist Message */
.empty-wishlist {
  text-align: center;
  font-size: 1.25rem;
  color: #555;
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

.btn-secondary {
  background: #4caf50;
  color: white;
}

.btn-secondary:hover {
  background: #45a049;
}

.btn-danger {
  background: #ff6b6b;
  color: white;
}

.btn-danger:hover {
  background: #ff4c4c;
}
</style>