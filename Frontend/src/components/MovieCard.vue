<script setup>
defineProps(['screeningId', 'movie', 'genre', 'language', 'filmRating', 'dateStart', 'dateEnd'])
</script>

<template>
  <v-container
    class="bg-white rounded-lg"
  >
    <v-row>
      <v-col
        class="rounded shadow-lg"
      >
        <v-card-item
          class="bg-grey-lighten-3 rounded"
        >
          <v-card-title>{{ movie }}</v-card-title>
          <v-card-subtitle>{{ genre }}</v-card-subtitle>
        </v-card-item>
      </v-col>
      <v-col>
        <v-card-item
          class="justify-end pr-0"
        >
          <v-card-title
            class="text-end pr-1"
          >
            {{ language }}
          </v-card-title>
          <v-card-subtitle
            class="text-end bg-grey-darken-4 rounded-pill pr-3 pl-3 pb-0 pt-0"
          >
            rating: {{ filmRating }}
          </v-card-subtitle>
        </v-card-item>
      </v-col>
    </v-row>
    <v-row
      class="pb-2"
    >
      <v-col
        class="ma-0 pa-0 pl-3"
        style="display: flex; justify-content: left; align-items: center;"
      >
        <v-btn
          style="background-color: var(--color-primary);"
          @click="overlay = !overlay; getReservedSeats()"
        >
          Tickets <v-icon>mdi-ticket</v-icon>
        </v-btn>
      </v-col>
      <v-col
        class="ma-0 pa-0"
        style="display: flex; justify-content: right; align-items: center;"
      >
        <v-card-item
          class="justify-end ma-0 pa-0"
        >
          <v-card-text
            class="font-weight-bold text-h5 text-grey-darken-4 pt-0 pb-0"
          >
            <span style="color: black"> {{ formattedTime }} </span> {{ formattedDate }}
          </v-card-text>
        </v-card-item>
      </v-col>
      <v-overlay
        v-model="overlay"
        location-strategy="static"
        scroll-strategy="block"
        style="background-color: rgba(0,0,0,0.35)"
        class="d-flex justify-center align-center"
      >
        <v-card
          class="bg-grey-darken-4"
          style="width: 70rem; height: 40rem; max-width: 90vw; max-height: 80vh;"
        >
          <v-container
            class="mt-10"
            style="max-width: 720px; width: 80rem"
          >
            <v-row
              class="ma-0 pa-0"
              v-for="row in Array.from({ length: 10 }, (_, i) => i + 1).reverse()" :key="row"
            >
              <v-col
                class="mb-2 pa-0"
                v-for="col in 18"
                :key="col"
              >
                <v-btn
                  style="background-color: var(--color-primary); width: 2rem; min-width: 20px"
                  :color="seatStatus(row, col)"
                  @click="selectSeat(row, col)"
                >
                  {{ col }}
                </v-btn>
              </v-col>
            </v-row>
            <v-row
            >
              <v-container
                class="ma-0 pa-0 d-flex align-center"
              >
                <v-container
                  class="ma-0 pa-0 d-flex align-center"
                >
                  <v-icon
                    icon="mdi-minus"
                    @click="seatAmount > 1 ? seatAmount-- : 1"
                  />
                  <div
                    class="text-center"
                    style="width: 20px"
                  >
                    {{ seatAmount }}
                  </div>
                  <v-icon
                    icon="mdi-plus"
                    @click="seatAmount < 18 ? seatAmount++ : 18"
                  />
                  <v-btn
                    @click="getSeatRecommendation()"
                    color="yellow"
                  >
                    Recommend seats
                  </v-btn>
                </v-container>
                <v-btn
                  @click="reserveSeats()"
                >
                  Reserve seats
                </v-btn>
                <v-btn
                  class="ml-3"
                  @click="cancelReservations()"
                  color="red"
                >
                  Cancel reservations
                </v-btn>
              </v-container>
            </v-row>
          </v-container>
        </v-card>
      </v-overlay>
    </v-row>


  </v-container>
</template>

<script>

import axios from 'axios'
import router from '@/router/index.js'

export default {
  data: () => ({
    overlay: false,
    formattedTime: '',
    formattedDate: '',
    seatAmount: 1,
    reservedSeats: new Map(),
    selectedSeats: new Map()
  }),
  methods: {
    formatDates() {
      const start = new Date(this.dateStart)

      const hours = start.getHours().toString().padStart(2, '0')
      const minutes = start.getMinutes().toString().padStart(2, '0')
      const day = start.getDate().toString().padStart(2, '0')
      const month = (start.getMonth() + 1).toString().padStart(2, '0')

      this.formattedTime = `${hours}:${minutes}`
      this.formattedDate = `${day}.${month}`
    },
    async getReservedSeats() {
      this.reservedSeats.clear();
      await axios.get(`/api/screening/reservations?id=${this.screeningId}`)
        .then(response => {
          for (const reservation of response.data) {
            if (this.reservedSeats.has(reservation.seatRow)) {
              this.reservedSeats.get(reservation.seatRow).push(reservation.seatNumber);
            } else {
              this.reservedSeats.set(reservation.seatRow, [reservation.seatNumber]);
            }
          }
        });
    },
    async getSeatRecommendation() {
      this.selectedSeats.clear();
      await axios.get(`/api/screening/reservation/recommendation?id=${this.screeningId}&seats=${this.seatAmount}`)
        .then(response => {
          for (const seat of response.data.recommendedSeats) {
            this.selectSeat(seat[0], seat[1]);
          }
        });
    },
    selectSeat(row, col) {
      if (this.reservedSeats.has(row) && this.reservedSeats.get(row).includes(col)) {
        return null;
      } else if (this.selectedSeats.has(row) && this.selectedSeats.get(row).includes(col)) {
        this.selectedSeats.get(row).splice(this.selectedSeats.get(row).indexOf(col), 1);
      } else {
        if (this.selectedSeats.has(row)) {
          this.selectedSeats.get(row).push(col);
        } else {
          this.selectedSeats.set(row, [col]);
        }
      }
    },
    async reserveSeats() {
      if (localStorage.getItem('user-token') === null) {
        this.overlay = false;
        router.push('/login');
        return;
      }
      const data = {
        screeningId: this.screeningId,
        seatRow: undefined,
        seatNumber: undefined,
      }
      for (const [row, cols] of this.selectedSeats) {
        for (const col of cols) {
          data.seatRow = row;
          data.seatNumber = col;
          await axios.post('/api/screening/reservation', data)
            .then(response => {
              if (response.status !== 200) {
                alert('Something went wrong while reserving seats')
              }
            });
        }
      }
      this.getReservedSeats();
    },
    async cancelReservations() {
      if (localStorage.getItem('user-token') === null) {
        this.overlay = false;
        router.push('/login');
        return;
      }
      await axios.delete('/api/screening/reservation?id=' + this.screeningId)
        .then(response => {
          if (response.status !== 200) {
            alert('Something went wrong while canceling reservations')
          }
        });
      this.getReservedSeats();
    },
    seatStatus(row, col) {
      if (this.reservedSeats.has(row) && this.reservedSeats.get(row).includes(col)) {
        return 'red'
      } else if (this.selectedSeats.has(row) && this.selectedSeats.get(row).includes(col)) {
        return 'yellow'
      }
      return 'var(--color-primary)'
    }
  },
  mounted() {
    this.formatDates()
  }
}
</script>