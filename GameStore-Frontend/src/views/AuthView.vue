<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router"; // Import Vue Router

const isLogin = ref(true); // Toggle between login and signup
const form = ref({
  username: "",
  email: "",
  password: "",
  address: "",
  phoneNumber: "",
  type: isLogin.value ? "" : "customer", // Type is set automatically to 'customer' for signup
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
};

const handleSubmit = () => {
  if (isLogin.value) {
    // Perform login logic
    console.log("Logging in with:", form.value.email, form.value.password, "as", form.value.type);

    // Redirect based on user type
    if (form.value.type === "customer") {
      router.push("/customergames");
    } else if (form.value.type === "staff") {
      router.push("/staffgames");
    } else if (form.value.type === "owner") {
      router.push("/games");
    } else {
      alert("Please select a valid user type.");
    }
  } else {
    // Perform signup logic
    console.log(
      "Signing up with:",
      form.value.username,
      form.value.email,
      form.value.password,
      form.value.address,
      form.value.phoneNumber,
      "as a customer"
    );

    // Redirect to the customer games page after signup
    router.push("/customergames");
  }
};
</script>

<template>
  <main class="auth-container">
    <div class="auth-card">
      <h1>{{ isLogin ? "Login" : "Signup" }}</h1>
      <form @submit.prevent="handleSubmit">
        <!-- Username for Signup -->
        <div v-if="!isLogin" class="form-group">
          <label for="username">Username</label>
          <input id="username" type="text" v-model="form.username" placeholder="Enter your username" required />
        </div>

        <!-- Email -->
        <div class="form-group">
          <label for="email">Email</label>
          <input id="email" type="email" v-model="form.email" placeholder="Enter your email" required />
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