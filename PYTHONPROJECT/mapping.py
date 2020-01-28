# -*- coding: utf-8 -*-
"""
Created on Thu Jun 13 17:45:27 2019

@author: KIIT
"""

import folium
map=folium.Map(location=[38.58, -99.89],zoom_start=6,tiles="Mapbox Bright")
#now we will add marker
fg=folium.FeatureGroup(name="My Map")
for coordinates in [[38.2,-99.1],[39.2,-97.1]]:
    fg.add_child(folium.Marker(location=coordinates,popup="Hi I am marker", icon=folium.Icon(color="green")))
map.add_child(fg)
fg.save("map2.html")