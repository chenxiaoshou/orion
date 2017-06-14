package common.others;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TokenTimeGenerator {

	public static void main(String[] args) {
		System.out.println(LocalDateTime.ofInstant(Instant.ofEpochMilli(1497343471213L), ZoneId.systemDefault()));
		System.out.println(LocalDateTime.ofInstant(Instant.ofEpochMilli(1497386671213L), ZoneId.systemDefault()));
		System.out.println(LocalDateTime.ofInstant(Instant.ofEpochMilli(1497935230629L), ZoneId.systemDefault()));
	}

}
