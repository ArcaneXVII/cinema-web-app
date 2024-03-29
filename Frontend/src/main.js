import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import * as labsComponents from "vuetify/labs/components";
import axios from "axios";

const vuetify = createVuetify({
  components,
  labsComponents,
  directives,
  ssr: true,
})

axios.defaults.baseURL = 'http://localhost:8080/'

axios.interceptors.request.use((config) => {
  const token = localStorage.getItem('user-token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

const app = createApp(App)

app.use(router).use(vuetify)

app.mount('#app')
