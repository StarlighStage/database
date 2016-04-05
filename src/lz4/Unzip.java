package lz4;

import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

public class Unzip {
	String addrOrig = "";
	LZ4Factory factory = LZ4Factory.fastestInstance();
	LZ4FastDecompressor decompressor = factory.fastDecompressor();

}
