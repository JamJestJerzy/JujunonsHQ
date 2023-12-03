package dev.j3rzy.jujunonshq.events;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static dev.j3rzy.jujunonshq.utils.ConsoleUtils.log;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        SelfUser user = event.getJDA().getSelfUser();
        log.info("Ready! Logged in as " + user.getName() + "#" + user.getDiscriminator());
        log.info(event.getJDA().getInviteUrl(Permission.ADMINISTRATOR));
    }
}
