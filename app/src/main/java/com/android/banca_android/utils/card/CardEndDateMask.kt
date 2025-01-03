package com.android.banca_android.utils.card

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import java.util.Calendar

class CardEndDateMask(
    private val editText: EditText,
    private val textViewCopy: TextView
) : TextWatcher {

    private var current: String = ""
    private val ddmmyyyy: String = "MMAA"
    private val cal: Calendar = Calendar.getInstance()

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s?.let {
            if (it.toString() != current) {
                var clean = it.toString().replace("[^\\d.]|\\.".toRegex(), "")
                val cleanC = current.replace("[^\\d.]|\\.".toRegex(), "")

                val cl = clean.length
                var sel = cl

                for (i in 2 until cl.coerceAtMost(4) step 2) {
                    sel++
                }

                // Fix for pressing delete next to a forward slash
                if (clean == cleanC) sel--

                if (clean.length < 4) {
                    clean += ddmmyyyy.substring(clean.length)
                } else {
                    // Ensure that when we finish entering numbers the date is correct
                    var mon = clean.substring(0, 2).toInt()
                    val year = clean.substring(2, 4).toInt()
                    mon = when {
                        mon < 1 -> 1
                        mon > 12 -> 12
                        else -> mon
                    }

                    clean = String.format("%02d%02d", mon, year)
                }

                clean = String.format("%s/%s", clean.substring(0, 2), clean.substring(2, 4))

                sel = sel.coerceAtLeast(0)
                current = clean
                editText.setText(current)
                textViewCopy.text = current
                editText.setSelection(sel.coerceAtMost(current.length))
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(s: Editable?) {}
}