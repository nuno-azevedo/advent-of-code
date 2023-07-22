package com.adventofcode.year2020.day12;

record Point(int latitude, int longitude) {
    int getManhattanDistance() {
        return Math.abs(this.latitude) + Math.abs(this.longitude);
    }

    Point move(NavInstruction instruction) {
        return switch (instruction.direction()) {
            case NORTH, SOUTH -> new Point(this.latitude + instruction.value(), this.longitude);
            case EAST, WEST -> new Point(this.latitude, this.longitude + instruction.value());
            default -> throw new IllegalArgumentException();
        };
    }

    Point move(Point waypoint, int times) {
        return new Point(this.latitude + waypoint.latitude * times, this.longitude + waypoint.longitude * times);
    }

    Point rotate(int degrees) {
        return switch (degrees % 360) {
            case -270, 90 -> new Point(-this.longitude, this.latitude);
            case -180, 180 -> new Point(-this.latitude, -this.longitude);
            case -90, 270 -> new Point(this.longitude, -this.latitude);
            default -> this;
        };
    }
}
