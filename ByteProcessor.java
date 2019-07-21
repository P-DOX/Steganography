public class ByteProcessor
{
	public static final int MAX_FLAG = 3;
	//FLAGS Reference
	//1) 3:3:2
	//2) 3:2:3
	//3) 2:3:3

	//SliceByte
	private static int[] SliceByte(int data,int flag)
	{
		switch(flag)
		{
			case 1:
				return new int[] {(data & 0xE0)>>5 , (data & 0x1C)>>2 , (data & 0x3)};
			case 2:
				return new int[] {(data & 0xE0)>>5 , (data & 0x18)>>3 , (data & 0x7)};
			case 3:
				return new int[] {(data & 0xC0)>>6 , (data & 0x38)>>3 , (data & 0x7)};
		}
		return new int[]{0,0,0};

	}//slicebyte


    public static int[] MergeByte(int data,
    							  int R,
    							  int G,
    							  int B,
    							  int flag) throws IllegalArgumentException{
        
        int []sByte = SliceByte(data,flag);
        //FLAGS Reference
        //1) 3:3:2
        //2) 3:2:3
        //3) 2:3:3
        switch(flag)
        {
        	case 1:
        		return new int[]{(R & ~0x7) | sByte[0] , (G & ~0x7) | sByte[1] , (B & ~0x3) | sByte[2]};
    		case 2:
        		return new int[]{(R & ~0x7) | sByte[0] , (G & ~0x3) | sByte[1] , (B & ~0x7) | sByte[2]};
    		case 3:
        		return new int[]{(R & ~0x3) | sByte[0] , (G & ~0x7) | sByte[1] , (B & ~0x7) | sByte[2]};
        }

        throw new IllegalArgumentException("Invalid Flag Value Flag: "+flag);
    }

    //GetByte
    public static int GetByte(int arr[],int flag) throws IllegalArgumentException
    {
    	if(arr.length > 3)
    		throw new IllegalArgumentException("Array Size Greater Than 3");

    	//FLAGS Reference
    	//1) 3:3:2
    	//2) 3:2:3
    	//3) 2:3:3

    	switch(flag)
    	{
    		case 1:
    			return ( (arr[0] & 0x7)<<5 | (arr[1] & 0x7)<<2 | (arr[2] & 0x3) );
			case 2:
    			return ( (arr[0] & 0x7)<<5 | (arr[1] & 0x3)<<3 | (arr[2] & 0x7) );
			case 3:	 
    			return ( (arr[0] & 0x3)<<6 | (arr[1] & 0x7)<<3 | (arr[2] & 0x7) );
    	}

    	throw new IllegalArgumentException("Invalid Flag = "+ flag);
    }//getByte
}
