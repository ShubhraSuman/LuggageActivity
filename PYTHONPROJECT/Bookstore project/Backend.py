# -*- coding: utf-8 -*-
"""
Created on Fri Jun 14 15:24:42 2019

@author: KIIT
"""

import sqlite3

#now we will connect the database
def connect():
    conn=sqlite3.connect("Books.db")
    curr=conn.cursor()
    curr.execute("CREATE TABLE IF NOT EXISTS book(id INTEGER PRIMARY KEY,title text,author text,year integer,isbn integer)")
    conn.commit()
    conn.close()
    
#now to add any data in the database we will use insert method
def insert(title,author,year,isbn):
    conn=sqlite3.connect("Book.db")
    curr=conn.cursor()
    curr.execute("INSERT INTO book VALUES(NULL,?,?,?,?)",(title,author,year,isbn))
    conn.commit()
    conn.close()

#now we will create view method to see the records
def view():
    conn=sqlite3.connect("Book.db")
    curr=conn.cursor()
    curr.execute("SELECT *FROM book")
    rows=curr.fetchall()
    conn.close()  
    return rows


#now we will create a function tat will search for a particular data
def search(title="",author="",year="",isbn=""): 
    conn=sqlite3.connect("Book.db")
    curr=conn.cursor()
    curr.execute("SELECT *FROM book WHERE title=? or author=? or year=? or isbn=?",(title,author,year,isbn))
    rows=curr.fetchall()
    conn.close()  
    return rows

#now we will create delete function
def delete(id):
    conn=sqlite3.connect("Book.db")
    curr=conn.cursor()
    curr.execute("DELETE FROM book WHERE id=?",(id,))
    conn.commit()
    conn.close()  

#now we will write update fucntion
def update(id,title,author,year,isbn):
     conn=sqlite3.connect("Book.db")
     curr=conn.cursor()
     curr.execute("UPDATE book SET title=? , author=?, year=?,isbn=? WHERE id=?",(title,author,year,isbn,id))
     conn.commit()
     conn.close()  


connect()
#insert("The math","shubhra suman",20198,11234)
#delete(3)
#print(view())
#print("REcord founded is:")
#print(search(author="shubhra smith"))