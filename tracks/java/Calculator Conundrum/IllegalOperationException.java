Back to Exercise
Java
/
Calculator Conundrum
Settings
Open more options
src/main/java/CalculatorConundrum.java
src/main/java/IllegalOperationException.java
1
2
3
4
5
6
7
8
⌄
⌄
⌄
public class IllegalOperationException extends RuntimeException {
    public IllegalOperationException(String errorMessage) {
        super(errorMessage);
    }
    public IllegalOperationException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}

Stuck? Get help

Run Tests

Submit
All tasks passed

Sweet. Looks like you've solved the exercise!
Good job! You can continue to improve your code or, if you're done, submit an iteration to get automated feedback and optionally request mentoring.


Submit