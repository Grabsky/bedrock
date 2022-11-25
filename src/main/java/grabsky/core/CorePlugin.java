package grabsky.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of every plugin utilizing this library <i>should</i> extend {@link CorePlugin} which is based on {@link JavaPlugin}.
 */
@Accessors(fluent = true)
public abstract class CorePlugin extends JavaPlugin {

    @Getter(AccessLevel.PUBLIC)
    protected CoreScheduler coreScheduler;

    @Override
    public void onEnable() {
        this.coreScheduler = new CoreScheduler(this);
    }

    /**
     * Reloads the plugin in a safe way, basically.
     */
    public abstract boolean onReload();

}
