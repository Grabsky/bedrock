package cloud.grabsky.bedrock.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import org.jetbrains.annotations.NotNull;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Triggered by various plugins depending on this library.
 *
 * The purpose of this event is to allow plugins to dynamically apply decorative
 * effects through different teleportation states.
 *
 * @apiNote This is NOT triggered by teleportation API. It is only called when some plugin wants to decorate its teleportation with effects.
 */
public class TeleportRequestDecorationEvent extends PlayerEvent {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter(AccessLevel.PUBLIC)
    private final @NotNull Location from;

    @Getter(AccessLevel.PUBLIC)
    private final @NotNull Location to;

    @Getter(AccessLevel.PUBLIC)
    private final @NotNull State state;

    public TeleportRequestDecorationEvent(final @NotNull Player who, final @NotNull Location from, final @NotNull Location to, final @NotNull State state) {
        super(who);
        this.from = from;
        this.to = to;
        this.state = state;
    }

    public TeleportRequestDecorationEvent(final @NotNull Player who, final @NotNull Location from, final @NotNull Location to, final @NotNull State state, final boolean async) {
        super(who, async);
        this.from = from;
        this.to = to;
        this.state = state;
    }

    public boolean isChangingWorlds() {
        return from.getWorld().getKey().equals(to.getWorld().getKey());
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public enum State {
        BEGIN, END, FAILURE
    }

}