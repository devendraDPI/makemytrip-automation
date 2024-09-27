package xmmtsearch;

public class App {
    public void getGreeting() throws InterruptedException {
        TestCases tests = new TestCases(); // Initialize your test class

        // TODO: call your test case functions one after other here
        // START Tests
        tests.testCase01();
        tests.testCase02();
        tests.testCase03();
        tests.testCase04();

        // END Tests
        tests.endTest(); // End your test by clearing connections and closing browser
    }

    public static void main(String[] args) throws InterruptedException {
        new App().getGreeting();
    }
}
