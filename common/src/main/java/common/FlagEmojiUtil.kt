package common

import java.util.Locale


/**
Extension function to convert a country code to its corresponding flag emoji.
This function takes a country code as a string and returns the flag emoji representing the country.
It first checks if the country code is "UK" and replaces it with "GB" (Great Britain).
Then it searches for the country code in the list of countries and retrieves the corresponding uppercase code.
If the uppercase code is not found or the first two characters are not letters, null is returned.
Finally, it calculates the Unicode code points for the flag emoji based on the first two characters of the code,
converts them to characters, and returns the concatenated flag emoji string.
 */
fun String.toFlagEmoji(): String? {
    val countryCodeCaps = (if (this == "UK")
        "GB"
    else
        countries.firstOrNull {
            it.first.equals(this, true) || it.second.equals(this, true) || it.third.equals(this, true)
        }?.second?.uppercase()) ?: return null

    if (!countryCodeCaps[0].isLetter() || !countryCodeCaps[1].isLetter()) {
        return null
    }
    val firstLetter = Character.codePointAt(countryCodeCaps, 0) - 0x41 + 0x1F1E6
    val secondLetter = Character.codePointAt(countryCodeCaps, 1) - 0x41 + 0x1F1E6

    return "${String(Character.toChars(firstLetter))}${String(Character.toChars(secondLetter))}"
}

private val countries = Locale.getISOCountries().map {
    val locale = Locale("en", it)
    val code = locale.isO3Country
    val name = locale.country
    val displayName = locale.displayCountry
    Triple(code, name, displayName)
}
