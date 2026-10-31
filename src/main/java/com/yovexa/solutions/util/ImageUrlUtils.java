package com.yovexa.solutions.util;

/**
 * Utility for sanitizing, resolving, and normalizing image URLs.
 */
public final class ImageUrlUtils {

    private ImageUrlUtils() {
        // Prevent instantiation
    }

    /**
     * Resolves and normalizes an image URL string.
     * Trims leading/trailing whitespace, strips quotes if wrapped,
     * and extracts URL if serialized as a JSON object (e.g. ImageKit upload response).
     *
     * @param imageUrl the raw image URL input
     * @return the clean resolved image URL, or null if input is null or blank
     */
    public static String resolveImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return null;
        }

        String trimmed = imageUrl.trim();

        // Handle stringified JSON object (e.g. {"url":"https://ik.imagekit.io/..."})
        if (trimmed.startsWith("{") && trimmed.contains("\"url\"")) {
            int start = trimmed.indexOf("\"url\"");
            int colon = trimmed.indexOf(':', start);
            if (colon != -1) {
                int firstQuote = trimmed.indexOf('"', colon);
                if (firstQuote != -1) {
                    int secondQuote = trimmed.indexOf('"', firstQuote + 1);
                    if (secondQuote != -1) {
                        return trimmed.substring(firstQuote + 1, secondQuote).trim().replace("\\/", "/");
                    }
                }
            }
        }

        // Remove surrounding quotes if present
        if ((trimmed.startsWith("\"") && trimmed.endsWith("\"")) ||
            (trimmed.startsWith("'") && trimmed.endsWith("'"))) {
            trimmed = trimmed.substring(1, trimmed.length() - 1).trim();
        }

        return trimmed.isEmpty() ? null : trimmed;
    }
}
