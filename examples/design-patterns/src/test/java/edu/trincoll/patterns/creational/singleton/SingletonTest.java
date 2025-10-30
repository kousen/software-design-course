package edu.trincoll.patterns.creational.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Singleton Pattern Tests")
class SingletonTest {

    @Test
    @DisplayName("Enum singleton returns same instance")
    void enumSingletonReturnsSameInstance() {
        AppConfig config1 = AppConfig.INSTANCE;
        AppConfig config2 = AppConfig.INSTANCE;

        assertThat(config1).isSameAs(config2);
    }

    @Test
    @DisplayName("Enum singleton maintains state")
    void enumSingletonMaintainsState() {
        AppConfig.INSTANCE.set("test.key", "test.value");

        String value = AppConfig.INSTANCE.get("test.key");

        assertThat(value).isEqualTo("test.value");
    }

    @Test
    @DisplayName("Holder pattern singleton returns same instance")
    void holderPatternReturnsSameInstance() {
        DatabaseConnection conn1 = DatabaseConnection.getInstance();
        DatabaseConnection conn2 = DatabaseConnection.getInstance();

        assertThat(conn1).isSameAs(conn2);
    }

    @Test
    @DisplayName("Eager singleton returns same instance")
    void eagerSingletonReturnsSameInstance() {
        EagerSingleton instance1 = EagerSingleton.getInstance();
        EagerSingleton instance2 = EagerSingleton.getInstance();

        assertThat(instance1).isSameAs(instance2);
    }

    @Test
    @DisplayName("AppConfig has default configuration")
    void appConfigHasDefaults() {
        String appName = AppConfig.INSTANCE.get("app.name");
        String appVersion = AppConfig.INSTANCE.get("app.version");

        assertThat(appName).isEqualTo("Design Patterns Demo");
        assertThat(appVersion).isEqualTo("1.0");
    }
}
