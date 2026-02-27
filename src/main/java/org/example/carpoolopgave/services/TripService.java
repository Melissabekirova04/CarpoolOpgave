package org.example.carpoolopgave.services;

import org.example.carpoolopgave.entities.Trip;
import org.springframework.stereotype.Service;
import java.io.*;



    /*public List<Trip> getAllTrips() {
        List<Trip> trips = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(url))) {
            String line;
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                trips.add(new Trip(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4]));
            }
        } catch (Exception e){
            e.getLocalizedMessage();
        }
        return trips;
    }*/


    @Service
    public class TripService {

      /*  private final String url =
                System.getProperty("user.home") + "/trips.csv";*/

        private String url = "trips.csv";

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

        public int getNextTripId(){
            int highestTripId = 0;

            try(BufferedReader br = new BufferedReader(new FileReader(url))){
                br.readLine();

                String line;

                while ((line = br.readLine()) != null){
                    if (line.trim().isEmpty()) continue;

                    String[] data = line.split(",");
                    int currentId = Integer.parseInt(data[0]);

                    if (currentId > highestTripId){
                        highestTripId = currentId;
                    }
                }
            } catch (IOException e){
                return 1;
            }
            return highestTripId;
        }
}
