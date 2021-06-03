package sr.server;

import org.apache.thrift.TException;
import sr.Application;
import sr.Person;
import sr.Process;
import sr.Service;

public class ServiceHandler implements Service.Iface {

    public ServiceHandler(){}

    @Override
    public boolean sendPerson(Person person) throws TException {
        //System.out.println(person.id + " " + person.name + " " + person.surname);
        return true;
    }

    @Override
    public boolean sendApplication(Application application) throws TException {
        //System.out.println(application.id + " " + application.hr + " " + application.wrom);
        return true;
    }

    @Override
    public boolean sendProcess(Process process) throws TException {
        //System.out.println(process.companyId);
        return true;
    }
}