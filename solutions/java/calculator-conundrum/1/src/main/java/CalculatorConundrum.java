class CalculatorConundrum {

    private int add(int operand1, int operand2) {
        return operand1 + operand2;
    }

    private int multiply(int operand1, int operand2) {
        return operand1 * operand2;
    }

    private int divide(int operand1, int operand2) {
        return operand1 / operand2;
    }

    public String calculate(int operand1, int operand2, String operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation cannot be null");
        }
        if (operation.isEmpty()) {
            throw new IllegalArgumentException("Operation cannot be empty");
        }

        int result;

        switch (operation) {
            case "+":
                result = add(operand1, operand2);
                break;
            case "*":
                result = multiply(operand1, operand2);
                break;
            case "/":
                try {
                    result = divide(operand1, operand2);
                } catch (ArithmeticException e) {
                    throw new IllegalOperationException("Division by zero is not allowed", e);
                }
                break;
            default:
                throw new IllegalOperationException("Operation '" + operation + "' does not exist");
        }

        return operand1 + " " + operation + " " + operand2 + " = " + result;
    }
}