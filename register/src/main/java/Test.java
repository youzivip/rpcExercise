import service.HelloService;

public class Test {
    public static void main(String[] args) {
        Class clz = HelloService.class;
        Class xl[] = clz.getDeclaredClasses();
        System.out.println();
    }
}
