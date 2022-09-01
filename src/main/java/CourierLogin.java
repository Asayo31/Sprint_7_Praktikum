public class CourierLogin {

    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public CourierLogin(String login) {
        this.login = login;
    }

    public static CourierLogin from(Courier courier) {
        return new CourierLogin(courier.getLogin());
    }
}