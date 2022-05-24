package io.github.mathmaster13.aspenlangs

object Jobik {
    private val kParticle = Regex("[aeiouyáéíóúý]k")
    private const val CONSONANTS = "([pbtdkg][fsšxh]?|[fsšxh][pbtdkg]?|[mnñwljr])"

    /**
     * A `Regex` representing the phonotactics of a single jobík word.
     * All words in jobík must follow this structure, with the exception of the particles *i* and *.k*.
     * @see isValidSequence
     */
    @JvmField
    val phonotactics = Regex("$CONSONANTS[aeiouyáé]$CONSONANTS[aeiouyáéíóúý]$CONSONANTS?")

    /**
     * Returns `true` if a word is orthographically and phonotactically valid in jobík, and `false` otherwise.
     * Capitalization is ignored.
     * Multiple words can be separated by whitespace, and this method will only return `true` if all words in `sequence` are valid.
     * Leading and trailing whitespace is ignored (the [String.trim] method is called on `sequence`).
     * A sequence containing punctuation marks, numbers, or other non-letter characters returns `false`,
     * as well as an empty sequence or one containing only whitespace.
     *
     * Specifically, a word must be C(C)VC(C)V(CC), with the valid consonant and vowel sequences in each position
     * defined in the [jobík documentation](https://aspenlangs.neocities.org/jobdoc.html).
     * You can also obtain a `Regex` of the phonotactics from the {@link phonotactics} property.
     *
     * There is a closed class of words that do not follow these phonotactics—namely, the particles *i* and *.k*—that
     * are considered valid due to their use in the language. All other words must adhere to the above rules.
     *
     * @see phonotactics
     */
    // KDoc copied (but slightly edited) from SumaSisi
    @JvmStatic
    fun isValidSequence(sequence: String): Boolean {
        val sequence = sequence.trim().lowercase()
        return sequence == "i" || sequence.matches(kParticle) // special cases
                || (isValidSequence(sequence, phonotactics) && sequence.isNotBlank()) // normal words
    }
}
