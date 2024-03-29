<template>
  <v-form
      @submit.prevent="submit"
      style="width: 15rem; padding-top: 20vh;"
      class="ma-auto"
  >
    <v-alert
        v-if="errorMsg"
        dismissible
        type="error"
        icon="mdi-alert"
    >
      <v-alert-title class="text-sm-subtitle-2">
        {{ errorMsg }}
      </v-alert-title>
    </v-alert>
    <v-text-field
        label="Username"
        type="text"
        v-model="username"
        required
    />
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
    <v-btn
        type="submit"
        color="var(--color-primary)"
        style="width: 100%"
    >
      Sign Up
    </v-btn>
  </v-form>
</template>
<script>
import axios from "axios";
import router from "@/router";

export default {
  name: "SignupForm",
  data: () => ({
    username: null,
    email: null,
    password: null,
    errorMsg: '',
  }),
  methods: {
    async submit() {
      const data = {
        username: this.username,
        email: this.email,
        password: this.password,
      }
      const headers = {
        'Access-Control-Allow-Origin': '*',
        "Content-Type": "application/json"
      };
      await axios.post("/api/account", data, { headers: headers })
          .then(response => {
            if (response.status === 200) {
              router.push("/login");
            }
          }).catch(error => {
            this.errorMsg = error.response.data.message;
          });
    },
  },
}
</script>