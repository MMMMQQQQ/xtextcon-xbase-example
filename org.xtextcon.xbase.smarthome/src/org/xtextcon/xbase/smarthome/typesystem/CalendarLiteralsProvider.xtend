package org.xtextcon.xbase.smarthome.typesystem

import org.eclipse.xtext.xbase.scoping.batch.ImplicitlyImportedFeatures
import org.xtextcon.xbase.smarthome.lib.CalendarLiterals

class CalendarLiteralsProvider extends ImplicitlyImportedFeatures {
	
	override protected getStaticImportClasses() {
		val result = super.getStaticImportClasses()
		result += CalendarLiterals
		return result
	}
	
}