<template>
  <v-form
    @submit.prevent="submit"
    style="width: 15rem; padding-top: 25vh;"
    class="ma-auto"
  >
    <v-alert
      v-if="errorMsg"
      dismissible
      type="error"
      icon="mdi-alert"
    >
      <v-alert-title
        class="text-sm-subtitle-2"
      >
        {{ errorMsg }}
      </v-alert-title>
    </v-alert>
    <v-text-field
      label="Email"
      type="email"
      v-model="email"
      required
    />
    <v-text-field
      label="Password"
      type="password"
      v-model="password"
      required
    />
    <v-container
      style="display: flex; justify-content: space-between; padding: 0; margin-top: 1rem;"
    >
      <v-btn type="submit" color="var(--color-primary)">
        Login
      </v-btn>
      <v-btn type="submit" color="grey" href="/signup">
        Sign Up
      </v-btn>
    </v-container>
  </v-form>
</template>

<script>

import axios from "axios";
import router from "@/router";

export default {
  name: "LoginForm",
  data: () => ({
    email: null,
    password: null,
    errorMsg: '',
    userToken: '',
  }),
  async mounted() {
    const itemLogin = localStorage.getItem("user-token");
    if (itemLogin != null) {
      localStorage.removeItem("user-token");
    }
  },
  methods: {
    submit: async function () {
      const data = {
        "password": this.password,
        "email": this.email
      };
      const headers2 = {
        "Content-Type": "application/json"
      };
      await axios.put("api/account", data, {headers: headers2})
        .then(response => {
          this.userToken = response.data;
          localStorage.setItem("user-token", this.userToken);
          if (response.status === 200) {
            router.push("/");
          }
        })
        .catch(_ => {
          this.errorMsg = "Invalid login credentials";
        });
    },

  },
}

</script>