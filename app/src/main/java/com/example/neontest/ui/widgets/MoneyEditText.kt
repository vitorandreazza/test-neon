package com.example.neontest.ui.widgets

import android.content.Context
import android.text.InputFilter
import android.text.Spanned
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.View.OnFocusChangeListener
import androidx.appcompat.widget.AppCompatEditText
import com.example.neontest.R
import com.example.neontest.extensions.content
import com.example.neontest.extensions.toMoney
import java.util.*

class MoneyEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyle) {

    private val localCurrencySymbol: String
        get() = Currency.getInstance(Locale.getDefault()).symbol
    private var moneyFormat = MONEY_FORMAT_INTEGER
    var value: Float
        get() = extractValueFromContent()
        set(value) {
            content = value.toString()
        }
    private var firstFocus = true

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MoneyEditText)
        val maxLength = a.getInt(R.styleable.MoneyEditText_maxLength, -1)
        moneyFormat = a.getInt(R.styleable.MoneyEditText_moneyFormat, MONEY_FORMAT_INTEGER)
        a.recycle()

        val filters = arrayListOf<InputFilter>()
        if (maxLength > 0) filters.add(InputFilter.LengthFilter(maxLength + localCurrencySymbol.length))

        filters.add(prefixCurrencySymbolInputFilter())
        this.filters = filters.toTypedArray()

        //Needed to EditText accept currency symbol chars
        keyListener = DigitsKeyListener.getInstance(ACCEPTABLE_DIGITS + localCurrencySymbol)

        val padding = resources.getDimensionPixelSize(R.dimen.keyline5)
        setPadding(padding, 0, padding, 0)

        value = 0f
        onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus && firstFocus) {
                content = ""
                firstFocus = false
            }
        }
    }

    private fun prefixCurrencySymbolInputFilter(): InputFilter {
        return InputFilter { source, start, end, dest, dstart, dend ->
            if (dend <= localCurrencySymbol.length) {
                val filterResult = when {
                    willDeleteCurrencySymbol(source) -> dest.substring(dstart, dend)
                    isInputBeforeCurrencySymbolEnd(dend) && sourceIsNotCurrencySymbolChars(
                        source,
                        dest
                    ) -> ""
                    else -> prefixCurrencySymbol(dstart, source)
                }
                setSelection(dest.length)
                filterResult
            } else {
                null
            }
        }
    }

    private fun willDeleteCurrencySymbol(source: CharSequence) = source.isEmpty()

    private fun isInputBeforeCurrencySymbolEnd(dend: Int) = dend <= localCurrencySymbol.lastIndex

    private fun sourceIsNotCurrencySymbolChars(source: CharSequence, dest: Spanned) =
        source.none { localCurrencySymbol.contains(it) } && dest.contains(localCurrencySymbol)

    private fun prefixCurrencySymbol(dstart: Int, source: CharSequence?) =
        localCurrencySymbol.substring(dstart, localCurrencySymbol.length) + source

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        keepCursorAfterCurrencySymbol(selStart, selEnd)
    }

    private fun keepCursorAfterCurrencySymbol(selStart: Int, selEnd: Int) {
        if (!text.isNullOrEmpty() && selStart == localCurrencySymbol.lastIndex) {
            setSelection(localCurrencySymbol.lastIndex, selEnd)
        }
    }

    private fun extractValueFromContent() =
        if (content.isBlank() || content.trim() == localCurrencySymbol) {
            0f
        } else {
            content.replace(localCurrencySymbol, "").trim().toFloat()
        }

    override fun setText(text: CharSequence?, type: BufferType?) {
        val formattedText = if (text.isNullOrEmpty()) {
            ""
        } else {
            val maximumFractionDigits = when (moneyFormat) {
                MONEY_FORMAT_INTEGER -> 0
                MONEY_FORMAT_DECIMAL -> 2
                else -> throw IllegalArgumentException("Unrecognized money format")
            }
            val value = try {
                text.toString().toFloat()
            } catch (e: NumberFormatException) {
                text.replace(REMOVE_NON_NUMBER_REGEX.toRegex(), "").toFloat()
            }
            value.toMoney(maximumFractionDigits).replace(localCurrencySymbol, "")
        }
        super.setText(formattedText, type)
    }

    companion object {

        private const val ACCEPTABLE_DIGITS = "0123456789"
        private const val REMOVE_NON_NUMBER_REGEX = "[^\\d,.]"

        private const val MONEY_FORMAT_INTEGER = 0
        private const val MONEY_FORMAT_DECIMAL = 1
    }
}