package edu.trincoll.patterns.structural.proxy;

/**
 * Protection Proxy - Controls access based on permissions.
 */
public class DocumentProxy implements Document {
    private final SecureDocument document;
    private final String userRole;

    public DocumentProxy(String userRole) {
        this.document = new SecureDocument();
        this.userRole = userRole;
    }

    @Override
    public void view() {
        // Everyone can view
        document.view();
    }

    @Override
    public void edit() {
        // Only admins can edit
        if ("ADMIN".equals(userRole)) {
            document.edit();
        } else {
            throw new SecurityException("Access denied: Only admins can edit");
        }
    }
}
