package uz.agro.users.helpers;

public enum Constants {
    MASTER("master"),
    ADMIN("admin"),
    SERVER_URL("http://localhost:8080"),
    ADMIN_CLI("admin-cli"),
    SECRET("secret"),
    GRANT_TYPE("grant_type"),
    CUSTOM_REALM("agro-realm"),
    CUSTOM_CLIENT("agro-client"),
    PROVIDER("provider"),
    GRANT_TYPE_PASSWORD("password"),
    CUSTOM_ROLE("default-roles-agro-realm"),
    ;

    private final String name;

    Constants(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
