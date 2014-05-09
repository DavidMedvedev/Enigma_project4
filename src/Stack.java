public class Stack {


    //
    // Public
    //
    public Stack() {
        init();
    }

    public void push(Locale currentLocale) throws Exception{
        // Check for stack overflow.
        if (topPtr > 0) {
            topPtr = topPtr - 1;
            arr[topPtr] = currentLocale.getName();
        } else {
            Exception overflow = new Exception("Stack Overflow");
            throw overflow;
        }
    }

    public String pop() throws Exception{
        String retVal = null;
        // Check for stack underflow.
        if (topPtr < CAPACITY) {
            retVal = arr[topPtr];
            topPtr = topPtr + 1;
        } else {
        	retVal = null;
        	Exception underflow = new Exception("Stach underflow");
            throw underflow;
        }
        return retVal;
    }

    public boolean isEmpty() {
        boolean retVal = false;
        if (topPtr == CAPACITY) {
            retVal = true;
        }
        return retVal;
    }

    public boolean isEmptyMo() {
        return (topPtr == CAPACITY);
    }


    //
    // Private
    //
    private final int CAPACITY = 50;
    private String[] arr = new String[CAPACITY];
    private int topPtr = 0;

    private void init() {
       for (int i = 0; i < CAPACITY; i++) {
           arr[i] = null;
       }
       topPtr = CAPACITY;
    }


}