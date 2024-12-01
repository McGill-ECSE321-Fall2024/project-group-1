<template>
  <div id="app">
    <!-- Basic Header -->
    <header>
      <div class="branding">
        <div class="logo-container">
          <img alt="Video Game Management Logo" class="logo" src="@/assets/logo.svg" />
        </div>
        <h1>Samy's Video Game Kingdom</h1>
      </div>
    </header>

    <!-- Main Content -->
    <main>
      <div class="content-container">
        <router-view />
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style scoped>
/* General Styling */
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  font-family: "Roboto", sans-serif;
  background: #f8f8f8;
  color: #333;
}

/* Header */
header {
  background: linear-gradient(135deg, #2d88ff, #6a5eff);
  padding: 1.5rem;
  text-align: center;
  color: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  border-bottom-left-radius: 20px;
  border-bottom-right-radius: 20px;
}

header .branding {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
}

header .logo-container {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 80px;
}

header .logo {
  width: 80px;
  height: 80px;
  object-fit: cover; /* Ensure the logo is displayed properly */
}

header h1 {
  font-size: 1.75rem;
  font-weight: bold;
  margin: 0;
  text-shadow: 0 1px 4px rgba(0, 0, 0, 0.2);
}

/* Main Content */
main {
  padding: 2rem;
  display: flex;
  justify-content: center;
  align-items: flex-start;
}

.content-container {
  width: 100%;
  max-width: 1200px; /* Set a maximum width for the content */
  min-width: 800px; /* Ensure it’s not too thin */
  background: white;
  border-radius: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 2rem;
}

/* Media Queries for Responsiveness */
@media (max-width: 800px) {
  .content-container {
    min-width: 100%; /* Allow the content to shrink on smaller screens */
    padding: 1rem;
  }
}
</style>