package com.theonlytails.latexbot

import com.nfbsoftware.latex.LaTeXConverter
import java.io.File

fun String.parseLatex(): File = LaTeXConverter.convertToImage(this)

fun String.notBlankOrNull() = this.ifBlank { null }