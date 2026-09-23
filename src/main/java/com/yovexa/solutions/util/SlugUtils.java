package com.yovexa.solutions.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public final class SlugUtils {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern MULTIDASH = Pattern.compile("-+");

    private SlugUtils() {}

    public static String toSlug(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "untitled-" + System.currentTimeMillis();
        }
        String nowhitespace = WHITESPACE.matcher(input.trim()).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = MULTIDASH.matcher(slug).replaceAll("-");
        slug = slug.toLowerCase(Locale.ENGLISH);
        slug = slug.replaceAll("^-+|-+$", "");
        return slug.isEmpty() ? "item-" + System.currentTimeMillis() : slug;
    }

    public static String calculateReadingTime(String content) {
        if (content == null || content.trim().isEmpty()) {
            return "1 min read";
        }
        String plainText = content.replaceAll("<[^>]*>", " ").trim();
        String[] words = plainText.split("\\s+");
        int count = 0;
        for (String w : words) {
            if (!w.trim().isEmpty()) {
                count++;
            }
        }
        int minutes = (int) Math.ceil((double) count / 200.0);
        return (minutes <= 0 ? 1 : minutes) + " min read";
    }
}
