package io.github.mathmaster13.aspenlangs

object Jobik {
    private val kParticle = Regex("[aeiouyáéíóúý]k")
    private const val CONSONANTS = "([pbtdkg][fsšxh]?|[fsšxh][pbtdkg]?|[mnñwljr])"
    val phonotactics = Regex("$consonants[aeiouyáé]$consonants[aeiouyáéíóúý]$consonants?")
    /**
     * Returns `true` if a word is orthographically and phonotactically valid in jobík, and `false` otherwise.
     * Capitalization is ignored.
     * Multiple words can be separated by whitespace, and this method will only return `true` if all words in `sequence` are valid.
     * Leading and trailing whitespace is ignored (the [String.trim] method is called on `sequence`).
     * A sequence containing punctuation marks, numbers, or other non-letter characters returns `false`,
     * as well as an empty sequence or one containing only whitespace.
     */
    // TODO document special cases and how this works
    // KDoc copied (but slightly edited) from SumaSisi
    @JvmStatic
    fun isValidSequence(sequence: String): Boolean {
        val sequence = sequence.trim().lowercase()
        val consonants = "([pbtdkg][fsšxh]?|[fsšxh][pbtdkg]?|[mnñwljr])"
        return sequence == "i" || sequence.matches(kParticle) // special cases
                || (isValidSequence(sequence, phonotactics) && sequence.isNotBlank()) // normal words
    }
}
