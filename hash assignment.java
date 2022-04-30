import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Hw03
{
	public static void UCFxram(String input, int len)
	{
		char[] data = new char[input.length()];
		int tempData;
		
		int randVal1 = 0xbcde98ef;
		int randVal2 = 0x7890face;
        int hashVal = 0xfa01bc96;
        int roundedEnd = len & 0xfffffffc;
        
        for(int i = 0; i < input.length(); i++)//puts all the input in data array
        {
            data[i] = input.charAt(i);
        }
        
        for(int i = 0; i < roundedEnd; i = i + 4) 
		{
        	tempData = (data[i] & 0xff) | ((data[i + 1] & 0xff) << 8) | ((data[i + 2] & 0xff) << 16) | ((data[i + 3] & 0xff) << 24);
			tempData = tempData * randVal1;
            tempData = Integer.rotateLeft(tempData, 12);
            tempData = tempData * randVal2;
			hashVal = hashVal ^ tempData;
			hashVal = Integer.rotateLeft(hashVal, 13);
            hashVal = hashVal * 5 + 0x46b6456e;
        }
        
		tempData = 0;
		
		if((len & 0x03) == 3) 
		{
			tempData = (data[roundedEnd + 2] & 0xff) << 16;
			len = len - 1;
		}
		if((len & 0x03) == 2) 
		{
			tempData |= ((data[roundedEnd +1 ] & 0xff) << 8);
			len = len - 1;
		}
		if((len & 0x03) == 1)
		{
			tempData |= (data[roundedEnd] & 0xff);
			tempData = tempData * randVal1;
			tempData = Integer.rotateLeft(tempData, 14);
			tempData = tempData * randVal2;
			hashVal = hashVal ^ tempData;
        }

        hashVal = hashVal ^ len;
        hashVal = hashVal & 0xb6acbe58;
        hashVal = hashVal ^ hashVal >>> 13;
        hashVal = hashVal * 0x53ea2b2c;
        hashVal = hashVal ^ hashVal >>> 16;
        
		System.out.format("%10x:%s\n",hashVal, input );//outputs in the correct format
	}

	public static void complexityIndicator()
	{
		System.err.println("no545885;1;2");
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		File file = new File(args[0]);
		Scanner in = new Scanner(file);

		while(in.hasNextLine()) 
		{
			String line = in.nextLine();
            UCFxram(line, line.length());
		}
		
		System.out.println("Input file processed");
	    complexityIndicator();
	}
}