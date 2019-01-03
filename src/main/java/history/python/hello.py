

def hello():
    return 'Hello world!'
def getValue(o):
    result = 0
    if o < 2:
      return -1
    else:
      for i in range(o):
       		result +=i
    return result