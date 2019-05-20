# demo

## github rsa
1. 生成rsa证书
> ssh-keygen -t rsa -b 4096 -C "your_email@example.com"
2. 运行ssh-agent
> eval $(ssh-agent -s)
3. 将SSH私钥添加到ssh-agent
> ssh-add ~/.ssh/id_rsa
4. 拷贝公钥内容
> clip < ~/.ssh/id_rsa.pub
5. 登录github，新建SSH密钥
