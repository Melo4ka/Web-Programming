package ru.meldren.weblab2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConstantTest {

    @Test
    void checkIfValueIsOnPlotRectangle() {
        assertTrue(Constant.isOnPlot(0.5, -1.0, 1.0));
    }

    @Test
    void checkIfValueIsNotOnPlotRectangle() {
        assertFalse(Constant.isOnPlot(1.0, -1.0, 1.0));
    }

    @Test
    void checkIfValueIsOnPlotTriangle() {
        assertTrue(Constant.isOnPlot(0.75, 0.75, 3.0));
    }

    @Test
    void checkIfValueIsNotOnPlotTriangle() {
        assertFalse(Constant.isOnPlot(1.5, 1.5, 3.0));
    }

    @Test
    void checkIfValueIsOnPlotCircle() {
        assertTrue(Constant.isOnPlot(-0.5, -0.5, 2.0));
    }

    @Test
    void checkIfValueIsNotOnPlotCircle() {
        assertFalse(Constant.isOnPlot(-1.0, -1.0, 2.0));
    }
}