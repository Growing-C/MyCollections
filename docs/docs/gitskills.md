## 1 删除分支-----------------
git 删除分支，打开git命令界面输入 
```
git push origin ：分支名（分支名前无空格）
```

## 2 标签------------------
git push 标签：
```
git push origin --tags 
```

## 3 重命名分支------------------
git重命名分支： git branch -m oldname newname
git重命名远程分支（origin***）其实就是先用上面重命名的方式重命名本地分支
，然后用 
```
git push origin newname
```
 把重命名的本地分支推送到远程成为origin。
最后删除老的远程分支  就行了
在git中重命名远程分支，其实就是先删除远程分支，然后重命名本地分支，再重新提交一个远程分支。



## 4 项目免输密码------------------------
git提交的时候总要输入用户名密码，很是麻烦
这里提供一个单独对某个项目免密的方式（因为有的情况电脑上会使用多个git账号）
如果还未添加远程地址，可以输入一下命令：
```
git remote add origin https://username:password@github.com/Growing-C/MyCollections
```
或者直接用gitextension  clone库的时候填写上述地址  

如果已添加远程地址

最为简单的方式就是，直接在.git/config文件中进行修改（在项目目录下），按如上格式，添加用户名和密码

还有一种方式
在工程文件夹下的.git下打开config文件
添加   
```
[credential]
 helper = store
```
在输入一次用户名密码后就可以保存了


## 5. 版本回退-----------------------
如果不小心删除了git的内容，打开git命令行 输入 git reflog 可查看到历史纪录 head前面有6位码  记住要回退的版本
然后输入  
```
git rest --hard xxxxxx(如0bfafad)
```

## 6. 修改提交------------------
如果merge之类的 提交信息 想要修改的话输入

```
git commit --amend
```

会弹出修改的文档，修改后保存即可