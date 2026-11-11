package org.manasuraksha.core.phone;

import java.util.Objects;

/** Basic normalisation helper; not a phone ownership or identity verifier. */
public final class PhoneNumberNormalizer {
    private PhoneNumberNormalizer() { }

    public static String normaliseIndia(String input) {
        Objects.requireNonNull(input, "Phone number must not be null");
        String digits = input.replaceAll("[^0-9]", "");
        if (digits.startsWith("91") && digits.length() == 12) {
            digits = digits.substring(2);
        }
        if (digits.length() != 10 || digits.charAt(0) < '6' || digits.charAt(0) > '9') {
            throw new IllegalArgumentException("Enter a valid 10-digit Indian mobile number");
        }
        return "+91" + digits;
    }
}
