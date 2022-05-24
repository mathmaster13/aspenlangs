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
        val asBijectiveOctal = toBijectiveOctal(number)
        var output = if (asBijectiveOctal.length == 1) "sa" else ""
        asBijectiveOctal.forEach {
            output += when (it) {
                '1' -> "pa"
                '2' -> "pi"
                '3' -> "pu"
                '4' -> "ma"
                '5' -> "mi"
                '6' -> "mu"
                '7' -> "si"
                '8' -> "su"
                else -> throw AssertionError("A digit other than 1-8 was found. Please report an issue on GitHub with this error message. number: $number. digit: $it.")
            }
        }
        return if (asBijectiveOctal.length <= 2) output + "sa" else output
    }

    private val leadingZeroes = Regex("^0+")
    /**
     * Returns a String containing `number` written in bijective octal, using the digits 1-8.
     * An empty String is returned if `number` is 0, in accordance with the definition of bijective bases on [Wikipedia](https://en.wikipedia.org/wiki/Bijective_numeration#Definition).
     */
    @JvmSynthetic
    fun toBijectiveOctal(number: ULong): String {
        // assume no leading zeroes
        if (number == 0UL) return ""
        val octalArray = number.toString(8).let {
            if (!it.contains('0')) return it
            it.toCharArray()
        }
        var i = octalArray.size - 1
        while (i >= 0) {
            if (octalArray[i] != '0') {
                i--
                continue
            }
            val j = run {
                var j = i + 1
                while (octalArray[--j] == '0');
                j + 1
            }
            for (k in j..i) {
                octalArray[k - 1] = (octalArray[k - 1].digitToInt() - 1).digitToChar()
                octalArray[k] = '8'
            }
            i = j - 2
        }
        return String(octalArray).replace(leadingZeroes, "")
    }

    @JvmField
    val phonotactics = Regex("[aiu]?([smp][aiu])*")
    /**
     * Returns `true` if a word is orthographically and phonotactically valid in suma sisi, and `false` otherwise.
     * Capitalization is ignored.
     * Multiple words can be separated by whitespace, and this method will only return `true` if all words in `sequence` are valid.
     * Leading and trailing whitespace is ignored (the [String.trim] method is called on `sequence`).
     * A sequence containing punctuation marks, numbers, or other non-letter characters returns `false`,
     * as well as an empty sequence or one containing only whitespace.
     *
     * @see phonotactics
     */
    // Because suma sisi is so small, just one regex will handle everything!
    @JvmStatic
    fun isValidSequence(sequence: String) = isValidSequence(sequence.trim().lowercase(), phonotactics)
}
