from history.python.interfacePython import Worker


class Worker(Worker):
    def __init__(self):
        self.first = "FirstName"
        self.last = "LastName"

    def getFirstName(self):
        return self.first

    def getLastName(self):
        return self.last

    def getId(self):
        return self.Id

    def setId(self, newId):
        self.Id = newId