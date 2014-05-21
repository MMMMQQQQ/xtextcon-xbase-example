package org.xtextcon.xbase.smarthome.lib

import com.google.common.util.concurrent.RateLimiter
import java.util.Calendar

interface TimeDependent {
	def void trigger(Calendar time)	
}

class Simulator {
	
	@Property Calendar calendar
	
	new() {
		this.calendar = Calendar.getInstance
		calendar.set(Calendar.SECOND, 0)
	}
	
	def void setTime(int hour, int min) {
		synchronized(calendar) {
			calendar.set(Calendar.HOUR_OF_DAY, hour)
			calendar.set(Calendar.MINUTE, min)
			println("Time set to " + hour + ":" + min)
		}
	}
	
	def void submit(TimeDependent instance) {
		new Thread [|
			val runnable = new Runnable() {
				override run() {
					synchronized(calendar) {
						calendar.add(Calendar.MINUTE, 1)
						instance.trigger(calendar)
					}
				}
			}
			val rateLimiter = RateLimiter.create(1.0)
			while(true) {
				rateLimiter.acquire
				runnable.run
			}
		].start
	}
	
}