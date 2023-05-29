package constants;

/**
 * This class contains constants for the project
 */
public class Constants {
    private Constants() {}

    /**
     * This class contains constants HTTP status codes
     */
    public static class HttpMessages {
        private HttpMessages() {}
        public static final String FORBIDDEN = "You are not allowed to visit this page";
        public static final String NOT_FOUND = "Not found";
        public static final String UNAUTHORIZED = "Unauthorized";

    }

    /**
     * This class contains constants for messages to user
     */
    public static class Messages {
        private Messages() {}
        public static final String UPDATED = "Dish was updated";
        public static final String SAVED = "Dish was saved";
    }

    /**
     * This class contains constants for JWT tokens
     */
    public static class TokenChecker {
        private TokenChecker() {}

        public static final String SECRET_KEY = "OuhvjjC1Nv0b8F4KoXkKRREP2nQew5xpMuLwH6go4I4n7+4dyalzX2ei3/Isskb+4yjwDpvJ+llHYzwHSSfYzg==";
        public static final String ALGORITHM = "HmacSHA512";

    }

    /**
     * This class contains constants for user service
     */
    public static class DishService {
        private DishService() {}
        public static final String SUCCESS = "Success";
    }


}
