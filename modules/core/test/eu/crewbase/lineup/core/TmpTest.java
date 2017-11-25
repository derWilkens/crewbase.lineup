package eu.crewbase.lineup.core;

import java.math.BigInteger;
import java.util.UUID;

public class TmpTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s2 = "2f0032cdbbeecbab2b0213641a9a907c";
		UUID siteId = new UUID(new BigInteger(s2.substring(0, 16), 16).longValue(),
		        new BigInteger(s2.substring(16), 16).longValue());
		System.out.println(siteId.toString());
	}

}
