package learn.nio.buffer;

import java.nio.ByteBuffer;

public class AbsoluteBufferTest {

	public static void main(String[] args) {

		ByteBuffer buf = ByteBuffer.allocate(10);
		System.out.println("Init Position :" + buf.position());
		System.out.println(", Init Limit :" + buf.limit());
		System.out.println(", Init Capacity :" + buf.capacity());
		
		buf.put(3, (byte) 3).put(4,(byte) 4).put(5, (byte) 5);
		
		
		System.out.println("Value : " + buf.get(3) + ", position : " + buf.position());
		System.out.println("Value : " + buf.get(4) + ", position : " + buf.position());
		System.out.println("Value : " + buf.get(5) + ", position : " + buf.position());
		
		System.out.println("Value : " + buf.get(9) + ", position : " + buf.position());
	}

}
