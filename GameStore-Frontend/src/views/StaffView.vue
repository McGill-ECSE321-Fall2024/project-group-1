<template>
  <div>
    <!-- Top Menu -->
    <nav class="top-menu">
      <button @click="goToGamesView" class="nav-button">Games</button>
      <button @click="goToCustomersView" class="nav-button">Customers</button>
      <button @click="goToCategoriesView" class="nav-button">Categories</button>
      <button @click="logout" class="nav-button logout-button">Logout</button>
    </nav>

    <!-- Create/Edit Staff Section -->
    <div class="card create-form" v-if="!state.editMode">
      <h2>Staff</h2>
      <h3>Add New Staff Member</h3>
      <input type="text" v-model="state.newStaff.name" placeholder="Username" />
      <input type="email" v-model="state.newStaff.email" placeholder="Email" />
      <input type="password" v-model="state.newStaff.password" placeholder="Password" />
      <div class="buttons">
        <button class="btn-primary" @click="addStaff">Add Staff</button>
        <button class="btn-secondary" @click="resetNewStaffForm">Clear</button>
      </div>
    </div>

    <!-- Edit Staff Section -->
    <div class="card create-form" v-if="state.editMode">
      <h2>Edit Staff Member</h2>
      <input type="text" v-model="state.editStaff.name" placeholder="Username" />
      <input type="email" v-model="state.editStaff.email" placeholder="Email" />
      <input type="password" v-model="state.editStaff.password" placeholder="Password" />
      <div class="buttons">
        <button class="btn-primary" @click="saveEdit">Save Changes</button>
        <button class="btn-secondary" @click="cancelEdit">Cancel</button>
      </div>
    </div>

    <!-- Staff Table -->
    <div class="card table-container">
      <h2>Staff List</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="staff in state.staffList" :key="staff.id">
            <td>{{ staff.id }}</td>
            <td>{{ staff.username }}</td>
            <td>{{ staff.email }}</td>
            <td>
              <button class="btn-primary" @click="editStaff(staff)">Edit</button>
              <button class="btn-danger" @click="removeStaff(staff.id)">Remove</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import axios from "axios";
import { onMounted } from 'vue'


const axiosClient = axios.create({
  baseURL: "http://localhost:8080",
});

const fetchStaffList = async () => {
  let response = null;
  try {
    response = await axiosClient.get("/staff/all");
    console.log(response.data.customers);
    state.staffList = response.data.customers;
  } catch (e) {
    alert(e)
  }
  console.log(response);
}


const state = reactive({
  staffList: [
  ],
  newStaff: {
    name: '',
    email: '',
    password: '',
  },
  editMode: false,
  editStaff: {},
});

const goToGamesView = () => {
  window.location.href = '/games';
};

const goToCustomersView = () => {
  window.location.href = '/customers';
};

const goToCategoriesView = () => {
  window.location.href = '/categories';
};

const logout = () => {
  window.location.href = 'http://localhost:8087';
  sessionStorage.setItem("user", null)
};

const addStaff = async () => {

  const staffToCreate = { 
    username: state.newStaff.name,
    password: state.newStaff.password,
    email: state.newStaff.email,
  }
  try {
    await axiosClient.post('/staff', staffToCreate);
  } catch (e) {
    alert(e);
  }
  fetchStaffList();
  resetNewStaffForm();
};

const resetNewStaffForm = () => {
  state.newStaff = {
    name: '',
    email: '',
    password: '',
  };
};

const removeStaff = async (id) => {
  try {
    const url = `/staff/${id}`;
    await axiosClient.delete(url);
    fetchStaffList();
  } catch (e) {
    alert(e);
  }

  //state.staffList = state.staffList.filter((staff) => staff.id !== id);
};
let cur_edited = -1;
const editStaff = (staff) => {
  state.editMode = true;
  state.editStaff = { ...staff };
  cur_edited = staff.id;
};

const saveEdit = async () => {
  const staffToCreate = { 
    username: state.editStaff.name,
    password: state.editStaff.password,
    email: state.editStaff.email,
  }
  try {
    await axiosClient.put('/staff/' + cur_edited, staffToCreate);
  } catch (e) {
    alert(e);
  }
  fetchStaffList();
  resetNewStaffForm();
  state.editMode = false;
  state.editStaff = {};
};

const cancelEdit = () => {
  state.editMode = false;
  state.editStaff = {};
};

onMounted(() => {
  fetchStaffList();
});
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
  padding: 1.5rem;
  border-radius: 15px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 2rem;
}

h2 {
  font-size: 1.75rem;
  margin-bottom: 1rem;
}

h3 {
  font-size: 1.25rem;
  margin-bottom: 1rem;
  color: #555;
}

input {
  display: block;
  width: 100%;
  margin-bottom: 1rem;
  padding: 0.75rem;
  font-size: 1rem;
  border: 1px solid #ddd;
  border-radius: 10px;
  background: #f9f9f9;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

input:focus {
  border-color: #2d88ff;
  box-shadow: 0 0 5px rgba(45, 136, 255, 0.5);
}

.buttons {
  display: flex;
  gap: 1rem;
}

button {
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  font-weight: bold;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s ease, transform 0.2s ease;
}

button:hover {
  transform: scale(1.05);
}

.btn-primary {
  background: linear-gradient(135deg, #2d88ff, #6a5eff);
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
  background: #2d88ff;
  color: white;
}

tbody tr:hover {
  background: #f9f9f9;
}
</style>