这次提交代码的时候我才完成注册界面和第一个封面然后我在研究怎么把注册信息存储到数据库的问题我想过通过jdbs驱动器连接起来但是好像PPT使用的是SQLite所以我打算先看一下PPT的相关知识，再研究研究感觉跟我想的好像不太一样

2024/06/28第二次提交的时候我主要是完善了一些增删改查的Activity以及联系人，设置之类的Activity数据库我也能成功创建了SQLite，然后就是发现这个程序并不需要我跟我的sql server 连接起来，就是通过一个数据库帮助类就能创建数据库，然后就是需要进一步完善一下聊天程序的功能，特别是联系人那块，还有聊天那块

2024/07/02第三次提交的我能实现Activity的跳转，是因为我往数据库帮助类的时候没有调用到另外两个表的的方法，最直接的方法就是把数据库删掉这样的话数据库帮助类的OnCreate方法就会自动帮我创建数据库并调用其中的表（通过View-->Tool Windows-->Device Explorer删除数据库），然后就是要注意绝对位置和相对位置的问题，相对位置的xml语句在运行以后后变换位置而绝对的、带约束的就不会出现这样的问题，然后下一步就是需要完善增删改查的功能和聊天的功能，还有就是如果运行出问题且没有飘红的话就在LogCat查看问题出现在哪里