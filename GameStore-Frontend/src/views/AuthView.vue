<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router"; // Import Vue Router
import axios from "axios";

//export customer = null;

const axiosClient = axios.create({
  baseURL: "http://localhost:8080",
});

const isLogin = ref(true); // Toggle between login and signup
const form = ref({
  username: "",
  email: "",
  password: "",
  address: "",
  phoneNumber: "",
  type: "customer", // Default to "customer" for signup
});

const newCategory = ref({
  name: "",
});

const router = useRouter(); // Access the router instance

const resetForm = () => {
  form.value = {
    username: "",
    email: "",
    password: "",
    address: "",
    phoneNumber: "",
    type: isLogin.value ? "" : "customer",
  };
  newCategory.value.name = "";
};

const handleSubmit = async () => {
  if (isLogin.value) {
    // Perform login logic

    const personParams = {
      username: form.value.username,
      password: form.value.password
    };
    console.log("Logging in with:", personParams.username, personParams.password, "as", form.value.type);

    let response = null;

    try {
      // Redirect based on user type
      if (form.value.type === "customer") {
        response = await axiosClient.post("/login/customer", personParams);
      } else if (form.value.type === "staff") {
        response = await axiosClient.post("/login/staff", personParams);
      } else if (form.value.type === "owner") {
        response = await axiosClient.post("/login/owner", personParams);
      } else {
        alert("Please select a valid user type.");
        return;
      }
    } catch (error) {
      alert("Login failed:", error.response.data.error);
      alert("Login failed. Please try again.");
      return;
    }
    if (response.data === "") {
      alert("Username and password do not match!");
      return;
    } 
    console.log(response);
    sessionStorage.setItem('user', JSON.stringify(response));

    if (form.value.type === "customer") {
      router.push("/customergames");
    } else if (form.value.type === "staff") {
      router.push("/staffgames");
    } else if (form.value.type === "owner") {
      router.push("/games");
    }
    
  } else {
    const custParams = {
      username: form.value.username,
      password: form.value.password,
      email: form.value.email,
      address: form.value.address,
      phoneNumber: form.value.phoneNumber
    };


    // Perform signup logic
    console.log(
      "Signing up with:",
      custParams.username,
      custParams.email,
      custParams.password,
      custParams.address,
      custParams.phoneNumber,
      "as a customer"
    );

    console.log(custParams);

    try {
      // Make the signup request here if necessary
      let customer = await axiosClient.post("/customer", custParams);
      //console.log("RESPONSERESPONSERESPONSE");
      //console.log(customer);
      // Redirect to the customer games page after signup
      sessionStorage.setItem('user', JSON.stringify(customer));
      router.push("/customergames");
    } catch (qwerty) {
      alert("Signup failed: " + qwerty.response.data.error);
      //alert("Signup failed. Please try again.");
    }
  }
};
</script>


<template>
  <main class="auth-container">
    <div class="auth-card">
      <h1>{{ isLogin ? "Login" : "Signup" }}</h1>
      <form @submit.prevent="handleSubmit">
        <!-- Email for Signup -->
        <div v-if="!isLogin" class="form-group">
          <label for="email">Email</label>
          <input id="email" type="text" v-model="form.email" placeholder="Enter your email" required />
        </div>

        <!-- Username -->
        <div class="form-group">
          <label for="username">Username</label>
          <input id="username" type="username" v-model="form.username" placeholder="Enter your username" required />
        </div>

        <!-- Password -->
        <div class="form-group">
          <label for="password">Password</label>
          <input id="password" type="password" v-model="form.password" placeholder="Enter your password" required />
        </div>

        <!-- Address for Signup -->
        <div v-if="!isLogin" class="form-group">
          <label for="address">Address</label>
          <input id="address" type="text" v-model="form.address" placeholder="Enter your address" required />
        </div>

        <!-- Phone Number for Signup -->
        <div v-if="!isLogin" class="form-group">
          <label for="phoneNumber">Phone Number</label>
          <input
            id="phoneNumber"
            type="tel"
            v-model="form.phoneNumber"
            placeholder="Enter your phone number"
            required
          />
        </div>

        <!-- Type Dropdown for Login -->
        <div v-if="isLogin" class="form-group">
          <label for="type">Type</label>
          <select id="type" v-model="form.type" required>
            <option value="" disabled>Select type</option>
            <option value="customer">Customer</option>
            <option value="staff">Staff</option>
            <option value="owner">Owner</option>
          </select>
        </div>

        <!-- Buttons -->
        <div class="buttons">
          <button type="submit" class="btn-primary">{{ isLogin ? "Login" : "Signup" }}</button>
          <button type="button" class="btn-secondary" @click="resetForm">Clear</button>
        </div>
      </form>

      <!-- Toggle Login/Signup -->
      <p class="toggle-auth">
        {{ isLogin ? "Don't have an account?" : "Already have an account?" }}
        <a href="#" @click.prevent="isLogin = !isLogin; resetForm();">
          {{ isLogin ? "Signup" : "Login" }}
        </a>
      </p>
    </div>
  </main>
</template>

<style scoped>
/* Styling for the form */
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: #f8f8f8;
}

.auth-card {
  background: white;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

h1 {
  margin-bottom: 1rem;
  font-size: 1.75rem;
  text-align: center;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
}

input,
select {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 5px;
  transition: border-color 0.3s;
}

input:focus,
select:focus {
  border-color: #2d88ff;
}

.buttons {
  display: flex;
  justify-content: space-between;
}

button {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: bold;
  transition: background 0.3s ease;
}

button:hover {
  background: #f0f0f0;
}

.btn-primary {
  background: #2d88ff;
  color: white;
}

.btn-secondary {
  background: #ddd;
  color: #333;
}

.toggle-auth {
  margin-top: 1rem;
  text-align: center;
}

.toggle-auth a {
  color: #2d88ff;
  font-weight: bold;
  text-decoration: none;
}

.toggle-auth a:hover {
  text-decoration: underline;
}
</style>