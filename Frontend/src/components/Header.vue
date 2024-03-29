<template>
  <header>
    <h1
      class="text-black font-weight-bold pa-3"
    >
      Spectrum Cinema
    </h1>
    <nav>
      <RouterLink
        to="/login"
        v-if="!loginStatus"
      >
        Login
      </RouterLink>
      <RouterLink
        to="/"
        v-if="loginStatus"
        @click="removeToken"
      >
        Log out
      </RouterLink>
    </nav>
  </header>
</template>

<script>
export default {
  data() {
    return {
      loginStatus: false,
    };
  },
  methods: {
    removeToken() {
      localStorage.removeItem("user-token");
      this.loginStatus = false;
    },
  },
  mounted() {
    this.loginStatus = localStorage.getItem("user-token") !== null;
  }
}
</script>

<style scoped>
header {
  display: flex;
  justify-content: space-between;
  width: 100vw;
  background-color: #ffffff;
  padding: 0;
  margin: 0;
  box-shadow: 0 0.1vw 3vw rgba(255, 255, 255, 0.35);
  z-index: 100;
}

header span {
  color: var(--color-primary);
  font-size: 2rem;
  padding-left: 2rem;
}

nav {
  display: flex;
  justify-content: flex-end;
  padding: 1rem;
}

nav a {
  color: black;
  font-size: large;
  font-weight: bold;
  background-color: var(--color-primary);
  border-radius: 0.5vw;
  box-shadow: rgba(0, 0, 0, 0.5) 0.01vw 0.01vw 0.5vw;
  padding: 0.5rem 1rem;
}
</style>