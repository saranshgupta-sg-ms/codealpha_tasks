# Task 3 — Artificial Intelligence Chatbot

## Features
- Interactive console-based conversation.
- Rule-based response selection using keyword matching.
- Handles greetings, Java, OOP, internship, projects and GitHub questions.
- Includes fallback responses and exit commands.

## Requirements
Java JDK 8 or later.

## Run
```bash
javac Main.java
java Main
```

## How it works
The program normalizes input to lowercase and checks it against a small keyword/response knowledge base. The longest matching keyword is selected. This is a **rule-based chatbot**, not a trained machine-learning model or advanced NLP system; it demonstrates a basic text-matching approach suitable for this task's rule-based option.

## Concepts demonstrated
Maps, strings, loops, methods, conditional logic and basic text processing.
