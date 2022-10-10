package org.example.app.ui;

import org.example.app.domain.User;
import org.example.app.enums.UserTypes;
import org.example.app.services.UserService;
import org.example.db.Database;

import java.util.Scanner;

public class Guest {
    private static UserService userService;

    private static final Scanner scanner = new Scanner(System.in);

    public Guest(Database database) {
        userService = new UserService(database);
    }

    public static void showMenu() {
        System.out.println("\nHoşgeldiniz...");

        loops:
        while (true) {
            Common.menuHeader();
            System.out.println("1- Giriş Yap");
            System.out.println("2- Kaydol");
            Common.menuFooter();

            switch (scanner.nextLine()) {
                case "1":
                    String email;
                    String password;

                    while (true) {
                        System.out.println("\nEposta adresinizi giriniz:");
                        email = scanner.nextLine();

                        System.out.println("\nŞifrenizi giriniz:");
                        password = scanner.nextLine();

                        if (userService.login(email, password)) {
                            System.out.println("\nBaşarıyla kullanıcı girişi yapıldı.");
                            break;
                        } else {
                            System.out.println("\nHata: Sistemde böyle bir kullanıcı bulunmuyor.");
                        }
                    }
                    Common.menuSelector();
                    break;
                case "2":
                    User newUser;
                    String retypedPassword;

                    while (true) {
                        newUser = new User();

                        newUser.setType(UserTypes.CUSTOMER);

                        System.out.println("\nAdınızı giriniz:");
                        newUser.setName(scanner.nextLine());

                        System.out.println("\nSoyadınızı giriniz:");
                        newUser.setSurname(scanner.nextLine());

                        System.out.println("\nEposta adresinizi giriniz:");
                        newUser.setEmail(scanner.nextLine());

                        while (true) {

                            System.out.println("\nŞifrenizi giriniz:");
                            newUser.setPassword(scanner.nextLine());

                            System.out.println("\nŞifrenizi tekrar giriniz:");
                            retypedPassword = scanner.nextLine();

                            if (userService.checkPasswords(newUser.getPassword(), retypedPassword)) {
                                break;
                            } else {
                                System.out.println("\nHata: Şifreler Uyuşmuyor. Lütfen tekrar giriniz.");
                            }

                        }

                        if (userService.register(newUser, retypedPassword)) {
                            System.out.println("\nKullanıcı kaydı başarıyla gerçekleşti.");
                            break;
                        } else {
                            System.out.println("\nHata: kullanıcı kaydı oluşturulamadı.");
                        }
                    }
                    Common.menuSelector();
                    break;
                case "ç":
                    break loops;
                default:
                    System.out.println("\nHata: Lütfen doğru seçeneği giriniz.");
                    break;
            }
        }
    }

}
