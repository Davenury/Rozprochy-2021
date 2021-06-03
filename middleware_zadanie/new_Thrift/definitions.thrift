struct Person {
    1: i32 id,
    2: string name,
    3: string surname,
    4: list<string> skills
}

struct Application {
    1: i32 id,
    2: Person wrom,
    3: Person hr
}

struct Process {
    1: i32 companyId,
    2: list<Application> applications
}

service Service {
    bool sendPerson(1: Person person)
    bool sendApplication(1: Application application)
    bool sendProcess(1: Process process)
}