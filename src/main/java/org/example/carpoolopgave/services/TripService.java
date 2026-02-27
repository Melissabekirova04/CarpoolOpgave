package org.example.carpoolopgave.services;

import org.example.carpoolopgave.entities.Trip;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {

    private String url = "trips.csv";

    public List<Trip> getAllTrips() {
        List<Trip> trips = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("trips.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    Trip trip = new Trip();
                    trip.setTripId(Integer.parseInt(parts[0]));
                    trip.setDriverName(parts[1]);
                    trip.setStartDestination(parts[2]);
                    trip.setEndDestination(parts[3]);
                    trip.setDate(parts[4]);
                    trip.setTime(parts[5]);
                    trip.setSeats(Integer.parseInt(parts[6]));
                    trips.add(trip);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trips;
    }

    public Trip getTripById(int tripId) {
        List<Trip> allTrips = getAllTrips();

        for (Trip trip : allTrips) {
            if (trip.getTripId() == tripId) {
                return trip;
            }
        }
        return null;
    }


    public void updateTripsFile(List<Trip> trips) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("trips.csv"))) {

            for (Trip trip : trips) {

                // 🔥 Remove trips with no seats left
                if (trip.getSeats() > 0) {

                    writer.write(
                            trip.getTripId() + "," +
                                    trip.getDriverName() + "," +
                                    trip.getStartDestination() + "," +
                                    trip.getEndDestination() + "," +
                                    trip.getDate() + "," +
                                    trip.getTime() + "," +
                                    trip.getSeats()
                    );

                    writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTrip(Trip trip) {

        try {
            File file = new File(url);

            if (!file.exists()) {
                file.createNewFile();
            }

            boolean hasContent = file.length() > 0;

            try (BufferedWriter bw =
                         new BufferedWriter(new FileWriter(file, true))) {

                if (!hasContent) {
                    bw.write("tripId,driverName,from,to,date,time,seats");
                    bw.newLine();
                }

                bw.write(
                        trip.getTripId() + "," +
                                trip.getDriverName() + "," +
                                trip.getStartDestination() + "," +
                                trip.getEndDestination() + "," +
                                trip.getDate() + "," +
                                trip.getTime() + "," +
                                trip.getSeats()
                );

                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNextTripId() {
        int highestTripId = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(url))) {
            br.readLine();

            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");
                int currentId = Integer.parseInt(data[0]);

                if (currentId > highestTripId) {
                    highestTripId = currentId + 1;
                }
            }
        } catch (IOException e) {
            return 1;
        }
        return highestTripId;
    }

    public void saveBooking(String username, Trip trip) {
        try (FileWriter fw = new FileWriter("bookedTrips.csv", true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(username + "," +
                    trip.getTripId() + "," +
                    trip.getStartDestination() + "," +
                    trip.getEndDestination() + "," +
                    trip.getDate() + "," +
                    trip.getTime());

            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
