# -*- coding: utf-8 -*-
"""
Created on Thu Jun 13 13:49:59 2019

@author: KIIT
"""
import pandas as pd
import geopy

df=pd.read_csv(r'C:\Users\KIIT\Downloads\supermarkets.csv')
#print(df)
from geopy.geocoders import Nominatim
non=Nominatim()
non.geocode("3995 23rd St , San Francisco, CA 94114")
