package com.theonlytails;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class DiscordBot {
    public static JDA jda;

    public static void main(String[] args) throws LoginException {
        jda = JDABuilder
                .createDefault(jda.getToken())
                .build();

        jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB,
                Activity.listening("testing-channel"));

        jda.addEventListener(new EventListener());
    }
}