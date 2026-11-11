package org.manasuraksha.core.phone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PhoneNumberNormalizerTest {
    @Test
    void normalisesIndianNumber() {
        assertEquals("+919876543210", PhoneNumberNormalizer.normaliseIndia("98765 43210"));
    }

    @Test
    void acceptsCountryPrefix() {
        assertEquals("+919876543210", PhoneNumberNormalizer.normaliseIndia("+91 98765-43210"));
    }

    @Test
    void rejectsInvalidNumber() {
        assertThrows(IllegalArgumentException.class,
                () -> PhoneNumberNormalizer.normaliseIndia("12345"));
    }
}
