package net.opencraft;

public class SharedConstants {

	public static final String VERSION_STRING = "1.0.0";
	
	public static final String INIT_ERROR_MESSAGE = "%s didn't initiate correctly!",
							   INVALID_DIR_MESSAGE = "Invalid directory!";
	
	
	public static final int GENERIC_ERROR_CODE     = 0x0F,
							INIT_ERROR_CODE        = 0x01,
							NO_NATIVES_LINKED_CODE = 0x02,
							INVALID_DIR_CODE       = 0x03,
							FORCE_THREAD_EXIT_CODE = 0x04;
	
}