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
6. ssh-agent在Git for Windows上自动启动
ssh-agent在打开bash或Git shell时自动运行。复制下面的行，并将其粘贴到您~/.profile或~/.bashrcGit的shell文件：
  env=~/.ssh/agent.env

  agent_load_env () { test -f "$env" && . "$env" >| /dev/null ; }

  agent_start () {
    (umask 077; ssh-agent >| "$env")
    . "$env" >| /dev/null ; }

  agent_load_env

  # agent_run_state: 0=agent running w/ key; 1=agent w/o key; 2= agent not running
  agent_run_state=$(ssh-add -l >| /dev/null 2>&1; echo $?)

  if [ ! "$SSH_AUTH_SOCK" ] || [ $agent_run_state = 2 ]; then
    agent_start
    ssh-add
  elif [ "$SSH_AUTH_SOCK" ] && [ $agent_run_state = 1 ]; then
    ssh-add
  fi

  unset env
