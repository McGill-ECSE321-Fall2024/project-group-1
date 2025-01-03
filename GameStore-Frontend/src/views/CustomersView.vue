<template>
  <div>
    <!-- Top Menu -->
    <nav class="top-menu">
      <button @click="goToGamesView" class="nav-button">Games</button>
      <button @click="goToStaffView" class="nav-button">Staff</button>
      <button @click="goToCategoriesView" class="nav-button">Categories</button>
      <button @click="logout" class="nav-button logout-button">Logout</button>
    </nav>

    <!-- Customers Table -->
    <div class="card table-container">
      <h2>Customers</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Full Name</th>
            <th>Email</th>
            <th>Phone Number</th>
            <th>Address</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="customer in customers" :key="customer.id">
            <td>{{ customer.id }}</td>
            <td>{{ customer.name }}</td>
            <td>{{ customer.email }}</td>
            <td>{{ customer.phoneNumber }}</td>
            <td>{{ customer.address }}</td>
            <td>
              <button class="btn-primary" @click="viewOrderHistory(customer)">View Order History</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="customers.length === 0" class="empty-table">
        <p>No customers available.</p>
      </div>
    </div>

    <!-- Order History Modal -->
    <div v-if="showOrderHistory" class="modal-overlay" @click.self="closeOrderHistory">
      <div class="modal">
        <h2>Order History for {{ currentCustomer.name }}</h2>
        <table>
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Games</th>
              <th>Quantities</th>
              <th>Total Price</th>
              <th>Discount</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="order in orderHistory" :key="order.id">
            <td>{{ order.id }}</td>
            <td>{{ order.purchased.name }}</td>
            <td>{{ 1 }}</td>
            <td>${{ order.price.toFixed(2) }}</td>
            <td>{{ order.discount ? order.discount + '%' : 'No Discount' }}</td>
          </tr>
          </tbody>
        </table>
        <button class="btn-secondary" @click="closeOrderHistory">Close</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080",
});

export default {
  name: "CustomersView",
  data() {
    return {
      customers: [],
      showOrderHistory: false,
      orderHistory: [],
      currentCustomer: null,
    };
  },
  async created() {
      try {
        let temp_customers = await axiosClient.get("/customer/all");
        //console.log(temp_customers.data.customers);
        this.customers = temp_customers.data.customers; 
      } catch (e) {
        alert(e?.response?.data?.error);
      }
  },
  methods: {
    goToGamesView() {
      this.$router.push("/games");
    },
    goToStaffView() {
      this.$router.push("/staff");
    },
    goToCategoriesView() {
      this.$router.push("/categories");
    },
    logout() {
      window.location.href = "http://localhost:8087";
      sessionStorage.setItem("user", null)
    },
    async viewOrderHistory(customer) {
      this.currentCustomer = customer;
      this.orderHistory = (await axiosClient.get("/order/customer/" + customer.id)).data.orders;
      this.showOrderHistory = true;
    },
    closeOrderHistory() {
      this.showOrderHistory = false;
      this.currentCustomer = null;
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

/* Card and Table Styling */
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

/* Empty Table Message */
.empty-table {
  text-align: center;
  font-size: 1.25rem;
  color: #555;
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

.modal table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
}

.modal table th,
.modal table td {
  padding: 1rem;
  text-align: left;
}

.modal table th {
  background: linear-gradient(135deg, #2d88ff, #6a5eff);
  color: white;
}
</style>