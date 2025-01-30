# Best Route Optimization

## Overview
This project calculates the optimal route for a delivery executive by:
- Using the **Haversine formula** to compute distances between locations.
- Considering **restaurant preparation times** to minimize delivery time.
- **Generating valid delivery routes dynamically** and selecting the best one.
- **Ensuring delivery order correctness**, i.e., picking up from a restaurant before delivering to the corresponding consumer.

## Features
- **Scalable & Dynamic**: Works with any number of delivery points.
- **Encapsulated Design**: Uses a `Haversine` class for distance calculation and `RoutePlanner` for optimization.
- **Automatic Route Optimization**: Finds the best sequence of deliveries instead of relying on hardcoded options.

## How to Run
1. Compile the Java files:
   ```sh
   javac best_route_delivery.java
   ```
2. Run the program:
   ```sh
   java best_route_delivery
   ```

## Expected Output
The program prints:
- The best delivery route.
- The total estimated time in hours.

## Future Enhancements
- Support for real-world road distances via API integration.
- Consideration of real-time traffic conditions.
- Multi-agent delivery route optimization.

