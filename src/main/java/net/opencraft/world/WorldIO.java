package net.opencraft.world;

import java.io.*;
import java.util.zip.Deflater;

import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class WorldIO {

	private WorldIO() {
	}
	
	public static void write(OutputStream out, World world) throws IOException {
		var gzos = new GZIPOutputStream(out) {
			{
				this.def.setLevel(Deflater.BEST_COMPRESSION);
			}
		};
		
		var oos = new ObjectOutputStream(gzos);
		oos.writeObject(world);
		oos.close();
	}
	
	public static World read(InputStream in) throws IOException, ClassNotFoundException, ClassCastException {
		Object obj;
		
		var gzis = new GZIPInputStream(in);
		var ois = new ObjectInputStream(gzis);
		obj = ois.readObject();
		ois.close();
		
		return (World) obj;
	}
}
