package de.eindaniel.moderation.utils;

import de.eindaniel.moderation.Main;

public class MessageUtilsSingleton {
    private static MessageUtils instance;

    public static void initialize(Main plugin) {
        if (instance == null) {
            instance = new MessageUtils(plugin);
        }
    }

    public static MessageUtils getInstance() {
        if (instance == null) {
            throw new IllegalStateException("MessageUtilsSingleton is not initialized. Call initialize() first.");
        }
        return instance;
    }
}