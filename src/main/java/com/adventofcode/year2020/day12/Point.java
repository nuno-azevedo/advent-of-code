package com.adventofcode.year2020.day12;

record Point(int latitude, int longitude) {
    int getManhattanDistance() {
        return Math.abs(latitude) + Math.abs(longitude);
    }

    Point move(NavInstruction instruction) {
        return switch (instruction.direction()) {
            case NORTH, SOUTH -> new Point(latitude + instruction.value(), longitude);
            case EAST, WEST -> new Point(latitude, longitude + instruction.value());
            default -> throw new IllegalArgumentException();
        };
    }

    Point move(Point waypoint, int times) {
        return new Point(latitude + waypoint.latitude * times, longitude + waypoint.longitude * times);
    }

    Point rotate(int degrees) {
        return switch (degrees % 360) {
            case -270, 90 -> new Point(-longitude, latitude);
            case -180, 180 -> new Point(-latitude, -longitude);
            case -90, 270 -> new Point(longitude, -latitude);
            default -> this;
        };
    }
}
