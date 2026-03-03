class Calculator {
	
    public double square(double num){
       return Math.pow(num, 2);
    }
    
    public double multiply(double num1, double num2) {
    	return num1*num2;
    }
}

class AdvanceCalculator extends Calculator {
	
	public double squareAndAdd(double num1, double num2) {
    	Function<Double, Double> square = super::square; 	
    	return square.apply(num1) + square.apply(num2);
    }
	
	public double squareAndMultiply(double num1, double num2) {
    	Function<Double, Double> square = super::square; 
    	BiFunction<Double, Double, Double> multiply = super::multiply;
    	return multiply.apply(square.apply(num1), square.apply(num2));
    }
}
