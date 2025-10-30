package edu.trincoll.patterns.structural.proxy;

/**
 * Virtual Proxy - Delays creation of expensive object until needed.
 *
 * Proxy Pattern Benefits:
 * - Lazy initialization (create only when needed)
 * - Access control (add security checks)
 * - Logging (track method calls)
 * - Caching (store expensive results)
 */
public class ServiceProxy implements Service {
    private ExpensiveService realService;

    @Override
    public void doWork() {
        // Lazy initialization - create only when first needed
        if (realService == null) {
            realService = new ExpensiveService();
        }
        realService.doWork();
    }
}
