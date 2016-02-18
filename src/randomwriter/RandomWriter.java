package randomwriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RandomWriter {
	public static void main(String[]args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a file name: ");
		String filename = input.nextLine();
		
		File f = new File(filename);
		if(f.isFile() && f.canRead()) {
			try {
				System.out.println("Enter N value: ");
				int ngramValue;
				if((ngramValue = input.nextInt()) < 3) {
					System.err.println("N must be 2 or greater.");
				}
				
			} catch (Exception e) {
				System.err.println("Illegal integer format. Try again.");
			}
		}
		else {
			System.err.println("Unable to open that file. Try again.");
		}
	}
}