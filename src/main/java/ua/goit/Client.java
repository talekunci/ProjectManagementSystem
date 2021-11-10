package ua.goit;

import ua.goit.console.CommandHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Client {

    public static void main(String[] args) {
        System.out.println("Start application");

        runMainApp();

        System.out.println("END application");
    }

    public static void runMainApp() {
        CommandHandler commandHandler = new CommandHandler();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                commandHandler.handleCommand(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
