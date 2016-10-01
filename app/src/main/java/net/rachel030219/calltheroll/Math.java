package net.rachel030219.calltheroll;

import java.util.*;

public class Math
{
	public static int random(int min,int max){
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
	}
}
