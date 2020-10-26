package com.theonlytails;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class EventListener extends ListenerAdapter {
    String prefix = "!";
    int numOfMessages = 0;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();

        MessageChannel channel = message.getChannel();

        if (content.equals(prefix + "ping")) {
            channel.sendMessage("Pong!").queue();
        }

        numOfMessages++;


        if (numOfMessages >= 10) {
            MessageEmbed embedMessage = new EmbedBuilder()
                    .addField("You reached the goal!",
                            "10 messages have been sent so far! Time to reset the counter...",
                            false)
                    .build();

            channel.sendMessage(embedMessage).queue();
            numOfMessages = 0;
        }

        MessageEmbed embedMessage = new EmbedBuilder()
                .setTitle(String.format("Since the last reset, %d messages have been sent!",
                        numOfMessages))
                .build();

        channel.sendMessage(embedMessage).queue();
    }
}