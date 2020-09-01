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
(1)如果还未添加远程地址，可以输入一下命令：
```
git remote add origin https://username:password@github.com/Growing-C/MyCollections
```
或者直接用gitextension  clone库的时候填写上述地址  

(2)如果已添加远程地址

最为简单的方式就是，直接在.git/config文件中进行修改（在项目目录下），按如上格式，添加用户名和密码
```
[remote "origin"]
	url = https://Growing-C:password@github.com/Growing-C/MyCollections.git
	fetch = +refs/heads/*:refs/remotes/origin/*
[branch "master"]
	remote = origin
	merge = refs/heads/master
[user]
	name = Growing-C
	email = xxx@xxx,com
```


(3)还有一种方式
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



## 7. github pages------------------
github pages 可以用来搭建个人博客，也可以用来直接搭载个人网站
不过在index.html中  引入的.js 必须使用 ./js/main.js这种相对路径 因为js/main.js这种识别不了

最好在docs文件夹中放入js项目 在根目录下放似乎有点问题？

引用的 .js最好放在body中？放在header中似乎有问题（待确认）


## 8. git拉取项目失败，Early EOF 错误------------------
```
https://stackoverflow.com/questions/21277806/fatal-early-eof-fatal-index-pack-failed


first, turn off compression:

git config --global core.compression 0
Next, let's do a partial clone to truncate the amount of info coming down:

git clone --depth 1 <repo_URI>                   //这个clone只会clone最近有操作的分支的一个commit
When that works, go into the new directory and retrieve the rest of the clone:

git fetch --unshallow 
or, alternately,

git fetch --depth=2147483647
Now, do a regular pull:

git pull --all
```

如果不行，设置了 compression = 0之后 可以  
git fetch --depth=100  慢慢往上升


## 9.有时候git fetch获取不到远程分支
有时候遇到上面8的 early EOF的时候 使用了 depth等 拉取成功后，fetch一直获取不到远程分支
此时查看 .git 中的config
```
[remote "origin"]
	url = http://xxxxx.git
	fetch = +refs/heads/*:refs/remotes/origin/*
```
注意必须是*
获取 不到远程分支的原因是 fetch中 指定了远程分支如下，此时怎么都获取不到其他分支了，改成*即可
```
fetch = +refs/heads/dev20171216:refs/remotes/origin/dev20171216
```


