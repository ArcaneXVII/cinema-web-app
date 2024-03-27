package ee.arcane.cinemawebapp.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeatReservationUtility {
    private final int[][] seatLayout = new int[10][18];
    // Optimal position for the best seats
    private static final double BEST_SEAT_ROW = 5;
    private static final double BEST_SEAT_COL = 8.5;

    public SeatReservationUtility() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 18; j++) {
                seatLayout[i][j] = 0;
            }
        }
    }

    public List<List<Integer>> getSeatRecommendation(int seatAmount) {
        List<List<int[]>> availableSeatGroups = new ArrayList<>();
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column <= 18 - seatAmount; column++) {
                List<int[]> seatGroup = new ArrayList<>();
                boolean isGroupAvailable = true;
                for (int k = 0; k < seatAmount; k++) {
                    if (seatLayout[row][column + k] != 0) {
                        isGroupAvailable = false;
                        break;
                    }
                    seatGroup.add(new int[]{row, column + k});
                }
                if (isGroupAvailable) {
                    availableSeatGroups.add(seatGroup);
                }
            }
        }

        return getBestSeatGroup(seatAmount, availableSeatGroups);
    }

    private List<List<Integer>> getBestSeatGroup(int seatAmount, List<List<int[]>> availableSeatGroups) {
        if (availableSeatGroups.isEmpty()) {
            return List.of();
        }

        availableSeatGroups.sort((group1, group2) -> {
            double distance1 = calculateGroupDistance(group1, seatAmount);
            double distance2 = calculateGroupDistance(group2, seatAmount);
            if (distance1 != distance2) {
                return Double.compare(distance1, distance2);
            } else {
                // If distances are equal, pick the further row from the screen.
                return Integer.compare(group2.getFirst()[0], group1.getFirst()[0]);
            }
        });

        List<List<Integer>> recommendedSeats = new ArrayList<>();
        for (int[] seat : availableSeatGroups.getFirst()) {
            recommendedSeats.add(Arrays.asList(seat[0] + 1, seat[1] + 1));
        }

        return recommendedSeats;
    }

    private double calculateGroupDistance(List<int[]> group, int seatAmount) {
        double totalDistance = 0;
        for (int[] seat : group) {
            totalDistance += calculateDistance(seat[0], seat[1]);
        }
        return totalDistance / seatAmount;
    }

    private double calculateDistance(int row, int col) {
        return Math.sqrt(Math.pow(row - BEST_SEAT_ROW, 2) + Math.pow(col - BEST_SEAT_COL, 2));
    }

    public void setSeatReserved(int row, int col) {
        seatLayout[row - 1][col - 1] = 1;
    }

}
