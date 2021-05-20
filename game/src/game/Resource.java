package game;

import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Resource {
	public static void Save(Serializable data, String fileName) throws Exception {
		try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
			
		}
	}
	public static Object Load(String fileName) throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
			return ois.readObject();
		}
	}
}
