package org.manasuraksha.core.demo;

import org.manasuraksha.core.sms.SmsAnalyzer;
import org.manasuraksha.core.url.UrlAnalyzer;

/** Run with: mvn -pl core-java exec:java (after adding the exec plugin) or from an IDE. */
public final class ManaSurakshaDemo {
    private ManaSurakshaDemo() { }

    public static void main(String[] args) {
        SmsAnalyzer smsAnalyzer = new SmsAnalyzer();
        UrlAnalyzer urlAnalyzer = new UrlAnalyzer();

        System.out.println("=== Mana Suraksha: SMS check ===");
        System.out.println(smsAnalyzer.analyze(
                "Your account will be blocked. Share your OTP immediately."));

        System.out.println("\n=== Mana Suraksha: URL check ===");
        System.out.println(urlAnalyzer.analyze("http://192.0.2.10/login"));
    }
}
