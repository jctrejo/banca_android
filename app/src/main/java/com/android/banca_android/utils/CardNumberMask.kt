package com.android.banca_android.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.android.mibanca.R
import java.util.Calendar
import java.util.regex.Pattern

class CardNumberMask(
    private val editText: EditText,
    private val textViewCopy: TextView,
    private val img: ImageView
) : TextWatcher {

    private var current: String = ""
    private val format: String = "••••••••••••••••"
    private val cal: Calendar = Calendar.getInstance()
    private val ptVisa = "^4[0-9]{12}(?:[0-9]{3}){0,2}$"
    private val ptMasterCard = "^(?:5[1-5]|2(?!2([01]|20)|7(2[1-9]|3))[2-7])\\d{14}$"
    private val ptAmeExp = "^3[47][0-9]{13}$"
    private var compare: Boolean = false

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s?.let {
            if (it.toString() != current) {
                var clean = it.toString().replace("[^\\d.]|\\.".toRegex(), "")
                val cleanC = current.replace("[^\\d.]|\\.".toRegex(), "")
                val cl = clean.length
                var sel = cl + cl / 4 // Incrementar la selección por cada bloque de 4 dígitos

                // Fix for pressing delete next to a forward slash
                if (clean == cleanC) sel--

                if (clean.length < 16) {
                    clean += format.substring(clean.length)
                }

                clean = String.format(
                    "%s %s %s %s",
                    clean.substring(0, minOf(clean.length, 4)),
                    clean.substring(4, minOf(clean.length, 8)),
                    clean.substring(8, minOf(clean.length, 12)),
                    clean.substring(12, minOf(clean.length, 16))
                )

                sel = sel.coerceAtLeast(0)
                if (cl >= 16) {
                    addImageCard(clean, img)
                } else {
                    img.setImageResource(android.R.color.transparent)
                    img.setBackgroundResource(R.drawable.shape_card_payments_photo)
                }

                current = clean
                editText.setText(current)
                textViewCopy.text = current
                editText.setSelection(sel.coerceAtMost(current.length))
            }
        }
    }

    private fun addImageCard(number: String, img: ImageView) {
        val card = number.replace(" ", "")
        compare = Pattern.matches(ptVisa, card)

        if (!compare) {
            compare = Pattern.matches(ptMasterCard, card)
            img.setImageResource(if (compare) R.drawable.ic_mastercard else R.drawable.ic_coinpurse)
        } else {
            img.setImageResource(R.drawable.ic_visa)
        }

        if (!compare) {
            compare = Pattern.matches(ptAmeExp, card)
            img.setImageResource(if (compare) R.drawable.ic_amex else R.drawable.ic_coinpurse)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(s: Editable?) {}
}