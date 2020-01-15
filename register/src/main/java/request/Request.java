package request;

import lombok.Data;

import java.io.Serializable;

@Data
public class Request<T> implements Serializable {

    private int type;

    private String url;

    private String method;

    private String alis;

    private T param;

}
