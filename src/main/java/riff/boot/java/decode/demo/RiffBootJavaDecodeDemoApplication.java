package riff.boot.java.decode.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Base64;
import java.util.function.Function;

@SpringBootApplication
public class RiffBootJavaDecodeDemoApplication implements Function<String,String> {

	private static final Logger LOGGER= LoggerFactory.getLogger(RiffBootJavaDecodeDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RiffBootJavaDecodeDemoApplication.class, args);
	}

	@Override
	public String apply(String s) {
		LOGGER.info("Received input------->"+s);

        byte []decodedBytes = Base64.getDecoder().decode(s);
		String decoded = new String(decodedBytes);

		LOGGER.info("output----->  "+decoded);
		return decoded;
	}

}
