import re
def getconfig(flag):
    result=''
    f=open('/home/qiao/IdeaProjects/tanklegendnetty/src/main/java/history/python/demo1.properties','r')
    for i in f:
        g=re.findall(flag+'.*=.*',i)
        if len(g)>0:
            al=g[0].split('=')
            result=al[1].strip()
            break
    return result