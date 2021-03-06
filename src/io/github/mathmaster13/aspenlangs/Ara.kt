package io.github.mathmaster13.aspenlangs

object Ara {
    private val invalidChars = Regex("[^pbtdkgmnñńfvszšžxyhwljraeiouäüöø\\s]")
    
    /**
     * Returns `true` if a word is orthographically and phonotactically valid in ara, and `false` otherwise.
     * Capitalization is ignored.
     * Multiple words can be separated by whitespace, and this method will only return `true` if all words in `sequence` are valid.
     * Leading and trailing whitespace is ignored (the [String.trim] method is called on `sequence`).
     * A sequence containing punctuation marks, numbers, or other non-letter characters returns `false`,
     * as well as an empty sequence or one containing only whitespace.
     *
     * Specifically, a word must be V...(C(C)V...)... [where ... indicates that the previous pattern can be repeated infinitely), and repeated consonants are not allowed.
     *
     * There is an exception to these rules—the prefix *n#n* can appear at the beginning of a word, which violates the rule that a word must start with a vowel.
     * Words starting with *n#n* are considered valid due to their use in the language. All other words must adhere to the above rules.
     */
    // KDoc copied from SumaSisi
    @JvmStatic
    fun isValidSequence(sequence: String): Boolean {
        val sequence = sequence.trim().lowercase()
        if (sequence.contains(invalidChars)) return false
        return isValidSequence(sequence, ::validator)
    }

    private val nPrefix = Regex("^n[aeiouäüö]n")
    private val consonants = Regex("[pbtdkgmnñńfvszšžxyhwljr]{1,2}")
    private val vowels = Regex("[aeiouäüöø]+")
    private fun validator(sequence: String): Boolean {
        // There is a prefix, n#n, which breaks normal rules and should be handled first:
        val sequence = if (sequence.contains(nPrefix)) sequence.substring(3) else sequence

        val vowelList = sequence.split(consonants)
        val consonantList = sequence.split(vowels)

        // If the consonant list does not contain an empty string at the beginning and end,
        // the word does not start/end with a vowel, which is bad.
        if (consonantList[0].isNotEmpty() || consonantList[consonantList.size - 1].isNotEmpty()) return false

        // If there are empty strings in the vowel list, there is a consonant sequence of more than 2, which is bad.
        if (vowelList.contains("")) return false

        // No repeated consonants allowed!
        consonantList.forEach {
            if (it.length == 2 && it[0] == it[1]) return false
        }

        if (vowelList[0].contains('ø')) return false

        var hasNonzeroArgument = false
        for (i in 1 until vowelList.size) {
            val vowels = vowelList[i]
            if (!vowels.contains('ø')) hasNonzeroArgument = true
            else if (vowels.length > 1) return false
        }

        return hasNonzeroArgument
    }
}
