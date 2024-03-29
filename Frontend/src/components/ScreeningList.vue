<template>
  <v-layout
    class="pa-0 d-flex justify-center"
  >
    <v-main
      style="max-width: 512px"
    >
      <v-container
        class="pb-0"
      >
        <v-card
          class="text-center font-weight-bold text-h5 pa-3"
        >
          Active Screenings
        </v-card>
      </v-container>
      <v-container
        class="d-flex justify-center"
        v-for="screening in screenings" :key="screening.screeningId">
        <MovieCard
          :screeningId="screening.screeningId"
          :movie="screening.movie"
          :genre="screening.genre"
          :language="screening.language"
          :filmRating="screening.filmRating"
          :dateStart="screening.dateStart"
          :dateEnd="screening.dateEnd"
        />
      </v-container>
    </v-main>
  </v-layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import MovieCard from '@/components/MovieCard.vue'

const screenings = ref([])

onMounted(async () => {
  const response = await axios.get('/api/screening/active')
  for (const screening of response.data) {
    screenings.value.push(screening)
  }
})
</script>