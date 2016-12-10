#!/usr/bin/env python 

import threading
import datetime
import urllib2

allgo = threading.Condition()

class ThreadClass(threading.Thread):
    def run(self):
        url = urllib2.urlopen("http://132.207.12.236:8000?name=krab")
	print url

for i in range(50):
    t = ThreadClass()
    t.start()

print "done"
exit(1)
