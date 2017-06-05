package com.cooksys.ftd.assignments.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Client {

    /**
     * The client should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" and "host" properties of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.RemoteConfig} object to create a socket that connects to
     * a {@link Server} listening on the given host and port.
     *
     * The client should expect the server to send a {@link com.cooksys.ftd.assignments.socket.model.Student} object
     * over the socket as xml, and should unmarshal that object before printing its details to the console.
     */
    public static void main(String[] args) {
    	String path = "C:/Users/ftd-2/code/combined-assignments/5-socket-io-serialization/config/config.xml";
    	JAXBContext jaxb = Utils.createJAXBContext();
    	Config config = Utils.loadConfig(path, jaxb);
    	
		try {
			Socket s = new Socket(config.getRemote().getHost(), config.getRemote().getPort());
			DataInputStream input =
	                new DataInputStream(s.getInputStream());
			Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
			Student student = (Student) jaxbUnmarshaller.unmarshal(input);
			System.out.println(student.toString());
		} catch (IOException | JAXBException e) {
			e.printStackTrace();
		} finally {
		//	s.close();
		}
		
    	
    }
}
