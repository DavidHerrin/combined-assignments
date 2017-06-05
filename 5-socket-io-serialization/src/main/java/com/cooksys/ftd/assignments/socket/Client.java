package com.cooksys.ftd.assignments.socket;

import javax.xml.bind.JAXBContext;

import com.cooksys.ftd.assignments.socket.model.Config;

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
    	JAXBContext jaxb = createJAXBContext();
    	Config config = loadConfig(path, jaxb);
    }
}
