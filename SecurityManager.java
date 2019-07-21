class SecurityManager
{
	private String password;
	private int index = 0;
	private int asciiSum = 0;
	private int len;

	public SecurityManager(String password)
	{
		this.password = password;
		this.len = password.length();
		//compute asciiSum
		for(int i=0;i<len;++i)
			asciiSum += (int)password.charAt(i);

	}

	public int GetPermutation()
	{
		return asciiSum % ByteProcessor.MAX_FLAG +1;
	}

	public int PrimaryCrypto(int data)
	{
		int y = data ^ password.charAt(index);
		index = (index+1) % len;
		return y;
	}
}