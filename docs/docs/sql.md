1.大小写敏感性
SQLite 是不区分大小写的，但也有一些命令是大小写敏感的，比如 GLOB 和 glob 在 SQLite 的语句中有不同的含义。

2.添加表
"CREATE TABLE if not exists [" + TABLE_NAME_USER_ACCOUNT + "] (" + "["
                + ENUM_USER_ACCOUNT.USER_ID.toString() + "] TEXT NOT NULL, " + "["
                + ENUM_USER_ACCOUNT.PHONE.toString() + "] TEXT NOT NULL, " + "["
                + ENUM_USER_ACCOUNT.ACCOUNT.toString() + "] TEXT, " + "["
                + ENUM_USER_ACCOUNT.NAME.toString() + "] TEXT, " + "["
                + ENUM_USER_ACCOUNT.SEX.toString() + "] INTEGER NOT NULL," + "["
                + ENUM_USER_ACCOUNT.EMAIL.toString() + "] TEXT " + ")";

3.已有表添加一列
ALTER  TABLE   table-name  ADD COLUMN  column-name column-type 
例如在student表中添加一列名为name，类型为varchar:
alter table student add column name varchar; 

