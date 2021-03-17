package uk.ac.swansea.dascalu.dvmicc.home

import android.content.Context
import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Format
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme

fun setCodeViewOptions(context: Context, codeView: CodeView, language: String, codeStringID: Int?) {
    //check if there actually is code to place in the code view
    if(codeStringID != null) {
        codeView.setOptions(Options.Default.get(context)
                .withLanguage(language)
                .withCode(context.getString(codeStringID))
                .withTheme(ColorTheme.MONOKAI)
                .withFormat(Format.ExtraCompact)
                .disableHighlightAnimation())
    }
}