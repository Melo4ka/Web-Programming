package ru.meldren.weblab2.servlet;

import org.junit.jupiter.api.Test;
import ru.meldren.weblab2.util.ReflectionUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AreaCheckServletTest {

    @Test
    void checkWrongRValueValidation() {
        assertFalse(callValidate(1.0, 2.0, 1.7, false));
    }

    @Test
    void checkRightRValueValidation() {
        assertTrue(callValidate(1.0, 2.0, 1.5, false));
    }

    @Test
    void checkClickedValueValidation() {
        assertTrue(callValidate(1.123, 2.99, 1.0, true));
    }

    @Test
    void checkNotClickedValueValidation() {
        assertFalse(callValidate(1.123, 2.99, 1.0, false));
    }

    private boolean callValidate(double x, double y, double r, boolean clicked) {
        return (boolean) ReflectionUtil.invokeMethod(new AreaCheckServlet(),
                "validate", x, y, r, clicked);
    }
}