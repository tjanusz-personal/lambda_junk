Lambda Junk
==================

The lambda junk project is a bunch of Java classes and unit tests I wrote to get more familiar with the Java8 changes (e.g. lambdas, Operator, Predicates, DateTime, etc). It initially started as just lambdas but turned into most of the Java 8 updates. 

My current company was initially putting me on a project using Java 8 but they later cancelled the project. I keep these around to help re-set my brain back to coding in Java.


## Getting Started

* Get all source from this git repo
* **Java 8** - http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* **IntelliJ Community edition** - https://www.jetbrains.com/idea/download/#section=linux
* **Junit** - see dependencies
* **Hamcrest matchers** - I use these in my unit tests so I can do stuff like "is" and "assertThat"

### Dependencies

* **Junit** - org.hamcrest:hamcrest-junit:2.0.0.0
* **Http Client** - org.apache.httpcomponents:httpclient:4.4.1

### Configuring the Project

* **Maven** - I had to add the two dependency projects using Mavin plugin in IntelliJ. I'm not super familiar with Maven so I'm sure this isn't installed correctly for a command line execution.

### Running the Project
There are numerous classes/unit tests so just run the suite of tests using a Junit test runner. There is no single "program" to run.


## Testing

- Junit Tests

## Deployment

N/A

## TODO

* Fix the dependencies to work cleaner with Maven from a command line
* Add some additional examples of functional Java

## Getting Help

### Documentation

* None
