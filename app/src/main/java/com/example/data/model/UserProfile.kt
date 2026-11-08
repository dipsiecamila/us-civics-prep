package com.example.data.model

enum class AppLanguage(
    val code: String,
    val nativeName: String,
    val englishName: String,
    val rank: Int
) {
    SPANISH("es", "Español", "Spanish", 2),
    ENGLISH("en", "English", "English", 1),
    CHINESE("zh", "中文", "Chinese", 3),
    TAGALOG("tl", "Tagalog", "Tagalog / Filipino", 4),
    VIETNAMESE("vi", "Tiếng Việt", "Vietnamese", 5),
    ARABIC("ar", "العربية", "Arabic", 6),
    FRENCH("fr", "Français", "French", 7),
    KOREAN("ko", "한국어", "Korean", 8),
    RUSSIAN("ru", "Русский", "Russian", 9),
    HAITIAN_CREOLE("ht", "Kreyòl Ayisyen", "Haitian Creole", 10),
    GERMAN("de", "Deutsch", "German", 11),
    HINDI("hi", "हिन्दी", "Hindi", 12),
    PORTUGUESE("pt", "Português", "Portuguese", 13),
    ITALIAN("it", "Italiano", "Italian", 14),
    POLISH("pl", "Polski", "Polish", 15),
    JAPANESE("ja", "日本語", "Japanese", 16),
    PERSIAN("fa", "فارسی", "Persian / Farsi", 17),
    GUJARATI("gu", "ગુજરાતી", "Gujarati", 18),
    TELUGU("te", "తెలుగు", "Telugu", 19),
    BENGALI("bn", "বাংলা", "Bengali", 20),
    SWAHILI("sw", "Kiswahili", "Swahili", 21),
    ESPERANTO("eo", "Esperanto", "Esperanto", 22);

    val displayName: String get() = nativeName

    companion object {
        val top20InOrder: List<AppLanguage> = entries.sortedBy { it.rank }.take(20)
        val allInOrder: List<AppLanguage> = entries.sortedBy { it.rank }
        fun fromCode(code: String): AppLanguage =
            entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: SPANISH
    }
}

data class UserProfile(
    val language: AppLanguage = AppLanguage.SPANISH,
    val age: Int = 35,
    val yearsInUS: Int = 5,
    val selectedStateCode: String = "CA"
) {
    /**
     * Official USCIS 65/20 Exemption Rule:
     * An applicant who is 65 years of age or older AND has been a lawful permanent resident
     * for at least 20 years only needs to study the 20 designated questions marked with an asterisk (*).
     */
    val qualifiesForSenior6520: Boolean
        get() = age >= 65 && yearsInUS >= 20
}
