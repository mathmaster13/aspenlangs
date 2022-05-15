package io.github.mathmaster13.aspenlangs

// I don't know why you'd use this, but I'll allow it.
typealias Jobík = Jobik

/**
 * Returns `true` if a word is orthographically and phonotactically valid as specified by `validator`, and `false` otherwise.
 * Multiple words can be separated by whitespace, and this method will only return `true` if all words in `sequence` are valid.
 * Leading and trailing whitespace is ignored (the [String.trim] method is called on `sequence`).
 * A sequence containing punctuation marks or other characters that are not part of words returns `false`,
 * as well as an empty sequence or one containing only whitespace.
 */
@JvmSynthetic
internal inline fun isValidSequence(sequence: String, validChars: String, validator: (String) -> Boolean): Boolean {
    val sequence = sequence.trim()
    if (sequence.contains(Regex("[^$validChars\\s+]")) || sequence.isBlank()) return false

    // Handle a multiple-word sequence
    val wordArray = sequence.split(Regex("\\s+"))
    if (wordArray.isEmpty()) return false

    var output = true
    for (i in wordArray) output = validator(i) && output
    return output
}

// For simple checks where one regex will do
@JvmSynthetic
internal fun isValidSequence(sequence: String, regex: Regex): Boolean {
    val sequence = sequence.trim()
    return sequence.matches(regex) && sequence.isNotBlank()
}