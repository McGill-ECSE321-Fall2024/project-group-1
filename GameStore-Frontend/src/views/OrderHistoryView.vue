<template>
  <div class="order-history-view">
    <!-- Top Menu -->
    <nav class="top-menu">
      <button @click="goToCustomerGamesView" class="nav-button">Games</button>
      <button @click="goToCartView" class="nav-button">Cart</button>
      <button @click="goToWishlistView" class="nav-button">Wishlist</button>
      <button @click="logout" class="nav-button logout-button">Logout</button>
    </nav>

    <!-- Order History Table -->
    <div class="card table-container">
      <h2>Your Order History</h2>
      <table>
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Video Games</th>
            <th>Quantities</th>
            <th>Total Price</th>
            <th>Discount Applied</th>
            <th>Review</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in orderHistory" :key="order.id">
            <td>{{ order.id }}</td>
            <td>
              <ul>
                <li v-for="game in order.games" :key="game.name">{{ game.name }}</li>
              </ul>
            </td>
            <td>
              <ul>
                <li v-for="game in order.games" :key="game.name">{{ game.quantity }}</li>
              </ul>
            </td>
            <td>${{ order.totalPrice.toFixed(2) }}</td>
            <td>{{ order.discount ? order.discount + '%' : 'No Discount' }}</td>
            <td>
              <button class="btn-primary" @click="openReviewModal(order.games)">New Review</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="orderHistory.length === 0" class="empty-history">
        <p>You have no orders yet. Browse games and start shopping!</p>
      </div>
    </div>

    <!-- Review Modal -->
    <div v-if="showReviewModal" class="modal-overlay" @click.self="closeReviewModal">
      <div class="modal">
        <h2>New Review</h2>
        <div class="form-group">
          <label for="game-select">Select Game</label>
          <select id="game-select" v-model="selectedGame">
            <option v-for="game in reviewableGames" :key="game.name" :value="game.name">
              {{ game.name }}
            </option>
          </select>
        </div>
        <div class="form-group">
          <label for="rating-select">Rating</label>
          <select id="rating-select" v-model="review.rating">
            <option value="1">1 Star</option>
            <option value="2">2 Stars</option>
            <option value="3">3 Stars</option>
            <option value="4">4 Stars</option>
            <option value="5">5 Stars</option>
          </select>
        </div>
        <div class="form-group">
          <label for="review-comment">Comment</label>
          <textarea id="review-comment" v-model="review.comment" placeholder="Write your review here..."></textarea>
        </div>
        <div class="buttons">
          <button class="btn-primary" @click="submitReview">Submit Review</button>
          <button class="btn-secondary" @click="closeReviewModal">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080",
});

let customer = null;
export default {
  name: "OrderHistoryView",
  data() {
    return {
      orderHistory: [],
      showReviewModal: false,
      reviewableGames: [],
      selectedGame: "",
      review: {
        rating: "",
        comment: "",
      },
    };
  },
  async created() {
    try {
      customer = (await axiosClient.get("/customer/" + JSON.parse(sessionStorage.getItem("user")).data.id)).data;
      console.log(customer);
      this.orderHistory = customer.order;
    } catch (e) {
      alert(e.response?.data?.error);
    }
  },
  methods: {
    goToCustomerGamesView() {
      this.$router.push("/customergames");
    },
    goToCartView() {
      this.$router.push("/cart");
    },
    goToWishlistView() {
      this.$router.push("/wishlist");
    },
    logout() {
      window.location.href = "http://localhost:8087";
      sessionStorage.setItem("user", null)
    },
    openReviewModal(games) {
      this.reviewableGames = games;
      this.selectedGame = games[0]?.name || "";
      this.showReviewModal = true;
    },
    closeReviewModal() {
      this.showReviewModal = false;
      this.selectedGame = "";
      this.review = {
        rating: "",
        comment: "",
      };
    },
    submitReview() {
      if (!this.selectedGame || !this.review.rating) {
        alert("Please select a game and provide a rating.");
        return;
      }
      alert(
        `Review submitted for ${this.selectedGame}:\nRating: ${this.review.rating} Star(s)\nComment: ${this.review.comment}`
      );
      this.closeReviewModal();
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

/* Modal Styles */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal {
  background: white;
  padding: 2rem;
  border-radius: 15px;
  width: 50%;
  max-width: 600px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

.modal h2 {
  margin-bottom: 1rem;
}

.form-group {
  margin-bottom: 1rem;
}

textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 5px;
  resize: none;
}

textarea:focus {
  border-color: #2d88ff;
  box-shadow: 0 0 5px rgba(45, 136, 255, 0.5);
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