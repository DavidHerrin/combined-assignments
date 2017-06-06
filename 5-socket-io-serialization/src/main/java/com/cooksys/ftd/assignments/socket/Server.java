package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Server extends Utils {

    /**
     * Reads a {@link Student} object from the given file path
     *
     * @param studentFilePath the file path from which to read the student config file
     * @param jaxb the JAXB context to use during unmarshalling
     * @return a {@link Student} object unmarshalled from the given file path
     */
    public static Student loadStudent(String studentFilePath, JAXBContext jaxb) {
    	File file = new File(studentFilePath);
    	Student student = null;
    	
		try {
			Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
			student = (Student) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return student;
    }

    /**
     * The server should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" property of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.LocalConfig} object to create a server socket that
     * listens for connections on the configured port.
     *
     * Upon receiving a connection, the server should unmarshal a {@link Student} object from a file location
     * specified by the config's "studentFilePath" property. It should then re-marshal the object to xml over the
     * socket's output stream, sending the object to the client.
     *
     * Following this transaction, the server may shut down or listen for more connections.
     */
    public static void main(String[] args) throws IOException{
    	String path = "C:/Users/ftd-2/code/combined-assignments/5-socket-io-serialization/config/config.xml";
//    	LocalConfig localConfig = new LocalConfig();
//    	localConfig.setPort(9898);
//    	RemoteConfig remoteConfig = new RemoteConfig();
//    	remoteConfig.setHost("10.1.1.138");
//    	remoteConfig.setPort(7575);
//        Config config = new Config();
//        config.setLocal(localConfig);
//        config.setRemote(remoteConfig);      
//        config.setStudentFilePath(path);
//      JAXBContext jaxbContext = createJAXBContext();
//      Marshaller jaxbMarshaller;
//		try {
//			jaxbMarshaller = jaxbContext.createMarshaller();
//			File file = new File(path);
//	        jaxbMarshaller.marshal(config, file);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
//        
//    	String path2 = "C:/Users/ftd-2/code/combined-assignments/5-socket-io-serialization/config/student.xml";
//      Student student = new Student();
//      student.setFirstName("David");
//      student.setLastName("Herrin");
//      student.setFavoriteIDE("Eclipse");
//      student.setFavoriteLanguage("Java");
//      student.setFavoriteParadigm("Object-Oriented");
//		try {
//			jaxbMarshaller = jaxbContext.createMarshaller();
//			File file = new File(path2);
//	        jaxbMarshaller.marshal(student, file);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
    	
    	JAXBContext jaxb = createJAXBContext();
    	Config config = loadConfig(path, jaxb);
    	 	
		
		ServerSocket listener = new ServerSocket(config.getLocal().getPort());
		try {
            while (true) {
                Socket socket = listener.accept();
                try {
                	Student student = loadStudent(config.getStudentFilePath(), jaxb);
                	
                	DataOutputStream out =
                            new DataOutputStream(socket.getOutputStream());
                	Marshaller jaxbMarshaller = jaxb.createMarshaller();
                	jaxbMarshaller.marshal(student, out);
                	break;
                } catch (IOException | JAXBException e) {
                	
                }
                finally {
                    socket.close();
                }
            } 
        }
        finally {
            listener.close();
        }
        
    }
}
