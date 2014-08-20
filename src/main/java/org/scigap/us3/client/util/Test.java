package org.scigap.us3.client.util;

public class Test {

	public static void main(String[] args) {
//		String test = "Lmod Warning: The following modules have changed: TACC-paths, amber, cluster, cluster-paths, intel, mvapich2 Lmod Warning: "
//				+ "Please re-create this collection. "
//				+ "Restoring mkdir:modules to system default";
		String test = "/AAA/RRR/squeue -u test";
		String substring = test.substring(test.lastIndexOf("/")+1, test.indexOf(" "));
		System.out.println(substring + substring.length());
//		if(test.contains("mkdir:")){
//			System.out.println("AAAAAAAA");
//		}else{
//			System.out.println("VVVVV");
//		}
	}

}
