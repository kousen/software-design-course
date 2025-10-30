package edu.trincoll.patterns.structural.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Proxy Pattern Tests")
class ProxyTest {

    @Test
    @DisplayName("Virtual proxy delays creation until first use")
    void virtualProxyDelaysCreation() {
        Service proxy = new ServiceProxy();

        // Proxy created instantly (no output yet)
        System.out.println("Proxy created, doing other work...");

        // Real service created only when doWork() called
        proxy.doWork();
    }

    @Test
    @DisplayName("Protection proxy allows viewing for all users")
    void protectionProxyAllowsViewing() {
        Document userDoc = new DocumentProxy("USER");
        Document adminDoc = new DocumentProxy("ADMIN");

        // Both can view
        userDoc.view();
        adminDoc.view();
    }

    @Test
    @DisplayName("Protection proxy allows editing only for admins")
    void protectionProxyAllowsEditingForAdmins() {
        Document adminDoc = new DocumentProxy("ADMIN");

        // Admin can edit
        adminDoc.edit();
    }

    @Test
    @DisplayName("Protection proxy denies editing for regular users")
    void protectionProxyDeniesEditingForUsers() {
        Document userDoc = new DocumentProxy("USER");

        // User cannot edit
        assertThatThrownBy(() -> userDoc.edit())
            .isInstanceOf(SecurityException.class)
            .hasMessageContaining("Access denied");
    }

    @Test
    @DisplayName("Multiple calls to proxy use same real object")
    void multipleCallsUseSameRealObject() {
        ServiceProxy proxy = new ServiceProxy();

        // First call creates real service
        proxy.doWork();

        // Subsequent calls reuse same instance (no "Creating..." output)
        proxy.doWork();
        proxy.doWork();
    }
}
