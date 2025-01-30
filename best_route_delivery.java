package org.example;

import java.util.*;

class Haversine {
    private static final double R = 6371;

    public static double distance(double[] coord1, double[] coord2) {
        double lat1 = Math.toRadians(coord1[0]);
        double lon1 = Math.toRadians(coord1[1]);
        double lat2 = Math.toRadians(coord2[0]);
        double lon2 = Math.toRadians(coord2[1]);

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}

class RoutePlanner {
    private static final double SPEED = 20;

    public static double travelTime(double[] coord1, double[] coord2) {
        return Haversine.distance(coord1, coord2) / SPEED;
    }

    public static List<List<String>> generateValidRoutes() {
        List<String> steps = Arrays.asList("R1", "C1", "R2", "C2");
        List<List<String>> validRoutes = new ArrayList<>();

        permute(steps, 0, validRoutes);
        return validRoutes;
    }

    private static void permute(List<String> steps, int start, List<List<String>> result) {
        if (start == steps.size()) {
            if (isValidRoute(steps)) {
                result.add(new ArrayList<>(steps));
            }
            return;
        }
        for (int i = start; i < steps.size(); i++) {
            Collections.swap(steps, i, start);
            permute(steps, start + 1, result);
            Collections.swap(steps, i, start);
        }
    }

    private static boolean isValidRoute(List<String> route) {
        return route.indexOf("R1") < route.indexOf("C1") && route.indexOf("R2") < route.indexOf("C2");
    }

    public static double calculateRouteTime(List<String> route, Map<String, double[]> locations, double pt1, double pt2) {
        double currentTime = 0.0;
        String currentLocation = "A";
        Double c1Time = null, c2Time = null;

        for (String step : route) {
            double[] startCoords = locations.get(currentLocation);
            double[] destCoords = locations.get(step);

            currentTime += travelTime(startCoords, destCoords);

            if (step.equals("R1") && currentTime < pt1) {
                currentTime = pt1;
            } else if (step.equals("R2") && currentTime < pt2) {
                currentTime = pt2;
            }

            if (step.equals("C1")) c1Time = currentTime;
            if (step.equals("C2")) c2Time = currentTime;

            currentLocation = step;
        }
        return Math.max(c1Time, c2Time);
    }

    public static Map.Entry<List<String>, Double> findBestRoute(Map<String, double[]> locations, double pt1, double pt2) {
        List<List<String>> validRoutes = generateValidRoutes();
        List<String> bestRoute = null;
        double bestTime = Double.MAX_VALUE;

        for (List<String> route : validRoutes) {
            double totalTime = calculateRouteTime(route, locations, pt1, pt2);
            if (totalTime < bestTime) {
                bestTime = totalTime;
                bestRoute = route;
            }
        }
        return new AbstractMap.SimpleEntry<>(bestRoute, bestTime);
    }
}

class Main {
    public static void main(String[] args) {
        Map<String, double[]> locations = new HashMap<>();
        locations.put("A", new double[]{12.9356, 77.6143});
        locations.put("R1", new double[]{12.9350, 77.6150});
        locations.put("C1", new double[]{12.9360, 77.6160});
        locations.put("R2", new double[]{12.9340, 77.6130});
        locations.put("C2", new double[]{12.9330, 77.6120});

        double pt1 = 0.5;  // 30 minutes
        double pt2 = 0.25; // 15 minutes

        Map.Entry<List<String>, Double> result = RoutePlanner.findBestRoute(locations, pt1, pt2);
        System.out.println("Best Route: " + result.getKey());
        System.out.println("Total Time: " + result.getValue() + " hours");
    }
}
