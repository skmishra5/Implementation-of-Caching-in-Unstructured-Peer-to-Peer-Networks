
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class zipfsDistribution
{
    int index; //rank
    String file;
   
    zipfsDistribution(int index, String file)
    {
        this.index = index;
        this.file = file;
    }
}

public class zipf
{
	private Random rnd = new Random(System.currentTimeMillis());
	private int N;
	private double s;
	private double bottom = 0;
 
	public zipf(int N, double s)
	{
		this.N = N;
		this.s = s;
 
		for(int i=1;i < N; i++)
		{
			this.bottom += (1/Math.pow(i, this.s));
		}
	}
 
 // the next() method returns an random rank id.
 // The frequency of returned rank ids are follows Zipf distribution.
	public int next()
	{
		int rank;
		double freq = 0;
		double dice;
 
		rank = rnd.nextInt(N);
		freq = (1.0d / Math.pow(rank, this.s)) / this.bottom;
		dice = rnd.nextDouble();
 
		while(!(dice < freq))
		{
			rank = rnd.nextInt(N);
			freq = (1.0d / Math.pow(rank, this.s)) / this.bottom;
			dice = rnd.nextDouble();
		}
 
		return rank;
	}
 
 // This method returns a probability that the given rank occurs.
	public double getProbability(int rank)
	{
		return (1.0d / Math.pow(rank, this.s)) / this.bottom;
	}
 
	public static void main(String[] args) throws IOException
	{
		if(args.length != 2) {
			System.out.println("usage: ./zipf size skew");
			System.exit(-1);
		}
  
		String fileName = "resources.txt"; //file from which we have to read data
		String line = null;
		FileReader fileReader = new FileReader(fileName);

		BufferedReader bufferedReader = new BufferedReader(fileReader);
  
		ArrayList<zipfsDistribution> ZL = new ArrayList<zipfsDistribution>();
 
		zipfsDistribution zip;
		int i=0;
		while((line = bufferedReader.readLine()) != null)
		{ 
      
			i=i+1;
      
			zip = new zipfsDistribution(i, line);
			ZL.add(zip);
    
		}
//		for(int j = 0;j< ZL.size(); j++)
//		{
//        //System.out.println("Entry Info:" + " " + ZL.get(j).index + " " + ZL.get(j).file); //printing the values in arraylist
//		}
      
		bufferedReader.close();
 
		zipf zipf = new zipf(Integer.valueOf(args[0]), Double.valueOf(args[1]));
//		for(int l=1;l <= 100; l++)
//			System.out.println(l+" "+zipf.getProbability(l));
		
		if(args[1].equals("0.2")){
		File file = new File("zipfprob.txt");
		file.createNewFile();
		FileWriter writer = new FileWriter(file); 
		int l = 1;
		for(int j = 0;j< ZL.size(); j++)
		{
			if(j == 0){
			writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "15" +"\n");}
			else if(j < 15)
			{
				writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "14" +"\n");
			}
			else if(j < 30)
			{
				writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "13" +"\n");
			}
			else if(j < 45)
			{
				writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "12" +"\n");
			}
			else if(j < 60)
			{
				writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "11" +"\n");
			}
			else if(j < 75)
			{
				writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "10" +"\n");
			}
			else if(j < 90)
			{
				writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "9" +"\n");
			}
			else if(j < 105)
			{
				writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "8" +"\n");
			}
			else if(j < 120)
			{
				writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "7" +"\n");
			}
			else if(j < 135)
			{
				writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "6" +"\n");
			}
			else if(j < 150)
			{
				writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "5" +"\n");
			}
			else if(j < 160)
			{
				writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "4" +"\n");
			}
			l++;
		} 
	    writer.flush();
	    writer.close();	}
		else if(args[1].equals("0.6")){
			File file = new File("zipfprob.txt");
			file.createNewFile();
			FileWriter writer = new FileWriter(file); 
			int l = 1;
			for(int j = 0;j< ZL.size(); j++)
			{
				if(j == 0){
				writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "18" +"\n");}
				else if(j < 15)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "14" +"\n");
				}
				else if(j < 30)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "13" +"\n");
				}
				else if(j < 45)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "12" +"\n");
				}
				else if(j < 60)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "11" +"\n");
				}
				else if(j < 75)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "10" +"\n");
				}
				else if(j < 90)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "9" +"\n");
				}
				else if(j < 105)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "8" +"\n");
				}
				else if(j < 120)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "6" +"\n");
				}
				else if(j < 135)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "4" +"\n");
				}
				else if(j < 150)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "3" +"\n");
				}
				else if(j < 160)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "2" +"\n");
				}
				l++;
			} 
		    writer.flush();
		    writer.close();	}
			else if(args[1].equals("0.8")){
			File file = new File("zipfprob.txt");
			file.createNewFile();
			FileWriter writer = new FileWriter(file); 
			int l = 1;
			for(int j = 0;j< ZL.size(); j++)
			{
				if(j == 0){
				writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "22" +"\n");}
				else if(j < 15)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "14" +"\n");
				}
				else if(j < 30)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "13" +"\n");
				}
				else if(j < 45)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "12" +"\n");
				}
				else if(j < 60)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "11" +"\n");
				}
				else if(j < 75)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "10" +"\n");
				}
				else if(j < 90)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "9" +"\n");
				}
				else if(j < 105)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "7" +"\n");
				}
				else if(j < 120)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "5" +"\n");
				}
				else if(j < 135)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "4" +"\n");
				}
				else if(j < 150)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "2" +"\n");
				}
				else if(j < 160)
				{
					writer.write(ZL.get(j).index + ":" + ZL.get(j).file + ":" + zipf.getProbability(l) + ":" + "1" +"\n");
				}
				l++;
			} 
		    writer.flush();
		    writer.close();	}
 	}
 
}
