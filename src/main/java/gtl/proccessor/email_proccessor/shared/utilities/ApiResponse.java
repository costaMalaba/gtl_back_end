package gtl.proccessor.email_proccessor.shared.utilities;

import java.io.Serializable;
import java.util.HashMap;

public record ApiResponse<T>(
        int statusCode,
        String message,
        T body
) implements Serializable {
    // Constructor to handle null body gracefully
    public ApiResponse(int statusCode, String message, T body) {
        this.statusCode = statusCode;
        this.message = message;
        // Replace null body with empty map
        this.body = body != null ? body : createEmptyBody();
    }

    @SuppressWarnings("unchecked")
    private static <T> T createEmptyBody() {
        // Cast is unchecked but encapsulated here to centralize the warning
        return (T) new HashMap<>();
    }
}
