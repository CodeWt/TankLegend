1. python3的字符串编码语言是使用unicode编码,由于python的自负床类型是str，
在内存中以Unicode表示，一个字符对应若干个字节，如果要在网络上传输，或者保存在磁盘上
就需要吧str变成自己为单位的bytes。
    python对byte类型的数据用带b前缀的单引号或双引号表示。
2. 