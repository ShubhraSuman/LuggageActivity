from tkinter import *

window=Tk()

l1=Label(window,text="Title")
l1.grid(row=0,column=0)

l2=Label(window,text="Author")
l2.grid(row=0,column=2)

l3=Label(window,text="Year")
l3.grid(row=1,column=0)


l4=Label(window,text="ISBN")
l4.grid(row=1,column=2)


#Now we have to make enteries
#fro title column
title_text=StringVar()
e1=Entry(window,textvariable=title_text)
e1.grid(row=0,column=1)

#now for author column
author_text=StringVar()
e1=Entry(window,textvariable=author_text)
e1.grid(row=0,column=3)

#now fro year column
year_text=StringVar()
e1=Entry(window,textvariable=year_text)
e1.grid(row=1,column=1)

#now for ISBN number
isbn_text=StringVar()
e1=Entry(window,textvariable=isbn_text)
e1.grid(row=1,column=3)


#now we will make a listbox
list1=Listbox(window,height=6,width=35)
list1.grid(row=2,column=0,rowspan=6,columnspan=2)


#now we will make scrollbar
sb1=Scrollbar(window)
sb1.grid(row=2,column=2,rowspan=6)

list1.configure(yscrollcommand=sb1.set)
sb1.configure(command=list1.yview)

#now we have to make 6 buttons
b1=Button(window,text="View all",width=12)
b1.grid(row=2,column=3)

b1=Button(window,text="Search entry",width=12)
b1.grid(row=3,column=3)

b1=Button(window,text="Add Entry",width=12)
b1.grid(row=3,column=3)

b1=Button(window,text="Update",width=12)
b1.grid(row=4,column=3)

b1=Button(window,text="Delete",width=12)
b1.grid(row=5,column=3)

b1=Button(window,text="Close",width=12)
b1.grid(row=6,column=3)


window.mainloop()