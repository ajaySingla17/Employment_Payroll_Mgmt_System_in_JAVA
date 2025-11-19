package payroll;

import java.util.Scanner;

public class Admin {
    private final String username;
    private final String password;

    public Admin(String u, String p) {
        this.username = u;
        this.password = p;
    }

    public boolean login(Scanner sc) {
        System.out.print("Username: ");
        String u = sc.nextLine().trim();
        System.out.print("Password: ");
        String p = sc.nextLine().trim();
        return username.equals(u) && password.equals(p);
    }
}
