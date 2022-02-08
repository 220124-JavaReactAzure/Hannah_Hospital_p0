//package com.revature.p0.util.collections;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//import com.revature.p0.models.*;
//
//public class TempDriver {
//
//	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//	public static void main(String[] args) {
//		List<User> myList = new ArrayList<User>();
//		for (int i = 0; i < 2; i++) {
//			try {
//				System.out.println("What's your type?");
//				String type = br.readLine();
//				System.out.println("What's your first name?");
//				String first = br.readLine();
//				System.out.println("What's your last name?");
//				String last = br.readLine();
//				User user = new User(type, first, last);
//				myList.add(user);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		System.out.println(myList.get(0).getFirstName());
//	
//
//
//		
//		
////			myStringList.add("Hello");
////			myStringList.add("There");
////			myStringList.add("Ahhh");
////			myStringList.add("General");
////			myStringList.add("Kenobi");
////			
////			System.out.println(myStringList.contains("Hello")); // true
////			System.out.println(myStringList.contains("There")); // true
////			System.out.println(myStringList.contains("Ahhh"));  // false
////			System.out.println(myStringList.contains("General")); // true
////			System.out.println(myStringList.contains("Kenobi")); // true
////			
////			myStringList.remove("There");
////			myStringList.contains("There");
////			myStringList.add("There");		
////			myStringList.contains("There");
////			
////			
////			myStringList.get(1);
////			myStringList.get(0);
//	}
//
//}
