package sr.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import sr.Application;
import sr.Person;
import sr.Process;
import sr.Service;
import sr.common.ProtocolType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Client {

    private static FileWriter fileWriter;

    public static void main(String[] args) throws TException, IOException {
        String protocolTypeArg = args[0];
        ProtocolType protocolType = ProtocolType.valueOf(protocolTypeArg);
        String host = "127.0.0.2";
        TTransport transport = new TSocket(host, 9080);
        transport.open();
        TProtocol protocol = createProtocol(protocolType, transport);

        FileWriter writer = new FileWriter("pomiary.txt", true);
        fileWriter = writer;
        writer.write("\nJAVA - " + protocolTypeArg + "\n");

        Service.Client client = new Service.Client(protocol);
        testPerson(client);
        System.out.println("Tested Person");
        testProcess(client);
        System.out.println("Tested Process");
        testApplication(client);
        System.out.println("Tested Application");

        transport.close();
        writer.close();
    }

    private static void testPerson(Service.Client client) throws TException, IOException {
        Person person = new Person(1, "name", "surname", Arrays.asList("skill1", "skill2"));
        testOnePerson(client, person);
        testManySkillPerson(client, person, 1000);
    }

    private static void testOnePerson(Service.Client client, Person person) throws TException, IOException {
        double time = sendPerson(client, person);
        fileWriter.write("One skill person: "+String.valueOf(time)+"\n");
    }

    private static void testManySkillPerson(Service.Client client, Person person, int copies) throws TException, IOException {
        List<String> skills = Collections.nCopies(copies, "skill");
        person.setSkills(skills);
        double time = sendPerson(client, person);
        fileWriter.write("Many skill person: "+String.valueOf(time)+"\n");
    }

    private static double sendPerson(Service.Client client, Person person) throws TException {
        double start = System.currentTimeMillis();
        for(int i=0; i<1000; i++){
            client.sendPerson(person);
        }
        double stop = System.currentTimeMillis();
        return (stop - start) / 1000;
    }

    private static void testApplication(Service.Client client) throws TException, IOException {
        Application application = new Application(1, new Person(1, "worm", "worm", Arrays.asList("skill")), new Person(2, "hr", "hr", Arrays.asList("skill")));
        sendOneApplication(client, application);
        sendManyApplications(client, application, 1000);
    }

    private static void sendOneApplication(Service.Client client, Application application) throws TException, IOException {
        double time = sendApplications(client, application);
        fileWriter.write("Application one skills: "+String.valueOf(time)+"\n");
    }

    private static void sendManyApplications(Service.Client client, Application application, int copies) throws TException, IOException {
        application.getHr().setSkills(Collections.nCopies(copies, "skill"));
        application.getWrom().setSkills(Collections.nCopies(copies, "skill"));
        double time = sendApplications(client, application);
        fileWriter.write("Application many skills: "+String.valueOf(time)+"\n");
    }

    private static double sendApplications(Service.Client client, Application application) throws TException {
        double start = System.currentTimeMillis();
        for(int i=0; i<1000; i++){
            client.sendApplication(application);
        }
        double stop = System.currentTimeMillis();
        return (stop - start) / 1000;
    }

    private static void testProcess(Service.Client client) throws TException, IOException {
        Process process = new Process(1, Collections.singletonList(new Application(1, new Person(1, "worm", "worm", Arrays.asList("skill")), new Person(2, "hr", "hr", Arrays.asList("skill")))));
        sendOneProcess(client, process);
        sendManyProcesses(client, process, 1000);
    }

    private static void sendOneProcess(Service.Client client, Process process) throws TException, IOException {
        double time = sendProcesses(client, process);
        fileWriter.write("Process one application: "+String.valueOf(time)+"\n");
    }

    private static void sendManyProcesses(Service.Client client, Process process, int copies) throws TException, IOException {
        process.setApplications(Collections.nCopies(copies, new Application(1, new Person(1, "worm", "worm", Collections.singletonList("skill")), new Person(2, "hr", "hr", Collections.singletonList("skill")))));
        double time = sendProcesses(client, process);
        fileWriter.write("Process many applications: "+String.valueOf(time)+"\n");
    }

    private static double sendProcesses(Service.Client client, Process process) throws TException {
        double start = System.currentTimeMillis();
        for(int i=0; i<1000; i++){
            client.sendProcess(process);
        }
        double stop = System.currentTimeMillis();
        return (stop - start) / 1000;
    }

    private static TProtocol createProtocol(ProtocolType type, TTransport transport) {
        return switch (type) {
            case COMPACT -> new TCompactProtocol(transport);
            case BINARY -> new TBinaryProtocol(transport);
            case JSON -> new TJSONProtocol(transport);
        };
    }
}