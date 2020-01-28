# -*- coding: utf-8 -*-
"""
Created on Thu Jun 13 22:56:05 2019

@author: KIIT
"""

import time
from datetime import datetime as dt
hosts_path=r'C:\Users\KIIT\Downloads\hosts'
redirect="127.0.0.1"
website_list=["www.facebook.com","facebook.com"]
while True:
    if dt(dt.now().year,dt.now().month,dt.now().day,21)<dt.now()<dt(dt.now().year,dt.now().month,dt.now().day,24):
        print("Working hours------------")
        with open(hosts_temp,'r+') as file:
            content=file.read()
            for website in website_list:
                if website in content:
                    pass
                else:
                    file.write(website)
    else:
        print("Fun hours--------")
    time.sleep(5)