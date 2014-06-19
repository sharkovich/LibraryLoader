package ib.utilities.libloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


public class LibraryLoad implements Runnable {
	/**
	 * This method checks os version and automatically loads needed 
	 * OpenCV native libraries (included in jar)
	 * 
	 * @throws RuntimeException - when unable to load library
	 */
	public static void loadOpenCVLibrary() {
	    try {

	        File fileOut = null;
	        String osName = System.getProperty("os.name");
	        URL url = null;
	        
	        if(osName.startsWith("Windows")){
	            int bitness = Integer.parseInt(System.getProperty("sun.arch.data.model"));
	            if(bitness == 32){
	            	System.out.println("Loading 32bit OpenCV native library");
	            	url = LibraryLoad.class.getResource("/opencv/x86/opencv_java248.dll");

	                fileOut = File.createTempFile("lib", ".dll");
	            }
	            else if (bitness == 64){
	            	
	            	
	            	System.out.println("Loading 64bit OpenCV native library");

	            	url = LibraryLoad.class.getResource("/opencv/x64/opencv_java248.dll");


	                fileOut = File.createTempFile("lib_opencv", ".dll");
	            }
	            else{
	            	System.out.println("Loading 32bit OpenCV native library");
	            	url = LibraryLoad.class.getResource("/opencv/x86/opencv_java248.dll");

	                fileOut = File.createTempFile("lib_opencv", ".dll");
	            }
	        }
	        InputStream in = url.openStream();
	        OutputStream out = new FileOutputStream(fileOut);

	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = in.read(buffer)) > 0) {
	            out.write(buffer, 0, length);
	        }

	        fileOut.deleteOnExit();
	        out.close();
	        in.close();
	        System.load(fileOut.toString());
	        System.out.println("Loaded file: " + fileOut.toString());
	        
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to load opencv native library", e);

	    }
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		loadOpenCVLibrary();		
	}


}
