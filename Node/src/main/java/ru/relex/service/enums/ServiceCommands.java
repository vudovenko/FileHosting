package ru.relex.service.enums;

public enum ServiceCommands{
    HELP("/help"),
    REGISTRATION("/registration"),
    CANCEL("/cancel"),
    START("/start");
    private final String value;

    ServiceCommands(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static ServiceCommands fromValue(String command) {
        for (ServiceCommands enumCommand: ServiceCommands.values()) {
            if (enumCommand.value.equals(command)) {
                return enumCommand;
            }
        }
        return null;
    }
}