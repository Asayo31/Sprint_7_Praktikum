public class CourierGen {

    public static Courier getCourierCreate() {
        return new Courier("Test01", "Qwerty", "User");
    }

    public static Courier getDefault() {
        return new Courier("Test02", "Qwerty", "UserTest");
    }

    public static Courier getDuplicateLogin() {
        return new Courier("Test03", "!Qwerty1", "UserTest");
    }

    public static Courier CourierNonAuthorization() {
        return new Courier("Test04", "!Qwerty1", "User04");
    }

}