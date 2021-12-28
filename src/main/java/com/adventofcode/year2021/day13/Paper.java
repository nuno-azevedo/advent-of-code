package com.adventofcode.year2021.day13;

import com.adventofcode.common.geometry.Point2D;

import java.util.List;

public record Paper(List<Point2D> dots, List<Fold> folds) { }
