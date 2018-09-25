package com.imlianai.dollpub.app.modules.support.relation.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/** 
 * 一致性hash代码 
 *  
 * @author wangzh 
 * 
 * @param <T> 
 */  
public class ConsistencyHashUtils<T> {  
	
	public static List<Node> ndList = new ArrayList<Node>();
	//初始化所有分表
	static{
		ndList.add(new Node("relation_1"));
        ndList.add(new Node("relation_2"));
        ndList.add(new Node("relation_3"));
        ndList.add(new Node("relation_4"));
        ndList.add(new Node("relation_5"));
        ndList.add(new Node("relation_6"));
        ndList.add(new Node("relation_7"));
        ndList.add(new Node("relation_8"));
        ndList.add(new Node("relation_9"));
        ndList.add(new Node("relation_10"));
        
        ndList.add(new Node("relation_11"));
        ndList.add(new Node("relation_12"));
        ndList.add(new Node("relation_13"));
        ndList.add(new Node("relation_14"));
        ndList.add(new Node("relation_15"));
        ndList.add(new Node("relation_16"));
        ndList.add(new Node("relation_17"));
        ndList.add(new Node("relation_18"));
        ndList.add(new Node("relation_19"));
        ndList.add(new Node("relation_20"));
        
        ndList.add(new Node("relation_21"));
        ndList.add(new Node("relation_22"));
        ndList.add(new Node("relation_23"));
        ndList.add(new Node("relation_24"));
        ndList.add(new Node("relation_25"));
        ndList.add(new Node("relation_26"));
        ndList.add(new Node("relation_27"));
        ndList.add(new Node("relation_28"));
        ndList.add(new Node("relation_29"));
        ndList.add(new Node("relation_30"));
        
        ndList.add(new Node("relation_31"));
        ndList.add(new Node("relation_32"));
        ndList.add(new Node("relation_33"));
        ndList.add(new Node("relation_34"));
        ndList.add(new Node("relation_35"));
        ndList.add(new Node("relation_36"));
        ndList.add(new Node("relation_37"));
        ndList.add(new Node("relation_38"));
        ndList.add(new Node("relation_39"));
        ndList.add(new Node("relation_40"));
	}
  
    // 真实节点对应的虚拟节点数量  
    private int length = 100;  
    // 虚拟节点信息  
    private  TreeMap<Long, T> virtualNodes = null;
    // 真实节点信息  
    private List<T> realNodes;  
    
    final static ConsistencyHashUtils<Node> sh = new ConsistencyHashUtils<Node>(ndList);
	
  
    public ConsistencyHashUtils(List<T> realNodes) {  
        this.realNodes = realNodes;  
        init();  
    }  
  
    public List<T> getReal() {  
        return realNodes;  
    }  
  
    /** 
     * 初始化虚拟节点 
     */  
    private void init() {  
    	virtualNodes = new TreeMap<Long, T>();  
        for (int i = 0; i < realNodes.size(); i++) {  
            for (int j = 0; j < length; j++) {  
                virtualNodes.put(hash("aa" + i + j), realNodes.get(i));  
            }  
        }  
    }  
  
    /** 
     * 获取一个结点 
     *  
     * @param key 
     * @return 
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    public T getNode(String key) {  
        Long hashedKey = hash(key);  
        Entry en = virtualNodes.ceilingEntry(hashedKey);  
        if (en == null) {  
            return (T) virtualNodes.firstEntry().getValue();  
        }  
        return (T) en.getValue();  
    }  
  
    /** 
     * MurMurHash算法，是非加密HASH算法，性能很高， 
     * 比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免） 
     * 等HASH算法要快很多，而且据说这个算法的碰撞率很低. http://murmurhash.googlepages.com/ 
     */  
    private Long hash(String key) {  
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());  
        int seed = 0x1234ABCD;  
  
        ByteOrder byteOrder = buf.order();  
        buf.order(ByteOrder.LITTLE_ENDIAN);  
  
        long m = 0xc6a4a7935bd1e995L;  
        int r = 47;  
  
        long h = seed ^ (buf.remaining() * m);  
  
        long k;  
        while (buf.remaining() >= 8) {  
            k = buf.getLong();  
  
            k *= m;  
            k ^= k >>> r;  
            k *= m;  
  
            h ^= k;  
            h *= m;  
        }  
  
        if (buf.remaining() > 0) {  
            ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);  
            // for big-endian version, do this first:  
            // finish.position(8-buf.remaining());  
            finish.put(buf).rewind();  
            h ^= finish.getLong();  
            h *= m;  
        }  
  
        h ^= h >>> r;  
        h *= m;  
        h ^= h >>> r;  
  
        buf.order(byteOrder);  
        return h;  
    }  
    
    
    /**
     * 从哈希环上取表名
     * @return
     */
    public static String getTableNameOnRing(long uid){
    	String tableName = sh.getNode(String.valueOf(uid)).getName();
    	return tableName;
    }
    
  
    /** 
     * 测试内部类 
     *  
     * @author wangzh 
     * 
     */  
    static public class Node {  
        private String name;  
        private int count = 0;  
  
        public Node() {  
  
        }  
  
        public Node(String i) {  
            this.name = i;  
        }  
  
         
  
        public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getCount() {  
            return count;  
        }  
  
        // 同步方法，防止并发  
        synchronized public void inc() {  
            count++;  
        }  
  
    }  
   
}  
