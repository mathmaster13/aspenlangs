package io.github.mathmaster13.aspenlangs

object SumaSisi {
    /**
     * An overload for the unsigned equivalent of this method, because Java hates unsigned types.
     * An [IllegalArgumentException] will be thrown if `number` is negative.
     * @see number
     */
    @JvmStatic
    fun number(number: Long) = if (number < 0) throw IllegalArgumentException("suma sisi numbers cannot be negative.") else number(number.toULong())

    /**
     * Returns `number` as a suma sisi number.
     * suma sisi uses *ma* (no) as a replacement for 0, but since this is not a number,
     * and since *ma* goes before a word (as opposed to numbers, which go after words),
     * an [IllegalArgumentException] will be thrown if `number == 0UL`.
     */
    @JvmSynthetic
    fun number(number: ULong): String {
        if (number == 0UL) throw IllegalArgumentException("number cannot be 0")
        val asBijectiveNonary = toBijectiveNonary(number)
        var output = if (asBijectiveNonary.length == 1) "sa" else ""
        for (i in asBijectiveNonary) {
            output += when (i) {
                '1' -> "pa"
                '2' -> "pi"
                '3' -> "pu"
                '4' -> "ma"
                '5' -> "mi"
                '6' -> "mu"
                '7' -> "sa"
                '8' -> "si"
                '9' -> "su"
                else -> throw AssertionError("A digit other than 1-9 was found. Please report an issue on GitHub with this error message. number: $number")
            }
        }
        return if (asBijectiveNonary.length <= 2) output + "sa" else output
    }

    /**
     * Returns a String containing `number` written in bijective nonary, using the digits 1-9.
     * An empty String is returned if `number` is 0, in accordance with the definition of bijective bases on [Wikipedia](https://en.wikipedia.org/wiki/Bijective_numeration#Definition).
     */
    @JvmSynthetic
    fun toBijectiveNonary(number: ULong): String {
        // assume no leading zeroes
        if (number == 0UL) return ""
        val nonaryArray = number.toString(9).let {
            if (!it.contains('0')) return it
            it.toCharArray()
        }
        var i = nonaryArray.size - 1
        while (i >= 0) {
            if (nonaryArray[i] != '0') {
                i--
                continue
            }
            val j = run {
                var j = i + 1
                while (nonaryArray[--j] == '0');
                j + 1
            }
            for (k in j..i) {
                nonaryArray[k - 1] = (nonaryArray[k - 1].digitToInt() - 1).digitToChar()
                nonaryArray[k] = '9'
            }
            i = j - 2
        }
        return String(nonaryArray)
    }

    /**
     * Returns `true` if a word is orthographically and phonotactically valid in suma sisi, and `false` otherwise.
     * Capitalization is ignored.
     * Multiple words can be separated by whitespace, and this method will only return `true` if all words in `sequence` are valid.
     * Leading and trailing whitespace is ignored (the [String.trim] method is called on `sequence`).
     * A sequence containing punctuation marks, numbers, or other non-letter characters returns `false`,
     * as well as an empty sequence or one containing only whitespace.
     */
    @JvmStatic
    fun isValidSequence(sequence: String): Boolean {
        val sequence = sequence.lowercase().trim()
        if (sequence.contains(Regex("[^aiupsm\\s+]")) || sequence.isBlank()) return false

        // Check for a multiple-word sequence
        val wordArray = sequence.split(Regex("\\s+"))
        if (wordArray.isEmpty()) return false
        if (wordArray.size > 1) {
            var output = true
            for (i in wordArray) output = isValidSequence(i) && output
            return output
        }

        if (!sequence[sequence.length - 1].isVowel()) return false

        val firstCharIsVowel = sequence[0].isVowel()

        // These should be the opposite of the first character
        for (i in 1 until sequence.length step 2)
            if (sequence[i].isVowel() == firstCharIsVowel) return false

        // These should be the same as the first character
        for (i in 2 until sequence.length step 2)
            if (sequence[i].isVowel() != firstCharIsVowel) return false

        return true
    }

    private fun Char.isVowel() = this == 'a' || this == 'i' || this == 'u'
}