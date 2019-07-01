package com.dragovorn.dragonbot.api.config;

import com.google.common.collect.ImmutableMap;

public interface IConfiguration {

    /**
     * Saves the configuration.
     */
    void save();

    /**
     * Loads the configuration.
     */
    void load();

    /**
     * Completely clears all of the configuration's values.
     */
    void reset();

    /**
     * Remove a key from the configuration.
     *
     * @param key The key to remove from the configuration.
     */
    void remove(String key);

    /**
     * An optional method to allow implementations declare defaults values for their configurations
     */
    default void addDefaults() { }

    /**
     * Sets the given key to the given value, one should avoid using maps or lists,
     * because it is currently unknown how they're going to serialize, this unknown
     * factor should be treated as technical debt.
     *
     * @param key The key to map the value to.
     * @param value The value to assign to the key.
     */
    void set(String key, Object value);

    /**
     * Check if the configuration has the given key.
     *
     * @param key The key to check.
     * @return If the given key is in the configuration.
     */
    boolean has(String key);

    /**
     * Gets the value of the given key while mapping it to the proper type.
     *
     * @param key The key to get the value of.
     * @param <T> The type to return.
     * @return The value from the key.
     */
    <T> T get(String key);

    /**
     * An ImmutableMap of the key values in the configuration.
     *
     * @return The values of the configuration.
     */
    ImmutableMap<String, Object> getValues();

    /**
     * An ImmutableMap of the declared defaults of the configuration.
     *
     * @return The declared defaults of the configuration.
     */
    default ImmutableMap<String, Object> getDefaults() {
        return ImmutableMap.of(); // default to an empty map, because we don't know if addDefaults is even implemented.
    }
}
