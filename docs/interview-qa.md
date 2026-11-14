# Interview preparation — Mana Suraksha

## 60-second project pitch

“Mana Suraksha is a Java-based digital-safety prototype. It accepts suspicious SMS text, website URLs, and Indian phone-number input. The Core Java module contains separate analyzers and a common `ThreatAnalyzer` interface. A Spring Boot REST API exposes these checks, and a small HTML, CSS and JavaScript client calls the API. The result includes findings and safety guidance. The project deliberately reports unknown or unverified states because it does not use a live fraud database. The next steps are database integration, stronger validation with real datasets, reporting and moderation, and a Telugu/English user experience.”

## How it works — about two minutes

1. A user selects SMS, URL, or phone analysis in the browser interface.
2. The browser sends a JSON request to the Spring Boot API.
3. The controller validates the request. For example, SMS text must not be blank and has a maximum length.
4. The controller calls the relevant Core Java class. SMS and URL analyzers implement the generic `ThreatAnalyzer<String>` interface.
5. The analyzer applies local rules and creates findings. SMS rules look for a small set of OTP-disclosure and urgent-account phrases. URL checks inspect URI structure, scheme, host, punycode, IP-literal hosts and user-info.
6. The phone helper normalises an Indian mobile number. It does not verify ownership or caller reputation.
7. The API returns a structured result. The browser displays it with warnings about the prototype's limits.

## 15 fresher-level questions

1. **What problem does the project address?** It helps users inspect warning signs in suspicious SMS messages, URLs and phone-number formats.
2. **Why did you use Java?** The core analysis logic is written in Java and can be reused independently of the web client.
3. **What is an interface?** It defines a contract that implementing classes must provide.
4. **Where is polymorphism used?** `SmsAnalyzer` and `UrlAnalyzer` implement the same `ThreatAnalyzer<String>` interface.
5. **Why use generics?** `ThreatAnalyzer<T>` expresses the input type at compile time.
6. **What is an ArrayList?** A resizable list; the analyzers use it to collect findings.
7. **Why use a separate analyzer class?** It keeps each analysis rule set separate from HTTP request handling.
8. **What does a REST controller do?** It maps HTTP requests to Java methods and returns response data.
9. **What does `@PostMapping` do?** It maps an HTTP POST route to a controller method.
10. **What does `@Valid` do here?** It triggers validation of request objects using their validation annotations.
11. **What is a Java record?** A concise immutable data carrier, used for API request and response shapes.
12. **How does URL analysis work?** It parses the URI and inspects its structure; it does not open the link.
13. **Does the phone check detect scammers?** No. It normalises an Indian number and returns an unknown reputation status.
14. **Why do you have tests?** To check core rules and invalid inputs without manually calling the web API each time.
15. **What is the biggest limitation?** Rules are basic and not calibrated against real fraud data; no database or verified threat feed is connected.

## Interview honesty checklist

- Do not call the risk score a probability.
- Do not say the URL is safe just because no rule matched.
- Do not claim caller identity or reputation verification.
- Do not claim JPA, MySQL persistence, login, Android, or moderation are implemented.
