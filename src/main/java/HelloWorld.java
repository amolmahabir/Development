public class HelloWorld {
  public static void main(String[] args) {
    String john = bogusMethod("John");
    System.out.println(getGreeting());
    System.out.println(john);
  }

  public static String getGreeting() {
    return "Hello, World!";
  }

  public static String bogusMethod(String name) {
    if ("Amol".equals(name)) {
      return "Welcome, " + name + "!";
    }
    else return "Bugger off, " + name + "!";
  }
}
