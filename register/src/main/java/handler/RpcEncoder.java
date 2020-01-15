package handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    protected void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
          //  byte[] data = Serialization.serialize(in);
            // 编码将 in转化成jsonString
           String k =  JSONObject.toJSONString(in);
     //       out.writeInt(data.length);
            out.writeBytes(k.getBytes());
        }
    }
}
