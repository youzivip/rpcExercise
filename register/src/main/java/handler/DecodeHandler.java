package handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class DecodeHandler extends ByteToMessageDecoder {
    private Class<?> clz;

    public DecodeHandler(Class<?> clz) {
        this.clz = clz;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("接收到消息了");
        //这个可以加String encoder
//        String a = in.toString(Charset.defaultCharset());

        byte[] data = new byte[in.readableBytes()];
        in.readBytes(data);
        Object obj = JSONObject.parseObject(data,clz);
        out.add(obj);
         // 为什么小于4要return呢 int从4开始读
//        if(in.readByte()<4) return;
//        in.markReaderIndex();
//        int dataLength = in.readInt();
//        if (in.readableBytes() < dataLength) {
//            in.resetReaderIndex();
//            return;
//        }
//        byte[] data = new byte[dataLength];
//        in.readBytes(data);
//        //使用序列化转化为对象
//        Object obj = Serialization.deserialize(data,clz);
//        out.add(obj);
    }
}
