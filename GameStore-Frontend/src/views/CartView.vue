<template>
  <div class="cart-view">
    <!-- Top Menu -->
    <nav class="top-menu">
      <button @click="goToCustomerGamesView" class="nav-button">Games</button>
      <button @click="goToWishlistView" class="nav-button">Wishlist</button>
      <button @click="goToOrderHistoryView" class="nav-button">Order History</button>
      <button @click="logout" class="nav-button logout-button">Logout</button>
    </nav>

    <!-- Cart Table -->
    <div class="card table-container">
      <h2>Your Cart</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Category</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="game in cart" :key="game.id">
            <td>{{ game.id }}</td>
            <td>{{ game.name }}</td>
            <td>{{ game.description }}</td>
            <td>${{ game.price.toFixed(2) }}</td>
            <td>{{ 1 }}</td>
            <td>{{ game.category.name }}</td>
            <td>
              <button class="btn-danger" @click="removeFromCart(game)">Remove</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="cart.length === 0" class="empty-cart">
        <p>Your cart is empty. Add some games to proceed!</p>
      </div>
    </div>

    <!-- Buy Button -->
    <div v-if="cart.length > 0" class="buy-container">
      <button class="btn-buy" @click="openModal">Buy</button>
    </div>

    <!-- Modal -->
    <div v-if="isModalOpen" class="modal-overlay">
      <div class="modal">
        <h2>Purchase Summary</h2>
        <p>Please fill in your payment details to complete the purchase.
          Unfortunately, no offers are available at this time.</p>
        <div class="cart-summary">
          <h3>Items:</h3>
          <ul>
            <li v-for="game in cart" :key="game.id">
              {{ game.name }} - ${{ game.price.toFixed(2) }} x {{ 1 }}
            </li>
          </ul>
          <h3>Total Price: ${{ totalPrice.toFixed(2) }}</h3>
        </div>

        <!-- Credit Card Form -->
        <form @submit.prevent="processPayment">
          <div class="form-group">
            <label for="cardName">Cardholder Name</label>
            <input id="cardName" v-model="paymentDetails.cardName" type="text" required />
          </div>
          <div class="form-group">
            <label for="address">Address</label>
            <input id="address" v-model="paymentDetails.address" type="text" required />
          </div>
          <div class="form-group">
            <label for="cardNumber">Card Number</label>
            <input id="cardNumber" v-model="paymentDetails.cardNumber" type="text" required />
          </div>
          <div class="form-group">
            <label for="expiryDate">Expiry Date (MM/YY)</label>
            <input id="expiryDate" v-model="paymentDetails.expiryDate" type="text" required />
          </div>
          <div class="form-group">
            <label for="cvv">CVV</label>
            <input id="cvv" v-model="paymentDetails.cvv" type="text" required />
          </div>
          <button type="submit" class="btn-confirm">Confirm Payment</button>
          <button type="button" class="btn-secondary" @click="closeModal">Cancel</button>
        </form>
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
  name: "CartView",
  data() {
    return {
      cart: [],
      isModalOpen: false,
      paymentDetails: {
        cardName: "",
        cardNumber: "",
        expiryDate: "",
        cvv: "",
        address: ""
      },
    };
  },
  async created() {
    try {
      customer = (await axiosClient.get("/customer/" + JSON.parse(sessionStorage.getItem("user")).id)).data;
      sessionStorage.setItem("user", JSON.stringify(customer));
      this.cart = customer.cart;
    } catch (e) {
      alert(e.response?.data?.error);
    }
  },
  computed: {
    totalPrice() {
      return this.cart.reduce((total, game) => total + game.price * game.quantity, 0);
    },
  },
  methods: {
    goToCustomerGamesView() {
      this.$router.push("/customergames");
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
    async removeFromCart(game) {
      try {
        //(`/customer/${customer.id}/cart/${game.id}/quantity/1`);
        await axiosClient.delete(`/customer/${customer.id}/cart/${game.id}`);
      } catch (e) {
        alert(e?.response?.data?.error);
      }
      //update wishlist
      try {
        customer = (await axiosClient.get("/customer/" + JSON.parse(sessionStorage.getItem("user")).id)).data;
        sessionStorage.setItem("user", JSON.stringify(customer));
        this.cart = customer.cart;
      } catch (e) {
        alert(e.response?.data?.error);
      }
    },
    openModal() {
      this.isModalOpen = true;
    },
    closeModal() {
      this.isModalOpen = false;
      this.paymentDetails = {
        cardName: "",
        cardNumber: "",
        expiryDate: "",
        cvv: "",
        address: ""
      };
    },
    async processPayment() {
      try {
        const args = {
          address: this.paymentDetails.address,
          customerId: customer.id
        }
        throw 12312;
        //console.log(args);
        //await axiosClient.post("/customer/" + customer.id + "/order", args);
        //console.logs("past!");
        //await axiosClient.delete("/customer/" + customer.id + "/cart");
        //console.log("ALL DONE")
        //customer = (await axiosClient.get("/customer/" + JSON.parse(sessionStorage.getItem("user")).id)).data;
        //sessionStorage.setItem("user", JSON.stringify(customer));
        //this.cart = customer.cart; // Clear the cart after purchase
      } catch (e) {
        console.error(e?.response?.data?.error);
        alert("Issue in BackEnd: Post API generation of customer orders fails horribly. Only works via curl, and only if the customer_order database has been 'delete from customer_order;'ed since last generation. Tests still pass fine, but try {} catch {} fails to catch the issue with repo.save(). \nIn the meantime, use \"curl --request POST http://localhost:8080/customer/34/order --data '{\"address\":\"123 Brook Avenue\", \"customerId\":\"34\"}\' --header \'Content-Type: application/json\' \" to create customer orders FROM CUSTOMER 34's CART, for instance.");
        alert(e?.response?.data?.error)
        alert("Payment Error!");
        this.closeModal();
        return;
      }
      alert("Payment Successful!");
      this.closeModal();
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

/* Modal Styling */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal {
  background: white;
  border-radius: 10px;
  padding: 2rem;
  width: 400px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
  text-align: center;
}

ul {
  margin: 0;
  padding: 0;
  list-style: none;
}

ul li {
  margin-bottom: 0.5rem;
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

.btn-danger {
  background: #ff6b6b;
  color: white;
}

.empty-cart {
  text-align: center;
  font-size: 1.25rem;
  color: #555;
}
</style>