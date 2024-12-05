<template>
  <div>
    <!-- Top Menu -->
    <nav class="top-menu">
      <button @click="goToGamesView" class="nav-button">Games</button>
      <button @click="goToStaffView" class="nav-button">Staff</button>
      <button @click="goToCustomersView" class="nav-button">Customers</button>
      <button @click="logout" class="nav-button logout-button">Logout</button>
    </nav>

    <!-- Add Category Section -->
    <div class="card create-form">
      <h2>Manage Categories</h2>
      <div class="form-group">
        <input type="text" v-model="newCategory.name" placeholder="Category Name" />
      </div>
      <div class="buttons">
        <button class="btn-primary" @click="createCategory">Add Category</button>
        <button class="btn-secondary" @click="resetForm">Clear</button>
      </div>
    </div>

    <!-- Categories Table -->
    <div class="card table-container">
      <h2>Available Categories</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Category Name</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="category in categories" :key="category.id">
            <td>{{ category.id }}</td>
            <td>{{ category.name }}</td>
            <td>
              <button class="btn-danger" @click="removeCategory(category.id)">Remove</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="categories.length === 0" class="empty-table">
        <p>No categories available.</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const axiosClient = axios.create({
	baseURL: "http://localhost:8080"
});

export default {
  name: "CategoriesView",
  data() {
    return {
      categories: [
      ],
      newCategory: {
        name: "",
      },
    };
  },
  async created() {
    try {
      const response = await axiosClient.get("/category");
      this.categories = response.data.categories;
    }
    catch (e) {
      // this.errorMessage = "Failed to create category.";
      alert("Failed to fetch categories.");
    }
  },
  methods: {
    async createCategory() {
      const categoryToCreate = {
        name: this.newCategory.name,
        description: this.newCategory.name
      };
      try { 
        const response = await axiosClient.post("/category", categoryToCreate);
        this.categories.push(response.data);
      }
      catch (e) {
        // this.errorMessage = "Failed to create category.";
        console.error("Failed to create category.");
      }
      this.resetForm();
    },
    goToGamesView() {
      this.$router.push("/games");
    },
    goToStaffView() {
      this.$router.push("/staff");
    },
    goToCustomersView() {
      this.$router.push("/customers");
    },
    logout() {
      window.location.href = "http://localhost:8087";
      sessionStorage.setItem("user", null)
    },
    // addCategory() {

    //   if (!this.newCategory.name.trim()) {
    //     alert("Category name cannot be empty.");
    //     return;
    //   }
    //   const newId = this.categories.length ? this.categories[this.categories.length - 1].id + 1 : 1;
    //   const category = { id: newId, name: this.newCategory.name.trim() };
    //   this.categories.push(category);
    //   this.resetForm();
    // },
    async removeCategory(id) {
      const url = `/category/${id}`;
      try {
        await axiosClient.delete(url);
      } catch (e) {
        alert(e.response.data.error + ". Failed to delete category (video games still attached to category)");
        return;
      }
      this.categories = this.categories.filter((category) => category.id !== id);
      alert("Category removed successfully.");
    },
    resetForm() {
      this.newCategory.name = "";
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

.form-group input {
  width: 100%;
  padding: 0.75rem;
  margin-bottom: 1rem;
  border-radius: 5px;
  border: 1px solid #ddd;
  transition: border-color 0.3s, box-shadow 0.3s;
}

.form-group input:focus {
  border-color: #2d88ff;
  box-shadow: 0 0 5px rgba(45, 136, 255, 0.5);
}

.buttons {
  display: flex;
  gap: 1rem;
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

.btn-danger {
  background: #ff6b6b;
  color: white;
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

/* Empty Table Message */
.empty-table {
  text-align: center;
  font-size: 1.25rem;
  color: #555;
}
</style>