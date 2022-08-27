public class CourierCred {
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private  String login;
    private String password;

    public CourierCred(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCred from (Courier courier){
        return new CourierCred (courier.getLogin(), courier.getPassword());
    }
}