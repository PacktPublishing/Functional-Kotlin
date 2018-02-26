package com.packtpub.functionalkotlin.chapter05

import javafx.application.Application
import tornadofx.*

/**
 * Created by IntelliJ IDEA.
 * @author Mario Arias
 * Date: 11/12/17
 * Time: 1:18 AM
 */

fun main(args: Array<String>) {
	Application.launch(FxApp::class.java, *args)
}

class FxApp: App(FxView::class)

class FxView: View() {
	override val root = vbox {
		label("Functional Kotlin")
		button("Press me")
	}
}